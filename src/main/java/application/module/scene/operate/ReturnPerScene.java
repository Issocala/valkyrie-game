package application.module.scene.operate;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-5-10
 * @Source 1.0
 */
public record ReturnPerScene(long playerId, ActorRef client) implements DataBase {
}
