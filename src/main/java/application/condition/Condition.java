package application.condition;

import java.util.Collection;
import java.util.List;

public interface Condition<T> {
	int eligibleTo(T t);

    boolean isEmpty();

	Collection<List<ConditionItem<T>>> getConditions();
}
