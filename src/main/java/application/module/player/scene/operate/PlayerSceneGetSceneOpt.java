package application.module.player.scene.operate;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2022-5-18
 * @Source 1.0
 */
public record PlayerSceneGetSceneOpt(int sceneTemplateId, ActorRef scene) implements OperateType, DataBase {
}
