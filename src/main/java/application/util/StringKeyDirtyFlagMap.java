package application.util;

public class StringKeyDirtyFlagMap extends DirtyFlagMap<String, Object> {
    static final long serialVersionUID = -9076749120524952280L;

    public StringKeyDirtyFlagMap() {
        super();
    }

    public StringKeyDirtyFlagMap(int initialCapacity) {
        super(initialCapacity);
    }

    public StringKeyDirtyFlagMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return getWrappedMap().hashCode();
    }

    /**
     * Get a copy of the Map's String keys in an array of Strings.
     */
    public String[] getKeys() {
        return keySet().toArray(new String[size()]);
    }


    /**
     * <p>
     * Adds the given <code>int</code> value to the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     */
    public void put(String key, int value) {
        super.put(key, value);
    }

    /**
     * <p>
     * Adds the given <code>long</code> value to the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     */
    public void put(String key, long value) {
        super.put(key, value);
    }

    /**
     * <p>
     * Adds the given <code>float</code> value to the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     */
    public void put(String key, float value) {
        super.put(key, value);
    }

    /**
     * <p>
     * Adds the given <code>double</code> value to the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     */
    public void put(String key, double value) {
        super.put(key, value);
    }

    /**
     * <p>
     * Adds the given <code>boolean</code> value to the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     */
    public void put(String key, boolean value) {
        super.put(key, value);
    }

    /**
     * <p>
     * Adds the given <code>char</code> value to the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     */
    public void put(String key, char value) {
        super.put(key, value);
    }

    /**
     * <p>
     * Adds the given <code>String</code> value to the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     */
    public void put(String key, String value) {
        super.put(key, value);
    }

    /**
     * <p>
     * Adds the given <code>Object</code> value to the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     */
    @Override
    public Object put(String key, Object value) {
        return super.put(key, value);
    }

    /**
     * <p>
     * Retrieve the identified <code>int</code> value from the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not an Integer.
     */
    public int getInt(String key) {
        Object obj = get(key);
        if (obj == null) {
            return 0;
        }
        try {
            if (obj instanceof Integer)
                return (Integer) obj;
            return Integer.parseInt((String) obj);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not an Integer.");
        }
    }

    /**
     * <p>
     * Retrieve the identified <code>long</code> value from the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a Long.
     */
    public long getLong(String key) {
        Object obj = get(key);
        if (obj == null) {
            return 0L;
        }
        try {
            if (obj instanceof Long)
                return (Long) obj;
            return Long.parseLong((String) obj);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Long.");
        }
    }

    /**
     * <p>
     * Retrieve the identified <code>float</code> value from the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a Float.
     */
    public float getFloat(String key) {
        Object obj = get(key);
        if (obj == null) {
            return 0f;
        }
        try {
            if (obj instanceof Float)
                return (Float) obj;
            return Float.parseFloat((String) obj);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Float.");
        }
    }

    /**
     * <p>
     * Retrieve the identified <code>double</code> value from the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a Double.
     */
    public double getDouble(String key) {
        Object obj = get(key);
        if (obj == null) {
            return 0d;
        }
        try {
            if (obj instanceof Double)
                return (Double) obj;
            return Double.parseDouble((String) obj);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Double.");
        }
    }

    /**
     * <p>
     * Retrieve the identified <code>boolean</code> value from the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a Boolean.
     */
    public boolean getBoolean(String key) {
        Object obj = get(key);
        if (obj == null) {
            return false;
        }
        try {
            if (obj instanceof Boolean)
                return (Boolean) obj;
            return Boolean.parseBoolean((String) obj);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Boolean.");
        }
    }

    /**
     * <p>
     * Retrieve the identified <code>char</code> value from the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a Character.
     */
    public char getChar(String key) {
        Object obj = get(key);

        try {
            if (obj instanceof Character)
                return (Character) obj;
            return ((String) obj).charAt(0);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Character.");
        }
    }

    /**
     * <p>
     * Retrieve the identified <code>String</code> value from the
     * <code>StringKeyDirtyFlagMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a String.
     */
    public String getString(String key) {
        Object obj = get(key);
        if (obj == null) {
            return "";
        }
        try {
            return (String) obj;
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a String.");
        }
    }
}
