package com.microsoft.aad.msal4j;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response returned from the STS device code endpoint containing information necessary for
 * device code flow
 */

public final class DeviceCode {

    /**
     * code which user needs to provide when authenticating at the verification URI
     */
    @JsonProperty("user_code")
    private String userCode;

    /**
     * code which should be included in the request for the access token
     */
    @JsonProperty("device_code")
    private String deviceCode;

    /**
     * URI where user can authenticate
     */
    @JsonProperty("verification_uri")
    private String verificationUri;

    /**
     * expiration time of device code in seconds.
     */
    @JsonProperty("expires_in")
    private long expiresIn;

    /**
     * interval at which the STS should be polled at
     */
    @JsonProperty("interval")
    private long interval;

    /**
     * message which should be displayed to the user.
     */
    @JsonProperty("message")
    private String message;

    private transient String correlationId = null;

    private transient String clientId = null;

    private transient String scopes = null;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
