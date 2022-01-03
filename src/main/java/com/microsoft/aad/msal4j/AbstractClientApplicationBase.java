//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.Exception.VasaraCloudException;
import com.microsoft.aad.msal4j.account.IAccount;
import com.microsoft.aad.msal4j.result.IAuthenticationResult;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import javax.net.ssl.SSLSocketFactory;
import org.slf4j.Logger;

public abstract class AbstractClientApplicationBase implements IClientApplicationBase {
    protected Logger log;
    protected Authority authenticationAuthority;
    private ServiceBundle serviceBundle;
    private String clientId;
    private String authority;
    private boolean validateAuthority;
    private String correlationId;
    private boolean logPii;
    private Consumer<List<HashMap<String, String>>> telemetryConsumer;
    private Proxy proxy;
    private SSLSocketFactory sslSocketFactory;
    private Integer connectTimeoutForDefaultHttpClient;
    private Integer readTimeoutForDefaultHttpClient;
    protected TokenCache tokenCache;
    private String applicationName;
    private String applicationVersion;
    private AadInstanceDiscoveryResponse aadAadInstanceDiscoveryResponse;
    private String clientCapabilities;
    private boolean autoDetectRegion;
    private String azureRegion;

    protected abstract ClientAuthentication clientAuthentication();

    public CompletableFuture<IAuthenticationResult> acquireToken(AuthorizationCodeParameters parameters) {
        ParameterValidationUtils.validateNotNull("parameters", parameters);
        RequestContext context = new RequestContext(this, PublicApi.ACQUIRE_TOKEN_BY_AUTHORIZATION_CODE, parameters);
        AuthorizationCodeRequest authorizationCodeRequest = new AuthorizationCodeRequest(parameters, this, context);
        return this.executeRequest(authorizationCodeRequest);
    }

    public CompletableFuture<IAuthenticationResult> acquireToken(RefreshTokenParameters parameters) {
        ParameterValidationUtils.validateNotNull("parameters", parameters);
        RequestContext context = new RequestContext(this, PublicApi.ACQUIRE_TOKEN_BY_REFRESH_TOKEN, parameters);
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(parameters, this, context);
        return this.executeRequest(refreshTokenRequest);
    }

    public CompletableFuture<IAuthenticationResult> acquireTokenSilently(SilentParameters parameters) throws MalformedURLException {
        ParameterValidationUtils.validateNotNull("parameters", parameters);
        RequestContext context;
        if (parameters.account() != null) {
            context = new RequestContext(this, PublicApi.ACQUIRE_TOKEN_SILENTLY, parameters, UserIdentifier.fromHomeAccountId(parameters.account().homeAccountId()));
        } else {
            context = new RequestContext(this, PublicApi.ACQUIRE_TOKEN_SILENTLY, parameters);
        }

        SilentRequest silentRequest = new SilentRequest(parameters, this, context, (IUserAssertion)null);
        return this.executeRequest(silentRequest);
    }

    public CompletableFuture<Set<IAccount>> getAccounts() {
        RequestContext context = new RequestContext(this, PublicApi.GET_ACCOUNTS, (IAcquireTokenParameters)null);
        MsalRequest msalRequest = new MsalRequest(this, (AbstractMsalAuthorizationGrant)null, context) {
        };
        AccountsSupplier supplier = new AccountsSupplier(this, msalRequest);
        return this.serviceBundle.getExecutorService() != null ? CompletableFuture.supplyAsync(supplier, this.serviceBundle.getExecutorService()) : CompletableFuture.supplyAsync(supplier);
    }

    public CompletableFuture<Void> removeAccount(IAccount account) {
        RequestContext context = new RequestContext(this, PublicApi.REMOVE_ACCOUNTS, (IAcquireTokenParameters)null);
        MsalRequest msalRequest = new MsalRequest(this, (AbstractMsalAuthorizationGrant)null, context) {
        };
        RemoveAccountRunnable runnable = new RemoveAccountRunnable(msalRequest, account);
        return this.serviceBundle.getExecutorService() != null ? CompletableFuture.runAsync(runnable, this.serviceBundle.getExecutorService()) : CompletableFuture.runAsync(runnable);
    }

