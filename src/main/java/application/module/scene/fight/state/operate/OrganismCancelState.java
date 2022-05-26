package application.module.scene.fight.state.operate;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-4-21
 * @Source 1.0
 */
public record OrganismCancelState(long organismId, byte organismType, short stateType, ActorRef scene) implements DataBase {
}
