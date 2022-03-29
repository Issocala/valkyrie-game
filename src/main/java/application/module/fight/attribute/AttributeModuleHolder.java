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

    private static final AttributeModuleHolder ATTRIBUTE_MODULE_HOLDER = new AttributeModuleHolder();

    public static AttributeModuleHolder getAttributeModuleHolder() {
        return ATTRIBUTE_MODULE_HOLDER;
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
