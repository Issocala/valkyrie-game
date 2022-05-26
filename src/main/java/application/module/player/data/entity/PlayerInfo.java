package application.module.player.data.entity;

/**
 * @author Luo Yong
 * @date 2021-11-29
 * @Source 1.0
 */
public record PlayerInfo(long id, String name, int level, byte gender, byte profession, long lastLoginTime) {
}
