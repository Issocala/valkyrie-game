package application.module.state;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.player.data.PlayerEntityData;
import application.module.player.data.message.PlayerLogin;
import com.cala.orm.message.SubscribeEvent;
import mobius.modular.module.api.AbstractModule;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class StateModule extends AbstractModule {

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
            this.playerEntityData.tell(new SubscribeEvent(PlayerLogin.class, self()), self());
        }
    }
}
