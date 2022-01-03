package com.microsoft.aad.msal4j;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

class ErrorResponse {

    private Integer statusCode;
    private String statusMessage;

    @JsonProperty("error")
    protected String error;

    @JsonProperty("error_description")
    protected String errorDescription;

    @JsonProperty("error_codes")
    protected long[] errorCodes;

    @JsonProperty("suberror")
    protected String subError;

    @JsonProperty("trace_id")
    protected String traceId;

    @JsonProperty("timestamp")
    protected String timestamp;

    @JsonProperty("correlation_id")
    protected String correlation_id;

    @JsonProperty("claims")
    private String claims;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public long[] getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(long[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    public String getSubError() {
        return subError;
    }

    public void setSubError(String subError) {
        this.subError = subError;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCorrelation_id() {
        return correlation_id;
    }

    public void setCorrelation_id(String correlation_id) {
        this.correlation_id = correlation_id;
    }

    public String getClaims() {
        return claims;
    }

    public void setClaims(String claims) {
        this.claims = claims;
    }
}
