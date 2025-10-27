package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes6.dex */
public class r extends com.tencent.liteav.basic.opengl.j {

    /* renamed from: v, reason: collision with root package name */
    private static String f18964v = "GPUSharpen";

    /* renamed from: r, reason: collision with root package name */
    private int f18965r;

    /* renamed from: s, reason: collision with root package name */
    private float f18966s;

    /* renamed from: t, reason: collision with root package name */
    private int f18967t;

    /* renamed from: u, reason: collision with root package name */
    private int f18968u;

    public r() {
        this(0.0f);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        super.a(i2, i3);
        a(this.f18967t, 1.0f / i2);
        a(this.f18968u, 1.0f / i3);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        boolean zB = super.b();
        this.f18965r = GLES20.glGetUniformLocation(q(), "sharpness");
        this.f18967t = GLES20.glGetUniformLocation(q(), "imageWidthFactor");
        this.f18968u = GLES20.glGetUniformLocation(q(), "imageHeightFactor");
        a(this.f18966s);
        return zB;
    }

    public r(float f2) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nuniform float imageWidthFactor; \nuniform float imageHeightFactor; \nuniform float sharpness;\n\nvarying vec2 textureCoordinate;\nvarying vec2 leftTextureCoordinate;\nvarying vec2 rightTextureCoordinate; \nvarying vec2 topTextureCoordinate;\nvarying vec2 bottomTextureCoordinate;\n\nvarying float centerMultiplier;\nvarying float edgeMultiplier;\n\nvoid main()\n{\n    gl_Position = position;\n    \n    mediump vec2 widthStep = vec2(imageWidthFactor, 0.0);\n    mediump vec2 heightStep = vec2(0.0, imageHeightFactor);\n    \n    textureCoordinate = inputTextureCoordinate.xy;\n    leftTextureCoordinate = inputTextureCoordinate.xy - widthStep;\n    rightTextureCoordinate = inputTextureCoordinate.xy + widthStep;\n    topTextureCoordinate = inputTextureCoordinate.xy + heightStep;     \n    bottomTextureCoordinate = inputTextureCoordinate.xy - heightStep;\n    \n    centerMultiplier = 1.0 + 4.0 * sharpness;\n    edgeMultiplier = sharpness;\n}", "precision highp float;\n\nvarying highp vec2 textureCoordinate;\nvarying highp vec2 leftTextureCoordinate;\nvarying highp vec2 rightTextureCoordinate; \nvarying highp vec2 topTextureCoordinate;\nvarying highp vec2 bottomTextureCoordinate;\n\nvarying highp float centerMultiplier;\nvarying highp float edgeMultiplier;\n\nuniform sampler2D inputImageTexture;\n\nvoid main()\n{\n    mediump vec3 textureColor = texture2D(inputImageTexture, textureCoordinate).rgb;\n    mediump vec3 leftTextureColor = texture2D(inputImageTexture, leftTextureCoordinate).rgb;\n    mediump vec3 rightTextureColor = texture2D(inputImageTexture, rightTextureCoordinate).rgb;\n    mediump vec3 topTextureColor = texture2D(inputImageTexture, topTextureCoordinate).rgb;\n    mediump vec3 bottomTextureColor = texture2D(inputImageTexture, bottomTextureCoordinate).rgb;\n\n    gl_FragColor = vec4((textureColor * centerMultiplier - (leftTextureColor * edgeMultiplier + rightTextureColor * edgeMultiplier + topTextureColor * edgeMultiplier + bottomTextureColor * edgeMultiplier)), 1.0);\n}");
        this.f18966s = f2;
    }

    public void a(float f2) {
        this.f18966s = f2;
        TXCLog.i(f18964v, "set Sharpness " + f2);
        a(this.f18965r, this.f18966s);
    }
}
