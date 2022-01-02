package com.microsoft.aad.msal4j.result;

import com.microsoft.aad.msal4j.account.IAccount;
import com.microsoft.aad.msal4j.ITenantProfile;

import java.util.Date;

public interface IAuthenticationResult {

    String accessToken();

    String idToken();

    IAccount account();

    ITenantProfile tenantProfile();

    String environment();

    String scopes();

    Date expiresOnDate();
}
