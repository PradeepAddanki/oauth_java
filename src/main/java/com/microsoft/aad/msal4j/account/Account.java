package com.microsoft.aad.msal4j.account;

import com.microsoft.aad.msal4j.ITenantProfile;
import com.microsoft.aad.msal4j.account.IAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Accessors(fluent = true)
@Getter
@Setter
@AllArgsConstructor
public class Account implements IAccount {

    String homeAccountId;

    String environment;

    String username;

    Map<String, ITenantProfile> tenantProfiles;

    public Map<String, ITenantProfile> getTenantProfiles() {
        return tenantProfiles;
    }
}
