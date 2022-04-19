package application.module.gm.domain;

import java.util.List;

/**
 * @author HRT
 * @date 2022-4-12
 */
public class GmCmdArgs {

    private final List<String> args;
    private int index;

    public GmCmdArgs(List<String> args) {
        this.args = args;
    }

    public List<String> getArgs() {
        return args;
    }

    public int size() {
        return args.size();
    }

    public String get(int i) {
        return args.get(i);
    }

    public int getInt(int i) {
        return Integer.parseInt(get(i));
    }

    public byte getByte(int i) {
        return Byte.parseByte(get(i));
    }

    public short getShort(int i) {
        return Short.parseShort(get(i));
    }

    public long getLong(int i) {
        return Long.parseLong(get(i));
    }

    public float getFloat(int i) {
        return Float.parseFloat(get(i));
    }

    public double getDouble(int i) {
        return Double.parseDouble(get(i));
    }

    public boolean getBoolean(int i) {
        return Boolean.parseBoolean(get(i));
    }

    public void reset() {
        index = 0;
    }

    public boolean hasNext() {
        return index < args.size();
    }

    public String next() {
        return get(index ++);
    }

    public int nextInt() {
        return getInt(index ++);
    }

    public byte nextByte() {
        return getByte(index ++);
    }

    public short nextShort() {
        return getShort(index ++);
    }

    public long nextLong() {
        return getLong(index ++);
    }

    public float nextFloat() {
        return getFloat(index ++);
    }

    public double nextDouble() {
        return getDouble(index ++);
    }

    public boolean nextBoolean() {
        return getBoolean(index ++);
    }

    public Object getValue(int i, Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return getInt(i);
        } else if (type == String.class) {
            return get(i);
        } else if (type == float.class || type == Float.class) {
            return getFloat(i);
        } else if (type == double.class || type == Double.class) {
            return getDouble(i);
        } else if (type == long.class || type == Long.class) {
            return getLong(i);
        } else if (type == boolean.class || type == Boolean.class) {
            return getBoolean(i);
        }  else if (type == short.class || type == Short.class) {
            return getShort(i);
        } else if (type == byte.class || type == Byte.class) {
            return getByte(i);
        }
        return get(i);
    }

}
