package application.module.fight.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 战斗相关静态数据
 *
 * @author Luo Yong
 * @date 2022-2-24
 * @Source 1.0
 */
public class FightTempAttributeUtil {

    /**
     * 属性id
     */
    public final static List<Short> allFightAttributeTypeIdList = new ArrayList<>();

    /**
     * 值类型id
     */
    public final static List<Short> fightAttributeValueIdList = new ArrayList<>();

    /**
     * 比例类型id
     */
    public final static List<Short> fightAttributeRateIdList = new ArrayList<>();

    /**
     * 比例id转换映射值id
     */
    public final static Map<Short, Short> valueId2RateIdMap = new HashMap<>();


}
