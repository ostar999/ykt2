package com.tencent.liteav.videobase.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.e;
import com.tencent.liteav.videobase.frame.c;
import com.tencent.liteav.videobase.utils.OpenGlUtils;
import com.tencent.liteav.videobase.utils.b;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: f, reason: collision with root package name */
    private static final float[] f19952f = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};

    /* renamed from: g, reason: collision with root package name */
    private static final AtomicInteger f19953g = new AtomicInteger();

    /* renamed from: a, reason: collision with root package name */
    protected final e f19954a;

    /* renamed from: b, reason: collision with root package name */
    protected int f19955b;

    /* renamed from: c, reason: collision with root package name */
    protected int f19956c;

    /* renamed from: d, reason: collision with root package name */
    protected int f19957d;

    /* renamed from: e, reason: collision with root package name */
    protected c f19958e;

    /* renamed from: h, reason: collision with root package name */
    private final b f19959h;

    /* renamed from: i, reason: collision with root package name */
    private final com.tencent.liteav.videobase.utils.c f19960i;

    /* renamed from: j, reason: collision with root package name */
    private int f19961j;

    /* renamed from: k, reason: collision with root package name */
    private int f19962k;

    /* renamed from: l, reason: collision with root package name */
    private boolean f19963l;

    /* renamed from: m, reason: collision with root package name */
    private float[] f19964m;

    public a() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nuniform mat4 textureTransform;\nvarying highp vec2 textureCoordinate;\nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "varying highp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    public int a() {
        return R2.attr.tab_indicator_height;
    }

    public void a(int i2) {
    }

    public final void a(c cVar) {
        if (this.f19963l) {
            return;
        }
        this.f19962k = c();
        this.f19955b = GLES20.glGetAttribLocation(d(), "position");
        this.f19956c = GLES20.glGetUniformLocation(d(), "inputImageTexture");
        this.f19957d = GLES20.glGetAttribLocation(d(), "inputTextureCoordinate");
        this.f19961j = GLES20.glGetUniformLocation(d(), "textureTransform");
        b(cVar);
        this.f19963l = true;
        TXCLog.d("TXCGPUImageFilter", "%s initialized, count: %d", this, Integer.valueOf(f19953g.incrementAndGet()));
    }

    public final void b() {
        if (this.f19963l) {
            f();
            g();
            this.f19963l = false;
            int i2 = this.f19962k;
            if (i2 != -1) {
                GLES20.glDeleteProgram(i2);
                this.f19962k = -1;
            }
            TXCLog.d("TXCGPUImageFilter", "%s uninitialized, count: %d", this, Integer.valueOf(f19953g.decrementAndGet()));
        }
    }

    public int c() {
        return this.f19960i.a();
    }

    public final int d() {
        return this.f19962k;
    }

    public void e() {
    }

    public void f() {
        this.f19959h.a();
    }

    public void g() {
    }

    public a(String str, String str2) {
        this.f19954a = new e(-1, -1);
        this.f19962k = -1;
        this.f19959h = new b();
        this.f19960i = new com.tencent.liteav.videobase.utils.c(str, str2);
    }

    public void b(c cVar) {
        this.f19958e = cVar;
    }

    public final void a(Runnable runnable) {
        this.f19959h.a(runnable);
    }

    public void a(int i2, int i3) {
        e eVar = this.f19954a;
        eVar.f18712a = i2;
        eVar.f18713b = i3;
    }

    public void a(float[] fArr) {
        this.f19964m = fArr;
    }

    public void a(int i2, c.a aVar, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (this.f19963l) {
            GLES20.glUseProgram(d());
            f();
            floatBuffer.position(0);
            GLES20.glVertexAttribPointer(this.f19955b, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) floatBuffer);
            GLES20.glEnableVertexAttribArray(this.f19955b);
            floatBuffer2.position(0);
            GLES20.glVertexAttribPointer(this.f19957d, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) floatBuffer2);
            GLES20.glEnableVertexAttribArray(this.f19957d);
            if (i2 != -1) {
                GLES20.glActiveTexture(33984);
                OpenGlUtils.bindTexture(a(), i2);
                GLES20.glUniform1i(this.f19956c, 0);
            }
            if (aVar != null) {
                aVar.a();
            } else {
                OpenGlUtils.bindFramebuffer(36160, 0);
            }
            float[] fArr = this.f19964m;
            if (fArr == null) {
                fArr = f19952f;
            }
            GLES20.glUniformMatrix4fv(this.f19961j, 1, false, fArr, 0);
            a(i2);
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.f19955b);
            GLES20.glDisableVertexAttribArray(this.f19957d);
            e();
            GLES20.glActiveTexture(33984);
            OpenGlUtils.bindTexture(a(), 0);
            if (aVar != null) {
                aVar.b();
            }
        }
    }
}
