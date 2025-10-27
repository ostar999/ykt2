package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.beauty.NativeLoad;
import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
public class f extends com.tencent.liteav.basic.opengl.j {
    private int[] A;
    private float B = 4.0f;

    /* renamed from: r, reason: collision with root package name */
    int f18895r;

    /* renamed from: s, reason: collision with root package name */
    int f18896s;

    /* renamed from: t, reason: collision with root package name */
    boolean f18897t;

    /* renamed from: u, reason: collision with root package name */
    private d f18898u;

    /* renamed from: v, reason: collision with root package name */
    private com.tencent.liteav.basic.opengl.j f18899v;

    /* renamed from: w, reason: collision with root package name */
    private c f18900w;

    /* renamed from: x, reason: collision with root package name */
    private a f18901x;

    /* renamed from: y, reason: collision with root package name */
    private b f18902y;

    /* renamed from: z, reason: collision with root package name */
    private int[] f18903z;

    public static class a extends t {
        public a(String str) {
            super(str);
        }

        @Override // com.tencent.liteav.beauty.b.t, com.tencent.liteav.basic.opengl.j
        public boolean b() {
            return super.b();
        }
    }

    public static class b extends s {
        public b(String str) {
            super(str);
        }

        @Override // com.tencent.liteav.beauty.b.s, com.tencent.liteav.basic.opengl.j
        public boolean b() {
            return super.b();
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        if (this.f18597f == i3 && this.f18596e == i2) {
            return;
        }
        super.a(i2, i3);
        if (!this.f18897t) {
            if (i2 < i3) {
                if (i2 < 540) {
                    this.B = 1.0f;
                } else {
                    this.B = 4.0f;
                }
            } else if (i3 < 540) {
                this.B = 1.0f;
            } else {
                this.B = 4.0f;
            }
        }
        float f2 = this.B;
        int i4 = (int) (i2 / f2);
        this.f18895r = i4;
        int i5 = (int) (i3 / f2);
        this.f18896s = i5;
        this.f18899v.a(i4, i5);
        this.f18900w.a(this.f18895r, this.f18896s);
        this.f18901x.a(this.f18895r, this.f18896s);
        this.f18902y.a(i2, i3);
        this.f18898u.a(this.f18895r, this.f18896s);
        int[] iArr = this.f18903z;
        if (iArr != null) {
            GLES20.glDeleteFramebuffers(iArr.length, iArr, 0);
            GLES20.glDeleteTextures(this.f18903z.length, this.A, 0);
            this.f18903z = null;
            this.A = null;
        }
        int[] iArr2 = new int[8];
        this.f18903z = iArr2;
        this.A = new int[iArr2.length];
        GLES20.glGenFramebuffers(iArr2.length, iArr2, 0);
        GLES20.glGenTextures(this.f18903z.length, this.A, 0);
        for (int i6 = 0; i6 < this.f18903z.length; i6++) {
            GLES20.glBindTexture(R2.attr.tab_indicator_height, this.A[i6]);
            if (i6 >= 5) {
                GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, R2.dimen.dm_200, i2, i3, 0, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, null);
            } else {
                GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, R2.dimen.dm_200, this.f18895r, this.f18896s, 0, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, null);
            }
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, 10240, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071.0f);
            GLES20.glBindFramebuffer(36160, this.f18903z[i6]);
            GLES20.glFramebufferTexture2D(36160, 36064, R2.attr.tab_indicator_height, this.A[i6], 0);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
            GLES20.glBindFramebuffer(36160, 0);
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        boolean zB = super.b();
        if (zB) {
            d dVar = new d();
            this.f18898u = dVar;
            if (zB) {
                zB = dVar.a();
            }
            c cVar = new c();
            this.f18900w = cVar;
            if (zB) {
                zB = cVar.a();
            }
            a aVar = new a("precision highp float;  \nuniform sampler2D inputImageTexture;  \nuniform sampler2D inputImageTexture2;  \nvarying vec2 textureCoordinate;  \nvarying vec2 textureCoordinate2;  \nvoid main()  \n{  \n\tgl_FragColor = texture2D(inputImageTexture2, textureCoordinate2) - texture2D(inputImageTexture, textureCoordinate) * texture2D(inputImageTexture2, textureCoordinate2);  \n}  \n");
            this.f18901x = aVar;
            if (zB) {
                zB = aVar.a();
            }
            b bVar = new b("precision highp float;   \nuniform sampler2D inputImageTexture;   \nuniform sampler2D inputImageTexture2;  \nuniform sampler2D inputImageTexture3;   \nvarying vec2 textureCoordinate;   \nvarying vec2 textureCoordinate2;  \nvarying vec2 textureCoordinate3;    \nvoid main()   \n{   \n\tgl_FragColor = texture2D(inputImageTexture, textureCoordinate) * texture2D(inputImageTexture3, textureCoordinate3) + texture2D(inputImageTexture2, textureCoordinate2);   \n}   \n");
            this.f18902y = bVar;
            if (zB) {
                zB = bVar.a();
            }
            com.tencent.liteav.basic.opengl.j jVar = new com.tencent.liteav.basic.opengl.j();
            this.f18899v = jVar;
            jVar.a(true);
            if (zB) {
                zB = this.f18899v.a();
            }
            if (zB) {
                return true;
            }
        }
        d();
        return false;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void e() {
        if (this.f18598g) {
            super.e();
            this.f18898u.d();
            this.f18900w.d();
            this.f18901x.d();
            this.f18902y.d();
            this.f18899v.d();
            int[] iArr = this.f18903z;
            if (iArr != null) {
                GLES20.glDeleteFramebuffers(iArr.length, iArr, 0);
                GLES20.glDeleteTextures(this.f18903z.length, this.A, 0);
                this.f18903z = null;
            }
            this.A = null;
        }
    }

