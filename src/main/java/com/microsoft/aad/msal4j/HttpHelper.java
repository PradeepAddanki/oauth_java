package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.Exception.VasaraCloudException;
import com.microsoft.aad.msal4j.account.IAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static com.microsoft.aad.msal4j.Constants.POINT_DELIMITER;

class HttpHelper {

    private static final Logger log = LoggerFactory.getLogger(HttpHelper.class);
    public static final String RETRY_AFTER_HEADER = "Retry-After";
    public static final int RETRY_NUM = 2;
    public static final int RETRY_DELAY_MS = 1000;

    public static final int HTTP_STATUS_200 = 200;
    public static final int HTTP_STATUS_429 = 429;
    public static final int HTTP_STATUS_500 = 500;

    private HttpHelper() {
    }

    static IHttpResponse executeHttpRequest(HttpRequest httpRequest,
                                            RequestContext requestContext,
                                            ServiceBundle serviceBundle) {
        checkForThrottling(requestContext);

        HttpEvent httpEvent = new HttpEvent(); // for tracking http telemetry
        IHttpResponse httpResponse;

        try (TelemetryHelper telemetryHelper = serviceBundle.getTelemetryManager().createTelemetryHelper(
                requestContext.getTelemetryRequestId(),
                requestContext.getClientId(),
                httpEvent,
                false)) {

            addRequestInfoToTelemetry(httpRequest, httpEvent);

            try {
                IHttpClient httpClient = serviceBundle.getHttpClient();
                httpResponse = executeHttpRequestWithRetries(httpRequest, httpClient);

            } catch (Exception e) {
                httpEvent.setOauthErrorCode(AuthenticationErrorCode.UNKNOWN);
                throw new VasaraCloudException(e);
            }

            addResponseInfoToTelemetry(httpResponse, httpEvent);

            if (httpResponse.headers() != null) {
                HttpHelper.verifyReturnedCorrelationId(httpRequest, httpResponse);
            }
        }
        processThrottlingInstructions(httpResponse, requestContext);

        return httpResponse;
    }

    private static String getRequestThumbprint(RequestContext requestContext) {
        StringBuilder sb = new StringBuilder();
        sb.append(requestContext.getClientId() + POINT_DELIMITER);
        sb.append(requestContext.getAuthority() + POINT_DELIMITER);

        IAcquireTokenParameters apiParameters = requestContext.getApiParameters();

        if (apiParameters instanceof SilentParameters) {
            IAccount account = ((SilentParameters) apiParameters).account();
            if (account != null) {
                sb.append(account.homeAccountId() + POINT_DELIMITER);
            }
        }

        Set<String> sortedScopes = new TreeSet<>(apiParameters.scopes());
        sb.append(String.join(" ", sortedScopes));

        return StringHelper.createSha256Hash(sb.toString());
    }

    private static boolean isRetryable(IHttpResponse httpResponse) {
        return httpResponse.statusCode() >= HTTP_STATUS_500 &&
                getRetryAfterHeader(httpResponse) == null;
    }

    private static IHttpResponse executeHttpRequestWithRetries(HttpRequest httpRequest, IHttpClient httpClient)
            throws Exception {
        IHttpResponse httpResponse = null;
        for (int i = 0; i < RETRY_NUM; i++) {
            httpResponse = httpClient.send(httpRequest);
            if (!isRetryable(httpResponse)) {
                break;
            }
            Thread.sleep(RETRY_DELAY_MS);
        }
        return httpResponse;
    }

    private static void checkForThrottling(RequestContext requestContext) {
        if (requestContext.getClientApplication() instanceof PublicClientApplication &&
                requestContext.getApiParameters() != null) {
            String requestThumbprint = getRequestThumbprint(requestContext);

            long retryInMs = ThrottlingCache.retryInMs(requestThumbprint);

            if (retryInMs > 0) {
                throw new MsalThrottlingException(retryInMs);
            }
        }
    }

