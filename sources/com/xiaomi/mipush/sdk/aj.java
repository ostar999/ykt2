package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;

/* loaded from: classes6.dex */
final class aj implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24522a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String[] f123a;

    public aj(String[] strArr, Context context) {
        this.f123a = strArr;
        this.f24522a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        PackageInfo packageInfo;
        try {
            for (String str : this.f123a) {
                if (!TextUtils.isEmpty(str) && (packageInfo = this.f24522a.getPackageManager().getPackageInfo(str, 4)) != null) {
                    MiPushClient.awakePushServiceByPackageInfo(this.f24522a, packageInfo);
                }
            }
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
        }
    }
}
