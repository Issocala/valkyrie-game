package application.condition;

public interface Condition<T> {

    int eligibleTo(T t);

    boolean isEmpty();

}
