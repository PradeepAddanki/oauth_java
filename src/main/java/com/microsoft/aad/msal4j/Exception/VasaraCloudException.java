package com.microsoft.aad.msal4j.Exception;

public class VasaraCloudException extends RuntimeException {
    private String errorCode;

    public VasaraCloudException(final Throwable throwable) {
        super(throwable);
    }

    public VasaraCloudException(final String message, final String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return errorCode;
    }
}
