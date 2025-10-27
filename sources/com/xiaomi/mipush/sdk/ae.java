package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;

/* loaded from: classes6.dex */
final class ae implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24518a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Intent f122a;

    public ae(Context context, Intent intent) {
        this.f24518a = context;
        this.f122a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.f24518a.startService(this.f122a);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m117a(e2.getMessage());
        }
    }
}
