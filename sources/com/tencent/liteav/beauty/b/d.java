package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;

/* loaded from: classes6.dex */
public class d extends com.tencent.liteav.basic.opengl.j {

    /* renamed from: r, reason: collision with root package name */
    private float f18889r;

    /* renamed from: s, reason: collision with root package name */
    private int f18890s;

    /* renamed from: t, reason: collision with root package name */
    private int f18891t;

    public d() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "precision highp float;  \n \nuniform sampler2D inputImageTexture;  \nvarying highp vec2 textureCoordinate; \nuniform float texelWidthOffset; \nuniform float texelHeightOffset; \nvec2 pos[9]; \n \nvoid main()  \n{  \n\tpos[0] = textureCoordinate + vec2(-texelWidthOffset, -texelHeightOffset); \n\tpos[1] = textureCoordinate + vec2(-texelWidthOffset, 0.0); \n\tpos[2] = textureCoordinate + vec2(-texelWidthOffset, texelHeightOffset); \n\tpos[3] = textureCoordinate + vec2(0.0, -texelHeightOffset); \n\tpos[4] = textureCoordinate + vec2(0.0, 0.0); \n\tpos[5] = textureCoordinate + vec2(0.0, texelHeightOffset); \n\tpos[6] = textureCoordinate + vec2(texelWidthOffset, -texelHeightOffset); \n\tpos[7] = textureCoordinate + vec2(texelWidthOffset, 0.0); \n\tpos[8] = textureCoordinate + vec2(texelWidthOffset, texelHeightOffset); \n\tvec4 fragmentColor = texture2D(inputImageTexture, pos[0]);  \n\tfragmentColor += texture2D(inputImageTexture, pos[1]);  \n\tfragmentColor += texture2D(inputImageTexture, pos[2]);  \n\tfragmentColor += texture2D(inputImageTexture, pos[3]);  \n\tfragmentColor += texture2D(inputImageTexture, pos[4]);  \n\tfragmentColor += texture2D(inputImageTexture, pos[5]);  \n\tfragmentColor += texture2D(inputImageTexture, pos[6]);  \n\tfragmentColor += texture2D(inputImageTexture, pos[7]);  \n\tfragmentColor += texture2D(inputImageTexture, pos[8]);  \n \n\tgl_FragColor = fragmentColor / 9.0;  \n} \n");
        this.f18889r = 2.0f;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        super.a(i2, i3);
        a(this.f18889r);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        if (!super.b()) {
            return false;
        }
        this.f18890s = GLES20.glGetUniformLocation(this.f18592a, "texelWidthOffset");
        this.f18891t = GLES20.glGetUniformLocation(this.f18592a, "texelHeightOffset");
        return true;
    }

    public void a(float f2) {
        this.f18889r = f2;
        a(this.f18890s, f2 / this.f18596e);
        a(this.f18891t, this.f18889r / this.f18597f);
    }
}
