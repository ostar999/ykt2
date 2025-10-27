package com.alipay.sdk.auth;

import com.github.lzyzsd.jsbridge.BridgeUtil;

/* loaded from: classes2.dex */
final class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f3154a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ AuthActivity f3155b;

    public c(AuthActivity authActivity, String str) {
        this.f3155b = authActivity;
        this.f3154a = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.f3155b.f3143c.loadUrl(BridgeUtil.JAVASCRIPT_STR + this.f3154a);
        } catch (Exception unused) {
        }
    }
}
