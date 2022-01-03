package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.Exception.VasaraCloudException;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponse;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;
import net.minidev.json.JSONObject;

class TokenResponse extends OIDCTokenResponse {
    private String scope;
    private String clientInfo;
    private long expiresIn;
    private long extExpiresIn;
    private String foci;
    private long refreshIn;

    TokenResponse(AccessToken accessToken, RefreshToken refreshToken, String idToken, String scope, String clientInfo, long expiresIn, long extExpiresIn, String foci, long refreshIn) {
        super(new OIDCTokens(idToken, accessToken, refreshToken));
        this.scope = scope;
        this.clientInfo = clientInfo;
        this.expiresIn = expiresIn;
        this.extExpiresIn = extExpiresIn;
        this.refreshIn = refreshIn;
        this.foci = foci;
    }

    static TokenResponse parseHttpResponse(HTTPResponse httpResponse) throws ParseException {
        httpResponse.ensureStatusCode(new int[]{200});
        JSONObject jsonObject = httpResponse.getContentAsJSONObject();
        return parseJsonObject(jsonObject);
    }

    static Long getLongValue(JSONObject jsonObject, String key) throws ParseException {
        Object value = jsonObject.get(key);
        return value instanceof Long ? JSONObjectUtils.getLong(jsonObject, key) : Long.parseLong(JSONObjectUtils.getString(jsonObject, key));
    }

    static TokenResponse parseJsonObject(JSONObject jsonObject) throws ParseException {
        String idTokenValue = "";
        if (jsonObject.containsKey("id_token")) {
            idTokenValue = JSONObjectUtils.getString(jsonObject, "id_token");
        }

        String scopeValue = null;
        if (jsonObject.containsKey("scope")) {
            scopeValue = JSONObjectUtils.getString(jsonObject, "scope");
        }

        String clientInfo = null;
        if (jsonObject.containsKey("client_info")) {
            clientInfo = JSONObjectUtils.getString(jsonObject, "client_info");
        }

        long expiresIn = 0L;
        if (jsonObject.containsKey("expires_in")) {
            expiresIn = getLongValue(jsonObject, "expires_in");
        }

        long ext_expires_in = 0L;
        if (jsonObject.containsKey("ext_expires_in")) {
            ext_expires_in = getLongValue(jsonObject, "ext_expires_in");
        }

        String foci = null;
        if (jsonObject.containsKey("foci")) {
            foci = JSONObjectUtils.getString(jsonObject, "foci");
        }

        long refreshIn = 0L;
        if (jsonObject.containsKey("refresh_in")) {
            refreshIn = getLongValue(jsonObject, "refresh_in");
        }

        try {
            AccessToken accessToken = AccessToken.parse(jsonObject);
            RefreshToken refreshToken = RefreshToken.parse(jsonObject);
            return new TokenResponse(accessToken, refreshToken, idTokenValue, scopeValue, clientInfo, expiresIn, ext_expires_in, foci, refreshIn);
        } catch (ParseException var13) {
            throw new VasaraCloudException("Invalid or missing token, could not parse. If using B2C, information on a potential B2C issue and workaround can be found here: https://aka.ms/msal4j-b2c-known-issues", "invalid_json");
        } catch (Exception var14) {
            throw new VasaraCloudException(var14);
        }
    }

    String getScope() {
        return this.scope;
    }

    String getClientInfo() {
        return this.clientInfo;
    }

    long getExpiresIn() {
        return this.expiresIn;
    }

    long getExtExpiresIn() {
        return this.extExpiresIn;
    }

    String getFoci() {
        return this.foci;
    }

    long getRefreshIn() {
        return this.refreshIn;
    }
}
