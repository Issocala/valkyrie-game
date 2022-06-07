package application.condition.core;

import application.condition.Condition;
import application.condition.ConditionItem;
import application.util.ApplicationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ConditionBase implements Condition<ConditionContext> {

    private static final Logger Logger = LoggerFactory.getLogger(ConditionBase.class.getSimpleName());

    private final List<ConditionItem<ConditionContext>> items = new ArrayList<>();

    @Override
    public int eligibleTo(ConditionContext context) {
        for (ConditionItem<ConditionContext> conditionItem : items) {
            try {
                int code = conditionItem.eligibleTo(context);
                if (code != ApplicationCode.CODE_SUCCESS) {
                    return code;
                }
            } catch (Exception e) {
                Logger.error("condition {} error", conditionItem.getClass().getSimpleName());
                return -1;
            }
        }
        return ApplicationCode.CODE_SUCCESS;
    }

    @Override
    public int eligibleToOr(ConditionContext context) {
        int error = -1;
        for (ConditionItem<ConditionContext> conditionItem : items) {
            int code = conditionItem.eligibleTo(context);
            if (Logger.isDebugEnabled()) {
            }
            if (code == ApplicationCode.CODE_SUCCESS) {
                return ApplicationCode.CODE_SUCCESS;
            }
            error = code;
        }
        return error;
    }

    public void addConditionItem(ConditionItem<ConditionContext> conditionItem) {
        items.add(conditionItem);
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<ConditionItem<ConditionContext>> getItems() {
        return items;
    }

    @Override
    public Collection<ConditionItem<ConditionContext>> getConditions() {
        return items;
    }

    @Override
    public String toString() {
        return "ConditionBase [items=" + items + "]";
    }
}
