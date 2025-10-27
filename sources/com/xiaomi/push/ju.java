package com.xiaomi.push;

import com.xiaomi.push.jx;

/* loaded from: classes6.dex */
public class ju {

    /* renamed from: a, reason: collision with root package name */
    private final kb f25490a;

    /* renamed from: a, reason: collision with other field name */
    private final kk f918a;

    public ju() {
        this(new jx.a());
    }

    public ju(kd kdVar) {
        kk kkVar = new kk();
        this.f918a = kkVar;
        this.f25490a = kdVar.a(kkVar);
    }

    public void a(jq jqVar, byte[] bArr) {
        try {
            this.f918a.a(bArr);
            jqVar.a(this.f25490a);
        } finally {
            this.f25490a.l();
        }
    }
}
