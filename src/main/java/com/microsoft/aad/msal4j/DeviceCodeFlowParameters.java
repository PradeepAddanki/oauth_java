package com.microsoft.aad.msal4j;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.microsoft.aad.msal4j.ParameterValidationUtils.validateNotNull;

/**
 * Object containing parameters for device code flow. Can be used as parameter to
 * {@link PublicClientApplication#acquireToken(DeviceCodeFlowParameters)}. For more details,
 * see https://aka.ms/msal4j-device-code
 */
@Builder
@Accessors(fluent = true)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeviceCodeFlowParameters implements IAcquireTokenParameters {


    private Set<String> scopes;

    private Consumer<DeviceCode> deviceCodeConsumer;

    /**
     * Claims to be requested through the OIDC claims request parameter, allowing requests for standard and custom claims
     */
    private ClaimsRequest claims;

    /**
     * Adds additional headers to the token request
     */
    private Map<String, String> extraHttpHeaders;

    /**
     * Overrides the tenant value in the authority URL for this request
     */
    private String tenant;

    private static DeviceCodeFlowParametersBuilder builder() {

        return new DeviceCodeFlowParametersBuilder();
    }


    public static DeviceCodeFlowParametersBuilder builder
    (Set<String> scopes, Consumer<DeviceCode> deviceCodeConsumer) {

        validateNotNull("scopes", scopes);

        return builder()
                .scopes(scopes)
                .deviceCodeConsumer(deviceCodeConsumer);
    }
}
