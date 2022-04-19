package application.module.fight.skill.base.function;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.fight.skill.base.function.passive.FightPassiveSkillFunction;
import com.cala.orm.util.ClassScanningUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class FightSkillFunctionContainer {

    private final static Map<String, ActorRef> map = new HashMap<>();

    private static final String PROPS_CREATE_METHOD = "create";

    private static final Map<String, FightPassiveSkillFunction> fightSkillPassiveFunctionMap = new HashMap<>();

    public static ActorRef getFunction(String name) {
        return map.get(name);
    }

    public static void add(String name, ActorRef fightSkillFunction) {
        map.put(name, fightSkillFunction);
    }

    public static void add(String name, FightPassiveSkillFunction function) {
        fightSkillPassiveFunctionMap.put(name, function);
    }

    public static FightPassiveSkillFunction getPassiveFunction(String name) {
        return fightSkillPassiveFunctionMap.get(name);
    }

    public static void registerPassive(ActorContext actorContext) {
        Set<Class<?>> set = ClassScanningUtil.findClassBySuper(FightSkillActiveFunction.class.getPackageName(), FightSkillActiveFunction.class);
        set.forEach(clazz -> {
            Method method;
            ActorRef actorRef;
            try {
                method = clazz.getMethod(PROPS_CREATE_METHOD);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException(clazz + "并未创建create actor方法,方法名为" + PROPS_CREATE_METHOD);
            }
            try {
                actorRef = actorContext.actorOf((Props) method.invoke(PROPS_CREATE_METHOD), clazz.getName());
                add(clazz.getSimpleName(), actorRef);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        registerPassive();
    }

    public static void registerPassive() {
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
