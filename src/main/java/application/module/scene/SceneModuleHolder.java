package application.module.scene;

import akka.actor.Props;
import mobius.modular.module.api.AbstractModuleHolder;

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
        return List.of(SceneProtocols.SCENE_ENTER, SceneProtocols.SCENE_EXIT, SceneProtocols.SCENE_MOVE, SceneProtocols.SCENE_STOP,
                SceneProtocols.SCENE_JUMP, SceneProtocols.SCENE_FLASH, SceneProtocols.AI, SceneProtocols.HELP_USE_SKILL,
                SceneProtocols.RECEIVE);
    }
}
