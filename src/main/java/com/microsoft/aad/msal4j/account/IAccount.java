package com.microsoft.aad.msal4j.account;

import com.microsoft.aad.msal4j.ITenantProfile;

import java.io.Serializable;
import java.util.Map;


public interface IAccount extends Serializable {

    String homeAccountId();
    String environment();
    String username();
    Map<String, ITenantProfile> getTenantProfiles();
}
