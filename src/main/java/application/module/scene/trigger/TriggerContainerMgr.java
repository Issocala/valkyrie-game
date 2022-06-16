package application.module.scene.trigger;

import application.module.scene.trigger.action.TriggerActionContainer;
import application.module.scene.trigger.condition.TriggerConditionContainer;

/**
 * @author Luo Yong
 * @date 2022-6-13
 * @Source 1.0
 */
public class TriggerContainerMgr {

    public static void init() {
        TriggerActionContainer.register();
        TriggerConditionContainer.register();
    }

}
