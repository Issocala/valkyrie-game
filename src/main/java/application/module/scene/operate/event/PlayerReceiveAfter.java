package application.module.scene.operate.event;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.Event;
import mobius.modular.client.Client;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-4-21
 * @Source 1.0
 */
public record PlayerReceiveAfter(Map<Long, ActorRef> clientMap, long playerId, ActorRef scene) implements DataBase, Event {
}
