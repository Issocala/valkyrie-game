package application.module.fight.skill.data;

import akka.actor.Props;
import application.module.fight.skill.data.domain.Skill;
import application.module.fight.skill.data.message.SkillIsLearn;
import application.module.fight.skill.operate.SkillIsLearnType;
import application.util.CommonOperateTypeInfo;
import application.util.DataMessageAndReply;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.util.RuntimeResult;

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
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case SkillIsLearn skillIsLearn -> skillIsLearn(skillIsLearn);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
    }

    private void skillIsLearn(SkillIsLearn skillIsLearn) {
        DataMessageAndReply dataMessageAndReply = skillIsLearn.dataMessageAndReply();
        SkillIsLearnType skillIsLearnType = (SkillIsLearnType) dataMessageAndReply.operateType();
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillIsLearnType.operateTypeInfo();
        protocol.Skill.CS10052 cs10052 = (protocol.Skill.CS10052) commonOperateTypeInfo.message();
        long id = commonOperateTypeInfo.r().uID();
        Skill skill = null;
        if (skill.isSkill(cs10052.getSkillId())) {
            dataMessageAndReply.ref().tell(new DataReturnMessage(RuntimeResult.ok(), null,
                    skillIsLearnType), self());
        } else {
            dataMessageAndReply.ref().tell(new DataReturnMessage(RuntimeResult.runtimeError("技能未学习!"),
                    null, skillIsLearnType), self());
        }
    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == Skill.class;
    }
}
