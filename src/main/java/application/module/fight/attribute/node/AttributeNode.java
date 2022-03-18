package application.module.fight.attribute.node;

import akka.actor.ActorRef;
import application.module.fight.attribute.AttributeTemplateIdContainer;
import application.module.fight.attribute.data.message.AttributeUpdateFightAttribute;
import application.module.fight.attribute.provider.AttributeProvider;
import application.util.AttributeMapUtil;
import application.util.UpdateAttributeObject;
import com.google.common.base.Preconditions;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static application.module.fight.attribute.data.AttributeData.doAddAttribute;

/**
 * <p>
 * 属性处理逻辑为初始化获取当前节点属性参数，非初始化情况获取变化差值，
 * id2AllFightAttributeMap 当前节点加子节点属性集合
 * isInit 是否已经初始化
 * </p>
 *
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public record AttributeNode(short typeId, AttributeNode fatherNode, List<AttributeNode> childList,
                            Map<Short, Long> id2AllFightAttributeMap,
                            AttributeProvider attributeProvider) {

    public void update(UpdateAttributeObject<?> o, long playerId, ActorRef sceneData) {
        Preconditions.checkNotNull(attributeProvider, "战力提供函数为空,策划配置函数名和代码函数名不一致！！！");
        Map<Short, Long> id2FightAttributeMap;

        id2FightAttributeMap = attributeProvider.subProvider(o);

        //属性并未变化
        if (Objects.isNull(id2FightAttributeMap) || id2FightAttributeMap.isEmpty()) {
            return;
        }
        Map<Short, Long> reduces = AttributeTemplateIdContainer.reducePartExt(id2AllFightAttributeMap, id2FightAttributeMap.keySet());
        AttributeMapUtil.add(id2AllFightAttributeMap, id2FightAttributeMap);
        Map<Short, Long> addExtMap = AttributeTemplateIdContainer.finalPartResult(id2AllFightAttributeMap, id2FightAttributeMap.keySet());
        //TODO 计算模块战力

        if (Objects.nonNull(fatherNode)) {
            Map<Short, Long> fatherId2AllFightAttributeMap = fatherNode.id2AllFightAttributeMap;
            AttributeMapUtil.sub(fatherId2AllFightAttributeMap, reduces);
            AttributeMapUtil.add(fatherId2AllFightAttributeMap, addExtMap);

            doAddAttribute(fatherId2AllFightAttributeMap, id2FightAttributeMap);
            sceneData.tell(new AttributeUpdateFightAttribute(fatherId2AllFightAttributeMap, playerId), null);
            //TODO 计算整体战力

        }
    }

    public void addChildList(AttributeNode attributeNode) {
        this.childList.add(attributeNode);
    }
}
