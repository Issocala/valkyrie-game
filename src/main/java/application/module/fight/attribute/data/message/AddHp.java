package application.module.fight.attribute.data.message;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-11
 * @Source 1.0
 */
public record AddHp(long organismId, byte organismType, Client.ReceivedFromClient r, long hp, long sourceId, byte sourceType, ActorRef scene, ActorRef stateData) implements DataBase {
}
