package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public class Schlick extends Easing {
    private static final boolean DEBUG = false;
    double eps;
    double mS;
    double mT;

    public Schlick(String str) {
        this.str = str;
        int iIndexOf = str.indexOf(40);
        int iIndexOf2 = str.indexOf(44, iIndexOf);
        this.mS = Double.parseDouble(str.substring(iIndexOf + 1, iIndexOf2).trim());
        int i2 = iIndexOf2 + 1;
        this.mT = Double.parseDouble(str.substring(i2, str.indexOf(44, i2)).trim());
    }

    private double dfunc(double d3) {
        double d4 = this.mT;
        if (d3 < d4) {
            double d5 = this.mS;
            return ((d5 * d4) * d4) / ((((d4 - d3) * d5) + d3) * ((d5 * (d4 - d3)) + d3));
        }
        double d6 = this.mS;
        return (((d4 - 1.0d) * d6) * (d4 - 1.0d)) / (((((-d6) * (d4 - d3)) - d3) + 1.0d) * ((((-d6) * (d4 - d3)) - d3) + 1.0d));
    }

    private double func(double d3) {
        double d4 = this.mT;
        return d3 < d4 ? (d4 * d3) / (d3 + (this.mS * (d4 - d3))) : ((1.0d - d4) * (d3 - 1.0d)) / ((1.0d - d3) - (this.mS * (d4 - d3)));
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public double get(double d3) {
        return func(d3);
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public double getDiff(double d3) {
        return dfunc(d3);
    }
}
