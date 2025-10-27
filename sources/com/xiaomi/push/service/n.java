package com.xiaomi.push.service;

import android.text.TextUtils;
import com.xiaomi.push.hw;
import com.xiaomi.push.ib;
import com.xiaomi.push.iq;
import com.xiaomi.push.jb;
import com.xiaomi.push.je;
import com.xiaomi.push.jp;
import com.xiaomi.push.service.XMPushService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
class n extends XMPushService.i {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ m f25702a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f1076a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ List f1077a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f25703b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public n(m mVar, int i2, String str, List list, String str2) {
        super(i2);
        this.f25702a = mVar;
        this.f1076a = str;
        this.f1077a = list;
        this.f25703b = str2;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    public String a() {
        return "Send tiny data.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public void mo463a() {
        String strA = this.f25702a.a(this.f1076a);
        ArrayList<je> arrayListA = bl.a(this.f1077a, this.f1076a, strA, 32768);
        com.xiaomi.channel.commonutils.logger.b.m117a("TinyData LongConnUploader.upload pack notifications " + arrayListA.toString() + "  ts:" + System.currentTimeMillis());
        Iterator<je> it = arrayListA.iterator();
        while (it.hasNext()) {
            je next = it.next();
            next.a("uploadWay", "longXMPushService");
            jb jbVarA = af.a(this.f1076a, strA, next, hw.Notification);
            if (!TextUtils.isEmpty(this.f25703b) && !TextUtils.equals(this.f1076a, this.f25703b)) {
                if (jbVarA.m595a() == null) {
                    iq iqVar = new iq();
                    iqVar.a("-1");
                    jbVarA.a(iqVar);
                }
                jbVarA.m595a().b("ext_traffic_source_pkg", this.f25703b);
            }
            this.f25702a.f25699a.a(this.f1076a, jp.a(jbVarA), true);
        }
        Iterator it2 = this.f1077a.iterator();
        while (it2.hasNext()) {
            com.xiaomi.channel.commonutils.logger.b.m117a("TinyData LongConnUploader.upload uploaded by com.xiaomi.push.service.TinyDataUploader.  item" + ((ib) it2.next()).d() + "  ts:" + System.currentTimeMillis());
        }
    }
}
