package com.microsoft.aad.msal4j;

import com.fasterxml.jackson.annotation.JsonProperty;

class AadInstanceDiscoveryResponse {
    @JsonProperty("tenant_discovery_endpoint")
    private String tenantDiscoveryEndpoint;
    @JsonProperty("metadata")
    private InstanceDiscoveryMetadataEntry[] metadata;
    @JsonProperty("error_description")
    private String errorDescription;
    @JsonProperty("error_codes")
    private long[] errorCodes;
    @JsonProperty("error")
    private String error;
    @JsonProperty("correlation_id")
    private String correlationId;

    AadInstanceDiscoveryResponse() {
    }

    String tenantDiscoveryEndpoint() {
        return this.tenantDiscoveryEndpoint;
    }

    InstanceDiscoveryMetadataEntry[] metadata() {
        return this.metadata;
    }

    String errorDescription() {
        return this.errorDescription;
    }

    long[] errorCodes() {
        return this.errorCodes;
    }

    String error() {
        return this.error;
    }

    String correlationId() {
        return this.correlationId;
    }
}
