package application.module.fight.attribute.node;

import application.module.fight.attribute.provider.FightAttributeRegister;
import template.AttributeTreeTemplate;
import template.AttributeTreeTemplateHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public class FightAttributeNodeManager {

    private final static Map<Long, Map<Short, FightAttributeNode>> playerId2TypeId3FightAttributeMap = new HashMap<>();

    public static FightAttributeNode getFightAttribute(long playerId, short type) {
        Map<Short, FightAttributeNode> typeId2FightAttributeNodeMap = playerId2TypeId3FightAttributeMap.get(playerId);
        if (Objects.isNull(typeId2FightAttributeNodeMap)) {
            typeId2FightAttributeNodeMap = buildTree();
            playerId2TypeId3FightAttributeMap.put(playerId, typeId2FightAttributeNodeMap);
        }
        return typeId2FightAttributeNodeMap.get(type);
    }

    public static void addFightAttributeNode(Short type, FightAttributeNode fightAttributeNode, Map<Short, FightAttributeNode> typeId2FightAttributeNodeMap) {
        typeId2FightAttributeNodeMap.put(type, fightAttributeNode);
    }

    private static Map<Short, FightAttributeNode> buildTree() {
        Map<Short, FightAttributeNode> typeId2FightAttributeNodeMap = new HashMap<>();

        AttributeTreeTemplateHolder.getValues().forEach(e ->
                addFightAttributeNode((short) e.id(), buildNode(e, typeId2FightAttributeNodeMap), typeId2FightAttributeNodeMap));
        return typeId2FightAttributeNodeMap;
    }

    public static FightAttributeNode buildNode(AttributeTreeTemplate attributeTreeTemplate, Map<Short, FightAttributeNode> typeId2FightAttributeNodeMap) {
        FightAttributeNode fatherNode = null;
        int fatherNodeType = attributeTreeTemplate.fatherNodeId();
        if (fatherNodeType != 0) {
            fatherNode = typeId2FightAttributeNodeMap.get((short) fatherNodeType);
            //将当前节点添加到父节点的子节点集合中
            fatherNode.addChildList(fatherNode);
        }
        return new FightAttributeNode((short) attributeTreeTemplate.id(), fatherNode, new ArrayList<>(),
                new HashMap<>(), FightAttributeRegister.getFightAttributeProvider(attributeTreeTemplate.className()));
    }
}
