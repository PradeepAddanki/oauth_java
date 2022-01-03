package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.Exception.VasaraCloudException;
import com.microsoft.aad.msal4j.account.IAccount;
import com.microsoft.aad.msal4j.result.IAuthenticationResult;
import com.nimbusds.jwt.JWTParser;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

final class AuthenticationResult implements IAuthenticationResult {
    private static final long serialVersionUID = 1L;
    private final String accessToken;
    private final long expiresOn;
    private final long extExpiresOn;
    private final String refreshToken;
    private final Long refreshOn;
    private final String familyId;
    private final String idToken;
    private final AtomicReference<Object> idTokenObject = new AtomicReference();
    private final AccountCacheEntity accountCacheEntity;
    private final AtomicReference<Object> account = new AtomicReference();
    private final AtomicReference<Object> tenantProfile = new AtomicReference();
    private String environment;
    private final AtomicReference<Object> expiresOnDate = new AtomicReference();
    private final String scopes;

    private IdToken getIdTokenObj() {
        if (StringHelper.isBlank(this.idToken)) {
            return null;
        } else {
            try {
                String idTokenJson = JWTParser.parse(this.idToken).getParsedParts()[1].decodeToString();
                return (IdToken)JsonHelper.convertJsonToObject(idTokenJson, IdToken.class);
            } catch (ParseException var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    private IAccount getAccount() {
        return this.accountCacheEntity == null ? null : this.accountCacheEntity.toAccount();
    }

    private ITenantProfile getTenantProfile() {
        if (StringHelper.isBlank(this.idToken)) {
            return null;
        } else {
            try {
                return new TenantProfile(JWTParser.parse(this.idToken).getJWTClaimsSet().getClaims(), this.getAccount().environment());
            } catch (ParseException var2) {
                throw new VasaraCloudException("Cached JWT could not be parsed: " + var2.getMessage(), "invalid_jwt");
            }
        }
    }

    AuthenticationResult(String accessToken, long expiresOn, long extExpiresOn, String refreshToken, Long refreshOn, String familyId, String idToken, AccountCacheEntity accountCacheEntity, String environment, String scopes) {
        this.accessToken = accessToken;
        this.expiresOn = expiresOn;
        this.extExpiresOn = extExpiresOn;
        this.refreshToken = refreshToken;
        this.refreshOn = refreshOn;
        this.familyId = familyId;
        this.idToken = idToken;
        this.accountCacheEntity = accountCacheEntity;
        this.environment = environment;
        this.scopes = scopes;
    }

    public static AuthenticationResult.AuthenticationResultBuilder builder() {
        return new AuthenticationResult.AuthenticationResultBuilder();
    }

    public String accessToken() {
        return this.accessToken;
    }

    public String refreshToken() {
        return this.refreshToken;
    }

    public Long refreshOn() {
        return this.refreshOn;
    }

    public String idToken() {
        return this.idToken;
    }

    public String environment() {
        return this.environment;
    }

    public String scopes() {
        return this.scopes;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AuthenticationResult)) {
            return false;
        } else {
            AuthenticationResult other = (AuthenticationResult)o;
            if (this.expiresOn() != other.expiresOn()) {
                return false;
            } else if (this.extExpiresOn() != other.extExpiresOn()) {
                return false;
            } else {
                label157: {
                    Object this$refreshOn = this.refreshOn();
                    Object other$refreshOn = other.refreshOn();
                    if (this$refreshOn == null) {
                        if (other$refreshOn == null) {
                            break label157;
                        }
                    } else if (this$refreshOn.equals(other$refreshOn)) {
                        break label157;
                    }

                    return false;
                }

                label150: {
                    Object this$accessToken = this.accessToken();
                    Object other$accessToken = other.accessToken();
                    if (this$accessToken == null) {
                        if (other$accessToken == null) {
                            break label150;
                        }
                    } else if (this$accessToken.equals(other$accessToken)) {
                        break label150;
                    }

                    return false;
                }

                Object this$refreshToken = this.refreshToken();
                Object other$refreshToken = other.refreshToken();
                if (this$refreshToken == null) {
                    if (other$refreshToken != null) {
                        return false;
                    }
                } else if (!this$refreshToken.equals(other$refreshToken)) {
                    return false;
                }

                label136: {
                    Object this$familyId = this.familyId();
                    Object other$familyId = other.familyId();
                    if (this$familyId == null) {
                        if (other$familyId == null) {
                            break label136;
                        }
                    } else if (this$familyId.equals(other$familyId)) {
                        break label136;
                    }

                    return false;
                }

                Object this$idToken = this.idToken();
                Object other$idToken = other.idToken();
                if (this$idToken == null) {
                    if (other$idToken != null) {
                        return false;
                    }
                } else if (!this$idToken.equals(other$idToken)) {
                    return false;
                }

                label122: {
                    Object this$idTokenObject = this.idTokenObject();
                    Object other$idTokenObject = other.idTokenObject();
                    if (this$idTokenObject == null) {
                        if (other$idTokenObject == null) {
                            break label122;
                        }
                    } else if (this$idTokenObject.equals(other$idTokenObject)) {
                        break label122;
                    }

                    return false;
                }

                Object this$accountCacheEntity = this.accountCacheEntity();
                Object other$accountCacheEntity = other.accountCacheEntity();
                if (this$accountCacheEntity == null) {
                    if (other$accountCacheEntity != null) {
                        return false;
                    }
                } else if (!this$accountCacheEntity.equals(other$accountCacheEntity)) {
                    return false;
                }

                Object this$account = this.account();
                Object other$account = other.account();
                if (this$account == null) {
                    if (other$account != null) {
                        return false;
                    }
                } else if (!this$account.equals(other$account)) {
                    return false;
                }

                Object this$tenantProfile = this.tenantProfile();
                Object other$tenantProfile = other.tenantProfile();
                if (this$tenantProfile == null) {
                    if (other$tenantProfile != null) {
                        return false;
                    }
                } else if (!this$tenantProfile.equals(other$tenantProfile)) {
                    return false;
                }

                label94: {
                    Object this$environment = this.environment();
                    Object other$environment = other.environment();
                    if (this$environment == null) {
                        if (other$environment == null) {
                            break label94;
                        }
                    } else if (this$environment.equals(other$environment)) {
                        break label94;
                    }

                    return false;
                }

                label87: {
                    Object this$expiresOnDate = this.expiresOnDate();
                    Object other$expiresOnDate = other.expiresOnDate();
                    if (this$expiresOnDate == null) {
                        if (other$expiresOnDate == null) {
                            break label87;
                        }
                    } else if (this$expiresOnDate.equals(other$expiresOnDate)) {
                        break label87;
                    }

                    return false;
                }

                Object this$scopes = this.scopes();
                Object other$scopes = other.scopes();
                if (this$scopes == null) {
                    if (other$scopes != null) {
                        return false;
                    }
                } else if (!this$scopes.equals(other$scopes)) {
                    return false;
                }

                return true;
            }
        }
    }

    long expiresOn() {
        return this.expiresOn;
    }

    long extExpiresOn() {
        return this.extExpiresOn;
    }

    String familyId() {
        return this.familyId;
    }

    IdToken idTokenObject() {
        Object value = this.idTokenObject.get();
        if (value == null) {
            synchronized(this.idTokenObject) {
                value = this.idTokenObject.get();
                if (value == null) {
                    IdToken actualValue = this.getIdTokenObj();
                    value = actualValue == null ? this.idTokenObject : actualValue;
                    this.idTokenObject.set(value);
                }
            }
        }

        return (IdToken)(value == this.idTokenObject ? null : value);
    }

    AccountCacheEntity accountCacheEntity() {
        return this.accountCacheEntity;
    }

    public IAccount account() {
        Object value = this.account.get();
        if (value == null) {
            synchronized(this.account) {
                value = this.account.get();
                if (value == null) {
                    IAccount actualValue = this.getAccount();
                    value = actualValue == null ? this.account : actualValue;
                    this.account.set(value);
                }
            }
        }

        return (IAccount)(value == this.account ? null : value);
    }

    public ITenantProfile tenantProfile() {
        Object value = this.tenantProfile.get();
        if (value == null) {
            synchronized(this.tenantProfile) {
                value = this.tenantProfile.get();
                if (value == null) {
                    ITenantProfile actualValue = this.getTenantProfile();
                    value = actualValue == null ? this.tenantProfile : actualValue;
                    this.tenantProfile.set(value);
                }
            }
        }

        return (ITenantProfile)(value == this.tenantProfile ? null : value);
    }

    public Date expiresOnDate() {
        Object value = this.expiresOnDate.get();
        if (value == null) {
            synchronized(this.expiresOnDate) {
                value = this.expiresOnDate.get();
                if (value == null) {
                    Date actualValue = new Date(this.expiresOn * 1000L);
                    value = actualValue == null ? this.expiresOnDate : actualValue;
                    this.expiresOnDate.set(value);
                }
            }
        }

        return (Date)(value == this.expiresOnDate ? null : value);
    }

    public static class AuthenticationResultBuilder {
        private String accessToken;
        private long expiresOn;
        private long extExpiresOn;
        private String refreshToken;
        private Long refreshOn;
        private String familyId;
        private String idToken;
        private AccountCacheEntity accountCacheEntity;
        private String environment;
        private String scopes;

        AuthenticationResultBuilder() {
        }

        public AuthenticationResult.AuthenticationResultBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public AuthenticationResult.AuthenticationResultBuilder expiresOn(long expiresOn) {
            this.expiresOn = expiresOn;
            return this;
        }

        public AuthenticationResult.AuthenticationResultBuilder extExpiresOn(long extExpiresOn) {
            this.extExpiresOn = extExpiresOn;
            return this;
        }

        public AuthenticationResult.AuthenticationResultBuilder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public AuthenticationResult.AuthenticationResultBuilder refreshOn(Long refreshOn) {
            this.refreshOn = refreshOn;
            return this;
        }

        public AuthenticationResult.AuthenticationResultBuilder familyId(String familyId) {
            this.familyId = familyId;
            return this;
        }

        public AuthenticationResult.AuthenticationResultBuilder idToken(String idToken) {
            this.idToken = idToken;
            return this;
        }

        public AuthenticationResult.AuthenticationResultBuilder accountCacheEntity(AccountCacheEntity accountCacheEntity) {
            this.accountCacheEntity = accountCacheEntity;
            return this;
        }

        public AuthenticationResult.AuthenticationResultBuilder environment(String environment) {
            this.environment = environment;
            return this;
        }

        public AuthenticationResult.AuthenticationResultBuilder scopes(String scopes) {
            this.scopes = scopes;
            return this;
        }

        public AuthenticationResult build() {
            return new AuthenticationResult(this.accessToken, this.expiresOn, this.extExpiresOn, this.refreshToken, this.refreshOn, this.familyId, this.idToken, this.accountCacheEntity, this.environment, this.scopes);
        }

        public String toString() {
            return "AuthenticationResult.AuthenticationResultBuilder(accessToken=" + this.accessToken + ", expiresOn=" + this.expiresOn + ", extExpiresOn=" + this.extExpiresOn + ", refreshToken=" + this.refreshToken + ", refreshOn=" + this.refreshOn + ", familyId=" + this.familyId + ", idToken=" + this.idToken + ", accountCacheEntity=" + this.accountCacheEntity + ", environment=" + this.environment + ", scopes=" + this.scopes + ")";
        }
    }
}