    public URL getAuthorizationRequestUrl(AuthorizationRequestUrlParameters parameters) {
        ParameterValidationUtils.validateNotNull("parameters", parameters);
        parameters.requestParameters.put("client_id", Collections.singletonList(this.clientId));
        if (this.clientCapabilities != null) {
            if (parameters.requestParameters.containsKey("claims")) {
                String claims = String.valueOf(((List)parameters.requestParameters.get("claims")).get(0));
                String mergedClaimsCapabilities = JsonHelper.mergeJSONString(claims, this.clientCapabilities);
                parameters.requestParameters.put("claims", Collections.singletonList(mergedClaimsCapabilities));
            } else {
                parameters.requestParameters.put("claims", Collections.singletonList(this.clientCapabilities));
            }
        }

        return parameters.createAuthorizationURL(this.authenticationAuthority, parameters.getRequestParameters());
    }

    CompletableFuture<IAuthenticationResult> executeRequest(MsalRequest msalRequest) {
        AuthenticationResultSupplier supplier = this.getAuthenticationResultSupplier(msalRequest);
        ExecutorService executorService = this.serviceBundle.getExecutorService();
        return executorService != null ? CompletableFuture.supplyAsync(supplier, executorService) : CompletableFuture.supplyAsync(supplier);
    }

    AuthenticationResult acquireTokenCommon(MsalRequest msalRequest, Authority requestAuthority) throws Exception {
        HttpHeaders headers = msalRequest.headers();
        if (this.logPii) {
            this.log.debug(LogHelper.createMessage(String.format("Using Client Http Headers: %s", headers), headers.getHeaderCorrelationIdValue()));
        }

        TokenRequestExecutor requestExecutor = new TokenRequestExecutor(requestAuthority, msalRequest, this.serviceBundle);
        AuthenticationResult result = requestExecutor.executeTokenRequest();
        if (this.authenticationAuthority.authorityType.equals(AuthorityType.AAD)) {
            InstanceDiscoveryMetadataEntry instanceDiscoveryMetadata = AadInstanceDiscoveryProvider.getMetadataEntry(requestAuthority.canonicalAuthorityUrl(), this.validateAuthority, msalRequest, this.serviceBundle);
            this.tokenCache.saveTokens(requestExecutor, result, instanceDiscoveryMetadata.preferredCache);
        } else {
            this.tokenCache.saveTokens(requestExecutor, result, this.authenticationAuthority.host);
        }

        return result;
    }

    private AuthenticationResultSupplier getAuthenticationResultSupplier(MsalRequest msalRequest) {
        Object supplier;
        if (msalRequest instanceof DeviceCodeFlowRequest) {
            supplier = new AcquireTokenByDeviceCodeFlowSupplier((PublicClientApplication)this, (DeviceCodeFlowRequest)msalRequest);
        } else if (msalRequest instanceof SilentRequest) {
            supplier = new AcquireTokenSilentSupplier(this, (SilentRequest)msalRequest);
        } else if (msalRequest instanceof InteractiveRequest) {
            supplier = new AcquireTokenByInteractiveFlowSupplier((PublicClientApplication)this, (InteractiveRequest)msalRequest);
        } else if (msalRequest instanceof ClientCredentialRequest) {
            supplier = new AcquireTokenByClientCredentialSupplier((ConfidentialClientApplication)this, (ClientCredentialRequest)msalRequest);
        } else if (msalRequest instanceof OnBehalfOfRequest) {
            supplier = new AcquireTokenByOnBehalfOfSupplier((ConfidentialClientApplication)this, (OnBehalfOfRequest)msalRequest);
        } else {
            supplier = new AcquireTokenByAuthorizationGrantSupplier(this, msalRequest, (Authority)null);
        }

        return (AuthenticationResultSupplier)supplier;
    }

    ServiceBundle getServiceBundle() {
        return this.serviceBundle;
    }

    protected static String enforceTrailingSlash(String authority) {
        authority = authority.toLowerCase();
        if (!authority.endsWith("/")) {
            authority = authority + "/";
        }

        return authority;
    }

