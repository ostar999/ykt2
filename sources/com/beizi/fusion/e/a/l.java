package com.beizi.fusion.e.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import com.beizi.fusion.e.a.b;
import com.beizi.fusion.e.b.g;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes2.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    Context f5043a;

    /* renamed from: b, reason: collision with root package name */
    String f5044b = "com.mdid.msa";

    /* renamed from: c, reason: collision with root package name */
    public final LinkedBlockingQueue<IBinder> f5045c = new LinkedBlockingQueue<>(1);

    /* renamed from: d, reason: collision with root package name */
    ServiceConnection f5046d = new ServiceConnection() { // from class: com.beizi.fusion.e.a.l.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws InterruptedException {
            try {
                l.this.f5045c.put(iBinder);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    public l(Context context) {
        this.f5043a = context;
    }

    private int a() throws PackageManager.NameNotFoundException {
        try {
            this.f5043a.getPackageManager().getPackageInfo(this.f5044b, 0);
            return 1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    private void a(String str) throws PackageManager.NameNotFoundException {
        a();
        Intent intent = new Intent();
        intent.setClassName(this.f5044b, "com.mdid.msa.service.MsaKlService");
        intent.setAction("com.bun.msa.action.start.service");
        intent.putExtra("com.bun.msa.param.pkgname", str);
        try {
            intent.putExtra("com.bun.msa.param.runinset", true);
            this.f5043a.startService(intent);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(b.a aVar) throws PackageManager.NameNotFoundException {
        try {
            this.f5043a.getPackageManager().getPackageInfo(this.f5044b, 0);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        String packageName = this.f5043a.getPackageName();
        a(packageName);
        Intent intent = new Intent();
        intent.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaIdService");
        intent.setAction("com.bun.msa.action.bindto.service");
        intent.putExtra("com.bun.msa.param.pkgname", packageName);
        if (this.f5043a.bindService(intent, this.f5046d, 1)) {
            try {
                String strA = new g.a.C0067a(this.f5045c.take()).a();
                if (aVar != null) {
                    aVar.a(strA);
                }
                this.f5043a.unbindService(this.f5046d);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }
}
