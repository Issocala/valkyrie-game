package application.condition.util;

import application.condition.ConditionItem;
import application.condition.core.ConditionBase;
import application.condition.core.ConditionContext;
import application.util.ArrayUtils;
import application.util.DebugUtil;
import application.util.StringUtils;
import com.cala.orm.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class ConditionParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionParser.class.getSimpleName());

    public static ConditionItem<ConditionContext> parseConditionItem(Pair<Short, String> itemData) {

        short type = itemData.getFirst();

        if (ConditionItemMgr.getInstance().containType(type)) {
            final Class<?> clazz = ConditionItemMgr.getInstance().getConditionItemImplClazz(type);
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

    public static ConditionBase parseCondition(String[] conditionDatas) {
        if (ArrayUtils.isEmpty(conditionDatas)) {
            return ConditionBase.getInstance();
        }
        ConditionBase condition = ConditionBase.of();
        for (String conditionData : conditionDatas) {
            String[] ss1 = StringUtils.toStringArrayByAnd(conditionData);
            ConditionBase.ConditionItemWrap<ConditionContext> conditionItems = new ConditionBase.ConditionItemWrap<>();
            for (String s : ss1) {
                String[] ss = StringUtils.toStringArrayByColon(s);
                if (ss.length != 2) {
                    throw new RuntimeException("condition条件解析格式有问题 == " + Arrays.toString(conditionDatas));
                }
                Pair<Short, String> pair = new Pair<>(Short.parseShort(ss[0]), ss[1]);
                ConditionItem<ConditionContext> conditionItem = parseConditionItem(pair);
                if (conditionItem != null) {
                    conditionItems.addConditionItem(conditionItem);
                }
            }
            condition.addConditionItemWrap(conditionItems);
        }
        return condition;
    }
}
