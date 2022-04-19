package application.module.fight.buff.function.impl;

import akka.actor.Props;
import application.module.fight.buff.function.FightOrganismBuffFunction;
import application.module.fight.buff.function.message.AddBuffFunction;
import application.module.fight.buff.function.message.TickBuffFunction;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public class AttributeTypeDamageBuffFunction extends FightOrganismBuffFunction {
    public static Props create() {
        return Props.create(AttributeTypeDamageBuffFunction.class);
    }

    @Override
    protected void tickBuffFunction(TickBuffFunction tickBuffFunction) {
        System.out.println("我是sb tick" + System.currentTimeMillis());

    }

    @Override
    protected void addBuffFunction(AddBuffFunction addBuff) {
        System.out.println("我是sb add" + System.currentTimeMillis());
    }
}
