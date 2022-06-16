package application.module.scene.trigger.action;

import application.module.scene.trigger.action.wrap.TriggerActionWrap;
import application.module.scene.trigger.context.TriggerContext;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public abstract class AbstractTriggerAction<T extends TriggerActionWrap> implements TriggerAction<T> {

    private final T actionWrap;

    @SuppressWarnings("unchecked")
    public AbstractTriggerAction(TriggerActionWrap actionWrap) {
        this.actionWrap = (T) actionWrap;
    }

    @Override
    public T getTriggerActionWrap() {
        return this.actionWrap;
    }

    @Override
    public void destroy(TriggerContext context) {
    }
}
