package com.beizi.fusion.e.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import com.beizi.fusion.e.a.b;
import com.beizi.fusion.e.b.a;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public final LinkedBlockingQueue<IBinder> f5000a = new LinkedBlockingQueue<>(1);

    /* renamed from: b, reason: collision with root package name */
    ServiceConnection f5001b = new ServiceConnection() { // from class: com.beizi.fusion.e.a.a.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws InterruptedException {
            try {
                a.this.f5000a.put(iBinder);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    /* renamed from: c, reason: collision with root package name */
    private Context f5002c;

    public a(Context context) {
        this.f5002c = context;
    }

    public void a(b.a aVar) throws PackageManager.NameNotFoundException {
        try {
            this.f5002c.getPackageManager().getPackageInfo("com.asus.msa.SupplementaryDID", 0);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setAction("com.asus.msa.action.ACCESS_DID");
        intent.setComponent(new ComponentName("com.asus.msa.SupplementaryDID", "com.asus.msa.SupplementaryDID.SupplementaryDIDService"));
        if (this.f5002c.bindService(intent, this.f5001b, 1)) {
            try {
                String strA = new a.C0063a(this.f5000a.take()).a();
                if (aVar != null) {
                    aVar.a(strA);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }
}
