package application.module.player.fight.skill;

import akka.actor.ActorRef;
import application.module.player.Player;
import application.module.player.fight.skill.data.SkillData;
import application.module.player.fight.skill.data.entity.Skill;
import application.module.player.fight.skill.operate.PlayerSkillGetOpt;
import application.module.player.fight.skill.operate.SkillProcessUseScene;
import application.module.player.fight.skill.operate.SkillUseScene;
import application.module.player.util.IgnoreOpt;
import application.module.player.util.PlayerMgr;
import com.cala.orm.cache.AbstractBase;
import com.cala.orm.cache.message.DataGet;
import com.cala.orm.cache.message.DataInsert;
import com.cala.orm.message.OperateType;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.modular.client.Client;
import template.OrganismDataTemplate;
import template.OrganismDataTemplateHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-5-18
 * @Source 1.0
 */
public class SkillPlayerMgr implements PlayerMgr {

    private Skill skill;

    public final static List<Class<? extends OperateType>> operateTypeList;

    public static List<Integer> messageList;

    static {
        messageList = List.of(FightSkillProtocols.GET_ALL, FightSkillProtocols.LEARN, FightSkillProtocols.USE,
                FightSkillProtocols.CANCEL_SKILL, FightSkillProtocols.USE_PROCESS);
        operateTypeList = List.of(PlayerSkillGetOpt.class);
    }

    @Override
    public void receiver(Player player, AbstractBase abstractBase, OperateType p) {
        if (p instanceof PlayerSkillGetOpt playerSkillGetOpt) {
            playerSkillGetOpt(player, abstractBase, playerSkillGetOpt);
        }
    }

    private void playerSkillGetOpt(Player player, AbstractBase abstractBase, PlayerSkillGetOpt playerSkillGetOpt) {
        if (Objects.nonNull(abstractBase)) {
            this.skill = (Skill) abstractBase;
        } else {
            this.skill = Skill.of(player.getId());
            getSkillData(player).tell(new DataInsert(player.getPlayerActor(), skill,
                    IgnoreOpt.INSTANCE), player.getPlayerActor());
        }
        OrganismDataTemplate organismDataTemplate = OrganismDataTemplateHolder.getData(player.getPlayerEntity().getProfession());
        Set<Integer> set = new HashSet<>();
        for (int skillId : organismDataTemplate.skills()) {
            set.add(skillId);
        }
        this.skill.getEnableSkillSet().addAll(set);
        player.addInitFinalSet(this.getClass());
    }

    @Override
    public void receiver(Player player, Client.ReceivedFromClient r) {
        try {
            switch (r.protoID()) {
                case FightSkillProtocols.USE -> use(player, r);
                case FightSkillProtocols.USE_PROCESS -> process(player, r);
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }

    private void process(Player player, Client.ReceivedFromClient r) throws InvalidProtocolBufferException {
        protocol.Skill.CS10055 cs10055 = protocol.Skill.CS10055.parseFrom(r.message());
        player.getScene().tell(new SkillProcessUseScene(r, cs10055), player.getPlayerActor());
    }

    private void use(Player player, Client.ReceivedFromClient r) throws InvalidProtocolBufferException {
        protocol.Skill.CS10052 cs10052 = protocol.Skill.CS10052.parseFrom(r.message());
        player.getScene().tell(new SkillUseScene(r, cs10052), player.getPlayerActor());
    }

    @Override
    public void init(Player player) {
        getSkillData(player).tell(new DataGet(player.getPlayerActor(), Skill.of(player.getId()),
                new PlayerSkillGetOpt()), player.getPlayerActor());
    }

    @Override
    public void destroy(Player player) {

    }


    public ActorRef getSkillData(Player player) {
        return player.getDataMap().get(SkillData.class);
    }

    @Override
    public List<Integer> getProtoIds() {
        return messageList;
    }

    @Override
    public List<Class<? extends OperateType>> getOperateTypes() {
        return operateTypeList;
    }

    public Skill getSkill() {
        return skill;
    }
}