    AbstractClientApplicationBase(AbstractClientApplicationBase.Builder<?> builder) {
        this.clientId = builder.clientId;
        this.authority = builder.authority;
        this.validateAuthority = builder.validateAuthority;
        this.correlationId = builder.correlationId;
        this.logPii = builder.logPii;
        this.applicationName = builder.applicationName;
        this.applicationVersion = builder.applicationVersion;
        this.telemetryConsumer = builder.telemetryConsumer;
        this.proxy = builder.proxy;
        this.sslSocketFactory = builder.sslSocketFactory;
        this.connectTimeoutForDefaultHttpClient = builder.connectTimeoutForDefaultHttpClient;
        this.readTimeoutForDefaultHttpClient = builder.readTimeoutForDefaultHttpClient;
        this.serviceBundle = new ServiceBundle(builder.executorService, (IHttpClient)(builder.httpClient == null ? new DefaultHttpClient(builder.proxy, builder.sslSocketFactory, builder.connectTimeoutForDefaultHttpClient, builder.readTimeoutForDefaultHttpClient) : builder.httpClient), new TelemetryManager(this.telemetryConsumer, builder.onlySendFailureTelemetry));
        this.authenticationAuthority = builder.authenticationAuthority;
        this.tokenCache = new TokenCache(builder.tokenCacheAccessAspect);
        this.aadAadInstanceDiscoveryResponse = builder.aadInstanceDiscoveryResponse;
        this.clientCapabilities = builder.clientCapabilities;
        this.autoDetectRegion = builder.autoDetectRegion;
        this.azureRegion = builder.azureRegion;
        if (this.aadAadInstanceDiscoveryResponse != null) {
            AadInstanceDiscoveryProvider.cacheInstanceDiscoveryMetadata(this.authenticationAuthority.host, this.aadAadInstanceDiscoveryResponse);
        }

    }

    public String clientId() {
        return this.clientId;
    }

    public String authority() {
        return this.authority;
    }

    public boolean validateAuthority() {
        return this.validateAuthority;
    }

    public String correlationId() {
        return this.correlationId;
    }

    public boolean logPii() {
        return this.logPii;
    }

    Consumer<List<HashMap<String, String>>> telemetryConsumer() {
        return this.telemetryConsumer;
    }

    public Proxy proxy() {
        return this.proxy;
    }

    public SSLSocketFactory sslSocketFactory() {
        return this.sslSocketFactory;
    }

    public Integer connectTimeoutForDefaultHttpClient() {
        return this.connectTimeoutForDefaultHttpClient;
    }

    public Integer readTimeoutForDefaultHttpClient() {
        return this.readTimeoutForDefaultHttpClient;
    }

    public TokenCache tokenCache() {
        return this.tokenCache;
    }

    public String applicationName() {
        return this.applicationName;
    }

    public String applicationVersion() {
        return this.applicationVersion;
    }

    public AadInstanceDiscoveryResponse aadAadInstanceDiscoveryResponse() {
        return this.aadAadInstanceDiscoveryResponse;
    }

    public String clientCapabilities() {
        return this.clientCapabilities;
    }

    public boolean autoDetectRegion() {
        return this.autoDetectRegion;
    }

    public String azureRegion() {
        return this.azureRegion;
    }

    abstract static class Builder<T extends AbstractClientApplicationBase.Builder<T>> {
        private String clientId;
        private String authority = "https://login.microsoftonline.com/common/";
        private Authority authenticationAuthority = createDefaultAADAuthority();
        private boolean validateAuthority = true;
        private String correlationId;
        private boolean logPii = false;
        private ExecutorService executorService;
        private Proxy proxy;
        private SSLSocketFactory sslSocketFactory;
        private IHttpClient httpClient;
        private Consumer<List<HashMap<String, String>>> telemetryConsumer;
        private Boolean onlySendFailureTelemetry = false;
        private String applicationName;
        private String applicationVersion;
        private ITokenCacheAccessAspect tokenCacheAccessAspect;
        private AadInstanceDiscoveryResponse aadInstanceDiscoveryResponse;
        private String clientCapabilities;
        private boolean autoDetectRegion;
        private String azureRegion;
        private Integer connectTimeoutForDefaultHttpClient;
        private Integer readTimeoutForDefaultHttpClient;

        public Builder(String clientId) {
            ParameterValidationUtils.validateNotBlank("clientId", clientId);
            this.clientId = clientId;
        }

        abstract T self();

