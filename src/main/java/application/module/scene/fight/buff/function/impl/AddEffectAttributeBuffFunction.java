package application.module.scene.fight.buff.function.impl;

import application.module.organism.FightOrganism;
import application.module.scene.fight.buff.FightOrganismBuff;
import application.module.scene.fight.buff.function.FightOrganismBuffFunction;
import application.util.AttributeMapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public class AddEffectAttributeBuffFunction extends FightOrganismBuffFunction {
    @Override
    public boolean addBuffFunction(FightOrganism from, FightOrganism to, FightOrganismBuff buff) {
        Map<Short, Long> map = new HashMap<>();
        AttributeMapUtil.add(map, buff.getAttributeMap());
        AttributeMapUtil.add(buff.getCountAttributeMap(), buff.getAttributeMap());
        to.getFightAttributeMgr().addFightMap(map);
        return true;
    }

    @Override
    public boolean removeBuffFunction(FightOrganism from, FightOrganism to, FightOrganismBuff buff) {
        Map<Short, Long> map = new HashMap<>();
        AttributeMapUtil.add(map, buff.getCountAttributeMap());
        to.getFightAttributeMgr().subFightMap(map);
        return true;
    }

}
