package application.module.fight.buff.function.impl;

import akka.actor.Props;
import application.module.fight.attribute.data.message.UpdateFightAttribute;
import application.module.fight.buff.data.domain.FightOrganismBuff;
import application.module.fight.buff.function.FightOrganismBuffFunction;
import application.module.fight.buff.function.message.AddBuffFunction;
import application.module.fight.buff.function.message.RemoveBuffFunction;
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
public class AddAttributeBuffFunction extends FightOrganismBuffFunction {
    public static Props create() {
        return Props.create(AddAttributeBuffFunction.class);
    }

    @Override
    protected void removeBuffFunction(RemoveBuffFunction removeBuffFunction) {
        FightOrganismBuff fightOrganismBuff = removeBuffFunction.fightOrganismBuff();
        FightBuffTemplate fightBuffTemplate = fightOrganismBuff.getFightBuffTemplate();
        Map<Short, Long> map = new HashMap<>();
        AttributeMapUtil.sub(map, StringUtils.toAttributeMap(fightBuffTemplate.attributeMap()));
        if (fightOrganismBuff.getCurrCoverCount() == fightBuffTemplate.buffCoverCount() - 1) {
            AttributeMapUtil.sub(map, StringUtils.toAttributeMap(fightBuffTemplate.MaxCoverAttributeMap()));
        }
        fightOrganismBuff.getAttributeData().tell(new UpdateFightAttribute(map, fightOrganismBuff.getToId(), fightOrganismBuff.getScene()), self());
    }

    @Override
    protected void addBuffFunction(AddBuffFunction addBuff) {
        FightOrganismBuff fightOrganismBuff = addBuff.fightOrganismBuff();
        FightBuffTemplate fightBuffTemplate = fightOrganismBuff.getFightBuffTemplate();
        Map<Short, Long> map = new HashMap<>();
        AttributeMapUtil.add(map, StringUtils.toAttributeMap(fightBuffTemplate.attributeMap()));
        if (fightOrganismBuff.getCurrCoverCount() >= fightBuffTemplate.buffCoverCount()) {
            AttributeMapUtil.add(map, StringUtils.toAttributeMap(fightBuffTemplate.MaxCoverAttributeMap()));
        }
        fightOrganismBuff.getAttributeData().tell(new UpdateFightAttribute(map, fightOrganismBuff.getToId(), fightOrganismBuff.getScene()), self());
    }
}
