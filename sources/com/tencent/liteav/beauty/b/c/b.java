package com.tencent.liteav.beauty.b.c;

import android.opengl.GLES20;
import com.tencent.liteav.beauty.b.t;

/* loaded from: classes6.dex */
public class b extends t {

    /* renamed from: r, reason: collision with root package name */
    private int f18879r;

    /* renamed from: s, reason: collision with root package name */
    private int f18880s;

    public b() {
        super(" attribute vec4 position;\n attribute vec4 inputTextureCoordinate;\n \n varying vec2 textureCoordinate;\n \n void main(void)\n {\n     gl_Position = position;\n     textureCoordinate = inputTextureCoordinate.xy;\n }\n", " varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n \n void main()\n {\n     lowp vec3 iColor = texture2D(inputImageTexture, textureCoordinate).rgb;\n     lowp vec3 meanColor = texture2D(inputImageTexture2, textureCoordinate).rgb;\n     highp vec3 diffColor = (iColor - meanColor) * 7.07;\n     diffColor = min(diffColor * diffColor, 1.0);\n     gl_FragColor = vec4(diffColor, 1.0);\n }\n");
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        float fMin = Math.min(1.0f, 360.0f / Math.min(i2, i3));
        this.f18879r = Math.round(i2 * fMin);
        int iRound = Math.round(i3 * fMin);
        this.f18880s = iRound;
        super.a(this.f18879r, iRound);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public int b(int i2) {
        GLES20.glViewport(0, 0, this.f18879r, this.f18880s);
        return super.a(i2, this.f18604m, this.f18605n);
    }
}
