package com.microsoft.aad.msal4j;

enum CredentialTypeEnum {

    ACCESS_TOKEN("AccessToken"),
    REFRESH_TOKEN("RefreshToken"),
    ID_TOKEN("IdToken");

    private final String value;

    public String value() {
        return this.value;
    }

    CredentialTypeEnum(String value) {
        this.value = value;
    }
}
