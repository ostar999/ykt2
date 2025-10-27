package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.hw;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.je;
import com.yikaobang.yixue.R2;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
final class bg implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24551a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f146a;

    public bg(Context context, boolean z2) {
        this.f24551a = context;
        this.f146a = z2;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException, NoSuchAlgorithmException {
        Map<String, String> map;
        String strD;
        String str;
        com.xiaomi.channel.commonutils.logger.b.m117a("do sync info");
        je jeVar = new je(com.xiaomi.push.service.ar.a(), false);
        d dVarM156a = d.m156a(this.f24551a);
        jeVar.c(in.SyncInfo.f622a);
        jeVar.b(dVarM156a.m157a());
        jeVar.d(this.f24551a.getPackageName());
        HashMap map2 = new HashMap();
        jeVar.f769a = map2;
        Context context = this.f24551a;
        com.xiaomi.push.p.a(map2, "app_version", com.xiaomi.push.g.m439a(context, context.getPackageName()));
        Map<String, String> map3 = jeVar.f769a;
        Context context2 = this.f24551a;
        com.xiaomi.push.p.a(map3, Constants.EXTRA_KEY_APP_VERSION_CODE, Integer.toString(com.xiaomi.push.g.a(context2, context2.getPackageName())));
        com.xiaomi.push.p.a(jeVar.f769a, "push_sdk_vn", "3_6_12");
        com.xiaomi.push.p.a(jeVar.f769a, "push_sdk_vc", Integer.toString(R2.styleable.background_selector_bl_focused_activated));
        com.xiaomi.push.p.a(jeVar.f769a, "token", dVarM156a.b());
        if (!com.xiaomi.push.n.d()) {
            String strA = com.xiaomi.push.ay.a(com.xiaomi.push.j.f(this.f24551a));
            String strH = com.xiaomi.push.j.h(this.f24551a);
            if (!TextUtils.isEmpty(strH)) {
                strA = strA + "," + strH;
            }
            if (!TextUtils.isEmpty(strA)) {
                com.xiaomi.push.p.a(jeVar.f769a, Constants.EXTRA_KEY_IMEI_MD5, strA);
            }
        }
        com.xiaomi.push.p.a(jeVar.f769a, Constants.EXTRA_KEY_REG_ID, dVarM156a.m164c());
        com.xiaomi.push.p.a(jeVar.f769a, Constants.EXTRA_KEY_REG_SECRET, dVarM156a.d());
        com.xiaomi.push.p.a(jeVar.f769a, Constants.EXTRA_KEY_ACCEPT_TIME, MiPushClient.getAcceptTime(this.f24551a).replace(",", "-"));
        if (this.f146a) {
            com.xiaomi.push.p.a(jeVar.f769a, Constants.EXTRA_KEY_ALIASES_MD5, bf.c(MiPushClient.getAllAlias(this.f24551a)));
            com.xiaomi.push.p.a(jeVar.f769a, Constants.EXTRA_KEY_TOPICS_MD5, bf.c(MiPushClient.getAllTopic(this.f24551a)));
            map = jeVar.f769a;
            strD = bf.c(MiPushClient.getAllUserAccount(this.f24551a));
            str = Constants.EXTRA_KEY_ACCOUNTS_MD5;
        } else {
            com.xiaomi.push.p.a(jeVar.f769a, Constants.EXTRA_KEY_ALIASES, bf.d(MiPushClient.getAllAlias(this.f24551a)));
            com.xiaomi.push.p.a(jeVar.f769a, Constants.EXTRA_KEY_TOPICS, bf.d(MiPushClient.getAllTopic(this.f24551a)));
            map = jeVar.f769a;
            strD = bf.d(MiPushClient.getAllUserAccount(this.f24551a));
            str = Constants.EXTRA_KEY_ACCOUNTS;
        }
        com.xiaomi.push.p.a(map, str, strD);
        az.a(this.f24551a).a((az) jeVar, hw.Notification, false, (iq) null);
    }
}
