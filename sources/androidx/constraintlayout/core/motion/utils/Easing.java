package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

/* loaded from: classes.dex */
public class Easing {
    private static final String ACCELERATE = "cubic(0.4, 0.05, 0.8, 0.7)";
    private static final String ANTICIPATE = "cubic(0.36, 0, 0.66, -0.56)";
    private static final String ANTICIPATE_NAME = "anticipate";
    private static final String DECELERATE = "cubic(0.0, 0.0, 0.2, 0.95)";
    private static final String LINEAR = "cubic(1, 1, 0, 0)";
    private static final String OVERSHOOT = "cubic(0.34, 1.56, 0.64, 1)";
    private static final String OVERSHOOT_NAME = "overshoot";
    private static final String STANDARD = "cubic(0.4, 0.0, 0.2, 1)";
    private static final String STANDARD_NAME = "standard";
    String str = "identity";
    static Easing sDefault = new Easing();
    private static final String ACCELERATE_NAME = "accelerate";
    private static final String DECELERATE_NAME = "decelerate";
    private static final String LINEAR_NAME = "linear";
    public static String[] NAMED_EASING = {"standard", ACCELERATE_NAME, DECELERATE_NAME, LINEAR_NAME};

    public static Easing getInterpolator(String str) {
        if (str == null) {
            return null;
        }
        if (str.startsWith("cubic")) {
            return new CubicEasing(str);
        }
        if (str.startsWith("spline")) {
            return new StepCurve(str);
        }
        if (str.startsWith("Schlick")) {
            return new Schlick(str);
        }
        switch (str) {
            case "accelerate":
                return new CubicEasing(ACCELERATE);
            case "decelerate":
                return new CubicEasing(DECELERATE);
            case "anticipate":
                return new CubicEasing(ANTICIPATE);
            case "linear":
                return new CubicEasing(LINEAR);
            case "overshoot":
                return new CubicEasing(OVERSHOOT);
            case "standard":
                return new CubicEasing(STANDARD);
            default:
                System.err.println("transitionEasing syntax error syntax:transitionEasing=\"cubic(1.0,0.5,0.0,0.6)\" or " + Arrays.toString(NAMED_EASING));
                return sDefault;
        }
    }

    public double get(double d3) {
        return d3;
    }

    public double getDiff(double d3) {
        return 1.0d;
    }

    public String toString() {
        return this.str;
    }

    public static class CubicEasing extends Easing {
        private static double d_error = 1.0E-4d;
        private static double error = 0.01d;

        /* renamed from: x1, reason: collision with root package name */
        double f1771x1;
        double x2;

        /* renamed from: y1, reason: collision with root package name */
        double f1772y1;
        double y2;

        public CubicEasing(String str) {
            this.str = str;
            int iIndexOf = str.indexOf(40);
            int iIndexOf2 = str.indexOf(44, iIndexOf);
            this.f1771x1 = Double.parseDouble(str.substring(iIndexOf + 1, iIndexOf2).trim());
            int i2 = iIndexOf2 + 1;
            int iIndexOf3 = str.indexOf(44, i2);
            this.f1772y1 = Double.parseDouble(str.substring(i2, iIndexOf3).trim());
            int i3 = iIndexOf3 + 1;
            int iIndexOf4 = str.indexOf(44, i3);
            this.x2 = Double.parseDouble(str.substring(i3, iIndexOf4).trim());
            int i4 = iIndexOf4 + 1;
            this.y2 = Double.parseDouble(str.substring(i4, str.indexOf(41, i4)).trim());
        }

        private double getDiffX(double d3) {
            double d4 = 1.0d - d3;
            double d5 = this.f1771x1;
            double d6 = this.x2;
            return (d4 * 3.0d * d4 * d5) + (d4 * 6.0d * d3 * (d6 - d5)) + (3.0d * d3 * d3 * (1.0d - d6));
        }

        private double getDiffY(double d3) {
            double d4 = 1.0d - d3;
            double d5 = this.f1772y1;
            double d6 = this.y2;
            return (d4 * 3.0d * d4 * d5) + (d4 * 6.0d * d3 * (d6 - d5)) + (3.0d * d3 * d3 * (1.0d - d6));
        }

        private double getX(double d3) {
            double d4 = 1.0d - d3;
            double d5 = 3.0d * d4;
            return (this.f1771x1 * d4 * d5 * d3) + (this.x2 * d5 * d3 * d3) + (d3 * d3 * d3);
        }

        private double getY(double d3) {
            double d4 = 1.0d - d3;
            double d5 = 3.0d * d4;
            return (this.f1772y1 * d4 * d5 * d3) + (this.y2 * d5 * d3 * d3) + (d3 * d3 * d3);
        }

        @Override // androidx.constraintlayout.core.motion.utils.Easing
        public double get(double d3) {
            if (d3 <= 0.0d) {
                return 0.0d;
            }
            if (d3 >= 1.0d) {
                return 1.0d;
            }
            double d4 = 0.5d;
            double d5 = 0.5d;
            while (d4 > error) {
                d4 *= 0.5d;
                d5 = getX(d5) < d3 ? d5 + d4 : d5 - d4;
            }
            double d6 = d5 - d4;
            double x2 = getX(d6);
            double d7 = d5 + d4;
            double x3 = getX(d7);
            double y2 = getY(d6);
            return (((getY(d7) - y2) * (d3 - x2)) / (x3 - x2)) + y2;
        }

        @Override // androidx.constraintlayout.core.motion.utils.Easing
        public double getDiff(double d3) {
            double d4 = 0.5d;
            double d5 = 0.5d;
            while (d4 > d_error) {
                d4 *= 0.5d;
                d5 = getX(d5) < d3 ? d5 + d4 : d5 - d4;
            }
            double d6 = d5 - d4;
            double d7 = d5 + d4;
            return (getY(d7) - getY(d6)) / (getX(d7) - getX(d6));
        }

        public void setup(double d3, double d4, double d5, double d6) {
            this.f1771x1 = d3;
            this.f1772y1 = d4;
            this.x2 = d5;
            this.y2 = d6;
        }

        public CubicEasing(double d3, double d4, double d5, double d6) {
            setup(d3, d4, d5, d6);
        }
    }
}
