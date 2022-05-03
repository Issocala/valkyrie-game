package application.module.state;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.player.data.PlayerEntityData;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.data.message.event.PlayerRegister;
import application.module.scene.data.SceneData;
import application.module.scene.operate.event.CreateOrganismEntityAfter;
import application.module.scene.operate.event.CreatePlayerEntitiesAfter;
import application.module.scene.operate.event.PlayerReceiveAfter;
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
    private ActorRef sceneData;

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(PlayerEntityData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(StateData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(SceneData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(PlayerLogin.class, this::playerLogin)
                .match(PlayerRegister.class, this::playerRegister)
                .match(CreatePlayerEntitiesAfter.class, this::createPlayerEntitiesAfter)
                .match(CreateOrganismEntityAfter.class, this::createOrganismEntityAfter)
                .match(PlayerReceiveAfter.class, this::playerReceiveAfter)
                .build();
    }

    private void playerReceiveAfter(PlayerReceiveAfter playerReceiveAfter) {
        this.stateData.tell(playerReceiveAfter, self());
    }

    private void createOrganismEntityAfter(CreateOrganismEntityAfter createOrganismEntityAfter) {
        this.stateData.tell(createOrganismEntityAfter, self());
    }

    private void createPlayerEntitiesAfter(CreatePlayerEntitiesAfter createPlayerEntitiesAfter) {
        this.stateData.tell(createPlayerEntitiesAfter, self());
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
        }else if (dataResult.clazz() == SceneData.class) {
            this.sceneData = dataResult.actorRef();
            this.sceneData.tell(new SubscribeEvent(CreatePlayerEntitiesAfter.class, self()), self());
            this.sceneData.tell(new SubscribeEvent(CreateOrganismEntityAfter.class, self()), self());
            this.sceneData.tell(new SubscribeEvent(PlayerReceiveAfter.class, self()), self());
        }
    }
}
