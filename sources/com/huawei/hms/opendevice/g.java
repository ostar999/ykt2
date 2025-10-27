package com.huawei.hms.opendevice;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes4.dex */
public class g implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Bundle f7908a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Context f7909b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ h f7910c;

    public g(h hVar, Bundle bundle, Context context) {
        this.f7910c = hVar;
        this.f7908a = bundle;
        this.f7909b = context;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
        HMSLog.i("RemoteService", "remote service onConnected");
        this.f7910c.f7912b = new Messenger(iBinder);
        Message messageObtain = Message.obtain();
        messageObtain.setData(this.f7908a);
        try {
            this.f7910c.f7912b.send(messageObtain);
        } catch (RemoteException unused) {
            HMSLog.i("RemoteService", "remote service message send failed");
        }
        HMSLog.i("RemoteService", "remote service unbindservice");
        this.f7909b.unbindService(this.f7910c.f7911a);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        HMSLog.i("RemoteService", "remote service onDisconnected");
        this.f7910c.f7912b = null;
    }
}
