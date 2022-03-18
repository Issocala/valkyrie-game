package application.module.fight.attribute.data;

import akka.actor.Props;
import application.module.fight.attribute.AttributeTemplateIdContainer;
import application.module.fight.attribute.data.domain.Attribute;
import application.util.AttributeMapUtil;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;

import java.util.Map;

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
    public void dbReturnMessage(DBReturnMessage dbReturnMessage) {

    }

    @Override
    public void receive(DataBase dataBase) {

    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == Attribute.class;
    }

    /**
     * 处理全局模块的百分比加成
     *
     * @param fatherId2AllFightAttributeMap 被添加的集合目标
     * @param id2FightAttributeMap          需要添加的集合
     */
    public static void doAddAttribute(Map<Short, Long> fatherId2AllFightAttributeMap, Map<Short, Long> id2FightAttributeMap) {
        AttributeTemplateIdContainer.reducePublicExt(fatherId2AllFightAttributeMap, id2FightAttributeMap.keySet());
        AttributeMapUtil.add(fatherId2AllFightAttributeMap, id2FightAttributeMap);
        AttributeTemplateIdContainer.finalFatherResult(id2FightAttributeMap);
    }

    /**
     * 处理全局模块的百分比加成
     *
     * @param fatherId2AllFightAttributeMap 被减少的集合目标
     * @param id2FightAttributeMap          需要减少的集合
     */
    public static void doSubAttribute(Map<Short, Long> fatherId2AllFightAttributeMap, Map<Short, Long> id2FightAttributeMap) {
        AttributeTemplateIdContainer.reducePublicExt(fatherId2AllFightAttributeMap, id2FightAttributeMap.keySet());
        AttributeMapUtil.sub(fatherId2AllFightAttributeMap, id2FightAttributeMap);
        AttributeTemplateIdContainer.finalFatherResult(id2FightAttributeMap);
    }
}
