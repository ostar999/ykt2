package com.alibaba.sdk.android.httpdns.g;

import cn.hutool.core.text.StrPool;
import com.alibaba.sdk.android.httpdns.g.g;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import com.alibaba.sdk.android.httpdns.net.Inet64Util;

/* loaded from: classes2.dex */
public class i implements g.a {

    /* renamed from: b, reason: collision with root package name */
    private com.alibaba.sdk.android.httpdns.d.d f2792b;

    /* renamed from: j, reason: collision with root package name */
    private boolean f2793j = false;

    /* renamed from: p, reason: collision with root package name */
    private String f2794p;

    public i(com.alibaba.sdk.android.httpdns.d.d dVar) {
        this.f2792b = dVar;
    }

    private void a(d dVar, boolean z2) {
        if (this.f2793j) {
            dVar.j(this.f2794p);
            this.f2793j = false;
            if (z2) {
                return;
            }
            this.f2792b.m36a();
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(d dVar) {
        if (Inet64Util.isIPv6OnlyNetwork()) {
            this.f2794p = dVar.l();
            this.f2793j = true;
            String strE = this.f2792b.e();
            HttpDnsLog.d("origin ip is " + this.f2794p + " change to " + strE);
            StringBuilder sb = new StringBuilder();
            sb.append(StrPool.BRACKET_START);
            sb.append(strE);
            sb.append(StrPool.BRACKET_END);
            dVar.j(sb.toString());
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(d dVar, Object obj) {
        a(dVar, true);
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(d dVar, Throwable th) {
        a(dVar, false);
    }
}
