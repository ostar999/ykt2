package com.vivo.push.util;

import java.util.List;

/* loaded from: classes6.dex */
final class l implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ List f24463a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ k f24464b;

    public l(k kVar, List list) {
        this.f24464b = kVar;
        this.f24463a = list;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.f24464b.f24458b != null) {
            w.b().a("com.vivo.push.notify_key", this.f24464b.f24459c);
            NotifyAdapterUtil.pushNotification(this.f24464b.f24457a, this.f24463a, this.f24464b.f24458b, this.f24464b.f24459c, this.f24464b.f24461e, this.f24464b.f24462f);
        }
    }
}
