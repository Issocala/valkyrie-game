package application.module.scene.trigger.event;

import application.module.scene.trigger.TriggerMgr;
import application.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TriggerEventMgr {
    private static final Logger logger = LoggerFactory.getLogger(TriggerEventMgr.class.getSimpleName());
    private final List<TriggerEvent<?>> events = new ArrayList<>();

    private final List<TriggerEvent<?>> pendingEvents = new ArrayList();

    private final AtomicBoolean isPolling = new AtomicBoolean(false);

    private TriggerMgr triggerMgr;

    public TriggerMgr getTriggerMgr() {
        return triggerMgr;
    }

    public void setTriggerMgr(TriggerMgr triggerMgr) {
        this.triggerMgr = triggerMgr;
    }

    public void destroy() {

    }

    public void addEvent(TriggerEvent<?> event) {
        if (isPolling.get()) {
            addPendingEvent(event);
            return;
        }
        events.add(event);
    }

    public void addPendingEvent(TriggerEvent<?> event) {
        pendingEvents.add(event);
    }

    public void pollEvents() {
        if (pendingEvents.size() != 0) {
            events.addAll(pendingEvents);
            pendingEvents.clear();
        }
        boolean compareAndSet = false;
        try {
            compareAndSet = isPolling.compareAndSet(false, true);
            if (compareAndSet) {
                int size = events.size();
                for (int i = 0; i < size; i++) {
                    try {
                        TriggerEvent<?> triggerEvent = (TriggerEvent<?>) events.get(i);
                        triggerMgr.onTriggerEvent(triggerEvent);

                    } catch (Exception e) {
                        logger.error(DebugUtil.printStack(e));
                    }
                }
                events.clear();
            }
        } finally {
            if (compareAndSet) {
                isPolling.compareAndSet(true, false);
            }
        }

    }
}
