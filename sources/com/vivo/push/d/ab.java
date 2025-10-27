package com.vivo.push.d;

import java.util.List;

/* loaded from: classes6.dex */
final class ab implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f24305a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ List f24306b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ List f24307c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f24308d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ aa f24309e;

    public ab(aa aaVar, int i2, List list, List list2, String str) {
        this.f24309e = aaVar;
        this.f24305a = i2;
        this.f24306b = list;
        this.f24307c = list2;
        this.f24308d = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        aa aaVar = this.f24309e;
        ((z) aaVar).f24347b.onSetTags(((com.vivo.push.l) aaVar).f24388a, this.f24305a, this.f24306b, this.f24307c, this.f24308d);
    }
}
