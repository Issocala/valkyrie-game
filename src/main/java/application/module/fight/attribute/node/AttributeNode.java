package application.module.fight.attribute.node;

import application.module.fight.attribute.provider.AttributeProvider;
import application.util.AttributeMapUtil;
import application.util.UpdateAttributeObject;
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

    public Map<Short, Long> update(UpdateAttributeObject<?> o) {
        Preconditions.checkNotNull(attributeProvider, "战力提供函数为空,策划配置函数名和代码函数名不一致！！！");
        Map<Short, Long> id2FightAttributeMap;

        id2FightAttributeMap = attributeProvider.provider(o);
        Map<Short, Long> subMap = AttributeMapUtil.sub(id2FightAttributeMap, id2AllFightAttributeMap);
        AttributeMapUtil.add(id2AllFightAttributeMap, subMap);
//        //旧的系统百分比算出的额外值
//        Map<Short, Long> reduces = AttributeTemplateIdContainer.reducePartExt(id2AllFightAttributeMap);
//        Map<Short, Long> addExtMap = AttributeTemplateIdContainer.finalPartResult(id2AllFightAttributeMap, id2FightAttributeMap.keySet());
//        Map<Short, Long> addPublic = AttributeMapUtil.sub(addExtMap, reduces);
//        //TODO 计算模块战力
//        AttributeMapUtil.add(id2FightAttributeMap, addPublic);
        return subMap;
    }
}
