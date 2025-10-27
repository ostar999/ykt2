package com.tencent.liteav.beauty.a.a;

import android.view.Surface;

/* loaded from: classes6.dex */
public class c extends b {

    /* renamed from: b, reason: collision with root package name */
    private Surface f18790b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f18791c;

    public c(a aVar, int i2, int i3, boolean z2) {
        super(aVar);
        a(i2, i3);
        this.f18791c = z2;
    }

    public void c() {
        a();
        Surface surface = this.f18790b;
        if (surface != null) {
            if (this.f18791c) {
                surface.release();
            }
            this.f18790b = null;
        }
    }
}
