package com.microsoft.aad.msal4j;

import com.fasterxml.jackson.annotation.JsonProperty;

class Credential {

    @JsonProperty("home_account_id")
    protected String homeAccountId;

    @JsonProperty("environment")
    protected String environment;

    @JsonProperty("client_id")
    protected String clientId;

    @JsonProperty("secret")
    protected String secret;

    public String getHomeAccountId() {
        return homeAccountId;
    }

    public void setHomeAccountId(String homeAccountId) {
        this.homeAccountId = homeAccountId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUserAssertionHash() {
        return userAssertionHash;
    }

    public void setUserAssertionHash(String userAssertionHash) {
        this.userAssertionHash = userAssertionHash;
    }

    @JsonProperty("user_assertion_hash")
    protected String userAssertionHash;


}
