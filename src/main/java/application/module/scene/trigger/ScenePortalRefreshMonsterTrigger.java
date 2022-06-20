package application.module.scene.trigger;

import akka.actor.AbstractActor;
import application.module.organism.EffectOrganism;
import application.module.organism.MonsterOrganism;
import application.module.organism.NpcOrganism;
import application.module.organism.PlayerFight;
import application.module.scene.Scene;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.module.scene.fight.state.base.StateType;
import application.module.scene.operate.CloseNpcDoor;
import application.util.RandomUtil;
import application.util.Vector3;
import template.OrganismDataTemplate;
import template.OrganismDataTemplateHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-4-19
 * @Source 1.0
 */
public class ScenePortalRefreshMonsterTrigger {

    private final int triggerId;

    private Scene scene;

    private final long tick = 20000;

    private final long useSkillTick = 5000;

    private final long itemTick = 15000;

    private final long closeTick = 40000;

    private MonsterOrganism boss;

    private static final int SCENE_ENTITY = 10011;

    private final Map<Long, DoorData> doorDataMap = new HashMap<>();

    private SceneEntityData sceneEntityNpc;

    public ScenePortalRefreshMonsterTrigger(int triggerId) {
        this.triggerId = triggerId;
    }

    public void helpUseSkill(DoorData doorData) {

        EffectOrganism effectOrganism = doorData.effectOrganism;
        int templateId = effectOrganism.getOrganismTemplateId();
        int skillId = randomNpcSkillId(templateId);
        PlayerFight playerFight = scene.getPlayerSceneMgr().getAiPlayerFight();
        if (Objects.nonNull(playerFight)) {
            playerFight.getClient().tell(new application.client.Client.SendToClientJ(SceneProtocols.HELP_USE_SKILL,
                    SceneProtocolBuilder.getSc10308(effectOrganism.getId(), skillId)), scene.getSceneActor());
            doorData.setCurrPreUseSkillTime();
        }
    }

    public void helpUseSkill(SceneEntityData data) {
        NpcOrganism npcOrganism = data.npcOrganism;
        int num = randomMonsterSkillIdNum();
        PlayerFight playerFight = scene.getPlayerSceneMgr().getAiPlayerFight();
        OrganismDataTemplate dataTemplate = OrganismDataTemplateHolder.getData(SCENE_ENTITY);
        if (Objects.nonNull(playerFight)) {
            for (int i = 0; i < num; i++) {
                playerFight.getClient().tell(new application.client.Client.SendToClientJ(SceneProtocols.HELP_USE_SKILL,
                        SceneProtocolBuilder.getSc10308(npcOrganism.getId(), dataTemplate.skills()[0])), scene.getSceneActor());
            }
            data.setCurrPreUseSkillTime();
        }
    }

    public void init(Scene scene) {
        this.scene = scene;
        int templateId1 = 10001;
        EffectOrganism effectOrganism1 = EffectOrganism.of(scene, templateId1, new Vector3(-14.2f, 0.3f, 0));
        scene.getEffectSceneMgr().addEffectOrganism(effectOrganism1);
        doorDataMap.put(effectOrganism1.getId(), new DoorData(effectOrganism1));

        int templateId2 = 10002;
        EffectOrganism effectOrganism2 = EffectOrganism.of(scene, templateId2, new Vector3(14.2f, 0.3f, 0));
        scene.getEffectSceneMgr().addEffectOrganism(effectOrganism2);
        doorDataMap.put(effectOrganism2.getId(), new DoorData(effectOrganism2));

        int templateId3 = 10003;
        EffectOrganism effectOrganism3 = EffectOrganism.of(scene, templateId3, new Vector3(-14.2f, 11.3f, 0));
        scene.getEffectSceneMgr().addEffectOrganism(effectOrganism3);
        doorDataMap.put(effectOrganism3.getId(), new DoorData(effectOrganism3));

        int templateId4 = 10004;
        EffectOrganism effectOrganism4 = EffectOrganism.of(scene, templateId4, new Vector3(14.2f, 11.3f, 0));
        scene.getEffectSceneMgr().addEffectOrganism(effectOrganism4);
        doorDataMap.put(effectOrganism4.getId(), new DoorData(effectOrganism4));


        int templateId5 = 10009;
        this.boss = MonsterOrganism.of(scene, templateId5, new Vector3(0, 4.5f, 0));
        scene.getMonsterSceneMgr().addMonsterOrganism(this.boss);

        NpcOrganism npcOrganism = NpcOrganism.of(scene, SCENE_ENTITY, new Vector3(19, -0.2f, 0));
        this.sceneEntityNpc = new SceneEntityData(npcOrganism);
        scene.getNpcSceneMgr().addNpcOrganism(npcOrganism);
    }

