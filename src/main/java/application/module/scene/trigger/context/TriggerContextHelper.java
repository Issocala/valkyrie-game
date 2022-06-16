package application.module.scene.trigger.context;

import application.module.scene.trigger.DefaultTrigger;
import application.module.scene.trigger.Trigger;
import application.module.scene.trigger.TriggerMgr;
import application.module.scene.trigger.action.TriggerAction;
import application.module.scene.trigger.action.TriggerActionContainer;
import application.module.scene.trigger.action.wrap.TriggerActionWrap;
import application.module.scene.trigger.condition.TriggerCondition;
import application.module.scene.trigger.condition.TriggerConditionContainer;
import application.module.scene.trigger.condition.wrap.TriggerConditionWrap;
import application.module.scene.trigger.event.TriggerEventMgr;
import application.module.scene.trigger.event.wrap.TriggerEventWrap;
import application.module.scene.trigger.timer.TriggerTimerMgr;
import application.module.scene.trigger.wrap.TriggerWrap;
import application.util.BlackBoard;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-6-13
 * @Source 1.0
 */
public class TriggerContextHelper {

    public static TriggerContext createTriggerContext(List<TriggerWrap> triggerWraps, BlackBoard<?> blackBoard) {
        DefaultTriggerContext context = new DefaultTriggerContext();
        context.setBlackBoard(blackBoard);
        TriggerMgr triggerMgr = new TriggerMgr();
        TriggerEventMgr triggerEventMgr = new TriggerEventMgr();
        triggerEventMgr.setTriggerMgr(triggerMgr);
        TriggerTimerMgr triggerTimerMgr = new TriggerTimerMgr();
        triggerTimerMgr.setTriggerEventMgr(triggerEventMgr);
        context.setTriggerTimerMgr(triggerTimerMgr);
        context.setTriggerMgr(triggerMgr);
        context.setTriggerEventMgr(triggerEventMgr);

        for (TriggerWrap triggerWrap : triggerWraps) {
            DefaultTrigger defaultTrigger = new DefaultTrigger(triggerWrap.getTriggerId());
            defaultTrigger.setContext(context);
            triggerMgr.addTrigger(defaultTrigger);
            if (triggerWrap.isTurnOn()) {
                defaultTrigger.turnOn();
            }
            defaultTrigger.setTriggerCount(triggerWrap.getTriggerCount());
            initEvents(defaultTrigger, triggerWrap);
            initConditions(defaultTrigger, triggerWrap);
            initActions(defaultTrigger, triggerWrap);
        }

        return context;
    }

    private static void initEvents(Trigger trigger, TriggerWrap triggerWrap) {
        List<TriggerEventWrap> eventRefs = triggerWrap.getEventWraps();
        for (TriggerEventWrap eventRef : eventRefs) {
            trigger.addEventId(eventRef.getEventId());
        }
    }

    private static void initConditions(Trigger trigger, TriggerWrap triggerWrap) {
        List<TriggerConditionWrap> conditionWraps = triggerWrap.getConditionWraps();
        for (TriggerConditionWrap conditionWrap : conditionWraps) {
            TriggerCondition<?> readSingleCondition = readSingleCondition(conditionWrap);
            trigger.addCondition(readSingleCondition);
        }
    }

    private static void initActions(Trigger trigger, TriggerWrap triggerWrap) {
        List<TriggerActionWrap> actionWraps = triggerWrap.getActionWraps();
        for (TriggerActionWrap actionWrap : actionWraps) {
            TriggerAction<?> readSingleAction = readSingleAction(actionWrap);
            trigger.addAction(readSingleAction);
        }
    }

    private static TriggerCondition<?> readSingleCondition(TriggerConditionWrap conditionWrap) {
        return TriggerConditionContainer.create(conditionWrap.getConditionClassName(), conditionWrap);
    }

    private static TriggerAction<?> readSingleAction(TriggerActionWrap actionWrap) {
        return TriggerActionContainer.create(actionWrap.getActionClassName(), actionWrap);
    }

}
