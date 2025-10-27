package com.alipay.sdk.auth;

import android.content.DialogInterface;

/* loaded from: classes2.dex */
final class f implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ e f3159a;

    public f(e eVar) {
        this.f3159a = eVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i2) {
        AuthActivity.this.f3147g = true;
        this.f3159a.f3157a.proceed();
        dialogInterface.dismiss();
    }
}
