package com.xiaomi.push.service;

import android.util.Base64;
import com.xiaomi.push.al;
import com.xiaomi.push.dg;
import com.xiaomi.push.es;
import com.xiaomi.push.service.bi;
import java.util.List;

/* loaded from: classes6.dex */
class bj extends al.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ bi f25655a;

    /* renamed from: a, reason: collision with other field name */
    boolean f1040a = false;

    public bj(bi biVar) {
        this.f25655a = biVar;
    }

    @Override // com.xiaomi.push.al.b
    public void b() {
        try {
            es.a aVarA = es.a.a(Base64.decode(dg.a(com.xiaomi.push.v.m770a(), "http://resolver.msg.xiaomi.net/psc/?t=a", (List<com.xiaomi.push.ar>) null), 10));
            if (aVarA != null) {
                this.f25655a.f1038a = aVarA;
                this.f1040a = true;
                this.f25655a.e();
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m117a("fetch config failure: " + e2.getMessage());
        }
    }

    @Override // com.xiaomi.push.al.b
    /* renamed from: c */
    public void mo328c() {
        bi.a[] aVarArr;
        this.f25655a.f1037a = null;
        if (this.f1040a) {
            synchronized (this.f25655a) {
                aVarArr = (bi.a[]) this.f25655a.f1039a.toArray(new bi.a[this.f25655a.f1039a.size()]);
            }
            for (bi.a aVar : aVarArr) {
                aVar.a(this.f25655a.f1038a);
            }
        }
    }
}
