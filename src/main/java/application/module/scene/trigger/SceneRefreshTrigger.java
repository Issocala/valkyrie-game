package application.module.scene.trigger;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import application.module.scene.operate.CreateOrganismEntity;
import application.module.scene.operate.SceneInit;

import java.time.Duration;

/**
 * @author Luo Yong
 * @date 2022-4-19
 * @Source 1.0
 */
public class SceneRefreshTrigger extends UntypedAbstractActor {

    private final int triggerId;

    private ActorRef scene;

    // TODO: 2022-4-19 初始化读配置，后续可能会有其他操作可以修改这个时间
    private long tick = 3000;

    public static Props create(int triggerId, ActorContext actorContext) {
        return Props.create(SceneRefreshTrigger.class, triggerId, actorContext);
    }

    public SceneRefreshTrigger(int triggerId) {
        this.triggerId = triggerId;
    }

    public int getTriggerId() {
        return triggerId;
    }


    @Override
    public void onReceive(Object message) {
        switch (message) {
            case SceneInit sceneInit -> sceneInit(sceneInit);
            default -> throw new IllegalStateException("Unexpected value: " + message.getClass().getName());
        }
    }

    private void sceneInit(SceneInit sceneInit) {
        this.scene = sender();
        Cancellable cancellable = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), scene,
                new CreateOrganismEntity(1,3, 10001), getContext().dispatcher(), self());
    }
}
