package com.microsoft.aad.msal4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class UserNamePasswordParameters implements IAcquireTokenParameters {

    private Set<String> scopes;

    private String username;

    private char[] password;
    private ClaimsRequest claims;
    private Map<String, String> extraHttpHeaders;
    private String tenant;

    public char[] password() {
        return (char[])this.password.clone();
    }

    private static UserNamePasswordParameters.UserNamePasswordParametersBuilder builder() {
        return new UserNamePasswordParameters.UserNamePasswordParametersBuilder();
    }

    public static UserNamePasswordParameters.UserNamePasswordParametersBuilder builder(Set<String> scopes, String username, char[] password) {
        ParameterValidationUtils.validateNotNull("scopes", scopes);
        ParameterValidationUtils.validateNotBlank("username", username);
        ParameterValidationUtils.validateNotEmpty("password", password);
        return builder().scopes(scopes).username(username).password(password);
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

    private UserNamePasswordParameters(Set<String> scopes, String username, char[] password, ClaimsRequest claims, Map<String, String> extraHttpHeaders, String tenant) {
        if (scopes == null) {
            throw new NullPointerException("scopes is marked non-null but is null");
        } else if (username == null) {
            throw new NullPointerException("username is marked non-null but is null");
        } else if (password == null) {
            throw new NullPointerException("password is marked non-null but is null");
        } else {
            this.scopes = scopes;
            this.username = username;
            this.password = password;
            this.claims = claims;
            this.extraHttpHeaders = extraHttpHeaders;
            this.tenant = tenant;
        }
    }

    public static class UserNamePasswordParametersBuilder {
        private Set<String> scopes;
        private String username;
        private char[] password;
        private ClaimsRequest claims;
        private Map<String, String> extraHttpHeaders;
        private String tenant;

        public UserNamePasswordParameters.UserNamePasswordParametersBuilder password(char[] password) {
            this.password = (char[])password.clone();
            return this;
        }

        UserNamePasswordParametersBuilder() {
        }

        public UserNamePasswordParameters.UserNamePasswordParametersBuilder scopes(Set<String> scopes) {
            if (scopes == null) {
                throw new NullPointerException("scopes is marked non-null but is null");
            } else {
                this.scopes = scopes;
                return this;
            }
        }

        public UserNamePasswordParameters.UserNamePasswordParametersBuilder username(String username) {
            if (username == null) {
                throw new NullPointerException("username is marked non-null but is null");
            } else {
                this.username = username;
                return this;
            }
        }

        public UserNamePasswordParameters.UserNamePasswordParametersBuilder claims(ClaimsRequest claims) {
            this.claims = claims;
            return this;
        }

        public UserNamePasswordParameters.UserNamePasswordParametersBuilder extraHttpHeaders(Map<String, String> extraHttpHeaders) {
            this.extraHttpHeaders = extraHttpHeaders;
            return this;
        }

        public UserNamePasswordParameters.UserNamePasswordParametersBuilder tenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public UserNamePasswordParameters build() {
            return new UserNamePasswordParameters(this.scopes, this.username, this.password, this.claims, this.extraHttpHeaders, this.tenant);
        }

        public String toString() {
            Set var10000 = this.scopes;
            return "UserNamePasswordParameters.UserNamePasswordParametersBuilder(scopes=" + var10000 + ", username=" + this.username + ", password=" + Arrays.toString(this.password) + ", claims=" + this.claims + ", extraHttpHeaders=" + this.extraHttpHeaders + ", tenant=" + this.tenant + ")";
        }
    }
}
