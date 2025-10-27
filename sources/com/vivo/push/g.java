package com.vivo.push;

import com.vivo.push.e;

/* loaded from: classes6.dex */
final class g implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ e.a f24377a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ e f24378b;

    public g(e eVar, e.a aVar) {
        this.f24378b = eVar;
        this.f24377a = aVar;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        if (i2 != 0) {
            this.f24378b.f24358k = null;
            this.f24378b.f24357j.b("APP_TOKEN");
            return;
        }
        Object[] objArrB = this.f24377a.b();
        if (objArrB == null || objArrB.length == 0) {
            com.vivo.push.util.p.a("PushClientManager", "bind app result is null");
        } else {
            this.f24378b.a((String) this.f24377a.b()[0]);
        }
    }
}
