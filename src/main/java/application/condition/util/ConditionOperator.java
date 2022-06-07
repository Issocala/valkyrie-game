package application.condition.util;

import application.condition.Condition;
import application.condition.core.ConditionContext;
import application.util.ApplicationCode;
import com.cala.orm.util.RuntimeResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConditionOperator {

    public static RuntimeResult eligibleTo(Condition<ConditionContext> condition, ConditionContext context) {
        if (condition != null) {
            int code = condition.eligibleTo(context);
            if (code != ApplicationCode.CODE_SUCCESS) {
                return RuntimeResult.runtimeApplicationError(code);
            }
        }
        return RuntimeResult.ok();
    }


    public static RuntimeResult eligibleToOr(Condition<ConditionContext> condition, ConditionContext context) {
        if (condition != null) {
            int code = condition.eligibleToOr(context);
            if (code != ApplicationCode.CODE_SUCCESS) {
                return RuntimeResult.runtimeApplicationError(code);
            }
        }
        return RuntimeResult.ok();
    }
}
