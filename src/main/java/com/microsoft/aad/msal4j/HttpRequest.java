package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.Exception.VasaraCloudException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


public class HttpRequest {

    /**
     * {@link HttpMethod}
     */
    private HttpMethod httpMethod;

    /**
     * HTTP request url
     */
    private URL url;

    /**
     * HTTP request headers
     */
    private Map<String, String> headers;

    /**
     * HTTP request body
     */
    private String body;

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    HttpRequest(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = createUrlFromString(url);
    }

    HttpRequest(HttpMethod httpMethod, String url, Map<String, String> headers) {
        this.httpMethod = httpMethod;
        this.url = createUrlFromString(url);
        this.headers = headers;
    }

    HttpRequest(HttpMethod httpMethod, String url, String body) {
        this.httpMethod = httpMethod;
        this.url = createUrlFromString(url);
        this.body = body;
    }

    HttpRequest(HttpMethod httpMethod,
                String url, Map<String, String> headers,
                String body) {
        this.httpMethod = httpMethod;
        this.url = createUrlFromString(url);
        this.headers = headers;
        this.body = body;
    }

    /**
     * @param headerName Name of HTTP header name
     * @return Value of HTTP header
     */
    public String headerValue(String headerName) {

        if (headerName == null || headers == null) {
            return null;
        }

        return headers.get(headerName);
    }

    private URL createUrlFromString(String stringUrl) {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            throw new VasaraCloudException(e);
        }

        return url;
    }
}
