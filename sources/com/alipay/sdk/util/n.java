package com.alipay.sdk.util;

import android.app.Activity;

/* loaded from: classes2.dex */
final class n implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Activity f3396a;

    public n(Activity activity) {
        this.f3396a = activity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f3396a.finish();
    }
}
