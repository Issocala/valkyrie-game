package application.module.fight.buff.function.impl;

import akka.actor.Props;
import application.module.fight.attribute.data.message.UpdateFightAttribute;
import application.module.fight.buff.data.domain.FightOrganismBuff;
import application.module.fight.buff.function.FightOrganismBuffFunction;
import application.module.fight.buff.function.message.AddBuffFunction;
import application.module.fight.buff.function.message.RemoveBuffFunction;
import application.module.state.operate.OrganismCancelState;
import application.module.state.operate.OrganismChangeState;
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
public class AddStateBuffFunction extends FightOrganismBuffFunction {
    public static Props create() {
        return Props.create(AddStateBuffFunction.class);
    }

    @Override
    protected void removeBuffFunction(RemoveBuffFunction removeBuffFunction) {
        FightOrganismBuff fightOrganismBuff = removeBuffFunction.fightOrganismBuff();
        FightBuffTemplate fightBuffTemplate = fightOrganismBuff.getFightBuffTemplate();
        int[] stateTypes = StringUtils.toIntArray(fightBuffTemplate.parameter());
        short stateType = (short) stateTypes[0];
        Map<Short, Long> map = new HashMap<>();
        if (fightBuffTemplate.attributeMap().length > 0) {
            AttributeMapUtil.sub(map, StringUtils.toAttributeMap(fightBuffTemplate.attributeMap()));
        }
        if (fightOrganismBuff.getAttributeMap() != null) {
            AttributeMapUtil.sub(map, fightOrganismBuff.getAttributeMap());
        }
        if (map.size() > 0) {
            fightOrganismBuff.getAttributeData().tell(new UpdateFightAttribute(map, fightOrganismBuff.getToId(),
                    fightOrganismBuff.getScene(), fightOrganismBuff.getStateData()), self());
        }
        fightOrganismBuff.getStateData().tell(new OrganismCancelState(fightOrganismBuff.getToId(), (byte) 1,
                stateType, fightOrganismBuff.getScene()), self());
    }

    @Override
    protected void addBuffFunction(AddBuffFunction addBuff) {
        FightOrganismBuff fightOrganismBuff = addBuff.fightOrganismBuff();
        FightBuffTemplate fightBuffTemplate = fightOrganismBuff.getFightBuffTemplate();
        int[] stateTypes = StringUtils.toIntArray(fightBuffTemplate.parameter());
        short stateType = (short) stateTypes[0];
        Map<Short, Long> map = new HashMap<>();
        if (fightBuffTemplate.attributeMap().length > 0) {
            AttributeMapUtil.add(map, StringUtils.toAttributeMap(fightBuffTemplate.attributeMap()));
        }
        if (fightOrganismBuff.getAttributeMap() != null) {
            AttributeMapUtil.add(map, fightOrganismBuff.getAttributeMap());
        }
        if (map.size() > 0) {
            fightOrganismBuff.getAttributeData().tell(new UpdateFightAttribute(map, fightOrganismBuff.getToId(),
                    fightOrganismBuff.getScene(), fightOrganismBuff.getStateData()), self());
        }
        fightOrganismBuff.getStateData().tell(new OrganismChangeState(fightOrganismBuff.getToId(), (byte) 1,
                stateType, fightOrganismBuff.getScene()), self());
    }
}
