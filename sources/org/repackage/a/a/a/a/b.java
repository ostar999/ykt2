package org.repackage.a.a.a.a;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import org.repackage.a.a.a.a;

/* loaded from: classes9.dex */
public class b implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ c f27987a;

    public b(c cVar) {
        this.f27987a = cVar;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f27987a.f27988a = a.AbstractBinderC0476a.a(iBinder);
        synchronized (this.f27987a.f27991d) {
            this.f27987a.f27991d.notify();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.f27987a.f27988a = null;
    }
}
