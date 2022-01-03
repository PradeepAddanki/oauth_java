package com.microsoft.aad.msal4j;

import java.util.concurrent.atomic.AtomicReference;

abstract class MsalRequest {
    AbstractMsalAuthorizationGrant msalAuthorizationGrant;
    private final AbstractClientApplicationBase application;
    private final RequestContext requestContext;
    private final AtomicReference<Object> headers = new AtomicReference();

    MsalRequest(AbstractClientApplicationBase clientApplicationBase, AbstractMsalAuthorizationGrant abstractMsalAuthorizationGrant, RequestContext requestContext) {
        this.application = clientApplicationBase;
        this.msalAuthorizationGrant = abstractMsalAuthorizationGrant;
        this.requestContext = requestContext;
        CurrentRequest currentRequest = new CurrentRequest(requestContext.getPublicApi());
        this.application.getServiceBundle().getServerSideTelemetry().setCurrentRequest(currentRequest);
    }

    AbstractMsalAuthorizationGrant msalAuthorizationGrant() {
        return this.msalAuthorizationGrant;
    }

    AbstractClientApplicationBase application() {
        return this.application;
    }

    RequestContext requestContext() {
        return this.requestContext;
    }

    public MsalRequest(AbstractMsalAuthorizationGrant msalAuthorizationGrant, AbstractClientApplicationBase application, RequestContext requestContext) {
        this.msalAuthorizationGrant = msalAuthorizationGrant;
        this.application = application;
        this.requestContext = requestContext;
    }

    HttpHeaders headers() {
        Object value = this.headers.get();
        if (value == null) {
            synchronized(this.headers) {
                value = this.headers.get();
                if (value == null) {
                    HttpHeaders actualValue = new HttpHeaders(this.requestContext);
                    value = actualValue == null ? this.headers : actualValue;
                    this.headers.set(value);
                }
            }
        }

        return (HttpHeaders)(value == this.headers ? null : value);
    }
}
