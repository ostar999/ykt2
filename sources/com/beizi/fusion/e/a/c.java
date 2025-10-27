package com.beizi.fusion.e.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import com.beizi.fusion.e.a.b;
import com.beizi.fusion.e.b.b;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public final LinkedBlockingQueue<IBinder> f5007a = new LinkedBlockingQueue<>(1);

    /* renamed from: b, reason: collision with root package name */
    ServiceConnection f5008b = new ServiceConnection() { // from class: com.beizi.fusion.e.a.c.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws InterruptedException {
            try {
                c.this.f5007a.put(iBinder);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    /* renamed from: c, reason: collision with root package name */
    private Context f5009c;

    public c(Context context) {
        this.f5009c = context;
    }

    public void a(b.a aVar) throws PackageManager.NameNotFoundException {
        try {
            this.f5009c.getPackageManager().getPackageInfo("com.huawei.hwid", 0);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Intent intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
        intent.setPackage("com.huawei.hwid");
        try {
            if (this.f5009c.bindService(intent, this.f5008b, 1)) {
                try {
                    b.a aVar2 = new b.a(this.f5007a.take());
                    String strA = aVar2.a();
                    aVar2.b();
                    if (aVar != null) {
                        aVar.a(strA);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        } finally {
            this.f5009c.unbindService(this.f5008b);
        }
    }
}
