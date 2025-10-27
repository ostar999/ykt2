package com.tencent.liteav.basic.opengl;

/* loaded from: classes6.dex */
public class m {

    /* renamed from: a, reason: collision with root package name */
    public static final float[] f18638a = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    /* renamed from: b, reason: collision with root package name */
    public static final float[] f18639b = {1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};

    /* renamed from: c, reason: collision with root package name */
    public static final float[] f18640c = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};

    /* renamed from: d, reason: collision with root package name */
    public static final float[] f18641d = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};

    /* renamed from: e, reason: collision with root package name */
    public static final float[] f18642e = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};

    /* renamed from: com.tencent.liteav.basic.opengl.m$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f18643a;

        static {
            int[] iArr = new int[l.values().length];
            f18643a = iArr;
            try {
                iArr[l.ROTATION_90.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f18643a[l.ROTATION_180.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f18643a[l.ROTATION_270.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f18643a[l.NORMAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static float a(float f2) {
        return f2 == 0.0f ? 1.0f : 0.0f;
    }

    public static float[] a(l lVar, boolean z2, boolean z3) {
        int i2 = AnonymousClass1.f18643a[lVar.ordinal()];
        float[] fArr = i2 != 1 ? i2 != 2 ? i2 != 3 ? (float[]) f18638a.clone() : (float[]) f18641d.clone() : (float[]) f18640c.clone() : (float[]) f18639b.clone();
        if (z2) {
            fArr = new float[]{a(fArr[0]), fArr[1], a(fArr[2]), fArr[3], a(fArr[4]), fArr[5], a(fArr[6]), fArr[7]};
        }
        return z3 ? new float[]{fArr[0], a(fArr[1]), fArr[2], a(fArr[3]), fArr[4], a(fArr[5]), fArr[6], a(fArr[7])} : fArr;
    }
}
