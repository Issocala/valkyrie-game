package application.module.player.fight.attribute.data.message;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-3-9
 * @Source 1.0
 */
public record UpdateFightAttribute(Map<Short, Long> result, long organismId, byte organismType) implements DataBase {
}
