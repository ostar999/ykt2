package com.beizi.fusion.g;

/* loaded from: classes2.dex */
public class ai {
    public static boolean a(int i2) {
        return ((int) ((Math.random() * 100.0d) + 1.0d)) <= i2;
    }

    public static int b(int i2) {
        return (int) ((Math.random() * i2) + 1.0d);
    }

    public static int[] a(int i2, int i3) {
        int[] iArr = new int[2];
        boolean z2 = false;
        while (!z2) {
            double dRint = Math.rint(b(0, (int) (i2 * 2 * 0.95d)));
            double dRint2 = Math.rint(b(0, (int) (i3 * 2 * 0.95d)));
            double d3 = i2;
            double d4 = i3;
            if (a(d3, d4, dRint, dRint2, d3 * 0.9d, d4 * 0.9d) <= 1.0d) {
                iArr[0] = (int) dRint;
                iArr[1] = (int) dRint2;
                z2 = true;
            }
        }
        return iArr;
    }

    private static int b(int i2, int i3) {
        return (int) ((Math.random() * (i3 - i2)) + i2);
    }

    private static double a(double d3, double d4, double d5, double d6, double d7, double d8) {
        return (Math.pow(d5 - d3, 2.0d) / Math.pow(d7, 2.0d)) + (Math.pow(d6 - d4, 2.0d) / Math.pow(d8, 2.0d));
    }
}
