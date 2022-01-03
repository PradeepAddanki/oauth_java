package com.microsoft.aad.msal4j;

import java.util.Map;
import java.util.Set;

/**
 * Parameters shared by all acquireToken methods
 */
interface IAcquireTokenParameters {
    Set<String> scopes();

    ClaimsRequest claims();

    Map<String, String> extraHttpHeaders();
    String tenant();
}
