package com.tencent.liteav.beauty.b.a;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.b.h;
import com.tencent.liteav.beauty.b.q;

/* loaded from: classes6.dex */
public class a extends com.tencent.liteav.beauty.b.b {

    /* renamed from: r, reason: collision with root package name */
    private c f18824r = null;

    /* renamed from: s, reason: collision with root package name */
    private d f18825s = null;

    /* renamed from: t, reason: collision with root package name */
    private e f18826t = null;

    /* renamed from: u, reason: collision with root package name */
    private h f18827u = null;

    /* renamed from: v, reason: collision with root package name */
    private q f18828v = null;

    /* renamed from: w, reason: collision with root package name */
    private b f18829w = null;

    /* renamed from: x, reason: collision with root package name */
    private String f18830x = "TXCBeauty2Filter";

    /* renamed from: y, reason: collision with root package name */
    private int f18831y = 0;

    /* renamed from: z, reason: collision with root package name */
    private int f18832z = 0;
    private int A = 0;
    private float B = 1.0f;
    private final float C = 0.7f;
    private float D = 0.8f;
    private float E = 2.0f;
    private int F = 0;
    private int G = 0;
    private int H = 0;
    private int I = 0;

    private void g(int i2) {
        float f2 = 1.0f - (i2 / 50.0f);
        this.B = f2;
        h hVar = this.f18827u;
        if (hVar != null) {
            hVar.a(f2);
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        if (this.f18596e == i2 && this.f18597f == i3) {
            return;
        }
        this.f18596e = i2;
        this.f18597f = i3;
        d(i2, i3);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public int b(int i2) {
        if (1.0f != this.E) {
            GLES20.glViewport(0, 0, this.H, this.I);
        }
        int iC = this.f18826t.c(this.f18825s.b(i2), i2);
        if (1.0f != this.E) {
            GLES20.glViewport(0, 0, this.F, this.G);
        }
        if (this.D > 0.7f) {
            iC = this.f18828v.b(iC);
        }
        return this.f18829w.c(iC, i2);
    }

    @Override // com.tencent.liteav.beauty.b.b
    public boolean c(int i2, int i3) {
        return d(i2, i3);
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void d(int i2) {
        b bVar = this.f18829w;
        if (bVar != null) {
            bVar.a(i2 / 10.0f);
        }
        this.f18832z = i2;
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void e(int i2) {
        b bVar = this.f18829w;
        if (bVar != null) {
            bVar.b(i2 / 10.0f);
        }
        this.A = i2;
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void f(int i2) {
        float f2 = (i2 / 12.0f) + 0.7f;
        if (Math.abs(this.D - f2) < 0.001d) {
            return;
        }
        this.D = f2;
        TXCLog.i(this.f18830x, "set mSharpenLevel " + i2);
        q qVar = this.f18828v;
        if (qVar != null) {
            qVar.a(this.D);
        }
    }

    public void r() {
        b bVar = this.f18829w;
        if (bVar != null) {
            bVar.d();
            this.f18829w = null;
        }
        d dVar = this.f18825s;
        if (dVar != null) {
            dVar.d();
            this.f18825s = null;
        }
        e eVar = this.f18826t;
        if (eVar != null) {
            eVar.d();
            this.f18826t = null;
        }
        h hVar = this.f18827u;
        if (hVar != null) {
            hVar.d();
            this.f18827u = null;
        }
        q qVar = this.f18828v;
        if (qVar != null) {
            qVar.d();
            this.f18828v = null;
        }
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void c(int i2) {
        e eVar = this.f18826t;
        if (eVar != null) {
            eVar.a(i2 / 10.0f);
        }
        this.f18831y = i2;
        g(i2);
    }

    private boolean d(int i2, int i3) {
        this.F = i2;
        this.G = i3;
        this.H = i2;
        this.I = i3;
        float f2 = this.E;
        if (1.0f != f2) {
            this.H = (int) (i2 / f2);
            this.I = (int) (i3 / f2);
        }
        TXCLog.i(this.f18830x, "mResampleRatio " + this.E + " mResampleWidth " + this.H + " mResampleHeight " + this.I);
        if (this.f18829w == null) {
            b bVar = new b();
            this.f18829w = bVar;
            bVar.a(true);
            if (!this.f18829w.a()) {
                TXCLog.e(this.f18830x, "mBeautyBlendFilter init failed!!, break init");
                return false;
            }
        }
        this.f18829w.a(i2, i3);
        if (this.f18825s == null) {
            d dVar = new d();
            this.f18825s = dVar;
            dVar.a(true);
            if (!this.f18825s.a()) {
                TXCLog.e(this.f18830x, "m_horizontalFilter init failed!!, break init");
                return false;
            }
        }
        this.f18825s.a(this.H, this.I);
        if (this.f18826t == null) {
            e eVar = new e();
            this.f18826t = eVar;
            eVar.a(true);
            this.f18826t.b(1.0f != this.E);
            if (!this.f18826t.a()) {
                TXCLog.e(this.f18830x, "m_verticalFilter init failed!!, break init");
                return false;
            }
        }
        this.f18826t.a(this.H, this.I);
        if (this.f18827u == null) {
            h hVar = new h(1.0f);
            this.f18827u = hVar;
            hVar.a(true);
            if (!this.f18827u.a()) {
                TXCLog.e(this.f18830x, "m_gammaFilter init failed!!, break init");
                return false;
            }
        }
        this.f18827u.a(this.H, this.I);
        if (this.f18828v == null) {
            q qVar = new q();
            this.f18828v = qVar;
            qVar.a(true);
            if (!this.f18828v.a()) {
                TXCLog.e(this.f18830x, "mSharpenFilter init failed!!, break init");
                return false;
            }
        }
        this.f18828v.a(i2, i3);
        return true;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void e() {
        super.e();
        r();
    }
}
