package application.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Luo Yong
 * @date 2022-3-16
 * @Source 1.0
 */
public class RandomUtil {

    public static double randomDouble1() {
        return randomDouble(1);
    }

    public static double randomDouble(double d) {
        return ThreadLocalRandom.current().nextDouble(d);
    }

}
