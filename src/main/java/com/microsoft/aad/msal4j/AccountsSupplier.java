package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.account.IAccount;

import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.function.Supplier;

class AccountsSupplier implements Supplier<Set<IAccount>> {

    AbstractClientApplicationBase clientApplication;
    MsalRequest msalRequest;

    AccountsSupplier(AbstractClientApplicationBase clientApplication, MsalRequest msalRequest) {

        this.clientApplication = clientApplication;
        this.msalRequest = msalRequest;
    }

    @Override
    public Set<IAccount> get() {
        try {
            return clientApplication.tokenCache.getAccounts
                    (clientApplication.clientId());

        } catch (Exception ex) {
            clientApplication.log.error(
                    LogHelper.createMessage("Execution of " + this.getClass() + " failed.",
                            msalRequest.headers().getHeaderCorrelationIdValue()), ex);

            throw new CompletionException(ex);
        }
    }
}
