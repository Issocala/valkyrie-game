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
public class FightAttributeRegister {
    private final static Map<String, FightAttributeProvider> register = new HashMap<>();

    public static void register() {
        Set<Class<?>> set = ClassScanningUtil.findClassBySuper(FightAttributeRegister.class.getPackageName(), FightAttributeProvider.class);
        set.forEach(e -> {
            try {
                if (e == FightAttributeProvider.class) {
                    register.put(e.getSimpleName(), (FightAttributeProvider) e.getDeclaredConstructor().newInstance());
                }
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static FightAttributeProvider getFightAttributeProvider(String name) {
        return register.get(name);
    }
}
