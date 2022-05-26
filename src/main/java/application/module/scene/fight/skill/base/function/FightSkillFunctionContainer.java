package application.module.scene.fight.skill.base.function;

import akka.actor.ActorContext;
import application.module.scene.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.scene.fight.skill.base.function.passive.FightPassiveSkillFunction;
import com.cala.orm.util.ClassScanningUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class FightSkillFunctionContainer {

    private final static Map<String, FightSkillActiveFunction> map = new HashMap<>();

    private static final String PROPS_CREATE_METHOD = "create";

    private static final Map<String, FightPassiveSkillFunction> fightSkillPassiveFunctionMap = new HashMap<>();

    public static FightSkillActiveFunction getFunction(String name) {
        return map.get(name);
    }

    public static void add(String name, FightSkillActiveFunction fightSkillFunction) {
        map.put(name, fightSkillFunction);
    }

    public static void add(String name, FightPassiveSkillFunction function) {
        fightSkillPassiveFunctionMap.put(name, function);
    }

    public static FightPassiveSkillFunction getPassiveFunction(String name) {
        return fightSkillPassiveFunctionMap.get(name);
    }

    public static void registerSkillAndPassive() {
        Set<Class<?>> set = ClassScanningUtil.findClassBySuper(FightSkillActiveFunction.class.getPackageName(), FightSkillActiveFunction.class);
        set.forEach(e -> {
            try {
                add(e.getSimpleName(), (FightSkillActiveFunction) e.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
        registerPassive();
    }

    private static void registerPassive() {
        Set<Class<?>> set = ClassScanningUtil.findClassBySuper(FightPassiveSkillFunction.class.getPackageName(), FightPassiveSkillFunction.class);
        set.forEach(e -> {
            try {
                add(e.getSimpleName(), (FightPassiveSkillFunction) e.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
    }

}
