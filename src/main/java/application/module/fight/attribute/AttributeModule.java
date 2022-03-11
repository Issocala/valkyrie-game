package application.module.fight.attribute;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.fight.attribute.data.AttributeData;
import application.module.fight.attribute.data.message.AttributeUpdateFightAttribute;
import application.module.fight.attribute.node.AttributeNode;
import application.module.fight.attribute.node.AttributeNodeManager;
import application.module.fight.attribute.provider.AttributeRegister;
import application.module.scene.data.SceneData;
import application.util.AttributeTemplateIdContainer;
import application.util.UpdateAttributeObject;
import com.cala.orm.message.DataReturnMessage;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-2-14
 * @Source 1.0
 */
public class AttributeModule extends AbstractModule {

    private ActorRef fightAttributeData;

    private ActorRef sceneData;

    record UpdateAttribute(long playerId, Short type, boolean isInit, UpdateAttributeObject<?> o) {
    }

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
                .match(UpdateAttribute.class, this::updateAttribute)
                .build();
    }

    private void updateAttribute(UpdateAttribute updateAttribute) {
        long playerId = updateAttribute.playerId;
        AttributeNode attributeNode = AttributeNodeManager.getFightAttribute(playerId, updateAttribute.type);
        if (Objects.isNull(attributeNode)) {
            return;
        }
        Set<Short> isUpdateTypeSet = attributeNode.update(updateAttribute.isInit, updateAttribute.o);
        if (Objects.isNull(isUpdateTypeSet)) {
            return;
        }
        //处理战力计算
        isUpdateTypeSet.forEach(s -> {
            AttributeNode attributeNode1 = AttributeNodeManager.getFightAttribute(playerId, s);
            Map<Short, Long> map = attributeNode1.id2AllFightAttributeMap();
            //百分比属性及一级属性转换二级属性计算
            Map<Short, Long> result = AttributeTemplateIdContainer.finalResult(map);
            //父节点==null代表为根节点，根节点更新需要通知战斗属性变化
            if (Objects.isNull(attributeNode1.fatherNode())) {
                sceneData.tell(new AttributeUpdateFightAttribute(result, playerId), self());
            }

            //TODO 计算战力值，保存到缓存中，并持久化。
            long fighting = calculateFighting(result);
        });
    }

    private void receivedFromClient(Client.ReceivedFromClient r) {

    }

    private void dataResultMessage(DataReturnMessage dataReturnMessage) {

    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == AttributeData.class) {
            this.fightAttributeData = dataResult.actorRef();
            getContext().system().eventStream().subscribe(fightAttributeData, UpdateAttribute.class);
        } else if (dataResult.clazz() == SceneData.class) {
            this.sceneData = dataResult.actorRef();
        }
    }


    public static long calculateFighting(Map<Short, Long> result) {
        return 0;
    }
}
