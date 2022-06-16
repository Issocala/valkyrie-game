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
public class DefaultTriggerContext implements TriggerContext {

    private BlackBoard<?> blackBoard;

    private TriggerMgr triggerMgr;

    private TriggerTimerMgr triggerTimerMgr;

    private TriggerEventMgr triggerEventMgr;


    @Override
    public BlackBoard<?> getBlackBoard() {
        return blackBoard;
    }

    @Override
    public void setBlackBoard(BlackBoard<?> blackBoard) {
        this.blackBoard = blackBoard;
    }

    @Override
    public TriggerMgr getTriggerMgr() {
        return triggerMgr;
    }

    @Override
    public void setTriggerMgr(TriggerMgr triggerMgr) {
        this.triggerMgr = triggerMgr;
    }

    @Override
    public TriggerTimerMgr getTriggerTimerMgr() {
        return triggerTimerMgr;
    }

    @Override
    public void setTriggerTimerMgr(TriggerTimerMgr triggerTimerMgr) {
        this.triggerTimerMgr = triggerTimerMgr;
    }

    @Override
    public TriggerEventMgr getTriggerEventMgr() {
        return triggerEventMgr;
    }

    @Override
    public void setTriggerEventMgr(TriggerEventMgr triggerEventMgr) {
        this.triggerEventMgr = triggerEventMgr;
    }

    @Override
    public void destroy() {
        triggerMgr.destroy();
        triggerEventMgr.destroy();
        triggerEventMgr.destroy();
    }

}
