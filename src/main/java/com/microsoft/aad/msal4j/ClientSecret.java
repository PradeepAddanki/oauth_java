package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.client.IClientSecret;

final class ClientSecret implements IClientSecret {

    private final String clientSecret;

    /**
     * Constructor to create credential with client id and secret
     *
     * @param clientSecret Secret of the client requesting the token.
     */
    ClientSecret(final String clientSecret) {
        if (StringHelper.isBlank(clientSecret)) {
            throw new IllegalArgumentException("clientSecret is null or empty");
        }

        this.clientSecret = clientSecret;
    }

    public String clientSecret() {
        return this.clientSecret;
    }
}
