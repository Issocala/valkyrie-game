package application.module.scene.operate.event;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.Event;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-4-13
 * @Source 1.0
 */
public record CreateOrganismEntities(ActorRef client, List<Long> organismId, byte organismType) implements DataBase, Event {
}
