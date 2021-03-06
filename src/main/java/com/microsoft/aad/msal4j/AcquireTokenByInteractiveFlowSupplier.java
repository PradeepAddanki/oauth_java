package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.Exception.VasaraCloudException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class AcquireTokenByInteractiveFlowSupplier extends AuthenticationResultSupplier {

    private final static Logger LOG = LoggerFactory.getLogger(AcquireTokenByAuthorizationGrantSupplier.class);

    private PublicClientApplication clientApplication;
    private InteractiveRequest interactiveRequest;

    private BlockingQueue<AuthorizationResult> authorizationResultQueue;
    private HttpListener httpListener;

    AcquireTokenByInteractiveFlowSupplier(PublicClientApplication clientApplication,
                                          InteractiveRequest request) {
        super(clientApplication, request);
        this.clientApplication = clientApplication;
        this.interactiveRequest = request;
    }

    @Override
    AuthenticationResult execute() throws Exception {
        AuthorizationResult authorizationResult = getAuthorizationResult();
        validateState(authorizationResult);
        return acquireTokenWithAuthorizationCode(authorizationResult);
    }

    private AuthorizationResult getAuthorizationResult() {

        AuthorizationResult result;
        try {
            SystemBrowserOptions systemBrowserOptions =
                    interactiveRequest.getInteractiveRequestParameters().systemBrowserOptions();

            authorizationResultQueue = new LinkedBlockingQueue<>();
            AuthorizationResponseHandler authorizationResponseHandler =
                    new AuthorizationResponseHandler(
                            authorizationResultQueue,
                            systemBrowserOptions);

            startHttpListener(authorizationResponseHandler);

            if (systemBrowserOptions != null && systemBrowserOptions.openBrowserAction() != null) {
                interactiveRequest.getInteractiveRequestParameters().systemBrowserOptions().openBrowserAction()
                        .openBrowser(interactiveRequest.authorizationUrl());
            } else {
                openDefaultSystemBrowser(interactiveRequest.authorizationUrl());
            }

            result = getAuthorizationResultFromHttpListener();
        } finally {
            if (httpListener != null) {
                httpListener.stopListener();
            }
        }
        return result;
    }

    private void validateState(AuthorizationResult authorizationResult) {
        if (StringHelper.isBlank(authorizationResult.getState()) ||
                !authorizationResult.getState().equals(interactiveRequest.getState())) {

            throw new VasaraCloudException("State returned in authorization result is blank or does " +
                    "not match state sent on outgoing request",
                    AuthenticationErrorCode.INVALID_AUTHORIZATION_RESULT);
        }
    }

    private void startHttpListener(AuthorizationResponseHandler handler) {
        // if port is unspecified, set to 0, which will cause socket to find a free port
        int port = interactiveRequest.getInteractiveRequestParameters().redirectUri().getPort() == -1 ?
                0 :
                interactiveRequest.getInteractiveRequestParameters().redirectUri().getPort();

        httpListener = new HttpListener();
        httpListener.startListener(port, handler);

        //If no port is passed, http listener finds a free one. We should update redirect URL to
        // point to this port.
        if (port != httpListener.getPort()) {
            updateRedirectUrl();
        }
    }

    private void updateRedirectUrl() {
        try {
            URI updatedRedirectUrl = new URI("http://localhost:" + httpListener.getPort());
            interactiveRequest.getInteractiveRequestParameters().redirectUri(updatedRedirectUrl);
            LOG.debug("Redirect URI updated to" + updatedRedirectUrl);
        } catch (URISyntaxException ex) {
            throw new VasaraCloudException("Error updating redirect URI. Not a valid URI format",
                    AuthenticationErrorCode.INVALID_REDIRECT_URI);
        }
    }

    private void openDefaultSystemBrowser(URL url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(url.toURI());
                LOG.debug("Opened default system browser");
            } else {
                throw new VasaraCloudException("Unable to open default system browser",
                        AuthenticationErrorCode.DESKTOP_BROWSER_NOT_SUPPORTED);
            }
        } catch (URISyntaxException | IOException ex) {
            throw new VasaraCloudException(ex);
        }
    }

    private AuthorizationResult getAuthorizationResultFromHttpListener() {
        AuthorizationResult result = null;
        try {
            LOG.debug("Listening for authorization result");
            long expirationTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + 120;

            while (result == null && !interactiveRequest.getFutureReference().get().isCancelled() &&
                    TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) < expirationTime) {

                result = authorizationResultQueue.poll(100, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            throw new VasaraCloudException(e);
        }

        if (result == null || StringHelper.isBlank(result.getCode())) {
            throw new VasaraCloudException("No Authorization code was returned from the server",
                    AuthenticationErrorCode.INVALID_AUTHORIZATION_RESULT);
        }
        return result;
    }

    private AuthenticationResult acquireTokenWithAuthorizationCode(AuthorizationResult authorizationResult)
            throws Exception {
        AuthorizationCodeParameters parameters = AuthorizationCodeParameters
                .builder(authorizationResult.getCode(), interactiveRequest.getInteractiveRequestParameters().redirectUri())
                .scopes(interactiveRequest.getInteractiveRequestParameters().scopes())
                .codeVerifier(interactiveRequest.getVerifier())
                .claims(interactiveRequest.getInteractiveRequestParameters().claims())
                .build();

        RequestContext context = new RequestContext(
                clientApplication,
                PublicApi.ACQUIRE_TOKEN_BY_AUTHORIZATION_CODE,
                parameters,
                interactiveRequest.requestContext().getUserIdentifier());

        AuthorizationCodeRequest authCodeRequest = new AuthorizationCodeRequest(
                parameters,
                clientApplication,
                context);

        Authority authority;

        //The result field of an AuthorizationResult object is only set if the response contained the 'cloud_instance_host_name' key,
        // which indicates that this token request is instance aware and should use that as the environment value
        //Otherwise, use the authority value from the client application
        if (authorizationResult.getEnvironment() != null) {
            authority = Authority.createAuthority(new URL(clientApplication.authenticationAuthority.canonicalAuthorityUrl.getProtocol(),
                    authorizationResult.getEnvironment(),
                    clientApplication.authenticationAuthority.canonicalAuthorityUrl.getFile()));
        } else {
            authority = clientApplication.authenticationAuthority;
        }

        AcquireTokenByAuthorizationGrantSupplier acquireTokenByAuthorizationGrantSupplier =
                new AcquireTokenByAuthorizationGrantSupplier(
                        clientApplication,
                        authCodeRequest,
                        authority);

        return acquireTokenByAuthorizationGrantSupplier.execute();
    }
}