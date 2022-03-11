package application.module.fight.skill.util;

import template.FightSkillTemplate;

/**
 * @author Luo Yong
 * @date 2022-2-22
 * @Source 1.0
 */
public interface SkillType {

    /**
     * 主动技能
     */
    byte ACTIVE = 1;
    /**
     * 被动技能
     */
    byte PASSIVE = 2;

    static boolean isActive(FightSkillTemplate fightSkillTemplate) {
        return fightSkillTemplate.skillType() == ACTIVE;
    }

}
