package application.module.scene;

import akka.actor.ActorRef;
import application.guid.IdUtils;
import application.module.player.PlayerFight;
import application.module.scene.container.SceneMgrContainer;
import application.module.scene.organism.ItemSceneMgr;
import application.module.scene.organism.MonsterSceneMgr;
import application.module.scene.organism.NpcSceneMgr;
import application.module.scene.organism.PlayerSceneMgr;
import application.util.RandomUtil;
import application.util.Vector;
import com.cala.orm.message.OperateType;
import template.SceneDataTemplate;
import template.SceneDataTemplateHolder;

import java.util.HashMap;
import java.util.Map;
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

    private ActorRef playerActor;

    private final Vector[] playerBirths;

    private final Map<Integer, ActorRef> sceneId2SceneMap = new HashMap<>();

    private final Map<Class<?>, ActorRef> dataMap = new HashMap<>();

    private final Map<Class<?>, SceneMgr> mgrMap = new HashMap<>();

    private final Map<Class<? extends OperateType>, SceneMgr> operateTypeMgrMap = new HashMap<>();


    public Scene(ActorRef sceneActor, int sceneTemplateId, ActorRef sceneModule) {
        this.sceneId = IdUtils.fastSimpleUUIDLong();
        this.sceneActor = sceneActor;
        this.sceneTemplateId = sceneTemplateId;
        this.sceneModule = sceneModule;
        this.sceneDataTemplate = SceneDataTemplateHolder.getData(sceneTemplateId);
        float[] birthPoint = this.sceneDataTemplate.birthPoint();
        int length = birthPoint.length;
        int halfLength = length / 2;
        this.playerBirths = new Vector[halfLength];
        int index = 0;
        for (int i = 0; i < length; i += 2) {
            this.playerBirths[index++] = new Vector(birthPoint[i], birthPoint[i + 1]);
        }
    }

    public void init() {
        Set<SceneMgr> set = SceneMgrContainer.getSceneMgr();
        set.forEach(sceneMgr -> {
            this.mgrMap.put(sceneMgr.getClass(), sceneMgr);
            sceneMgr.getOperateTypes().forEach(aClass -> this.operateTypeMgrMap.put(aClass, sceneMgr));
        });
        this.mgrMap.values().forEach(sceneMgr -> sceneMgr.init(this));
    }

    public Vector getPlayerBirth() {
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


    public long getSceneId() {
        return sceneId;
    }

    public int getSceneTemplateId() {
        return sceneTemplateId;
    }

    public SceneDataTemplate getSceneDataTemplate() {
        return sceneDataTemplate;
    }

    public ActorRef getPlayerActor() {
        return playerActor;
    }

    public void setPlayerActor(ActorRef playerActor) {
        this.playerActor = playerActor;
    }
}
