package com.xiaomi.push;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.xiaomi.push.cv;

/* loaded from: classes6.dex */
class cu implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ct f24699a;

    public cu(ct ctVar) {
        this.f24699a = ctVar;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.v("GeoFencingServiceWrapper", "*** GeoFencingService connected ***");
        this.f24699a.f279a = cv.a.a(iBinder);
        if (this.f24699a.f278a != null) {
            this.f24699a.f278a.sendEmptyMessage(3);
            this.f24699a.f278a.sendEmptyMessageDelayed(2, 60000L);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        Log.v("GeoFencingServiceWrapper", "*** GeoFencingService disconnected ***");
        this.f24699a.f279a = null;
    }
}
