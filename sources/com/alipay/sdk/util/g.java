package com.alipay.sdk.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import com.alipay.android.app.IRemoteServiceCallback;

/* loaded from: classes2.dex */
final class g extends IRemoteServiceCallback.Stub {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ e f3374a;

    public g(e eVar) {
        this.f3374a = eVar;
    }

    @Override // com.alipay.android.app.IRemoteServiceCallback
    public final boolean isHideLoadingScreen() throws RemoteException {
        return false;
    }

    @Override // com.alipay.android.app.IRemoteServiceCallback
    public final void payEnd(boolean z2, String str) throws RemoteException {
    }

    @Override // com.alipay.android.app.IRemoteServiceCallback
    public final void startActivity(String str, String str2, int i2, Bundle bundle) throws RemoteException {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        if (bundle == null) {
            bundle = new Bundle();
        }
        try {
            bundle.putInt("CallingPid", i2);
            intent.putExtras(bundle);
        } catch (Exception unused) {
        }
        intent.setClassName(str, str2);
        if (this.f3374a.f3366a != null) {
            this.f3374a.f3366a.startActivity(intent);
        }
        this.f3374a.f3370f.b();
    }
}
