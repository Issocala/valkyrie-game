package application.module.fight.skill;

import application.module.GameProtocols;

/**
 * 技能
 *
 * @author Luo Yong
 * @date 2022-2-18
 * @Source 1.0
 */
public interface FightSkillProtocols {

    /**
     * 获取全部技能
     */
    int GET_ALL = GameProtocols.SKILL;

    /**
     * 学习技能
     */
    int LEARN = GameProtocols.SKILL + 1;

    /**
     * 使用技能
     */
    int USE_SKILL = GameProtocols.SKILL + 2;


}
