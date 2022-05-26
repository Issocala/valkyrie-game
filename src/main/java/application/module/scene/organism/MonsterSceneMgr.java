package application.module.scene.organism;

import application.module.organism.MonsterOrganism;
import application.module.scene.Scene;
import application.module.scene.SceneMgr;
import com.cala.orm.message.OperateType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public class MonsterSceneMgr implements SceneMgr {

    private final Map<Long, MonsterOrganism> monsterMap = new HashMap<>();


    public void addMonsterOrganism(MonsterOrganism monsterOrganism) {
        this.monsterMap.put(monsterOrganism.getId(), monsterOrganism);
    }

    public void removeMonsterOrganism(long id) {
        this.monsterMap.remove(id);
    }

    public MonsterOrganism getMonsterOrganism(long id) {
        return this.monsterMap.get(id);
    }

    public boolean containsMonsterOrganism(long id) {
        return this.monsterMap.containsKey(id);
    }




    @Override
    public void init(Scene scene) {

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
