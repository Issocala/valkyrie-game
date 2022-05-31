package application.util;

import org.apache.commons.math3.exception.MathArithmeticException;


/**
 * @author Luo Yong
 * @date 2022-3-18
 * @Source 1.0
 */
public record Vector3(float x, float y, float z) {

    public Vector3(float a, Vector3 u) {
        this(a * u.x, a * u.y, a * u.z);
    }

    public static Vector3 ofXY(final float x, final float y) {
        return new Vector3(x, y, 0);
    }

    public static Vector3 ofXZ(final float x, final float z) {
        return new Vector3(x, 0, z);
    }
    public static Vector3 ofYZ(final float y, final float z) {
        return new Vector3(0, y, z);
    }

    public static final Vector3 ZERO = new Vector3(0, 0, 0);

    public static final Vector3 PLUS_I = new Vector3(1, 0, 0);

    public static final Vector3 MINUS_I = new Vector3(-1, 0, 0);

    public static final Vector3 PLUS_J = new Vector3(0, 1, 0);

    public static final Vector3 MINUS_J = new Vector3(0, -1, 0);

    public static final Vector3 PLUS_K = new Vector3(0, 0, 1);

    public static final Vector3 MINUS_K = new Vector3(0, 0, -1);


    public float getNorm1() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    /**
     * 向量模
     */
    public float getNorm() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float getAlpha() {
        return (float) Math.atan2(y, x);
    }

    public float getDelta() {
        return (float) Math.asin(z / getNorm());
    }

