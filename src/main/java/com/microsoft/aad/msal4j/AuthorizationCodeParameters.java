package com.microsoft.aad.msal4j;

import java.net.URI;
import java.util.Map;
import java.util.Set;


public class AuthorizationCodeParameters implements IAcquireTokenParameters {
    private String authorizationCode;
    private URI redirectUri;
    private Set<String> scopes;
    private ClaimsRequest claims;
    private String codeVerifier;
    private Map<String, String> extraHttpHeaders;
    private String tenant;

    private static AuthorizationCodeParameters.AuthorizationCodeParametersBuilder builder() {
        return new AuthorizationCodeParameters.AuthorizationCodeParametersBuilder();
    }

    public static AuthorizationCodeParameters.AuthorizationCodeParametersBuilder builder(String authorizationCode, URI redirectUri) {
        ParameterValidationUtils.validateNotBlank("authorizationCode", authorizationCode);
        return builder().authorizationCode(authorizationCode).redirectUri(redirectUri);
    }

    public String authorizationCode() {
        return this.authorizationCode;
    }

    public URI redirectUri() {
        return this.redirectUri;
    }

    public Set<String> scopes() {
        return this.scopes;
    }

    public ClaimsRequest claims() {
        return this.claims;
    }

    public String codeVerifier() {
        return this.codeVerifier;
    }

    public Map<String, String> extraHttpHeaders() {
        return this.extraHttpHeaders;
    }

    public String tenant() {
        return this.tenant;
    }

    private AuthorizationCodeParameters(String authorizationCode, URI redirectUri, Set<String> scopes, ClaimsRequest claims, String codeVerifier, Map<String, String> extraHttpHeaders, String tenant) {
        if (authorizationCode == null) {
            throw new NullPointerException("authorizationCode is marked non-null but is null");
        } else if (redirectUri == null) {
            throw new NullPointerException("redirectUri is marked non-null but is null");
        } else {
            this.authorizationCode = authorizationCode;
            this.redirectUri = redirectUri;
            this.scopes = scopes;
            this.claims = claims;
            this.codeVerifier = codeVerifier;
            this.extraHttpHeaders = extraHttpHeaders;
            this.tenant = tenant;
        }
    }

    public static class AuthorizationCodeParametersBuilder {
        private String authorizationCode;
        private URI redirectUri;
        private Set<String> scopes;
        private ClaimsRequest claims;
        private String codeVerifier;
        private Map<String, String> extraHttpHeaders;
        private String tenant;

        AuthorizationCodeParametersBuilder() {
        }

        public AuthorizationCodeParameters.AuthorizationCodeParametersBuilder authorizationCode(String authorizationCode) {
            if (authorizationCode == null) {
                throw new NullPointerException("authorizationCode is marked non-null but is null");
            } else {
                this.authorizationCode = authorizationCode;
                return this;
            }
        }

        public AuthorizationCodeParameters.AuthorizationCodeParametersBuilder redirectUri(URI redirectUri) {
            if (redirectUri == null) {
                throw new NullPointerException("redirectUri is marked non-null but is null");
            } else {
                this.redirectUri = redirectUri;
                return this;
            }
        }

        public AuthorizationCodeParameters.AuthorizationCodeParametersBuilder scopes(Set<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public AuthorizationCodeParameters.AuthorizationCodeParametersBuilder claims(ClaimsRequest claims) {
            this.claims = claims;
            return this;
        }

        public AuthorizationCodeParameters.AuthorizationCodeParametersBuilder codeVerifier(String codeVerifier) {
            this.codeVerifier = codeVerifier;
            return this;
        }

        public AuthorizationCodeParameters.AuthorizationCodeParametersBuilder extraHttpHeaders(Map<String, String> extraHttpHeaders) {
            this.extraHttpHeaders = extraHttpHeaders;
            return this;
        }

        public AuthorizationCodeParameters.AuthorizationCodeParametersBuilder tenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public AuthorizationCodeParameters build() {
            return new AuthorizationCodeParameters(this.authorizationCode, this.redirectUri, this.scopes, this.claims, this.codeVerifier, this.extraHttpHeaders, this.tenant);
        }

        public String toString() {
            return "AuthorizationCodeParameters.AuthorizationCodeParametersBuilder(authorizationCode=" + this.authorizationCode + ", redirectUri=" + this.redirectUri + ", scopes=" + this.scopes + ", claims=" + this.claims + ", codeVerifier=" + this.codeVerifier + ", extraHttpHeaders=" + this.extraHttpHeaders + ", tenant=" + this.tenant + ")";
        }
    }
}
