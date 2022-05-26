package application.module.player.fight.skill;

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
    int USE = GameProtocols.SKILL + 2;

    /**
     * 技能战报结果
     */
    int USE_RESULT = GameProtocols.SKILL + 3;

    /**
     * 取消技能
     */
    int CANCEL_SKILL = GameProtocols.SKILL + 4;

    /**
     * 后续删除，使用技能过程
     */
    int USE_PROCESS = GameProtocols.SKILL + 5;

}
