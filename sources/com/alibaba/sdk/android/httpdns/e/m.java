package com.alibaba.sdk.android.httpdns.e;

import com.alibaba.sdk.android.httpdns.HTTPDNSResult;
import com.alibaba.sdk.android.httpdns.RequestIpType;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class m {

    /* renamed from: a, reason: collision with root package name */
    private com.alibaba.sdk.android.httpdns.d.c f2765a;

    /* renamed from: a, reason: collision with other field name */
    private b f46a;

    /* renamed from: a, reason: collision with other field name */
    private e f47a;

    /* renamed from: a, reason: collision with other field name */
    private h f48a;

    /* renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.httpdns.probe.b f49a;

    public m(h hVar, e eVar, com.alibaba.sdk.android.httpdns.probe.b bVar, b bVar2, com.alibaba.sdk.android.httpdns.d.c cVar) {
        this.f48a = hVar;
        this.f47a = eVar;
        this.f49a = bVar;
        this.f46a = bVar2;
        this.f2765a = cVar;
    }

    public void a(ArrayList<String> arrayList, final RequestIpType requestIpType) {
        HttpDnsLog.d("resolve host " + arrayList.toString() + " " + requestIpType);
        ArrayList arrayList2 = new ArrayList(arrayList);
        int size = (arrayList.size() / 5) + 1;
        for (int i2 = 0; i2 < size; i2++) {
            final ArrayList<String> arrayList3 = new ArrayList<>();
            while (arrayList3.size() < 5 && arrayList2.size() > 0) {
                String str = (String) arrayList2.remove(0);
                HTTPDNSResult hTTPDNSResultA = this.f48a.a(str, requestIpType, null);
                if (!com.alibaba.sdk.android.httpdns.j.a.m57d(str) || com.alibaba.sdk.android.httpdns.j.a.e(str) || this.f46a.a(str) || !((hTTPDNSResultA == null || hTTPDNSResultA.isExpired()) && this.f2765a.m33a(str, requestIpType))) {
                    HttpDnsLog.d("resolve ignore host " + str);
                } else {
                    arrayList3.add(str);
                }
            }
            if (arrayList3.size() > 0) {
                HttpDnsLog.i("resolve host " + arrayList3.toString() + " " + requestIpType);
                this.f47a.a(arrayList3, requestIpType, new com.alibaba.sdk.android.httpdns.g.j<k>() { // from class: com.alibaba.sdk.android.httpdns.e.m.1
                    @Override // com.alibaba.sdk.android.httpdns.g.j
                    public void a(k kVar) {
                        HttpDnsLog.d("resolve hosts for " + arrayList3.toString() + " " + requestIpType + " return " + kVar.toString());
                        m.this.f48a.a(requestIpType, kVar);
                        RequestIpType requestIpType2 = requestIpType;
                        if (requestIpType2 == RequestIpType.v4 || requestIpType2 == RequestIpType.both) {
                            for (String str2 : kVar.b()) {
                                m.this.f49a.a(str2, kVar.m46a(str2).getIps(), new com.alibaba.sdk.android.httpdns.probe.a() { // from class: com.alibaba.sdk.android.httpdns.e.m.1.1
                                    @Override // com.alibaba.sdk.android.httpdns.probe.a
                                    public void a(String str3, String[] strArr) {
                                        m.this.f48a.b(str3, RequestIpType.v4, null, strArr);
                                    }
                                });
                            }
                        }
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            m.this.f2765a.a((String) it.next(), requestIpType);
                        }
                    }

                    @Override // com.alibaba.sdk.android.httpdns.g.j
                    public void b(Throwable th) {
                        HttpDnsLog.w("resolve hosts for " + arrayList3.toString() + " fail", th);
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            m.this.f2765a.a((String) it.next(), requestIpType);
                        }
                    }
                });
            }
        }
    }
}
