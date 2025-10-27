package com.alibaba.sdk.android.httpdns.d;

import android.content.Context;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class f {

    /* renamed from: a, reason: collision with other field name */
    private e f25a;

    /* renamed from: a, reason: collision with root package name */
    private a f2718a = new a();

    /* renamed from: b, reason: collision with root package name */
    private HashMap<String, HttpDnsService> f2719b = new HashMap<>();

    public f(e eVar) {
        this.f25a = eVar;
    }

    public HttpDnsService b(Context context, String str, String str2) {
        String str3;
        if (context == null) {
            str3 = "init httpdns with null context!!";
        } else {
            if (str != null && !str.equals("")) {
                HttpDnsService httpDnsService = this.f2719b.get(str);
                if (httpDnsService == null) {
                    HttpDnsService httpDnsServiceA = this.f25a.a(context, str, str2);
                    this.f2719b.put(str, httpDnsServiceA);
                    return httpDnsServiceA;
                }
                if (!(httpDnsService instanceof g)) {
                    return httpDnsService;
                }
                ((g) httpDnsService).c(str2);
                return httpDnsService;
            }
            str3 = "init httpdns with emtpy account!!";
        }
        HttpDnsLog.e(str3);
        return this.f2718a;
    }
}
