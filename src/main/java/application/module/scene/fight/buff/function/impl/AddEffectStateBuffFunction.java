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
public class AddEffectStateBuffFunction extends FightOrganismBuffFunction {


    @Override
    public boolean addBuffFunction(FightOrganism from, FightOrganism to, FightOrganismBuff buff) {
        FightBuffTemplate fightBuffTemplate = buff.getFightBuffTemplate();
        int[] stateTypes = StringUtils.toIntArray(fightBuffTemplate.parameter());
        short stateType = (short) stateTypes[0];
        Map<Short, Long> map = new HashMap<>();
        AttributeMapUtil.add(map, buff.getAttributeMap());
        AttributeMapUtil.add(buff.getCountAttributeMap(), buff.getAttributeMap());
//        if (map.size() > 0) {
//            buff.getAttributeData().tell(new UpdateFightAttribute(map, buff.getTo(),
//                    buff.getScene(), buff.getStateData()), self());
//        }
//        buff.getStateData().tell(new OrganismChangeState(buff.getTo(), (byte) 1,
//                stateType, buff.getScene()), self());
        return true;
    }

    @Override
    public boolean removeBuffFunction(FightOrganism from, FightOrganism to, FightOrganismBuff buff) {
        FightBuffTemplate fightBuffTemplate = buff.getFightBuffTemplate();
        int[] stateTypes = StringUtils.toIntArray(fightBuffTemplate.parameter());
        short stateType = (short) stateTypes[0];
        Map<Short, Long> map = new HashMap<>();
        AttributeMapUtil.sub(map, buff.getAttributeMap());
        AttributeMapUtil.sub(buff.getCountAttributeMap(), buff.getAttributeMap());
//        if (map.size() > 0) {
//            fightOrganismBuff.getAttributeData().tell(new UpdateFightAttribute(map, fightOrganismBuff.getTo(),
//                    fightOrganismBuff.getScene(), fightOrganismBuff.getStateData()), self());
//        }
//        fightOrganismBuff.getStateData().tell(new OrganismCancelState(fightOrganismBuff.getTo(), (byte) 1,
//                stateType, fightOrganismBuff.getScene()), self());
        return true;
    }

}
