package com.alipay.sdk.app;

import android.webkit.SslErrorHandler;

/* loaded from: classes2.dex */
final class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ SslErrorHandler f3086a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ b f3087b;

    public c(b bVar, SslErrorHandler sslErrorHandler) {
        this.f3087b = bVar;
        this.f3086a = sslErrorHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.alipay.sdk.widget.e.a(this.f3087b.f3080a, "安全警告", "安全连接证书校验无效，将无法保证访问数据的安全性，可能存在风险，请选择是否继续？", "继续", new d(this), "退出", new e(this));
    }
}
