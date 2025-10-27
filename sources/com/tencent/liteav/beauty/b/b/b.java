package com.tencent.liteav.beauty.b.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.opengl.j;
import com.tencent.liteav.beauty.NativeLoad;

/* loaded from: classes6.dex */
public class b extends j {

    /* renamed from: r, reason: collision with root package name */
    private int f18854r;

    /* renamed from: s, reason: collision with root package name */
    private int f18855s;

    /* renamed from: t, reason: collision with root package name */
    private float[] f18856t;

    /* renamed from: u, reason: collision with root package name */
    private String f18857u;

    public b() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.f18854r = -1;
        this.f18855s = -1;
        this.f18856t = new float[4];
        this.f18857u = "Beauty3Filter";
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean a() {
        int iNativeLoadGLProgram = NativeLoad.nativeLoadGLProgram(14);
        this.f18592a = iNativeLoadGLProgram;
        if (iNativeLoadGLProgram == 0 || !b()) {
            this.f18598g = false;
        } else {
            this.f18598g = true;
        }
        c();
        return this.f18598g;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        boolean zB = super.b();
        this.f18854r = GLES20.glGetUniformLocation(q(), "singleStepOffset");
        this.f18855s = GLES20.glGetUniformLocation(q(), "beautyParams");
        a(5.0f);
        return zB;
    }

    public void c(int i2, int i3) {
        a(this.f18854r, new float[]{2.0f / i2, 2.0f / i3});
    }

    public void c(float f2) {
        float[] fArr = this.f18856t;
        fArr[2] = f2;
        b(fArr);
    }

    public void b(float f2) {
        float[] fArr = this.f18856t;
        fArr[1] = f2;
        b(fArr);
    }

    private void b(float[] fArr) {
        c(this.f18855s, fArr);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        super.a(i2, i3);
        c(i2, i3);
    }

    public void a(float f2) {
        float[] fArr = this.f18856t;
        fArr[0] = f2;
        b(fArr);
    }
}
