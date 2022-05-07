package application.module.fight.attribute.provider.impl;

import application.module.fight.attribute.provider.AttributeProvider;
import application.module.player.data.domain.PlayerInfo;
import application.util.AttributeMapUtil;
import application.util.StringUtils;
import application.util.UpdateAttributeObject;
import template.OrganismDataTemplateHolder;

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
        PlayerInfo playerInfo = (PlayerInfo) o.t();
        Map<Short, Long> map = new HashMap<>();
        if (playerInfo.profession() == 2) {
            map.put(ICE_MAGIC_SHIELD, 1L);
        }
        Map<Short, Long> addMap = StringUtils.toAttributeMap(OrganismDataTemplateHolder.getData(playerInfo.profession()).attributeMap());
        AttributeMapUtil.add(map, addMap);
        return map;
    }
}
