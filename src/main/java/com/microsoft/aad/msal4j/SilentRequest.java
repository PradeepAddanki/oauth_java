package com.microsoft.aad.msal4j;

import java.net.MalformedURLException;
import java.net.URL;

class SilentRequest extends MsalRequest {

    private SilentParameters parameters;
    private IUserAssertion assertion;
    private Authority requestAuthority;

    SilentRequest(SilentParameters parameters,
                  AbstractClientApplicationBase application,
                  RequestContext requestContext,
                  IUserAssertion assertion) throws MalformedURLException {

        super(application, null, requestContext);

        this.parameters = parameters;
        this.assertion = assertion;
        this.requestAuthority = StringHelper.isBlank(parameters.authorityUrl()) ?
                application.authenticationAuthority :
                Authority.createAuthority(new URL(parameters.authorityUrl()));

        if (parameters.forceRefresh()) {
            application.getServiceBundle().getServerSideTelemetry().getCurrentRequest().setCacheInfo(
                    CacheTelemetry.REFRESH_FORCE_REFRESH.telemetryValue);
        }
    }

    public SilentParameters getParameters() {
        return parameters;
    }

    public void setParameters(SilentParameters parameters) {
        this.parameters = parameters;
    }

    public IUserAssertion getAssertion() {
        return assertion;
    }

    public void setAssertion(IUserAssertion assertion) {
        this.assertion = assertion;
    }

    public Authority getRequestAuthority() {
        return requestAuthority;
    }

    public void setRequestAuthority(Authority requestAuthority) {
        this.requestAuthority = requestAuthority;
    }
}
