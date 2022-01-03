package com.microsoft.aad.msal4j;

class CurrentRequest {

    private final PublicApi publicApi;

    private int cacheInfo = -1;

    private String regionUsed = StringHelper.EMPTY_STRING;

    private int regionSource = 0;

    private int regionOutcome = 0;

    CurrentRequest(PublicApi publicApi) {
        this.publicApi = publicApi;
    }

    public PublicApi getPublicApi() {
        return publicApi;
    }

    public int getCacheInfo() {
        return cacheInfo;
    }

    public void setCacheInfo(int cacheInfo) {
        this.cacheInfo = cacheInfo;
    }

    public String getRegionUsed() {
        return regionUsed;
    }

    public void setRegionUsed(String regionUsed) {
        this.regionUsed = regionUsed;
    }

    public int getRegionSource() {
        return regionSource;
    }

    public void setRegionSource(int regionSource) {
        this.regionSource = regionSource;
    }

    public int getRegionOutcome() {
        return regionOutcome;
    }

    public void setRegionOutcome(int regionOutcome) {
        this.regionOutcome = regionOutcome;
    }
}