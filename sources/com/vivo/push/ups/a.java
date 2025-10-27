package com.vivo.push.ups;

import android.content.Context;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;

/* loaded from: classes6.dex */
final class a implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UPSRegisterCallback f24422a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Context f24423b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ VUpsManager f24424c;

    public a(VUpsManager vUpsManager, UPSRegisterCallback uPSRegisterCallback, Context context) {
        this.f24424c = vUpsManager;
        this.f24422a = uPSRegisterCallback;
        this.f24423b = context;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        this.f24422a.onResult(new TokenResult(i2, PushClient.getInstance(this.f24423b).getRegId()));
    }
}
