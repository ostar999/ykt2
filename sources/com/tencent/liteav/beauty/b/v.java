package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;

/* loaded from: classes6.dex */
public class v extends u {

    /* renamed from: u, reason: collision with root package name */
    protected float f18981u;

    @Override // com.tencent.liteav.beauty.b.g, com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        super.a(i2, i3);
        t();
    }

    @Override // com.tencent.liteav.beauty.b.g, com.tencent.liteav.basic.opengl.j
    public boolean b() {
        return super.b() && GLES20.glGetError() == 0;
    }

    public float r() {
        return this.f18981u;
    }

    public float s() {
        return this.f18981u;
    }

    public void t() {
        float fS = s();
        com.tencent.liteav.basic.opengl.j jVar = ((g) this).f18907r.get(0);
        int iGlGetUniformLocation = GLES20.glGetUniformLocation(jVar.q(), "texelWidthOffset");
        int iGlGetUniformLocation2 = GLES20.glGetUniformLocation(jVar.q(), "texelHeightOffset");
        jVar.a(iGlGetUniformLocation, fS / this.f18596e);
        jVar.a(iGlGetUniformLocation2, 0.0f);
        float fR = r();
        com.tencent.liteav.basic.opengl.j jVar2 = ((g) this).f18907r.get(1);
        int iGlGetUniformLocation3 = GLES20.glGetUniformLocation(jVar2.q(), "texelWidthOffset");
        int iGlGetUniformLocation4 = GLES20.glGetUniformLocation(jVar2.q(), "texelHeightOffset");
        jVar2.a(iGlGetUniformLocation3, 0.0f);
        jVar2.a(iGlGetUniformLocation4, fR / this.f18597f);
    }
}
