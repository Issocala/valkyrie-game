package application.module.scene.trigger.condition;

import application.module.scene.trigger.context.TriggerContext;
import application.module.scene.trigger.condition.wrap.TriggerConditionWrap;
import application.module.scene.trigger.event.TriggerEvent;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public interface TriggerCondition<T extends TriggerConditionWrap> {

    T getTriggerConditionWrap();

    boolean doTriggerEvent(TriggerEvent<?> event, TriggerContext context);

    boolean wasCompleted();

}
