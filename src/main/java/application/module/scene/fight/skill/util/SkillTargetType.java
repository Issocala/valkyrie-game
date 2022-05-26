package application.module.scene.fight.skill.util;

import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-2-23
 * @Source 1.0
 */
public interface SkillTargetType {

    /**
     * 自己
     */
    byte OWNER = 0;
    /**
     * 目标
     */
    byte TARGET = 1;
    /**
     * 全体友方
     */
    byte ALL_FRIEND = 2;
    /**
     * 队伍
     */
    byte TEAM = 3;
    /**
     * 工会
     */
    byte UNION = 4;

    static boolean isOwner(FightSkillProcessTemplate fightSkillProcessTemplate) {
        return fightSkillProcessTemplate.skillTargetType() == OWNER;
    }

}
