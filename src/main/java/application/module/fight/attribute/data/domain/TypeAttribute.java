package application.module.fight.attribute.data.domain;

import application.module.fight.attribute.data.message.UpdateAttribute;
import application.module.fight.attribute.node.AttributeNode;
import application.module.fight.attribute.provider.AttributeRegister;
import template.AttributeTreeTemplate;
import template.AttributeTreeTemplateHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static application.module.fight.attribute.data.AttributeData.doAddAttribute;

/**
 * @author Luo Yong
 * @date 2022-3-23
 * @Source 1.0
 */
public class TypeAttribute {

    private final Map<Short, AttributeNode> typeId2AttributeMap = new HashMap<>();

    private final Map<Short, Long> allTemplateAttributeMap = new HashMap<>();

    private final Map<Short, Long> fightAttributeMap = new HashMap<>();

    public void addAttribute(UpdateAttribute updateAttribute) {
        short type = updateAttribute.type();
        AttributeNode attributeNode = typeId2AttributeMap.get(type);
        if (Objects.isNull(attributeNode)) {
            AttributeTreeTemplate attributeTreeTemplate = AttributeTreeTemplateHolder.getData(type);
            attributeNode = new AttributeNode(type, new HashMap<>(), AttributeRegister.getFightAttributeProvider(attributeTreeTemplate.className()));
            typeId2AttributeMap.put(type, attributeNode);
        }
        Map<Short, Long> id2FightAttributeMap = attributeNode.update(updateAttribute.o());
        doAddAttribute(allTemplateAttributeMap, id2FightAttributeMap);
        addFightAttribute(id2FightAttributeMap);
    }

    public void addFightAttribute(Map<Short, Long> map) {
        doAddAttribute(fightAttributeMap, map);
    }

    public Map<Short, AttributeNode> getTypeId2AttributeMap() {
        return typeId2AttributeMap;
    }

    public Map<Short, Long> getAllTemplateAttributeMap() {
        return allTemplateAttributeMap;
    }

    public Map<Short, Long> getFightAttributeMap() {
        return fightAttributeMap;
    }
}
