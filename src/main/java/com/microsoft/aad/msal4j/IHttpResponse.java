package com.microsoft.aad.msal4j;

import java.util.List;
import java.util.Map;

/**
 * HTTP response from execution of {@link HttpRequest} in {@link IHttpClient}
 */
public interface IHttpResponse {

    /**
     * @return HTTP response status code.
     */
    int statusCode();

    /**
     * @return HTTP response headers
     */
    Map<String, List<String>> headers();

    /**
     * @return HTTP response body
     */
    String body();
}
