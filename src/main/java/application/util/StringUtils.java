package application.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class StringUtils {

    public final static String DEFAULT_SPLIT = ",";

    public final static String SEMICOLON_SPLIT = ";";

    public final static String AND_SPLIT = "&";

    public final static String COLON_SPLIT = ":";

    public static String[] toStringArray(String s) {
        return s.split(DEFAULT_SPLIT);
    }

    public static String[] toStringArrayBySemicolon(String s) {
        return s.split(SEMICOLON_SPLIT);
    }

    public static String[] toStringArrayByAnd(String s) {
        return s.split(AND_SPLIT);
    }

    public static String[] toStringArrayByColon(String s) {
        return s.split(COLON_SPLIT);
    }

    public static String[] toStringArray(String s, String split) {
        return s.split(split);
    }

    public static int[] toIntArray(String s) {
        String[] ss = toStringArray(s);
        int length = ss.length;
        int[] is = new int[length];
        for (int i = 0; i < length; i++) {
            is[i] = Integer.parseInt(ss[i]);
        }
        return is;
    }

    public static Map<Short, Long> toAttributeMap(String s) {
        Map<Short, Long> attributeMap = new HashMap<>();
        if (isEmpty(s)) {
            return attributeMap;
        }
        String[] ss = s.split(DEFAULT_SPLIT);
        for (String s1 : ss) {
            String[] ss1 = s1.split(":");
            if (ss1.length != 2) {
                throw new RuntimeException("你的AttributeMap配错格式了!");
            }
            short key = Short.parseShort(ss1[0]);
            long value = Long.parseLong(ss1[1]);
            attributeMap.put(key, value);
        }
        return attributeMap;
    }

    public static Map<Short, Long> toAttributeMap(int[][] ints) {
        Map<Short, Long> attributeMap = new HashMap<>();
        for (int[] ints1 : ints) {
            short key = (short) ints1[0];
            long value = ints1[1];
            attributeMap.put(key, value);
        }
        return attributeMap;
    }


    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() != 0;
    }

}
