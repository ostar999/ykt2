package com.xiaomi.mipush.sdk;

import android.content.Context;

/* loaded from: classes6.dex */
final class af implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24519a;

    public af(Context context) {
        this.f24519a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        MessageHandleService.c(this.f24519a);
    }
}
