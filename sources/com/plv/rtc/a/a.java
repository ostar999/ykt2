package com.plv.rtc.a;

import android.graphics.SurfaceTexture;
import io.agora.rtc.gl.EglBase;
import io.agora.rtc.mediaio.SurfaceTextureHelper;

/* loaded from: classes5.dex */
public abstract class a implements SurfaceTextureHelper.OnTextureFrameAvailableListener {

    /* renamed from: a, reason: collision with root package name */
    protected SurfaceTextureHelper f10851a;

    /* renamed from: b, reason: collision with root package name */
    protected int f10852b;

    /* renamed from: c, reason: collision with root package name */
    protected int f10853c;

    /* renamed from: d, reason: collision with root package name */
    protected int f10854d;

    /* renamed from: e, reason: collision with root package name */
    protected int f10855e;

    public a(EglBase.Context context, int i2, int i3) {
        this.f10854d = i2;
        this.f10852b = i2;
        this.f10855e = i3;
        this.f10853c = i3;
        SurfaceTextureHelper surfaceTextureHelperCreate = SurfaceTextureHelper.create("plv-tex-cam-A", context);
        this.f10851a = surfaceTextureHelperCreate;
        surfaceTextureHelperCreate.getSurfaceTexture().setDefaultBufferSize(i2, i3);
        this.f10851a.startListening(this);
    }

    public void a() {
        d();
    }

    public SurfaceTexture b() {
        return this.f10851a.getSurfaceTexture();
    }

    public boolean c() {
        return e();
    }

    public abstract void d();

    public abstract boolean e();

    public abstract boolean f();

    public abstract void g();

    public void h() {
        this.f10851a.stopListening();
        this.f10851a.dispose();
    }

    public boolean i() {
        return f();
    }

    public void j() {
        g();
    }

    @Override // io.agora.rtc.mediaio.SurfaceTextureHelper.OnTextureFrameAvailableListener
    public void onTextureFrameAvailable(int i2, float[] fArr, long j2) {
        this.f10851a.returnTextureFrame();
    }
}
