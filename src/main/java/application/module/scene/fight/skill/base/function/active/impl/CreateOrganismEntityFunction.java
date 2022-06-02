package application.module.scene.fight.skill.base.function.active.impl;

import akka.actor.Props;
import application.module.organism.*;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.scene.fight.skill.base.skill.FightSkillWrap;
import application.util.StringUtils;
import application.util.Vector3;
import template.FightSkillProcessTemplate;
import template.OrganismDataTemplateHolder;

/**
 * @author Luo Yong
 * @date 2022-4-20
 * @Source 1.0
 */
public class CreateOrganismEntityFunction implements FightSkillActiveFunction {

    public static Props create() {
        return Props.create(CreateOrganismEntityFunction.class);
    }

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        int[] attributeParameter = StringUtils.toIntArray(fightSkillProcessTemplate.attributeParameter());
        int organismTemplateId = attributeParameter[0];
        int number = attributeParameter[1];
        long duration = attributeParameter[2];
        short type = OrganismDataTemplateHolder.getData(organismTemplateId).type();
        switch (type) {
            case OrganismType.MONSTER:
                for (int i = 0; i < number; i++) {
                    MonsterOrganism monster = MonsterOrganism.of(useSkillDataTemp.getScene(), organismTemplateId,
                            Vector3.ofXY(useSkillDataTemp.getSkillPosX(), useSkillDataTemp.getSkillPosY()));
                    useSkillDataTemp.getScene().getMonsterSceneMgr().addMonsterOrganism(monster, duration);
                }
                break;
            case OrganismType.NPC:
                for (int i = 0; i < number; i++) {
                    NpcOrganism npc = NpcOrganism.of(useSkillDataTemp.getScene(), organismTemplateId,
                            Vector3.ofXY(useSkillDataTemp.getSkillPosX(), useSkillDataTemp.getSkillPosY()));
                    useSkillDataTemp.getScene().getNpcSceneMgr().addNpcOrganism(npc, duration);
                }
                break;
            case OrganismType.ITEM:
                for (int i = 0; i < number; i++) {
                    ItemOrganism item = ItemOrganism.of(useSkillDataTemp.getScene(), organismTemplateId,
                            Vector3.ofXY(useSkillDataTemp.getSkillPosX(), useSkillDataTemp.getSkillPosY()));
                    useSkillDataTemp.getScene().getItemSceneMgr().addItemOrganism(item, duration);
                }
                break;
            case OrganismType.EFFECT:
                for (int i = 0; i < number; i++) {
                    EffectOrganism effect = EffectOrganism.of(useSkillDataTemp.getScene(), organismTemplateId,
                            Vector3.ofXY(useSkillDataTemp.getSkillPosX(), useSkillDataTemp.getSkillPosY()));
                    useSkillDataTemp.getScene().getEffectSceneMgr().addEffectOrganism(effect, duration);
                }
                break;
        }
    }
}
