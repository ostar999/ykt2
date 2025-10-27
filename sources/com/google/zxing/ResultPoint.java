package com.google.zxing;

import com.google.zxing.common.detector.MathUtils;

/* loaded from: classes4.dex */
public class ResultPoint {

    /* renamed from: x, reason: collision with root package name */
    private final float f7076x;

    /* renamed from: y, reason: collision with root package name */
    private final float f7077y;

    public ResultPoint(float f2, float f3) {
        this.f7076x = f2;
        this.f7077y = f3;
    }

    private static float crossProductZ(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        float f2 = resultPoint2.f7076x;
        float f3 = resultPoint2.f7077y;
        return ((resultPoint3.f7076x - f2) * (resultPoint.f7077y - f3)) - ((resultPoint3.f7077y - f3) * (resultPoint.f7076x - f2));
    }

    public static float distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.f7076x, resultPoint.f7077y, resultPoint2.f7076x, resultPoint2.f7077y);
    }

    public static void orderBestPatterns(ResultPoint[] resultPointArr) {
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        float fDistance = distance(resultPointArr[0], resultPointArr[1]);
        float fDistance2 = distance(resultPointArr[1], resultPointArr[2]);
        float fDistance3 = distance(resultPointArr[0], resultPointArr[2]);
        if (fDistance2 >= fDistance && fDistance2 >= fDistance3) {
            resultPoint = resultPointArr[0];
            resultPoint2 = resultPointArr[1];
            resultPoint3 = resultPointArr[2];
        } else if (fDistance3 < fDistance2 || fDistance3 < fDistance) {
            resultPoint = resultPointArr[2];
            resultPoint2 = resultPointArr[0];
            resultPoint3 = resultPointArr[1];
        } else {
            resultPoint = resultPointArr[1];
            resultPoint2 = resultPointArr[0];
            resultPoint3 = resultPointArr[2];
        }
        if (crossProductZ(resultPoint2, resultPoint, resultPoint3) < 0.0f) {
            ResultPoint resultPoint4 = resultPoint3;
            resultPoint3 = resultPoint2;
            resultPoint2 = resultPoint4;
        }
        resultPointArr[0] = resultPoint2;
        resultPointArr[1] = resultPoint;
        resultPointArr[2] = resultPoint3;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ResultPoint)) {
            return false;
        }
        ResultPoint resultPoint = (ResultPoint) obj;
        return this.f7076x == resultPoint.f7076x && this.f7077y == resultPoint.f7077y;
    }

    public final float getX() {
        return this.f7076x;
    }

    public final float getY() {
        return this.f7077y;
    }

    public final int hashCode() {
        return (Float.floatToIntBits(this.f7076x) * 31) + Float.floatToIntBits(this.f7077y);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(25);
        sb.append('(');
        sb.append(this.f7076x);
        sb.append(',');
        sb.append(this.f7077y);
        sb.append(')');
        return sb.toString();
    }
}
