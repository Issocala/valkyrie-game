package application.module.scene.trigger;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import application.module.fight.buff.data.message.AddBuff;
import application.module.organism.MonsterOrganism;
import application.module.organism.NpcOrganism;
import application.module.organism.OrganismType;
import application.module.scene.data.domain.PositionInfo;
import application.module.scene.operate.*;
import application.module.state.base.StateType;
import application.module.state.operate.OrganismCancelState;
import application.module.state.operate.OrganismChangeState;
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

    private ActorRef stateData;

    private ActorRef buffData;

    private ActorRef attributeData;

    private final long tick = 20000;

    private final long itemTick = 15000;

    private MonsterOrganism boss;

    private static final int SCENE_ENTRY = 10011;

    private final Map<Long, NpcOrganism> npcOrganismMap = new HashMap<>();

    private final Set<Long> disableNpcOrganism = new HashSet<>();

    public static Props create(int triggerId) {
        return Props.create(ScenePortalRefreshMonsterTrigger.class, triggerId);
    }

    public ScenePortalRefreshMonsterTrigger(int triggerId) {
        this.triggerId = triggerId;
    }

    public Map<Long, Cancellable> cancellableMap = new HashMap<>();

    public Map<Long, Cancellable> openNpcDoorMap = new HashMap<>();

    @Override
    public void postStop() throws Exception {
        super.postStop();
        cancellableMap.forEach((integer, cancellable) -> {
            cancellable.cancel();
        });
        cancellableMap = null;
        openNpcDoorMap.values().forEach(Cancellable::cancel);
        openNpcDoorMap = null;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SceneInit.class, this::sceneInit)
                .match(CreateOrganismEntity.class, this::createOrganismEntity)
                .match(HelpUseSkill.class, this::helpUseSkill)
                .match(OpenNpcDoor.class, this::openNpcDoor)
                .match(CloseNpcDoor.class, this::closeNpcDoor)
                .build();
    }

    private void openNpcDoor(OpenNpcDoor openNpcDoor) {
        long organismId = openNpcDoor.organismId();
        disableNpcOrganism.remove(organismId);
        this.stateData.tell(new OrganismCancelState(organismId, OrganismType.NPC, StateType.ActionType.IDLE_STATE, self()), self());
        openNpcDoorMap.remove(organismId);
    }

    private void helpUseSkill(HelpUseSkill helpUseSkill) {
        int templateId = helpUseSkill.organismTemplateId();
        long id = helpUseSkill.organismId();
        if (templateId == SCENE_ENTRY) {
            getContext().system().scheduler().scheduleOnce(Duration.ofMillis(itemTick), self(),
                    new HelpUseSkill(SCENE_ENTRY, helpUseSkill.organismId(), helpUseSkill.organismType(), helpUseSkill.skillTemplateId()),
                    getContext().dispatcher(), self());
            int num = randomMonsterSkillIdNum();
            for (int i = 0; i < num; i++) {
                this.scene.tell(helpUseSkill, self());
            }
            return;
        }
        if (helpUseSkill.organismType() == OrganismType.NPC) {
            if (validUseSkill(id)) {
                this.scene.tell(helpUseSkill, self());
            }
            int skillId = randomNpcSkillId(templateId);
            Cancellable cancellable = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                    new HelpUseSkill(templateId, id, helpUseSkill.organismType(), skillId), getContext().dispatcher(), self());
            cancellableMap.put(helpUseSkill.organismId(), cancellable);
        }
    }

    private void createOrganismEntity(CreateOrganismEntity createOrganismEntity) {

    }

    private void sceneInit(SceneInit sceneInit) {
        this.scene = sender();
        this.stateData = sceneInit.stateData();
        this.buffData = sceneInit.buffData();
        this.attributeData = sceneInit.attributeData();
        int templateId1 = 10001;
        NpcOrganism npcOrganism1 = new NpcOrganism(templateId1);
        scene.tell(new SceneOrganismEntry(npcOrganism1, new PositionInfo(-20, 10), 0), self());
        npcOrganismMap.put(npcOrganism1.getId(), npcOrganism1);
        Cancellable cancellable1 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new HelpUseSkill(templateId1, npcOrganism1.getId(), npcOrganism1.getOrganismType(), randomNpcSkillId(templateId1)), getContext().dispatcher(), self());
        cancellableMap.put(npcOrganism1.getId(), cancellable1);

        int templateId2 = 10002;
        NpcOrganism npcOrganism2 = new NpcOrganism(templateId2);
        scene.tell(new SceneOrganismEntry(npcOrganism2, new PositionInfo(20, 10), 0), self());
        npcOrganismMap.put(npcOrganism2.getId(), npcOrganism2);
        Cancellable cancellable2 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new HelpUseSkill(templateId2, npcOrganism2.getId(), npcOrganism2.getOrganismType(), randomNpcSkillId(templateId2)), getContext().dispatcher(), self());
        cancellableMap.put(npcOrganism2.getId(), cancellable2);

        int templateId3 = 10003;
        NpcOrganism npcOrganism3 = new NpcOrganism(templateId3);
        scene.tell(new SceneOrganismEntry(npcOrganism3, new PositionInfo(-19, -0.2f), 0), self());
        npcOrganismMap.put(npcOrganism3.getId(), npcOrganism3);
        Cancellable cancellable3 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new HelpUseSkill(templateId3, npcOrganism3.getId(), npcOrganism3.getOrganismType(), randomNpcSkillId(templateId3)), getContext().dispatcher(), self());
        cancellableMap.put(npcOrganism3.getId(), cancellable3);

        int templateId4 = 10004;
        NpcOrganism npcOrganism4 = new NpcOrganism(templateId4);
        scene.tell(new SceneOrganismEntry(npcOrganism4, new PositionInfo(19, -0.2f), 0), self());
        npcOrganismMap.put(npcOrganism4.getId(), npcOrganism4);
        Cancellable cancellable4 = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                new HelpUseSkill(templateId4, npcOrganism4.getId(), npcOrganism4.getOrganismType(), randomNpcSkillId(templateId4)), getContext().dispatcher(), self());
        cancellableMap.put(npcOrganism4.getId(), cancellable4);

        int templateId5 = 10009;
        this.boss = new MonsterOrganism(templateId5);
        scene.tell(new SceneOrganismEntry(this.boss, new PositionInfo(0, 4.5f), 0), self());

        NpcOrganism npcOrganism6 = new NpcOrganism(SCENE_ENTRY);
        npcOrganismMap.put(npcOrganism6.getId(), npcOrganism6);
        scene.tell(new SceneOrganismEntry(npcOrganism6, new PositionInfo(19, -0.2f), 0), self());
        OrganismDataTemplate dataTemplate = OrganismDataTemplateHolder.getData(SCENE_ENTRY);
        getContext().system().scheduler().scheduleOnce(Duration.ofMillis(itemTick), self(),
                new HelpUseSkill(SCENE_ENTRY, npcOrganism6.getId(), npcOrganism6.getOrganismType(), dataTemplate.skills()[0]), getContext().dispatcher(), self());
    }

    private void closeNpcDoor(CloseNpcDoor closeNpcDoor) {
        int organismTemplateId = closeNpcDoor.organismTemplateId();
        long organismId = closeNpcDoor.organismId();
        disableNpcOrganism.add(organismId);
        this.stateData.tell(new OrganismChangeState(organismId, OrganismType.NPC, StateType.ActionType.DEAD_STATE, self()), self());
        if (disableNpcOrganism.size() == 4) {
            disableNpcOrganism.forEach(id -> {
                if (openNpcDoorMap.containsKey(id)) {
                    Cancellable cancellable = openNpcDoorMap.get(id);
                    cancellable.cancel();
                    openNpcDoorMap.remove(id);
                }
                Cancellable cancellable = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick), self(),
                        new OpenNpcDoor(organismId, organismTemplateId, OrganismType.NPC), getContext().dispatcher(), self());
                openNpcDoorMap.put(organismId, cancellable);
            });
            buffData.tell(new AddBuff(null, 10012, 0L, boss.getId(), 10000L, scene, attributeData, stateData, new HashMap<>()), self());
        } else {
            Cancellable cancellable = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(tick * 2), self(),
                    new OpenNpcDoor(organismId, organismTemplateId, OrganismType.NPC), getContext().dispatcher(), self());
            openNpcDoorMap.put(organismId, cancellable);
        }
    }

    private int randomNpcSkillId(int npcTemplateId) {
        OrganismDataTemplate dataTemplate = OrganismDataTemplateHolder.getData(npcTemplateId);
        int i = RandomUtil.randomInt(dataTemplate.skills().length);
        return dataTemplate.skills()[i];
    }

    private int randomMonsterSkillIdNum() {
        return RandomUtil.randomInt(2) + 2;
    }

    private boolean validUseSkill(long organismId) {
        return !disableNpcOrganism.contains(organismId);
    }

    public int getTriggerId() {
        return triggerId;
    }
}
