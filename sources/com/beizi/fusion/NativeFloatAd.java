package com.beizi.fusion;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.RequiresPermission;
import com.beizi.fusion.d.q;

/* loaded from: classes2.dex */
public class NativeFloatAd {

    /* renamed from: a, reason: collision with root package name */
    private q f4718a;

    @RequiresPermission("android.permission.INTERNET")
    public NativeFloatAd(Context context, String str, NativeFloatAdListener nativeFloatAdListener) throws InterruptedException {
        q qVar = new q(context, str, nativeFloatAdListener, com.heytap.mcssdk.constant.a.f7153q);
        this.f4718a = qVar;
        qVar.a((ViewGroup) null);
    }

    public void destroy() {
        q qVar = this.f4718a;
        if (qVar != null) {
            qVar.l();
        }
    }

    @RequiresPermission("android.permission.INTERNET")
    public NativeFloatAd(Context context, String str, NativeFloatAdListener nativeFloatAdListener, long j2) throws InterruptedException {
        q qVar = new q(context, str, nativeFloatAdListener, j2);
        this.f4718a = qVar;
        qVar.a((ViewGroup) null);
    }
}
