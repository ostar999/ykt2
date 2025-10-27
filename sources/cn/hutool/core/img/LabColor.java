package cn.hutool.core.img;

import cn.hutool.core.lang.Assert;
import java.awt.Color;
import java.awt.color.ColorSpace;

/* loaded from: classes.dex */
public class LabColor {
    private static final ColorSpace XYZ_COLOR_SPACE = ColorSpace.getInstance(1001);

    /* renamed from: a, reason: collision with root package name */
    private final double f2459a;

    /* renamed from: b, reason: collision with root package name */
    private final double f2460b;

    /* renamed from: l, reason: collision with root package name */
    private final double f2461l;

    public LabColor(Integer num) {
        this(num != null ? new Color(num.intValue()) : null);
    }

    private static double f(double d3) {
        return d3 > 0.008856451679035631d ? Math.cbrt(d3) : 0.13793103448275862d + (Math.pow(4.833333333333333d, 2.0d) * 0.3333333333333333d * d3);
    }

    private float[] fromXyz(float[] fArr) {
        return fromXyz(fArr[0], fArr[1], fArr[2]);
    }

    public double getDistance(LabColor labColor) {
        double d3 = this.f2459a;
        double d4 = this.f2460b;
        double dSqrt = Math.sqrt((d3 * d3) + (d4 * d4));
        double d5 = labColor.f2459a;
        double d6 = labColor.f2460b;
        double dSqrt2 = dSqrt - Math.sqrt((d5 * d5) + (d6 * d6));
        double d7 = this.f2459a - labColor.f2459a;
        double d8 = this.f2460b - labColor.f2460b;
        return Math.sqrt(Math.max(0.0d, Math.pow(this.f2461l - labColor.f2461l, 2.0d) + Math.pow(dSqrt2 / ((0.045d * dSqrt) + 1.0d), 2.0d) + Math.pow(Math.sqrt(Math.max(0.0d, ((d7 * d7) + (d8 * d8)) - (dSqrt2 * dSqrt2))) / ((dSqrt * 0.015d) + 1.0d), 2.0d)));
    }

    public LabColor(Color color) throws IllegalArgumentException {
        Assert.notNull(color, "Color must not be null", new Object[0]);
        float[] fArrFromXyz = fromXyz(color.getColorComponents(XYZ_COLOR_SPACE, (float[]) null));
        this.f2461l = fArrFromXyz[0];
        this.f2459a = fArrFromXyz[1];
        this.f2460b = fArrFromXyz[2];
    }

    private static float[] fromXyz(float f2, float f3, float f4) {
        double d3 = f3;
        return new float[]{(float) ((f(d3) - 16.0d) * 116.0d), (float) ((f(f2) - f(d3)) * 500.0d), (float) ((f(d3) - f(f4)) * 200.0d)};
    }
}
