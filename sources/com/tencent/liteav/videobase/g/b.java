package com.tencent.liteav.videobase.g;

/* loaded from: classes6.dex */
final /* synthetic */ class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private final a f20112a;

    private b(a aVar) {
        this.f20112a = aVar;
    }

    public static Runnable a(a aVar) {
        return new b(aVar);
    }

    @Override // java.lang.Runnable
    public void run() {
        a.a(this.f20112a);
    }
}
