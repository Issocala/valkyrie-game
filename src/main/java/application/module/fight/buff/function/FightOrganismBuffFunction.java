package application.module.fight.buff.function;

import akka.actor.UntypedAbstractActor;
import application.module.fight.buff.function.message.AddBuffFunction;
import application.module.fight.buff.function.message.RemoveBuffFunction;
import application.module.fight.buff.function.message.TickBuffFunction;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public abstract class FightOrganismBuffFunction extends UntypedAbstractActor {

    @Override
    public void onReceive(Object message) {
        switch (message) {
            case AddBuffFunction addBuffFunction -> addBuffFunction(addBuffFunction);
            case RemoveBuffFunction removeBuffFunction -> removeBuffFunction(removeBuffFunction);
            case TickBuffFunction tickBuffFunction -> tickBuffFunction(tickBuffFunction);
            default -> receiveOther(message);
        }
    }

    protected void tickBuffFunction(TickBuffFunction tickBuffFunction) {
    }

    protected void removeBuffFunction(RemoveBuffFunction removeBuffFunction) {
    }

    protected void addBuffFunction(AddBuffFunction addBuff) {
    }

    protected void receiveOther(Object message) {
    }
}