    public Vector3 add(final Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    public Vector3 subtract(final Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * 单位向量
     */
    public Vector3 normalize() {
        return scalarMultiply((float) (1 / getNorm()));
    }

    public Vector3 orthogonal() throws MathArithmeticException {

        float threshold = (float) (0.6 * getNorm());

        if (Math.abs(x) <= threshold) {
            float inverse = (float) (1 / Math.sqrt(y * y + z * z));
            return new Vector3(0, inverse * z, -inverse * y);
        } else if (Math.abs(y) <= threshold) {
            float inverse = (float) (1 / Math.sqrt(x * x + z * z));
            return new Vector3(-inverse * z, 0, inverse * x);
        }
        float inverse = (float) (1 / Math.sqrt(x * x + y * y));
        return new Vector3((float) inverse * y, -inverse * x, 0);

    }

    public Vector3 scalarMultiply(float a) {
        return new Vector3(a * x, a * y, a * z);
    }

    public static float angle(Vector3 v1, Vector3 v2) throws MathArithmeticException {

        float normProduct = v1.getNorm() * v2.getNorm();

        float dot = v1.dotProduct(v2);
        float threshold = normProduct * 0.9999f;
        if ((dot < -threshold) || (dot > threshold)) {
            Vector3 v3 = crossProduct(v1, v2);
            if (dot >= 0) {
                return (float) Math.asin(v3.getNorm() / normProduct);
            }
            return (float) (Math.PI - Math.asin(v3.getNorm() / normProduct));
        }

        return (float) Math.acos(dot / normProduct);

    }

    /**
     * Get the opposite of the instance
     */
    public Vector3 negate() {
        return new Vector3(-x, -y, -z);
    }


    public float dotProduct(final Vector3 v) {
        return linearCombination(x, v.x, y, v.y, z, v.z);
    }

    public static Vector3 crossProduct(Vector3 v1, Vector3 v2) {
        return v1.crossProduct(v2);
    }

    public Vector3 crossProduct(Vector3 v) {
        return new Vector3(linearCombination(y, v.z, -z, v.y),
                linearCombination(z, v.x, -x, v.z),
                linearCombination(x, v.y, -y, v.x));
    }

    public float distance(Vector3 v) {
        final float x = v.x - this.x;
        final float y = v.y - this.y;
        final float z = v.z - this.z;
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public static float linearCombination(final float a1, final float b1,
                                          final float a2, final float b2) {

        // the code below is split in many additions/subtractions that may
        // appear redundant. However, they should NOT be simplified, as they
        // use IEEE754 floating point arithmetic rounding properties.
        // The variable naming conventions are that xyzHigh contains the most significant
        // bits of xyz and xyzLow contains its least significant bits. So theoretically
        // xyz is the sum xyzHigh + xyzLow, but in many cases below, this sum cannot
        // be represented in only one double precision number, so we preserve two numbers
        // to hold it as long as we can, combining the high and low order bits together
        // only at the end, after cancellation may have occurred on high order bits

        // split a1 and b1 as one 26 bits number and one 27 bits number
        final float a1High = Float.floatToIntBits(Float.floatToIntBits(a1) & ((-1L) << 27));
        final float a1Low = a1 - a1High;
        final float b1High = Float.floatToIntBits(Float.floatToIntBits(b1) & ((-1L) << 27));
        final float b1Low = b1 - b1High;

        // accurate multiplication a1 * b1
        final float prod1High = a1 * b1;
        final float prod1Low = a1Low * b1Low - (((prod1High - a1High * b1High) - a1Low * b1High) - a1High * b1Low);

        // split a2 and b2 as one 26 bits number and one 27 bits number
        final float a2High = Float.floatToIntBits(Float.floatToIntBits(a2) & ((-1L) << 27));
        final float a2Low = a2 - a2High;
        final float b2High = Float.floatToIntBits(Float.floatToIntBits(b2) & ((-1L) << 27));
        final float b2Low = b2 - b2High;

        // accurate multiplication a2 * b2
        final float prod2High = a2 * b2;
        final float prod2Low = a2Low * b2Low - (((prod2High - a2High * b2High) - a2Low * b2High) - a2High * b2Low);

        // accurate addition a1 * b1 + a2 * b2
        final float s12High = prod1High + prod2High;
        final float s12Prime = s12High - prod2High;
        final float s12Low = (prod2High - (s12High - s12Prime)) + (prod1High - s12Prime);

        // final rounding, s12 may have suffered many cancellations, we try
        // to recover some bits from the extra words we have saved up to now
        float result = s12High + (prod1Low + prod2Low + s12Low);

        if (Double.isNaN(result)) {
            // either we have split infinite numbers or some coefficients were NaNs,
            // just rely on the naive implementation and let IEEE754 handle this
            result = a1 * b1 + a2 * b2;
        }

        return result;
    }

    public static float linearCombination(final float a1, final float b1,
                                          final float a2, final float b2,
                                          final float a3, final float b3) {

        // the code below is split in many additions/subtractions that may
        // appear redundant. However, they should NOT be simplified, as they
        // do use IEEE754 floating point arithmetic rounding properties.
        // The variables naming conventions are that xyzHigh contains the most significant
        // bits of xyz and xyzLow contains its least significant bits. So theoretically
        // xyz is the sum xyzHigh + xyzLow, but in many cases below, this sum cannot
        // be represented in only one float precision number so we preserve two numbers
        // to hold it as long as we can, combining the high and low order bits together
        // only at the end, after cancellation may have occurred on high order bits

        // split a1 and b1 as one 26 bits number and one 27 bits number
        final float a1High = Float.floatToIntBits(Float.floatToIntBits(a1) & ((-1L) << 27));
        final float a1Low = a1 - a1High;
        final float b1High = Float.floatToIntBits(Float.floatToIntBits(b1) & ((-1L) << 27));
        final float b1Low = b1 - b1High;

        // accurate multiplication a1 * b1
        final float prod1High = a1 * b1;
        final float prod1Low = a1Low * b1Low - (((prod1High - a1High * b1High) - a1Low * b1High) - a1High * b1Low);

        // split a2 and b2 as one 26 bits number and one 27 bits number
        final float a2High = Float.floatToIntBits(Float.floatToIntBits(a2) & ((-1L) << 27));
        final float a2Low = a2 - a2High;
        final float b2High = Float.floatToIntBits(Float.floatToIntBits(b2) & ((-1L) << 27));
        final float b2Low = b2 - b2High;

        // accurate multiplication a2 * b2
        final float prod2High = a2 * b2;
        final float prod2Low = a2Low * b2Low - (((prod2High - a2High * b2High) - a2Low * b2High) - a2High * b2Low);

        // split a3 and b3 as one 26 bits number and one 27 bits number
        final float a3High = Float.floatToIntBits(Float.floatToIntBits(a3) & ((-1L) << 27));
        final float a3Low = a3 - a3High;
        final float b3High = Float.floatToIntBits(Float.floatToIntBits(b3) & ((-1L) << 27));
        final float b3Low = b3 - b3High;

        // accurate multiplication a3 * b3
        final float prod3High = a3 * b3;
        final float prod3Low = a3Low * b3Low - (((prod3High - a3High * b3High) - a3Low * b3High) - a3High * b3Low);

        // accurate addition a1 * b1 + a2 * b2
        final float s12High = prod1High + prod2High;
        final float s12Prime = s12High - prod2High;
        final float s12Low = (prod2High - (s12High - s12Prime)) + (prod1High - s12Prime);

        // accurate addition a1 * b1 + a2 * b2 + a3 * b3
        final float s123High = s12High + prod3High;
        final float s123Prime = s123High - prod3High;
        final float s123Low = (prod3High - (s123High - s123Prime)) + (s12High - s123Prime);

        // final rounding, s123 may have suffered many cancellations, we try
        // to recover some bits from the extra words we have saved up to now
        float result = s123High + (prod1Low + prod2Low + prod3Low + s12Low + s123Low);

        if (Double.isNaN(result)) {
            // either we have split infinite numbers or some coefficients were NaNs,
            // just rely on the naive implementation and let IEEE754 handle this
            result = a1 * b1 + a2 * b2 + a3 * b3;
        }

        return result;
    }

}
