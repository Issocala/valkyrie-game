package application.module.scene.fight.skill.util;

import template.FightSkillTemplate;

/**
 * @author Luo Yong
 * @date 2022-2-23
 * @Source 1.0
 */
public interface SkillAimType {

    /**
     * 单一目标
     */
    byte ONE = 0;
    /**
     * AOE圆形
     */
    byte AOE_ROUNDNESS = 1;
    /**
     * AOE矩形
     */
    byte AOE_RECTANGLE = 2;
    /**
     * AOE扇形
     */
    byte AOE_ANNULUS = 3;

    static boolean isOne(FightSkillTemplate skillTemplate) {
        return skillTemplate.skillAimType() == ONE;
    }


}
