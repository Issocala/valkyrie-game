package application.module.scene.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.fight.attribute.data.message.UpdateFightAttribute;
import application.module.fight.skill.data.message.SkillUseState;
import application.module.player.data.message.PlayerLogin;
import application.module.scene.data.domain.Scene;
import application.module.scene.data.domain.SceneEntity;
import application.module.scene.data.message.SceneInit;
import application.module.scene.operate.*;
import application.util.CommonOperateTypeInfo;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
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
            case SkillUseState skillUse -> skillUse(skillUse);
            case ScenePlayerEntry scenePlayerEntry -> scenePlayerEntry(scenePlayerEntry);
            case ScenePlayerExit scenePlayerExit -> scenePlayerExit(scenePlayerExit);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            case SceneInit sceneInit -> sceneInit(sceneInit);
            case UpdateFightAttribute updateFightAttribute -> attributeUpdateFightAttribute(updateFightAttribute);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
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
        long sceneId = 20003L;
        if (this.sceneId2SceneMap.containsKey(sceneId)) {
            sceneId2SceneMap.get(sceneId).tell(playerLogin, self());
            playerId2SceneIdMap.put(playerLogin.playerId(), sceneId);
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

    private void attributeUpdateFightAttribute(UpdateFightAttribute updateFightAttribute) {
        long playerId = updateFightAttribute.playerId();
        if (playerId2SceneIdMap.containsKey(playerId)) {
            long sceneId = playerId2SceneIdMap.get(playerId);
            ActorRef scene = sceneId2SceneMap.get(sceneId);
            scene.tell(updateFightAttribute, self());
        }
    }

    private void skillUse(SkillUseState skillUse) {
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUse.operateTypeInfo();
        long sceneId = this.playerId2SceneIdMap.getOrDefault(commonOperateTypeInfo.r().uID(), 0L);
        ActorRef actorRef = sceneId2SceneMap.get(sceneId);
        if (Objects.nonNull(actorRef)) {
            actorRef.tell(skillUse, sender());
        }
    }

    private void sceneInit(SceneInit sceneInit) {
        this.sceneId2SceneMap.put(20003L, getContext().actorOf(Scene.create(20003L)));
    }


    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == SceneEntity.class;
    }
}

