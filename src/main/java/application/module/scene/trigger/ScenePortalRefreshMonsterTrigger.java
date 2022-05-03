package application.module.scene.trigger;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import application.module.organism.MonsterOrganism;
import application.module.organism.NpcOrganism;
import application.module.scene.data.domain.PositionInfo;
import application.module.scene.operate.CreateOrganismEntity;
import application.module.scene.operate.NpcUseSkill;
import application.module.scene.operate.SceneInit;
import application.module.scene.operate.SceneOrganismEntry;
import application.util.RandomUtil;
import mobius.core.java.api.AbstractLogActor;
import template.OrganismDataTemplate;
import template.OrganismDataTemplateHolder;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-4-19
 * @Source 1.0
 */
public class ScenePortalRefreshMonsterTrigger extends AbstractLogActor {

    private final int triggerId;

    private ActorRef scene;

    // TODO: 2022-4-19 初始化读配置，后续可能会有其他操作可以修改这个时间
    private final long tick = 20000;

    private MonsterOrganism boss;

    private final Map<Integer, NpcOrganism> npcOrganismMap = new HashMap<>();

    private final Set<Integer> disableNpcOrganism = new HashSet<>();

    public static Props create(int triggerId) {
        return Props.create(ScenePortalRefreshMonsterTrigger.class, triggerId);
    }

    public ScenePortalRefreshMonsterTrigger(int triggerId) {
        this.triggerId = triggerId;
    }

    public int getTriggerId() {
        return triggerId;
    }

    public Map<Integer, Cancellable> cancellableMap = new HashMap<>();


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SceneInit.class, this::sceneInit)
                .match(CreateOrganismEntity.class, this::createOrganismEntity)
                .match(NpcUseSkill.class, this::npcUseSkill)
                .build();
    }

    private void npcUseSkill(NpcUseSkill npcUseSkill) {
        int templateId = npcUseSkill.npcTemplateId();
        this.scene.tell(npcUseSkill, self());
        Cancellable cancellable2 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new NpcUseSkill(templateId, randomNpcSkillId(templateId)), getContext().dispatcher(), self());
        cancellableMap.put(templateId, cancellable2);
    }

    private void createOrganismEntity(CreateOrganismEntity createOrganismEntity) {

    }

    private void sceneInit(SceneInit sceneInit) {
        this.scene = sender();
        int templateId1 = 10001;
        NpcOrganism npcOrganism1 = new NpcOrganism(templateId1);
        scene.tell(new SceneOrganismEntry(npcOrganism1, new PositionInfo(-10.39662f, 8.045186f), 0), self());
        npcOrganismMap.put(templateId1, npcOrganism1);
        Cancellable cancellable1 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new NpcUseSkill(templateId1, randomNpcSkillId(templateId1)), getContext().dispatcher(), self());
        cancellableMap.put(templateId1, cancellable1);

        int templateId2 = 10002;
        NpcOrganism npcOrganism2 = new NpcOrganism(templateId2);
        scene.tell(new SceneOrganismEntry(npcOrganism2, new PositionInfo(10.47174f, 8.055185f), 0), self());
        npcOrganismMap.put(templateId2, npcOrganism2);
        Cancellable cancellable2 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new NpcUseSkill(templateId2, randomNpcSkillId(templateId2)), getContext().dispatcher(), self());
        cancellableMap.put(templateId2, cancellable2);

        int templateId3 = 10003;
        NpcOrganism npcOrganism3 = new NpcOrganism(templateId3);
        scene.tell(new SceneOrganismEntry(npcOrganism3, new PositionInfo(-10.42631f, 1.051806f), 0), self());
        npcOrganismMap.put(templateId3, npcOrganism3);
        Cancellable cancellable3 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new NpcUseSkill(templateId3, randomNpcSkillId(templateId3)), getContext().dispatcher(), self());
        cancellableMap.put(templateId3, cancellable3);

        int templateId4 = 10004;
        NpcOrganism npcOrganism4 = new NpcOrganism(templateId4);
        scene.tell(new SceneOrganismEntry(npcOrganism4, new PositionInfo(10.43515f, 1.051806f), 0), self());
        npcOrganismMap.put(templateId4, npcOrganism4);
        Cancellable cancellable4 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new NpcUseSkill(templateId4, randomNpcSkillId(templateId4)), getContext().dispatcher(), self());
        cancellableMap.put(templateId4, cancellable4);

        MonsterOrganism monsterOrganism = new MonsterOrganism(10009);
        scene.tell(new SceneOrganismEntry(monsterOrganism, new PositionInfo(3, 6.251080f), 0), self());

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

    private int randomNpcSkillId(int npcTemplateId) {
        OrganismDataTemplate dataTemplate = OrganismDataTemplateHolder.getData(npcTemplateId);
        int i = RandomUtil.randomInt(dataTemplate.skills().length);
        return dataTemplate.skills()[i];
    }
}
