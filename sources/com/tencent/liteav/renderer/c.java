package com.tencent.liteav.renderer;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.tencent.liteav.basic.log.TXCLog;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private final float[] f19757a;

    /* renamed from: b, reason: collision with root package name */
    private final float[] f19758b;

    /* renamed from: c, reason: collision with root package name */
    private FloatBuffer f19759c;

    /* renamed from: f, reason: collision with root package name */
    private int f19762f;

    /* renamed from: h, reason: collision with root package name */
    private int f19764h;

    /* renamed from: i, reason: collision with root package name */
    private int f19765i;

    /* renamed from: j, reason: collision with root package name */
    private int f19766j;

    /* renamed from: k, reason: collision with root package name */
    private int f19767k;

    /* renamed from: m, reason: collision with root package name */
    private boolean f19769m;

    /* renamed from: d, reason: collision with root package name */
    private float[] f19760d = new float[16];

    /* renamed from: e, reason: collision with root package name */
    private float[] f19761e = new float[16];

    /* renamed from: g, reason: collision with root package name */
    private int f19763g = -12345;

    /* renamed from: l, reason: collision with root package name */
    private boolean f19768l = false;

    /* renamed from: n, reason: collision with root package name */
    private boolean f19770n = false;

    /* renamed from: o, reason: collision with root package name */
    private int f19771o = -1;

    /* renamed from: p, reason: collision with root package name */
    private int f19772p = 0;

    /* renamed from: q, reason: collision with root package name */
    private int f19773q = 0;

    public c(boolean z2) {
        float[] fArr = {-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
        this.f19757a = fArr;
        float[] fArr2 = {1.0f, -1.0f, 0.0f, 1.0f, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f};
        this.f19758b = fArr2;
        this.f19769m = z2;
        if (z2) {
            FloatBuffer floatBufferAsFloatBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            this.f19759c = floatBufferAsFloatBuffer;
            floatBufferAsFloatBuffer.put(fArr).position(0);
        } else {
            FloatBuffer floatBufferAsFloatBuffer2 = ByteBuffer.allocateDirect(fArr2.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            this.f19759c = floatBufferAsFloatBuffer2;
            floatBufferAsFloatBuffer2.put(fArr2).position(0);
        }
        Matrix.setIdentityM(this.f19761e, 0);
    }

    private void b(int i2, int i3) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(R2.id.ly_collection);
        if (this.f19768l) {
            this.f19768l = false;
            return;
        }
        GLES20.glUseProgram(this.f19762f);
        a("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(i2, i3);
        this.f19759c.position(0);
        GLES20.glVertexAttribPointer(this.f19766j, 3, R2.color.m3_ref_palette_dynamic_tertiary60, false, 20, (Buffer) this.f19759c);
        a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.f19766j);
        a("glEnableVertexAttribArray maPositionHandle");
        this.f19759c.position(3);
        GLES20.glVertexAttribPointer(this.f19767k, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 20, (Buffer) this.f19759c);
        a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.f19767k);
        a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.f19760d, 0);
        GLES20.glUniformMatrix4fv(this.f19764h, 1, false, this.f19760d, 0);
        if (this.f19772p % 8 != 0) {
            Matrix.scaleM(this.f19761e, 0, ((r10 - 1) * 1.0f) / (((r10 + 7) / 8) * 8), 1.0f, 1.0f);
        }
        if (this.f19773q % 8 != 0) {
            Matrix.scaleM(this.f19761e, 0, 1.0f, ((r10 - 1) * 1.0f) / (((r10 + 7) / 8) * 8), 1.0f);
        }
        GLES20.glUniformMatrix4fv(this.f19765i, 1, false, this.f19761e, 0);
        GLES20.glDrawArrays(5, 0, 4);
        a("glDrawArrays");
        GLES20.glFinish();
    }

    private void d() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        int i2 = iArr[0];
        this.f19763g = i2;
        GLES20.glBindTexture(36197, i2);
        a("glBindTexture mTextureID");
    }

    public int a() {
        return this.f19763g;
    }

    public void c() {
        int i2 = this.f19762f;
        if (i2 != 0) {
            GLES20.glDeleteProgram(i2);
        }
        GLES20.glDeleteTextures(1, new int[]{this.f19763g}, 0);
        this.f19763g = -1;
    }

    public void a(SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            return;
        }
        a("onDrawFrame start");
        surfaceTexture.getTransformMatrix(this.f19761e);
        b(36197, this.f19763g);
    }

    public void a(int i2, int i3) {
        this.f19772p = i2;
        this.f19773q = i3;
    }

    public void a(int i2, boolean z2, int i3) {
        if (this.f19770n != z2 || this.f19771o != i3) {
            this.f19770n = z2;
            this.f19771o = i3;
            float[] fArr = new float[20];
            for (int i4 = 0; i4 < 20; i4++) {
                fArr[i4] = this.f19758b[i4];
            }
            if (this.f19770n) {
                fArr[0] = -fArr[0];
                fArr[5] = -fArr[5];
                fArr[10] = -fArr[10];
                fArr[15] = -fArr[15];
            }
            int i5 = i3 / 90;
            for (int i6 = 0; i6 < i5; i6++) {
                float f2 = fArr[3];
                float f3 = fArr[4];
                fArr[3] = fArr[8];
                fArr[4] = fArr[9];
                fArr[8] = fArr[18];
                fArr[9] = fArr[19];
                fArr[18] = fArr[13];
                fArr[19] = fArr[14];
                fArr[13] = f2;
                fArr[14] = f3;
            }
            this.f19759c.clear();
            this.f19759c.put(fArr).position(0);
        }
        b(R2.attr.tab_indicator_height, i2);
    }

    private int a(int i2, String str) {
        int iGlCreateShader = GLES20.glCreateShader(i2);
        a("glCreateShader type=" + i2);
        GLES20.glShaderSource(iGlCreateShader, str);
        GLES20.glCompileShader(iGlCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return iGlCreateShader;
        }
        TXCLog.e("TXCOesTextureRender", "Could not compile shader " + i2 + ":");
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.append(GLES20.glGetShaderInfoLog(iGlCreateShader));
        TXCLog.e("TXCOesTextureRender", sb.toString());
        GLES20.glDeleteShader(iGlCreateShader);
        return 0;
    }

    private int a(String str, String str2) {
        int iA;
        int iA2 = a(35633, str);
        if (iA2 == 0 || (iA = a(35632, str2)) == 0) {
            return 0;
        }
        int iGlCreateProgram = GLES20.glCreateProgram();
        a("glCreateProgram");
        if (iGlCreateProgram == 0) {
            TXCLog.e("TXCOesTextureRender", "Could not create program");
        }
        GLES20.glAttachShader(iGlCreateProgram, iA2);
        a("glAttachShader");
        GLES20.glAttachShader(iGlCreateProgram, iA);
        a("glAttachShader");
        GLES20.glLinkProgram(iGlCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(iGlCreateProgram, 35714, iArr, 0);
        if (iArr[0] == 1) {
            return iGlCreateProgram;
        }
        TXCLog.e("TXCOesTextureRender", "Could not link program: ");
        TXCLog.e("TXCOesTextureRender", GLES20.glGetProgramInfoLog(iGlCreateProgram));
        GLES20.glDeleteProgram(iGlCreateProgram);
        return 0;
    }

    public void b() {
        if (this.f19769m) {
            this.f19762f = a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        } else {
            this.f19762f = a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        int i2 = this.f19762f;
        if (i2 == 0) {
            TXCLog.e("TXCOesTextureRender", "failed creating program");
            return;
        }
        this.f19766j = GLES20.glGetAttribLocation(i2, "aPosition");
        a("glGetAttribLocation aPosition");
        if (this.f19766j == -1) {
            TXCLog.e("TXCOesTextureRender", "Could not get attrib location for aPosition");
            return;
        }
        this.f19767k = GLES20.glGetAttribLocation(this.f19762f, "aTextureCoord");
        a("glGetAttribLocation aTextureCoord");
        if (this.f19767k == -1) {
            TXCLog.e("TXCOesTextureRender", "Could not get attrib location for aTextureCoord");
            return;
        }
        this.f19764h = GLES20.glGetUniformLocation(this.f19762f, "uMVPMatrix");
        a("glGetUniformLocation uMVPMatrix");
        if (this.f19764h == -1) {
            TXCLog.e("TXCOesTextureRender", "Could not get attrib location for uMVPMatrix");
            return;
        }
        this.f19765i = GLES20.glGetUniformLocation(this.f19762f, "uSTMatrix");
        a("glGetUniformLocation uSTMatrix");
        if (this.f19765i == -1) {
            TXCLog.e("TXCOesTextureRender", "Could not get attrib location for uSTMatrix");
            return;
        }
        if (this.f19769m) {
            d();
        }
        GLES20.glTexParameterf(36197, R2.drawable.ic_home_index_normal_day, 9729.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, R2.drawable.ic_home_index_normal_night, 33071);
        GLES20.glTexParameteri(36197, R2.drawable.ic_home_index_select_day, 33071);
        a("glTexParameter");
    }

    public void a(String str) {
        int iGlGetError = GLES20.glGetError();
        if (iGlGetError != 0) {
            TXCLog.e("TXCOesTextureRender", str + ": glError " + iGlGetError);
        }
    }
}
