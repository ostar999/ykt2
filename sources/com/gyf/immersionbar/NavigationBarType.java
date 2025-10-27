package com.gyf.immersionbar;

/* loaded from: classes4.dex */
public enum NavigationBarType {
    CLASSIC(0),
    GESTURES(1),
    GESTURES_THREE_STAGE(2),
    DOUBLE(3),
    UNKNOWN(-1);

    private final int type;

    NavigationBarType(int i2) {
        this.type = i2;
    }

    public int getType() {
        return this.type;
    }
}
