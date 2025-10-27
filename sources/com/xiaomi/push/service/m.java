package com.xiaomi.push.service;

import com.xiaomi.push.hv;
import com.xiaomi.push.ib;
import java.util.List;

/* loaded from: classes6.dex */
public class m implements hv {

    /* renamed from: a, reason: collision with root package name */
    private final XMPushService f25699a;

    public m(XMPushService xMPushService) {
        this.f25699a = xMPushService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String str) {
        return "com.xiaomi.xmsf".equals(str) ? "1000271" : this.f25699a.getSharedPreferences("pref_registered_pkg_names", 0).getString(str, null);
    }

    @Override // com.xiaomi.push.hv
    public void a(List<ib> list, String str, String str2) {
        com.xiaomi.channel.commonutils.logger.b.m117a("TinyData LongConnUploader.upload items size:" + list.size() + "  ts:" + System.currentTimeMillis());
        this.f25699a.a(new n(this, 4, str, list, str2));
    }
}
