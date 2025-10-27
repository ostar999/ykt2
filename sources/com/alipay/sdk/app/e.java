package com.alipay.sdk.app;

import android.content.DialogInterface;

/* loaded from: classes2.dex */
final class e implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ c f3089a;

    public e(c cVar) {
        this.f3089a = cVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i2) {
        this.f3089a.f3086a.cancel();
        this.f3089a.f3087b.f3083d = false;
        i.f3096a = i.a();
        this.f3089a.f3087b.f3080a.finish();
    }
}
