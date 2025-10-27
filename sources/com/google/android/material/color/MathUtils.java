package com.google.android.material.color;

/* loaded from: classes3.dex */
final class MathUtils {
    private MathUtils() {
    }

    public static float clamp(float f2, float f3, float f4) {
        return Math.min(Math.max(f4, f2), f3);
    }

    public static float differenceDegrees(float f2, float f3) {
        return 180.0f - Math.abs(Math.abs(f2 - f3) - 180.0f);
    }

    public static float lerp(float f2, float f3, float f4) {
        return ((1.0f - f4) * f2) + (f4 * f3);
    }

    public static float sanitizeDegrees(float f2) {
        return f2 < 0.0f ? (f2 % 360.0f) + 360.0f : f2 >= 360.0f ? f2 % 360.0f : f2;
    }

    public static int sanitizeDegrees(int i2) {
        return i2 < 0 ? (i2 % 360) + 360 : i2 >= 360 ? i2 % 360 : i2;
    }

    public static float toDegrees(float f2) {
        return (f2 * 180.0f) / 3.1415927f;
    }

    public static float toRadians(float f2) {
        return (f2 / 180.0f) * 3.1415927f;
    }
}
