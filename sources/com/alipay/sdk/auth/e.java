package com.alipay.sdk.auth;

import android.webkit.SslErrorHandler;
import com.alipay.sdk.auth.AuthActivity;

/* loaded from: classes2.dex */
final class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ SslErrorHandler f3157a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ AuthActivity.b f3158b;

    public e(AuthActivity.b bVar, SslErrorHandler sslErrorHandler) {
        this.f3158b = bVar;
        this.f3157a = sslErrorHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.alipay.sdk.widget.e.a(AuthActivity.this, "安全警告", "由于您的设备缺少根证书，将无法校验该访问站点的安全性，可能存在风险，请选择是否继续？", "继续", new f(this), "退出", new g(this));
    }
}
