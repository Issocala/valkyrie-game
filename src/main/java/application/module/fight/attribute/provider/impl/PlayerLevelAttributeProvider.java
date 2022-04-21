package application.module.fight.attribute.provider.impl;

import application.module.fight.attribute.provider.AttributeProvider;
import application.util.UpdateAttributeObject;

import java.util.HashMap;
import java.util.Map;

import static application.module.fight.attribute.AttributeTemplateId.*;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public class PlayerLevelAttributeProvider implements AttributeProvider {

    @Override
    public Map<Short, Long> provider(UpdateAttributeObject<?> o) {
        Map<Short, Long> map = new HashMap<>();
        map.put(MAX_HP, 1000L);
        map.put(MAX_MP, 1000L);
        map.put(VAR_HP, 1000L);
        map.put(VAR_MP, 1000L);
        map.put(ATTACK, 200L);
        map.put(ATTACK_DEFENCE, 100L);
        map.put(ATTACK_PIERCE, 100L);
        return map;
    }
}
