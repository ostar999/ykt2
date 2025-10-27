package com.xiaomi.mipush.sdk;

/* loaded from: classes6.dex */
final class ag implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f24520a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f24521b;

    public ag(String str, String str2) {
        this.f24520a = str;
        this.f24521b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        MiPushClient.initialize(MiPushClient.sContext, this.f24520a, this.f24521b, null);
    }
}
