package application.module.scene.trigger;

import application.module.scene.trigger.action.TriggerAction;
import application.module.scene.trigger.condition.TriggerCondition;
import application.module.scene.trigger.context.TriggerContext;
import application.module.scene.trigger.event.TriggerEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public class DefaultTrigger implements Trigger {
    private final int triggerId;

    private final List<String> eventIds = new ArrayList<>();

    private final List<TriggerCondition<?>> conditions = new ArrayList<>();

    private final List<TriggerAction<?>> actions = new ArrayList<>();

    private boolean isTurnOn = false;

    private TriggerContext context;

    private int triggerCount = 1;

    public DefaultTrigger(int triggerId) {
        this.triggerId = triggerId;
    }

    public static DefaultTrigger of(int triggerId) {
        return new DefaultTrigger(triggerId);
    }

    @Override
    public void addEventId(String eventId) {
        eventIds.add(eventId);
    }

    @Override
    public List<String> getEventIdList() {
        return eventIds;
    }

    @Override
    public void addCondition(TriggerCondition<?> condition) {
        conditions.add(condition);
    }

    @Override
    public List<TriggerCondition<?>> getConditionList() {
        return conditions;
    }

    @Override
    public void addAction(TriggerAction<?> action) {
        actions.add(action);
    }

    @Override
    public List<TriggerAction<?>> getActionList() {
        return actions;
    }

    @Override
    public void setContext(TriggerContext context) {
        this.context = context;
    }

    @Override
    public TriggerContext getContext() {
        return this.context;
    }

    @Override
    public int getTriggerId() {
        return triggerId;
    }

    @Override
    public boolean isTurnOn() {
        return this.isTurnOn;
    }

    @Override
    public void turnOff() {
        this.isTurnOn = false;
    }

    @Override
    public void turnOn() {
        this.isTurnOn = true;
    }

    @Override
    public void destroy() {
        for (TriggerAction<?> action : actions) {
            action.destroy(context);
        }

    }

    @Override
    public void run(boolean checkCondition, TriggerEvent<?> event) {
        if (checkCondition) {
            List<TriggerCondition<?>> conditionList = getConditionList();
            for (TriggerCondition<?> condition : conditionList) {
                if (!condition.wasCompleted()) {
                    return;
                }
            }
        }

        List<TriggerAction<?>> actionList = getActionList();
        for (TriggerAction<?> action : actionList) {
            action.doAction(getContext(), event);
        }

        if (triggerCount == -1) {
            return;
        }
        triggerCount--;
        if (triggerCount <= 0) {
            getContext().getTriggerMgr().removeTrigger(this);
        }
    }

    @Override
    public boolean doTriggerEvent(TriggerEvent<?> event) {
        if (!isTurnOn()) {
            return false;
        }

        if (getEventIdList().size() != 0 && !getEventIdList().contains(event.getId())) {
            return false;
        }

        List<TriggerCondition<?>> conditionList = getConditionList();
        boolean triggerSuccess = conditionList.size() == 0;
        for (TriggerCondition<?> condition : conditionList) {
            if (condition.doTriggerEvent(event, getContext())) {
                triggerSuccess = true;
            }
        }

        if (triggerSuccess) {
            run(true, event);
        }

        return triggerSuccess;
    }

    public int getTriggerCount() {
        return triggerCount;
    }

    public void setTriggerCount(int triggerCount) {
        this.triggerCount = triggerCount;
    }
}
