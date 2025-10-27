package com.xiaomi.push;

/* renamed from: com.xiaomi.push.if, reason: invalid class name */
/* loaded from: classes6.dex */
public enum Cif {
    Baidu(0),
    Tencent(1),
    AutoNavi(2),
    Google(3),
    GPS(4);


    /* renamed from: a, reason: collision with other field name */
    private final int f582a;

    Cif(int i2) {
        this.f582a = i2;
    }

    public static Cif a(int i2) {
        if (i2 == 0) {
            return Baidu;
        }
        if (i2 == 1) {
            return Tencent;
        }
        if (i2 == 2) {
            return AutoNavi;
        }
        if (i2 == 3) {
            return Google;
        }
        if (i2 != 4) {
            return null;
        }
        return GPS;
    }

    public int a() {
        return this.f582a;
    }
}
