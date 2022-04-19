package application.module.player;

import akka.actor.Props;
import mobius.modular.module.api.AbstractModuleHolder;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-1-14
 * @Source 1.0
 */
public class PlayerModuleHolder extends AbstractModuleHolder {

    private static final PlayerModuleHolder INSTANCE = new PlayerModuleHolder();

    public static PlayerModuleHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public Props props() {
        return Props.create(PlayerModule.class);
    }

    @Override
    public List<Integer> getProtocols() {
        return List.of(PlayerProtocols.GETAll, PlayerProtocols.CREATE, PlayerProtocols.LOGIN, PlayerProtocols.DELETE,
                PlayerProtocols.LOGOUT);
    }
}
