package com.beizi.fusion;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.RequiresPermission;
import com.beizi.fusion.d.r;

/* loaded from: classes2.dex */
public class NativeNotificationAd {

    /* renamed from: a, reason: collision with root package name */
    private r f4719a;

    @RequiresPermission("android.permission.INTERNET")
    public NativeNotificationAd(Context context, String str, NativeNotificationAdListener nativeNotificationAdListener) throws InterruptedException {
        r rVar = new r(context, str, nativeNotificationAdListener, com.heytap.mcssdk.constant.a.f7153q);
        this.f4719a = rVar;
        rVar.a((ViewGroup) null);
    }

    public void destroy() {
        r rVar = this.f4719a;
        if (rVar != null) {
            rVar.l();
        }
    }

    @RequiresPermission("android.permission.INTERNET")
    public NativeNotificationAd(Context context, String str, NativeNotificationAdListener nativeNotificationAdListener, long j2) throws InterruptedException {
        r rVar = new r(context, str, nativeNotificationAdListener, j2);
        this.f4719a = rVar;
        rVar.a((ViewGroup) null);
    }
}
