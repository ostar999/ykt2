package com.alipay.sdk.app;

/* loaded from: classes2.dex */
public class H5AuthActivity extends H5PayActivity {
    @Override // com.alipay.sdk.app.H5PayActivity
    public final void a() {
        Object obj = AuthTask.f3061a;
        synchronized (obj) {
            try {
                obj.notify();
            } catch (Exception unused) {
            }
        }
    }
}
