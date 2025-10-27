package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.ic;

/* loaded from: classes6.dex */
final class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24553a;

    public c(Context context) {
        this.f24553a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (b.m155b(this.f24553a)) {
            String strB = b.b(com.xiaomi.push.service.ao.a(this.f24553a).a(ic.AggregationSdkMonitorDepth.a(), 4));
            if (TextUtils.isEmpty(strB)) {
                return;
            }
            MiTinyDataClient.upload(this.f24553a, "monitor_upload", "call_stack", 1L, strB);
            b.c(this.f24553a);
        }
    }
}
