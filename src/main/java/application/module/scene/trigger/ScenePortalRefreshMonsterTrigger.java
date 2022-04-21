package application.module.scene.trigger;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import application.module.organism.MonsterOrganism;
import application.module.scene.operate.CreateOrganismEntity;
import application.module.scene.operate.SceneInit;
import application.module.scene.operate.SceneMonsterEntry;
import mobius.core.java.api.AbstractLogActor;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-4-19
 * @Source 1.0
 */
public class ScenePortalRefreshMonsterTrigger extends AbstractLogActor {

    private final int triggerId;

    private ActorRef scene;

    // TODO: 2022-4-19 初始化读配置，后续可能会有其他操作可以修改这个时间
    private long tick = 20000;

    private MonsterOrganism boss;

    public static Props create(int triggerId) {
        return Props.create(ScenePortalRefreshMonsterTrigger.class, triggerId);
    }

    public ScenePortalRefreshMonsterTrigger(int triggerId) {
        this.triggerId = triggerId;
    }

    public int getTriggerId() {
        return triggerId;
    }

    public Map<Integer, Trigger> triggerMap = new HashMap<>();

    private void createOrganismEntity(CreateOrganismEntity createOrganismEntity) {
        this.scene.tell(createOrganismEntity, self());
    }

    private void sceneInit(SceneInit sceneInit) {
        this.scene = sender();

        MonsterOrganism monsterOrganism1 = new MonsterOrganism(10001);
        MonsterOrganism monsterOrganism2 = new MonsterOrganism(10002);
        MonsterOrganism monsterOrganism3 = new MonsterOrganism(10003);
        MonsterOrganism monsterOrganism4 = new MonsterOrganism(10004);
        scene.tell(new SceneMonsterEntry(List.of(monsterOrganism1, monsterOrganism2, monsterOrganism3, monsterOrganism4)), self());

        Cancellable cancellable1 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new CreateOrganismEntity(1, 3, 10001), getContext().dispatcher(), self());
        put(10001, new Trigger(10001, cancellable1, true));
        Cancellable cancellable2 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new CreateOrganismEntity(1, 3, 10002), getContext().dispatcher(), self());
        put(10002, new Trigger(10002, cancellable2, true));

        Cancellable cancellable3 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new CreateOrganismEntity(1, 3, 10003), getContext().dispatcher(), self());
        put(10003, new Trigger(10003, cancellable3, true));

        Cancellable cancellable4 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new CreateOrganismEntity(1, 3, 10004), getContext().dispatcher(), self());
        put(10004, new Trigger(10004, cancellable4, true));

    }

    public void put(int id, Trigger trigger) {
        this.triggerMap.put(id, trigger);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SceneInit.class, this::sceneInit)
                .match(CreateOrganismEntity.class, this::createOrganismEntity)
                .build();
    }

    static class Trigger {
        private int id;
        private Cancellable cancellable;
        private boolean run;

        public Trigger(int id, Cancellable cancellable, boolean run) {
            this.id = id;
            this.cancellable = cancellable;
            this.run = run;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Cancellable getCancellable() {
            return cancellable;
        }

        public void setCancellable(Cancellable cancellable) {
            this.cancellable = cancellable;
        }

        public boolean isRun() {
            return run;
        }

        public void setRun(boolean run) {
            this.run = run;
        }
    }
}
