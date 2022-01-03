package com.microsoft.aad.msal4j;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class DeviceCodeFlowParameters implements IAcquireTokenParameters {
    private Set<String> scopes;
    private Consumer<DeviceCode> deviceCodeConsumer;
    private ClaimsRequest claims;
    private Map<String, String> extraHttpHeaders;
    private String tenant;

    private static DeviceCodeFlowParameters.DeviceCodeFlowParametersBuilder builder() {
        return new DeviceCodeFlowParameters.DeviceCodeFlowParametersBuilder();
    }

    public static DeviceCodeFlowParameters.DeviceCodeFlowParametersBuilder builder(Set<String> scopes, Consumer<DeviceCode> deviceCodeConsumer) {
        ParameterValidationUtils.validateNotNull("scopes", scopes);
        return builder().scopes(scopes).deviceCodeConsumer(deviceCodeConsumer);
    }

    public Set<String> scopes() {
        return this.scopes;
    }

    public Consumer<DeviceCode> deviceCodeConsumer() {
        return this.deviceCodeConsumer;
    }

    public ClaimsRequest claims() {
        return this.claims;
    }

    public Map<String, String> extraHttpHeaders() {
        return this.extraHttpHeaders;
    }

    public String tenant() {
        return this.tenant;
    }

    private DeviceCodeFlowParameters(Set<String> scopes, Consumer<DeviceCode> deviceCodeConsumer, ClaimsRequest claims, Map<String, String> extraHttpHeaders, String tenant) {
        this.scopes = scopes;
        this.deviceCodeConsumer = deviceCodeConsumer;
        this.claims = claims;
        this.extraHttpHeaders = extraHttpHeaders;
        this.tenant = tenant;
    }

    public static class DeviceCodeFlowParametersBuilder {
        private Set<String> scopes;
        private Consumer<DeviceCode> deviceCodeConsumer;
        private ClaimsRequest claims;
        private Map<String, String> extraHttpHeaders;
        private String tenant;

        DeviceCodeFlowParametersBuilder() {
        }

        public DeviceCodeFlowParameters.DeviceCodeFlowParametersBuilder scopes(Set<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public DeviceCodeFlowParameters.DeviceCodeFlowParametersBuilder deviceCodeConsumer(Consumer<DeviceCode> deviceCodeConsumer) {
            this.deviceCodeConsumer = deviceCodeConsumer;
            return this;
        }

        public DeviceCodeFlowParameters.DeviceCodeFlowParametersBuilder claims(ClaimsRequest claims) {
            this.claims = claims;
            return this;
        }

        public DeviceCodeFlowParameters.DeviceCodeFlowParametersBuilder extraHttpHeaders(Map<String, String> extraHttpHeaders) {
            this.extraHttpHeaders = extraHttpHeaders;
            return this;
        }

        public DeviceCodeFlowParameters.DeviceCodeFlowParametersBuilder tenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public DeviceCodeFlowParameters build() {
            return new DeviceCodeFlowParameters(this.scopes, this.deviceCodeConsumer, this.claims, this.extraHttpHeaders, this.tenant);
        }

        public String toString() {
            return "DeviceCodeFlowParameters.DeviceCodeFlowParametersBuilder(scopes=" + this.scopes + ", deviceCodeConsumer=" + this.deviceCodeConsumer + ", claims=" + this.claims + ", extraHttpHeaders=" + this.extraHttpHeaders + ", tenant=" + this.tenant + ")";
        }
    }
}
