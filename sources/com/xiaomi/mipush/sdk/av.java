package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;

/* loaded from: classes6.dex */
final class av implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24533a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Intent f128a;

    public av(Context context, Intent intent) {
        this.f24533a = context;
        this.f128a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        PushMessageHandler.b(this.f24533a, this.f128a);
    }
}
