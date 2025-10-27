package com.vivo.push.ups;

import com.vivo.push.IPushActionListener;

/* loaded from: classes6.dex */
final class b implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UPSRegisterCallback f24425a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ VUpsManager f24426b;

    public b(VUpsManager vUpsManager, UPSRegisterCallback uPSRegisterCallback) {
        this.f24426b = vUpsManager;
        this.f24425a = uPSRegisterCallback;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        this.f24425a.onResult(new TokenResult(i2, ""));
    }
}
