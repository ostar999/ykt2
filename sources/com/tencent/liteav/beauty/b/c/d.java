package com.tencent.liteav.beauty.b.c;

import android.opengl.GLES20;
import com.tencent.liteav.basic.opengl.j;

/* loaded from: classes6.dex */
class d extends j {

    /* renamed from: r, reason: collision with root package name */
    private final boolean f18884r;

    /* renamed from: s, reason: collision with root package name */
    private int f18885s;

    /* renamed from: t, reason: collision with root package name */
    private int f18886t;

    /* renamed from: u, reason: collision with root package name */
    private int f18887u;

    /* renamed from: v, reason: collision with root package name */
    private int f18888v;

    public d(boolean z2) {
        super(" attribute vec4 position;\n attribute vec4 inputTextureCoordinate;\n \n uniform float texelWidthOffset;\n uniform float texelHeightOffset;\n \n varying vec2 textureCoordinate;\n varying vec4 textureShift_1;\n varying vec4 textureShift_2;\n varying vec4 textureShift_3;\n varying vec4 textureShift_4;\n \n void main(void)\n {\n     gl_Position = position;\n     textureCoordinate = inputTextureCoordinate.xy;\n     \n     vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n     textureShift_1 = vec4(textureCoordinate - singleStepOffset, textureCoordinate + singleStepOffset);\n     textureShift_2 = vec4(textureCoordinate - 2.0 * singleStepOffset, textureCoordinate + 2.0 * singleStepOffset);\n     textureShift_3 = vec4(textureCoordinate - 3.0 * singleStepOffset, textureCoordinate + 3.0 * singleStepOffset);\n     textureShift_4 = vec4(textureCoordinate - 4.0 * singleStepOffset, textureCoordinate + 4.0 * singleStepOffset);\n }\n", "uniform sampler2D inputImageTexture;\n varying highp vec2 textureCoordinate;\n varying highp vec4 textureShift_1;\n varying highp vec4 textureShift_2;\n varying highp vec4 textureShift_3;\n varying highp vec4 textureShift_4;\n \n void main()\n {\n     mediump vec3 sum = texture2D(inputImageTexture, textureCoordinate).rgb;\n     sum += texture2D(inputImageTexture, textureShift_1.xy).rgb;\n     sum += texture2D(inputImageTexture, textureShift_1.zw).rgb;\n     sum += texture2D(inputImageTexture, textureShift_2.xy).rgb;\n     sum += texture2D(inputImageTexture, textureShift_2.zw).rgb;\n     sum += texture2D(inputImageTexture, textureShift_3.xy).rgb;\n     sum += texture2D(inputImageTexture, textureShift_3.zw).rgb;\n     sum += texture2D(inputImageTexture, textureShift_4.xy).rgb;\n     sum += texture2D(inputImageTexture, textureShift_4.zw).rgb;\n     \n     gl_FragColor = vec4(sum * 0.1111, 1.0);\n }\n");
        this.f18885s = -1;
        this.f18886t = -1;
        this.f18884r = z2;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        float fMin = Math.min(1.0f, 360.0f / Math.min(i2, i3));
        this.f18887u = Math.round(i2 * fMin);
        int iRound = Math.round(i3 * fMin);
        this.f18888v = iRound;
        super.a(this.f18887u, iRound);
        if (this.f18884r) {
            a(this.f18885s, 0.0f);
            a(this.f18886t, 1.5f / this.f18888v);
        } else {
            a(this.f18885s, 1.5f / this.f18887u);
            a(this.f18886t, 0.0f);
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        if (!super.b()) {
            return false;
        }
        this.f18885s = GLES20.glGetUniformLocation(q(), "texelWidthOffset");
        this.f18886t = GLES20.glGetUniformLocation(q(), "texelHeightOffset");
        return true;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public int b(int i2) {
        GLES20.glViewport(0, 0, this.f18887u, this.f18888v);
        return super.a(i2, this.f18604m, this.f18605n);
    }
}
