package application.module.fight.attribute.provider;

import application.util.UpdateAttributeObject;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public interface AttributeProvider {

    /**
     * 计算当前参数获得的属性
     */
    Map<Short, Long> provider(UpdateAttributeObject<?> o);

    /**
     * 计算当前参数和上一级参数的属性差值
     */
    Map<Short, Long> subProvider(UpdateAttributeObject<?> o);

}
