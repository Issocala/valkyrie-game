package application.module.fight.attribute.provider;

import com.cala.orm.util.ClassScanningUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-2-17
 * @Source 1.0
 */
public class AttributeRegister {
    private final static Map<String, AttributeProvider> register = new HashMap<>();

    public static void register() {
        Set<Class<?>> set = ClassScanningUtil.findClassBySuper(AttributeRegister.class.getPackageName(), AttributeProvider.class);
        set.forEach(e -> {
            try {
                if (e == AttributeProvider.class) {
                    register.put(e.getSimpleName(), (AttributeProvider) e.getDeclaredConstructor().newInstance());
                }
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static AttributeProvider getFightAttributeProvider(String name) {
        return register.get(name);
    }
}
