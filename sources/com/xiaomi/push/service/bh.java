package com.xiaomi.push.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import java.util.Iterator;

/* loaded from: classes6.dex */
class bh implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ bf f25653a;

    public bh(bf bfVar) {
        this.f25653a = bfVar;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.f25653a) {
            this.f25653a.f25651b = new Messenger(iBinder);
            this.f25653a.f1035b = false;
            Iterator it = this.f25653a.f1033a.iterator();
            while (it.hasNext()) {
                try {
                    this.f25653a.f25651b.send((Message) it.next());
                } catch (RemoteException e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                }
            }
            this.f25653a.f1033a.clear();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.f25653a.f25651b = null;
        this.f25653a.f1035b = false;
    }
}