        public T authority(String val) throws MalformedURLException {
            this.authority = AbstractClientApplicationBase.enforceTrailingSlash(val);
            URL authorityURL = new URL(this.authority);
            Authority.validateAuthority(authorityURL);
            switch(Authority.detectAuthorityType(authorityURL)) {
                case AAD:
                    this.authenticationAuthority = new AADAuthority(authorityURL);
                    break;
                case ADFS:
                    this.authenticationAuthority = new ADFSAuthority(authorityURL);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported authority type.");
            }

            return this.self();
        }

        public T b2cAuthority(String val) throws MalformedURLException {
            this.authority = AbstractClientApplicationBase.enforceTrailingSlash(val);
            URL authorityURL = new URL(this.authority);
            Authority.validateAuthority(authorityURL);
            if (Authority.detectAuthorityType(authorityURL) != AuthorityType.B2C) {
                throw new IllegalArgumentException("Unsupported authority type. Please use B2C authority");
            } else {
                this.authenticationAuthority = new B2CAuthority(authorityURL);
                this.validateAuthority = false;
                return this.self();
            }
        }

        public T validateAuthority(boolean val) {
            this.validateAuthority = val;
            return this.self();
        }

        public T correlationId(String val) {
            ParameterValidationUtils.validateNotBlank("correlationId", val);
            this.correlationId = val;
            return this.self();
        }

        public T logPii(boolean val) {
            this.logPii = val;
            return this.self();
        }

        public T executorService(ExecutorService val) {
            ParameterValidationUtils.validateNotNull("executorService", val);
            this.executorService = val;
            return this.self();
        }

        public T proxy(Proxy val) {
            ParameterValidationUtils.validateNotNull("proxy", val);
            this.proxy = val;
            return this.self();
        }

        public T httpClient(IHttpClient val) {
            ParameterValidationUtils.validateNotNull("httpClient", val);
            this.httpClient = val;
            return this.self();
        }

        public T sslSocketFactory(SSLSocketFactory val) {
            ParameterValidationUtils.validateNotNull("sslSocketFactory", val);
            this.sslSocketFactory = val;
            return this.self();
        }

        public T connectTimeoutForDefaultHttpClient(Integer val) {
            ParameterValidationUtils.validateNotNull("connectTimeoutForDefaultHttpClient", val);
            this.connectTimeoutForDefaultHttpClient = val;
            return this.self();
        }

        public T readTimeoutForDefaultHttpClient(Integer val) {
            ParameterValidationUtils.validateNotNull("readTimeoutForDefaultHttpClient", val);
            this.readTimeoutForDefaultHttpClient = val;
            return this.self();
        }

        T telemetryConsumer(Consumer<List<HashMap<String, String>>> val) {
            ParameterValidationUtils.validateNotNull("telemetryConsumer", val);
            this.telemetryConsumer = val;
            return this.self();
        }

        T onlySendFailureTelemetry(Boolean val) {
            this.onlySendFailureTelemetry = val;
            return this.self();
        }

        public T applicationName(String val) {
            ParameterValidationUtils.validateNotNull("applicationName", val);
            this.applicationName = val;
            return this.self();
        }

        public T applicationVersion(String val) {
            ParameterValidationUtils.validateNotNull("applicationVersion", val);
            this.applicationVersion = val;
            return this.self();
        }

        public T setTokenCacheAccessAspect(ITokenCacheAccessAspect val) {
            ParameterValidationUtils.validateNotNull("tokenCacheAccessAspect", val);
            this.tokenCacheAccessAspect = val;
            return this.self();
        }

        public T aadInstanceDiscoveryResponse(String val) {
            ParameterValidationUtils.validateNotNull("aadInstanceDiscoveryResponse", val);
            this.aadInstanceDiscoveryResponse = AadInstanceDiscoveryProvider.parseInstanceDiscoveryMetadata(val);
            return this.self();
        }

        private static Authority createDefaultAADAuthority() {
            try {
                Authority authority = new AADAuthority(new URL("https://login.microsoftonline.com/common/"));
                return authority;
            } catch (Exception var2) {
                throw new VasaraCloudException(var2);
            }
        }

        public T clientCapabilities(Set<String> capabilities) {
            this.clientCapabilities = JsonHelper.formCapabilitiesJson(capabilities);
            return this.self();
        }

        public T autoDetectRegion(boolean val) {
            this.autoDetectRegion = val;
            return this.self();
        }

        public T azureRegion(String val) {
            this.azureRegion = val;
            return this.self();
        }

        abstract AbstractClientApplicationBase build();
    }
}
