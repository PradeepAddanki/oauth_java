package com.microsoft.aad.msal4j;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class RequestedClaimAdditionalInfo {
    @JsonInclude(Include.NON_DEFAULT)
    @JsonProperty("essential")
    boolean essential;
    @JsonProperty("value")
    String value;
    @JsonProperty("values")
    List<String> values;

    public boolean isEssential() {
        return this.essential;
    }

    public String getValue() {
        return this.value;
    }

    public List<String> getValues() {
        return this.values;
    }

    @JsonProperty("essential")
    public void setEssential(boolean essential) {
        this.essential = essential;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("values")
    public void setValues(List<String> values) {
        this.values = values;
    }

    public RequestedClaimAdditionalInfo(boolean essential, String value, List<String> values) {
        this.essential = essential;
        this.value = value;
        this.values = values;
    }
}
