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
    @Override
    public Props props() {
        return null;
    }

    @Override
    public List<Integer> getProtocols() {
        return null;
    }
}
