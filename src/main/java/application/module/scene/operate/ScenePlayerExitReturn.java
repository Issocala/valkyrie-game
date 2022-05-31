package application.module.scene.operate;

import application.util.Vector3;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-4-19
 * @Source 1.0
 */
public record ScenePlayerExitReturn(long playerId, int sceneTemplateId, Vector3 vector3) implements DataBase {
}
