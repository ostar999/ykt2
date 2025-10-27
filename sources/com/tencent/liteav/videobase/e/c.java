package com.tencent.liteav.videobase.e;

/* loaded from: classes6.dex */
public enum c {
    UNKNOWN(-1),
    SEI(0),
    SPS(1),
    PPS(2),
    VPS(3),
    IDR(4);

    private final int mNativeValue;

    c(int i2) {
        this.mNativeValue = i2;
    }

    public int a() {
        return this.mNativeValue;
    }

    public boolean b() {
        return this == IDR;
    }
}
