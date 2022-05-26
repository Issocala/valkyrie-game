package application.module.scene.fight.buff.function;

import com.cala.orm.util.ClassScanningUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public class FightBuffFunctionContainer {

    private final static Map<String, FightOrganismBuffFunction> buffFunctionMap = new HashMap<>();

    public static void register() {
        Set<Class<?>> set = ClassScanningUtil.findClassBySuper(FightOrganismBuffFunction.class.getPackageName(), FightOrganismBuffFunction.class);
        set.forEach(clazz -> {
            try {
                add(clazz.getSimpleName(), (FightOrganismBuffFunction) clazz.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void add(String simpleName, FightOrganismBuffFunction newInstance) {
        buffFunctionMap.put(simpleName, newInstance);
    }

    public static FightOrganismBuffFunction getBuffFunction(String name) {
        return buffFunctionMap.get(name);
    }

}
