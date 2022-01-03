package com.microsoft.aad.msal4j;

import java.util.Map;
import java.util.Set;


public class OnBehalfOfParameters implements IAcquireTokenParameters {
    private Set<String> scopes;
    private Boolean skipCache;
    private IUserAssertion userAssertion;
    private ClaimsRequest claims;
    private Map<String, String> extraHttpHeaders;
    private String tenant;

    private static OnBehalfOfParameters.OnBehalfOfParametersBuilder builder() {
        return new OnBehalfOfParameters.OnBehalfOfParametersBuilder();
    }

    public static OnBehalfOfParameters.OnBehalfOfParametersBuilder builder(Set<String> scopes, UserAssertion userAssertion) {
        ParameterValidationUtils.validateNotNull("scopes", scopes);
        return builder().scopes(scopes).userAssertion(userAssertion);
    }

    private static Boolean $default$skipCache() {
        return false;
    }

     public Set<String> scopes() {
        return this.scopes;
    }

    public Boolean skipCache() {
        return this.skipCache;
    }
     public IUserAssertion userAssertion() {
        return this.userAssertion;
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
    private OnBehalfOfParameters( Set<String> scopes, Boolean skipCache, IUserAssertion userAssertion, ClaimsRequest claims, Map<String, String> extraHttpHeaders, String tenant) {
        if (scopes == null) {
            throw new NullPointerException("scopes is marked non-null but is null");
        } else if (userAssertion == null) {
            throw new NullPointerException("userAssertion is marked non-null but is null");
        } else {
            this.scopes = scopes;
            this.skipCache = skipCache;
            this.userAssertion = userAssertion;
            this.claims = claims;
            this.extraHttpHeaders = extraHttpHeaders;
            this.tenant = tenant;
        }
    }

    public static class OnBehalfOfParametersBuilder {
        private Set<String> scopes;
        private boolean skipCache$set;
        private Boolean skipCache$value;
        private IUserAssertion userAssertion;
        private ClaimsRequest claims;
        private Map<String, String> extraHttpHeaders;
        private String tenant;

        OnBehalfOfParametersBuilder() {
        }

        public OnBehalfOfParameters.OnBehalfOfParametersBuilder scopes(Set<String> scopes) {
            if (scopes == null) {
                throw new NullPointerException("scopes is marked non-null but is null");
            } else {
                this.scopes = scopes;
                return this;
            }
        }

        public OnBehalfOfParameters.OnBehalfOfParametersBuilder skipCache(Boolean skipCache) {
            this.skipCache$value = skipCache;
            this.skipCache$set = true;
            return this;
        }

        public OnBehalfOfParameters.OnBehalfOfParametersBuilder userAssertion(IUserAssertion userAssertion) {
            if (userAssertion == null) {
                throw new NullPointerException("userAssertion is marked non-null but is null");
            } else {
                this.userAssertion = userAssertion;
                return this;
            }
        }

        public OnBehalfOfParameters.OnBehalfOfParametersBuilder claims(ClaimsRequest claims) {
            this.claims = claims;
            return this;
        }

        public OnBehalfOfParameters.OnBehalfOfParametersBuilder extraHttpHeaders(Map<String, String> extraHttpHeaders) {
            this.extraHttpHeaders = extraHttpHeaders;
            return this;
        }

        public OnBehalfOfParameters.OnBehalfOfParametersBuilder tenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public OnBehalfOfParameters build() {
            Boolean skipCache$value = this.skipCache$value;
            if (!this.skipCache$set) {
                skipCache$value = OnBehalfOfParameters.$default$skipCache();
            }

            return new OnBehalfOfParameters(this.scopes, skipCache$value, this.userAssertion, this.claims, this.extraHttpHeaders, this.tenant);
        }

        public String toString() {
            return "OnBehalfOfParameters.OnBehalfOfParametersBuilder(scopes=" + this.scopes + ", skipCache$value=" + this.skipCache$value + ", userAssertion=" + this.userAssertion + ", claims=" + this.claims + ", extraHttpHeaders=" + this.extraHttpHeaders + ", tenant=" + this.tenant + ")";
        }
    }
}
