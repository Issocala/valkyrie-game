package application.module.fight.skill.base.skill;

import template.FightPassiveSkillProcessTemplate;
import template.FightPassiveSkillTemplate;
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

    private final List<FightPassiveSkillProcessTemplate> list;

    private final FightPassiveSkillTemplate fightPassiveSkillTemplate;

    public FightPassiveSkillWrap(FightPassiveSkillTemplate fightPassiveSkillTemplate) {
        list = new ArrayList<>();
        this.fightPassiveSkillTemplate = fightPassiveSkillTemplate;
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
}
