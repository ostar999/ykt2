package com.xiaomi.push;

/* loaded from: classes6.dex */
public enum id {
    MISC_CONFIG(1),
    PLUGIN_CONFIG(2);


    /* renamed from: a, reason: collision with other field name */
    private final int f578a;

    id(int i2) {
        this.f578a = i2;
    }

    public static id a(int i2) {
        if (i2 == 1) {
            return MISC_CONFIG;
        }
        if (i2 != 2) {
            return null;
        }
        return PLUGIN_CONFIG;
    }

    public int a() {
        return this.f578a;
    }
}
