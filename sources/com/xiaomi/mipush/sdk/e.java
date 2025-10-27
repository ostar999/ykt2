package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.eu;
import com.xiaomi.push.fa;
import com.xiaomi.push.fe;
import com.xiaomi.push.hw;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.je;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class e implements fe {
    @Override // com.xiaomi.push.fe
    public void a(Context context, HashMap<String, String> map) {
        je jeVar = new je();
        jeVar.b(fa.a(context).m416a());
        jeVar.d(fa.a(context).b());
        jeVar.c(in.AwakeAppResponse.f622a);
        jeVar.a(com.xiaomi.push.service.ar.a());
        jeVar.f769a = map;
        az.a(context).a((az) jeVar, hw.Notification, true, (iq) null, true);
        com.xiaomi.channel.commonutils.logger.b.m117a("MoleInfo：\u3000send data in app layer");
    }

    @Override // com.xiaomi.push.fe
    public void b(Context context, HashMap<String, String> map) {
        MiTinyDataClient.upload("category_awake_app", "wake_up_app", 1L, eu.a(map));
        com.xiaomi.channel.commonutils.logger.b.m117a("MoleInfo：\u3000send data in app layer");
    }

    @Override // com.xiaomi.push.fe
    public void c(Context context, HashMap<String, String> map) {
        com.xiaomi.channel.commonutils.logger.b.m117a("MoleInfo：\u3000" + eu.b(map));
        String str = map.get("event_type");
        String str2 = map.get("awake_info");
        if (String.valueOf(1007).equals(str)) {
            o.a(context, str2);
        }
    }
}
