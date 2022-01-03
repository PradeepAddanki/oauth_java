package com.microsoft.aad.msal4j;

import com.nimbusds.oauth2.sdk.RefreshTokenGrant;
import com.nimbusds.oauth2.sdk.token.RefreshToken;

import java.util.Set;
import java.util.TreeSet;

import static com.microsoft.aad.msal4j.Constants.POINT_DELIMITER;

class RefreshTokenRequest extends MsalRequest {

    private SilentRequest parentSilentRequest;
    private RefreshTokenParameters parameters;

    RefreshTokenRequest(RefreshTokenParameters parameters,
                        AbstractClientApplicationBase application,
                        RequestContext requestContext) {
        super(application, createAuthenticationGrant(parameters), requestContext);
        this.parameters = parameters;
    }

    RefreshTokenRequest(RefreshTokenParameters parameters,
                        AbstractClientApplicationBase application,
                        RequestContext requestContext,
                        SilentRequest silentRequest) {
        this(parameters, application, requestContext);
        this.parentSilentRequest = silentRequest;
    }

    private static AbstractMsalAuthorizationGrant createAuthenticationGrant(
            RefreshTokenParameters parameters) {

        RefreshTokenGrant refreshTokenGrant = new RefreshTokenGrant(new RefreshToken(parameters.refreshToken()));
        return new OAuthAuthorizationGrant(refreshTokenGrant, parameters.scopes(), parameters.claims());
    }

    String getFullThumbprint() {
        StringBuilder sb = new StringBuilder();

        sb.append(application().clientId() + POINT_DELIMITER);

        String authority = (parentSilentRequest != null && parentSilentRequest.getRequestAuthority() != null)
                ? parentSilentRequest.getRequestAuthority().authority() : application().authority();
        sb.append(authority + POINT_DELIMITER);

        if (parentSilentRequest != null && parentSilentRequest.getParameters().account() != null) {
            sb.append(parentSilentRequest.getParameters().account().homeAccountId() + POINT_DELIMITER);
        }

        sb.append(parameters.refreshToken() + POINT_DELIMITER);

        Set<String> sortedScopes = new TreeSet<>(parameters.scopes());
        sb.append(String.join(" ", sortedScopes) + POINT_DELIMITER);

        return StringHelper.createSha256Hash(sb.toString());
    }
}
