package com.beizi.fusion.e.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.Signature;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import com.beizi.fusion.e.a.b;
import com.beizi.fusion.e.b.d;
import com.google.android.exoplayer2.C;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class g {

    /* renamed from: b, reason: collision with root package name */
    com.beizi.fusion.e.b.d f5018b;

    /* renamed from: d, reason: collision with root package name */
    private Context f5020d;

    /* renamed from: e, reason: collision with root package name */
    private String f5021e;

    /* renamed from: a, reason: collision with root package name */
    public String f5017a = "OUID";

    /* renamed from: c, reason: collision with root package name */
    ServiceConnection f5019c = new ServiceConnection() { // from class: com.beizi.fusion.e.a.g.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            g.this.f5018b = d.a.a(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            g.this.f5018b = null;
        }
    };

    public g(Context context) {
        this.f5020d = context;
    }

    public String a(b.a aVar) throws NoSuchAlgorithmException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot run on MainThread");
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.heytap.openid", "com.heytap.openid.IdentifyService"));
        intent.setAction("action.com.heytap.openid.OPEN_ID_SERVICE");
        if (this.f5020d.bindService(intent, this.f5019c, 1)) {
            try {
                SystemClock.sleep(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (this.f5018b != null) {
                String strA = a("OUID");
                a("DUID");
                a("AUID");
                if (aVar == null) {
                    return strA;
                }
                aVar.a(strA);
                return strA;
            }
        }
        return null;
    }

    private String a(String str) throws NoSuchAlgorithmException {
        Signature[] signatureArr;
        String packageName = this.f5020d.getPackageName();
        if (this.f5021e == null) {
            String string = null;
            try {
                signatureArr = this.f5020d.getPackageManager().getPackageInfo(packageName, 64).signatures;
            } catch (Exception e2) {
                e2.printStackTrace();
                signatureArr = null;
            }
            if (signatureArr != null && signatureArr.length > 0) {
                byte[] byteArray = signatureArr[0].toByteArray();
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                    if (messageDigest != null) {
                        byte[] bArrDigest = messageDigest.digest(byteArray);
                        StringBuilder sb = new StringBuilder();
                        for (byte b3 : bArrDigest) {
                            sb.append(Integer.toHexString((b3 & 255) | 256).substring(1, 3));
                        }
                        string = sb.toString();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            this.f5021e = string;
        }
        return ((d.a.C0065a) this.f5018b).a(packageName, this.f5021e, str);
    }
}
