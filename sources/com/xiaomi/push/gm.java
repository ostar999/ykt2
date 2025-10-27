package com.xiaomi.push;

/* loaded from: classes6.dex */
class gm implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ gj f24944a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f494a;

    public gm(gj gjVar, String str) {
        this.f24944a = gjVar;
        this.f494a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        dc.a().a(this.f494a, true);
    }
}
