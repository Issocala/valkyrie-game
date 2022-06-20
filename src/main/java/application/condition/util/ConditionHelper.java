package application.condition.util;

import application.condition.Condition;
import application.condition.core.ConditionContext;
import application.util.ApplicationCode;
import com.cala.orm.util.RuntimeResult;

import java.util.Objects;

public class ConditionHelper {

    public static RuntimeResult eligibleTo(Condition<ConditionContext> condition, ConditionContext context) {
        if (condition != null) {
            int code = condition.eligibleTo(context);
            if (code != ApplicationCode.CODE_SUCCESS) {
                return RuntimeResult.runtimeApplicationError(code);
            }
        }
        return RuntimeResult.ok();
    }

    public static RuntimeResult eligibleTo(Condition<ConditionContext> condition, Object... objects) {
        Objects.requireNonNull(condition, "输入的condition为空！");
        if (condition.isEmpty()) {
            return RuntimeResult.ok();
        }
        ConditionContext context = ConditionContext.of();
        for (Object o : objects) {
            context.put(o.getClass(), o);
        }
        int code = condition.eligibleTo(context);
        if (code != ApplicationCode.CODE_SUCCESS) {
            return RuntimeResult.runtimeApplicationError(code);
        }
        return RuntimeResult.ok();
    }

}
