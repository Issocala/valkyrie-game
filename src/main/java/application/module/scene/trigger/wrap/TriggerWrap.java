package application.module.scene.trigger.wrap;

import application.module.scene.trigger.action.wrap.TriggerActionWrap;
import application.module.scene.trigger.condition.wrap.TriggerConditionWrap;
import application.module.scene.trigger.event.wrap.TriggerEventWrap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public class TriggerWrap {

    private int triggerId;

    private boolean isTurnOn;

    private int triggerCount;

    private final List<TriggerEventWrap> eventWraps = new ArrayList<>();

    private final List<TriggerConditionWrap> conditionWraps = new ArrayList<>();

    private final List<TriggerActionWrap> actionWraps = new ArrayList<>();

    public static TriggerWrap of() {
        return new TriggerWrap();
    }

    public int getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(int triggerId) {
        this.triggerId = triggerId;
    }

    public boolean isTurnOn() {
        return isTurnOn;
    }

    public void setTurnOn(boolean turnOn) {
        isTurnOn = turnOn;
    }

    public int getTriggerCount() {
        return triggerCount;
    }

    public void setTriggerCount(int triggerCount) {
        this.triggerCount = triggerCount;
    }

    public List<TriggerEventWrap> getEventWraps() {
        return eventWraps;
    }

    public List<application.module.scene.trigger.condition.wrap.TriggerConditionWrap> getConditionWraps() {
        return conditionWraps;
    }

    public List<TriggerActionWrap> getActionWraps() {
        return actionWraps;
    }
}
