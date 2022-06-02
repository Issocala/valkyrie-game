package application.module.scene;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import application.guid.IdUtils;
import application.module.organism.*;
import application.module.scene.container.SceneMgrContainer;
import application.module.scene.fight.skill.base.context.TargetParameter;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.util.SkillAimType;
import application.module.scene.operate.SceneOut;
import application.module.scene.operate.SceneTick;
import application.module.scene.organism.*;
import application.module.scene.trigger.ScenePortalRefreshMonsterTrigger;
import application.util.CommonOperateTypeInfo;
import application.util.RandomUtil;
import application.util.Vector3;
import com.cala.orm.message.OperateType;
import protocol.Skill;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;
import template.SceneDataTemplate;
import template.SceneDataTemplateHolder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public class Scene {

    /**
     * 场景怪物最大数量 //TODO 后面需要读表
     */
    public final static int MAX_MONSTER_COUNT = 30;

    private final int sceneTemplateId;

    private final long sceneId;

    private final SceneDataTemplate sceneDataTemplate;

    private final ActorRef sceneActor;

    private ActorRef sceneModule;

    private final Vector3[] playerBirths;

    private Map<Integer, ActorRef> sceneId2SceneMap = new HashMap<>();

    private final Map<Class<?>, ActorRef> dataMap = new HashMap<>();

    private final Map<Class<?>, SceneMgr> mgrMap = new HashMap<>();

    private final Map<Class<? extends OperateType>, SceneMgr> operateTypeMgrMap = new HashMap<>();

    private AbstractActor.ActorContext context;

    private ScenePortalRefreshMonsterTrigger trigger;

    public Scene(ActorRef sceneActor, int sceneTemplateId) {
        this.sceneId = IdUtils.fastSimpleUUIDLong();
        this.sceneActor = sceneActor;
        this.sceneTemplateId = sceneTemplateId;
        this.sceneDataTemplate = SceneDataTemplateHolder.getData(sceneTemplateId);
        float[] birthPoint = this.sceneDataTemplate.birthPoint();
        int length = birthPoint.length;
        int halfLength = length / 2;
        this.playerBirths = new Vector3[halfLength];
        int index = 0;
        for (int i = 0; i < length; i += 2) {
            this.playerBirths[index++] = Vector3.ofXY(birthPoint[i], birthPoint[i + 1]);
        }
    }

    public void init(AbstractActor.ActorContext context) {
        this.context = context;
        Set<SceneMgr> set = SceneMgrContainer.getSceneMgr();
        set.forEach(sceneMgr -> {
            this.mgrMap.put(sceneMgr.getClass(), sceneMgr);
            sceneMgr.getOperateTypes().forEach(aClass -> this.operateTypeMgrMap.put(aClass, sceneMgr));
        });
        this.mgrMap.values().forEach(sceneMgr -> sceneMgr.init(this));
    }

    public Vector3 getPlayerBirth() {
        if (playerBirths.length == 1) {
            return playerBirths[0];
        }
        int r = RandomUtil.randomInt(playerBirths.length);
        return playerBirths[r];
    }

    public ActorRef getSceneActor() {
        return sceneActor;
    }

    public PlayerFight getPlayerFight(long id) {
        return getPlayerSceneMgr().getPlayerFight(id);
    }

    public UseSkillDataTemp getTarget(Skill.CS10055 cs10055, Skill.CS10052 cs10052, boolean isSkillProcess) {
        // TODO: 2022-4-29 后续需要删除
        if (isSkillProcess) {
            //生成技能释放上下文
            UseSkillDataTemp useSkillDataTemp = UseSkillDataTemp.of(cs10055, this);
            useSkillDataTemp.setSceneId(getSceneId());
            long fightOrganismId = cs10055.getFightOrganismId();
            useSkillDataTemp.setAttackId(fightOrganismId);
            FightOrganism fightOrganism = getFightOrganism(fightOrganismId);
            if (Objects.nonNull(fightOrganism) && fightOrganism.getOrganismType() != OrganismType.PLAYER) {
                useSkillDataTemp.setAttackType(fightOrganism.getOrganismType());
                useSkillDataTemp.setAttackTemplateId(fightOrganism.getOrganismTemplateId());
            }

            //TODO 这里代码需要修改，写的什么垃圾
            useSkillDataTemp.getTargetId().forEach(id -> {
                PlayerFight playerFight = getPlayerSceneMgr().getPlayerFight(id);
                if (Objects.nonNull(playerFight)) {
                    useSkillDataTemp.getTargetParameters().add(new TargetParameter(playerFight));
                } else {
                    FightOrganism organism = getFightOrganism(id);
                    if (Objects.isNull(organism)) {
                        return;
                    }
                    useSkillDataTemp.getTargetParameters().add(new TargetParameter(organism));
                }
            });
            return useSkillDataTemp;
        }

        FightSkillTemplate fightSkillTemplate = FightSkillTemplateHolder.getData(cs10052.getSkillId());
        //生成技能释放上下文
        UseSkillDataTemp useSkillDataTemp = UseSkillDataTemp.of(cs10052, this);
        useSkillDataTemp.setSceneId(getSceneTemplateId());
        long fightOrganismId = cs10052.getFightOrganismId();
        useSkillDataTemp.setAttackId(fightOrganismId);
        FightOrganism fightOrganism = getFightOrganism(fightOrganismId);
        if (Objects.nonNull(fightOrganism) && fightOrganism.getOrganismType() != OrganismType.PLAYER) {
            useSkillDataTemp.setAttackType(fightOrganism.getOrganismType());
            useSkillDataTemp.setAttackTemplateId(fightOrganism.getOrganismTemplateId());
        }
        //TODO 获取目标
        if (SkillAimType.isOne(fightSkillTemplate)) {
        }
        //TODO 这里代码需要修改，写的什么垃圾
        useSkillDataTemp.getTargetId().forEach(id -> {
            PlayerFight playerFight = getPlayerSceneMgr().getPlayerFight(id);
            if (Objects.nonNull(playerFight)) {
                useSkillDataTemp.getTargetParameters().add(new TargetParameter(playerFight));
            } else {
                FightOrganism organism = getFightOrganism(id);
                if (Objects.isNull(organism)) {
                    return;
                }
                useSkillDataTemp.getTargetParameters().add(new TargetParameter(organism));
            }
        });
        return useSkillDataTemp;
    }


    public FightOrganism getFightOrganism(long organismId) {
        FightOrganism fightOrganism = null;
        if (getPlayerSceneMgr().containsPlayerFight(organismId)) {
            fightOrganism = getPlayerFight(organismId);
        } else if (getMonsterSceneMgr().containsMonsterOrganism(organismId)) {
            fightOrganism = getMonsterSceneMgr().getMonsterOrganism(organismId);
        } else if (getNpcSceneMgr().containsNpcOrganism(organismId)) {
            fightOrganism = getNpcSceneMgr().getNpcOrganism(organismId);
        } else if (getEffectSceneMgr().containsEffectOrganism(organismId)) {
            fightOrganism = getEffectSceneMgr().getEffectOrganism(organismId);
        }
        return fightOrganism;
    }

    // TODO: 2022-5-9 临时处理boss死亡
    public void dealBossDead() {
        //场景所有怪物消失
        getMonsterSceneMgr().getMonsterMap().keySet().removeIf(organismId -> {
            getPlayerSceneMgr().sendToAllClient(this, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(organismId));
            return true;
        });
        trigger = null;
        getContext().getSystem().scheduler().scheduleOnce(Duration.ofMillis(10000), this.sceneActor,
                new SceneOut(this.getSceneTemplateId()), getContext().dispatcher(), this.sceneActor);

    }

    public void tick(SceneTick sceneTick) {
        getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> playerFight.tick(sceneTick));
        getMonsterSceneMgr().getMonsterMap().values().forEach(monsterOrganism -> monsterOrganism.tick(sceneTick));
    }

    //get and set

    public Map<Class<?>, ActorRef> getDataMap() {
        return dataMap;
    }

    public Map<Class<?>, SceneMgr> getMgrMap() {
        return mgrMap;
    }

    public PlayerSceneMgr getPlayerSceneMgr() {
        return (PlayerSceneMgr) mgrMap.get(PlayerSceneMgr.class);
    }

    public MonsterSceneMgr getMonsterSceneMgr() {
        return (MonsterSceneMgr) mgrMap.get(MonsterSceneMgr.class);
    }

    public NpcSceneMgr getNpcSceneMgr() {
        return (NpcSceneMgr) mgrMap.get(NpcSceneMgr.class);
    }

    public ItemSceneMgr getItemSceneMgr() {
        return (ItemSceneMgr) mgrMap.get(ItemSceneMgr.class);
    }

    public EffectSceneMgr getEffectSceneMgr() {
        return (EffectSceneMgr) mgrMap.get(EffectSceneMgr.class);
    }

    public long getSceneId() {
        return sceneId;
    }

    public int getSceneTemplateId() {
        return sceneTemplateId;
    }

    public SceneDataTemplate getSceneDataTemplate() {
        return sceneDataTemplate;
    }

    public AbstractActor.ActorContext getContext() {
        return context;
    }

    public ScenePortalRefreshMonsterTrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(ScenePortalRefreshMonsterTrigger trigger) {
        this.trigger = trigger;
    }

    public Map<Integer, ActorRef> getSceneId2SceneMap() {
        return sceneId2SceneMap;
    }

    public void setSceneId2SceneMap(Map<Integer, ActorRef> sceneId2SceneMap) {
        this.sceneId2SceneMap = sceneId2SceneMap;
    }

    public ActorRef getSceneModule() {
        return sceneModule;
    }

    public void setSceneModule(ActorRef sceneModule) {
        this.sceneModule = sceneModule;
    }
}
