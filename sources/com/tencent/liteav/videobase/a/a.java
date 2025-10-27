package com.tencent.liteav.videobase.a;

/* loaded from: classes6.dex */
public interface a {

    /* renamed from: a, reason: collision with root package name */
    public static final float[] f19931a = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f};

    /* renamed from: b, reason: collision with root package name */
    public static final float[] f19932b = {-1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f};

    /* renamed from: c, reason: collision with root package name */
    public static final float[] f19933c = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};

    /* renamed from: d, reason: collision with root package name */
    public static final float[] f19934d = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};

    /* renamed from: e, reason: collision with root package name */
    public static final float[] f19935e = {1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};

    /* renamed from: f, reason: collision with root package name */
    public static final float[] f19936f = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f};

    /* renamed from: g, reason: collision with root package name */
    public static final float[] f19937g = {1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f};

    /* renamed from: com.tencent.liteav.videobase.a.a$a, reason: collision with other inner class name */
    public enum EnumC0340a {
        FIT_CENTER,
        CENTER_CROP,
        FILL
    }

    public enum b {
        BYTE_ARRAY,
        BYTE_BUFFER,
        TEXTURE_2D,
        TEXTURE_OES
    }

    public enum c {
        RGBA(0),
        I420(1),
        NV21(2),
        NV12(3);

        private final int mJniValue;

        c(int i2) {
            this.mJniValue = i2;
        }

        public int a() {
            return this.mJniValue;
        }
    }
}
