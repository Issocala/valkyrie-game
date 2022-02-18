package application.util;

import java.util.*;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public class AttributeMapUtil {


    /**
     * 2个map相加(重叠部分值相加，非重叠部分取并集)，返回一个参数map
     *
     * @param map1 return
     * @return map1
     */
    public static Map<Short, Long> add(Map<Short, Long> map1, Map<Short, Long> map2) {
        if (Objects.isNull(map1) || Objects.isNull(map2)) {
            throw new NullPointerException("map1 or map2 is null");
        }
        map2.forEach((k, v) -> {
            Long l = map1.get(k);
            if (Objects.isNull(l)) {
                map1.put(k, v);
            } else {
                map1.put(k, v + l);
            }
        });
        return map1;
    }

    /**
     * <p>
     * 两个字典值相减，算数值差值
     * eg:
     * map1中存在，map2中存在  map1中对应的值减去map2中对应的值
     * map1中存在，map2中不存在 map1中数据保留
     * map1中不存在， map2中存在 map2中对应的值取负数，存入map1
     * <p>
     * 计算结果为键值对会被移除！
     * </p>
     *
     * @return map1
     */
    public static Map<Short, Long> sub(Map<Short, Long> map1, Map<Short, Long> map2) {
        List<Short> list = new ArrayList<>();
        map2.forEach((k, v) -> {
            Long l = map1.get(k);
            if (Objects.isNull(l)) {
                if (v != 0) {
                    map1.put(k, -v);
                }
            } else {
                long sub = l - v;
                if (sub == 0) {
                    list.add(k);
                } else {
                    map1.put(k, l - v);
                }
            }
        });
        list.forEach(map1::remove);
        return map1;
    }

    public static Map<Short, Long> copy(Map<Short, Long> map) {
        return new HashMap<>(map);
    }

}
