package com.vivo.push.ups;

import com.vivo.push.IPushActionListener;

/* loaded from: classes6.dex */
final class c implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UPSTurnCallback f24427a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ VUpsManager f24428b;

    public c(VUpsManager vUpsManager, UPSTurnCallback uPSTurnCallback) {
        this.f24428b = vUpsManager;
        this.f24427a = uPSTurnCallback;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        this.f24427a.onResult(new CodeResult(i2));
    }
}
