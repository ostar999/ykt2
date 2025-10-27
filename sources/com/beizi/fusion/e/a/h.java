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
import com.beizi.fusion.e.b.e;
import com.google.android.exoplayer2.C;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class h {

    /* renamed from: b, reason: collision with root package name */
    com.beizi.fusion.e.b.e f5024b;

    /* renamed from: d, reason: collision with root package name */
    private Context f5026d;

    /* renamed from: e, reason: collision with root package name */
    private String f5027e;

    /* renamed from: a, reason: collision with root package name */
    public String f5023a = "OUID";

    /* renamed from: c, reason: collision with root package name */
    ServiceConnection f5025c = new ServiceConnection() { // from class: com.beizi.fusion.e.a.h.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            h.this.f5024b = e.a.a(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            h.this.f5024b = null;
        }
    };

    public h(Context context) {
        this.f5026d = context;
    }

    public String a(b.a aVar) throws NoSuchAlgorithmException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot run on MainThread");
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.heytap.openid", "com.heytap.openid.IdentifyService"));
        intent.setAction("action.com.heytap.openid.OPEN_ID_SERVICE");
        if (this.f5026d.bindService(intent, this.f5025c, 1)) {
            try {
                SystemClock.sleep(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (this.f5024b != null) {
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
        String packageName = this.f5026d.getPackageName();
        if (this.f5027e == null) {
            String string = null;
            try {
                signatureArr = this.f5026d.getPackageManager().getPackageInfo(packageName, 64).signatures;
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
            this.f5027e = string;
        }
        return ((e.a.C0066a) this.f5024b).a(packageName, this.f5027e, str);
    }
}
