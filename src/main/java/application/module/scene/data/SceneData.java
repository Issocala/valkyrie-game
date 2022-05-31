package application.module.scene.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.operate.PlayerLoginDbReturn;
import application.module.player.scene.operate.PlayerSceneGetSceneOpt;
import application.module.scene.SceneActor;
import application.util.Vector3;
import application.module.scene.data.entity.SceneEntity;
import application.module.scene.operate.AllSceneInitFinally;
import application.module.scene.operate.SceneOut;
import application.module.scene.operate.ScenePlayerExitReturn;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.db.message.DbGet;
import com.cala.orm.db.message.DbInsert;
import com.cala.orm.db.message.DbUpdate;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;

import java.util.Map;
import java.util.Objects;

import static application.module.scene.SceneModule.MAIN_SCENE;

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

    private Map<Integer, ActorRef> sceneId2SceneMap;

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case ScenePlayerExitReturn scenePlayerExitReturn -> scenePlayerExitReturn(scenePlayerExitReturn);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            case SceneOut sceneOut -> sceneOut(sceneOut);
            case PlayerSceneGetSceneOpt playerSceneGetSceneOpt -> playerSceneGetSceneOpt(playerSceneGetSceneOpt);
            case AllSceneInitFinally allSceneInitFinally -> allSceneInitFinally(allSceneInitFinally);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase.getClass().getName());
        }
    }

    private void allSceneInitFinally(AllSceneInitFinally allSceneInitFinally) {
        this.sceneId2SceneMap = allSceneInitFinally.sceneId2SceneMap();
    }

    private void playerSceneGetSceneOpt(PlayerSceneGetSceneOpt playerSceneGetSceneOpt) {
        int sceneTemplateId = playerSceneGetSceneOpt.sceneTemplateId();
        ActorRef scene = this.sceneId2SceneMap.get(sceneTemplateId);
        if (Objects.isNull(scene)) {
            sender().tell(new PlayerSceneGetSceneOpt(MAIN_SCENE, this.sceneId2SceneMap.get(MAIN_SCENE)), self());
        } else {
            sender().tell(new PlayerSceneGetSceneOpt(sceneTemplateId, scene), self());
        }
    }

    private void sceneOut(SceneOut sceneOut) {

    }

    private void scenePlayerExitReturn(ScenePlayerExitReturn scenePlayerExitReturn) {

        //TODO 这里需要拿场景配置，看是否是常驻场景，保存上一个常驻场景的id
        if (scenePlayerExitReturn.sceneTemplateId() == MAIN_SCENE) {
            long playerId = scenePlayerExitReturn.playerId();
            SceneEntity sceneEntity = new SceneEntity(playerId, scenePlayerExitReturn.sceneTemplateId(), scenePlayerExitReturn.vector3());
            put(scenePlayerExitReturn.playerId(), sceneEntity);
            if (containsKey(playerId)) {
                getDbManager().tell(new DbUpdate(self(), sceneEntity, null, false), self());
            } else {
                getDbManager().tell(new DbInsert(self(), sceneEntity, null, false), self());
            }
        }
    }

    @Override
    protected void afterDbReturnMessage(DBReturnMessage dbReturnMessage) {
        switch (dbReturnMessage.operateType()) {
            case PlayerLoginDbReturn playerLoginDbReturn -> playerLoginDbReturn(dbReturnMessage, playerLoginDbReturn);
            default -> {}
        }
        super.afterDbReturnMessage(dbReturnMessage);
    }

    private void playerLoginDbReturn(DBReturnMessage dbReturnMessage, PlayerLoginDbReturn playerLoginDbReturn) {
        SceneEntity sceneEntity = (SceneEntity) dbReturnMessage.abstractEntityBase();
        long playerId = playerLoginDbReturn.playerLogin().playerInfo().id();
        if (Objects.isNull(sceneEntity)) {
            sceneEntity = new SceneEntity(playerId, MAIN_SCENE,
                    new Vector3(SceneActor.DEFAULT_X, SceneActor.DEFAULT_Y, SceneActor.DEFAULT_FACE));
            this.put(playerId, sceneEntity);
            this.getDbManager().tell(new DbInsert(self(), sceneEntity, playerLoginDbReturn, false), self());
        }
        int sceneId = sceneEntity.getSceneTemplateId();
        PlayerLogin playerLogin = playerLoginDbReturn.playerLogin();
        if (this.sceneId2SceneMap.containsKey(sceneId)) {
            sceneId2SceneMap.get(sceneId).tell(playerLogin, self());
        } else {
            sceneId2SceneMap.get(MAIN_SCENE).tell(playerLogin, self());
        }
    }

    private void playerLogin(PlayerLogin playerLogin) {
        // TODO 场景id应该是保存起来的离线场景，或者是主城id，看策划怎么设计
        long playerId = playerLogin.playerInfo().id();
        SceneEntity sceneEntity = (SceneEntity) get(playerId);

        if (Objects.isNull(sceneEntity)) {
            getDbManager().tell(new DbGet(self(), new SceneEntity(playerId), new PlayerLoginDbReturn(playerLogin)), this.self());
        } else {
            int sceneId = sceneEntity.getSceneTemplateId();
            if (this.sceneId2SceneMap.containsKey(sceneId)) {
                sceneId2SceneMap.get(sceneId).tell(playerLogin, self());
            } else {
                sceneId2SceneMap.get(MAIN_SCENE).tell(playerLogin, self());
            }
        }
    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == SceneEntity.class;
    }
}

