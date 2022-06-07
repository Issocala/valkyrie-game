package application.condition.util;

import application.condition.Condition;
import application.condition.annotation.ConditionImpl;
import application.condition.core.ConditionItemBase;
import application.util.DebugUtil;
import com.cala.orm.util.ClassScanningUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class ConditionItemMgr {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionItemMgr.class.getSimpleName());

    private final Map<Short, Class> clazzMap = new HashMap<>();

    private final static ConditionItemMgr instance = new ConditionItemMgr();

    private ConditionItemMgr() {
    }

    public static ConditionItemMgr getInstance() {
        return instance;
    }

    public boolean containType(short type) {
        return clazzMap.containsKey(type);
    }

    public Class getConditionItemImplClazz(short type) {
        return clazzMap.get(type);
    }

    public void register() {
        final Set<Class<?>> classes = ClassScanningUtil.findFileClass(Condition.class.getPackageName() + "impl");
        registerConditionItem(classes);
    }

    private void registerConditionItem(Set<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            if (clazz.getSuperclass() == ConditionItemBase.class) {
                final ConditionImpl annotation = clazz.getAnnotation(ConditionImpl.class);
                if (annotation != null) {
                    short type = annotation.id();
                    if (clazzMap.containsKey(type)) {
                        LOGGER.error("class:{} 重复类型: {}" + clazz.getSimpleName(), type);
                        continue;
                    }
                    clazzMap.put(type, clazz);
                    LOGGER.debug("type:{} --- class:{} " + clazz.getSimpleName());
                } else {
                    try {
                        final Constructor<?> constructor = clazz.getConstructor();
                        final ConditionItemBase instance = (ConditionItemBase) constructor.newInstance();
                        try {
                            final Field field = ConditionItemBase.class.getDeclaredField("id");
                            field.setAccessible(true);
                            short type = field.getShort(instance);
                            if (clazzMap.containsKey(type)) {
                                LOGGER.error("class:{} 重复类型: {}" + clazz.getSimpleName(), type);
                                continue;
                            }
                            clazzMap.put(type, clazz);
                            LOGGER.debug("type:{} --- class:{} ", type, clazz.getSimpleName());
                        } catch (NoSuchFieldException e) {
                            LOGGER.error("反射找不到方法..{}", DebugUtil.printStack(e));
                        }
                    } catch (NoSuchMethodException e) {
                        LOGGER.info("{} 没有无参构造函数，请注意检查", clazz.getSimpleName());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        LOGGER.error("反射生成类出现异常....{}", DebugUtil.printStack(e));
                    }
                }
            }
        }
    }

}
