package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.client.IClientSecret;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import static com.microsoft.aad.msal4j.ParameterValidationUtils.validateNotNull;


public class ClientCredentialFactory {

    /**
     * Static method to create a {@link ClientSecret} instance from a client secret
     *
     * @param secret secret of application requesting a token
     * @return {@link ClientSecret}
     */
    public static IClientSecret createFromSecret(String secret) {
        return new ClientSecret(secret);
    }


    public static IClientCertificate createFromCertificate(final InputStream pkcs12Certificate, final String password)
            throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, NoSuchProviderException, IOException {
        return ClientCertificate.create(pkcs12Certificate, password);
    }

    public static IClientCertificate createFromCertificate(final PrivateKey key, final X509Certificate publicKeyCertificate) {
        validateNotNull("publicKeyCertificate", publicKeyCertificate);

        return ClientCertificate.create(key, publicKeyCertificate);
    }

    /**
     * Static method to create a {@link ClientCertificate} instance.
     *
     * @param key                       RSA private key to sign the assertion.
     * @param publicKeyCertificateChain ordered with the user's certificate first followed by zero or more certificate authorities
     * @return {@link ClientCertificate}
     */
    public static IClientCertificate createFromCertificateChain(PrivateKey key, List<X509Certificate> publicKeyCertificateChain) {
        if (key == null || publicKeyCertificateChain == null || publicKeyCertificateChain.size() == 0) {
            throw new IllegalArgumentException("null or empty input parameter");
        }
        return new ClientCertificate(key, publicKeyCertificateChain);
    }

    /**
     * Static method to create a {@link ClientAssertion} instance.
     *
     * @param clientAssertion Jwt token encoded as a base64 URL encoded string
     * @return {@link ClientAssertion}
     */
    public static IClientAssertion createFromClientAssertion(String clientAssertion) {
        return new ClientAssertion(clientAssertion);
    }
}
