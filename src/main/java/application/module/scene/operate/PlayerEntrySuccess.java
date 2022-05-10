package application.module.scene.operate;

import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-5-10
 * @Source 1.0
 */
public record PlayerEntrySuccess(long playerId, int sceneId) implements DataBase {
}
