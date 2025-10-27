package com.alipay.sdk.util;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.alipay.android.app.IAlixPay;

/* loaded from: classes2.dex */
final class f implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ e f3373a;

    public f(e eVar) {
        this.f3373a = eVar;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.f3373a.f3368d) {
            this.f3373a.f3367c = IAlixPay.Stub.asInterface(iBinder);
            this.f3373a.f3368d.notify();
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.f3373a.f3367c = null;
    }
}
