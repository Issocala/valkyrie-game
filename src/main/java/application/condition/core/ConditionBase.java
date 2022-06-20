package application.condition.core;

import application.condition.Condition;
import application.condition.ConditionItem;
import application.util.ApplicationCode;
import application.util.ApplicationErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class ConditionBase implements Condition<ConditionContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionBase.class.getSimpleName());

    private final List<ConditionItemWrap<ConditionContext>> itemList = new ArrayList<>();

    /**
     * 缓存一个空对象，其他地方可以复用，避免多次创建无用对象浪费空间
     */
    private static final ConditionBase INSTANCE = new ConditionBase();

    public static ConditionBase of() {
        return new ConditionBase();
    }

    @Override
    public int eligibleTo(ConditionContext context) {
        int code = ApplicationErrorCode.CODE_ERROR;
        for (ConditionItemWrap<ConditionContext> conditionItemWrap : itemList) {
            for (ConditionItem<ConditionContext> conditionItem : conditionItemWrap.itemList) {
                try {
                    code = conditionItem.eligibleTo(context);
                    if (code != ApplicationCode.CODE_SUCCESS) {
                        break;
                    }
                } catch (Exception e) {
                    LOGGER.error("condition {} error", conditionItem.getClass().getSimpleName());
                    return -1;
                }
            }
            if (code == ApplicationCode.CODE_SUCCESS) {
                return ApplicationCode.CODE_SUCCESS;
            }
        }
        return code;
    }

    public void addConditionItemWrap(ConditionItemWrap<ConditionContext> conditionItemWrap) {
        itemList.add(conditionItemWrap);
    }

    @Override
    public boolean isEmpty() {
        return itemList.isEmpty();
    }

    public static ConditionBase getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "ConditionBase [items=" + itemList + "]";
    }

    public static class ConditionItemWrap<T> {
        private final List<ConditionItem<T>> itemList = new ArrayList<>();

        public List<ConditionItem<T>> getItemList() {
            return itemList;
        }

        public void addConditionItem(ConditionItem<T> conditionItem) {
            this.itemList.add(conditionItem);
        }
    }
}
