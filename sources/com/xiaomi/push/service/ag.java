package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.hw;
import com.xiaomi.push.in;
import com.xiaomi.push.je;
import com.xiaomi.push.jp;
import com.xiaomi.push.service.bk;
import java.util.HashMap;

/* loaded from: classes6.dex */
final class ag extends bk.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25572a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ s f986a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ag(String str, long j2, XMPushService xMPushService, s sVar) {
        super(str, j2);
        this.f25572a = xMPushService;
        this.f986a = sVar;
    }

    @Override // com.xiaomi.push.service.bk.a
    public void a(bk bkVar) {
        String strA = bkVar.a("GAID", "gaid");
        String strB = com.xiaomi.push.j.b((Context) this.f25572a);
        com.xiaomi.channel.commonutils.logger.b.c("gaid :" + strB);
        if (TextUtils.isEmpty(strB) || TextUtils.equals(strA, strB)) {
            return;
        }
        bkVar.a("GAID", "gaid", strB);
        je jeVar = new je();
        jeVar.b(this.f986a.f25715d);
        jeVar.c(in.ClientInfoUpdate.f622a);
        jeVar.a(ar.a());
        jeVar.a(new HashMap());
        jeVar.m610a().put("gaid", strB);
        byte[] bArrA = jp.a(af.a(this.f25572a.getPackageName(), this.f986a.f25715d, jeVar, hw.Notification));
        XMPushService xMPushService = this.f25572a;
        xMPushService.a(xMPushService.getPackageName(), bArrA, true);
    }
}
