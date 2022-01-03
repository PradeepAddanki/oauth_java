package com.microsoft.aad.msal4j;


import java.util.UUID;

class RequestContext {

    private String telemetryRequestId;
    private String clientId;
    private String correlationId;
    private PublicApi publicApi;
    private String applicationName;
    private String applicationVersion;
    private String authority;
    private IAcquireTokenParameters apiParameters;
    private IClientApplicationBase clientApplication;
    private UserIdentifier userIdentifier;

    public RequestContext(AbstractClientApplicationBase clientApplication,
                          PublicApi publicApi,
                          IAcquireTokenParameters apiParameters) {
        this.clientApplication = clientApplication;

        this.clientId = StringHelper.isBlank(clientApplication.clientId()) ?
                "unset_client_id" :
                clientApplication.clientId();
        this.correlationId = StringHelper.isBlank(clientApplication.correlationId()) ?
                generateNewCorrelationId() :
                clientApplication.correlationId();

        this.applicationVersion = clientApplication.applicationVersion();
        this.applicationName = clientApplication.applicationName();
        this.publicApi = publicApi;
        this.authority = clientApplication.authority();
        this.apiParameters = apiParameters;
    }

    public RequestContext(AbstractClientApplicationBase clientApplication,
                          PublicApi publicApi,
                          IAcquireTokenParameters apiParameters,
                          UserIdentifier userIdentifier) {
        this(clientApplication, publicApi, apiParameters);
        this.userIdentifier = userIdentifier;
    }

    private static String generateNewCorrelationId() {
        return UUID.randomUUID().toString();
    }

    public String getTelemetryRequestId() {
        return telemetryRequestId;
    }

    public void setTelemetryRequestId(String telemetryRequestId) {
        this.telemetryRequestId = telemetryRequestId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public PublicApi getPublicApi() {
        return publicApi;
    }

    public void setPublicApi(PublicApi publicApi) {
        this.publicApi = publicApi;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public IAcquireTokenParameters getApiParameters() {
        return apiParameters;
    }

    public void setApiParameters(IAcquireTokenParameters apiParameters) {
        this.apiParameters = apiParameters;
    }

    public IClientApplicationBase getClientApplication() {
        return clientApplication;
    }

    public void setClientApplication(IClientApplicationBase clientApplication) {
        this.clientApplication = clientApplication;
    }

    public UserIdentifier getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(UserIdentifier userIdentifier) {
        this.userIdentifier = userIdentifier;
    }
}