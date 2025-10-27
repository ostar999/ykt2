package com.tencent.liteav.beauty.b.b;

import android.util.Log;
import com.tencent.liteav.beauty.b.r;

/* loaded from: classes6.dex */
public class a extends com.tencent.liteav.beauty.b.b {

    /* renamed from: r, reason: collision with root package name */
    private b f18847r = null;

    /* renamed from: s, reason: collision with root package name */
    private r f18848s = null;

    /* renamed from: t, reason: collision with root package name */
    private String f18849t = "TXCBeauty3Filter";

    /* renamed from: u, reason: collision with root package name */
    private float f18850u = 0.0f;

    /* renamed from: v, reason: collision with root package name */
    private float f18851v = 0.0f;

    /* renamed from: w, reason: collision with root package name */
    private float f18852w = 0.0f;

    /* renamed from: x, reason: collision with root package name */
    private float f18853x = 0.0f;

    private boolean d(int i2, int i3) {
        if (this.f18847r == null) {
            b bVar = new b();
            this.f18847r = bVar;
            bVar.a(true);
            if (!this.f18847r.a()) {
                Log.e(this.f18849t, "m_verticalFilter init failed!!, break init");
                return false;
            }
        }
        this.f18847r.a(i2, i3);
        if (this.f18848s == null) {
            r rVar = new r();
            this.f18848s = rVar;
            rVar.a(true);
            if (!this.f18848s.a()) {
                Log.e(this.f18849t, "mSharpnessFilter init failed!!, break init");
                return false;
            }
        }
        this.f18848s.a(i2, i3);
        return true;
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
        if (this.f18850u > 0.0f || this.f18851v > 0.0f || this.f18852w > 0.0f) {
            i2 = this.f18847r.b(i2);
        }
        return this.f18853x > 0.0f ? this.f18848s.b(i2) : i2;
    }

    @Override // com.tencent.liteav.beauty.b.b
    public boolean c(int i2, int i3) {
        return d(i2, i3);
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void e(int i2) {
        float f2 = i2 / 10.0f;
        this.f18852w = f2;
        b bVar = this.f18847r;
        if (bVar != null) {
            bVar.c(f2);
        }
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void f(int i2) {
        float f2 = i2 / 20.0f;
        if (Math.abs(this.f18853x - f2) < 0.001d) {
            return;
        }
        this.f18853x = f2;
        r rVar = this.f18848s;
        if (rVar != null) {
            rVar.a(f2);
        }
    }

    public void r() {
        b bVar = this.f18847r;
        if (bVar != null) {
            bVar.e();
            this.f18847r = null;
        }
        r rVar = this.f18848s;
        if (rVar != null) {
            rVar.e();
            this.f18848s = null;
        }
    }

    @Override // com.tencent.liteav.beauty.b.b
    public void c(int i2) {
        float f2 = i2 / 10.0f;
        this.f18850u = f2;
        b bVar = this.f18847r;
        if (bVar != null) {
            bVar.a(f2);
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
        this.f18851v = f2;
        b bVar = this.f18847r;
        if (bVar != null) {
            bVar.b(f2);
        }
    }
}
