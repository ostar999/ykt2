package com.xiaomi.push;

/* loaded from: classes6.dex */
class gb extends Thread {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ga f24917a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public gb(ga gaVar, String str) {
        super(str);
        this.f24917a = gaVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            this.f24917a.f24916a.m437a();
        } catch (Exception e2) {
            this.f24917a.c(9, e2);
        }
    }
}
