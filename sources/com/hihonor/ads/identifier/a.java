package com.hihonor.ads.identifier;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import com.hihonor.ads.identifier.AdvertisingIdClient;
import com.hihonor.cloudservice.oaid.a;
import com.hihonor.cloudservice.oaid.b;
import io.socket.client.Socket;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
public class a implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    public AdvertisingIdClient.Info f7267a;

    /* renamed from: b, reason: collision with root package name */
    public Context f7268b;

    /* renamed from: c, reason: collision with root package name */
    public BinderC0173a f7269c = new BinderC0173a();

    /* renamed from: d, reason: collision with root package name */
    public b f7270d = new b();

    /* renamed from: e, reason: collision with root package name */
    public CountDownLatch f7271e = new CountDownLatch(2);

    /* renamed from: com.hihonor.ads.identifier.a$a, reason: collision with other inner class name */
    public class BinderC0173a extends a.AbstractBinderC0174a {
        public BinderC0173a() {
        }

        @Override // com.hihonor.cloudservice.oaid.a
        public void a(int i2, long j2, boolean z2, float f2, double d3, String str) {
        }

        @Override // com.hihonor.cloudservice.oaid.a
        public void a(int i2, Bundle bundle) {
            Log.e("AdvertisingIdPlatform", "OAIDCallBack handleResult retCode=" + i2 + " retInfo=" + bundle);
            if (i2 != 0 || bundle == null) {
                Log.e("AdvertisingIdPlatform", "OAIDCallBack handleResult error retCode=$ " + i2);
            } else if (a.this.f7267a != null) {
                a.this.f7267a.id = bundle.getString("oa_id_flag");
                Log.i("AdvertisingIdPlatform", "OAIDCallBack handleResult success");
            }
            a.this.f7271e.countDown();
        }
    }

    public class b extends a.AbstractBinderC0174a {
        public b() {
        }

        @Override // com.hihonor.cloudservice.oaid.a
        public void a(int i2, long j2, boolean z2, float f2, double d3, String str) {
        }

        @Override // com.hihonor.cloudservice.oaid.a
        public void a(int i2, Bundle bundle) {
            Log.e("AdvertisingIdPlatform", "OAIDCallBack handleResult retCode=" + i2 + " retInfo= " + bundle);
            if (i2 != 0 || bundle == null) {
                Log.e("AdvertisingIdPlatform", "OAIDLimitCallback handleResult error retCode= " + i2);
            } else if (a.this.f7267a != null) {
                boolean z2 = bundle.getBoolean("oa_id_limit_state");
                a.this.f7267a.isLimit = z2;
                Log.i("AdvertisingIdPlatform", "OAIDLimitCallback handleResult success  isLimit=" + z2);
            }
            a.this.f7271e.countDown();
        }
    }

    public final void a() {
        Log.i("AdvertisingIdPlatform", Socket.EVENT_DISCONNECT);
        try {
            this.f7268b.unbindService(this);
        } catch (Exception e2) {
            Log.e("AdvertisingIdPlatform", "OAIDClientImpl#disconnect#Disconnect error::" + e2.getMessage());
        }
    }

    public boolean a(Context context) throws PackageManager.NameNotFoundException {
        try {
            context.getPackageManager().getPackageInfo("com.hihonor.id", 0);
            new Intent("com.hihonor.id.HnOaIdService").setPackage("com.hihonor.id");
            return !r5.queryIntentServices(r2, 0).isEmpty();
        } catch (PackageManager.NameNotFoundException | Exception unused) {
            return false;
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        com.hihonor.cloudservice.oaid.b c0175a;
        Log.i("AdvertisingIdPlatform", "onServiceConnected ");
        try {
            this.f7267a = new AdvertisingIdClient.Info();
            int i2 = b.a.f7274a;
            if (iBinder == null) {
                c0175a = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.hihonor.cloudservice.oaid.IOAIDService");
                c0175a = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof com.hihonor.cloudservice.oaid.b)) ? new b.a.C0175a(iBinder) : (com.hihonor.cloudservice.oaid.b) iInterfaceQueryLocalInterface;
            }
            c0175a.b(this.f7269c);
            c0175a.a(this.f7270d);
        } catch (Exception e2) {
            Log.e("AdvertisingIdPlatform", "onServiceConnected error:" + e2.getMessage());
            this.f7271e.countDown();
            this.f7271e.countDown();
            a();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        Log.i("AdvertisingIdPlatform", "onServiceDisconnected ");
        this.f7271e.countDown();
        this.f7271e.countDown();
    }
}
