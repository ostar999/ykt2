package com.beizi.fusion.sm.a.a;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import com.beizi.fusion.sm.a.a.m;
import com.beizi.fusion.sm.repeackage.com.heytap.openid.IOpenID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
class n implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5305a;

    /* renamed from: b, reason: collision with root package name */
    private String f5306b;

    public n(Context context) {
        if (context instanceof Application) {
            this.f5305a = context;
        } else {
            this.f5305a = context.getApplicationContext();
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() {
        Context context = this.f5305a;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.heytap.openid", 0) != null;
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            return false;
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) {
        if (this.f5305a == null || cVar == null) {
            return;
        }
        Intent intent = new Intent("action.com.heytap.openid.OPEN_ID_SERVICE");
        intent.setComponent(new ComponentName("com.heytap.openid", "com.heytap.openid.IdentifyService"));
        m.a(this.f5305a, intent, cVar, new m.a() { // from class: com.beizi.fusion.sm.a.a.n.1
            @Override // com.beizi.fusion.sm.a.a.m.a
            public String a(IBinder iBinder) throws com.beizi.fusion.sm.a.e, RemoteException {
                try {
                    return n.this.a(iBinder);
                } catch (RemoteException e2) {
                    throw e2;
                } catch (com.beizi.fusion.sm.a.e e3) {
                    throw e3;
                } catch (Exception e4) {
                    throw new com.beizi.fusion.sm.a.e(e4);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"PackageManagerGetSignatures"})
    public String a(IBinder iBinder) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, com.beizi.fusion.sm.a.e, RemoteException {
        String packageName = this.f5305a.getPackageName();
        String str = this.f5306b;
        if (str == null) {
            byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(this.f5305a.getPackageManager().getPackageInfo(packageName, 64).signatures[0].toByteArray());
            StringBuilder sb = new StringBuilder();
            for (byte b3 : bArrDigest) {
                sb.append(Integer.toHexString((b3 & 255) | 256).substring(1, 3));
            }
            String string = sb.toString();
            this.f5306b = string;
            return a(iBinder, packageName, string);
        }
        return a(iBinder, packageName, str);
    }

    private String a(IBinder iBinder, String str, String str2) throws com.beizi.fusion.sm.a.e, RemoteException {
        IOpenID iOpenIDAsInterface = IOpenID.Stub.asInterface(iBinder);
        if (iOpenIDAsInterface != null) {
            return iOpenIDAsInterface.getSerID(str, str2, "OUID");
        }
        throw new com.beizi.fusion.sm.a.e("IOpenID is null");
    }
}
