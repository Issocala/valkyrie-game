package application.module.player;

/**
 * @author Luo Yong
 * @date 2022-2-10
 * @Source 1.0
 */
public interface PlayerConfig {

    /**
     * 性别限制
     */
    interface Gender {
        byte MAN = 0;
        byte WOMAN = 1;

        static boolean valid(byte gender) {
            return gender == MAN || gender == WOMAN;
        }
    }

    /**
     * 职业限制
     */
    interface Profession {
        byte WARRIOR = 0;
        byte MAGE = 1;
        byte WARLOCK = 2;

        static boolean valid(byte profession) {
            return profession == WARRIOR || profession == MAGE || profession == WARLOCK;
        }
    }


}
