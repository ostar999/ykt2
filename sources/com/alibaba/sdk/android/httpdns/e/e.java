package com.alibaba.sdk.android.httpdns.e;

import com.alibaba.sdk.android.httpdns.RequestIpType;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private com.alibaba.sdk.android.httpdns.d.i f2736a;

    /* renamed from: a, reason: collision with other field name */
    private a f36a;

    /* renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.httpdns.h.a f37a;

    /* renamed from: b, reason: collision with root package name */
    private com.alibaba.sdk.android.httpdns.d.d f2737b;

    /* renamed from: c, reason: collision with root package name */
    private HashMap<String, String> f2738c = new HashMap<>();

    public e(com.alibaba.sdk.android.httpdns.d.d dVar, com.alibaba.sdk.android.httpdns.h.a aVar, com.alibaba.sdk.android.httpdns.d.i iVar) {
        this.f2737b = dVar;
        this.f37a = aVar;
        this.f36a = new a(aVar);
        this.f2736a = iVar;
    }

    public void a(String str, RequestIpType requestIpType, Map<String, String> map, String str2, com.alibaba.sdk.android.httpdns.g.j<f> jVar) {
        com.alibaba.sdk.android.httpdns.g.d dVarA = d.a(this.f2737b, str, requestIpType, map, str2, this.f2738c, this.f2736a);
        HttpDnsLog.d("start async ip request for " + str + " " + requestIpType);
        this.f36a.a().a(this.f2737b, dVarA, jVar);
    }

    public void a(ArrayList<String> arrayList, RequestIpType requestIpType, com.alibaba.sdk.android.httpdns.g.j<k> jVar) {
        com.alibaba.sdk.android.httpdns.g.d dVarA = d.a(this.f2737b, arrayList, requestIpType, this.f2736a);
        HttpDnsLog.d("start resolve hosts async for " + arrayList.toString() + " " + requestIpType);
        this.f2737b.m35a().execute(new com.alibaba.sdk.android.httpdns.g.f(new com.alibaba.sdk.android.httpdns.g.l(new com.alibaba.sdk.android.httpdns.g.g(new com.alibaba.sdk.android.httpdns.g.g(new com.alibaba.sdk.android.httpdns.g.g(new com.alibaba.sdk.android.httpdns.g.c(dVarA, new l()), new com.alibaba.sdk.android.httpdns.g.e(com.alibaba.sdk.android.httpdns.f.b.a(this.f2737b.getAccountId()))), new com.alibaba.sdk.android.httpdns.g.i(this.f2737b)), new n(this.f2737b, this.f37a, this.f36a)), 1), jVar));
    }

    public void clearSdnsGlobalParams() {
        this.f2738c.clear();
    }

    public void e() {
        this.f36a.reset();
    }

    public void setSdnsGlobalParams(Map<String, String> map) {
        this.f2738c.clear();
        if (map != null) {
            this.f2738c.putAll(map);
        }
    }
}
