package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.ai;
import com.xiaomi.push.hw;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.je;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class bi extends ai.a {

    /* renamed from: a, reason: collision with root package name */
    private Context f24552a;

    public bi(Context context) {
        this.f24552a = context;
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 10;
    }

    @Override // java.lang.Runnable
    public void run() {
        je jeVar = new je(com.xiaomi.push.service.ar.a(), false);
        d dVarM156a = d.m156a(this.f24552a);
        jeVar.c(in.SyncMIID.f622a);
        jeVar.b(dVarM156a.m157a());
        jeVar.d(this.f24552a.getPackageName());
        HashMap map = new HashMap();
        com.xiaomi.push.p.a(map, Constants.EXTRA_KEY_MIID, com.xiaomi.push.service.o.a(this.f24552a).a());
        jeVar.f769a = map;
        az.a(this.f24552a).a((az) jeVar, hw.Notification, true, (iq) null);
    }
}
