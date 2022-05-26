package application.module.scene.operate;

import application.module.scene.data.entity.PositionInfo;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-4-19
 * @Source 1.0
 */
public record ScenePlayerExitReturn(long playerId, int sceneTemplateId, PositionInfo positionInfo) implements DataBase {
}
