package com.alipay.sdk.app;

import android.content.DialogInterface;

/* loaded from: classes2.dex */
final class d implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ c f3088a;

    public d(c cVar) {
        this.f3088a = cVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i2) {
        this.f3088a.f3087b.f3083d = true;
        this.f3088a.f3086a.proceed();
        dialogInterface.dismiss();
    }
}
