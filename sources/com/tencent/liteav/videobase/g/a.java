package com.tencent.liteav.videobase.g;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
public class a extends e {

    /* renamed from: f, reason: collision with root package name */
    private static final float[] f20108f = {0.0f, -0.5019608f, -0.5019608f};

    /* renamed from: g, reason: collision with root package name */
    private static final float[] f20109g = {1.0f, 1.0f, 1.0f, 0.0f, -0.3441f, 1.772f, 1.402f, -0.7141f, 0.0f};

    /* renamed from: h, reason: collision with root package name */
    private int f20110h;

    /* renamed from: i, reason: collision with root package name */
    private int f20111i;

    public a() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nuniform mat4 textureTransform;\nvarying highp vec2 textureCoordinate;\nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "precision highp float;\nvarying vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform sampler2D uvTexture;\nuniform mat3 convertMatrix;\nuniform vec3 offset;\n\nvoid main()\n{\n    highp vec3 yuvColor;\n    highp vec3 rgbColor;\n\n    // Get the YUV values\n    yuvColor.x = texture2D(inputImageTexture, textureCoordinate).r;\n    yuvColor.y = texture2D(uvTexture, vec2(textureCoordinate.x * 0.5, textureCoordinate.y * 0.5)).r;\n    yuvColor.z = texture2D(uvTexture, vec2(textureCoordinate.x * 0.5, textureCoordinate.y * 0.5 + 0.5)).r;\n\n    // Do the color transform\n    yuvColor += offset;\n    rgbColor = convertMatrix * yuvColor;\n\n    gl_FragColor = vec4(rgbColor, 1.0);\n}");
    }

    public static /* synthetic */ void a(a aVar) {
        GLES20.glUniform3fv(aVar.f20111i, 1, f20108f, 0);
        GLES20.glUniformMatrix3fv(aVar.f20110h, 1, false, f20109g, 0);
    }

    @Override // com.tencent.liteav.videobase.g.e, com.tencent.liteav.videobase.b.a
    public void b(com.tencent.liteav.videobase.frame.c cVar) {
        super.b(cVar);
        this.f20110h = GLES20.glGetUniformLocation(d(), "convertMatrix");
        this.f20111i = GLES20.glGetUniformLocation(d(), "offset");
        a(b.a(this));
    }

    @Override // com.tencent.liteav.videobase.g.e
    public int h() {
        return R2.dimen.dm_224;
    }
}
