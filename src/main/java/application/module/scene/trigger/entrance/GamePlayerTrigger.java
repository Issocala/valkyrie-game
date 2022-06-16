package application.module.scene.trigger.entrance;

import application.module.scene.trigger.context.TriggerContext;
import application.module.scene.trigger.context.TriggerContextHelper;
import application.util.BlackBoard;

/**
 * @author Luo Yong
 * @date 2022-6-13
 * @Source 1.0
 */
public record GamePlayerTrigger(TriggerWrapContainer container, GameEventTriggerListener listener) {


    public static GamePlayerTrigger of(TriggerWrapContainer container, BlackBoard<?> blackBoard) {
        GamePlayerTrigger gamePlayerTrigger = new GamePlayerTrigger(container, new GameEventTriggerListener());
        gamePlayerTrigger.init(blackBoard);
        return gamePlayerTrigger;
    }

    private void init(BlackBoard<?> blackBoard) {
        TriggerContext context = TriggerContextHelper.createTriggerContext(container().getTriggerWraps(), blackBoard);
        this.listener.setContext(context);
    }

    @Override
    public TriggerWrapContainer container() {
        return container;
    }

    @Override
    public GameEventTriggerListener listener() {
        return listener;
    }
}
