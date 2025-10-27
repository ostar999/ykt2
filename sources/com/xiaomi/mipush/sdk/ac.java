package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;
import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
final class ac implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24516a;

    public ac(Context context) {
        this.f24516a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            PackageInfo packageInfo = this.f24516a.getPackageManager().getPackageInfo(this.f24516a.getPackageName(), R2.color.color_red_deep);
            ab.c(this.f24516a);
            ab.d(this.f24516a, packageInfo);
            ab.c(this.f24516a, packageInfo);
        } catch (Throwable th) {
            Log.e("ManifestChecker", "", th);
        }
    }
}