    public void closeNpcDoor(CloseNpcDoor closeNpcDoor) {
        long organismId = closeNpcDoor.organismId();
        DoorData doorData = doorDataMap.get(organismId);
        doorData.setCurrCloseTime();
        EffectOrganism effectOrganism = doorData.effectOrganism;
        effectOrganism.getFightStateMgr().changeState(StateType.ActionType.DEAD_STATE, scene);
        PlayerFight playerFight = scene.getPlayerFight(closeNpcDoor.playerId());
        playerFight.getFightBuffMgr().removeBuff(10013);
        if (getCloseDoorNum() >= 4) {
            doorDataMap.values().forEach(DoorData::setCurrCloseTime);
            this.boss.getFightBuffMgr().addBuff(10012, playerFight, this.boss, 10000L);
        }
    }

    private int randomNpcSkillId(int npcTemplateId) {
        OrganismDataTemplate dataTemplate = OrganismDataTemplateHolder.getData(npcTemplateId);
        int i = RandomUtil.randomInt(10);
        if (i < 2) {
            return dataTemplate.skills()[1];
        }else {
            return dataTemplate.skills()[0];
        }
    }

    private int randomMonsterSkillIdNum() {
        return RandomUtil.randomInt(2) + 2;
    }

    private AbstractActor.ActorContext getContext() {
        return scene.getContext();
    }

    public void tick() {
        if (scene.getPlayerSceneMgr().getPlayerFightMap().isEmpty()) {
            return;
        }
        doDoor();
        doSceneEntity();
    }

    public void doDoor() {
        doorDataMap.values().forEach(doorData -> {
            if (doorData.isClose()) {
                return;
            }
            EffectOrganism effectOrganism = doorData.effectOrganism;
            if (doorData.getEffectOrganism().isState(StateType.ActionType.DEAD_STATE)) {
                effectOrganism.getFightStateMgr().changeState(StateType.ActionType.IDLE_STATE, scene);
            } else {
                if (doorData.isUseSkill()) {
                    helpUseSkill(doorData);
                }
            }
        });
    }

    public void doSceneEntity() {
        if (this.sceneEntityNpc.isUseSkill()) {
            helpUseSkill(this.sceneEntityNpc);
        }
    }

    public class DoorData {
        private long closeTime;
        private EffectOrganism effectOrganism;
        private long preUseSkillTime;

        public DoorData(EffectOrganism effectOrganism) {
            this.closeTime = System.currentTimeMillis();
            this.effectOrganism = effectOrganism;
        }

        public boolean isClose() {
            return closeTime + closeTick > System.currentTimeMillis();
        }

        public boolean isUseSkill() {
            return preUseSkillTime + useSkillTick <= System.currentTimeMillis();
        }

        public void setCurrCloseTime() {
            this.closeTime = System.currentTimeMillis();
        }

        public void setCurrPreUseSkillTime() {
            this.preUseSkillTime = System.currentTimeMillis();
        }

        public long getCloseTime() {
            return closeTime;
        }

        public void setCloseTime(long closeTime) {
            this.closeTime = closeTime;
        }

        public long getPreUseSkillTime() {
            return preUseSkillTime;
        }

        public void setPreUseSkillTime(long preUseSkillTime) {
            this.preUseSkillTime = preUseSkillTime;
        }

        public EffectOrganism getEffectOrganism() {
            return effectOrganism;
        }

        public void setEffectOrganism(EffectOrganism effectOrganism) {
            this.effectOrganism = effectOrganism;
        }
    }

    public class SceneEntityData {
        private final NpcOrganism npcOrganism;
        private long preUseSkillTime;

        public SceneEntityData(NpcOrganism npcOrganism) {
            this.npcOrganism = npcOrganism;
            this.preUseSkillTime = System.currentTimeMillis();
        }

        public boolean isUseSkill() {
            return preUseSkillTime + itemTick <= System.currentTimeMillis();
        }

        public void setCurrPreUseSkillTime() {
            this.preUseSkillTime = System.currentTimeMillis();
        }

        public long getPreUseSkillTime() {
            return preUseSkillTime;
        }

        public void setPreUseSkillTime(long preUseSkillTime) {
            this.preUseSkillTime = preUseSkillTime;
        }
    }

    public int getCloseDoorNum() {
        return (int) this.doorDataMap.values().stream().filter(DoorData::isClose).count();
    }

}
