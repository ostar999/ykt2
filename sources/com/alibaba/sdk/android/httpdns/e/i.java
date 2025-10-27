package com.alibaba.sdk.android.httpdns.e;

import com.alibaba.sdk.android.httpdns.HTTPDNSResult;
import com.alibaba.sdk.android.httpdns.RequestIpType;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class i {

    /* renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.httpdns.d.c f40a;

    /* renamed from: a, reason: collision with other field name */
    private b f41a;

    /* renamed from: a, reason: collision with other field name */
    private e f42a;

    /* renamed from: a, reason: collision with other field name */
    private h f43a;

    /* renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.httpdns.probe.b f44a;

    /* renamed from: g, reason: collision with root package name */
    private boolean f2751g = false;

    /* renamed from: a, reason: collision with root package name */
    private com.alibaba.sdk.android.httpdns.d.b f2750a = new com.alibaba.sdk.android.httpdns.d.b();

    public i(com.alibaba.sdk.android.httpdns.probe.b bVar, e eVar, h hVar, b bVar2, com.alibaba.sdk.android.httpdns.d.c cVar) {
        this.f44a = bVar;
        this.f42a = eVar;
        this.f43a = hVar;
        this.f41a = bVar2;
        this.f40a = cVar;
    }

    public HTTPDNSResult a(final String str, final RequestIpType requestIpType, Map<String, String> map, final String str2) {
        if (this.f41a.a(str)) {
            HttpDnsLog.d("request host " + str + ", which is filtered");
        } else {
            HttpDnsLog.d("request host " + str + " with type " + requestIpType + " extras : " + com.alibaba.sdk.android.httpdns.j.a.b(map) + " cacheKey " + str2);
            HTTPDNSResult hTTPDNSResultA = this.f43a.a(str, requestIpType, str2);
            StringBuilder sb = new StringBuilder();
            sb.append("host ");
            sb.append(str);
            sb.append(" result is ");
            sb.append(com.alibaba.sdk.android.httpdns.j.a.toString(hTTPDNSResultA));
            HttpDnsLog.d(sb.toString());
            if ((hTTPDNSResultA == null || hTTPDNSResultA.isExpired()) && this.f40a.m34a(str, requestIpType, str2)) {
                this.f42a.a(str, requestIpType, map, str2, new com.alibaba.sdk.android.httpdns.g.j<f>() { // from class: com.alibaba.sdk.android.httpdns.e.i.1
                    @Override // com.alibaba.sdk.android.httpdns.g.j
                    public void a(f fVar) {
                        HttpDnsLog.i("ip request for " + str + " " + requestIpType + " return " + fVar.toString());
                        i.this.f43a.a(str, requestIpType, fVar.h(), str2, fVar);
                        RequestIpType requestIpType2 = requestIpType;
                        if (requestIpType2 == RequestIpType.v4 || requestIpType2 == RequestIpType.both) {
                            i.this.f44a.a(str, fVar.getIps(), new com.alibaba.sdk.android.httpdns.probe.a() { // from class: com.alibaba.sdk.android.httpdns.e.i.1.1
                                @Override // com.alibaba.sdk.android.httpdns.probe.a
                                public void a(String str3, String[] strArr) {
                                    HttpDnsLog.i("ip probe for " + str3 + " " + requestIpType + " return " + com.alibaba.sdk.android.httpdns.j.a.a(strArr));
                                    i.this.f43a.b(str3, RequestIpType.v4, str2, strArr);
                                }
                            });
                        }
                        i.this.f40a.a(str, requestIpType, str2);
                    }

                    @Override // com.alibaba.sdk.android.httpdns.g.j
                    public void b(Throwable th) {
                        HttpDnsLog.w("ip request for " + str + " fail", th);
                        i.this.f40a.a(str, requestIpType, str2);
                    }
                });
            }
            if (hTTPDNSResultA != null && (!hTTPDNSResultA.isExpired() || this.f2751g || hTTPDNSResultA.isFromDB())) {
                HttpDnsLog.i("request host " + str + " for " + requestIpType + " and return " + hTTPDNSResultA.toString() + " immediately");
                return hTTPDNSResultA;
            }
            HttpDnsLog.i("request host " + str + " and return empty immediately");
        }
        return HTTPDNSResult.empty(str);
    }

    public HTTPDNSResult b(final String str, final RequestIpType requestIpType, Map<String, String> map, final String str2) {
        if (this.f41a.a(str)) {
            HttpDnsLog.d("request host " + str + ", which is filtered");
        } else {
            HttpDnsLog.d("request host " + str + " sync with type " + requestIpType + " extras : " + com.alibaba.sdk.android.httpdns.j.a.b(map) + " cacheKey " + str2);
            HTTPDNSResult hTTPDNSResultA = this.f43a.a(str, requestIpType, str2);
            StringBuilder sb = new StringBuilder();
            sb.append("host ");
            sb.append(str);
            sb.append(" result is ");
            sb.append(com.alibaba.sdk.android.httpdns.j.a.toString(hTTPDNSResultA));
            HttpDnsLog.d(sb.toString());
            if (hTTPDNSResultA != null && !hTTPDNSResultA.isExpired()) {
                HttpDnsLog.i("request host " + str + " for " + requestIpType + " and return " + hTTPDNSResultA.toString() + " immediately");
                return hTTPDNSResultA;
            }
            if (this.f2750a.m32a(str, requestIpType, str2)) {
                this.f42a.a(str, requestIpType, map, str2, new com.alibaba.sdk.android.httpdns.g.j<f>() { // from class: com.alibaba.sdk.android.httpdns.e.i.2
                    @Override // com.alibaba.sdk.android.httpdns.g.j
                    public void a(f fVar) {
                        HttpDnsLog.i("ip request for " + str + " " + requestIpType + " return " + fVar.toString());
                        i.this.f43a.a(str, requestIpType, fVar.h(), str2, fVar);
                        RequestIpType requestIpType2 = requestIpType;
                        if (requestIpType2 == RequestIpType.v4 || requestIpType2 == RequestIpType.both) {
                            i.this.f44a.a(str, fVar.getIps(), new com.alibaba.sdk.android.httpdns.probe.a() { // from class: com.alibaba.sdk.android.httpdns.e.i.2.1
                                @Override // com.alibaba.sdk.android.httpdns.probe.a
                                public void a(String str3, String[] strArr) {
                                    HttpDnsLog.i("ip probe for " + str3 + " " + requestIpType + " return " + com.alibaba.sdk.android.httpdns.j.a.a(strArr));
                                    i.this.f43a.b(str3, RequestIpType.v4, str2, strArr);
                                }
                            });
                        }
                        i.this.f2750a.a(str, requestIpType, str2);
                    }

                    @Override // com.alibaba.sdk.android.httpdns.g.j
                    public void b(Throwable th) {
                        HttpDnsLog.w("ip request for " + str + " fail", th);
                        i.this.f2750a.a(str, requestIpType, str2);
                    }
                });
            }
            HttpDnsLog.d("wait for request finish");
            try {
                this.f2750a.a(str, requestIpType, str2, 15L, TimeUnit.SECONDS);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            HTTPDNSResult hTTPDNSResultA2 = this.f43a.a(str, requestIpType, str2);
            if (hTTPDNSResultA2 != null && (!hTTPDNSResultA2.isExpired() || this.f2751g || hTTPDNSResultA2.isFromDB())) {
                HttpDnsLog.i("request host " + str + " for " + requestIpType + " and return " + hTTPDNSResultA2.toString() + " after request");
                return hTTPDNSResultA2;
            }
            HttpDnsLog.i("request host " + str + " and return empty after request");
        }
        return HTTPDNSResult.empty(str);
    }

    public void g(boolean z2) {
        this.f2751g = z2;
    }
}
