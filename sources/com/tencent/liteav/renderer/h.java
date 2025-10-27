package com.tencent.liteav.renderer;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.tencent.liteav.basic.log.TXCLog;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes6.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    public static int f19844a = 1;

    /* renamed from: b, reason: collision with root package name */
    public static int f19845b = 2;

    /* renamed from: o, reason: collision with root package name */
    private boolean f19858o;

    /* renamed from: p, reason: collision with root package name */
    private final float[] f19859p;

    /* renamed from: q, reason: collision with root package name */
    private FloatBuffer f19860q;

    /* renamed from: t, reason: collision with root package name */
    private int f19863t;

    /* renamed from: w, reason: collision with root package name */
    private int f19866w;

    /* renamed from: x, reason: collision with root package name */
    private int f19867x;

    /* renamed from: y, reason: collision with root package name */
    private int f19868y;

    /* renamed from: z, reason: collision with root package name */
    private int f19869z;

    /* renamed from: c, reason: collision with root package name */
    private int f19846c = 0;

    /* renamed from: d, reason: collision with root package name */
    private int f19847d = 0;

    /* renamed from: e, reason: collision with root package name */
    private int f19848e = 0;

    /* renamed from: f, reason: collision with root package name */
    private int f19849f = 0;

    /* renamed from: g, reason: collision with root package name */
    private int f19850g = f19845b;

    /* renamed from: h, reason: collision with root package name */
    private int f19851h = 0;

    /* renamed from: i, reason: collision with root package name */
    private boolean f19852i = false;

    /* renamed from: j, reason: collision with root package name */
    private float[] f19853j = new float[16];

    /* renamed from: k, reason: collision with root package name */
    private float[] f19854k = new float[16];

    /* renamed from: l, reason: collision with root package name */
    private float f19855l = 1.0f;

    /* renamed from: m, reason: collision with root package name */
    private float f19856m = 1.0f;

    /* renamed from: n, reason: collision with root package name */
    private boolean f19857n = false;

    /* renamed from: r, reason: collision with root package name */
    private float[] f19861r = new float[16];

    /* renamed from: s, reason: collision with root package name */
    private float[] f19862s = new float[16];

    /* renamed from: u, reason: collision with root package name */
    private int f19864u = -12345;

    /* renamed from: v, reason: collision with root package name */
    private int f19865v = -12345;

    public h(Boolean bool) {
        this.f19858o = true;
        float[] fArr = {-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
        this.f19859p = fArr;
        this.f19858o = bool.booleanValue();
        FloatBuffer floatBufferAsFloatBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f19860q = floatBufferAsFloatBuffer;
        floatBufferAsFloatBuffer.put(fArr).position(0);
        Matrix.setIdentityM(this.f19862s, 0);
    }

    private void e() {
        int i2 = this.f19865v;
        if (i2 != -12345) {
            GLES20.glDeleteFramebuffers(1, new int[]{i2}, 0);
            this.f19865v = -12345;
        }
        int i3 = this.f19864u;
        if (i3 != -12345) {
            GLES20.glDeleteTextures(1, new int[]{i3}, 0);
            this.f19864u = -12345;
        }
    }

    public void a(int i2, int i3) {
        if (i2 == this.f19846c && i3 == this.f19847d) {
            return;
        }
        TXCLog.i("TXTweenFilter", "Output resolution change: " + this.f19846c + "*" + this.f19847d + " -> " + i2 + "*" + i3);
        this.f19846c = i2;
        this.f19847d = i3;
        if (i2 > i3) {
            Matrix.orthoM(this.f19853j, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
            this.f19855l = 1.0f;
            this.f19856m = 1.0f;
        } else {
            Matrix.orthoM(this.f19853j, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
            this.f19855l = 1.0f;
            this.f19856m = 1.0f;
        }
        this.f19857n = true;
    }

    public void b(int i2, int i3) {
        if (i2 == this.f19848e && i3 == this.f19849f) {
            return;
        }
        TXCLog.i("TXTweenFilter", "Input resolution change: " + this.f19848e + "*" + this.f19849f + " -> " + i2 + "*" + i3);
        this.f19848e = i2;
        this.f19849f = i3;
    }

    public void c(int i2) {
        GLES20.glViewport(0, 0, this.f19846c, this.f19847d);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(R2.id.ly_collection);
        GLES20.glUseProgram(this.f19863t);
        a("glUseProgram");
        if (this.f19858o) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, i2);
        } else {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, i2);
        }
        this.f19860q.position(0);
        GLES20.glVertexAttribPointer(this.f19868y, 3, R2.color.m3_ref_palette_dynamic_tertiary60, false, 20, (Buffer) this.f19860q);
        a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.f19868y);
        a("glEnableVertexAttribArray maPositionHandle");
        this.f19860q.position(3);
        GLES20.glVertexAttribPointer(this.f19869z, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 20, (Buffer) this.f19860q);
        a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.f19869z);
        a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.f19861r, 0);
        b(this.f19861r);
        GLES20.glUniformMatrix4fv(this.f19866w, 1, false, this.f19861r, 0);
        GLES20.glUniformMatrix4fv(this.f19867x, 1, false, this.f19862s, 0);
        a("glDrawArrays");
        GLES20.glDrawArrays(5, 0, 4);
        a("glDrawArrays");
        if (this.f19858o) {
            GLES20.glBindTexture(36197, 0);
        } else {
            GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
        }
    }

    public int d(int i2) {
        d();
        int i3 = this.f19865v;
        if (i3 == -12345) {
            TXCLog.d("TXTweenFilter", "invalid frame buffer id");
            return i2;
        }
        GLES20.glBindFramebuffer(36160, i3);
        c(i2);
        GLES20.glBindFramebuffer(36160, 0);
        return this.f19864u;
    }

    public void b(int i2) {
        this.f19851h = i2;
    }

    private void b(float[] fArr) {
        int i2;
        int i3 = this.f19847d;
        if (i3 == 0 || (i2 = this.f19846c) == 0) {
            return;
        }
        int i4 = this.f19848e;
        int i5 = this.f19849f;
        int i6 = this.f19851h;
        if (i6 == 270 || i6 == 90) {
            i5 = i4;
            i4 = i5;
        }
        float f2 = i4;
        float f3 = (i2 * 1.0f) / f2;
        float f4 = i5;
        float f5 = (i3 * 1.0f) / f4;
        if (this.f19850g != f19844a ? f3 * f4 > i3 : f3 * f4 <= i3) {
            f3 = f5;
        }
        Matrix.setIdentityM(this.f19854k, 0);
        if (this.f19852i) {
            if (this.f19851h % 180 == 0) {
                Matrix.scaleM(this.f19854k, 0, -1.0f, 1.0f, 1.0f);
            } else {
                Matrix.scaleM(this.f19854k, 0, 1.0f, -1.0f, 1.0f);
            }
        }
        Matrix.scaleM(this.f19854k, 0, ((f2 * f3) / this.f19846c) * 1.0f, ((f4 * f3) / this.f19847d) * 1.0f, 1.0f);
        Matrix.rotateM(this.f19854k, 0, this.f19851h, 0.0f, 0.0f, -1.0f);
        Matrix.multiplyMM(fArr, 0, this.f19853j, 0, this.f19854k, 0);
    }

    private void d() {
        if (this.f19857n) {
            TXCLog.i("TXTweenFilter", "reloadFrameBuffer. size = " + this.f19846c + "*" + this.f19847d);
            e();
            int[] iArr = new int[1];
            int[] iArr2 = new int[1];
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glGenFramebuffers(1, iArr2, 0);
            this.f19864u = iArr[0];
            this.f19865v = iArr2[0];
            TXCLog.d("TXTweenFilter", "frameBuffer id = " + this.f19865v + ", texture id = " + this.f19864u);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f19864u);
            a("glBindTexture mFrameBufferTextureID");
            GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, R2.dimen.dm_200, this.f19846c, this.f19847d, 0, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, null);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, 10240, 9729.0f);
            GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071);
            GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071);
            a("glTexParameter");
            GLES20.glBindFramebuffer(36160, this.f19865v);
            GLES20.glFramebufferTexture2D(36160, 36064, R2.attr.tab_indicator_height, this.f19864u, 0);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
            GLES20.glBindFramebuffer(36160, 0);
            this.f19857n = false;
        }
    }

    public boolean a() {
        return this.f19858o;
    }

    public void a(int i2) {
        this.f19850g = i2;
    }

    public void a(boolean z2) {
        this.f19852i = z2;
    }

    public void a(float[] fArr) {
        this.f19862s = fArr;
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
        TXCLog.e("TXTweenFilter", "Could not compile shader " + i2 + ":");
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.append(GLES20.glGetShaderInfoLog(iGlCreateShader));
        TXCLog.e("TXTweenFilter", sb.toString());
        GLES20.glDeleteShader(iGlCreateShader);
        return 0;
    }

    public void b() {
        if (this.f19858o) {
            this.f19863t = a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        } else {
            this.f19863t = a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        int i2 = this.f19863t;
        if (i2 == 0) {
            TXCLog.e("TXTweenFilter", "failed creating program");
            return;
        }
        this.f19868y = GLES20.glGetAttribLocation(i2, "aPosition");
        a("glGetAttribLocation aPosition");
        if (this.f19868y == -1) {
            TXCLog.e("TXTweenFilter", "Could not get attrib location for aPosition");
            return;
        }
        this.f19869z = GLES20.glGetAttribLocation(this.f19863t, "aTextureCoord");
        a("glGetAttribLocation aTextureCoord");
        if (this.f19869z == -1) {
            TXCLog.e("TXTweenFilter", "Could not get attrib location for aTextureCoord");
            return;
        }
        this.f19866w = GLES20.glGetUniformLocation(this.f19863t, "uMVPMatrix");
        a("glGetUniformLocation uMVPMatrix");
        if (this.f19866w == -1) {
            TXCLog.e("TXTweenFilter", "Could not get attrib location for uMVPMatrix");
            return;
        }
        this.f19867x = GLES20.glGetUniformLocation(this.f19863t, "uSTMatrix");
        a("glGetUniformLocation uSTMatrix");
        if (this.f19867x == -1) {
            TXCLog.e("TXTweenFilter", "Could not get attrib location for uSTMatrix");
        }
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
            TXCLog.e("TXTweenFilter", "Could not create program");
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
        TXCLog.e("TXTweenFilter", "Could not link program: ");
        TXCLog.e("TXTweenFilter", GLES20.glGetProgramInfoLog(iGlCreateProgram));
        GLES20.glDeleteProgram(iGlCreateProgram);
        return 0;
    }

    public void c() {
        GLES20.glDeleteProgram(this.f19863t);
        e();
    }

    private void a(String str) {
        while (true) {
            int iGlGetError = GLES20.glGetError();
            if (iGlGetError == 0) {
                return;
            }
            TXCLog.e("TXTweenFilter", str + ": glError " + iGlGetError);
        }
    }
}
