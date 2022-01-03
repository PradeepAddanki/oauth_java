package com.microsoft.aad.msal4j;

import java.util.Map;
import java.util.Set;
public class RefreshTokenParameters implements IAcquireTokenParameters {
    private Set<String> scopes;
    private String refreshToken;
    private ClaimsRequest claims;
    private Map<String, String> extraHttpHeaders;
    private String tenant;

    private static RefreshTokenParameters.RefreshTokenParametersBuilder builder() {
        return new RefreshTokenParameters.RefreshTokenParametersBuilder();
    }

    public static RefreshTokenParameters.RefreshTokenParametersBuilder builder(Set<String> scopes, String refreshToken) {
        ParameterValidationUtils.validateNotBlank("refreshToken", refreshToken);
        return builder().scopes(scopes).refreshToken(refreshToken);
    }


    public Set<String> scopes() {
        return this.scopes;
    }


    public String refreshToken() {
        return this.refreshToken;
    }

    public ClaimsRequest claims() {
        return this.claims;
    }

    public Map<String, String> extraHttpHeaders() {
        return this.extraHttpHeaders;
    }

    public String tenant() {
        return this.tenant;
    }

    private RefreshTokenParameters( Set<String> scopes,  String refreshToken, ClaimsRequest claims, Map<String, String> extraHttpHeaders, String tenant) {
        if (scopes == null) {
            throw new NullPointerException("scopes is marked non-null but is null");
        } else if (refreshToken == null) {
            throw new NullPointerException("refreshToken is marked non-null but is null");
        } else {
            this.scopes = scopes;
            this.refreshToken = refreshToken;
            this.claims = claims;
            this.extraHttpHeaders = extraHttpHeaders;
            this.tenant = tenant;
        }
    }

    public static class RefreshTokenParametersBuilder {
        private Set<String> scopes;
        private String refreshToken;
        private ClaimsRequest claims;
        private Map<String, String> extraHttpHeaders;
        private String tenant;

        RefreshTokenParametersBuilder() {
        }

        public RefreshTokenParameters.RefreshTokenParametersBuilder scopes( Set<String> scopes) {
            if (scopes == null) {
                throw new NullPointerException("scopes is marked non-null but is null");
            } else {
                this.scopes = scopes;
                return this;
            }
        }

        public RefreshTokenParameters.RefreshTokenParametersBuilder refreshToken( String refreshToken) {
            if (refreshToken == null) {
                throw new NullPointerException("refreshToken is marked non-null but is null");
            } else {
                this.refreshToken = refreshToken;
                return this;
            }
        }

        public RefreshTokenParameters.RefreshTokenParametersBuilder claims(ClaimsRequest claims) {
            this.claims = claims;
            return this;
        }

        public RefreshTokenParameters.RefreshTokenParametersBuilder extraHttpHeaders(Map<String, String> extraHttpHeaders) {
            this.extraHttpHeaders = extraHttpHeaders;
            return this;
        }

        public RefreshTokenParameters.RefreshTokenParametersBuilder tenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public RefreshTokenParameters build() {
            return new RefreshTokenParameters(this.scopes, this.refreshToken, this.claims, this.extraHttpHeaders, this.tenant);
        }

        public String toString() {
            return "RefreshTokenParameters.RefreshTokenParametersBuilder(scopes=" + this.scopes + ", refreshToken=" + this.refreshToken + ", claims=" + this.claims + ", extraHttpHeaders=" + this.extraHttpHeaders + ", tenant=" + this.tenant + ")";
        }
    }
}
