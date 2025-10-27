package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import java.util.Iterator;

/* loaded from: classes6.dex */
class bc implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ az f24544a;

    public bc(az azVar) {
        this.f24544a = azVar;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.f24544a) {
            this.f24544a.f138a = new Messenger(iBinder);
            this.f24544a.f24539c = false;
            Iterator it = this.f24544a.f141a.iterator();
            while (it.hasNext()) {
                try {
                    this.f24544a.f138a.send((Message) it.next());
                } catch (RemoteException e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                }
            }
            this.f24544a.f141a.clear();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.f24544a.f138a = null;
        this.f24544a.f24539c = false;
    }
}
