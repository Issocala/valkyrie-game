package application.condition.util;

import application.condition.Condition;
import application.condition.annotation.ConditionImpl;
import application.condition.core.ConditionItemContext;
import com.cala.orm.util.ClassScanningUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ConditionItemMgr {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionItemMgr.class.getSimpleName());

    private final Map<Short, Class<?>> clazzMap = new HashMap<>();

    private final static ConditionItemMgr instance = new ConditionItemMgr();

    private ConditionItemMgr() {
    }

    public static ConditionItemMgr getInstance() {
        return instance;
    }

    public boolean containType(short type) {
        return clazzMap.containsKey(type);
    }

    public Class<?> getConditionItemImplClazz(short type) {
        return clazzMap.get(type);
    }

    public void register() {
        final Set<Class<?>> classes = ClassScanningUtil.findClassBySuper(Condition.class.getPackageName() + ".impl",
                ConditionItemContext.class);
        registerConditionItem(classes);
    }

    private void registerConditionItem(Set<Class<?>> classes) {
        classes.forEach(clazz -> {
            for (Class<?> target : clazz.getInterfaces()) {
                if (target == ConditionItemContext.class) {
                    final ConditionImpl annotation = clazz.getAnnotation(ConditionImpl.class);
                    Objects.requireNonNull(annotation, clazz.getPackageName() + "缺失ConditionImpl注解");
                    short type = annotation.id();
                    if (clazzMap.containsKey(type)) {
                        LOGGER.error("class:{} 重复类型: {}" + clazz.getSimpleName(), type);
                        continue;
                    }
                    clazzMap.put(type, clazz);
                    break;
                }
            }
        });
    }

}
