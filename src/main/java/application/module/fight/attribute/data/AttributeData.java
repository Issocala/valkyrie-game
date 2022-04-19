package application.module.fight.attribute.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.fight.attribute.AttributeProtocolBuilder;
import application.module.fight.attribute.AttributeProtocols;
import application.module.fight.attribute.AttributeTemplateIdContainer;
import application.module.fight.attribute.data.domain.Attribute;
import application.module.fight.attribute.data.domain.MonsterAttribute;
import application.module.fight.attribute.data.domain.TypeAttribute;
import application.module.fight.attribute.data.message.UpdateAttribute;
import application.module.fight.attribute.data.message.UpdateFightAttribute;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.data.message.SkillGetAllAttribute;
import application.module.player.data.message.event.PlayerLogin;
import application.module.scene.operate.event.CreateOrganismEntities;
import application.util.AttributeMapUtil;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.message.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static application.module.fight.attribute.AttributeTemplateId.*;

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

    private final static Map<Long, MonsterAttribute> monsterId2MonsterAttributeMap = new HashMap<>();

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case UpdateAttribute updateAttribute -> updateAttribute(updateAttribute);
            case UpdateFightAttribute updateFightAttribute -> updateFightAttribute(updateFightAttribute);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            case SkillGetAllAttribute skillGetAllAttribute -> skillGetAllAttribute(skillGetAllAttribute);
            case CreateOrganismEntities createOrganismEntities -> createOrganismEntities(createOrganismEntities);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
    }

    private void createOrganismEntities(CreateOrganismEntities createOrganismEntities) {
        createOrganismEntities.organismId().forEach(organismId -> {
            TypeAttribute typeAttribute = playerId2TypeAttributeMap.get(organismId);
            if (Objects.isNull(typeAttribute)) {
                typeAttribute = new TypeAttribute();
                playerId2TypeAttributeMap.put(organismId, typeAttribute);
            }
            returnClient(organismId, createOrganismEntities.client(), typeAttribute.getFightAttributeMap());
        });
    }

    private void skillGetAllAttribute(SkillGetAllAttribute skillGetAllAttribute) {
        UseSkillDataTemp useSkillDataTemp = skillGetAllAttribute.useSkillDataTemp();
        Map<Short, Long> map = new HashMap<>();
        map.put(MAX_HP, 1000L);
        map.put(MAX_MP, 1000L);
        map.put(VAR_HP, 1000L);
        map.put(VAR_MP, 1000L);
        map.put(ATTACK, 200L);
        map.put(ATTACK_DEFENCE, 100L);
        map.put(ATTACK_PIERCE, 100L);
        useSkillDataTemp.setAttackAttributeMap(map);

        useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            targetParameter.setAttributeMap(map);
        });
        this.sender().tell(skillGetAllAttribute, self());
    }

    private void playerLogin(PlayerLogin playerLogin) {

    }



    private void updateFightAttribute(UpdateFightAttribute updateFightAttribute) {
        long playerId = updateFightAttribute.playerId();
        TypeAttribute typeAttribute = playerId2TypeAttributeMap.get(playerId);
        if (Objects.isNull(typeAttribute)) {
            typeAttribute = new TypeAttribute();
            playerId2TypeAttributeMap.put(playerId, typeAttribute);
        }
        typeAttribute.addFightAttribute(updateFightAttribute.result());

        returnClient(playerId, updateFightAttribute.r().client(), typeAttribute.getFightAttributeMap());
    }

    private void updateAttribute(UpdateAttribute updateAttribute) {
        long playerId = updateAttribute.playerId();
        TypeAttribute typeAttribute = playerId2TypeAttributeMap.get(playerId);
        if (Objects.isNull(typeAttribute)) {
            typeAttribute = new TypeAttribute();
            playerId2TypeAttributeMap.put(playerId, typeAttribute);
        }

        typeAttribute.addAttribute(updateAttribute);

        returnClient(playerId, updateAttribute.r().client(), typeAttribute.getFightAttributeMap());
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

    private void returnClient(long fightOrganismId, ActorRef client, Map<Short, Long> attributeMap) {
        client.tell(new application.client.Client.SendToClientJ(AttributeProtocols.FIGHT_ATTRIBUTE_GET,
                AttributeProtocolBuilder.get10040(fightOrganismId, attributeMap)), self());
    }
}
