package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.client.IClientCredential;

/**
 * Credential type containing an assertion of type
 * "urn:ietf:params:oauth:token-type:jwt".
 * <p>
 * For more details, see https://aka.ms/msal4j-client-credentials
 */
public interface IClientAssertion extends IClientCredential {

    /**
     * @return Jwt token encoded as a base64 URL encoded string
     */
    String assertion();
}
