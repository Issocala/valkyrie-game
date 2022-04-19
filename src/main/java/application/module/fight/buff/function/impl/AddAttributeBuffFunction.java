package application.module.fight.buff.function.impl;

import akka.actor.Props;
import application.module.fight.buff.function.FightOrganismBuffFunction;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public class AddAttributeBuffFunction extends FightOrganismBuffFunction {
    public static Props create() {
        return Props.create(AddAttributeBuffFunction.class);
    }
}
