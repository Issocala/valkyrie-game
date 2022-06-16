package application.module.scene.trigger.action;

import application.module.scene.trigger.action.wrap.StartTimerActionWrap;
import application.module.scene.trigger.action.wrap.TriggerActionWrap;

/**
 * @author Luo Yong
 * @date 2022-6-13
 * @Source 1.0
 */

public abstract class AbstractTimerTriggerAction extends AbstractTriggerAction<StartTimerActionWrap> {


    public AbstractTimerTriggerAction(TriggerActionWrap actionWrap) {
        super(actionWrap);
    }


}
