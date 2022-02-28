package application.util;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class StringUtils {

    public final static String DEFAULT_SPLIT = ",";

    public static String[] toStringArray(String s) {
        return s.split(DEFAULT_SPLIT);
    }

    public static String[] toStringArray(String s, String split) {
        return s.split(split);
    }

}
