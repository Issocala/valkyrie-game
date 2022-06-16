package application.module.scene.trigger.timer;

import application.module.scene.trigger.Trigger;
import application.module.scene.trigger.event.TriggerEventMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public class TriggerTimerMgr {

    private final List<TriggerTimer> timers = new ArrayList<>();

    private final List<TriggerTimer> pendingRemoveTimers = new ArrayList<>();

    private TriggerEventMgr triggerEventMgr;

    public void destroy() {

    }

    public void addTimer(TriggerTimer timer) {
        timers.add(timer);
    }

    public void removeTimer(TriggerTimer timer) {
        timers.remove(timer);
    }

    public void clearTimer() {
        timers.clear();
    }

    private void addPendingRemoveTimer(TriggerTimer timer) {
        pendingRemoveTimers.add(timer);
    }

    public void pollTimers() {
        if (timers.size() == 0) {
            return;
        }

        boolean hasTimerTriggered = false;
        long now = System.currentTimeMillis();
        for (int i = 0; i < timers.size(); i++) {
            TriggerTimer triggerTimer = timers.get(i);
            if (triggerTimer.isExpired(now)) {
                hasTimerTriggered = true;
                triggerEventMgr.addEvent(triggerTimer.getTriggerEvent());
                Trigger trigger = triggerTimer.getTrigger();
                triggerTimer.init();
                if (!triggerTimer.isPeriod() || !trigger.isTurnOn()) {
                    addPendingRemoveTimer(triggerTimer);
                }
            }
        }

        if (pendingRemoveTimers.size() != 0) {
            timers.removeAll(pendingRemoveTimers);
            pendingRemoveTimers.clear();
        }
        if (hasTimerTriggered) {
            triggerEventMgr.pollEvents();
        }
    }

    public TriggerEventMgr getTriggerEventMgr() {
        return triggerEventMgr;
    }

    public void setTriggerEventMgr(TriggerEventMgr triggerEventMgr) {
        this.triggerEventMgr = triggerEventMgr;
    }
}
