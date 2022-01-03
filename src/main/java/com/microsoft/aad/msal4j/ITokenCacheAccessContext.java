package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.account.IAccount;

/**
 * Interface representing context in which the token cache is accessed
 * <p>
 * For more details, see https://aka.ms/msal4j-token-cache
 */
public interface ITokenCacheAccessContext {

    /**
     * @return instance of accessed ITokenCache
     */
    ITokenCache tokenCache();

    /**
     * @return client id used for cache access
     */
    String clientId();

    /**
     * @return instance of IAccount used for cache access
     */
    IAccount account();

    /**
     * @return a boolean value telling whether cache was changed
     */
    boolean hasCacheChanged();
}
