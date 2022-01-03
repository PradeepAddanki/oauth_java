package com.microsoft.aad.msal4j;

import java.util.Map;
import java.util.Set;


public class IntegratedWindowsAuthenticationParameters implements IAcquireTokenParameters {
    private Set<String> scopes;
    private String username;
    private ClaimsRequest claims;
    private Map<String, String> extraHttpHeaders;
    private String tenant;

    private static IntegratedWindowsAuthenticationParameters.IntegratedWindowsAuthenticationParametersBuilder builder() {
        return new IntegratedWindowsAuthenticationParameters.IntegratedWindowsAuthenticationParametersBuilder();
    }

    public static IntegratedWindowsAuthenticationParameters.IntegratedWindowsAuthenticationParametersBuilder builder(Set<String> scopes, String username) {
        ParameterValidationUtils.validateNotNull("scopes", scopes);
        ParameterValidationUtils.validateNotBlank("username", username);
        return builder().scopes(scopes).username(username);
    }

    public Set<String> scopes() {
        return this.scopes;
    }

    public String username() {
        return this.username;
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

    private IntegratedWindowsAuthenticationParameters(Set<String> scopes, String username, ClaimsRequest claims, Map<String, String> extraHttpHeaders, String tenant) {
        if (scopes == null) {
            throw new NullPointerException("scopes is marked non-null but is null");
        } else if (username == null) {
            throw new NullPointerException("username is marked non-null but is null");
        } else {
            this.scopes = scopes;
            this.username = username;
            this.claims = claims;
            this.extraHttpHeaders = extraHttpHeaders;
            this.tenant = tenant;
        }
    }

    public static class IntegratedWindowsAuthenticationParametersBuilder {
        private Set<String> scopes;
        private String username;
        private ClaimsRequest claims;
        private Map<String, String> extraHttpHeaders;
        private String tenant;

        IntegratedWindowsAuthenticationParametersBuilder() {
        }

        public IntegratedWindowsAuthenticationParameters.IntegratedWindowsAuthenticationParametersBuilder scopes(Set<String> scopes) {
            if (scopes == null) {
                throw new NullPointerException("scopes is marked non-null but is null");
            } else {
                this.scopes = scopes;
                return this;
            }
        }

        public IntegratedWindowsAuthenticationParameters.IntegratedWindowsAuthenticationParametersBuilder username(String username) {
            if (username == null) {
                throw new NullPointerException("username is marked non-null but is null");
            } else {
                this.username = username;
                return this;
            }
        }

        public IntegratedWindowsAuthenticationParameters.IntegratedWindowsAuthenticationParametersBuilder claims(ClaimsRequest claims) {
            this.claims = claims;
            return this;
        }

        public IntegratedWindowsAuthenticationParameters.IntegratedWindowsAuthenticationParametersBuilder extraHttpHeaders(Map<String, String> extraHttpHeaders) {
            this.extraHttpHeaders = extraHttpHeaders;
            return this;
        }

        public IntegratedWindowsAuthenticationParameters.IntegratedWindowsAuthenticationParametersBuilder tenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public IntegratedWindowsAuthenticationParameters build() {
            return new IntegratedWindowsAuthenticationParameters(this.scopes, this.username, this.claims, this.extraHttpHeaders, this.tenant);
        }

        public String toString() {
            return "IntegratedWindowsAuthenticationParameters.IntegratedWindowsAuthenticationParametersBuilder(scopes=" + this.scopes + ", username=" + this.username + ", claims=" + this.claims + ", extraHttpHeaders=" + this.extraHttpHeaders + ", tenant=" + this.tenant + ")";
        }
    }
}
