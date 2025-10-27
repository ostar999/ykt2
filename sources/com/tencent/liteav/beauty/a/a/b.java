package com.tencent.liteav.beauty.a.a;

import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLSurface;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    protected a f18786a;

    /* renamed from: b, reason: collision with root package name */
    private EGLSurface f18787b = EGL11.EGL_NO_SURFACE;

    /* renamed from: c, reason: collision with root package name */
    private int f18788c = -1;

    /* renamed from: d, reason: collision with root package name */
    private int f18789d = -1;

    public b(a aVar) {
        this.f18786a = aVar;
    }

    public void a(int i2, int i3) {
        if (this.f18787b != EGL11.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.f18787b = this.f18786a.a(i2, i3);
        this.f18788c = i2;
        this.f18789d = i3;
    }

    public void b() {
        this.f18786a.b(this.f18787b);
    }

    public void a() {
        this.f18786a.a(this.f18787b);
        this.f18787b = EGL11.EGL_NO_SURFACE;
        this.f18789d = -1;
        this.f18788c = -1;
    }
}
