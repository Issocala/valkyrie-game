package application.module.fight.attribute.provider;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public interface FightAttributeProvider {

    Map<Short, Long> provider(Object o);

    /**
     * 计算当前参数和上一级参数的属性差值
     */
    Map<Short, Long> subProvider(Object o);

}
