package application.module.scene.operate;

import akka.actor.ActorRef;
import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2022-5-10
 * @Source 1.0
 */
public record PlayerEntrySuccessOpt(long playerId, int sceneId, ActorRef sceneActor) implements OperateType {
}
