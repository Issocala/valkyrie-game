package application.module.player;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.player.base.data.PlayerEntityData;
import mobius.modular.module.api.AbstractModule;

/**
 * @author Luo Yong
 * @date 2022-1-14
 * @Source 1.0
 */
public class PlayerModule extends AbstractModule {

    private ActorRef playerEntityData;

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(PlayerEntityData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DataMessage.DataResult.class, this::dataResult)
                .build();
    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == PlayerEntityData.class) {
            this.playerEntityData = dataResult.actorRef();
        }
    }
}
