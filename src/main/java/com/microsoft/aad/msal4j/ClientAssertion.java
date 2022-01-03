package com.microsoft.aad.msal4j;

import com.nimbusds.oauth2.sdk.auth.JWTAuthentication;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

public final class ClientAssertion implements IClientAssertion {

    static final String assertionType = JWTAuthentication.CLIENT_ASSERTION_TYPE;
    private final String assertion;

    public ClientAssertion(final String assertion) {
        if (StringHelper.isBlank(assertion)) {
            throw new NullPointerException("assertion");
        }

        this.assertion = assertion;
    }

    public String assertion() {
        return this.assertion;
    }
}