package application.module.fight.attribute;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.fight.attribute.data.AttributeData;
import application.module.fight.attribute.node.AttributeNode;
import application.module.fight.attribute.node.AttributeNodeManager;
import application.module.fight.attribute.provider.AttributeRegister;
import com.cala.orm.message.DataReturnMessage;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;

import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-2-14
 * @Source 1.0
 */
public class AttributeModule extends AbstractModule {

    private ActorRef fightAttributeData;

    record UpdateAttribute(long playerId, Short type, boolean isInit, Object o) {
    }

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(AttributeData.class), self());
        AttributeRegister.register();
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Client.ReceivedFromClient.class, this::receivedFromClient)
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .match(UpdateAttribute.class, this::updateAttribute)
                .build();
    }

    private void updateAttribute(UpdateAttribute updateAttribute) {
        long playerId = updateAttribute.playerId;
        AttributeNode attributeNode = AttributeNodeManager.getFightAttribute(playerId, updateAttribute.type);
        if (Objects.isNull(attributeNode)) {
            return;
        }
        attributeNode.update(playerId, updateAttribute.isInit, updateAttribute.o);
    }

    private void receivedFromClient(Client.ReceivedFromClient r) {

    }

    private void dataResultMessage(DataReturnMessage dataReturnMessage) {

    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == AttributeData.class) {
            this.fightAttributeData = dataResult.actorRef();
            getContext().system().eventStream().subscribe(fightAttributeData, UpdateAttribute.class);
        }
    }
}
