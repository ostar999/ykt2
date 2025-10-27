package com.tencent.liteav.videoengine.decoder;

/* loaded from: classes6.dex */
final /* synthetic */ class k implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private final h f20356a;

    private k(h hVar) {
        this.f20356a = hVar;
    }

    public static Runnable a(h hVar) {
        return new k(hVar);
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f20356a.c();
    }
}
