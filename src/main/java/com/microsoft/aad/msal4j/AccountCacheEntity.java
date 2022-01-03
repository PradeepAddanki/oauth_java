//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.microsoft.aad.msal4j;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.aad.msal4j.account.Account;
import com.microsoft.aad.msal4j.account.IAccount;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class AccountCacheEntity implements Serializable {
    static final String MSSTS_ACCOUNT_TYPE = "MSSTS";
    static final String ADFS_ACCOUNT_TYPE = "ADFS";
    @JsonProperty("home_account_id")
    protected String homeAccountId;
    @JsonProperty("environment")
    protected String environment;
    @JsonProperty("realm")
    protected String realm;
    @JsonProperty("local_account_id")
    protected String localAccountId;
    @JsonProperty("username")
    protected String username;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("client_info")
    protected String clientInfoStr;
    @JsonProperty("user_assertion_hash")
    protected String userAssertionHash;
    @JsonProperty("authority_type")
    protected String authorityType;

    AccountCacheEntity() {
    }

    ClientInfo clientInfo() {
        return ClientInfo.createFromJson(this.clientInfoStr);
    }

    String getKey() {
        List<String> keyParts = new ArrayList();
        keyParts.add(this.homeAccountId);
        keyParts.add(this.environment);
        keyParts.add(StringHelper.isBlank(this.realm) ? "" : this.realm);
        return String.join("-", keyParts).toLowerCase();
    }

    static AccountCacheEntity create(String clientInfoStr, Authority requestAuthority, IdToken idToken, String policy) {
        AccountCacheEntity account = new AccountCacheEntity();
        account.authorityType("MSSTS");
        account.clientInfoStr = clientInfoStr;
        account.homeAccountId(policy != null ? account.clientInfo().toAccountIdentifier() + "-" + policy : account.clientInfo().toAccountIdentifier());
        account.environment(requestAuthority.host());
        account.realm(requestAuthority.tenant());
        if (idToken != null) {
            String localAccountId = !StringHelper.isBlank(idToken.objectIdentifier) ? idToken.objectIdentifier : idToken.subject;
            account.localAccountId(localAccountId);
            account.username(idToken.preferredUsername);
            account.name(idToken.name);
        }

        return account;
    }

    static AccountCacheEntity createADFSAccount(Authority requestAuthority, IdToken idToken) {
        AccountCacheEntity account = new AccountCacheEntity();
        account.authorityType("ADFS");
        account.homeAccountId(idToken.subject);
        account.environment(requestAuthority.host());
        account.username(idToken.upn);
        account.name(idToken.uniqueName);
        return account;
    }

    static AccountCacheEntity create(String clientInfoStr, Authority requestAuthority, IdToken idToken) {
        return create(clientInfoStr, requestAuthority, idToken, (String)null);
    }

    IAccount toAccount() {
        return new Account(this.homeAccountId, this.environment, this.username, (Map)null);
    }

    public String homeAccountId() {
        return this.homeAccountId;
    }

    public String environment() {
        return this.environment;
    }

    public String realm() {
        return this.realm;
    }

    public String localAccountId() {
        return this.localAccountId;
    }

    public String username() {
        return this.username;
    }

    public String name() {
        return this.name;
    }

    public String clientInfoStr() {
        return this.clientInfoStr;
    }

    public String userAssertionHash() {
        return this.userAssertionHash;
    }

    public String authorityType() {
        return this.authorityType;
    }

    @JsonProperty("home_account_id")
    public AccountCacheEntity homeAccountId(String homeAccountId) {
        this.homeAccountId = homeAccountId;
        return this;
    }

    @JsonProperty("environment")
    public AccountCacheEntity environment(String environment) {
        this.environment = environment;
        return this;
    }

    @JsonProperty("realm")
    public AccountCacheEntity realm(String realm) {
        this.realm = realm;
        return this;
    }

    @JsonProperty("local_account_id")
    public AccountCacheEntity localAccountId(String localAccountId) {
        this.localAccountId = localAccountId;
        return this;
    }

    @JsonProperty("username")
    public AccountCacheEntity username(String username) {
        this.username = username;
        return this;
    }

    @JsonProperty("name")
    public AccountCacheEntity name(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("client_info")
    public AccountCacheEntity clientInfoStr(String clientInfoStr) {
        this.clientInfoStr = clientInfoStr;
        return this;
    }

    @JsonProperty("user_assertion_hash")
    public AccountCacheEntity userAssertionHash(String userAssertionHash) {
        this.userAssertionHash = userAssertionHash;
        return this;
    }

    @JsonProperty("authority_type")
    public AccountCacheEntity authorityType(String authorityType) {
        this.authorityType = authorityType;
        return this;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AccountCacheEntity)) {
            return false;
        } else {
            AccountCacheEntity other = (AccountCacheEntity)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label119: {
                    Object this$homeAccountId = this.homeAccountId();
                    Object other$homeAccountId = other.homeAccountId();
                    if (this$homeAccountId == null) {
                        if (other$homeAccountId == null) {
                            break label119;
                        }
                    } else if (this$homeAccountId.equals(other$homeAccountId)) {
                        break label119;
                    }

                    return false;
                }

                Object this$environment = this.environment();
                Object other$environment = other.environment();
                if (this$environment == null) {
                    if (other$environment != null) {
                        return false;
                    }
                } else if (!this$environment.equals(other$environment)) {
                    return false;
                }

                label105: {
                    Object this$realm = this.realm();
                    Object other$realm = other.realm();
                    if (this$realm == null) {
                        if (other$realm == null) {
                            break label105;
                        }
                    } else if (this$realm.equals(other$realm)) {
                        break label105;
                    }

                    return false;
                }

                Object this$localAccountId = this.localAccountId();
                Object other$localAccountId = other.localAccountId();
                if (this$localAccountId == null) {
                    if (other$localAccountId != null) {
                        return false;
                    }
                } else if (!this$localAccountId.equals(other$localAccountId)) {
                    return false;
                }

                label91: {
                    Object this$username = this.username();
                    Object other$username = other.username();
                    if (this$username == null) {
                        if (other$username == null) {
                            break label91;
                        }
                    } else if (this$username.equals(other$username)) {
                        break label91;
                    }

                    return false;
                }

                Object this$name = this.name();
                Object other$name = other.name();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                label77: {
                    Object this$clientInfoStr = this.clientInfoStr();
                    Object other$clientInfoStr = other.clientInfoStr();
                    if (this$clientInfoStr == null) {
                        if (other$clientInfoStr == null) {
                            break label77;
                        }
                    } else if (this$clientInfoStr.equals(other$clientInfoStr)) {
                        break label77;
                    }

                    return false;
                }

                label70: {
                    Object this$userAssertionHash = this.userAssertionHash();
                    Object other$userAssertionHash = other.userAssertionHash();
                    if (this$userAssertionHash == null) {
                        if (other$userAssertionHash == null) {
                            break label70;
                        }
                    } else if (this$userAssertionHash.equals(other$userAssertionHash)) {
                        break label70;
                    }

                    return false;
                }

                Object this$authorityType = this.authorityType();
                Object other$authorityType = other.authorityType();
                if (this$authorityType == null) {
                    if (other$authorityType != null) {
                        return false;
                    }
                } else if (!this$authorityType.equals(other$authorityType)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof AccountCacheEntity;
    }

}
