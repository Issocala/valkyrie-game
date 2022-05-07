package application.condition;

import application.condition.annotation.ConditionId;
import com.cala.orm.util.ClassScanningUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-5-6
 * @Source 1.0
 */
public class ConditionContainer {

    private final static Map<Short, Class<?>> map = new HashMap<>();

    public static void register() {
        Set<Class<?>> set = ClassScanningUtil.findClassBySuper(Condition.class.getPackageName(), Condition.class);
        set.forEach(e -> {

            final ConditionId annotation = e.getAnnotation(ConditionId.class);
            if (annotation != null) {
                map.put(annotation.id(), e);
            }

        });
    }

    public static Class<?> getConditionClass(short id) {
        return map.get(id);
    }

    public static Condition parseCondition(short id, String[] ss) {
        Class<?> c = getConditionClass(id);
        try {
            Condition condition = (Condition) c.getDeclaredConstructor().newInstance();
            condition.parse(ss);
            return condition;
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
