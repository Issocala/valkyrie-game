package application.module.scene.trigger;

import application.module.scene.trigger.event.TriggerEvent;

import java.util.ArrayList;
import java.util.List;

public class TriggerMgr {

    private final List<Trigger> triggers = new ArrayList<>();
    // trigger be added by action. append to running trigger list, next cycle.
    private final List<Trigger> pendingTriggers = new ArrayList<>();
    // trigger be removed by action. remove from running trigger list, next
    // cycle.
    private final List<Trigger> pendingRemoveTriggers = new ArrayList<>();

    private boolean isPolling = false;

    public void destroy() {
        for (Trigger trigger : triggers) {
            try {
                trigger.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addTrigger(Trigger trigger) {
        if (isPolling) {
            addPendingTrigger(trigger);
            return;
        }
        getTriggers().add(trigger);
    }

    public void removeTrigger(Trigger trigger) {
        if (isPolling) {
            addPendingRemoveTrigger(trigger);
            return;
        }
        getTriggers().remove(trigger);
    }

    public void clear() {
        if (isPolling) {
            for (int i = 0; i < getTriggers().size(); i++) {
                addPendingRemoveTrigger(getTriggers().get(i));
            }
            return;
        }
        getTriggers().clear();
    }

    public void addPendingTrigger(Trigger trigger) {
        pendingTriggers.add(trigger);
    }

    public void clearPendingTrigger() {
        pendingTriggers.clear();
    }

    public void addPendingRemoveTrigger(Trigger trigger) {
        pendingRemoveTriggers.add(trigger);
    }

    public void clearPendingRemoveTrigger() {
        pendingRemoveTriggers.clear();
    }

    public void onTriggerEvent(TriggerEvent<?> event) {
        if (pendingRemoveTriggers.size() != 0) {
            getTriggers().removeAll(pendingRemoveTriggers);
            pendingRemoveTriggers.clear();
        }

        if (pendingTriggers.size() != 0) {
            getTriggers().addAll(pendingTriggers);
            pendingTriggers.clear();
        }

        isPolling = true;
        for (int i = 0; i < getTriggers().size(); i++) {
            Trigger trigger = getTriggers().get(i);
            trigger.doTriggerEvent(event);
        }
        isPolling = false;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public Trigger getTrigger(int triggerId) {
        for (int i = 0; i < triggers.size(); i++) {
            Trigger trigger = triggers.get(i);
            if (trigger.getTriggerId() == triggerId) {
                return trigger;
            }
        }
        return null;
    }
}
