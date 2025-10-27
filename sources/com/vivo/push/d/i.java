package com.vivo.push.d;

import java.util.List;

/* loaded from: classes6.dex */
final class i implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f24320a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ List f24321b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ List f24322c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f24323d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ h f24324e;

    public i(h hVar, int i2, List list, List list2, String str) {
        this.f24324e = hVar;
        this.f24320a = i2;
        this.f24321b = list;
        this.f24322c = list2;
        this.f24323d = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        h hVar = this.f24324e;
        ((z) hVar).f24347b.onDelTags(((com.vivo.push.l) hVar).f24388a, this.f24320a, this.f24321b, this.f24322c, this.f24323d);
    }
}
