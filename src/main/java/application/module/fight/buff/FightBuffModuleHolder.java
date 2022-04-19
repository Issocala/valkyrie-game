package application.module.fight.buff;

import akka.actor.Props;
import mobius.modular.module.api.AbstractModuleHolder;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-3-2
 * @Source 1.0
 */
public class FightBuffModuleHolder extends AbstractModuleHolder {

    private final static FightBuffModuleHolder INSTANCE = new FightBuffModuleHolder();

    public static FightBuffModuleHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public Props props() {
        return Props.create(FightBuffModule.class);
    }

    @Override
    public List<Integer> getProtocols() {
        return List.of(FightBuffProtocols.GET, FightBuffProtocols.ADD, FightBuffProtocols.REMOVE);
    }
}
