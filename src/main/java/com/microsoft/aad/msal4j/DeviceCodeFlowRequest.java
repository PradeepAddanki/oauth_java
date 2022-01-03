package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.result.IAuthenticationResult;
import com.nimbusds.oauth2.sdk.util.URLUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
class DeviceCodeFlowRequest extends MsalRequest {

    private AtomicReference<CompletableFuture<IAuthenticationResult>> futureReference;

    private DeviceCodeFlowParameters parameters;
    private String scopesStr;

    public AtomicReference<CompletableFuture<IAuthenticationResult>> getFutureReference() {
        return futureReference;
    }

    public void setFutureReference(AtomicReference<CompletableFuture<IAuthenticationResult>> futureReference) {
        this.futureReference = futureReference;
    }

    public DeviceCodeFlowParameters getParameters() {
        return parameters;
    }

    public void setParameters(DeviceCodeFlowParameters parameters) {
        this.parameters = parameters;
    }

    public String getScopesStr() {
        return scopesStr;
    }

    public void setScopesStr(String scopesStr) {
        this.scopesStr = scopesStr;
    }

    DeviceCodeFlowRequest(DeviceCodeFlowParameters parameters,
                          AtomicReference<CompletableFuture<IAuthenticationResult>> futureReference,
                          PublicClientApplication application,
                          RequestContext requestContext) {

        super(application, null, requestContext);

        this.parameters = parameters;
        this.scopesStr = String.join(" ", parameters.scopes());
        this.futureReference = futureReference;
    }

    DeviceCode acquireDeviceCode(String url,
                                 String clientId,
                                 Map<String, String> clientDataHeaders,
                                 ServiceBundle serviceBundle) {

        Map<String, String> headers = appendToHeaders(clientDataHeaders);
        String bodyParams = createQueryParams(clientId);

        HttpRequest httpRequest = new HttpRequest(HttpMethod.POST, url, headers, bodyParams);

        final IHttpResponse response = HttpHelper.executeHttpRequest(
                httpRequest,
                this.requestContext(),
                serviceBundle);

        if (response.statusCode() != HttpHelper.HTTP_STATUS_200) {
            throw MsalServiceExceptionFactory.fromHttpResponse(response);
        }

        return parseJsonToDeviceCodeAndSetParameters(response.body(), headers, clientId);
    }

    void createAuthenticationGrant(DeviceCode deviceCode) {
        msalAuthorizationGrant = new DeviceCodeAuthorizationGrant(deviceCode, deviceCode.getScopes(), parameters.claims());
    }

    private String createQueryParams(String clientId) {
        Map<String, List<String>> queryParameters = new HashMap<>();
        queryParameters.put("client_id", Collections.singletonList(clientId));

        String scopesParam = AbstractMsalAuthorizationGrant.COMMON_SCOPES_PARAM +
                AbstractMsalAuthorizationGrant.SCOPES_DELIMITER + scopesStr;

        queryParameters.put("scope", Collections.singletonList(scopesParam));

        return URLUtils.serializeParameters(queryParameters);
    }

    private Map<String, String> appendToHeaders(Map<String, String> clientDataHeaders) {
        Map<String, String> headers = new HashMap<>(clientDataHeaders);
        headers.put("Accept", "application/json");

        return headers;
    }

    private DeviceCode parseJsonToDeviceCodeAndSetParameters(
            String json,
            Map<String, String> headers,
            String clientId) {

        DeviceCode result;
        result = JsonHelper.convertJsonToObject(json, DeviceCode.class);

        String correlationIdHeader = headers.get(HttpHeaders.CORRELATION_ID_HEADER_NAME);
        if (correlationIdHeader != null) {
            result.setCorrelationId(correlationIdHeader);
        }

        result.setClientId(clientId);
        result.setScopes(scopesStr);

        return result;
    }
}