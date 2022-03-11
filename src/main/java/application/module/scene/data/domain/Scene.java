package application.module.scene.data.domain;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import application.module.fight.base.context.UseSkillDataTemp;
import application.module.fight.skill.data.message.SkillUse;
import application.module.fight.skill.util.SkillAimType;
import application.module.scene.operate.info.SkillUseInfo;
import application.util.CommonOperateTypeInfo;
import application.util.Vector;
import protocol.Skill;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public class Scene extends UntypedAbstractActor {

    /**
     * key: FightOrganismId
     */
    private final Map<Long, ActorRef> fightSkillRuntimeContextMap = new HashMap<>();

    /**
     * key: FightOrganismId
     * value: FightOrganism场景坐标等数据，类暂定
     */
    //TODO Vector 暂定
    private final Map<Integer, Vector> vectorMap = new HashMap<>();

    public static Props create() {
        return Props.create(Scene.class);
    }

    @Override
    public void onReceive(Object message) {
        switch (message) {
            case SkillUse skillUse -> skillUse(skillUse);
            default -> throw new IllegalStateException("Unexpected value: " + message);
        }
    }

    private void skillUse(SkillUse skillUse) {
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUse.operateTypeInfo();
        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
        ActorRef actorRef = this.fightSkillRuntimeContextMap.get(cs10052.getFightOrganismId());
        if (Objects.isNull(actorRef)) {
            return;
        }

        actorRef.tell(new SkillUse(new SkillUseInfo(self(), getTarget(cs10052), commonOperateTypeInfo.r())), self());

    }

    private UseSkillDataTemp getTarget(Skill.CS10052 cs10052) {
        FightSkillTemplate fightSkillTemplate = FightSkillTemplateHolder.getData(cs10052.getSkillId());
        UseSkillDataTemp useSkillDataTemp = UseSkillDataTemp.of(cs10052);
        useSkillDataTemp.setScene(self());
        //TODO 获取目标
        if (SkillAimType.isOne(fightSkillTemplate)) {
        }
        return useSkillDataTemp;
    }
}
