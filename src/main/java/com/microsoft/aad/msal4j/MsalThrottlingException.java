package com.microsoft.aad.msal4j;

public class MsalThrottlingException extends MsalServiceException {


    private long retryInMs;

    public MsalThrottlingException(long retryInMs) {
        super("Request was throttled according to instructions from STS. Retry in " + retryInMs + " ms.",
                AuthenticationErrorCode.THROTTLED_REQUEST);

        this.retryInMs = retryInMs;
    }

    public long retryInMs() {
        return retryInMs;
    }
}
