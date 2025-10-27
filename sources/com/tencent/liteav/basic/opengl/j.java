package com.tencent.liteav.basic.opengl;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;

/* loaded from: classes6.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    protected int f18592a;

    /* renamed from: b, reason: collision with root package name */
    protected int f18593b;

    /* renamed from: c, reason: collision with root package name */
    protected int f18594c;

    /* renamed from: d, reason: collision with root package name */
    protected int f18595d;

    /* renamed from: e, reason: collision with root package name */
    protected int f18596e;

    /* renamed from: f, reason: collision with root package name */
    protected int f18597f;

    /* renamed from: g, reason: collision with root package name */
    protected boolean f18598g;

    /* renamed from: h, reason: collision with root package name */
    protected FloatBuffer f18599h;

    /* renamed from: i, reason: collision with root package name */
    protected FloatBuffer f18600i;

    /* renamed from: j, reason: collision with root package name */
    protected float[] f18601j;

    /* renamed from: k, reason: collision with root package name */
    protected float[] f18602k;

    /* renamed from: l, reason: collision with root package name */
    protected a f18603l;

    /* renamed from: m, reason: collision with root package name */
    protected int f18604m;

    /* renamed from: n, reason: collision with root package name */
    protected int f18605n;

    /* renamed from: o, reason: collision with root package name */
    protected boolean f18606o;

    /* renamed from: p, reason: collision with root package name */
    protected boolean f18607p;

    /* renamed from: q, reason: collision with root package name */
    protected boolean f18608q;

    /* renamed from: r, reason: collision with root package name */
    private final LinkedList<Runnable> f18609r;

    /* renamed from: s, reason: collision with root package name */
    private final String f18610s;

    /* renamed from: t, reason: collision with root package name */
    private final String f18611t;

    /* renamed from: u, reason: collision with root package name */
    private boolean f18612u;

    /* renamed from: v, reason: collision with root package name */
    private int f18613v;

    /* renamed from: w, reason: collision with root package name */
    private float[] f18614w;

    public interface a {
        void a(int i2);
    }

    public j() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}", false);
    }

    public boolean a() {
        int iA = TXCOpenGlUtils.a(this.f18610s, this.f18611t);
        this.f18592a = iA;
        if (iA == 0 || !b()) {
            this.f18598g = false;
        } else {
            this.f18598g = true;
        }
        c();
        return this.f18598g;
    }

    public void b(boolean z2) {
        this.f18607p = z2;
        TXCLog.i("TXCGPUFilter", "set Nearest model " + z2);
    }

    public void c() {
    }

    public void c(final int i2, final float[] fArr) {
        a(new Runnable() { // from class: com.tencent.liteav.basic.opengl.j.5
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform4fv(i2, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    public void d() {
        GLES20.glDeleteProgram(this.f18592a);
        e();
        this.f18598g = false;
    }

    public void e() {
        f();
        this.f18597f = -1;
        this.f18596e = -1;
    }

    public void f() {
        int i2 = this.f18604m;
        if (i2 != -1) {
            GLES20.glDeleteFramebuffers(1, new int[]{i2}, 0);
            this.f18604m = -1;
        }
        int i3 = this.f18605n;
        if (i3 != -1) {
            GLES20.glDeleteTextures(1, new int[]{i3}, 0);
            this.f18605n = -1;
        }
    }

    public void g() {
        if (this.f18602k != null) {
            for (int i2 = 0; i2 < 8; i2 += 2) {
                float[] fArr = this.f18602k;
                fArr[i2] = 1.0f - fArr[i2];
            }
            a(this.f18601j, this.f18602k);
        }
    }

    public void h() {
        if (this.f18602k != null) {
            for (int i2 = 1; i2 < 8; i2 += 2) {
                float[] fArr = this.f18602k;
                fArr[i2] = 1.0f - fArr[i2];
            }
            a(this.f18601j, this.f18602k);
        }
    }

    public void i() {
    }

    public void j() {
    }

    public void k() {
        while (!this.f18609r.isEmpty()) {
            this.f18609r.removeFirst().run();
        }
    }

    public int l() {
        return this.f18605n;
    }

    public int m() {
        return this.f18604m;
    }

    public boolean n() {
        return this.f18598g;
    }

    public int o() {
        return this.f18596e;
    }

    public int p() {
        return this.f18597f;
    }

    public int q() {
        return this.f18592a;
    }

    public j(String str, String str2) {
        this(str, str2, false);
    }

    public j(String str, String str2, boolean z2) {
        this.f18612u = false;
        this.f18613v = -1;
        this.f18614w = null;
        this.f18604m = -1;
        this.f18605n = -1;
        this.f18606o = false;
        this.f18607p = false;
        this.f18608q = false;
        this.f18609r = new LinkedList<>();
        this.f18610s = str;
        this.f18611t = str2;
        this.f18608q = z2;
        if (true == z2) {
            TXCLog.i("TXCGPUFilter", "set Oes fileter");
        }
        float[] fArr = m.f18642e;
        FloatBuffer floatBufferAsFloatBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f18599h = floatBufferAsFloatBuffer;
        this.f18601j = fArr;
        floatBufferAsFloatBuffer.put(fArr).position(0);
        this.f18600i = ByteBuffer.allocateDirect(m.f18638a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        float[] fArrA = m.a(l.NORMAL, false, true);
        this.f18602k = fArrA;
        this.f18600i.put(fArrA).position(0);
    }

    public boolean b() {
        this.f18593b = GLES20.glGetAttribLocation(this.f18592a, "position");
        this.f18594c = GLES20.glGetUniformLocation(this.f18592a, "inputImageTexture");
        this.f18613v = GLES20.glGetUniformLocation(this.f18592a, "textureTransform");
        this.f18595d = GLES20.glGetAttribLocation(this.f18592a, "inputTextureCoordinate");
        return true;
    }

    public void a(boolean z2) {
        this.f18606o = z2;
    }

    public int b(int i2, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (!this.f18598g) {
            return -1;
        }
        a(i2, floatBuffer, floatBuffer2);
        a aVar = this.f18603l;
        if (!(aVar instanceof a)) {
            return 1;
        }
        aVar.a(i2);
        return 1;
    }

    public void a(a aVar) {
        this.f18612u = aVar != null;
        this.f18603l = aVar;
    }

    private static float[] a(FloatBuffer floatBuffer) {
        if (floatBuffer.limit() <= 0) {
            return null;
        }
        float[] fArr = new float[floatBuffer.limit()];
        for (int i2 = 0; i2 < floatBuffer.limit(); i2++) {
            fArr[i2] = floatBuffer.get(i2);
        }
        return fArr;
    }

    public int b(int i2) {
        return a(i2, this.f18604m, this.f18605n);
    }

    public void b(final int i2, final int i3) {
        a(new Runnable() { // from class: com.tencent.liteav.basic.opengl.j.1
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform1i(i2, i3);
            }
        });
    }

    public void b(final int i2, final float[] fArr) {
        a(new Runnable() { // from class: com.tencent.liteav.basic.opengl.j.4
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform3fv(i2, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    public void a(int i2, int i3) {
        if (this.f18597f == i3 && this.f18596e == i2) {
            return;
        }
        this.f18596e = i2;
        this.f18597f = i3;
        if (this.f18606o) {
            if (this.f18604m != -1) {
                f();
            }
            int[] iArr = new int[1];
            GLES20.glGenFramebuffers(1, iArr, 0);
            this.f18604m = iArr[0];
            this.f18605n = TXCOpenGlUtils.a(i2, i3, R2.dimen.dm_200, R2.dimen.dm_200);
            GLES20.glBindFramebuffer(36160, this.f18604m);
            GLES20.glFramebufferTexture2D(36160, 36064, R2.attr.tab_indicator_height, this.f18605n, 0);
            GLES20.glBindFramebuffer(36160, 0);
        }
    }

    public void a(int i2, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        float[] fArr;
        GLES20.glUseProgram(this.f18592a);
        k();
        if (this.f18598g) {
            floatBuffer.position(0);
            GLES20.glVertexAttribPointer(this.f18593b, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) floatBuffer);
            GLES20.glEnableVertexAttribArray(this.f18593b);
            floatBuffer2.position(0);
            GLES20.glVertexAttribPointer(this.f18595d, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) floatBuffer2);
            GLES20.glEnableVertexAttribArray(this.f18595d);
            int i3 = this.f18613v;
            if (i3 >= 0 && (fArr = this.f18614w) != null) {
                GLES20.glUniformMatrix4fv(i3, 1, false, fArr, 0);
            }
            if (i2 != -1) {
                GLES20.glActiveTexture(33984);
                if (true == this.f18608q) {
                    GLES20.glBindTexture(36197, i2);
                } else {
                    GLES20.glBindTexture(R2.attr.tab_indicator_height, i2);
                }
                GLES20.glUniform1i(this.f18594c, 0);
            }
            i();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.f18593b);
            GLES20.glDisableVertexAttribArray(this.f18595d);
            j();
            if (true == this.f18608q) {
                GLES20.glBindTexture(36197, 0);
            } else {
                GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
            }
        }
    }

    public void a(float[] fArr) {
        this.f18614w = fArr;
    }

    public int a(int i2) {
        return b(i2, this.f18599h, this.f18600i);
    }

    public int a(int i2, int i3, int i4) {
        if (!this.f18598g) {
            return -1;
        }
        GLES20.glBindFramebuffer(36160, i3);
        a(i2, this.f18599h, this.f18600i);
        a aVar = this.f18603l;
        if (aVar instanceof a) {
            aVar.a(i4);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return i4;
    }

    public void a(float[] fArr, float[] fArr2) {
        this.f18601j = fArr;
        FloatBuffer floatBufferAsFloatBuffer = ByteBuffer.allocateDirect(m.f18642e.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f18599h = floatBufferAsFloatBuffer;
        floatBufferAsFloatBuffer.put(fArr).position(0);
        this.f18602k = fArr2;
        FloatBuffer floatBufferAsFloatBuffer2 = ByteBuffer.allocateDirect(m.f18638a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f18600i = floatBufferAsFloatBuffer2;
        floatBufferAsFloatBuffer2.put(fArr2).position(0);
    }

    public float[] a(int i2, int i3, FloatBuffer floatBuffer, com.tencent.liteav.basic.opengl.a aVar, int i4) {
        float[] fArrA;
        if (floatBuffer != null) {
            fArrA = a(floatBuffer);
        } else if (4 == i4) {
            fArrA = m.a(l.NORMAL, false, false);
        } else {
            fArrA = m.a(l.NORMAL, false, true);
        }
        if (aVar != null) {
            float f2 = i2 * 1.0f;
            float f3 = aVar.f18492a / f2;
            float f4 = ((i2 - r10) - aVar.f18494c) / f2;
            float f5 = i3 * 1.0f;
            float f6 = aVar.f18493b / f5;
            float f7 = ((i3 - r10) - aVar.f18495d) / f5;
            for (int i5 = 0; i5 < fArrA.length / 2; i5++) {
                int i6 = i5 * 2;
                float f8 = fArrA[i6];
                if (f8 < 0.5f) {
                    fArrA[i6] = f8 + f3;
                } else {
                    fArrA[i6] = f8 - f4;
                }
                int i7 = i6 + 1;
                float f9 = fArrA[i7];
                if (f9 < 0.5f) {
                    fArrA[i7] = f9 + f6;
                } else {
                    fArrA[i7] = f9 - f7;
                }
            }
        }
        return fArrA;
    }

    public void a(int i2, int i3, int i4, float[] fArr, float f2, boolean z2, boolean z3) {
        int i5;
        float[] fArrA;
        if (fArr == null) {
            fArrA = m.a(l.NORMAL, false, true);
            i5 = i2;
        } else {
            i5 = i2;
            fArrA = fArr;
        }
        float f3 = i5;
        int i6 = i3;
        float f4 = i6;
        float f5 = f3 / f4;
        if (f5 > f2) {
            i5 = (int) (f4 * f2);
        } else if (f5 < f2) {
            i6 = (int) (f3 / f2);
        }
        float f6 = (1.0f - (i5 / f3)) / 2.0f;
        float f7 = (1.0f - (i6 / f4)) / 2.0f;
        for (int i7 = 0; i7 < fArrA.length / 2; i7++) {
            int i8 = i7 * 2;
            float f8 = fArrA[i8];
            if (f8 < 0.5f) {
                fArrA[i8] = f8 + f6;
            } else {
                fArrA[i8] = f8 - f6;
            }
            int i9 = i8 + 1;
            float f9 = fArrA[i9];
            if (f9 < 0.5f) {
                fArrA[i9] = f9 + f7;
            } else {
                fArrA[i9] = f9 - f7;
            }
        }
        int i10 = i4 / 90;
        for (int i11 = 0; i11 < i10; i11++) {
            float f10 = fArrA[0];
            float f11 = fArrA[1];
            fArrA[0] = fArrA[2];
            fArrA[1] = fArrA[3];
            fArrA[2] = fArrA[6];
            fArrA[3] = fArrA[7];
            fArrA[6] = fArrA[4];
            fArrA[7] = fArrA[5];
            fArrA[4] = f10;
            fArrA[5] = f11;
        }
        if (i10 == 0 || i10 == 2) {
            if (z2) {
                fArrA[0] = 1.0f - fArrA[0];
                fArrA[2] = 1.0f - fArrA[2];
                fArrA[4] = 1.0f - fArrA[4];
                fArrA[6] = 1.0f - fArrA[6];
            }
            if (z3) {
                fArrA[1] = 1.0f - fArrA[1];
                fArrA[3] = 1.0f - fArrA[3];
                fArrA[5] = 1.0f - fArrA[5];
                fArrA[7] = 1.0f - fArrA[7];
            }
        } else {
            if (z3) {
                fArrA[0] = 1.0f - fArrA[0];
                fArrA[2] = 1.0f - fArrA[2];
                fArrA[4] = 1.0f - fArrA[4];
                fArrA[6] = 1.0f - fArrA[6];
            }
            if (z2) {
                fArrA[1] = 1.0f - fArrA[1];
                fArrA[3] = 1.0f - fArrA[3];
                fArrA[5] = 1.0f - fArrA[5];
                fArrA[7] = 1.0f - fArrA[7];
            }
        }
        a((float[]) m.f18642e.clone(), fArrA);
    }

    public void a(int i2, FloatBuffer floatBuffer) {
        float[] fArrA;
        if (floatBuffer == null) {
            fArrA = m.a(l.NORMAL, false, true);
        } else {
            fArrA = a(floatBuffer);
        }
        int i3 = i2 / 90;
        for (int i4 = 0; i4 < i3; i4++) {
            float f2 = fArrA[0];
            float f3 = fArrA[1];
            fArrA[0] = fArrA[2];
            fArrA[1] = fArrA[3];
            fArrA[2] = fArrA[6];
            fArrA[3] = fArrA[7];
            fArrA[6] = fArrA[4];
            fArrA[7] = fArrA[5];
            fArrA[4] = f2;
            fArrA[5] = f3;
        }
        a((float[]) m.f18642e.clone(), fArrA);
    }

    public void a(final int i2, final float f2) {
        a(new Runnable() { // from class: com.tencent.liteav.basic.opengl.j.2
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform1f(i2, f2);
            }
        });
    }

    public void a(final int i2, final float[] fArr) {
        a(new Runnable() { // from class: com.tencent.liteav.basic.opengl.j.3
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform2fv(i2, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    public void a(Runnable runnable) {
        synchronized (this.f18609r) {
            this.f18609r.addLast(runnable);
        }
    }
}
