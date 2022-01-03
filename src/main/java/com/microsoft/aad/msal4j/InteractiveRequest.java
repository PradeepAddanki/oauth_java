package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.Exception.VasaraCloudException;
import com.microsoft.aad.msal4j.result.IAuthenticationResult;

import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;


class InteractiveRequest extends MsalRequest {

    private AtomicReference<CompletableFuture<IAuthenticationResult>> futureReference;

    private InteractiveRequestParameters interactiveRequestParameters;

    private String verifier;

    private String state;

    private PublicClientApplication publicClientApplication;
    private URL authorizationUrl;

    InteractiveRequest(InteractiveRequestParameters parameters,
                       AtomicReference<CompletableFuture<IAuthenticationResult>> futureReference,
                       PublicClientApplication publicClientApplication,
                       RequestContext requestContext) {

        super(publicClientApplication, null, requestContext);

        this.interactiveRequestParameters = parameters;
        this.futureReference = futureReference;
        this.publicClientApplication = publicClientApplication;
        validateRedirectUrl(parameters.redirectUri());
    }

    URL authorizationUrl() {
        if (this.authorizationUrl == null) {
            authorizationUrl = createAuthorizationUrl();
        }
        return authorizationUrl;
    }

    private void validateRedirectUrl(URI redirectUri) {
        try {
            if (!InetAddress.getByName(redirectUri.getHost()).isLoopbackAddress()) {
                throw new VasaraCloudException(String.format(
                        "Only loopback redirect uri is supported, but %s was found " +
                                "Configure http://localhost or http://localhost:port both during app registration" +
                                "and when you create the create the InteractiveRequestParameters object", redirectUri.getHost()),
                        AuthenticationErrorCode.LOOPBACK_REDIRECT_URI);
            }

            if (!redirectUri.getScheme().equals("http")) {
                throw new VasaraCloudException(String.format(
                        "Only http uri scheme is supported but %s was found. Configure http://localhost" +
                                "or http://localhost:port both during app registration and when you create" +
                                " the create the InteractiveRequestParameters object", redirectUri.toString()),
                        AuthenticationErrorCode.LOOPBACK_REDIRECT_URI);
            }
        } catch (Exception exception) {
            throw new VasaraCloudException(exception);
        }
    }

    private URL createAuthorizationUrl() {

        AuthorizationRequestUrlParameters.Builder authorizationRequestUrlBuilder =
                AuthorizationRequestUrlParameters
                        .builder(interactiveRequestParameters.redirectUri().toString(),
                                interactiveRequestParameters.scopes())
                        .prompt(interactiveRequestParameters.prompt())
                        .claimsChallenge(interactiveRequestParameters.claimsChallenge())
                        .loginHint(interactiveRequestParameters.loginHint())
                        .domainHint(interactiveRequestParameters.domainHint())
                        .correlationId(publicClientApplication.correlationId())
                        .instanceAware(interactiveRequestParameters.instanceAware());

        addPkceAndState(authorizationRequestUrlBuilder);
        return publicClientApplication.getAuthorizationRequestUrl(
                authorizationRequestUrlBuilder.build());
    }

    private void addPkceAndState(AuthorizationRequestUrlParameters.Builder builder) {

        // Create code verifier and code challenge as described in https://tools.ietf.org/html/rfc7636
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        state = UUID.randomUUID().toString() + UUID.randomUUID().toString();

        builder.codeChallenge(StringHelper.createBase64EncodedSha256Hash(verifier))
                .codeChallengeMethod("S256")
                .state(state);
    }

    public AtomicReference<CompletableFuture<IAuthenticationResult>> getFutureReference() {
        return futureReference;
    }

    public void setFutureReference(AtomicReference<CompletableFuture<IAuthenticationResult>> futureReference) {
        this.futureReference = futureReference;
    }

    public InteractiveRequestParameters getInteractiveRequestParameters() {
        return interactiveRequestParameters;
    }

    public void setInteractiveRequestParameters(InteractiveRequestParameters interactiveRequestParameters) {
        this.interactiveRequestParameters = interactiveRequestParameters;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public PublicClientApplication getPublicClientApplication() {
        return publicClientApplication;
    }

    public void setPublicClientApplication(PublicClientApplication publicClientApplication) {
        this.publicClientApplication = publicClientApplication;
    }

    public URL getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(URL authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }
}
