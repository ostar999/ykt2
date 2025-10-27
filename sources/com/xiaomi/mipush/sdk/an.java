package com.xiaomi.mipush.sdk;

import com.xiaomi.mipush.sdk.MiTinyDataClient;

/* loaded from: classes6.dex */
class an implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ MiTinyDataClient.a.C0405a f24524a;

    public an(MiTinyDataClient.a.C0405a c0405a) {
        this.f24524a = c0405a;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f24524a.f115a.size() != 0) {
            this.f24524a.b();
        } else if (this.f24524a.f116a != null) {
            this.f24524a.f116a.cancel(false);
            this.f24524a.f116a = null;
        }
    }
}
