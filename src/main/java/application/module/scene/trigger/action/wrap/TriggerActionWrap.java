package application.module.scene.trigger.action.wrap;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public interface TriggerActionWrap {

    int getId();

    String getActionClassName();

    void parse(String s);
}
