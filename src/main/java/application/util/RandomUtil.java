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

    public static int randomInt(int i) {
        return ThreadLocalRandom.current().nextInt(i);
    }

    public static int randomInt10000() {
        return ThreadLocalRandom.current().nextInt(10000);
    }

    public static boolean randomInt10000(int i) {
        return ThreadLocalRandom.current().nextInt(10000) < i;
    }
}
