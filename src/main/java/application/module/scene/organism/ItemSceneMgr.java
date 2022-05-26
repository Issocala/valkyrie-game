package application.module.scene.organism;

import application.module.organism.ItemOrganism;
import application.module.organism.ItemOrganism;
import application.module.scene.Scene;
import application.module.scene.SceneMgr;
import com.cala.orm.message.OperateType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-5-17
 * @Source 1.0
 */
public class ItemSceneMgr implements SceneMgr {

    private final Map<Long, ItemOrganism> itemMap = new HashMap<>();


    public void addItemOrganism(ItemOrganism itemOrganism) {
        this.itemMap.put(itemOrganism.getId(), itemOrganism);
    }

    public void removeItemOrganism(long id) {
        this.itemMap.remove(id);
    }

    public ItemOrganism getItemOrganism(long id) {
        return this.itemMap.get(id);
    }

    public boolean containsItemOrganism(long id) {
        return this.itemMap.containsKey(id);
    }

    public Map<Long, ItemOrganism> getItemMap() {
        return itemMap;
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
}
