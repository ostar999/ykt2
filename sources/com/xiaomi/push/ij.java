package com.xiaomi.push;

/* loaded from: classes6.dex */
public enum ij {
    Circle(0),
    Polygon(1);


    /* renamed from: a, reason: collision with other field name */
    private final int f607a;

    ij(int i2) {
        this.f607a = i2;
    }

    public static ij a(int i2) {
        if (i2 == 0) {
            return Circle;
        }
        if (i2 != 1) {
            return null;
        }
        return Polygon;
    }

    public int a() {
        return this.f607a;
    }
}
