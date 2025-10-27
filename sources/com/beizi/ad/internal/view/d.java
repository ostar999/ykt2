package com.beizi.ad.internal.view;

import android.view.View;

/* loaded from: classes2.dex */
public class d implements e {

    /* renamed from: a, reason: collision with root package name */
    private long f4654a;

    /* renamed from: b, reason: collision with root package name */
    private c f4655b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f4656c;

    /* renamed from: d, reason: collision with root package name */
    private com.beizi.ad.internal.b.b f4657d;

    public d(c cVar, Long l2, boolean z2, com.beizi.ad.internal.b.b bVar) {
        this.f4654a = l2.longValue();
        this.f4655b = cVar;
        this.f4656c = z2;
        this.f4657d = bVar;
    }

    @Override // com.beizi.ad.internal.view.e
    public long a() {
        return this.f4654a;
    }

    @Override // com.beizi.ad.internal.view.e
    public boolean b() {
        return this.f4656c;
    }

    @Override // com.beizi.ad.internal.view.e
    public com.beizi.ad.internal.b.b c() {
        return this.f4657d;
    }

    @Override // com.beizi.ad.internal.view.e
    public View d() {
        c cVar = this.f4655b;
        if (cVar == null) {
            return null;
        }
        return cVar.getView();
    }
}
