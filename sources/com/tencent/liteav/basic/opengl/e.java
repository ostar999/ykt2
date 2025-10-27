package com.tencent.liteav.basic.opengl;

/* loaded from: classes6.dex */
public interface e {

    public enum a {
        RGBA(0),
        I420(1),
        NV21(2),
        NV12(3);

        private final int mJniValue;

        a(int i2) {
            this.mJniValue = i2;
        }
    }
}
