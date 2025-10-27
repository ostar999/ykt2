package com.vivo.push.d;

import android.text.TextUtils;

/* loaded from: classes6.dex */
final class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f24317a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.i f24318b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ d f24319c;

    public e(d dVar, String str, com.vivo.push.b.i iVar) {
        this.f24319c = dVar;
        this.f24317a = str;
        this.f24318b = iVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (!TextUtils.isEmpty(this.f24317a)) {
            d dVar = this.f24319c;
            ((z) dVar).f24347b.onReceiveRegId(((com.vivo.push.l) dVar).f24388a, this.f24317a);
        }
        d dVar2 = this.f24319c;
        ((z) dVar2).f24347b.onBind(((com.vivo.push.l) dVar2).f24388a, this.f24318b.h(), this.f24318b.d());
    }
}
