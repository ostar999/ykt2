package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.ai;
import com.xiaomi.push.hw;
import com.xiaomi.push.iq;
import com.xiaomi.push.je;

/* loaded from: classes6.dex */
final class p extends ai.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24576a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ je f162a;

    public p(je jeVar, Context context) {
        this.f162a = jeVar;
        this.f24576a = context;
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 22;
    }

    @Override // java.lang.Runnable
    public void run() {
        je jeVar = this.f162a;
        if (jeVar != null) {
            jeVar.a(com.xiaomi.push.service.ar.a());
            az.a(this.f24576a.getApplicationContext()).a((az) this.f162a, hw.Notification, true, (iq) null, true);
        }
    }
}
