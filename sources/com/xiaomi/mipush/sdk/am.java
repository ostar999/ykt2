package com.xiaomi.mipush.sdk;

import com.xiaomi.mipush.sdk.MiTinyDataClient;
import com.xiaomi.push.ib;

/* loaded from: classes6.dex */
class am implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ MiTinyDataClient.a.C0405a f24523a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ ib f124a;

    public am(MiTinyDataClient.a.C0405a c0405a, ib ibVar) {
        this.f24523a = c0405a;
        this.f124a = ibVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f24523a.f115a.add(this.f124a);
        this.f24523a.a();
    }
}
