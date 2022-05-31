package application.module.player.util;

import application.module.player.data.entity.PlayerEntity;

/**
 * @author Luo Yong
 * @date 2022-5-31
 * @Source 1.0
 */
public record PlayerCreateEntityInfo(PlayerEntity playerEntity) {
    public static PlayerCreateEntityInfo of (PlayerEntity entity) {
        return new PlayerCreateEntityInfo(entity);
    }

}
