package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.account.IAccount;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import lombok.NonNull;

public class SilentParameters implements IAcquireTokenParameters {
    @NonNull
    private Set<String> scopes;
    private IAccount account;
    private ClaimsRequest claims;
    private String authorityUrl;
    private boolean forceRefresh;
    private Map<String, String> extraHttpHeaders;
    private String tenant;

    private static SilentParameters.SilentParametersBuilder builder() {
        return new SilentParameters.SilentParametersBuilder();
    }

    public static SilentParameters.SilentParametersBuilder builder(Set<String> scopes, IAccount account) {
        ParameterValidationUtils.validateNotNull("account", account);
        ParameterValidationUtils.validateNotNull("scopes", scopes);
        return builder().scopes(removeEmptyScope(scopes)).account(account);
    }

    /** @deprecated */
    @Deprecated
    public static SilentParameters.SilentParametersBuilder builder(Set<String> scopes) {
        ParameterValidationUtils.validateNotNull("scopes", scopes);
        return builder().scopes(removeEmptyScope(scopes));
    }

    private static Set<String> removeEmptyScope(Set<String> scopes) {
        Set<String> updatedScopes = new HashSet();
        Iterator var2 = scopes.iterator();

        while(var2.hasNext()) {
            String scope = (String)var2.next();
            if (!scope.equalsIgnoreCase(StringHelper.EMPTY_STRING)) {
                updatedScopes.add(scope.trim());
            }
        }

        return updatedScopes;
    }

    @NonNull
    public Set<String> scopes() {
        return this.scopes;
    }

    public IAccount account() {
        return this.account;
    }

    public ClaimsRequest claims() {
        return this.claims;
    }

    public String authorityUrl() {
        return this.authorityUrl;
    }

    public boolean forceRefresh() {
        return this.forceRefresh;
    }

    public Map<String, String> extraHttpHeaders() {
        return this.extraHttpHeaders;
    }

    public String tenant() {
        return this.tenant;
    }

    private SilentParameters(@NonNull Set<String> scopes, IAccount account, ClaimsRequest claims, String authorityUrl, boolean forceRefresh, Map<String, String> extraHttpHeaders, String tenant) {
        if (scopes == null) {
            throw new NullPointerException("scopes is marked non-null but is null");
        } else {
            this.scopes = scopes;
            this.account = account;
            this.claims = claims;
            this.authorityUrl = authorityUrl;
            this.forceRefresh = forceRefresh;
            this.extraHttpHeaders = extraHttpHeaders;
            this.tenant = tenant;
        }
    }

    public static class SilentParametersBuilder {
        private Set<String> scopes;
        private IAccount account;
        private ClaimsRequest claims;
        private String authorityUrl;
        private boolean forceRefresh;
        private Map<String, String> extraHttpHeaders;
        private String tenant;

        SilentParametersBuilder() {
        }

        public SilentParameters.SilentParametersBuilder scopes(@NonNull Set<String> scopes) {
            if (scopes == null) {
                throw new NullPointerException("scopes is marked non-null but is null");
            } else {
                this.scopes = scopes;
                return this;
            }
        }

        public SilentParameters.SilentParametersBuilder account(IAccount account) {
            this.account = account;
            return this;
        }

        public SilentParameters.SilentParametersBuilder claims(ClaimsRequest claims) {
            this.claims = claims;
            return this;
        }

        public SilentParameters.SilentParametersBuilder authorityUrl(String authorityUrl) {
            this.authorityUrl = authorityUrl;
            return this;
        }

        public SilentParameters.SilentParametersBuilder forceRefresh(boolean forceRefresh) {
            this.forceRefresh = forceRefresh;
            return this;
        }

        public SilentParameters.SilentParametersBuilder extraHttpHeaders(Map<String, String> extraHttpHeaders) {
            this.extraHttpHeaders = extraHttpHeaders;
            return this;
        }

        public SilentParameters.SilentParametersBuilder tenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public SilentParameters build() {
            return new SilentParameters(this.scopes, this.account, this.claims, this.authorityUrl, this.forceRefresh, this.extraHttpHeaders, this.tenant);
        }

        public String toString() {
            return "SilentParameters.SilentParametersBuilder(scopes=" + this.scopes + ", account=" + this.account + ", claims=" + this.claims + ", authorityUrl=" + this.authorityUrl + ", forceRefresh=" + this.forceRefresh + ", extraHttpHeaders=" + this.extraHttpHeaders + ", tenant=" + this.tenant + ")";
        }
    }
}
