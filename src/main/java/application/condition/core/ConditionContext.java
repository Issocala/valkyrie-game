package application.condition.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-5-6
 * @Source 1.0
 */
public class ConditionContext {

    private final Map<String, Object> stringObjectMap = new HashMap<>();

    public static ConditionContext of() {
        return new ConditionContext();
    }

    public void put(String key, Object value) {
        this.stringObjectMap.put(key, value);
    }

    public Object get(String key) {
        return this.stringObjectMap.get(key);
    }

    public boolean containsKey(String name) {
        return stringObjectMap.containsKey(name);
    }
}
