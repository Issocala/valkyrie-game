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
public class AddCoverStateBuffFunction extends FightOrganismBuffFunction {


    @Override
    public boolean addBuffFunction(FightOrganism from, FightOrganism to, FightOrganismBuff buff) {
        FightBuffTemplate fightBuffTemplate = buff.getFightBuffTemplate();
        int[] stateTypes = StringUtils.toIntArray(fightBuffTemplate.parameter());
        short stateType = (short) stateTypes[0];
        Map<Short, Long> map = new HashMap<>();
        if (fightBuffTemplate.attributeMap().length > 0) {
            AttributeMapUtil.add(map, StringUtils.toAttributeMap(fightBuffTemplate.attributeMap()));
        }
        if (map.size() > 0) {
           to.getFightAttributeMgr().addFightMap(map);
        }
        to.getFightStateMgr().changeState(stateType, to.getScene());
        return true;
    }

    @Override
    public boolean removeBuffFunction(FightOrganism from, FightOrganism to, FightOrganismBuff buff) {
        FightBuffTemplate fightBuffTemplate = buff.getFightBuffTemplate();
        int[] stateTypes = StringUtils.toIntArray(fightBuffTemplate.parameter());
        short stateType = (short) stateTypes[0];
        Map<Short, Long> map = new HashMap<>();
        if (fightBuffTemplate.attributeMap().length > 0) {
            AttributeMapUtil.sub(map, StringUtils.toAttributeMap(fightBuffTemplate.attributeMap()));
        }
        if (map.size() > 0) {
            to.getFightAttributeMgr().subFightMap(map);
        }
        to.getFightStateMgr().cancelState(stateType, to.getScene());
        return true;
    }

}
