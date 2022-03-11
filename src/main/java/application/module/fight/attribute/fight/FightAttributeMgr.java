package application.module.fight.attribute.fight;

import java.util.HashMap;
import java.util.Map;

/**
 * 战斗相关临时属性
 *
 * @author Luo Yong
 * @date 2022-2-24
 * @Source 1.0
 */
public class FightAttributeMgr {

    /**
     * 当前的战斗属性数据快照
     */
    private final Map<Short, Long> fightAttributeMap = new HashMap<>();

    /**
     * 当前buff添加的属性
     */
    private final Map<Short, Long> buffAttributeMap = new HashMap<>();

    public Long getValue(short id) {
        long value = fightAttributeMap.get(id);
        long buffValue = buffAttributeMap.get(id);
        return value + buffValue;
    }

}
