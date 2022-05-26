package application.module.player.fight.skill.data;

import akka.actor.Props;
import application.client.Client;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.fight.skill.FightSkillProtocols;
import application.module.player.fight.skill.data.entity.Skill;
import application.module.player.fight.skill.data.message.SkillIsLearn;
import application.module.player.operate.PlayerLoginDbReturn;
import application.module.scene.fight.skill.FightSkillProtocolBuilder;
import application.module.scene.fight.skill.operate.SkillIsLearnType;
import application.util.DataMessageAndReply;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.db.message.DbInsert;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.util.RuntimeResult;
import template.OrganismDataTemplate;
import template.OrganismDataTemplateHolder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-2-18
 * @Source 1.0
 */
public class SkillData extends AbstractDataCacheManager<Skill> {


    public static Props create() {
        return Props.create(SkillData.class, SkillData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    private SkillData() {
    }

    @Override
    protected void dataInit(DataInit dataInit) {
    }

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case SkillIsLearn skillIsLearn -> skillIsLearn(skillIsLearn);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
    }

    private void playerLogin(PlayerLogin playerLogin) {
        long playerId = playerLogin.playerInfo().id();

        OrganismDataTemplate organismDataTemplate = OrganismDataTemplateHolder.getData(playerLogin.playerInfo().profession());

        // TODO: 2022-5-2 暂定所有技能已经学习
        Set<Integer> set = new HashSet<>();
        for (int skillId : organismDataTemplate.skills()) {
            set.add(skillId);
        }
        playerLogin.r().client().tell(new Client.SendToClientJ(FightSkillProtocols.GET_ALL,
                FightSkillProtocolBuilder.getSc10050(playerId, set)), self());

//        Skill skill = (Skill) get(playerId);
//        if (Objects.isNull(skill)) {
//            getDbManager().tell(new DbGet(self(), new Skill(playerId), new PlayerLoginDbReturn(playerLogin)), this.self());
//        } else {
//            playerLogin.r().client().tell(new Client.SendToClientJ(FightSkillProtocols.GET_ALL,
//                    FightSkillProtocolBuilder.getSc10050(playerId, skill.getEnableSkillSet())), self());
//        }
    }

    @Override
    protected void afterDbReturnMessage(DBReturnMessage dbReturnMessage) {
        switch (dbReturnMessage.operateType()) {
            case PlayerLoginDbReturn playerLoginDbReturn -> playerLoginDbReturn(dbReturnMessage, playerLoginDbReturn);
            default -> {}
        }
        super.afterDbReturnMessage(dbReturnMessage);
    }

    private void playerLoginDbReturn(DBReturnMessage dbReturnMessage, PlayerLoginDbReturn playerLoginDbReturn) {
        PlayerLogin playerLogin = playerLoginDbReturn.playerLogin();
        long playerId = playerLogin.playerInfo().id();
        Skill skill = (Skill) dbReturnMessage.abstractEntityBase();
        if (skill == null) {
            skill = Skill.of(playerId);
            OrganismDataTemplate organismDataTemplate = OrganismDataTemplateHolder.getData(playerLogin.playerInfo().profession());
            for (int skillId : organismDataTemplate.skills()) {
                skill.getEnableSkillSet().add(skillId);
            }
            this.put(playerId, skill);
            getDbManager().tell(new DbInsert(self(), skill, playerLoginDbReturn, false), self());
        }
        playerLogin.r().client().tell(new Client.SendToClientJ(FightSkillProtocols.GET_ALL,
                FightSkillProtocolBuilder.getSc10050(playerId, skill.getEnableSkillSet())), self());
    }

    private void skillIsLearn(SkillIsLearn skillIsLearn) {
        // TODO: 2022-5-2 暂定所有技能已经学习
        DataMessageAndReply dataMessageAndReply = skillIsLearn.dataMessageAndReply();
        SkillIsLearnType skillIsLearnType = (SkillIsLearnType) dataMessageAndReply.operateType();
        dataMessageAndReply.ref().tell(new DataReturnMessage(RuntimeResult.ok(), null,
                skillIsLearnType), self());

//        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillIsLearnType.operateTypeInfo();
//        protocol.Skill.CS10052 cs10052 = (protocol.Skill.CS10052) commonOperateTypeInfo.message();
//        long id = commonOperateTypeInfo.r().uID();
//        Skill skill = (Skill) get(id);
//        if (Objects.isNull(skill)) {
//            return;
//        }
//        if (skill.isEnableSkill(cs10052.getSkillId())) {
//            dataMessageAndReply.ref().tell(new DataReturnMessage(RuntimeResult.ok(), null,
//                    skillIsLearnType), self());
//        } else {
//            dataMessageAndReply.ref().tell(new DataReturnMessage(RuntimeResult.runtimeError("技能未学习!"),
//                    null, skillIsLearnType), self());
//        }
    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == Skill.class;
    }
}
