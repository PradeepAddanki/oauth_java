package com.microsoft.aad.msal4j;

import com.fasterxml.jackson.annotation.JsonProperty;

class UserDiscoveryResponse {
    @JsonProperty("ver")
    private float version;
    @JsonProperty("account_type")
    private String accountType;
    @JsonProperty("federation_metadata_url")
    private String federationMetadataUrl;
    @JsonProperty("federation_protocol")
    private String federationProtocol;
    @JsonProperty("federation_active_auth_url")
    private String federationActiveAuthUrl;
    @JsonProperty("cloud_audience_urn")
    private String cloudAudienceUrn;

    UserDiscoveryResponse() {
    }

    boolean isAccountFederated() {
        return !StringHelper.isBlank(this.accountType) && this.accountType.equalsIgnoreCase("Federated");
    }

    boolean isAccountManaged() {
        return !StringHelper.isBlank(this.accountType) && this.accountType.equalsIgnoreCase("Managed");
    }

    float version() {
        return this.version;
    }

    String accountType() {
        return this.accountType;
    }

    String federationMetadataUrl() {
        return this.federationMetadataUrl;
    }

    String federationProtocol() {
        return this.federationProtocol;
    }

    String federationActiveAuthUrl() {
        return this.federationActiveAuthUrl;
    }

    String cloudAudienceUrn() {
        return this.cloudAudienceUrn;
    }
}
