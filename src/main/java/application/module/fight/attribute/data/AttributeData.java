package application.module.fight.attribute.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.fight.attribute.AttributeProtocolBuilder;
import application.module.fight.attribute.AttributeProtocols;
import application.module.fight.attribute.AttributeTemplateIdContainer;
import application.module.fight.attribute.data.domain.Attribute;
import application.module.fight.attribute.data.domain.FightAttribute;
import application.module.fight.attribute.data.domain.TypeAttribute;
import application.module.fight.attribute.data.message.*;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.data.message.SkillGetAllAttribute;
import application.module.organism.NpcOrganism;
import application.module.organism.Organism;
import application.module.organism.OrganismType;
import application.module.player.data.message.event.PlayerLogin;
import application.module.scene.operate.AoiSendMessageToClient;
import application.module.scene.operate.event.CreateOrganismEntityAfter;
import application.module.scene.operate.event.CreatePlayerEntitiesAfter;
import application.util.AttributeMapUtil;
import application.util.StringUtils;
import application.util.UpdateAttributeObject;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.Publish;
import template.OrganismDataTemplate;
import template.OrganismDataTemplateHolder;

import java.util.*;

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

    private final Map<Long, TypeAttribute> playerId2TypeAttributeMap = new HashMap<>();

    private final Map<Long, FightAttribute> monsterId2MonsterAttributeMap = new HashMap<>();

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case UpdateAttribute updateAttribute -> updateAttribute(updateAttribute);
            case UpdateFightAttribute updateFightAttribute -> updateFightAttribute(updateFightAttribute);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            case SkillGetAllAttribute skillGetAllAttribute -> skillGetAllAttribute(skillGetAllAttribute);
            case CreatePlayerEntitiesAfter createPlayerEntitiesAfter -> createPlayerEntitiesAfter(createPlayerEntitiesAfter);
            case CreateOrganismEntityAfter createOrganismEntityAfter -> createOrganismEntityAfter(createOrganismEntityAfter);
            case AddHp addHp -> addHp(addHp);
            case AddMp addMp -> addMp(addMp);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
    }

    private void addMp(AddMp addMp) {
        Map<Short, Long> fightAttributeMap = getFightAttributeMap(addMp.organismId());
        long mp = addMp.mp();
        if (mp == 0) {
            return;
        }
        long currMp = fightAttributeMap.getOrDefault(VAR_MP, 0L) + mp;
        if (currMp < 0) {
            currMp = 0;
        }
        currMp = Math.min(fightAttributeMap.getOrDefault(MAX_MP, 0L), currMp);
        fightAttributeMap.put(VAR_MP, currMp);
        addMp.scene().tell(new AoiSendMessageToClient(AttributeProtocols.FIGHT_ATTRIBUTE_GET,
                AttributeProtocolBuilder.get10040(addMp.organismId(), fightAttributeMap), addMp.organismId()), self());
    }

    private void addHp(AddHp addHp) {
        Map<Short, Long> fightAttributeMap = getFightAttributeMap(addHp.organismId());
        long hp = addHp.hp();
        if (hp == 0) {
            return;
        }
        long currHp = fightAttributeMap.getOrDefault(VAR_HP, 0L) + hp;
        if (currHp < 0) {
            currHp = 0;
            if (addHp.organismType() == OrganismType.PLAYER) {
                self().tell(new Publish(new PlayerDead(addHp.organismId(), addHp.sourceId())), self());
            }
        }
        currHp = Math.min(fightAttributeMap.getOrDefault(MAX_HP, 0L), currHp);
        fightAttributeMap.put(VAR_HP, currHp);
        addHp.scene().tell(new AoiSendMessageToClient(AttributeProtocols.FIGHT_ATTRIBUTE_GET,
                AttributeProtocolBuilder.get10040(addHp.organismId(), fightAttributeMap), addHp.organismId()), self());
    }

    private void createOrganismEntityAfter(CreateOrganismEntityAfter createOrganismEntityAfter) {
        Organism organism = createOrganismEntityAfter.organism();
        if (organism.getOrganismType() == OrganismType.NPC) {
            NpcOrganism npcOrganism = (NpcOrganism) organism;
            OrganismDataTemplate organismDataTemplate = OrganismDataTemplateHolder.getData(npcOrganism.getOrganismTemplateId());
            FightAttribute fightAttribute = new FightAttribute(npcOrganism, StringUtils.toAttributeMap(organismDataTemplate.attributeMap()));
            putMonsterFight(npcOrganism.getId(), fightAttribute);
            returnClient(npcOrganism.getId(), createOrganismEntityAfter.clients(), fightAttribute.getFightAttributeMap());
        }
    }

    private void createPlayerEntitiesAfter(CreatePlayerEntitiesAfter createPlayerEntitiesAfter) {
        long playerId = createPlayerEntitiesAfter.playerId();
        Map<Long, ActorRef> clientMap = createPlayerEntitiesAfter.clientMap();

        createPlayerEntitiesAfter.organisms().forEach(organism -> {
            returnClient(organism.getId(), clientMap.get(playerId), getFightAttributeMap(organism));
        });
    }

    private void skillGetAllAttribute(SkillGetAllAttribute skillGetAllAttribute) {
        UseSkillDataTemp useSkillDataTemp = skillGetAllAttribute.useSkillDataTemp();
        useSkillDataTemp.setAttackAttributeMap(getFightAttributeMap(useSkillDataTemp.getAttackId()));

        useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            targetParameter.setAttributeMap(getFightAttributeMap(targetParameter.getTargetId()));
        });
        this.sender().tell(skillGetAllAttribute, self());
    }

    private void playerLogin(PlayerLogin playerLogin) {
        long playerId = playerLogin.playerInfo().id();
        self().tell(new UpdateAttribute(playerId, (short) 2, new UpdateAttributeObject<>(1), new ArrayList<>()), self());
    }

    private void updateFightAttribute(UpdateFightAttribute updateFightAttribute) {
        long organismId = updateFightAttribute.organismId();
        TypeAttribute typeAttribute = playerId2TypeAttributeMap.get(organismId);
        if (Objects.isNull(typeAttribute)) {
            FightAttribute fightAttribute = monsterId2MonsterAttributeMap.get(organismId);
            fightAttribute.addFightAttribute(updateFightAttribute.result());
        }else {
            typeAttribute.addFightAttribute(updateFightAttribute.result());
        }
        updateFightAttribute.scene().tell(new AoiSendMessageToClient(AttributeProtocols.FIGHT_ATTRIBUTE_GET,
                AttributeProtocolBuilder.get10040(organismId, getFightAttributeMap(organismId)), organismId), self());
    }

    private void updateAttribute(UpdateAttribute updateAttribute) {
        long playerId = updateAttribute.playerId();
        TypeAttribute typeAttribute = playerId2TypeAttributeMap.get(playerId);
        if (Objects.isNull(typeAttribute)) {
            typeAttribute = new TypeAttribute();
            playerId2TypeAttributeMap.put(playerId, typeAttribute);
        }

        typeAttribute.addAttribute(updateAttribute);

        returnClient(playerId, updateAttribute.clients(), typeAttribute.getFightAttributeMap());
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

    private void returnClient(long fightOrganismId, Collection<ActorRef> clients, Map<Short, Long> attributeMap) {
        clients.forEach(client -> returnClient(fightOrganismId, client, attributeMap));
    }

    private void returnClient(long fightOrganismId, ActorRef client, Map<Short, Long> attributeMap) {
        if (Objects.nonNull(client)) {
            client.tell(new application.client.Client.SendToClientJ(AttributeProtocols.FIGHT_ATTRIBUTE_GET,
                    AttributeProtocolBuilder.get10040(fightOrganismId, attributeMap)), self());
        }
    }

    public Map<Short, Long> getFightAttributeMap(long organismId) {
        TypeAttribute typeAttribute = playerId2TypeAttributeMap.get(organismId);
        if (Objects.nonNull(typeAttribute)) {
            return typeAttribute.getFightAttributeMap();
        } else {
            FightAttribute fightAttribute = monsterId2MonsterAttributeMap.get(organismId);
            if (Objects.nonNull(fightAttribute)) {
                return fightAttribute.getFightAttributeMap();
            }
        }
        return new HashMap<>();
    }

    public Map<Short, Long> getFightAttributeMap(Organism organism) {
        if (organism.getOrganismType() == OrganismType.PLAYER) {
            TypeAttribute typeAttribute = playerId2TypeAttributeMap.get(organism.getId());
            return typeAttribute.getFightAttributeMap();
        } else if (organism.getOrganismType() == OrganismType.MONSTER) {
            FightAttribute fightAttribute = monsterId2MonsterAttributeMap.get(organism.getId());
            return fightAttribute.getFightAttributeMap();
        }
        return new HashMap<>();
    }

    public void putMonsterFight(long organismId, FightAttribute fightAttribute) {
        this.monsterId2MonsterAttributeMap.put(organismId, fightAttribute);
    }
}
