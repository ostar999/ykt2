package com.tencent.liteav.beauty.b.c;

import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes6.dex */
public class a extends com.tencent.liteav.beauty.b.b {

    /* renamed from: r, reason: collision with root package name */
    private d f18867r = null;

    /* renamed from: s, reason: collision with root package name */
    private d f18868s = null;

    /* renamed from: t, reason: collision with root package name */
    private b f18869t = null;

    /* renamed from: u, reason: collision with root package name */
    private c f18870u = null;

    /* renamed from: v, reason: collision with root package name */
    private d f18871v = null;

    /* renamed from: w, reason: collision with root package name */
    private d f18872w = null;

    /* renamed from: x, reason: collision with root package name */
    private float f18873x = 0.2f;

    /* renamed from: y, reason: collision with root package name */
    private float f18874y = 0.2f;

    /* renamed from: z, reason: collision with root package name */
    private float f18875z = 0.2f;

    private boolean d(int i2, int i3) {
        if (this.f18867r == null) {
            d dVar = new d(true);
            this.f18867r = dVar;
            dVar.a(true);
            if (!this.f18867r.a()) {
                TXCLog.e("TXCBeauty4Filter", "mSkinBlurFilterVertical init failed!!, break init");
                return false;
            }
        }
        if (this.f18868s == null) {
            d dVar2 = new d(false);
            this.f18868s = dVar2;
            dVar2.a(true);
            if (!this.f18868s.a()) {
                TXCLog.e("TXCBeauty4Filter", "mSkinBlurFilterHorizontal init failed!!, break init");
                return false;
            }
        }
        if (this.f18869t == null) {
            b bVar = new b();
            this.f18869t = bVar;
            bVar.a(true);
            if (!this.f18869t.a()) {
                TXCLog.e("TXCBeauty4Filter", "mBorderFilter init failed!!, break init");
                return false;
            }
        }
        if (this.f18871v == null) {
            d dVar3 = new d(true);
            this.f18871v = dVar3;
            dVar3.a(true);
            if (!this.f18871v.a()) {
                TXCLog.e("TXCBeauty4Filter", "mBorderBlurFilterVertical init failed!!, break init");
                return false;
            }
        }
        if (this.f18872w == null) {
            d dVar4 = new d(false);
            this.f18872w = dVar4;
            dVar4.a(true);
            if (!this.f18872w.a()) {
                TXCLog.e("TXCBeauty4Filter", "mBorderBlurFilterHorizontal init failed!!, break init");
                return false;
            }
        }
        if (this.f18870u == null) {
            c cVar = new c();
            this.f18870u = cVar;
            cVar.a(true);
            if (!this.f18870u.a()) {
                TXCLog.e("TXCBeauty4Filter", "mSmoothFilter init failed!!, break init");
                return false;
            }
        }
        this.f18870u.a(360.0f, 640.0f);
        this.f18870u.a(this.f18873x);
        this.f18870u.b(this.f18874y);
        this.f18870u.c(this.f18875z);
        a(i2, i3);
        return true;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        super.a(i2, i3);
        this.f18596e = i2;
        this.f18597f = i3;
        this.f18867r.a(i2, i3);
        this.f18868s.a(i2, i3);
        this.f18869t.a(i2, i3);
        this.f18871v.a(i2, i3);
        this.f18872w.a(i2, i3);
        this.f18870u.a(i2, i3);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public int b(int i2) {
        if (this.f18873x <= 0.0f && this.f18874y <= 0.0f && this.f18875z <= 0.0f) {
            return i2;
        }
        int iB = this.f18868s.b(this.f18867r.b(i2));
        return this.f18870u.a(i2, iB, this.f18872w.b(this.f18871v.b(this.f18869t.c(i2, iB))));
    }

    @Override // com.tencent.liteav.beauty.b.b
    public boolean c(int i2, int i3) {
        this.f18596e = i2;
        this.f18597f = i3;
        return d(i2, i3);
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void e(int i2) {
        float f2 = i2 / 10.0f;
        this.f18875z = f2;
        c cVar = this.f18870u;
        if (cVar != null) {
            cVar.c(f2);
        }
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void f(int i2) {
        this.f18870u.d(i2 / 10.0f);
    }

    public void r() {
        d dVar = this.f18867r;
        if (dVar != null) {
            dVar.e();
            this.f18867r = null;
        }
        d dVar2 = this.f18868s;
        if (dVar2 != null) {
            dVar2.e();
            this.f18868s = null;
        }
        b bVar = this.f18869t;
        if (bVar != null) {
            bVar.e();
            this.f18869t = null;
        }
        c cVar = this.f18870u;
        if (cVar != null) {
            cVar.e();
            this.f18870u = null;
        }
        d dVar3 = this.f18871v;
        if (dVar3 != null) {
            dVar3.e();
            this.f18871v = null;
        }
        d dVar4 = this.f18872w;
        if (dVar4 != null) {
            dVar4.e();
            this.f18872w = null;
        }
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void c(int i2) {
        float f2 = i2 / 10.0f;
        this.f18873x = f2;
        c cVar = this.f18870u;
        if (cVar != null) {
            cVar.a(f2);
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void e() {
        super.e();
        r();
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void d(int i2) {
        float f2 = i2 / 10.0f;
        this.f18874y = f2;
        c cVar = this.f18870u;
        if (cVar != null) {
            cVar.b(f2);
        }
    }
}
