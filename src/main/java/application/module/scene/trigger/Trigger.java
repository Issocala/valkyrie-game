package application.module.scene.trigger;

import application.module.scene.trigger.action.TriggerAction;
import application.module.scene.trigger.condition.TriggerCondition;
import application.module.scene.trigger.context.TriggerContext;
import application.module.scene.trigger.event.TriggerEvent;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public interface Trigger {

    int getTriggerId();

    boolean isTurnOn();

    void turnOff();

    void turnOn();

    void run(boolean checkCondition, TriggerEvent<?> event);

    boolean doTriggerEvent(TriggerEvent<?> event);

    void addEventId(String eventId);

    List<String> getEventIdList();

    void addCondition(TriggerCondition<?> condition);

    List<TriggerCondition<?>> getConditionList();

    void addAction(TriggerAction<?> action);

    List<TriggerAction<?>> getActionList();

    void setContext(TriggerContext context);

    TriggerContext getContext();

    void destroy();

}
