package com.microsoft.aad.msal4j;


import java.util.Map;
import java.util.Set;

import static com.microsoft.aad.msal4j.ParameterValidationUtils.validateNotNull;

public class ClientCredentialParameters implements IAcquireTokenParameters {

    /**
     * Scopes for which the application is requesting access to.
     */
    private Set<String> scopes;

    /**
     * Indicates whether the request should skip looking into the token cache. Be default it is
     * set to false.
     */
    private Boolean skipCache = false;

    /**
     * Claims to be requested through the OIDC claims request parameter, allowing requests for standard and custom claims
     */
    private ClaimsRequest claims;

    /**
     * Adds additional headers to the token request
     */
    private Map<String, String> extraHttpHeaders;

    /**
     * Overrides the tenant value in the authority URL for this request
     */
    private String tenant;
    private ClientCredentialParameters(Set<String> scopes, Boolean skipCache, ClaimsRequest claims, Map<String, String> extraHttpHeaders, String tenant) {
        if (scopes == null) {
            throw new NullPointerException("scopes is marked non-null but is null");
        } else {
            this.scopes = scopes;
            this.skipCache = skipCache;
            this.claims = claims;
            this.extraHttpHeaders = extraHttpHeaders;
            this.tenant = tenant;
        }
    }

    private static ClientCredentialParametersBuilder builder() {

        return new ClientCredentialParametersBuilder();
    }

    public static ClientCredentialParametersBuilder builder(Set<String> scopes) {

        validateNotNull("scopes", scopes);

        return builder().scopes(scopes);
    }

    public Set<String> scopes() {
        return this.scopes;
    }

    public Boolean skipCache() {
        return this.skipCache;
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

    public static class ClientCredentialParametersBuilder {
        private Set<String> scopes;
        private boolean skipCache$set;
        private Boolean skipCache$value;
        private ClaimsRequest claims;
        private Map<String, String> extraHttpHeaders;
        private String tenant;

        ClientCredentialParametersBuilder() {
        }

        public ClientCredentialParameters.ClientCredentialParametersBuilder scopes(Set<String> scopes) {
            if (scopes == null) {
                throw new NullPointerException("scopes is marked non-null but is null");
            } else {
                this.scopes = scopes;
                return this;
            }
        }

        public ClientCredentialParameters.ClientCredentialParametersBuilder skipCache(Boolean skipCache) {
            this.skipCache$value = skipCache;
            this.skipCache$set = true;
            return this;
        }

        public ClientCredentialParameters.ClientCredentialParametersBuilder claims(ClaimsRequest claims) {
            this.claims = claims;
            return this;
        }

        public ClientCredentialParameters.ClientCredentialParametersBuilder extraHttpHeaders(Map<String, String> extraHttpHeaders) {
            this.extraHttpHeaders = extraHttpHeaders;
            return this;
        }

        public ClientCredentialParameters.ClientCredentialParametersBuilder tenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public ClientCredentialParameters build() {
            Boolean skipCache$value = this.skipCache$value;
            if (!this.skipCache$set) {
                skipCache$value = ClientCredentialParameters.defaultSkipCache();
            }

            return new ClientCredentialParameters(this.scopes, skipCache$value, this.claims, this.extraHttpHeaders, this.tenant);
        }

        public String toString() {
            return "ClientCredentialParameters.ClientCredentialParametersBuilder(scopes=" + this.scopes + ", skipCache$value=" + this.skipCache$value + ", claims=" + this.claims + ", extraHttpHeaders=" + this.extraHttpHeaders + ", tenant=" + this.tenant + ")";
        }
    }
    private static Boolean defaultSkipCache() {
        return false;
    }
}
