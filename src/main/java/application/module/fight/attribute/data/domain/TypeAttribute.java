package application.module.fight.attribute.data.domain;

import application.module.fight.attribute.AttributeTemplateIdContainer;
import application.module.fight.attribute.data.message.UpdateAttribute;
import application.module.fight.attribute.fight.FightAttributeMgr;
import application.module.fight.attribute.node.AttributeNode;
import application.module.fight.attribute.provider.AttributeRegister;
import application.util.AttributeMapUtil;
import template.AttributeTreeTemplate;
import template.AttributeTreeTemplateHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static application.module.fight.attribute.AttributeTemplateId.*;

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
        AttributeTemplateIdContainer.reducePublicExt(allTemplateAttributeMap, id2FightAttributeMap.keySet());
        AttributeMapUtil.add(allTemplateAttributeMap, id2FightAttributeMap);
        AttributeTemplateIdContainer.finalFatherResult(allTemplateAttributeMap, id2FightAttributeMap.keySet());

        addFightAttribute(id2FightAttributeMap);
        fightAttributeMap.put(VAR_HP, FightAttributeMgr.getValue(fightAttributeMap, MAX_HP));
        fightAttributeMap.put(VAR_MP, FightAttributeMgr.getValue(fightAttributeMap, VAR_MP));
    }

    public void addFightAttribute(Map<Short, Long> map) {
        AttributeTemplateIdContainer.reducePublicExt(fightAttributeMap, map.keySet());
        AttributeMapUtil.add(fightAttributeMap, map);
        AttributeTemplateIdContainer.finalFatherResult(fightAttributeMap, map.keySet());
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
