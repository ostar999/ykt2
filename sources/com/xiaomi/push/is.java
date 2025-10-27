package com.xiaomi.push;

/* loaded from: classes6.dex */
public enum is {
    RegIdExpired(0),
    PackageUnregistered(1),
    Init(2);


    /* renamed from: a, reason: collision with other field name */
    private final int f670a;

    is(int i2) {
        this.f670a = i2;
    }

    public static is a(int i2) {
        if (i2 == 0) {
            return RegIdExpired;
        }
        if (i2 == 1) {
            return PackageUnregistered;
        }
        if (i2 != 2) {
            return null;
        }
        return Init;
    }

    public int a() {
        return this.f670a;
    }
}
