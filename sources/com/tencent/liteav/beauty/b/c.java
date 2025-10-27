package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.beauty.NativeLoad;

/* loaded from: classes6.dex */
public class c extends b {

    /* renamed from: r, reason: collision with root package name */
    private static final String f18858r = "c";

    /* renamed from: s, reason: collision with root package name */
    private f f18859s;

    /* renamed from: t, reason: collision with root package name */
    private a f18860t;

    /* renamed from: u, reason: collision with root package name */
    private r f18861u = null;

    /* renamed from: v, reason: collision with root package name */
    private int f18862v = -1;

    /* renamed from: w, reason: collision with root package name */
    private int f18863w = -1;

    /* renamed from: x, reason: collision with root package name */
    private float f18864x = 0.0f;

    /* renamed from: y, reason: collision with root package name */
    private float f18865y = 0.0f;

    /* renamed from: z, reason: collision with root package name */
    private float f18866z = 0.0f;
    private float A = 0.0f;

    public static class a extends s {

        /* renamed from: x, reason: collision with root package name */
        private int f18876x;

        /* renamed from: y, reason: collision with root package name */
        private int f18877y;

        /* renamed from: z, reason: collision with root package name */
        private int f18878z;

        public a() {
            super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\nattribute vec4 inputTextureCoordinate3;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\nvarying vec2 textureCoordinate3;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n    textureCoordinate3 = inputTextureCoordinate3.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
            this.f18876x = -1;
            this.f18877y = -1;
            this.f18878z = -1;
        }

        @Override // com.tencent.liteav.basic.opengl.j
        public boolean a() {
            int iNativeLoadGLProgram = NativeLoad.nativeLoadGLProgram(1);
            this.f18592a = iNativeLoadGLProgram;
            if (iNativeLoadGLProgram == 0 || !b()) {
                this.f18598g = false;
            } else {
                this.f18598g = true;
            }
            c();
            return this.f18598g;
        }

        @Override // com.tencent.liteav.beauty.b.s, com.tencent.liteav.basic.opengl.j
        public boolean b() {
            return super.b();
        }

        public void c(float f2) {
            a(this.f18878z, (f2 / 10.0f) / 2.0f);
        }

        public void b(float f2) {
            a(this.f18877y, f2 / 3.0f);
        }

        @Override // com.tencent.liteav.beauty.b.s, com.tencent.liteav.basic.opengl.j
        public void a(int i2, int i3) {
            if (this.f18597f == i3 && this.f18596e == i2) {
                return;
            }
            super.a(i2, i3);
            this.f18876x = GLES20.glGetUniformLocation(q(), "smoothDegree");
            this.f18877y = GLES20.glGetUniformLocation(q(), "brightDegree");
            this.f18878z = GLES20.glGetUniformLocation(q(), "ruddyDegree");
        }

        public void a(float f2) {
            a(this.f18876x, c.b(f2));
        }
    }

    private static float a(float f2, float f3, float f4) {
        return f3 + ((f4 - f3) * f2);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public int b(int i2) {
        float f2 = this.f18864x;
        if (f2 > 0.0f || this.f18865y > 0.0f || this.f18866z > 0.0f) {
            i2 = this.f18860t.a(f2 != 0.0f ? this.f18859s.b(i2) : i2, i2, i2);
        }
        return this.A > 0.0f ? this.f18861u.b(i2) : i2;
    }

    @Override // com.tencent.liteav.beauty.b.b
    public boolean c(int i2, int i3) {
        this.f18862v = i2;
        this.f18863w = i3;
        String str = f18858r;
        Log.i(str, "init mFrameWidth = " + i2 + "  mFrameHeight = " + i3);
        if (this.f18859s == null) {
            f fVar = new f();
            this.f18859s = fVar;
            fVar.a(true);
            if (!this.f18859s.a()) {
                Log.e(str, "mNewFaceFilter init Failed");
                return false;
            }
        }
        this.f18859s.a(this.f18862v, this.f18863w);
        if (this.f18860t == null) {
            a aVar = new a();
            this.f18860t = aVar;
            aVar.a(true);
            if (!this.f18860t.a()) {
                Log.e(str, "mBeautyCoreFilter init Failed");
                return false;
            }
        }
        this.f18860t.a(this.f18862v, this.f18863w);
        if (this.f18861u == null) {
            r rVar = new r();
            this.f18861u = rVar;
            rVar.a(true);
            if (!this.f18861u.a()) {
                Log.e(str, "mSharpenessFilter init Failed");
                return false;
            }
        }
        this.f18861u.a(this.f18862v, this.f18863w);
        return true;
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void d(int i2) {
        float f2 = i2;
        this.f18865y = f2;
        a aVar = this.f18860t;
        if (aVar != null) {
            aVar.b(f2);
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void e() {
        a aVar = this.f18860t;
        if (aVar != null) {
            aVar.d();
            this.f18860t = null;
        }
        f fVar = this.f18859s;
        if (fVar != null) {
            fVar.d();
            this.f18859s = null;
        }
        r rVar = this.f18861u;
        if (rVar != null) {
            rVar.d();
            this.f18861u = null;
        }
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void f(int i2) {
        float f2 = i2 / 15.0f;
        if (Math.abs(this.A - f2) < 0.001d) {
            return;
        }
        this.A = f2;
        r rVar = this.f18861u;
        if (rVar != null) {
            rVar.a(f2);
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        if (this.f18862v == i2 && this.f18863w == i3) {
            return;
        }
        Log.i(f18858r, "onOutputSizeChanged mFrameWidth = " + i2 + "  mFrameHeight = " + i3);
        this.f18862v = i2;
        this.f18863w = i3;
        c(i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float b(float f2) {
        if (f2 <= 1.0f) {
            return 0.1f;
        }
        double d3 = f2;
        if (d3 < 2.5d) {
            f2 = a((f2 - 1.0f) / 1.5f, 1.0f, 4.1f);
        } else if (f2 < 4.0f) {
            f2 = a((f2 - 2.5f) / 1.5f, 4.1f, 5.6f);
        } else if (d3 < 5.5d) {
            f2 = a((f2 - 4.0f) / 1.5f, 5.6f, 6.8f);
        } else if (d3 <= 7.0d) {
            f2 = a((f2 - 5.5f) / 1.5f, 6.8f, 7.0f);
        }
        return f2 / 10.0f;
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void e(int i2) {
        float f2 = i2;
        this.f18866z = f2;
        a aVar = this.f18860t;
        if (aVar != null) {
            aVar.c(f2);
        }
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void c(int i2) {
        float f2 = i2;
        this.f18864x = f2;
        a aVar = this.f18860t;
        if (aVar != null) {
            aVar.a(f2);
        }
    }
}
