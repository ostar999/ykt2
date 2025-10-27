package com.alipay.sdk.auth;

import android.content.DialogInterface;

/* loaded from: classes2.dex */
final class g implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ e f3160a;

    public g(e eVar) {
        this.f3160a = eVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i2) {
        this.f3160a.f3157a.cancel();
        AuthActivity.this.f3147g = false;
        h.a(AuthActivity.this, AuthActivity.this.f3144d + "?resultCode=150");
        AuthActivity.this.finish();
    }
}
