package application.module.scene.trigger.condition;

import application.module.scene.trigger.condition.wrap.TriggerConditionWrap;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public abstract class AbstractTriggerCondition<T extends TriggerConditionWrap> implements TriggerCondition<T> {

    private final T conditionWrap;

    private boolean completed = false;

    @SuppressWarnings("unchecked")
    public AbstractTriggerCondition(TriggerConditionWrap conditionWrap) {
        this.conditionWrap = (T) conditionWrap;
    }

    @Override
    public T getTriggerConditionWrap() {
        return this.conditionWrap;
    }

    @Override
    public boolean wasCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
