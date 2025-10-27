package com.xiaomi.push;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/* loaded from: classes6.dex */
public class ee extends ei {
    public ee(Context context, int i2) {
        super(context, i2);
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 4;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.AppInstallList;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public String mo336a() {
        StringBuilder sb = new StringBuilder();
        try {
            PackageManager packageManager = ((ei) this).f339a.getPackageManager();
            int i2 = 0;
            for (PackageInfo packageInfo : packageManager.getInstalledPackages(128)) {
                if ((packageInfo.applicationInfo.flags & 1) == 0) {
                    if (sb.length() > 0) {
                        sb.append(com.alipay.sdk.util.h.f3376b);
                    }
                    String string = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                    String strB = g.b(((ei) this).f339a, packageInfo.packageName);
                    sb.append(string);
                    sb.append(",");
                    sb.append(packageInfo.packageName);
                    sb.append(",");
                    sb.append(packageInfo.versionName);
                    sb.append(",");
                    sb.append(packageInfo.versionCode);
                    sb.append(",");
                    sb.append(strB);
                    i2++;
                    if (i2 >= 200) {
                        break;
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return sb.toString();
    }
}
