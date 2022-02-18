package application.module.fight.attribute;

import akka.actor.Props;
import mobius.modular.module.api.AbstractModuleHolder;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public class AttributeModuleHolder extends AbstractModuleHolder {

    private static final AttributeModule ATTRIBUTE_MODULE = new AttributeModule();

    public static AttributeModule getAttributeModule() {
        return ATTRIBUTE_MODULE;
    }

    @Override
    public Props props() {
        return Props.create(AttributeModule.class);
    }

    @Override
    public List<Integer> getProtocols() {
        return List.of();
    }
}
