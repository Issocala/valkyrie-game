package application.module.scene.fight.state.base;

import application.module.scene.fight.state.base.action.ActionState;
import application.module.scene.fight.state.base.fight.FightState;

import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-4-18
 * @Source 1.0
 */
public interface StateType {

    interface MovementType {

        short MOVE_ID = 101;

        short STOP_ID = 102;
    }

    interface ActionType {
        short IDLE_STATE = 201;

        short DEAD_STATE = 202;

        static boolean validNoUseSkill(ActionState actionState) {
            return validNoUseSkill(actionState.getId());
        }

        static boolean validNoUseSkill(short id) {
            return id == DEAD_STATE;
        }
    }

    interface FightType {
        /**
         * 冰冻
         */
        short FROZEN_STATE = 401;
        /**
         * 霸体
         */
        short SUPER_ARMOR_STATE = 402;
        /**
         * 禁锢
         */
        short IMPRISON_STATE = 403;
        /**
         * 减速
         */
        short SPEED_DOWN_STATE = 404;
        /**
         * 眩晕
         */
        short DIZZINESS_STATE = 405;

        /**
         * 加速
         */
        short SPEED_UP_STATE = 406;

        static boolean validNoUseSkill(Set<FightState> fightStateSet) {
            for (FightState fightState : fightStateSet) {
                if (validNoUseSkill(fightState.getId())) {
                    return true;
                }
            }
            return false;
        }

        static boolean validNoUseSkill(short id) {
            return id == FROZEN_STATE || id == DIZZINESS_STATE || id == IMPRISON_STATE;
        }
    }

}
