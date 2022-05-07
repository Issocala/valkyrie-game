package application.condition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-5-6
 * @Source 1.0
 */
public class ConditionContext {

    private final Map<String, Object> stringObjectMap = new HashMap<>();

    public void put(String key, Object value) {
        this.stringObjectMap.put(key, value);
    }

    public Object get(String key) {
        return this.stringObjectMap.get(key);
    }

}
