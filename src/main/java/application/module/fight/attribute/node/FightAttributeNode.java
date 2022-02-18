package application.module.fight.attribute.node;

import application.module.fight.attribute.provider.FightAttributeProvider;
import application.util.AttributeMapUtil;

import java.util.*;

/**
 * 属性处理逻辑为初始化获取当前节点属性参数，非初始化情况获取变化差值，
 * <p>
 * id2AllFightAttributeMap 当前节点加子节点属性集合
 *
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public record FightAttributeNode(short typeId, FightAttributeNode fatherNode, List<FightAttributeNode> childList,
                                 Map<Short, Long> id2AllFightAttributeMap,
                                 FightAttributeProvider fightAttributeProvider) {

    public final static Set<Short> isUpdateTypeSet = new HashSet<>();

    public void update(long playerId, boolean isInit, Object o) {
        isUpdateTypeSet.clear();
        Map<Short, Long> id2FightAttributeMap = new HashMap<>();
        if (Objects.nonNull(fightAttributeProvider)) {
            if (isInit) {
                id2AllFightAttributeMap.clear();
                id2FightAttributeMap = fightAttributeProvider.provider(o);

            } else {
                id2FightAttributeMap = fightAttributeProvider.subProvider(o);
            }
            //属性并未变化
            if (id2FightAttributeMap.isEmpty()) {
                return;
            }
            AttributeMapUtil.add(id2AllFightAttributeMap, id2FightAttributeMap);
            //缓存有更新的模块
            isUpdateTypeSet.add(typeId);
        }
        if (Objects.isNull(fatherNode)) {
            return;
        }
        //当前节点属性变化，父节点属性也会变化
        fatherNode.addId2AllFightAttributeMap(id2FightAttributeMap);
        //初始化不计算，战力读缓存
        if (isInit) {
            return;
        }
        //处理战力计算
        isUpdateTypeSet.forEach(s -> {
            FightAttributeNode fightAttributeNode = FightAttributeNodeManager.getFightAttribute(playerId, typeId);
            Map<Short, Long> map = fightAttributeNode.id2AllFightAttributeMap;
            //TODO 计算战力值，保存到缓存中，并持久化。

        });

    }

    /**
     * 新增属性至属性集合
     */
    public void addId2AllFightAttributeMap(Map<Short, Long> id2FightAttributeMap) {
        AttributeMapUtil.add(id2AllFightAttributeMap, id2FightAttributeMap);
        //缓存有更新的模块
        isUpdateTypeSet.add(typeId);
        if (Objects.isNull(fatherNode)) {
            return;
        }
        //当前节点属性变化，父节点属性也会变化
        fatherNode.addId2AllFightAttributeMap(id2FightAttributeMap);
    }

    public void addChildList(FightAttributeNode fightAttributeNode) {
        this.childList.add(fightAttributeNode);
    }
}
