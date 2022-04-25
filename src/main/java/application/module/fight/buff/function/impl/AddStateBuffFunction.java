package application.module.fight.buff.function.impl;

import akka.actor.Props;
import application.module.fight.buff.data.domain.FightOrganismBuff;
import application.module.fight.buff.function.FightOrganismBuffFunction;
import application.module.fight.buff.function.message.AddBuffFunction;
import application.module.fight.buff.function.message.RemoveBuffFunction;
import application.module.state.operate.OrganismCancelState;
import application.module.state.operate.OrganismChangeState;
import application.util.StringUtils;
import template.FightBuffTemplate;

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
        short stateType = (short) stateTypes[1];
        fightOrganismBuff.getStateData().tell(new OrganismCancelState(fightOrganismBuff.getToId(), (byte) 1,
                stateType, fightOrganismBuff.getScene()), self());
    }

    @Override
    protected void addBuffFunction(AddBuffFunction addBuff) {
        FightOrganismBuff fightOrganismBuff = addBuff.fightOrganismBuff();
        FightBuffTemplate fightBuffTemplate = fightOrganismBuff.getFightBuffTemplate();
        int[] stateTypes = StringUtils.toIntArray(fightBuffTemplate.parameter());
        short stateType = (short) stateTypes[1];
        fightOrganismBuff.getStateData().tell(new OrganismChangeState(fightOrganismBuff.getToId(), (byte) 1,
                stateType, fightOrganismBuff.getScene()), self());
    }
}
