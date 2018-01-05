package ch.rs.filter.util;

public class MathFunctions {

    public static final double biggerValue(double x1, double x2) {
        if (Double.compare(x1, x2) > 0) {
            return x1;
        }
        return x2;
    }

    public static final double clamp(double min, double value, double max) {
        if (Double.compare(value, min) < 0) {
            return min;
        } else if (Double.compare(value, max) > 0) {
            return max;
        }
        return value;
    }

}
