package application.module.scene.trigger.condition;

import application.module.scene.trigger.condition.wrap.TriggerConditionWrap;
import application.util.DebugUtil;
import com.cala.orm.util.ClassScanningUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-6-13
 * @Source 1.0
 */
public class TriggerConditionContainer {

    private static final Map<String, Class<?>> map = new HashMap<>();

    public static void register() {
        Set<Class<?>> triggerActionClazzs = ClassScanningUtil.findClassBySuper(TriggerConditionContainer.class.getPackageName(), AbstractTriggerCondition.class);
        triggerActionClazzs.forEach(clazz -> {
            String className = clazz.getSimpleName();
            if (map.containsKey(className)) {
                throw new RuntimeException("Already exist such class : " + className);
            }
            map.put(className, clazz);
        });
    }

    @SuppressWarnings("unchecked")
    public static <T extends TriggerCondition<?>> T create(String className, TriggerConditionWrap conditionWrap) throws RuntimeException {
        Class<?> clazz = map.get(className);
        Objects.requireNonNull(clazz, className + " nonexistent");
        try {
            return (T) clazz.getDeclaredConstructor(TriggerConditionWrap.class).newInstance(conditionWrap);
        } catch (Exception e) {
            throw new RuntimeException(DebugUtil.printStack(e));
        }
    }

    public static Class<?> getClass(String className) {
        return map.get(className);
    }
}
