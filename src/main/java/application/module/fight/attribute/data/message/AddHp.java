package application.module.fight.attribute.data.message;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-11
 * @Source 1.0
 */
public record AddHp(long organismId, int organismType, Client.ReceivedFromClient r, long hp, long sourceId, ActorRef scene) implements DataBase {
}
