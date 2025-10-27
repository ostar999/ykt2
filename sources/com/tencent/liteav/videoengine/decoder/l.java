package com.tencent.liteav.videoengine.decoder;

import android.graphics.SurfaceTexture;

/* loaded from: classes6.dex */
final /* synthetic */ class l implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private final h f20357a;

    /* renamed from: b, reason: collision with root package name */
    private final SurfaceTexture f20358b;

    private l(h hVar, SurfaceTexture surfaceTexture) {
        this.f20357a = hVar;
        this.f20358b = surfaceTexture;
    }

    public static Runnable a(h hVar, SurfaceTexture surfaceTexture) {
        return new l(hVar, surfaceTexture);
    }

    @Override // java.lang.Runnable
    public void run() {
        h.a(this.f20357a, this.f20358b);
    }
}
