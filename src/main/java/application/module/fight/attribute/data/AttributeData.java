package application.module.fight.attribute.data;

import akka.actor.Props;
import application.module.fight.attribute.AttributeTemplateIdContainer;
import application.module.fight.attribute.data.domain.Attribute;
import application.module.fight.attribute.data.domain.TypeAttribute;
import application.module.fight.attribute.data.message.UpdateAttribute;
import application.module.fight.attribute.data.message.UpdateFightAttribute;
import application.util.AttributeMapUtil;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.message.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public class AttributeData extends AbstractDataCacheManager<Attribute> {

    public static Props create() {
        return Props.create(AttributeData.class, AttributeData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    private AttributeData() {
    }

    @Override
    protected void dataInit(DataInit dataInit) {

    }

    private final static Map<Long, TypeAttribute> playerId2TypeAttributeMap = new HashMap<>();

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case UpdateAttribute updateAttribute -> updateAttribute(updateAttribute);
            case UpdateFightAttribute updateFightAttribute -> updateFightAttribute(updateFightAttribute);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
    }

    private void updateFightAttribute(UpdateFightAttribute updateFightAttribute) {
        long playerId = updateFightAttribute.playerId();
        TypeAttribute typeAttribute = playerId2TypeAttributeMap.get(playerId);
        if (Objects.isNull(typeAttribute)) {
            typeAttribute = new TypeAttribute();
            playerId2TypeAttributeMap.put(playerId, typeAttribute);
        }
        typeAttribute.addFightAttribute(updateFightAttribute.result());
    }

    private void updateAttribute(UpdateAttribute updateAttribute) {
        long playerId = updateAttribute.playerId();
        TypeAttribute typeAttribute = playerId2TypeAttributeMap.get(playerId);
        if (Objects.isNull(typeAttribute)) {
            typeAttribute = new TypeAttribute();
            playerId2TypeAttributeMap.put(playerId, typeAttribute);
        }

        typeAttribute.addAttribute(updateAttribute);
    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == Attribute.class;
    }

    /**
     * 处理全局模块的百分比加成
     *
     * @param allFightAttributeMap 被添加的集合目标
     * @param id2FightAttributeMap 需要添加的集合
     */
    public static void doAddAttribute(Map<Short, Long> allFightAttributeMap, Map<Short, Long> id2FightAttributeMap) {
        AttributeTemplateIdContainer.reducePublicExt(allFightAttributeMap, id2FightAttributeMap.keySet());
        AttributeMapUtil.add(allFightAttributeMap, id2FightAttributeMap);
        AttributeTemplateIdContainer.finalFatherResult(allFightAttributeMap);
    }

    /**
     * 处理全局模块的百分比加成
     *
     * @param allFightAttributeMap 被减少的集合目标
     * @param id2FightAttributeMap 需要减少的集合
     */
    public static void doSubAttribute(Map<Short, Long> allFightAttributeMap, Map<Short, Long> id2FightAttributeMap) {
        AttributeTemplateIdContainer.reducePublicExt(allFightAttributeMap, id2FightAttributeMap.keySet());
        AttributeMapUtil.sub(allFightAttributeMap, id2FightAttributeMap);
        AttributeTemplateIdContainer.finalFatherResult(allFightAttributeMap);
    }
}
