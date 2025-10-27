package com.tencent.liteav.beauty.b;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import com.tencent.liteav.basic.opengl.TXCOpenGlUtils;
import com.yikaobang.yixue.R2;
import java.nio.FloatBuffer;

/* loaded from: classes6.dex */
public class m extends com.tencent.liteav.basic.opengl.j {
    private int A;
    private float[] B;
    private int C;
    private float[] D;

    /* renamed from: r, reason: collision with root package name */
    private float f18937r;

    /* renamed from: s, reason: collision with root package name */
    private Bitmap f18938s;

    /* renamed from: t, reason: collision with root package name */
    private int f18939t;

    /* renamed from: u, reason: collision with root package name */
    private int f18940u;

    /* renamed from: v, reason: collision with root package name */
    private float f18941v;

    /* renamed from: w, reason: collision with root package name */
    private Bitmap f18942w;

    /* renamed from: x, reason: collision with root package name */
    private int f18943x;

    /* renamed from: y, reason: collision with root package name */
    private int f18944y;

    /* renamed from: z, reason: collision with root package name */
    private float f18945z;

    public m(float f2, Bitmap bitmap, float f3, Bitmap bitmap2, float f4) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2; // lookup texture 1\n uniform sampler2D inputImageTexture3; // lookup texture 2\n \n \n uniform lowp vec3 v3_params;\n uniform lowp vec2 v2_texs;\n \n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     mediump float blueColor = textureColor.b * 63.0;\n     \n     mediump vec2 quad1;\n     quad1.y = floor(floor(blueColor) / 8.0);\n     quad1.x = floor(blueColor) - (quad1.y * 8.0);\n     \n     mediump vec2 quad2;\n     quad2.y = floor(ceil(blueColor) / 8.0);\n     quad2.x = ceil(blueColor) - (quad2.y * 8.0);\n     \n     highp vec2 texPos1;\n     texPos1.x = (quad1.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos1.y = (quad1.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     highp vec2 texPos2;\n     texPos2.x = (quad2.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos2.y = (quad2.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     lowp vec4 newColor1;\n     lowp vec4 newColor2;\n     if(textureCoordinate.x <= v3_params.x) { \n       if(v2_texs.x == 1.0) { \n         newColor1 = texture2D(inputImageTexture2, texPos1);\n         newColor2 = texture2D(inputImageTexture2, texPos2);\n         lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\n         gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), v3_params.y);\n       } else { \n         gl_FragColor = textureColor;\n       } \n     } else {\n       if(v2_texs.y == 1.0) { \n         newColor1 = texture2D(inputImageTexture3, texPos1);\n         newColor2 = texture2D(inputImageTexture3, texPos2);\n         lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\n         gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), v3_params.z);\n       } else { \n         gl_FragColor = textureColor;\n       } \n     }\n }");
        this.B = new float[3];
        this.D = new float[2];
        this.f18937r = f2;
        this.f18938s = bitmap;
        this.f18942w = bitmap2;
        this.f18941v = f3;
        this.f18945z = f4;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void e() {
        super.e();
        GLES20.glDeleteTextures(2, new int[]{this.f18940u, this.f18944y}, 0);
        this.f18940u = -1;
        this.f18944y = -1;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void i() {
        if (this.f18940u != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f18940u);
            GLES20.glUniform1i(this.f18939t, 3);
        }
        if (this.f18944y != -1) {
            GLES20.glActiveTexture(33988);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f18944y);
            GLES20.glUniform1i(this.f18943x, 4);
        }
        GLES20.glUniform2fv(this.C, 1, FloatBuffer.wrap(this.D));
        GLES20.glUniform3fv(this.A, 1, FloatBuffer.wrap(this.B));
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void j() {
        if (this.f18940u != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
            GLES20.glActiveTexture(33984);
        }
        if (this.f18944y != -1) {
            GLES20.glActiveTexture(33988);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
            GLES20.glActiveTexture(33984);
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void c() {
        super.c();
        a(this.f18937r, this.f18938s, this.f18941v, this.f18942w, this.f18945z);
    }

    public void a(float f2, final Bitmap bitmap, float f3, final Bitmap bitmap2, float f4) {
        a(f2, f3, f4);
        a(new Runnable() { // from class: com.tencent.liteav.beauty.b.m.1
            @Override // java.lang.Runnable
            public void run() {
                m.this.f18938s = bitmap;
                m.this.f18942w = bitmap2;
                Bitmap bitmap3 = bitmap;
                if (bitmap3 != null) {
                    m mVar = m.this;
                    mVar.f18940u = TXCOpenGlUtils.a(bitmap3, mVar.f18940u, false);
                    m.this.D[0] = 1.0f;
                } else {
                    if (m.this.f18940u != -1) {
                        GLES20.glDeleteTextures(1, new int[]{m.this.f18940u}, 0);
                    }
                    m.this.f18940u = -1;
                    m.this.D[0] = 0.0f;
                }
                Bitmap bitmap4 = bitmap2;
                if (bitmap4 != null) {
                    m mVar2 = m.this;
                    mVar2.f18944y = TXCOpenGlUtils.a(bitmap4, mVar2.f18944y, false);
                    m.this.D[1] = 1.0f;
                } else {
                    if (m.this.f18944y != -1) {
                        GLES20.glDeleteTextures(1, new int[]{m.this.f18944y}, 0);
                    }
                    m.this.f18944y = -1;
                    m.this.D[1] = 0.0f;
                }
            }
        });
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        this.f18939t = GLES20.glGetUniformLocation(q(), "inputImageTexture2");
        this.f18943x = GLES20.glGetUniformLocation(q(), "inputImageTexture3");
        this.A = GLES20.glGetUniformLocation(q(), "v3_params");
        this.C = GLES20.glGetUniformLocation(q(), "v2_texs");
        return super.b();
    }

    public void a(float f2) {
        a(this.f18937r, f2, 0.0f);
    }

    public void a(float f2, float f3, float f4) {
        this.f18937r = f2;
        this.f18941v = f3;
        this.f18945z = f4;
        float[] fArr = this.B;
        fArr[0] = f2;
        fArr[1] = f3;
        fArr[2] = f4;
    }

    public m(String str, String str2) {
        super(str, str2);
        this.f18940u = -1;
        this.f18944y = -1;
    }

    public m() {
        this.f18940u = -1;
        this.f18944y = -1;
    }
}
