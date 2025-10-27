package com.xiaomi.push;

import java.io.IOException;
import java.net.InetAddress;

/* loaded from: classes6.dex */
class ce implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ cm f24673a;

    /* renamed from: a, reason: collision with other field name */
    private String f249a;

    public ce(cm cmVar, String str) {
        this.f24673a = cmVar;
        this.f249a = str;
    }

    @Override // java.lang.Runnable
    public void run() throws IOException {
        try {
            InetAddress.getByName(this.f249a).isReachable(500);
        } catch (Exception unused) {
        }
    }
}
