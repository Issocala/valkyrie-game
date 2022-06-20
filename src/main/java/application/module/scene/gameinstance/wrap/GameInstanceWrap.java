package application.module.scene.gameinstance.wrap;

import application.condition.core.ConditionBase;
import application.module.scene.trigger.entrance.TriggerWrapContainer;
import template.GameInstanceTemplate;

/**
 * @author Luo Yong
 * @date 2022-6-10
 * @Source 1.0
 */
public class GameInstanceWrap {

    private GameInstanceTemplate template;

    /**
     * 副本进入条件
     */
    private ConditionBase enterCondition;

    /**
     * 副本失败条件
     */
    private ConditionBase failedCondition;

    /**
     * 副本触发器
     */
    private TriggerWrapContainer triggerWrapContainer;

    public static GameInstanceWrap of() {
        return new GameInstanceWrap();
    }

    //get and set

    public GameInstanceTemplate getTemplate() {
        return template;
    }

    public void setTemplate(GameInstanceTemplate template) {
        this.template = template;
    }

    public ConditionBase getEnterCondition() {
        return enterCondition;
    }

    public void setEnterCondition(ConditionBase enterCondition) {
        this.enterCondition = enterCondition;
    }

    public ConditionBase getFailedCondition() {
        return failedCondition;
    }

    public void setFailedCondition(ConditionBase failedCondition) {
        this.failedCondition = failedCondition;
    }

    public TriggerWrapContainer getGamePlayerTriggerContainer() {
        return triggerWrapContainer;
    }

    public void setGamePlayerTriggerContainer(TriggerWrapContainer triggerWrapContainer) {
        this.triggerWrapContainer = triggerWrapContainer;
    }
}
