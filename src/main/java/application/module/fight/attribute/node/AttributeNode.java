package application.module.fight.attribute.node;

import application.module.fight.attribute.provider.AttributeProvider;
import application.util.AttributeMapUtil;
import com.google.common.base.Preconditions;

import java.util.*;

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

    public void update(long playerId, boolean isInit, Object o) {
        Set<Short> isUpdateTypeSet = new HashSet<>();
        Preconditions.checkNotNull(attributeProvider, "战力提供函数为空,策划配置函数名和代码函数名不一致！！！");
        Map<Short, Long> id2FightAttributeMap;
        if (isInit) {
            id2FightAttributeMap = attributeProvider.provider(o);
        } else {
            id2FightAttributeMap = attributeProvider.subProvider(o);
        }
        //属性并未变化
        if (Objects.isNull(id2FightAttributeMap) || id2FightAttributeMap.isEmpty()) {
            return;
        }
        AttributeMapUtil.add(id2AllFightAttributeMap, id2FightAttributeMap);
        //缓存有更新的模块
        isUpdateTypeSet.add(typeId);
        if (Objects.isNull(fatherNode)) {
            return;
        }
        //当前节点属性变化，父节点属性也会变化
        fatherNode.addId2AllFightAttributeMap(id2FightAttributeMap, isUpdateTypeSet);
        //初始化不计算，战力读缓存
        if (isInit) {
            return;
        }
        //处理战力计算
        isUpdateTypeSet.forEach(s -> {
            AttributeNode attributeNode = AttributeNodeManager.getFightAttribute(playerId, typeId);
            Map<Short, Long> map = attributeNode.id2AllFightAttributeMap;
            //TODO 百分比属性及一级属性转换二级属性计算
            //TODO 计算战力值，保存到缓存中，并持久化。

        });

    }

    /**
     * 新增属性至属性集合
     */
    public void addId2AllFightAttributeMap(Map<Short, Long> id2FightAttributeMap, Set<Short> isUpdateTypeSet) {
        AttributeMapUtil.add(id2AllFightAttributeMap, id2FightAttributeMap);
        //缓存有更新的模块
        isUpdateTypeSet.add(typeId);
        if (Objects.isNull(fatherNode)) {
            return;
        }
        //当前节点属性变化，父节点属性也会变化
        fatherNode.addId2AllFightAttributeMap(id2FightAttributeMap, isUpdateTypeSet);
    }

    public void addChildList(AttributeNode attributeNode) {
        this.childList.add(attributeNode);
    }
}
