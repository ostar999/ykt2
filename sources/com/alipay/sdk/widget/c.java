package com.alipay.sdk.widget;

/* loaded from: classes2.dex */
final class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f3411a;

    public c(a aVar) {
        this.f3411a = aVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.f3411a.f3402f == null || !this.f3411a.f3402f.isShowing()) {
            return;
        }
        try {
            this.f3411a.f3408l.removeMessages(1);
            this.f3411a.f3402f.dismiss();
        } catch (Exception unused) {
        }
    }
}
