package application.module.fight.skill.base.skill;

import template.FightSkillProcessTemplate;
import template.FightSkillTemplate;

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

    private final List<FightSkillProcessTemplate> list;

    private final FightSkillTemplate fightSkillTemplate;

    public FightPassiveSkillWrap(FightSkillTemplate fightSkillTemplate) {
        list = new ArrayList<>();
        this.fightSkillTemplate = fightSkillTemplate;
    }

    public void addFightSkillProcessTemplate(FightSkillProcessTemplate fightSkillProcessTemplate) {
        list.add(fightSkillProcessTemplate);
    }

    public List<FightSkillProcessTemplate> getList() {
        return list;
    }

    public FightSkillTemplate getFightSkillTemplate() {
        return fightSkillTemplate;
    }
}
