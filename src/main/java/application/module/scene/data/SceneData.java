package application.module.scene.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.fight.skill.data.message.SkillUse;
import application.module.scene.data.domain.Scene;
import application.module.scene.data.domain.SceneEntity;
import application.module.scene.data.message.SceneInit;
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

    Map<Long, ActorRef> sceneId2SceneMap = new HashMap<>();

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case SceneInit sceneInit -> sceneInit(sceneInit);
            case SkillUse skillUse -> skillUse(skillUse);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
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
        this.sceneId2SceneMap.put(1L, getContext().actorOf(Scene.create()));
    }


    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == SceneEntity.class;
    }
}
