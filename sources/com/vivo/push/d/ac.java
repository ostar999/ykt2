package com.vivo.push.d;

import java.util.List;

/* loaded from: classes6.dex */
final class ac implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f24310a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ List f24311b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ List f24312c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f24313d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ aa f24314e;

    public ac(aa aaVar, int i2, List list, List list2, String str) {
        this.f24314e = aaVar;
        this.f24310a = i2;
        this.f24311b = list;
        this.f24312c = list2;
        this.f24313d = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        aa aaVar = this.f24314e;
        ((z) aaVar).f24347b.onSetAlias(((com.vivo.push.l) aaVar).f24388a, this.f24310a, this.f24311b, this.f24312c, this.f24313d);
    }
}
