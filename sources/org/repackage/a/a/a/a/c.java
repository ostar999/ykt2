package org.repackage.a.a.a.a;

import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.repackage.a.a.a.a;

/* loaded from: classes9.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public org.repackage.a.a.a.a f27988a = null;

    /* renamed from: b, reason: collision with root package name */
    public String f27989b = null;

    /* renamed from: c, reason: collision with root package name */
    public String f27990c = null;

    /* renamed from: d, reason: collision with root package name */
    public final Object f27991d = new Object();

    /* renamed from: e, reason: collision with root package name */
    public ServiceConnection f27992e = new b(this);

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final c f27993a = new c(null);
    }

    public /* synthetic */ c(b bVar) {
    }

    public boolean a(Context context) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.heytap.openid", 0);
            return Build.VERSION.SDK_INT >= 28 ? packageInfo != null && packageInfo.getLongVersionCode() >= 1 : packageInfo != null && packageInfo.versionCode >= 1;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final String b(Context context, String str) throws NoSuchAlgorithmException {
        Signature[] signatureArr;
        if (TextUtils.isEmpty(this.f27989b)) {
            this.f27989b = context.getPackageName();
        }
        if (TextUtils.isEmpty(this.f27990c)) {
            String string = null;
            try {
                signatureArr = context.getPackageManager().getPackageInfo(this.f27989b, 64).signatures;
            } catch (PackageManager.NameNotFoundException e2) {
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
                } catch (NoSuchAlgorithmException e3) {
                    e3.printStackTrace();
                }
            }
            this.f27990c = string;
        }
        String strA = ((a.AbstractBinderC0476a.C0477a) this.f27988a).a(this.f27989b, this.f27990c, str);
        return TextUtils.isEmpty(strA) ? "" : strA;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x004b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized java.lang.String a(android.content.Context r5, java.lang.String r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.os.Looper r0 = android.os.Looper.myLooper()     // Catch: java.lang.Throwable -> L6f
            android.os.Looper r1 = android.os.Looper.getMainLooper()     // Catch: java.lang.Throwable -> L6f
            if (r0 == r1) goto L67
            org.repackage.a.a.a.a r0 = r4.f27988a     // Catch: java.lang.Throwable -> L6f
            if (r0 != 0) goto L59
            android.content.Intent r0 = new android.content.Intent     // Catch: java.lang.Throwable -> L6f
            r0.<init>()     // Catch: java.lang.Throwable -> L6f
            android.content.ComponentName r1 = new android.content.ComponentName     // Catch: java.lang.Throwable -> L6f
            java.lang.String r2 = "com.heytap.openid"
            java.lang.String r3 = "com.heytap.openid.IdentifyService"
            r1.<init>(r2, r3)     // Catch: java.lang.Throwable -> L6f
            r0.setComponent(r1)     // Catch: java.lang.Throwable -> L6f
            java.lang.String r1 = "action.com.heytap.openid.OPEN_ID_SERVICE"
            r0.setAction(r1)     // Catch: java.lang.Throwable -> L6f
            android.content.ServiceConnection r1 = r4.f27992e     // Catch: java.lang.Throwable -> L6f
            r2 = 1
            boolean r0 = r5.bindService(r0, r1, r2)     // Catch: java.lang.Throwable -> L6f
            if (r0 == 0) goto L43
            java.lang.Object r0 = r4.f27991d     // Catch: java.lang.Throwable -> L6f
            monitor-enter(r0)     // Catch: java.lang.Throwable -> L6f
            java.lang.Object r1 = r4.f27991d     // Catch: java.lang.Throwable -> L39 java.lang.InterruptedException -> L3b
            r2 = 3000(0xbb8, double:1.482E-320)
            r1.wait(r2)     // Catch: java.lang.Throwable -> L39 java.lang.InterruptedException -> L3b
            goto L3f
        L39:
            r5 = move-exception
            goto L41
        L3b:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L39
        L3f:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L39
            goto L43
        L41:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L39
            throw r5     // Catch: java.lang.Throwable -> L6f
        L43:
            org.repackage.a.a.a.a r0 = r4.f27988a     // Catch: java.lang.Throwable -> L6f
            if (r0 != 0) goto L4b
            java.lang.String r5 = ""
            monitor-exit(r4)
            return r5
        L4b:
            java.lang.String r5 = r4.b(r5, r6)     // Catch: android.os.RemoteException -> L51 java.lang.Throwable -> L6f
            monitor-exit(r4)
            return r5
        L51:
            r5 = move-exception
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L6f
            java.lang.String r5 = ""
            monitor-exit(r4)
            return r5
        L59:
            java.lang.String r5 = r4.b(r5, r6)     // Catch: android.os.RemoteException -> L5f java.lang.Throwable -> L6f
            monitor-exit(r4)
            return r5
        L5f:
            r5 = move-exception
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L6f
            java.lang.String r5 = ""
            monitor-exit(r4)
            return r5
        L67:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L6f
            java.lang.String r6 = "Cannot run on MainThread"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L6f
            throw r5     // Catch: java.lang.Throwable -> L6f
        L6f:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.repackage.a.a.a.a.c.a(android.content.Context, java.lang.String):java.lang.String");
    }
}
