package com.alipay.sdk.app;

/* loaded from: classes2.dex */
final class g implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f3091a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ boolean f3092b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ H5PayCallback f3093c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ PayTask f3094d;

    public g(PayTask payTask, String str, boolean z2, H5PayCallback h5PayCallback) {
        this.f3094d = payTask;
        this.f3091a = str;
        this.f3092b = z2;
        this.f3093c = h5PayCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f3093c.onPayResult(this.f3094d.h5Pay(this.f3091a, this.f3092b));
    }
}
