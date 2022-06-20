package application.condition.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-5-6
 * @Source 1.0
 */
public class ConditionContext {

    private final Map<Class<?>, Object> classObjectMap = new HashMap<>();

    public static ConditionContext of() {
        return new ConditionContext();
    }

    public void put(Class<?> key, Object value) {
        this.classObjectMap.put(key, value);
    }

    public Object get(Class<?> key) {
        return this.classObjectMap.get(key);
    }

    public boolean containsKey(Class<?> name) {
        return classObjectMap.containsKey(name);
    }
}
