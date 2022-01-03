package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.account.IAccount;

import java.util.concurrent.CompletionException;

class RemoveAccountRunnable implements Runnable {

    private RequestContext requestContext;
    private AbstractClientApplicationBase clientApplication;
    IAccount account;

    RemoveAccountRunnable(MsalRequest msalRequest, IAccount account) {
        this.clientApplication = msalRequest.application();
        this.requestContext = msalRequest.requestContext();
        this.account = account;
    }

    @Override
    public void run() {
        try {
            clientApplication.tokenCache.removeAccount
                    (clientApplication.clientId(), account);

        } catch (Exception ex) {
            clientApplication.log.error(
                    LogHelper.createMessage("Execution of " + this.getClass() + " failed.",
                            requestContext.getCorrelationId()), ex);

            throw new CompletionException(ex);
        }
    }
}
