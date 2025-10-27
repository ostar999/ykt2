package com.tencent.liteav.videobase.utils;

/* loaded from: classes6.dex */
public enum d {
    NORMAL,
    ROTATION_90,
    ROTATION_180,
    ROTATION_270;

    /* renamed from: com.tencent.liteav.videobase.utils.d$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f20125a;

        static {
            int[] iArr = new int[d.values().length];
            f20125a = iArr;
            try {
                iArr[d.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f20125a[d.ROTATION_90.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f20125a[d.ROTATION_180.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f20125a[d.ROTATION_270.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int a() {
        int i2 = AnonymousClass1.f20125a[ordinal()];
        if (i2 == 2) {
            return 90;
        }
        if (i2 != 3) {
            return i2 != 4 ? 0 : 270;
        }
        return 180;
    }

    public static d a(int i2) {
        return i2 != 0 ? i2 != 90 ? i2 != 180 ? i2 != 270 ? i2 != 360 ? NORMAL : NORMAL : ROTATION_270 : ROTATION_180 : ROTATION_90 : NORMAL;
    }
}
