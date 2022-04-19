package application.module.fight.skill;

import akka.actor.Props;
import mobius.modular.module.api.AbstractModuleHolder;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-2-18
 * @Source 1.0
 */
public class FightSkillModuleHolder extends AbstractModuleHolder {

    private final static FightSkillModuleHolder INSTANCE = new FightSkillModuleHolder();

    public static FightSkillModuleHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public Props props() {
        return Props.create(FightSkillModule.class);
    }

    @Override
    public List<Integer> getProtocols() {
        return List.of(FightSkillProtocols.GET_ALL, FightSkillProtocols.LEARN, FightSkillProtocols.USE, FightSkillProtocols.CANCEL_SKILL);
    }
}
