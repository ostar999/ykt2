package com.umeng.commonsdk.stateless;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private String f23278a = "10.0.0.172";

    /* renamed from: b, reason: collision with root package name */
    private int f23279b = 80;

    /* renamed from: c, reason: collision with root package name */
    private Context f23280c;

    public c(Context context) {
        this.f23280c = context;
    }

    private void a() {
        String strImprintProperty = UMEnvelopeBuild.imprintProperty(this.f23280c, "sl_domain_p", "");
        if (TextUtils.isEmpty(strImprintProperty)) {
            return;
        }
        a.f23261i = DataHelper.assembleStatelessURL(strImprintProperty);
    }

    private void b() {
        String strImprintProperty = UMEnvelopeBuild.imprintProperty(this.f23280c, "sl_domain_p", "");
        String strImprintProperty2 = UMEnvelopeBuild.imprintProperty(this.f23280c, "oversea_sl_domain_p", "");
        if (!TextUtils.isEmpty(strImprintProperty)) {
            a.f23260h = DataHelper.assembleStatelessURL(strImprintProperty);
        }
        if (!TextUtils.isEmpty(strImprintProperty2)) {
            a.f23263k = DataHelper.assembleStatelessURL(strImprintProperty2);
        }
        a.f23261i = a.f23263k;
        if (TextUtils.isEmpty(com.umeng.commonsdk.statistics.b.f23288b)) {
            return;
        }
        if (com.umeng.commonsdk.statistics.b.f23288b.startsWith("460") || com.umeng.commonsdk.statistics.b.f23288b.startsWith("461")) {
            a.f23261i = a.f23260h;
        }
    }

    private boolean c() {
        NetworkInfo activeNetworkInfo;
        String extraInfo;
        Context context = this.f23280c;
        if (context == null || context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", this.f23280c.getPackageName()) != 0) {
            return false;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.f23280c.getSystemService("connectivity");
            if (DeviceConfig.checkPermission(this.f23280c, "android.permission.ACCESS_NETWORK_STATE") && connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.getType() != 1 && (extraInfo = activeNetworkInfo.getExtraInfo()) != null) {
                if (!extraInfo.equals("cmwap") && !extraInfo.equals("3gwap")) {
                    if (extraInfo.equals("uniwap")) {
                    }
                }
                return true;
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(this.f23280c, th);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0114 A[EXC_TOP_SPLITTER, PHI: r2 r4 r13
      0x0114: PHI (r2v3 boolean) = (r2v0 boolean), (r2v0 boolean), (r2v4 boolean), (r2v4 boolean) binds: [B:35:0x012b, B:42:0x013a, B:53:0x0114, B:23:0x0111] A[DONT_GENERATE, DONT_INLINE]
      0x0114: PHI (r4v8 java.io.OutputStream) = (r4v5 java.io.OutputStream), (r4v6 java.io.OutputStream), (r4v11 java.io.OutputStream), (r4v11 java.io.OutputStream) binds: [B:35:0x012b, B:42:0x013a, B:53:0x0114, B:23:0x0111] A[DONT_GENERATE, DONT_INLINE]
      0x0114: PHI (r13v8 ??) = (r13v5 ??), (r13v6 ??), (r13v13 ??), (r13v13 ??) binds: [B:35:0x012b, B:42:0x013a, B:53:0x0114, B:23:0x0111] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x013d A[PHI: r2 r4 r13
      0x013d: PHI (r2v2 boolean) = (r2v0 boolean), (r2v0 boolean), (r2v3 boolean) binds: [B:35:0x012b, B:42:0x013a, B:24:0x0114] A[DONT_GENERATE, DONT_INLINE]
      0x013d: PHI (r4v7 java.io.OutputStream) = (r4v5 java.io.OutputStream), (r4v6 java.io.OutputStream), (r4v8 java.io.OutputStream) binds: [B:35:0x012b, B:42:0x013a, B:24:0x0114] A[DONT_GENERATE, DONT_INLINE]
      0x013d: PHI (r13v7 ??) = (r13v5 ??), (r13v6 ??), (r13v8 ??) binds: [B:35:0x012b, B:42:0x013a, B:24:0x0114] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r13v13, types: [java.net.HttpURLConnection, java.net.URLConnection, javax.net.ssl.HttpsURLConnection] */
    /* JADX WARN: Type inference failed for: r13v18 */
    /* JADX WARN: Type inference failed for: r13v19 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v20 */
    /* JADX WARN: Type inference failed for: r13v21 */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r7v2, types: [java.lang.StringBuilder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(byte[] r11, java.lang.String r12, java.lang.String r13, java.lang.String r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.stateless.c.a(byte[], java.lang.String, java.lang.String, java.lang.String):boolean");
    }
}
