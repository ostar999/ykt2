package com.tencent.liteav.basic.util;

/* loaded from: classes6.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    public int f18712a;

    /* renamed from: b, reason: collision with root package name */
    public int f18713b;

    public e() {
        this(0, 0);
    }

    public int a() {
        return this.f18712a * this.f18713b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return eVar.f18712a == this.f18712a && eVar.f18713b == this.f18713b;
    }

    public int hashCode() {
        return (this.f18712a * 32713) + this.f18713b;
    }

    public String toString() {
        return "Size(" + this.f18712a + ", " + this.f18713b + ")";
    }

    public e(int i2, int i3) {
        this.f18712a = i2;
        this.f18713b = i3;
    }
}
