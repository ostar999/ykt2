package com.huawei.hms.push;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Messenger;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes4.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    public ServiceConnection f7998a;

    /* renamed from: b, reason: collision with root package name */
    public Messenger f7999b = null;

    public boolean a(Context context, Bundle bundle, Intent intent) {
        Context applicationContext = context.getApplicationContext();
        this.f7998a = new g(this, bundle, applicationContext);
        HMSLog.i("RemoteService", "remote service bind service start");
        return applicationContext.bindService(intent, this.f7998a, 1);
    }
}