    public static class c extends t {

        /* renamed from: r, reason: collision with root package name */
        int f18904r;

        /* renamed from: s, reason: collision with root package name */
        int f18905s;

        /* renamed from: t, reason: collision with root package name */
        float f18906t;

        public c() {
            super(null, null);
            this.f18906t = 1.5f;
        }

        @Override // com.tencent.liteav.basic.opengl.j
        public boolean a() {
            int iNativeLoadGLProgram = NativeLoad.nativeLoadGLProgram(2);
            this.f18592a = iNativeLoadGLProgram;
            if (iNativeLoadGLProgram == 0 || !b()) {
                this.f18598g = false;
            } else {
                this.f18598g = true;
            }
            c();
            return this.f18598g;
        }

        @Override // com.tencent.liteav.beauty.b.t, com.tencent.liteav.basic.opengl.j
        public boolean b() {
            if (!super.b()) {
                return false;
            }
            this.f18904r = GLES20.glGetUniformLocation(this.f18592a, "texelWidthOffset");
            this.f18905s = GLES20.glGetUniformLocation(this.f18592a, "texelHeightOffset");
            return true;
        }

        public void a(float f2) {
            this.f18906t = f2;
            a(this.f18904r, f2 / this.f18596e);
            a(this.f18905s, this.f18906t / this.f18597f);
        }

        @Override // com.tencent.liteav.basic.opengl.j
        public void a(int i2, int i3) {
            super.a(i2, i3);
            a(this.f18906t);
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public int b(int i2) {
        int iB;
        if (this.B != 1.0f) {
            GLES20.glViewport(0, 0, this.f18895r, this.f18896s);
            iB = this.f18899v.b(i2);
        } else {
            iB = i2;
        }
        int iA = this.f18898u.a(iB, this.f18903z[4], this.A[4]);
        int iA2 = this.f18900w.a(iB, iA, this.f18903z[0], this.A[0]);
        int iA3 = this.f18901x.a(iA2, iA, this.f18903z[1], this.A[1]);
        int iA4 = this.f18898u.a(iA2, this.f18903z[2], this.A[2]);
        int iA5 = this.f18898u.a(iA3, this.f18903z[3], this.A[3]);
        if (this.B != 1.0f) {
            GLES20.glViewport(0, 0, this.f18596e, this.f18597f);
            iA4 = this.f18899v.a(iA4, this.f18903z[5], this.A[5]);
            iA5 = this.f18899v.a(iA5, this.f18903z[6], this.A[6]);
        }
        return this.f18902y.a(iA4, iA5, i2, this.f18903z[7], this.A[7]);
    }
}