    private static void processThrottlingInstructions(IHttpResponse httpResponse, RequestContext requestContext) {
        if (requestContext.getClientApplication() instanceof PublicClientApplication) {
            Long expirationTimestamp = null;

            Integer retryAfterHeaderVal = getRetryAfterHeader(httpResponse);
            if (retryAfterHeaderVal != null) {
                expirationTimestamp = System.currentTimeMillis() + retryAfterHeaderVal * 1000;
            } else if (httpResponse.statusCode() == HTTP_STATUS_429 ||
                    (httpResponse.statusCode() >= HTTP_STATUS_500)) {

                expirationTimestamp = System.currentTimeMillis() + ThrottlingCache.DEFAULT_THROTTLING_TIME_SEC * 1000;
            }
            if (expirationTimestamp != null) {
                ThrottlingCache.set(getRequestThumbprint(requestContext), expirationTimestamp);
            }
        }
    }

    private static Integer getRetryAfterHeader(IHttpResponse httpResponse) {

        if (httpResponse.headers() != null) {
            TreeMap<String, List<String>> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            headers.putAll(httpResponse.headers());

            if (headers.containsKey(RETRY_AFTER_HEADER) && headers.get(RETRY_AFTER_HEADER).size() == 1) {
                try {
                    int headerValue = Integer.parseInt(headers.get(RETRY_AFTER_HEADER).get(0));

                    if (headerValue > 0 && headerValue <= ThrottlingCache.MAX_THROTTLING_TIME_SEC) {
                        return headerValue;
                    }
                } catch (NumberFormatException ex) {
                    log.warn("Failed to parse value of Retry-After header - NumberFormatException");
                }
            }
        }
        return null;
    }

    private static void addRequestInfoToTelemetry(final HttpRequest httpRequest, HttpEvent httpEvent) {
        try {
            httpEvent.setHttpPath(httpRequest.getUrl().toURI());
            httpEvent.setHttpMethod(httpRequest.getHttpMethod().toString());
            if (!StringHelper.isBlank(httpRequest.getUrl().getQuery())) {
                httpEvent.setQueryParameters(httpRequest.getUrl().getQuery());
            }
        } catch (Exception ex) {
            String correlationId = httpRequest.headerValue(
                    HttpHeaders.CORRELATION_ID_HEADER_NAME);

            log.warn(LogHelper.createMessage("Setting URL telemetry fields failed: " +
                            LogHelper.getPiiScrubbedDetails(ex),
                    correlationId != null ? correlationId : ""));
        }
    }

    private static void addResponseInfoToTelemetry(IHttpResponse httpResponse, HttpEvent httpEvent) {

        httpEvent.setHttpResponseStatus(httpResponse.statusCode());

        Map<String, List<String>> headers = httpResponse.headers();

        String userAgent = HttpUtils.headerValue(headers, "User-Agent");
        if (!StringHelper.isBlank(userAgent)) {
            httpEvent.setUserAgent(userAgent);
        }

        String xMsRequestId = HttpUtils.headerValue(headers, "x-ms-request-id");
        if (!StringHelper.isBlank(xMsRequestId)) {
            httpEvent.setRequestIdHeader(xMsRequestId);
        }

        String xMsClientTelemetry = HttpUtils.headerValue(headers, "x-ms-clitelem");
        if (xMsClientTelemetry != null) {
            XmsClientTelemetryInfo xmsClientTelemetryInfo =
                    XmsClientTelemetryInfo.parseXmsTelemetryInfo(xMsClientTelemetry);

            if (xmsClientTelemetryInfo != null) {
                httpEvent.setXmsClientTelemetryInfo(xmsClientTelemetryInfo);
            }
        }
    }

    private static void verifyReturnedCorrelationId(final HttpRequest httpRequest,
                                                    IHttpResponse httpResponse) {

        String sentCorrelationId = httpRequest.headerValue(
                HttpHeaders.CORRELATION_ID_HEADER_NAME);

        String returnedCorrelationId = HttpUtils.headerValue(
                httpResponse.headers(),
                HttpHeaders.CORRELATION_ID_HEADER_NAME);

        if (StringHelper.isBlank(returnedCorrelationId) ||
                !returnedCorrelationId.equals(sentCorrelationId)) {

            String msg = LogHelper.createMessage(
                    String.format(
                            "Sent (%s) Correlation Id is not same as received (%s).",
                            sentCorrelationId,
                            returnedCorrelationId),
                    sentCorrelationId);

            log.info(msg);
        }
    }
}
