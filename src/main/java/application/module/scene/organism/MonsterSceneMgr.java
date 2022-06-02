package application.module.scene.organism;

import akka.actor.ActorRef;
import application.module.organism.MonsterOrganism;
import application.module.scene.Scene;
import application.module.scene.SceneMgr;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.module.scene.operate.SceneOrganismExit;
import application.util.Vector3;
import com.cala.orm.message.OperateType;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public class MonsterSceneMgr implements SceneMgr {
    private Scene scene;
    private final Map<Long, MonsterOrganism> monsterMap = new HashMap<>();

    private final static int MAX_NUMBER = 60;

    public void addMonsterOrganism(MonsterOrganism monsterOrganism) {
        addMonsterOrganism(monsterOrganism, 0);
    }

    public void addMonsterOrganism(MonsterOrganism monsterOrganism, long duration) {
        if (monsterMap.size() >= MAX_NUMBER) {
            return;
        }
        this.monsterMap.put(monsterOrganism.getId(), monsterOrganism);
        monsterOrganism.sendSelfDataToSceneClient(monsterOrganism.getScene());
        if (duration > 0) {
            ActorRef sceneActor = scene.getSceneActor();
            scene.getContext().system().scheduler().scheduleOnce(Duration.ofMillis(duration), sceneActor,
                    new SceneOrganismExit(monsterOrganism.getId(), monsterOrganism.getOrganismType()),
                    scene.getContext().dispatcher(), sceneActor);
        }
    }

    public void removeMonsterOrganism(long id) {
        MonsterOrganism organism = this.monsterMap.get(id);
        if (Objects.nonNull(organism) && organism.getOrganismTemplateId() == 10009) {
            scene.dealBossDead();
        }
        this.monsterMap.remove(id);
        scene.getPlayerSceneMgr().sendToAllClient(scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(id));
    }

    public MonsterOrganism getMonsterOrganism(long id) {
        return this.monsterMap.get(id);
    }

    public boolean containsMonsterOrganism(long id) {
        return this.monsterMap.containsKey(id);
    }

    public void entryScene() {

    }

    public List<MonsterOrganism> refreshMonster() {

        return null;
    }

    public MonsterOrganism refreshMonster(int monsterTemplateId, float x, float y, Vector3 position) {

        return null;
    }


    @Override
    public void init(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void destroy(Scene scene) {

    }

    @Override
    public List<Class<? extends OperateType>> getOperateTypes() {
        return List.of();
    }

    public Map<Long, MonsterOrganism> getMonsterMap() {
        return monsterMap;
    }
}
