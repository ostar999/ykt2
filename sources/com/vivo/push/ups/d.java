package com.vivo.push.ups;

import com.vivo.push.IPushActionListener;

/* loaded from: classes6.dex */
final class d implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UPSTurnCallback f24429a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ VUpsManager f24430b;

    public d(VUpsManager vUpsManager, UPSTurnCallback uPSTurnCallback) {
        this.f24430b = vUpsManager;
        this.f24429a = uPSTurnCallback;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        this.f24429a.onResult(new CodeResult(i2));
    }
}
