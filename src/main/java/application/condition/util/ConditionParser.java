package application.condition.util;

import application.condition.ConditionItem;
import application.condition.core.ConditionBase;
import application.condition.core.ConditionContext;
import application.util.DebugUtil;
import application.util.StringUtils;
import com.cala.orm.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public final class ConditionParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionParser.class.getSimpleName());

    public static ConditionItem<ConditionContext> parseConditionItem(Pair<Short, String> itemData) {

        short type = itemData.getFirst();

        if (ConditionItemMgr.getInstance().containType(type)) {
            final Class clazz = ConditionItemMgr.getInstance().getConditionItemImplClazz(type);
            try {
                @SuppressWarnings("unchecked")
                ConditionItem<ConditionContext> item = (ConditionItem<ConditionContext>) clazz.getDeclaredConstructor().newInstance();
                item.parser(itemData.getSecond());
                return item;
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                LOGGER.error("reflect condition error!!! type:{}", type);
                LOGGER.error("impl:{}", clazz.getSimpleName());
                LOGGER.error("call stack:{}", DebugUtil.printStack(e));
            }
        }
        return null;
    }

    public static ConditionBase parseCondition(String conditionData) {
        final ConditionBase condition = new ConditionBase();
        if (StringUtils.isEmpty(conditionData)) {
            return condition;
        }

        String[] ss = StringUtils.toStringArray(conditionData);
        for (int i = 0; i < ss.length; i += 2) {
            Pair<Short, String> pair = new Pair<>(Short.parseShort(ss[i]), ss[i + 1]);
            final ConditionItem<ConditionContext> conditionItem = parseConditionItem(pair);
            if (conditionItem != null) {
                condition.addConditionItem(conditionItem);
            }
        }
        return condition;
    }

    public static ConditionBase parseCondition(String[][] conditionData) {
        final ConditionBase condition = new ConditionBase();
        if (Objects.isNull(conditionData) || conditionData.length == 0) {
            return condition;
        }
        for (int i = 0; i < conditionData.length; i += 2) {
            Pair<Short, String> pair = new Pair<>(Short.parseShort(conditionData[i][0]), conditionData[i][1]);
            final ConditionItem<ConditionContext> conditionItem = parseConditionItem(pair);
            if (conditionItem != null) {
                condition.addConditionItem(conditionItem);
            }
        }
        return condition;
    }
}
