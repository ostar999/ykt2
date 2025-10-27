package com.xiaomi.push;

/* loaded from: classes6.dex */
class bz implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ by f24657a;

    public bz(by byVar) {
        this.f24657a = byVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (bl.a().m245b()) {
            this.f24657a.c();
        } else {
            this.f24657a.d();
        }
    }
}
