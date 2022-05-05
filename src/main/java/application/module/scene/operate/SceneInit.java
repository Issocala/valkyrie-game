package application.module.scene.operate;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public record SceneInit(ActorRef stateData, ActorRef buffData, ActorRef attributeData) implements DataBase {
}
