package application.module.player.fight.attribute;

import application.module.organism.OrganismType;
import application.module.player.Player;
import application.module.player.fight.attribute.data.AttributeData;
import application.module.player.fight.attribute.data.entity.Attribute;
import application.module.player.fight.attribute.data.message.UpdateFightAttribute;
import application.module.player.fight.attribute.node.AttributeNode;
import application.module.player.fight.attribute.operate.PlayerGetAttributeOpt;
import application.module.player.fight.attribute.provider.AttributeRegister;
import application.module.player.util.PlayerMgr;
import application.module.scene.fight.attribute.FightAttributeMgr;
import com.cala.orm.cache.AbstractBase;
import com.cala.orm.cache.message.DataGet;
import com.cala.orm.message.OperateType;
import mobius.modular.client.Client;
import template.AttributeTemplate;
import template.AttributeTemplateHolder;
import template.AttributeTreeTemplate;
import template.AttributeTreeTemplateHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static application.module.player.fight.attribute.AttributeTemplateId.*;

/**
 * @author Luo Yong
 * @date 2022-5-13
 * @Source 1.0
 */
public class AttributePlayerMgr implements PlayerMgr {

    private final Map<Short, AttributeNode> typeId2AttributeMap = new HashMap<>();

    private final Map<Short, Long> allTemplateAttributeMap = new HashMap<>();

    public final static List<Class<? extends OperateType>> operateTypeList;

    public final static List<Integer> messageList;

    static {
        messageList = List.of(AttributeProtocols.FIGHT_ATTRIBUTE_GET);

        operateTypeList = List.of(PlayerGetAttributeOpt.class);
    }

    @Override
    public void receiver(Player player, OperateType operateType) {

    }

    private void getAttributeOpt(Player player, PlayerGetAttributeOpt get) {
        player.addInitFinalSet(this.getClass());
    }


    public void updateAttribute(Player player, short type) {
        updateAttribute(player, type, true);
    }

    /**
     * 更新属性
     *
     * @param isSendFighting 除了玩家初始化的时候，其他时候都应该是true
     */
    public void updateAttribute(Player player, short type, boolean isSendFighting) {
        AttributeNode attributeNode = typeId2AttributeMap.get(type);
        if (Objects.isNull(attributeNode)) {
            AttributeTreeTemplate attributeTreeTemplate = AttributeTreeTemplateHolder.getData(type);
            attributeNode = new AttributeNode(type, new HashMap<>(), AttributeRegister.getFightAttributeProvider(attributeTreeTemplate.className()));
            typeId2AttributeMap.put(type, attributeNode);
        }
        Map<Short, Long> id2FightAttributeMap = attributeNode.update(player);

        FightAttributeMgr.doAddAttribute(allTemplateAttributeMap, id2FightAttributeMap);

        if (isSendFighting) {
            calculateAllFighting(player);
        }
    }


    /**
     * 计算更新战力
     */
    public long calculateAllFighting(Player player) {
        // TODO: 2022-6-2 需要添加处理发送战力给客户端
        long fighting = calculateAllFighting(allTemplateAttributeMap);
        return fighting;
    }

    /**
     * 计算战力
     */
    public long calculateAllFighting(Map<Short, Long> map) {
        long fighting;
        long baseCal = 0;
        long attributeType1 = 0;
        long attributeType2 = 0;
        for (Map.Entry<Short, Long> entry : map.entrySet()) {
            short id = entry.getKey();
            AttributeTemplate template = AttributeTemplateHolder.getData(id);
            if (Objects.isNull(template)) {
                continue;
            }
            long value = FightAttributeMgr.getValue(map, id);
            float baseCalTemp = template.baseCal() * value;
            if (template.attributeType() == 1) {
                attributeType1 += baseCalTemp;
            } else if (template.attributeType() == 2) {
                attributeType2 += template.extraCal() * value;
            }
            baseCal += baseCalTemp;
        }
        fighting = baseCal + attributeType1 / 640000 * attributeType2;
        return fighting;
    }

    public Map<Short, Long> getPlayerInitFightAttributeMap() {
        Map<Short, Long> fightAttributeMap = new HashMap<>(allTemplateAttributeMap);
        fightAttributeMap.put(VAR_HP, FightAttributeMgr.getValue(fightAttributeMap, MAX_HP));
        fightAttributeMap.put(VAR_MP, FightAttributeMgr.getValue(fightAttributeMap, MAX_MP));
        return fightAttributeMap;
    }

    public void initAfter(Player player) {
        updateAttribute(player, (short) 2, false);
    }

    @Override
    public void receiver(Player player, AbstractBase abstractBase, OperateType p) {
        if (p instanceof PlayerGetAttributeOpt get) {
            getAttributeOpt(player, get);
        } else {
            throw new IllegalStateException("Unexpected value: " + p.getClass().getName());
        }
    }

    @Override
    public void receiver(Player player, List<AbstractBase> abstractBases, OperateType p) {

    }

    @Override
    public void receiver(Player player, Client.ReceivedFromClient r) {

    }

    @Override
    public void init(Player player) {
        player.getDataMap().get(AttributeData.class).tell(new DataGet(player.getPlayerActor(),
                Attribute.of(player.getId()), new PlayerGetAttributeOpt()), player.getPlayerActor());
    }

    @Override
    public void destroy(Player player) {

    }

    @Override
    public List<Integer> getProtoIds() {
        return messageList;
    }

    @Override
    public List<Class<? extends OperateType>> getOperateTypes() {
        return operateTypeList;
    }

}
