package application.module.scene.fight.skill.base.skill;

import application.condition.core.ConditionBase;
import application.condition.util.ConditionParser;
import template.FightPassiveSkillProcessTemplate;
import template.FightPassiveSkillTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 技能静态数据二次封装类
 *
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class FightPassiveSkillWrap {

    private final List<FightPassiveSkillProcessTemplate> list;

    private final FightPassiveSkillTemplate fightPassiveSkillTemplate;

    private final ConditionBase condition;

    public FightPassiveSkillWrap(FightPassiveSkillTemplate template) {
        list = new ArrayList<>();
        this.fightPassiveSkillTemplate = template;
        condition = ConditionParser.parseCondition(template.condition());
    }

    public void add(FightPassiveSkillProcessTemplate fightPassiveSkillProcessTemplate) {
        list.add(fightPassiveSkillProcessTemplate);
    }

    public List<FightPassiveSkillProcessTemplate> getList() {
        return list;
    }

    public FightPassiveSkillTemplate getFightPassiveSkillTemplate() {
        return fightPassiveSkillTemplate;
    }

    public ConditionBase getCondition() {
        return condition;
    }
}
