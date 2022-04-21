package application.module.scene.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.fight.attribute.data.message.PlayerDead;
import application.module.fight.skill.data.message.SkillUseScene;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.data.message.event.PlayerLogout;
import application.module.player.operate.PlayerLoginDbReturn;
import application.module.scene.data.domain.PositionInfo;
import application.module.scene.data.domain.SceneEntity;
import application.module.scene.operate.*;
import application.util.CommonOperateTypeInfo;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.db.message.DbGet;
import com.cala.orm.db.message.DbInsert;
import com.cala.orm.db.message.DbUpdate;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public class SceneData extends AbstractDataCacheManager<SceneEntity> {

    public static Props create() {
        return Props.create(SceneData.class, SceneData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    private SceneData() {
    }

    @Override
    protected void dataInit(DataInit dataInit) {

    }

    private final Map<Long, Long> playerId2SceneIdMap = new HashMap<>();

    private final Map<Long, ActorRef> sceneId2SceneMap = new HashMap<>();

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case SceneMove sceneMove -> sceneMove(sceneMove);
            case SceneStop sceneStop -> sceneStop(sceneStop);
            case SceneJump sceneJump -> sceneJump(sceneJump);
            case SkillUseScene skillUse -> skillUse(skillUse);
            case ScenePlayerEntry scenePlayerEntry -> scenePlayerEntry(scenePlayerEntry);
            case ScenePlayerExit scenePlayerExit -> scenePlayerExit(scenePlayerExit);
            case ScenePlayerExitReturn scenePlayerExitReturn -> scenePlayerExitReturn(scenePlayerExitReturn);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            case SceneInit sceneInit -> sceneInit(sceneInit);
            case PlayerLogout playerLogout -> playerLogout(playerLogout);
            case SceneFlash sceneFlash -> sceneFlash(sceneFlash);
            case PlayerDead playerDead -> playerDead(playerDead);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase.getClass().getName());
        }
    }

    private void playerDead(PlayerDead playerDead) {

    }

    private void sceneFlash(SceneFlash sceneFlash) {
        long playerId = sceneFlash.r().uID();
        if (this.playerId2SceneIdMap.containsKey(playerId)) {
            long sceneId = this.playerId2SceneIdMap.get(playerId);
            this.sceneId2SceneMap.get(sceneId).tell(sceneFlash, self());
        }
    }

    private void scenePlayerExitReturn(ScenePlayerExitReturn scenePlayerExitReturn) {

        //TODO 这里需要拿场景配置，看是否是常驻场景，保存上一个常驻场景的id
        if (scenePlayerExitReturn.sceneTemplateId() == 20003L) {

        }
        long playerId = scenePlayerExitReturn.playerId();
        SceneEntity sceneEntity = new SceneEntity(playerId, scenePlayerExitReturn.sceneTemplateId(), scenePlayerExitReturn.positionInfo());
        put(scenePlayerExitReturn.playerId(), sceneEntity);
        if (containsKey(playerId)) {
            getDbManager().tell(new DbUpdate(self(), sceneEntity, null, false), self());
        } else {
            getDbManager().tell(new DbInsert(self(), sceneEntity, null, false), self());
        }
    }


    @Override
    protected void afterDbReturnMessage(DBReturnMessage dbReturnMessage) {
        switch (dbReturnMessage.operateType()) {
            case PlayerLoginDbReturn playerLoginDbReturn -> playerLoginDbReturn(dbReturnMessage, playerLoginDbReturn);
            default -> throw new IllegalStateException("Unexpected value: " + dbReturnMessage.operateType().getClass().getName());
        }
    }

    private void playerLoginDbReturn(DBReturnMessage dbReturnMessage, PlayerLoginDbReturn playerLoginDbReturn) {
        SceneEntity sceneEntity = (SceneEntity) dbReturnMessage.abstractEntityBase();
        long playerId = playerLoginDbReturn.playerLogin().playerInfo().id();
        if (Objects.isNull(sceneEntity)) {
            sceneEntity = new SceneEntity(playerId, 20003,
                    new PositionInfo(Scene.DEFAULT_X, Scene.DEFAULT_Y, Scene.DEFAULT_FACE));
            this.put(playerId, sceneEntity);
            this.getDbManager().tell(new DbInsert(self(), sceneEntity, playerLoginDbReturn, false), self());
        }
        long sceneId = sceneEntity.getSceneTemplateId();
        if (this.sceneId2SceneMap.containsKey(sceneId)) {
            sceneId2SceneMap.get(sceneId).tell(playerLoginDbReturn.playerLogin(), self());
            playerId2SceneIdMap.put(playerLoginDbReturn.playerLogin().playerInfo().id(), sceneId);
        }
    }

    private void playerLogout(PlayerLogout playerLogout) {
        long playerId = playerLogout.r().uID();
        if (this.playerId2SceneIdMap.containsKey(playerId)) {
            long sceneId = this.playerId2SceneIdMap.get(playerId);
            this.sceneId2SceneMap.get(sceneId).tell(playerLogout, self());
        }
        this.playerId2SceneIdMap.remove(playerLogout.r().uID());
    }

    private void sceneJump(SceneJump sceneJump) {
        long playerId = sceneJump.r().uID();
        if (this.playerId2SceneIdMap.containsKey(playerId)) {
            long sceneId = this.playerId2SceneIdMap.get(playerId);
            this.sceneId2SceneMap.get(sceneId).tell(sceneJump, self());
        }
    }

    private void playerLogin(PlayerLogin playerLogin) {
        // TODO 场景id应该是保存起来的离线场景，或者是主城id，看策划怎么设计
        long playerId = playerLogin.playerInfo().id();
        SceneEntity sceneEntity = (SceneEntity) get(playerId);
        if (Objects.isNull(sceneEntity)) {
            getDbManager().tell(new DbGet(self(), new SceneEntity(playerId), new PlayerLoginDbReturn(playerLogin)), this.self());
        } else {
            long sceneId = sceneEntity.getSceneTemplateId();
            if (this.sceneId2SceneMap.containsKey(sceneId)) {
                sceneId2SceneMap.get(sceneId).tell(playerLogin, self());
                playerId2SceneIdMap.put(playerId, sceneId);
            }
        }
    }

    private void sceneStop(SceneStop sceneStop) {
        long playerId = sceneStop.r().uID();
        if (this.playerId2SceneIdMap.containsKey(playerId)) {
            long sceneId = this.playerId2SceneIdMap.get(playerId);
            this.sceneId2SceneMap.get(sceneId).tell(sceneStop, self());
        }
    }

    private void scenePlayerExit(ScenePlayerExit scenePlayerExit) {
        long sceneId = scenePlayerExit.cs10031().getSceneId();
        if (this.sceneId2SceneMap.containsKey(sceneId)) {
            this.sceneId2SceneMap.get(sceneId).tell(scenePlayerExit, self());
        }
    }

    private void scenePlayerEntry(ScenePlayerEntry scenePlayerEntry) {
        long playerId = scenePlayerEntry.r().uID();
        long sceneId = scenePlayerEntry.cs10030().getSceneId();
        if (this.sceneId2SceneMap.containsKey(sceneId)) {
            this.sceneId2SceneMap.get(sceneId).tell(scenePlayerEntry, self());
            long oldSceneId = playerId2SceneIdMap.get(playerId);
            this.sceneId2SceneMap.get(oldSceneId).tell(new ScenePlayerExit(scenePlayerEntry.r(),
                    protocol.Scene.CS10031.newBuilder().setSceneId(oldSceneId).build()), self());
            playerId2SceneIdMap.put(playerId, sceneId);
        }
    }

    private void sceneMove(SceneMove sceneMove) {
        long playerId = sceneMove.r().uID();
        if (this.playerId2SceneIdMap.containsKey(playerId)) {
            long sceneId = this.playerId2SceneIdMap.get(playerId);
            this.sceneId2SceneMap.get(sceneId).tell(sceneMove, self());
        }
    }

    private void skillUse(SkillUseScene skillUse) {
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUse.operateTypeInfo();
        long sceneId = this.playerId2SceneIdMap.getOrDefault(commonOperateTypeInfo.r().uID(), 0L);
        ActorRef actorRef = sceneId2SceneMap.get(sceneId);
        if (Objects.nonNull(actorRef)) {
            actorRef.tell(skillUse, sender());
        }
    }

    private void sceneInit(SceneInit sceneInit) {
        ActorRef scene = getContext().actorOf(Scene.create(20003));
        this.sceneId2SceneMap.put(20003L, scene);
        scene.tell(sceneInit, self());

        ActorRef scene2 = getContext().actorOf(Scene.create(20004));
        this.sceneId2SceneMap.put(20004L, scene2);
        scene2.tell(sceneInit, self());
    }


    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == SceneEntity.class;
    }
}

