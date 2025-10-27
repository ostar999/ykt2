package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.eu;
import com.xiaomi.push.fa;
import com.xiaomi.push.fe;
import com.xiaomi.push.hu;
import com.xiaomi.push.hw;
import com.xiaomi.push.in;
import com.xiaomi.push.je;
import com.xiaomi.push.jp;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class az implements fe {
    @Override // com.xiaomi.push.fe
    public void a(Context context, HashMap<String, String> map) {
        je jeVar = new je();
        jeVar.b(fa.a(context).m416a());
        jeVar.d(fa.a(context).b());
        jeVar.c(in.AwakeAppResponse.f622a);
        jeVar.a(ar.a());
        jeVar.f769a = map;
        byte[] bArrA = jp.a(af.a(jeVar.c(), jeVar.b(), jeVar, hw.Notification));
        if (!(context instanceof XMPushService)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("MoleInfo : context is not correct in pushLayer " + jeVar.a());
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("MoleInfo : send data directly in pushLayer " + jeVar.a());
        ((XMPushService) context).a(context.getPackageName(), bArrA, true);
    }

    @Override // com.xiaomi.push.fe
    public void b(Context context, HashMap<String, String> map) {
        hu huVarA = hu.a(context);
        if (huVarA != null) {
            huVarA.a("category_awake_app", "wake_up_app", 1L, eu.a(map));
        }
    }

    @Override // com.xiaomi.push.fe
    public void c(Context context, HashMap<String, String> map) {
        com.xiaomi.channel.commonutils.logger.b.m117a("MoleInfo：\u3000" + eu.b(map));
    }
}
