package com.xiaomi.push;

/* loaded from: classes6.dex */
public enum ie {
    INT(1),
    LONG(2),
    STRING(3),
    BOOLEAN(4);


    /* renamed from: a, reason: collision with other field name */
    private final int f580a;

    ie(int i2) {
        this.f580a = i2;
    }

    public static ie a(int i2) {
        if (i2 == 1) {
            return INT;
        }
        if (i2 == 2) {
            return LONG;
        }
        if (i2 == 3) {
            return STRING;
        }
        if (i2 != 4) {
            return null;
        }
        return BOOLEAN;
    }
}
