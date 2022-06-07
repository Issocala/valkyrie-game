package application.condition;

import java.util.Collection;

public interface Condition<T> {
	int eligibleTo(T t);

	int eligibleToOr(T t);

	void addConditionItem(ConditionItem<T> conditionItem);

    boolean isEmpty();

	Collection<ConditionItem<T>> getConditions();
}
