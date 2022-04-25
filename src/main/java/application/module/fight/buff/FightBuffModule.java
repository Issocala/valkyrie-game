package application.module.fight.buff;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.fight.buff.data.FightBuffData;
import application.module.fight.buff.function.FightBuffFunctionContainer;
import mobius.modular.module.api.AbstractModule;

/**
 * @author Luo Yong
 * @date 2022-3-2
 * @Source 1.0
 */
public class FightBuffModule extends AbstractModule {

    private ActorRef fightBuffData;

    @Override
    public void initData() {
        FightBuffFunctionContainer.register(getContext());
        this.dataAgent().tell(new DataMessage.RequestData(FightBuffData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DataMessage.DataResult.class, this::dataResult)
                .build();
    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == FightBuffData.class) {
            fightBuffData = dataResult.actorRef();
        }
    }
}