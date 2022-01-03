package com.microsoft.aad.msal4j;

import java.net.URI;
import java.util.Map;
import java.util.Set;

public class InteractiveRequestParameters implements IAcquireTokenParameters {
    private URI redirectUri;
    private ClaimsRequest claims;
    private Set<String> scopes;
    private Prompt prompt;
    private String loginHint;
    private String domainHint;
    private SystemBrowserOptions systemBrowserOptions;
    private String claimsChallenge;
    private Map<String, String> extraHttpHeaders;
    private String tenant;
    private boolean instanceAware;

    private static InteractiveRequestParameters.InteractiveRequestParametersBuilder builder() {
        return new InteractiveRequestParameters.InteractiveRequestParametersBuilder();
    }

    public static InteractiveRequestParameters.InteractiveRequestParametersBuilder builder(URI redirectUri) {
        ParameterValidationUtils.validateNotNull("redirect_uri", redirectUri);
        return builder().redirectUri(redirectUri);
    }

    public URI redirectUri() {
        return this.redirectUri;
    }

    public ClaimsRequest claims() {
        return this.claims;
    }

    public Set<String> scopes() {
        return this.scopes;
    }

    public Prompt prompt() {
        return this.prompt;
    }

    public String loginHint() {
        return this.loginHint;
    }

    public String domainHint() {
        return this.domainHint;
    }

    public SystemBrowserOptions systemBrowserOptions() {
        return this.systemBrowserOptions;
    }

    public String claimsChallenge() {
        return this.claimsChallenge;
    }

    public Map<String, String> extraHttpHeaders() {
        return this.extraHttpHeaders;
    }

    public String tenant() {
        return this.tenant;
    }

    public boolean instanceAware() {
        return this.instanceAware;
    }

    private InteractiveRequestParameters(URI redirectUri, ClaimsRequest claims, Set<String> scopes, Prompt prompt, String loginHint, String domainHint, SystemBrowserOptions systemBrowserOptions, String claimsChallenge, Map<String, String> extraHttpHeaders, String tenant, boolean instanceAware) {
        if (redirectUri == null) {
            throw new NullPointerException("redirectUri is marked non-null but is null");
        } else {
            this.redirectUri = redirectUri;
            this.claims = claims;
            this.scopes = scopes;
            this.prompt = prompt;
            this.loginHint = loginHint;
            this.domainHint = domainHint;
            this.systemBrowserOptions = systemBrowserOptions;
            this.claimsChallenge = claimsChallenge;
            this.extraHttpHeaders = extraHttpHeaders;
            this.tenant = tenant;
            this.instanceAware = instanceAware;
        }
    }

    InteractiveRequestParameters redirectUri(URI redirectUri) {
        if (redirectUri == null) {
            throw new NullPointerException("redirectUri is marked non-null but is null");
        } else {
            this.redirectUri = redirectUri;
            return this;
        }
    }

    public static class InteractiveRequestParametersBuilder {
        private URI redirectUri;
        private ClaimsRequest claims;
        private Set<String> scopes;
        private Prompt prompt;
        private String loginHint;
        private String domainHint;
        private SystemBrowserOptions systemBrowserOptions;
        private String claimsChallenge;
        private Map<String, String> extraHttpHeaders;
        private String tenant;
        private boolean instanceAware;

        InteractiveRequestParametersBuilder() {
        }

        public InteractiveRequestParameters.InteractiveRequestParametersBuilder redirectUri(URI redirectUri) {
            if (redirectUri == null) {
                throw new NullPointerException("redirectUri is marked non-null but is null");
            } else {
                this.redirectUri = redirectUri;
                return this;
            }
        }

        public InteractiveRequestParameters.InteractiveRequestParametersBuilder claims(ClaimsRequest claims) {
            this.claims = claims;
            return this;
        }

        public InteractiveRequestParameters.InteractiveRequestParametersBuilder scopes(Set<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public InteractiveRequestParameters.InteractiveRequestParametersBuilder prompt(Prompt prompt) {
            this.prompt = prompt;
            return this;
        }

        public InteractiveRequestParameters.InteractiveRequestParametersBuilder loginHint(String loginHint) {
            this.loginHint = loginHint;
            return this;
        }

        public InteractiveRequestParameters.InteractiveRequestParametersBuilder domainHint(String domainHint) {
            this.domainHint = domainHint;
            return this;
        }

        public InteractiveRequestParameters.InteractiveRequestParametersBuilder systemBrowserOptions(SystemBrowserOptions systemBrowserOptions) {
            this.systemBrowserOptions = systemBrowserOptions;
            return this;
        }

        public InteractiveRequestParameters.InteractiveRequestParametersBuilder claimsChallenge(String claimsChallenge) {
            this.claimsChallenge = claimsChallenge;
            return this;
        }

        public InteractiveRequestParameters.InteractiveRequestParametersBuilder extraHttpHeaders(Map<String, String> extraHttpHeaders) {
            this.extraHttpHeaders = extraHttpHeaders;
            return this;
        }

        public InteractiveRequestParameters.InteractiveRequestParametersBuilder tenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public InteractiveRequestParameters.InteractiveRequestParametersBuilder instanceAware(boolean instanceAware) {
            this.instanceAware = instanceAware;
            return this;
        }

        public InteractiveRequestParameters build() {
            return new InteractiveRequestParameters(this.redirectUri, this.claims, this.scopes, this.prompt, this.loginHint, this.domainHint, this.systemBrowserOptions, this.claimsChallenge, this.extraHttpHeaders, this.tenant, this.instanceAware);
        }

        public String toString() {
            return "InteractiveRequestParameters.InteractiveRequestParametersBuilder(redirectUri=" + this.redirectUri + ", claims=" + this.claims + ", scopes=" + this.scopes + ", prompt=" + this.prompt + ", loginHint=" + this.loginHint + ", domainHint=" + this.domainHint + ", systemBrowserOptions=" + this.systemBrowserOptions + ", claimsChallenge=" + this.claimsChallenge + ", extraHttpHeaders=" + this.extraHttpHeaders + ", tenant=" + this.tenant + ", instanceAware=" + this.instanceAware + ")";
        }
    }
}
