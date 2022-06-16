package application.util;

import java.io.Serializable;
import java.util.Map;

public class BoardDataMap extends StringKeyDirtyFlagMap implements Serializable {

	private static final long serialVersionUID = -821335533600881489L;

	public BoardDataMap() {
		super(15);
	}

	/**
	 * <p>
	 * Create a <code>JobDataMap</code> with the given data.
	 * </p>
	 */
	public BoardDataMap(Map<?, ?> map) {
		this();
		@SuppressWarnings("unchecked") // casting to keep API compatible and
										// avoid compiler errors/warnings.
		Map<String, Object> mapTyped = (Map<String, Object>) map;
		putAll(mapTyped);
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Interface.
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	/**
	 * <p>
	 * Adds the given <code>boolean</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, boolean value) {
		String strValue = Boolean.valueOf(value).toString();

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Adds the given <code>Boolean</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, Boolean value) {
		String strValue = value.toString();

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Adds the given <code>char</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, char value) {
		String strValue = Character.valueOf(value).toString();

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Adds the given <code>Character</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, Character value) {
		String strValue = value.toString();

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Adds the given <code>double</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, double value) {
		String strValue = Double.toString(value);

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Adds the given <code>Double</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, Double value) {
		String strValue = value.toString();

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Adds the given <code>float</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, float value) {
		String strValue = Float.toString(value);

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Adds the given <code>Float</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, Float value) {
		String strValue = value.toString();

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Adds the given <code>int</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, int value) {
		String strValue = Integer.valueOf(value).toString();

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Adds the given <code>Integer</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, Integer value) {
		String strValue = value.toString();

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Adds the given <code>long</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, long value) {
		String strValue = Long.valueOf(value).toString();

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Adds the given <code>Long</code> value as a string version to the
	 * <code>Job</code>'s data map.
	 * </p>
	 */
	public void putAsString(String key, Long value) {
		String strValue = value.toString();

		super.put(key, strValue);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>int</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public int getIntFromString(String key) {
		Object obj = get(key);

		return Integer.parseInt((String) obj);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>int</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String or Integer.
	 */
	public int getIntValue(String key) {
		Object obj = get(key);

		if (obj instanceof String) {
			return getIntFromString(key);
		} else {
			return getInt(key);
		}
	}

	/**
	 * <p>
	 * Retrieve the identified <code>int</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public Integer getIntegerFromString(String key) {
		Object obj = get(key);

		return Integer.parseInt((String) obj);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>boolean</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public boolean getBooleanValueFromString(String key) {
		Object obj = get(key);

		return Boolean.parseBoolean((String) obj);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>boolean</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String or Boolean.
	 */
	public boolean getBooleanValue(String key) {
		Object obj = get(key);

		if (obj instanceof String) {
			return getBooleanValueFromString(key);
		} else {
			return getBoolean(key);
		}
	}

	/**
	 * <p>
	 * Retrieve the identified <code>Boolean</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public Boolean getBooleanFromString(String key) {
		Object obj = get(key);

		return Boolean.valueOf((String) obj);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>char</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public char getCharFromString(String key) {
		Object obj = get(key);

		return ((String) obj).charAt(0);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>Character</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public Character getCharacterFromString(String key) {
		Object obj = get(key);

		return ((String) obj).charAt(0);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>double</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public double getDoubleValueFromString(String key) {
		Object obj = get(key);

		return Double.valueOf((String) obj);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>double</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String or Double.
	 */
	public double getDoubleValue(String key) {
		Object obj = get(key);

		if (obj instanceof String) {
			return getDoubleValueFromString(key);
		} else {
			return getDouble(key);
		}
	}

	/**
	 * <p>
	 * Retrieve the identified <code>Double</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public Double getDoubleFromString(String key) {
		Object obj = get(key);

		return Double.parseDouble((String) obj);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>float</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public float getFloatValueFromString(String key) {
		Object obj = get(key);

		return Float.parseFloat((String) obj);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>float</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String or Float.
	 */
	public float getFloatValue(String key) {
		Object obj = get(key);

		if (obj instanceof String) {
			return getFloatValueFromString(key);
		} else {
			return getFloat(key);
		}
	}

	/**
	 * <p>
	 * Retrieve the identified <code>Float</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public Float getFloatFromString(String key) {
		Object obj = get(key);

		return Float.parseFloat((String) obj);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>long</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public long getLongValueFromString(String key) {
		Object obj = get(key);

		return Long.parseLong((String) obj);
	}

	/**
	 * <p>
	 * Retrieve the identified <code>long</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String or Long.
	 */
	public long getLongValue(String key) {
		Object obj = get(key);

		if (obj instanceof String) {
			return getLongValueFromString(key);
		} else {
			return getLong(key);
		}
	}

	/**
	 * <p>
	 * Retrieve the identified <code>Long</code> value from the
	 * <code>JobDataMap</code>.
	 * </p>
	 * 
	 * @throws ClassCastException
	 *             if the identified object is not a String.
	 */
	public Long getLongFromString(String key) {
		Object obj = get(key);

		return Long.parseLong((String) obj);
	}
}
