package application.module.common;

import akka.actor.ActorRef;
import application.module.common.data.entity.DataMessage;
import application.module.player.data.PlayerEntityData;
import application.module.player.data.message.event.PlayerLogin;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.SubscribeEvent;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;

/**
 * @author Luo Yong
 * @date 2022-3-28
 * @Source 1.0
 */
public class CommonModule extends AbstractModule {

    private ActorRef playerEntity;

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(PlayerEntityData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Client.ReceivedFromClient.class, this::receivedFromClient)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(PlayerLogin.class, this::playerLogin)
                .build();
    }

    private void playerLogin(PlayerLogin playerLogin) {
        playerLogin.r().client().tell(CommonProtocolBuilder.getSc10081(), self());
    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == PlayerEntityData.class) {
            this.playerEntity = dataResult.actorRef();
            this.playerEntity.tell(new SubscribeEvent(PlayerLogin.class, self()), self());
        }
    }

    private void dataResultMessage(DataReturnMessage dataReturnMessage) {

    }

    private void receivedFromClient(Client.ReceivedFromClient r) {

    }
}
