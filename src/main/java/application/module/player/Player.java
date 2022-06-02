package application.module.player;

import akka.actor.ActorRef;
import application.module.player.container.PlayerMgrContainer;
import application.module.player.data.entity.PlayerEntity;
import application.module.player.fight.attribute.AttributePlayerMgr;
import application.module.player.operate.PlayerInit;
import application.module.player.scene.ScenePlayerMgr;
import application.module.player.util.PlayerMgr;
import application.util.LongId;
import com.cala.orm.message.OperateType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author Luo Yong
 * @date 2022-5-13
 * @Source 1.0
 */
public class Player extends LongId {

    private final ActorRef playerActor;

    private Map<Long, ActorRef> playerActorMap = new HashMap<>();

    private final PlayerEntity playerEntity;

    private final Map<Class<?>, ActorRef> dataMap;

    private final Map<Integer, PlayerMgr> messageMap = new HashMap<>();

    private final Map<Class<? extends PlayerMgr>, PlayerMgr> mgrMap = new HashMap<>();

    private final Map<Class<? extends OperateType>, PlayerMgr> operateTypeMgrMap = new HashMap<>();

    private final Set<Class<? extends PlayerMgr>> initFinalSet = new HashSet<>();

    private int currCount;

    private ActorRef client;

    public Player(PlayerEntity playerEntity, ActorRef playerActor, Map<Class<?>, ActorRef> dataMap) {
        super(playerEntity.getId());
        this.playerEntity = playerEntity;
        this.playerActor = playerActor;
        this.dataMap = dataMap;
    }

    public void init(PlayerInit playerInit) {
        setClient(playerInit.client());
        playerActorMap = playerInit.playerActorMap();
        Set<PlayerMgr> playerMgrSet = PlayerMgrContainer.getPlayerMgr();
        playerMgrSet.forEach(playerMgr -> {
            putMgr(playerMgr.getClass(), playerMgr);
            playerMgr.getProtoIds().forEach(id -> this.messageMap.put(id, playerMgr));
            playerMgr.getOperateTypes().forEach(operateType -> this.operateTypeMgrMap.put(operateType, playerMgr));
        });
        this.mgrMap.values().forEach(playerMgr -> playerMgr.init(this));
    }

    public AttributePlayerMgr getAttributePlayerMgr() {
        return (AttributePlayerMgr) getMgr(AttributePlayerMgr.class);
    }

    //get and set

    public void putMgr(Class<? extends PlayerMgr> c, PlayerMgr playerMgr) {
        this.mgrMap.put(c, playerMgr);
    }

    public void addInitFinalSet(Class<? extends PlayerMgr> mgr) {
        this.initFinalSet.add(mgr);
    }

    public boolean isAllMgrInitFinal() {
        return mgrMap.size() == initFinalSet.size();
    }

    public PlayerMgr getMgr(Class<?> c) {
        return this.mgrMap.get(c);
    }

    public ActorRef getPlayerActor() {
        return playerActor;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public Map<Class<? extends PlayerMgr>, PlayerMgr> getMgrMap() {
        return mgrMap;
    }

    public Map<Integer, PlayerMgr> getMessageMap() {
        return messageMap;
    }

    public Map<Class<?>, ActorRef> getDataMap() {
        return dataMap;
    }

    public Map<Class<? extends OperateType>, PlayerMgr> getOperateTypeMgrMap() {
        return operateTypeMgrMap;
    }

    public ScenePlayerMgr getSceneMgr() {
        return (ScenePlayerMgr) this.mgrMap.get(ScenePlayerMgr.class);
    }

    public ActorRef getScene() {
        return getSceneMgr().getScene();
    }

    public Set<Class<? extends PlayerMgr>> getInitFinalSet() {
        return initFinalSet;
    }

    public int getCurrCount() {
        return currCount;
    }

    public void setCurrCount(int currCount) {
        this.currCount = currCount;
    }

    public ActorRef getClient() {
        return client;
    }

    public void setClient(ActorRef client) {
        this.client = client;
    }

    public Map<Long, ActorRef> getPlayerActorMap() {
        return playerActorMap;
    }

}
