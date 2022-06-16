package application.module.scene.trigger.entrance;

import application.module.scene.trigger.context.TriggerContext;

/**
 * @author Luo Yong
 * @date 2022-6-13
 * @Source 1.0
 */
public class GameEventTriggerListener implements GameEventListener{

    private TriggerContext context;

    @Override
    public void doGameEvent(GameEvent event) {

    }

    public TriggerContext getContext() {
        return context;
    }

    public void setContext(TriggerContext context) {
        this.context = context;
    }
}
