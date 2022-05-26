package application.module.scene;

import akka.actor.Props;
import mobius.modular.module.api.AbstractModuleHolder;
import protocol.Scene;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-3-7
 * @Source 1.0
 */
public class SceneModuleHolder extends AbstractModuleHolder {

    private final static SceneModuleHolder INSTANCE = new SceneModuleHolder();

    public static SceneModuleHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public Props props() {
        return Props.create(SceneModule.class);
    }

    @Override
    public List<Integer> getProtocols() {
        return List.of();
    }
}
