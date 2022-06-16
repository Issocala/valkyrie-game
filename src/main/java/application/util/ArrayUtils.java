package application.util;

import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-6-15
 * @Source 1.0
 */
public class ArrayUtils {

    public static boolean isEmpty(String[] ss) {
        return Objects.isNull(ss) || ss.length == 0;
    }

    public static boolean isNotEmpty(String[] ss) {
        return Objects.nonNull(ss) && ss.length > 0;
    }

}
