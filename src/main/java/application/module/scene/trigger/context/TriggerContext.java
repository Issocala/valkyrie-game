package application.module.scene.trigger.context;

import application.module.scene.trigger.TriggerMgr;
import application.module.scene.trigger.event.TriggerEventMgr;
import application.module.scene.trigger.timer.TriggerTimerMgr;
import application.util.BlackBoard;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public interface TriggerContext {

    void setBlackBoard(BlackBoard<?> blackBoard);

    BlackBoard<?> getBlackBoard();

    void setTriggerMgr(TriggerMgr triggerMgr);

    TriggerMgr getTriggerMgr();

    void setTriggerTimerMgr(TriggerTimerMgr triggerTimerMgr);

    TriggerTimerMgr getTriggerTimerMgr();

    void setTriggerEventMgr(TriggerEventMgr triggerEventMgr);

    TriggerEventMgr getTriggerEventMgr();

    void destroy();

}
