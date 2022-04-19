package application.module.gm;

import akka.actor.Props;
import mobius.modular.module.api.AbstractModuleHolder;

import java.util.List;

/**
 * @author HRT
 * @date 2022-4-11
 */
public class GmModuleHolder extends AbstractModuleHolder {

    private static final GmModuleHolder INSTANCE = new GmModuleHolder();

    public static GmModuleHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public Props props() {
        return Props.create(GmModule.class);
    }

    @Override
    public List<Integer> getProtocols() {
        return List.of(GmProtocols.CMD);
    }

}
