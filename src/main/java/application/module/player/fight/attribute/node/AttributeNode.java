package application.module.player.fight.attribute.node;

import application.module.player.Player;
import application.module.player.fight.attribute.provider.AttributeProvider;
import application.util.AttributeMapUtil;
import com.google.common.base.Preconditions;

import java.util.Map;

/**
 * <p>
 * 属性处理逻辑为初始化获取当前节点属性参数，非初始化情况获取变化差值，
 * id2AllFightAttributeMap 当前节点加子节点属性集合
 * isInit 是否已经初始化
 * </p>
 *
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public record AttributeNode(short typeId, Map<Short, Long> id2AllFightAttributeMap,
                            AttributeProvider attributeProvider) {

    public Map<Short, Long> update(Player player) {
        Preconditions.checkNotNull(attributeProvider, "战力提供函数为空,策划配置函数名和代码函数名不一致！！！");
        Map<Short, Long> id2FightAttributeMap;

        id2FightAttributeMap = attributeProvider.provider(player);
        Map<Short, Long> subMap = AttributeMapUtil.sub(id2FightAttributeMap, id2AllFightAttributeMap);
        AttributeMapUtil.add(id2AllFightAttributeMap, subMap);
        return subMap;
    }
}
