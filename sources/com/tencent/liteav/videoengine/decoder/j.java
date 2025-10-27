package com.tencent.liteav.videoengine.decoder;

/* loaded from: classes6.dex */
final /* synthetic */ class j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private final h f20354a;

    /* renamed from: b, reason: collision with root package name */
    private final com.tencent.liteav.videobase.e.b f20355b;

    private j(h hVar, com.tencent.liteav.videobase.e.b bVar) {
        this.f20354a = hVar;
        this.f20355b = bVar;
    }

    public static Runnable a(h hVar, com.tencent.liteav.videobase.e.b bVar) {
        return new j(hVar, bVar);
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f20354a.a(this.f20355b);
    }
}
