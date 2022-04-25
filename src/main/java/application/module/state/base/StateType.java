package application.module.state.base;

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

        short DEAD_STATE = 302;
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
    }

}
