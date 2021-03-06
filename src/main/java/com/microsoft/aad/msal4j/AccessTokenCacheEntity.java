package com.microsoft.aad.msal4j;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

class AccessTokenCacheEntity extends Credential {
    @JsonProperty("credential_type")
    private String credentialType;
    @JsonProperty("realm")
    protected String realm;
    @JsonProperty("target")
    private String target;
    @JsonProperty("cached_at")
    private String cachedAt;
    @JsonProperty("expires_on")
    private String expiresOn;
    @JsonProperty("extended_expires_on")
    private String extExpiresOn;
    @JsonProperty("refresh_on")
    private String refreshOn;

    String getKey() {
        List<String> keyParts = new ArrayList<>();
        keyParts.add(StringHelper.isBlank(homeAccountId) ? "" : homeAccountId);
        keyParts.add(environment);
        keyParts.add(credentialType);
        keyParts.add(clientId);
        keyParts.add(realm);
        keyParts.add(target);
        return String.join(Constants.CACHE_KEY_SEPARATOR, keyParts).toLowerCase();
    }

    public String credentialType() {
        return this.credentialType;
    }

    public String realm() {
        return this.realm;
    }

    public String target() {
        return this.target;
    }

    public String cachedAt() {
        return this.cachedAt;
    }

    public String expiresOn() {
        return this.expiresOn;
    }

    public String extExpiresOn() {
        return this.extExpiresOn;
    }

    public String refreshOn() {
        return this.refreshOn;
    }

    public AccessTokenCacheEntity credentialType(final String credentialType) {
        this.credentialType = credentialType;
        return this;
    }

    public AccessTokenCacheEntity realm(final String realm) {
        this.realm = realm;
        return this;
    }

    public AccessTokenCacheEntity target(final String target) {
        this.target = target;
        return this;
    }

    public AccessTokenCacheEntity cachedAt(final String cachedAt) {
        this.cachedAt = cachedAt;
        return this;
    }


    public AccessTokenCacheEntity expiresOn(final String expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    public AccessTokenCacheEntity extExpiresOn(final String extExpiresOn) {
        this.extExpiresOn = extExpiresOn;
        return this;
    }

    public AccessTokenCacheEntity refreshOn(final String refreshOn) {
        this.refreshOn = refreshOn;
        return this;
    }
}
