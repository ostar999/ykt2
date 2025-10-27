package com.alipay.sdk.auth;

/* loaded from: classes2.dex */
final class d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AuthActivity f3156a;

    public d(AuthActivity authActivity) {
        this.f3156a = authActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AuthActivity.g(this.f3156a);
    }
}
