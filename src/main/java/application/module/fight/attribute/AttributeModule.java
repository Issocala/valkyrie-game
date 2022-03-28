package application.module.fight.attribute;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.fight.attribute.data.AttributeData;
import application.module.fight.attribute.provider.AttributeRegister;
import application.module.scene.data.SceneData;
import com.cala.orm.message.DataReturnMessage;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-2-14
 * @Source 1.0
 */
public class AttributeModule extends AbstractModule {

    private ActorRef fightAttributeData;

    private ActorRef sceneData;


    @Override
    public void initData() {
        AttributeRegister.register();
        this.dataAgent().tell(new DataMessage.RequestData(AttributeData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(SceneData.class), self());
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Client.ReceivedFromClient.class, this::receivedFromClient)
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .build();
    }


    private void receivedFromClient(Client.ReceivedFromClient r) {

    }

    private void dataResultMessage(DataReturnMessage dataReturnMessage) {

    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == AttributeData.class) {
            this.fightAttributeData = dataResult.actorRef();
        } else if (dataResult.clazz() == SceneData.class) {
            this.sceneData = dataResult.actorRef();
        }
    }


    public static long calculateFighting(Map<Short, Long> result) {
        return 0;
    }
}
