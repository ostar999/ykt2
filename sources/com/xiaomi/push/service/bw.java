package com.xiaomi.push.service;

import com.xiaomi.push.et;
import com.xiaomi.push.gd;
import com.xiaomi.push.gg;
import java.util.Map;

/* loaded from: classes6.dex */
class bw extends gd {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25669a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bw(XMPushService xMPushService, Map map, int i2, String str, gg ggVar) {
        super(map, i2, str, ggVar);
        this.f25669a = xMPushService;
    }

    @Override // com.xiaomi.push.gd
    /* renamed from: a */
    public byte[] mo459a() {
        try {
            et.b bVar = new et.b();
            bVar.a(bi.a().m734a());
            return bVar.m334a();
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m117a("getOBBString err: " + e2.toString());
            return null;
        }
    }
}
