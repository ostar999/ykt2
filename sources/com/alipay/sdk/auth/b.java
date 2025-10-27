package com.alipay.sdk.auth;

import org.json.JSONException;

/* loaded from: classes2.dex */
final class b implements com.alipay.sdk.authjs.c {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AuthActivity f3153a;

    public b(AuthActivity authActivity) {
        this.f3153a = authActivity;
    }

    @Override // com.alipay.sdk.authjs.c
    public final void a(com.alipay.sdk.authjs.a aVar) throws JSONException {
        AuthActivity.a(this.f3153a, aVar);
    }
}
