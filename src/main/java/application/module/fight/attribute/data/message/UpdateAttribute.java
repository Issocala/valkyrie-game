package application.module.fight.attribute.data.message;

import akka.actor.ActorRef;
import application.util.UpdateAttributeObject;
import com.cala.orm.message.DataBase;

import java.util.Collection;

/**
 * @author Luo Yong
 * @date 2022-3-9
 * @Source 1.0
 */
public record UpdateAttribute(long playerId, Short type, UpdateAttributeObject<?> o, Collection<ActorRef> clients) implements DataBase {
}
