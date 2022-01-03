package com.microsoft.aad.msal4j.account;

import com.microsoft.aad.msal4j.ITenantProfile;

import java.util.Map;

public class Account implements IAccount {
    String homeAccountId;
    String environment;
    String username;
    Map<String, ITenantProfile> tenantProfiles;

    public Account(String homeAccountId, String environment, String username, Map<String, ITenantProfile> tenantProfiles) {
        this.homeAccountId = homeAccountId;
        this.environment = environment;
        this.username = username;
        this.tenantProfiles = tenantProfiles;
    }

    public Map<String, ITenantProfile> getTenantProfiles() {
        return this.tenantProfiles;
    }

    public String homeAccountId() {
        return this.homeAccountId;
    }

    public String environment() {
        return this.environment;
    }

    public String username() {
        return this.username;
    }

    public Map<String, ITenantProfile> tenantProfiles() {
        return this.tenantProfiles;
    }

    public Account homeAccountId(String homeAccountId) {
        this.homeAccountId = homeAccountId;
        return this;
    }

    public Account environment(String environment) {
        this.environment = environment;
        return this;
    }

    public Account username(String username) {
        this.username = username;
        return this;
    }

    public Account tenantProfiles(Map<String, ITenantProfile> tenantProfiles) {
        this.tenantProfiles = tenantProfiles;
        return this;
    }


}
