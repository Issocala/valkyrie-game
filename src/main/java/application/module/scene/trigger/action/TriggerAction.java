package application.module.scene.trigger.action;

import application.module.scene.trigger.context.TriggerContext;
import application.module.scene.trigger.event.TriggerEvent;
import application.module.scene.trigger.action.wrap.TriggerActionWrap;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public interface TriggerAction<T extends TriggerActionWrap> {

    T getTriggerActionWrap();

    void doAction(TriggerContext context, TriggerEvent<?> event);

    void destroy(TriggerContext context);

}
