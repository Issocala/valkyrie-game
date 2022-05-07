package application.condition;

/**
 * @author Luo Yong
 * @date 2022-5-6
 * @Source 1.0
 */
public interface Condition {

    boolean doValid(ConditionContext conditionContext);

    void parse(String[] ss);

}
