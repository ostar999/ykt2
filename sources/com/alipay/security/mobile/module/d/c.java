package com.alipay.security.mobile.module.d;

/* loaded from: classes2.dex */
public final class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ b f3430a;

    public c(b bVar) {
        this.f3430a = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.f3430a.a();
        } catch (Exception e2) {
            d.a(e2);
        }
    }
}
