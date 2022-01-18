package application.module.user;

import akka.actor.Props;
import mobius.modular.module.api.AbstractModuleHolder;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-1-17
 * @Source 1.0
 */
public class UserModuleHolder extends AbstractModuleHolder {

    private static final UserModuleHolder INSTANCE = new UserModuleHolder();

    public static UserModuleHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public Props props() {
        return Props.create(UserModule.class);
    }

    @Override
    public List<Integer> getProtocols() {
        return List.of(UserProtocols.REGISTER,UserProtocols.LOGIN);
    }
}
