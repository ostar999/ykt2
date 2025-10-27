package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.ai;
import com.xiaomi.push.hw;
import com.xiaomi.push.id;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.ix;
import com.xiaomi.push.je;
import com.xiaomi.push.jp;

/* loaded from: classes6.dex */
public class ao extends ai.a {

    /* renamed from: a, reason: collision with root package name */
    private Context f24525a;

    public ao(Context context) {
        this.f24525a = context;
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 2;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.push.service.ao aoVarA = com.xiaomi.push.service.ao.a(this.f24525a);
        ix ixVar = new ix();
        ixVar.a(com.xiaomi.push.service.ap.a(aoVarA, id.MISC_CONFIG));
        ixVar.b(com.xiaomi.push.service.ap.a(aoVarA, id.PLUGIN_CONFIG));
        je jeVar = new je("-1", false);
        jeVar.c(in.DailyCheckClientConfig.f622a);
        jeVar.a(jp.a(ixVar));
        az.a(this.f24525a).a((az) jeVar, hw.Notification, (iq) null);
    }
}
