package application.module.fight.skill.base.function.active.impl;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.fight.skill.base.skill.FightSkillWrap;
import application.module.organism.MonsterOrganism;
import application.module.scene.data.domain.PositionInfo;
import application.module.scene.operate.SceneOrganismEntry;
import application.util.StringUtils;
import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-4-20
 * @Source 1.0
 */
public class CreateOrganismEntityFunction extends FightSkillActiveFunction {

    public static Props create() {
        return Props.create(CreateOrganismEntityFunction.class);
    }

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        int[] attributeParameter = StringUtils.toIntArray(fightSkillProcessTemplate.attributeParameter());
        int organismTemplateId = attributeParameter[0];
        int number = attributeParameter[1];
        long duration = attributeParameter[2];
        for (int i = 0; i < number; i++) {
            useSkillDataTemp.getScene().tell(new SceneOrganismEntry(new MonsterOrganism(organismTemplateId),
                    new PositionInfo(useSkillDataTemp.getSkillPosX(), useSkillDataTemp.getSkillPosY()), duration), ActorRef.noSender());
        }
    }
}
