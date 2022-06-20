package application.condition.core;

import application.condition.Condition;
import application.condition.ConditionItem;
import application.util.ApplicationCode;
import application.util.ApplicationErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ConditionBase implements Condition<ConditionContext> {

    private static final Logger Logger = LoggerFactory.getLogger(ConditionBase.class.getSimpleName());

    private final List<List<ConditionItem<ConditionContext>>> itemList = new ArrayList<>();

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
        for (List<ConditionItem<ConditionContext>> conditionItems : itemList) {
            for (ConditionItem<ConditionContext> conditionItem : conditionItems) {
                try {
                    code = conditionItem.eligibleTo(context);
                    if (code != ApplicationCode.CODE_SUCCESS) {
                        break;
                    }
                } catch (Exception e) {
                    Logger.error("condition {} error", conditionItem.getClass().getSimpleName());
                    return -1;
                }
            }
            if (code == ApplicationCode.CODE_SUCCESS) {
                return ApplicationCode.CODE_SUCCESS;
            }
        }
        return code;
    }

    public void addConditionItem(List<ConditionItem<ConditionContext>> conditionItem) {
        itemList.add(conditionItem);
    }

    @Override
    public boolean isEmpty() {
        return itemList.isEmpty();
    }

    @Override
    public Collection<List<ConditionItem<ConditionContext>>> getConditions() {
        return itemList;
    }

    public static ConditionBase getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "ConditionBase [items=" + itemList + "]";
    }
}
