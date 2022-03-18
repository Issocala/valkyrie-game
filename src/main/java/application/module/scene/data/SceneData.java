package application.module.scene.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.fight.attribute.data.message.AttributeUpdateFightAttribute;
import application.module.fight.skill.data.message.SkillUse;
import application.module.scene.data.domain.Scene;
import application.module.scene.data.domain.SceneEntity;
import application.module.scene.data.message.SceneInit;
import application.module.scene.operate.SceneMove;
import application.module.scene.operate.ScenePlayerEntry;
import application.module.scene.operate.ScenePlayerExit;
import application.module.scene.operate.SceneStop;
import application.util.CommonOperateTypeInfo;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;
import protocol.Skill;

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
    public void dbReturnMessage(DBReturnMessage dbReturnMessage) {

    }

    private final Map<Long, Long> playerId2SceneIdMap = new HashMap<>();

    private final Map<Long, ActorRef> sceneId2SceneMap = new HashMap<>();

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case SceneMove sceneMove -> sceneMove(sceneMove);
            case SceneStop sceneStop -> sceneStop(sceneStop);
            case SkillUse skillUse -> skillUse(skillUse);
            case ScenePlayerEntry scenePlayerEntry -> scenePlayerEntry(scenePlayerEntry);
            case ScenePlayerExit scenePlayerExit -> scenePlayerExit(scenePlayerExit);
            case SceneInit sceneInit -> sceneInit(sceneInit);
            case AttributeUpdateFightAttribute attributeUpdateFightAttribute -> attributeUpdateFightAttribute(attributeUpdateFightAttribute);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
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

    private void attributeUpdateFightAttribute(AttributeUpdateFightAttribute attributeUpdateFightAttribute) {
        long playerId = attributeUpdateFightAttribute.playerId();
        if (playerId2SceneIdMap.containsKey(playerId)) {
            long sceneId = playerId2SceneIdMap.get(playerId);
            ActorRef scene = sceneId2SceneMap.get(sceneId);
            scene.tell(attributeUpdateFightAttribute, self());
        }
    }

    private void skillUse(SkillUse skillUse) {
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUse.operateTypeInfo();
        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
        ActorRef actorRef = sceneId2SceneMap.get(cs10052.getSceneId());
        if (Objects.nonNull(actorRef)) {
            actorRef.tell(skillUse, self());
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
