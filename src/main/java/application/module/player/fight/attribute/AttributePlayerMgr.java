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

    /**
     * 更新属性
     *
     * @param isSendFightToScene 除了玩家初始化的时候，其他时候都应该是true
     */
    public void updateAttribute(Player player, short type, boolean isSendFightToScene) {
        AttributeNode attributeNode = typeId2AttributeMap.get(type);
        if (Objects.isNull(attributeNode)) {
            AttributeTreeTemplate attributeTreeTemplate = AttributeTreeTemplateHolder.getData(type);
            attributeNode = new AttributeNode(type, new HashMap<>(), AttributeRegister.getFightAttributeProvider(attributeTreeTemplate.className()));
            typeId2AttributeMap.put(type, attributeNode);
        }
        Map<Short, Long> id2FightAttributeMap = attributeNode.update(player);

        FightAttributeMgr.doAddAttribute(allTemplateAttributeMap, id2FightAttributeMap);

        if (isSendFightToScene) {
            player.getScene().tell(new UpdateFightAttribute(id2FightAttributeMap, player.getId(), OrganismType.PLAYER), player.getPlayerActor());
        }
        calculateAllFighting();
    }

    /**
     * 计算更新战力
     */
    public long calculateAllFighting() {
        long fighting = 0;
        allTemplateAttributeMap.forEach((id, value) -> {

            if (AttributeTemplateIdContainer.VALUE.contains(id)) {

            } else {

            }
        });
        return fighting;
    }

    public Map<Short, Long> getFightAttributeMap() {
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
