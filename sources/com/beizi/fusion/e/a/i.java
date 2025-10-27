package com.beizi.fusion.e.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import com.beizi.fusion.e.a.b;
import com.beizi.fusion.e.b.f;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes2.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    public final LinkedBlockingQueue<IBinder> f5029a = new LinkedBlockingQueue<>(1);

    /* renamed from: b, reason: collision with root package name */
    ServiceConnection f5030b = new ServiceConnection() { // from class: com.beizi.fusion.e.a.i.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws InterruptedException {
            try {
                i.this.f5029a.put(iBinder);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    /* renamed from: c, reason: collision with root package name */
    private Context f5031c;

    public i(Context context) {
        this.f5031c = context;
    }

    public void a(b.a aVar) throws PackageManager.NameNotFoundException {
        try {
            this.f5031c.getPackageManager().getPackageInfo("com.samsung.android.deviceidservice", 0);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setClassName("com.samsung.android.deviceidservice", "com.samsung.android.deviceidservice.DeviceIdService");
        if (this.f5031c.bindService(intent, this.f5030b, 1)) {
            try {
                String strA = new f.a(this.f5029a.take()).a();
                if (aVar != null) {
                    aVar.a(strA);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }
}
