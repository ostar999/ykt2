package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;

/* loaded from: classes6.dex */
class ax implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24535a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ aw f132a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String[] f133a;

    public ax(aw awVar, String[] strArr, Context context) {
        this.f132a = awVar;
        this.f133a = strArr;
        this.f24535a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        int i2 = 0;
        while (true) {
            try {
                String[] strArr = this.f133a;
                if (i2 >= strArr.length) {
                    return;
                }
                if (!TextUtils.isEmpty(strArr[i2])) {
                    if (i2 > 0) {
                        Thread.sleep(((long) ((Math.random() * 2.0d) + 1.0d)) * 1000);
                    }
                    PackageInfo packageInfo = this.f24535a.getPackageManager().getPackageInfo(this.f133a[i2], 4);
                    if (packageInfo != null) {
                        this.f132a.a(this.f24535a, packageInfo);
                    }
                }
                i2++;
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.a(th);
                return;
            }
        }
    }
}
