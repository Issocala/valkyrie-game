package application.module.user.data.domain;

import application.util.AbstractBuilder;
import com.cala.orm.annotation.DbDeserialize;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DbStatus;

import javax.persistence.Column;

/**
 * @author Luo Yong
 * @date 2022-1-7
 * @Source 1.0
 */
@DbDeserialize(builder = User.Builder.class)
public class User extends AbstractEntityBase {

    @Column
    private final String account;

    @Column
    private final String accountName;

    @Column
    private final String password;

    @Column
    private final String platform;

    @Column
    private final long timestamp;

    public User(Long id, DbStatus dbStatus, String account, String accountName, String password, String platform, long timestamp) {
        super(id, dbStatus);
        this.account = account;
        this.accountName = accountName;
        this.password = password;
        this.platform = platform;
        this.timestamp = timestamp;
    }

    public static class Builder extends AbstractBuilder {
        private String account;

        private String accountName;

        private String password;

        private String platform;

        private long timestamp;

        public Builder setAccount(String account) {
            this.account = account;
            return this;
        }

        public Builder setAccountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setPlatform(String platform) {
            this.platform = platform;
            return this;
        }

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public User build() {
            return new User(getId(), getDbStatus(), account, accountName, password, platform, timestamp);
        }
    }

    public String getAccount() {
        return account;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getPassword() {
        return password;
    }

    public String getPlatform() {
        return platform;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
