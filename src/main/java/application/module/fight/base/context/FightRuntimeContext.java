package application.module.fight.base.context;

import application.module.fight.attribute.AttributeTemplateId;
import application.module.fight.attribute.data.message.UpdateFightAttribute;
import application.module.fight.attribute.fight.FightAttributeMgr;
import application.module.fight.base.skill.FightSkillWrap;
import application.module.fight.base.skill.FightSkillWrapContainer;
import application.module.fight.base.skill.SkillCDController;
import application.module.fight.base.skill.invoker.FightSkillInvoker;
import template.FightSkillTemplate;

/**
 * 战斗上下文数据
 *
 * @author Luo Yong
 * @date 2022-2-22
 * @Source 1.0
 */
public class FightRuntimeContext {

    /**
     * 技能cd控制器
     */
    private final SkillCDController skillCDController = new SkillCDController();

    /**
     * 战斗数据
     */
    private final FightAttributeMgr fightAttributeMgr = new FightAttributeMgr();

    public void castSkill(FightSkillWrap fightSkillWrap, UseSkillDataTemp useSkillDataTemp) {
        if (!inCDTime(fightSkillWrap)) {
            return;
        }
        FightSkillTemplate fightSkillTemplate = fightSkillWrap.getFightSkillTemplate();
        if (fightAttributeMgr.getValue(AttributeTemplateId.MAX_HP) <= fightSkillTemplate.costHp() && fightAttributeMgr.getValue(AttributeTemplateId.MAX_MP) < fightSkillTemplate.costMp()) {
            //TODO 考虑是否需要返回客户端错误信息
            return;
        }
        skillCDController.startCD(fightSkillTemplate);

        FightSkillInvoker.INSTANCE.invokeActive(fightSkillWrap, useSkillDataTemp);

    }

    public boolean inCDTime(FightSkillWrap fightSkillWrap) {
        return skillCDController.inCDTime(fightSkillWrap);
    }

    public SkillCDController getSkillCDController() {
        return skillCDController;
    }

    public FightAttributeMgr getFightAttributeMgr() {
        return fightAttributeMgr;
    }

    public void updateFightAttribute(UpdateFightAttribute updateFightAttribute) {
        fightAttributeMgr.addTemplateAttributeMap(updateFightAttribute.result());
    }

    public void skillUse(UseSkillDataTemp useSkillDataTemp) {
        FightSkillWrap fightSkillWrap = FightSkillWrapContainer.getFightSkillWrap(useSkillDataTemp.getSkillId());
        castSkill(fightSkillWrap, useSkillDataTemp);
    }

    public void tellClient() {

    }
}
