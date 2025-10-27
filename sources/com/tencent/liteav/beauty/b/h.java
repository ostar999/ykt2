package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;

/* loaded from: classes6.dex */
public class h extends com.tencent.liteav.basic.opengl.j {

    /* renamed from: r, reason: collision with root package name */
    private int f18912r;

    /* renamed from: s, reason: collision with root package name */
    private float f18913s;

    public h() {
        this(1.2f);
    }

    public void a(float f2) {
        this.f18913s = f2;
        a(this.f18912r, f2);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        boolean zB = super.b();
        this.f18912r = GLES20.glGetUniformLocation(q(), "gamma");
        return zB;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void c() {
        super.c();
        a(this.f18913s);
    }

    public h(float f2) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform lowp float gamma;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     gl_FragColor = vec4(pow(textureColor.rgb, vec3(gamma)), textureColor.w);\n }");
        this.f18913s = f2;
    }
}
