package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;

/* loaded from: classes6.dex */
public class l extends com.tencent.liteav.basic.opengl.j {

    /* renamed from: r, reason: collision with root package name */
    private static String f18934r = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nuniform int  bTransform;\nuniform mat4 textureTransform;\n\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n     gl_Position = position;\n    if (0 != bTransform){\n        textureCoordinate =  (textureTransform * inputTextureCoordinate).xy;\n    }else{\n        textureCoordinate = inputTextureCoordinate.xy;\n    }\n}\n";

    /* renamed from: s, reason: collision with root package name */
    private int f18935s;

    /* renamed from: t, reason: collision with root package name */
    private boolean f18936t;

    public l() {
        this(f18934r, "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}", false);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(float[] fArr) {
        if (fArr != null) {
            b(this.f18935s, 1);
        } else {
            b(this.f18935s, 0);
        }
        super.a(fArr);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        boolean zB = super.b();
        int iGlGetUniformLocation = GLES20.glGetUniformLocation(this.f18592a, "bTransform");
        this.f18935s = iGlGetUniformLocation;
        b(iGlGetUniformLocation, 0);
        return zB;
    }

    public void c(boolean z2) {
        if (z2 != this.f18936t) {
            this.f18936t = z2;
            h();
        }
    }

    public l(String str, String str2, boolean z2) {
        super(str, str2, z2);
        this.f18935s = -1;
        this.f18936t = false;
    }
}
