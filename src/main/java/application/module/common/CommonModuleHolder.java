package application.module.common;

import akka.actor.Props;
import mobius.modular.module.api.AbstractModuleHolder;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-3-28
 * @Source 1.0
 */
public class CommonModuleHolder extends AbstractModuleHolder {

    private static final CommonModuleHolder INSTANCE = new CommonModuleHolder();

    public static CommonModuleHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public Props props() {
        return Props.create(CommonModule.class);
    }

    @Override
    public List<Integer> getProtocols() {
        return List.of(CommonProtocols.SERVER_TIME, CommonProtocols.TICK);
    }
}
