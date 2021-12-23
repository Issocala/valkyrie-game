package application.module.example;

import akka.actor.Props;
import mobius.modular.module.api.AbstractModuleHolder;

import java.util.*;

/**
 * Created by RXL on 2021/12/1.
 * Maintainer:
 * RXL
 */
public class ExampleModuleHolder extends AbstractModuleHolder {

    /**
     * less lazy, considering use Guice in the future;
     *
     */
    private static final ExampleModuleHolder instance = new ExampleModuleHolder();

    public static ExampleModuleHolder getInstance() {
        return instance;
    }

    @Override
    public Props props() {
        return Props.create(ExampleModule.class);
    }


    @Override
    public List<Integer> getProtocols() {
        return Arrays.asList(4);
    }
}