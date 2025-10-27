package com.vivo.push;

import com.vivo.push.e;

/* loaded from: classes6.dex */
final class k implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f24386a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ e f24387b;

    public k(e eVar, String str) {
        this.f24387b = eVar;
        this.f24386a = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        e.a aVarD = this.f24387b.d(this.f24386a);
        if (aVarD != null) {
            aVarD.a(1003, new Object[0]);
        }
    }
}
