package application.module.scene.organism;

import application.module.organism.MonsterOrganism;
import application.module.organism.NpcOrganism;
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
public class NpcSceneMgr implements SceneMgr {

    private final Map<Long, NpcOrganism> npcMap = new HashMap<>();

    public void addNpcOrganism(NpcOrganism npcOrganism) {
        this.npcMap.put(npcOrganism.getId(), npcOrganism);
    }

    public void removeNpcOrganism(long id) {
        this.npcMap.remove(id);
    }

    public NpcOrganism getNpcOrganism(long id) {
        return this.npcMap.get(id);
    }

    public boolean containsNpcOrganism(long id) {
        return this.npcMap.containsKey(id);
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

    public Map<Long, NpcOrganism> getNpcMap() {
        return npcMap;
    }
}
