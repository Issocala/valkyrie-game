package application.module.player.data.domain;

/**
 * @author Luo Yong
 * @date 2021-11-29
 * @Source 1.0
 */
public class PlayerInfo {

    private long id;

    private String name;

    private int level;

    private byte gender;

    private byte profession;

    private long lastLoginTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public byte getProfession() {
        return profession;
    }

    public void setProfession(byte profession) {
        this.profession = profession;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
