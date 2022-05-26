package application.module.scene.fight.buff.function.impl;

import application.module.organism.FightOrganism;
import application.module.scene.fight.buff.FightOrganismBuff;
import application.module.scene.fight.buff.function.FightOrganismBuffFunction;
import application.util.AttributeMapUtil;
import application.util.StringUtils;
import template.FightBuffTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public class AddCoverAttributeBuffFunction extends FightOrganismBuffFunction {
    @Override
    public boolean addBuffFunction(FightOrganism from, FightOrganism to, FightOrganismBuff buff) {
        FightBuffTemplate fightBuffTemplate = buff.getFightBuffTemplate();
        Map<Short, Long> map = new HashMap<>();
        AttributeMapUtil.add(map, StringUtils.toAttributeMap(fightBuffTemplate.attributeMap()));
        if (buff.getCurrCoverCount() >= fightBuffTemplate.buffCoverCount()) {
            AttributeMapUtil.add(map, StringUtils.toAttributeMap(fightBuffTemplate.MaxCoverAttributeMap()));
        }
        to.getFightAttributeMgr().addFightMap(map);
        return true;
    }

    @Override
    public boolean removeBuffFunction(FightOrganism from, FightOrganism to, FightOrganismBuff buff) {
        FightBuffTemplate fightBuffTemplate = buff.getFightBuffTemplate();
        Map<Short, Long> map = new HashMap<>();
        AttributeMapUtil.sub(map, StringUtils.toAttributeMap(fightBuffTemplate.attributeMap()));
        if (buff.getCurrCoverCount() == fightBuffTemplate.buffCoverCount() - 1) {
            AttributeMapUtil.sub(map, StringUtils.toAttributeMap(fightBuffTemplate.MaxCoverAttributeMap()));
        }
        to.getFightAttributeMgr().addFightMap(map);
        return true;
    }

}
