package application.module.scene.trigger.condition.wrap;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public interface TriggerConditionWrap {

    int getId();

    String getConditionClassName();

    void parse(String s);

}
