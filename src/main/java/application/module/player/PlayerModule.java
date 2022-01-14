package application.module.player;

import mobius.modular.module.api.AbstractModule;

/**
 * @author Luo Yong
 * @date 2022-1-14
 * @Source 1.0
 */
public class PlayerModule extends AbstractModule {



    @Override
    public void initData() {

    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
