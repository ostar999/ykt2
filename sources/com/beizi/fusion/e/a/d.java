package com.beizi.fusion.e.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.beizi.fusion.e.a.b;
import com.beizi.fusion.e.b.c;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    com.beizi.fusion.e.b.c f5011a;

    /* renamed from: b, reason: collision with root package name */
    ServiceConnection f5012b = new ServiceConnection() { // from class: com.beizi.fusion.e.a.d.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            d.this.f5011a = new c.a.C0064a(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    /* renamed from: c, reason: collision with root package name */
    private Context f5013c;

    public d(Context context) {
        this.f5013c = context;
    }

    public void a(b.a aVar) {
        com.beizi.fusion.e.b.c cVar;
        String packageName = this.f5013c.getPackageName();
        Intent intent = new Intent();
        intent.setClassName("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService");
        if (!this.f5013c.bindService(intent, this.f5012b, 1) || (cVar = this.f5011a) == null) {
            return;
        }
        String strA = cVar.a();
        this.f5011a.b();
        this.f5011a.b(packageName);
        this.f5011a.b(packageName);
        if (aVar != null) {
            aVar.a(strA);
        }
    }
}
