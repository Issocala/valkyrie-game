package application.module.scene.fight.skill.base.context;

import application.module.scene.fight.skill.base.skill.FightSkillWrap;
import application.module.scene.fight.skill.base.skill.SkillCDController;
import template.FightPassiveSkillTemplate;
import template.FightSkillTemplate;

import java.util.HashMap;
import java.util.Map;

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

    private final Map<Long, DelaySkillProcessData> delaySkillProcessDataMap = new HashMap<>();

    public boolean inCDTime(FightSkillWrap fightSkillWrap) {
        return skillCDController.inCDTime(fightSkillWrap);
    }

    public boolean inCDTime(FightPassiveSkillTemplate fightPassiveSkillTemplate) {
        return skillCDController.inCDTime(fightPassiveSkillTemplate);
    }

    public SkillCDController getSkillCDController() {
        return skillCDController;
    }

    public void startCD(FightSkillTemplate fightSkillTemplate) {
        skillCDController.startCD(fightSkillTemplate);
    }

    public void startCD(FightPassiveSkillTemplate template) {
        skillCDController.startCD(template);
    }

    public static void passiveTrigger(byte triggerType, PassiveSkillDataTemp passiveSkillDataTemp) {

    }

}
