package application.module.scene.trigger.timer;

import application.module.scene.trigger.Trigger;
import application.module.scene.trigger.event.TriggerEvent;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public interface TriggerTimer {

    int getTimerId();

    int getIntervalSeconds();

    void init();

    boolean isExpired(long now);

    boolean isPeriod();

    boolean wasPaused();

    void pause();

    void restore();

    void setTrigger(Trigger trigger);

    Trigger getTrigger();

    void setTriggerEvent(TriggerEvent<?> triggerEvent);

    TriggerEvent<?> getTriggerEvent();

}
