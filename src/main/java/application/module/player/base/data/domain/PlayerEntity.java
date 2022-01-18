package application.module.player.base.data.domain;

import application.util.AbstractBuilder;
import com.cala.orm.annotation.DbDeserialize;
import com.cala.orm.annotation.DbPojoBuilder;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DbStatus;
import com.cala.orm.converter.Convert;
import com.cala.orm.converter.JsonAttributeConverter;

import javax.persistence.Column;

/**
 * @author Luo Yong
 * @date 2021-11-15
 * @Source 1.0
 */
@DbDeserialize(builder = PlayerEntity.Builder.class)
public class PlayerEntity extends AbstractEntityBase {

    /**
     * 角色名字
     */
    @Column
    private final String name;

    /**
     * 职业
     */
    @Column
    private final byte profession;

    /**
     * 账号id
     */
    @Column
    private final long accountId;

    /**
     * 账号名字
     */
    @Column
    private final String accountName;

    @Column
    private long fightPower;

    @Column
    private final int level;

    @Column
    private final byte vipLevel;

    @Column
    private final byte gender;

    @Column
    private final long lastLoginTime;

    @Column
    private final long lastLogoutTime;

    @Column
    @Convert(converter = JsonAttributeConverter.class)
    private final PlayerInfo playerInfo;

    @Column
    @Convert(converter = JsonAttributeConverter.class)
    private final Person person;

    public PlayerEntity(Long id, String name, byte profession, long accountId, String accountName, int level, byte vipLevel, byte gender, long lastLoginTime, long lastLogoutTime, DbStatus dbStatus, PlayerInfo playerInfo, Person person) {
        super(id, dbStatus);
        this.name = name;
        this.profession = profession;
        this.accountId = accountId;
        this.accountName = accountName;
        this.level = level;
        this.vipLevel = vipLevel;
        this.gender = gender;
        this.lastLoginTime = lastLoginTime;
        this.lastLogoutTime = lastLogoutTime;
        this.playerInfo = playerInfo;
        this.person = person;
    }

    public PlayerEntity(Long id, String name, byte profession, long accountId, String accountName, long fightPower, int level, byte vipLevel, byte gender, long lastLoginTime, long lastLogoutTime, DbStatus dbStatus, PlayerInfo playerInfo, Person person) {
        super(id, dbStatus);
        this.name = name;
        this.profession = profession;
        this.accountId = accountId;
        this.accountName = accountName;
        this.fightPower = fightPower;
        this.level = level;
        this.vipLevel = vipLevel;
        this.gender = gender;
        this.lastLoginTime = lastLoginTime;
        this.lastLogoutTime = lastLogoutTime;
        this.playerInfo = playerInfo;
        this.person = person;
    }

    @DbPojoBuilder
    public static class Builder extends AbstractBuilder {
        private String name;
        private byte profession;
        private long accountId;
        private String accountName;
        private long fightPower;
        private int level;
        private byte vipLevel;
        private byte gender;
        private long lastLoginTime;
        private long lastLogoutTime;
        private PlayerInfo playerInfo;
        private Person person;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPlayerInfo(PlayerInfo playerInfo) {
            this.playerInfo = playerInfo;
            return this;
        }

        public Builder setPerson(Person person) {
            this.person = person;
            return this;
        }

        public Builder setProfession(byte profession) {
            this.profession = profession;
            return this;
        }

        public Builder setAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder setAccountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public Builder setFightPower(long fightPower) {
            this.fightPower = fightPower;
            return this;
        }

        public Builder setLevel(int level) {
            this.level = level;
            return this;
        }

        public Builder setVipLevel(byte vipLevel) {
            this.vipLevel = vipLevel;
            return this;
        }

        public Builder setGender(byte gender) {
            this.gender = gender;
            return this;
        }

        public Builder setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
            return this;
        }

        public Builder setLastLogoutTime(long lastLogoutTime) {
            this.lastLogoutTime = lastLogoutTime;
            return this;
        }

        public PlayerEntity build() {
            return new PlayerEntity(getId(), name, profession, accountId, accountName, level, vipLevel, gender, lastLoginTime, lastLogoutTime, getDbStatus(), playerInfo, person);
        }
    }

    public static PlayerEntity of(Long id) {
        return new PlayerEntity(id, null, (byte) 0, 0, null, 0,
                (byte)0, (byte)0, 0L, 0, DbStatus.NORMAL, null, null);
    }

    public String getName() {
        return name;
    }

    public byte getProfession() {
        return profession;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public long getFightPower() {
        return fightPower;
    }

    public int getLevel() {
        return level;
    }

    public byte getVipLevel() {
        return vipLevel;
    }

    public byte getGender() {
        return gender;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public long getLastLogoutTime() {
        return lastLogoutTime;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return "PlayerEntity{" +
                "name='" + name + '\'' +
                ", profession=" + profession +
                ", accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", fightPower=" + fightPower +
                ", level=" + level +
                ", vipLevel=" + vipLevel +
                ", gender=" + gender +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLogoutTime=" + lastLogoutTime +
                ", playerInfo=" + playerInfo +
                ", person=" + person +
                '}';
    }
}
