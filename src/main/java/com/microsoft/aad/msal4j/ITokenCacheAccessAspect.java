package com.microsoft.aad.msal4j;

/**
 * Interface representing operation of executing code before and after cache access.
 * <p>
 * For more details, see https://aka.ms/msal4j-token-cache
 */
public interface ITokenCacheAccessAspect {

    void beforeCacheAccess(ITokenCacheAccessContext iTokenCacheAccessContext);

    void afterCacheAccess(ITokenCacheAccessContext iTokenCacheAccessContext);
}
