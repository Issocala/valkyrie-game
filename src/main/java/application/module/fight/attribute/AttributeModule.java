package application.module.fight.attribute;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.fight.attribute.data.AttributeData;
import application.module.fight.attribute.provider.AttributeRegister;
import application.module.player.data.PlayerEntityData;
import application.module.player.data.message.event.PlayerLogin;
import application.module.scene.data.SceneData;
import application.module.scene.operate.PlayerReceive;
import application.module.scene.operate.event.CreateOrganismEntityAfter;
import application.module.scene.operate.event.CreatePlayerEntitiesAfter;
import application.module.scene.operate.event.PlayerReceiveAfter;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.SubscribeEvent;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;

/**
 * @author Luo Yong
 * @date 2022-2-14
 * @Source 1.0
 */
public class AttributeModule extends AbstractModule {

    private ActorRef attributeData;

    private ActorRef playerEntityData;

    private ActorRef sceneData;

    @Override
    public void initData() {
        AttributeRegister.register();
        this.dataAgent().tell(new DataMessage.RequestData(AttributeData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(PlayerEntityData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(SceneData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Client.ReceivedFromClient.class, this::receivedFromClient)
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .match(PlayerLogin.class, this::playerLogin)
                .match(CreatePlayerEntitiesAfter.class, this::createPlayerEntitiesAfter)
                .match(CreateOrganismEntityAfter.class, this::createOrganismEntityAfter)
                .match(PlayerReceiveAfter.class, this::playerReceiveAfter)
                .build();
    }

    private void playerReceiveAfter(PlayerReceiveAfter playerReceiveAfter) {
        this.attributeData.tell(playerReceiveAfter, self());
    }

    private void createOrganismEntityAfter(CreateOrganismEntityAfter createOrganismEntityAfter) {
        this.attributeData.tell(createOrganismEntityAfter, self());
    }

    private void createPlayerEntitiesAfter(CreatePlayerEntitiesAfter createPlayerEntitiesAfter) {
        this.attributeData.tell(createPlayerEntitiesAfter, self());
    }

    private void playerLogin(PlayerLogin playerLogin) {
        attributeData.tell(playerLogin, self());
    }


    private void receivedFromClient(Client.ReceivedFromClient r) {

    }

    private void dataResultMessage(DataReturnMessage dataReturnMessage) {

    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == AttributeData.class) {
            this.attributeData = dataResult.actorRef();
        } else if (dataResult.clazz() == PlayerEntityData.class) {
            this.playerEntityData = dataResult.actorRef();
            this.playerEntityData.tell(new SubscribeEvent(PlayerLogin.class, self()), self());
        }else if (dataResult.clazz() == SceneData.class) {
            this.sceneData = dataResult.actorRef();
            this.sceneData.tell(new SubscribeEvent(CreatePlayerEntitiesAfter.class, self()), self());
            this.sceneData.tell(new SubscribeEvent(CreateOrganismEntityAfter.class, self()), self());
            this.sceneData.tell(new SubscribeEvent(PlayerReceiveAfter.class, self()), self());
        }
    }
}
