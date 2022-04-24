package application.module.scene.operate.event;

import akka.actor.ActorRef;
import application.module.organism.Organism;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.Event;

import java.util.List;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-4-13
 * @Source 1.0
 */
public record CreatePlayerEntitiesAfter(Map<Long, ActorRef> clientMap, long playerId,
                                        List<Organism> organisms) implements DataBase, Event {
}
