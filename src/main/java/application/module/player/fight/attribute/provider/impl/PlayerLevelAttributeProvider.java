package application.module.player.fight.attribute.provider.impl;

import application.module.player.Player;
import application.module.player.data.entity.PlayerInfo;
import application.module.player.fight.attribute.provider.AttributeProvider;
import application.util.AttributeMapUtil;
import application.util.StringUtils;
import template.OrganismDataTemplateHolder;

import java.util.HashMap;
import java.util.Map;

import static application.module.player.fight.attribute.AttributeTemplateId.ICE_MAGIC_SHIELD;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public class PlayerLevelAttributeProvider implements AttributeProvider {

    @Override
    public Map<Short, Long> provider(Player player) {
        PlayerInfo playerInfo = player.getPlayerEntity().getPlayerInfo();
        Map<Short, Long> map = new HashMap<>();
        if (playerInfo.profession() == 2) {
            map.put(ICE_MAGIC_SHIELD, 1L);
        }
        Map<Short, Long> addMap = StringUtils.toAttributeMap(OrganismDataTemplateHolder.getData(playerInfo.profession()).attributeMap());
        AttributeMapUtil.add(map, addMap);
        return map;
    }
}
