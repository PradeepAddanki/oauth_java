package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.Exception.VasaraCloudException;


import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Exception type thrown when service returns an error response or other networking errors occur.
 */
public class MsalServiceException extends VasaraCloudException {

    /**
     * Status code returned from http layer
     */
    private Integer statusCode;

    /**
     * Status message returned from the http layer
     */
    private String statusMessage;

    /**
     * An ID that can be used to piece up a single authentication flow.
     */
    private String correlationId;

    /**
     * Claims included in the claims challenge
     */
    private String claims;

    /**
     * Contains the http headers from the server response that indicated an error.
     * When the server returns a 429 Too Many Requests error, a Retry-After should be set.
     * It is important to read and respect the time specified in the Retry-After header
     */
    private Map<String, List<String>> headers;

    private String subError;

    /**
     * Initializes a new instance of the exception class with a specified error message
     *
     * @param message the error message that explains the reason for the exception
     */
    public MsalServiceException(final String message, final String error) {
        super(message, error);
    }

    /**
     * Initializes a new instance of the exception class
     *
     * @param errorResponse response object contain information about error returned by server
     * @param httpHeaders   http headers from the server response
     */
    public MsalServiceException(
            final ErrorResponse errorResponse,
            final Map<String, List<String>> httpHeaders) {

        super(errorResponse.errorDescription, errorResponse.getError());

        this.statusCode = errorResponse.getStatusCode();
        this.statusMessage = errorResponse.getStatusMessage();
        this.subError = errorResponse.getSubError();
        this.correlationId = errorResponse.getCorrelation_id();
        this.claims = errorResponse.getClaims();
        this.headers = Collections.unmodifiableMap(httpHeaders);
    }

    /**
     * Initializes a new instance of the exception class
     *
     * @param discoveryResponse response object from instance discovery network call
     */
    public MsalServiceException(final AadInstanceDiscoveryResponse discoveryResponse) {
        super(discoveryResponse.errorDescription(), discoveryResponse.error());

        this.correlationId = discoveryResponse.correlationId();
    }
}
