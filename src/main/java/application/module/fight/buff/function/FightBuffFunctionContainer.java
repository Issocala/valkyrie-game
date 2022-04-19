package application.module.fight.buff.function;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.cala.orm.util.ClassScanningUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public class FightBuffFunctionContainer {

    private final static Map<String, ActorRef> buffFunctionMap = new HashMap<>();
    private static final String PROPS_CREATE_METHOD = "create";

    public static void register(ActorContext actorContext) {
        Set<Class<?>> set = ClassScanningUtil.findClassBySuper(FightOrganismBuffFunction.class.getPackageName(), FightOrganismBuffFunction.class);
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
                buffFunctionMap.put(clazz.getSimpleName(), actorRef);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public static ActorRef getBuffFunction(String name) {
        return buffFunctionMap.get(name);
    }

}
