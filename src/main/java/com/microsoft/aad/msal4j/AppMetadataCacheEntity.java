package com.microsoft.aad.msal4j;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

class AppMetadataCacheEntity {

    public static final String APP_METADATA_CACHE_ENTITY_ID = "appmetadata";

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("environment")
    private String environment;

    @JsonProperty("family_id")
    private String familyId;

    String getKey() {
        List<String> keyParts = new ArrayList<>();

        keyParts.add(APP_METADATA_CACHE_ENTITY_ID);
        keyParts.add(environment);
        keyParts.add(clientId);

        return String.join(Constants.CACHE_KEY_SEPARATOR, keyParts).toLowerCase();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }
}
