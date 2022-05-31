package application.module.scene;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import application.guid.IdUtils;
import application.module.organism.*;
import application.module.scene.container.SceneMgrContainer;
import application.module.scene.fight.skill.base.context.TargetParameter;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.util.SkillAimType;
import application.module.scene.organism.*;
import application.util.CommonOperateTypeInfo;
import application.util.RandomUtil;
import application.util.Vector3;
import com.cala.orm.message.OperateType;
import protocol.Skill;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;
import template.SceneDataTemplate;
import template.SceneDataTemplateHolder;

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

    private final ActorRef sceneModule;

    private final Vector3[] playerBirths;

    private final Map<Integer, ActorRef> sceneId2SceneMap = new HashMap<>();

    private final Map<Class<?>, ActorRef> dataMap = new HashMap<>();

    private final Map<Class<?>, SceneMgr> mgrMap = new HashMap<>();

    private final Map<Class<? extends OperateType>, SceneMgr> operateTypeMgrMap = new HashMap<>();

    private AbstractActor.ActorContext context;

    public Scene(ActorRef sceneActor, int sceneTemplateId, ActorRef sceneModule) {
        this.sceneId = IdUtils.fastSimpleUUIDLong();
        this.sceneActor = sceneActor;
        this.sceneTemplateId = sceneTemplateId;
        this.sceneModule = sceneModule;
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
                    useSkillDataTemp.getTargetParameters().add(new TargetParameter(getFightOrganism(id)));
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
                useSkillDataTemp.getTargetParameters().add(new TargetParameter(getFightOrganism(id)));
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
}
