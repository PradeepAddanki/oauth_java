package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.Exception.VasaraCloudException;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.JWTBearerGrant;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.microsoft.aad.msal4j.AbstractMsalAuthorizationGrant.SCOPES_DELIMITER;


class OnBehalfOfRequest extends MsalRequest {

    OnBehalfOfParameters parameters;

    OnBehalfOfRequest(OnBehalfOfParameters parameters,
                      ConfidentialClientApplication application,
                      RequestContext requestContext) {
        super(application, createAuthenticationGrant(parameters), requestContext);
        this.parameters = parameters;
    }

    private static OAuthAuthorizationGrant createAuthenticationGrant(OnBehalfOfParameters parameters) {

        AuthorizationGrant jWTBearerGrant;
        try {
            jWTBearerGrant = new JWTBearerGrant(SignedJWT.parse(parameters.userAssertion().getAssertion()));
        } catch (Exception e) {
            throw new VasaraCloudException(e);
        }

        Map<String, List<String>> params = new HashMap<>();
        params.put("requested_token_use", Collections.singletonList("on_behalf_of"));
        if (parameters.claims() != null) {
            params.put("claims", Collections.singletonList(parameters.claims().formatAsJSONString()));
        }

        return new OAuthAuthorizationGrant(jWTBearerGrant, String.join(SCOPES_DELIMITER, parameters.scopes()), params);
    }
}
