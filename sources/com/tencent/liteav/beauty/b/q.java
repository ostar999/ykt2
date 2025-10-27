package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes6.dex */
public class q extends com.tencent.liteav.basic.opengl.j {

    /* renamed from: v, reason: collision with root package name */
    private static String f18959v = "GPUSharpen";

    /* renamed from: r, reason: collision with root package name */
    private int f18960r;

    /* renamed from: s, reason: collision with root package name */
    private float f18961s;

    /* renamed from: t, reason: collision with root package name */
    private int f18962t;

    /* renamed from: u, reason: collision with root package name */
    private int f18963u;

    public q() {
        this(0.0f);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        super.a(i2, i3);
        a(this.f18962t, 1.0f / i2);
        a(this.f18963u, 1.0f / i3);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        boolean zB = super.b();
        this.f18960r = GLES20.glGetUniformLocation(q(), "sharpness");
        this.f18962t = GLES20.glGetUniformLocation(q(), "imageWidthFactor");
        this.f18963u = GLES20.glGetUniformLocation(q(), "imageHeightFactor");
        a(this.f18961s);
        return zB;
    }

    public q(float f2) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nuniform float imageWidthFactor; \nuniform float imageHeightFactor; \n\nvarying vec2 textureCoordinate;\nvarying vec2 leftTextureCoordinate;\nvarying vec2 rightTextureCoordinate; \nvarying vec2 topTextureCoordinate;\nvarying vec2 bottomTextureCoordinate;\n\n\nvoid main()\n{\n    gl_Position = position;\n    \n    mediump vec2 widthStep = vec2(imageWidthFactor, 0.0);\n    mediump vec2 heightStep = vec2(0.0, imageHeightFactor);\n    \n    textureCoordinate = inputTextureCoordinate.xy;\n    leftTextureCoordinate = inputTextureCoordinate.xy - widthStep;\n    rightTextureCoordinate = inputTextureCoordinate.xy + widthStep;\n    topTextureCoordinate = inputTextureCoordinate.xy + heightStep;     \n    bottomTextureCoordinate = inputTextureCoordinate.xy - heightStep;\n}\n", "precision mediump float;\n\nuniform float sharpness;\nvarying mediump vec2 textureCoordinate;\nvarying mediump vec2 leftTextureCoordinate;\nvarying mediump vec2 rightTextureCoordinate; \nvarying mediump vec2 topTextureCoordinate;\nvarying mediump vec2 bottomTextureCoordinate;\n\nuniform sampler2D inputImageTexture;\nfloat centerMultiplier;\nfloat edgeMultiplier;\n\nvoid main()\n{\n    mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n    mediump vec3 leftTextureColor = texture2D(inputImageTexture, leftTextureCoordinate).rgb;\n    mediump vec3 rightTextureColor = texture2D(inputImageTexture, rightTextureCoordinate).rgb;\n    mediump vec3 topTextureColor = texture2D(inputImageTexture, topTextureCoordinate).rgb;\n    mediump vec3 bottomTextureColor = texture2D(inputImageTexture, bottomTextureCoordinate).rgb;\n\n    centerMultiplier = 1.0 + 4.0 * sharpness * (1.0 - textureColor.a);\n    edgeMultiplier = sharpness * (1.0 - textureColor.a);\n    gl_FragColor = vec4((textureColor.rgb * centerMultiplier - (leftTextureColor * edgeMultiplier + rightTextureColor * edgeMultiplier + topTextureColor * edgeMultiplier + bottomTextureColor * edgeMultiplier)), textureColor.a);    \n}\n");
        this.f18961s = f2;
    }

    public void a(float f2) {
        this.f18961s = f2;
        TXCLog.i(f18959v, "set Sharpness " + f2);
        a(this.f18960r, this.f18961s);
    }
}
