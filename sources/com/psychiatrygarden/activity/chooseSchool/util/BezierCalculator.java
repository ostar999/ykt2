package com.psychiatrygarden.activity.chooseSchool.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class BezierCalculator {
    private static List<Double> calculateParametersT(double startX, double controlX, double endX, double targetX) {
        ArrayList arrayList = new ArrayList();
        double d3 = (startX - (controlX * 2.0d)) + endX;
        double d4 = (controlX - startX) * 2.0d;
        double d5 = startX - targetX;
        if (Math.abs(d3) < 1.0E-10d) {
            if (Math.abs(d4) < 1.0E-10d) {
                if (Math.abs(d5) < 1.0E-10d) {
                    arrayList.add(Double.valueOf(0.5d));
                }
                return arrayList;
            }
            double d6 = (-d5) / d4;
            if (d6 >= -1.0E-10d && d6 <= 1.0000000001d) {
                arrayList.add(Double.valueOf(Math.max(0.0d, Math.min(1.0d, d6))));
            }
            return arrayList;
        }
        double d7 = (d4 * d4) - ((4.0d * d3) * d5);
        if (d7 < -1.0E-10d) {
            return arrayList;
        }
        if (d7 < 0.0d) {
            d7 = 0.0d;
        }
        double dSqrt = Math.sqrt(d7);
        double d8 = -d4;
        double d9 = d3 * 2.0d;
        double d10 = (d8 + dSqrt) / d9;
        double d11 = (d8 - dSqrt) / d9;
        if (d10 >= -1.0E-10d && d10 <= 1.0000000001d) {
            arrayList.add(Double.valueOf(Math.max(0.0d, Math.min(1.0d, d10))));
        }
        if (Math.abs(d10 - d11) > 1.0E-10d && d11 >= -1.0E-10d && d11 <= 1.0000000001d) {
            arrayList.add(Double.valueOf(Math.max(0.0d, Math.min(1.0d, d11))));
        }
        return arrayList;
    }

    public static List<Double> calculateY(double startX, double startY, double controlX, double controlY, double endX, double endY, double targetX) {
        ArrayList arrayList = new ArrayList();
        Iterator<Double> it = calculateParametersT(startX, controlX, endX, targetX).iterator();
        while (it.hasNext()) {
            arrayList.add(Double.valueOf(calculateYForT(startY, controlY, endY, it.next().doubleValue())));
        }
        return arrayList;
    }

    private static double calculateYForT(double startY, double controlY, double endY, double t2) {
        double d3 = 1.0d - t2;
        return (d3 * d3 * startY) + (t2 * 2.0d * d3 * controlY) + (t2 * t2 * endY);
    }
}
