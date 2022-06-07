package application.condition;


public interface ConditionItem<T> {
	int eligibleTo(T t);

	void parser(String s);
}
