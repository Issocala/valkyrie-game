package application.module.fight.attribute.provider;

import application.util.UpdateAttributeObject;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public interface AttributeProvider {

    Map<Short, Long> provider(UpdateAttributeObject<?> o);

}
