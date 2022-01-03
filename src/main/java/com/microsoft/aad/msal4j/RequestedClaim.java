package com.microsoft.aad.msal4j;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


import java.util.Collections;
import java.util.Map;

/**
 * Represents an individual requested claims that's part of a complete claims request parameter
 *
 * @see <a href="https://openid.net/specs/openid-connect-core-1_0-final.html#ClaimsParameter">https://openid.net/specs/openid-connect-core-1_0-final.html#ClaimsParameter</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestedClaim {

    @JsonIgnore
    public String name;

    RequestedClaimAdditionalInfo requestedClaimAdditionalInfo;

    @JsonAnyGetter
    protected Map<String, Object> any() {
        return Collections.singletonMap(name, requestedClaimAdditionalInfo);
    }
    public RequestedClaim(String name, RequestedClaimAdditionalInfo requestedClaimAdditionalInfo) {
        this.name = name;
        this.requestedClaimAdditionalInfo = requestedClaimAdditionalInfo;
    }
}
