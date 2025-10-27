package com.tencent.liteav.network.a;

/* loaded from: classes6.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    public final String f19517a;

    /* renamed from: b, reason: collision with root package name */
    public final int f19518b;

    /* renamed from: c, reason: collision with root package name */
    public final int f19519c;

    /* renamed from: d, reason: collision with root package name */
    public final long f19520d;

    public e(String str, int i2, int i3, long j2) {
        this.f19517a = str;
        this.f19518b = i2;
        this.f19519c = i3 < 600 ? 600 : i3;
        this.f19520d = j2;
    }

    public boolean a() {
        return this.f19518b == 5;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return this.f19517a.equals(eVar.f19517a) && this.f19518b == eVar.f19518b && this.f19519c == eVar.f19519c && this.f19520d == eVar.f19520d;
    }
}
