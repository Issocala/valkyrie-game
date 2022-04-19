package application.module.state;

import akka.actor.Props;
import mobius.modular.module.api.AbstractModuleHolder;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class StateModuleHolder extends AbstractModuleHolder {

    private static final StateModuleHolder INSTANCE = new StateModuleHolder();

    public static StateModuleHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public Props props() {
        return Props.create(StateModule.class);
    }

    @Override
    public List<Integer> getProtocols() {
        return List.of();
    }
}
