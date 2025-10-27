package com.alibaba.sdk.android.httpdns.h;

import android.text.TextUtils;
import com.alibaba.sdk.android.httpdns.g.g;
import com.alibaba.sdk.android.httpdns.g.i;
import com.alibaba.sdk.android.httpdns.g.j;
import com.alibaba.sdk.android.httpdns.g.k;
import com.alibaba.sdk.android.httpdns.g.l;

/* loaded from: classes2.dex */
public class f {
    public static void a(com.alibaba.sdk.android.httpdns.d.d dVar, String str, j<e> jVar) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        sb.append(dVar.getAccountId());
        sb.append("/ss?platform=android&sdk_version=");
        sb.append("2.0.4");
        if (TextUtils.isEmpty(str)) {
            str2 = "";
        } else {
            str2 = "&region=" + str + com.alibaba.sdk.android.httpdns.e.d.f() + com.alibaba.sdk.android.httpdns.e.d.g();
        }
        sb.append(str2);
        dVar.m35a().execute(new com.alibaba.sdk.android.httpdns.g.f(new l(new g(new g(new g(new com.alibaba.sdk.android.httpdns.g.c(new com.alibaba.sdk.android.httpdns.g.d(dVar.c(), dVar.d(), dVar.getPort(), sb.toString(), dVar.getTimeout()), new k<e>() { // from class: com.alibaba.sdk.android.httpdns.h.f.1
            @Override // com.alibaba.sdk.android.httpdns.g.k
            /* renamed from: b, reason: merged with bridge method [inline-methods] */
            public e a(String str3) {
                return e.a(str3);
            }
        }), new com.alibaba.sdk.android.httpdns.g.e(com.alibaba.sdk.android.httpdns.f.b.a(dVar.getAccountId()))), new i(dVar)), new d(dVar)), (dVar.m38a().length + dVar.b()) - 1), jVar));
    }
}
