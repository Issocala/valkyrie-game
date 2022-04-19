package application.module.state;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.player.data.PlayerEntityData;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.data.message.event.PlayerRegister;
import application.module.state.data.StateData;
import com.cala.orm.message.SubscribeEvent;
import mobius.modular.module.api.AbstractModule;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class StateModule extends AbstractModule {

    private ActorRef playerEntityData;
    private ActorRef stateData;

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(PlayerEntityData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(StateData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(PlayerLogin.class, this::playerLogin)
                .match(PlayerRegister.class, this::playerRegister)
                .build();
    }

    private void playerRegister(PlayerRegister playerRegister) {

    }

    private void playerLogin(PlayerLogin playerLogin) {
        this.stateData.tell(playerLogin, self());
    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == PlayerEntityData.class) {
            this.playerEntityData = dataResult.actorRef();
            this.playerEntityData.tell(new SubscribeEvent(PlayerLogin.class, self()), self());
            this.playerEntityData.tell(new SubscribeEvent(PlayerRegister.class, self()), self());
        }else if (dataResult.clazz() == StateData.class) {
            this.stateData = dataResult.actorRef();
        }
    }
}
