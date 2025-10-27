package com.alibaba.sdk.android.httpdns.d;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import com.alibaba.sdk.android.crashdefend.CrashDefendApi;
import com.alibaba.sdk.android.crashdefend.CrashDefendCallback;
import com.alibaba.sdk.android.httpdns.DegradationFilter;
import com.alibaba.sdk.android.httpdns.HTTPDNSResult;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.alibaba.sdk.android.httpdns.ILogger;
import com.alibaba.sdk.android.httpdns.RequestIpType;
import com.alibaba.sdk.android.httpdns.SyncService;
import com.alibaba.sdk.android.httpdns.e.m;
import com.alibaba.sdk.android.httpdns.h.a;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import com.alibaba.sdk.android.httpdns.net.Inet64Util;
import com.alibaba.sdk.android.httpdns.net.c;
import com.alibaba.sdk.android.httpdns.probe.IPProbeItem;
import com.alibaba.sdk.android.sender.AlicloudSender;
import com.alibaba.sdk.android.sender.SdkInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class g implements HttpDnsService, SyncService, a.InterfaceC0021a, c.b {

    /* renamed from: a, reason: collision with root package name */
    private c f2720a = new c();

    /* renamed from: a, reason: collision with other field name */
    private i f26a;

    /* renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.httpdns.e.b f27a;

    /* renamed from: a, reason: collision with other field name */
    protected com.alibaba.sdk.android.httpdns.e.e f28a;

    /* renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.httpdns.e.h f29a;

    /* renamed from: a, reason: collision with other field name */
    protected com.alibaba.sdk.android.httpdns.e.i f30a;

    /* renamed from: a, reason: collision with other field name */
    protected m f31a;

    /* renamed from: a, reason: collision with other field name */
    protected com.alibaba.sdk.android.httpdns.h.a f32a;

    /* renamed from: a, reason: collision with other field name */
    protected com.alibaba.sdk.android.httpdns.probe.b f33a;

    /* renamed from: b, reason: collision with root package name */
    protected d f2721b;

    /* renamed from: d, reason: collision with root package name */
    private boolean f2722d;

    public g(Context context, String str, String str2) {
        try {
            d dVar = new d(context, str);
            this.f2721b = dVar;
            c(context, dVar);
            if (!this.f2721b.isEnabled()) {
                HttpDnsLog.w("init fail, crashdefend");
                return;
            }
            com.alibaba.sdk.android.httpdns.net.c.a().m62a(context);
            Inet64Util.init(com.alibaba.sdk.android.httpdns.net.c.a());
            this.f27a = new com.alibaba.sdk.android.httpdns.e.b();
            this.f26a = new i(str2);
            com.alibaba.sdk.android.httpdns.probe.b bVar = new com.alibaba.sdk.android.httpdns.probe.b(this.f2721b);
            this.f33a = bVar;
            this.f29a = new com.alibaba.sdk.android.httpdns.e.h(this.f2721b, bVar);
            com.alibaba.sdk.android.httpdns.h.a aVar = new com.alibaba.sdk.android.httpdns.h.a(this.f2721b, this);
            this.f32a = aVar;
            com.alibaba.sdk.android.httpdns.e.e eVar = new com.alibaba.sdk.android.httpdns.e.e(this.f2721b, aVar, this.f26a);
            this.f28a = eVar;
            this.f30a = new com.alibaba.sdk.android.httpdns.e.i(this.f33a, eVar, this.f29a, this.f27a, this.f2720a);
            this.f31a = new m(this.f29a, this.f28a, this.f33a, this.f27a, this.f2720a);
            com.alibaba.sdk.android.httpdns.net.c.a().a(this);
            if (this.f2721b.m37a()) {
                this.f32a.f();
            }
            com.alibaba.sdk.android.httpdns.f.b.a(context);
            com.alibaba.sdk.android.httpdns.f.b.a(str).g(str);
            a(context, str);
            a(context, str, this.f2721b);
            HttpDnsLog.d("httpdns service is inited " + str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(Context context, String str) {
        try {
            HashMap map = new HashMap();
            map.put("accountId", str);
            SdkInfo sdkInfo = new SdkInfo();
            sdkInfo.setSdkId("httpdns");
            sdkInfo.setSdkVersion("2.0.4");
            sdkInfo.setExt(map);
            if (context.getApplicationContext() instanceof Application) {
                AlicloudSender.asyncSend((Application) context.getApplicationContext(), sdkInfo);
            } else {
                AlicloudSender.asyncSend(context.getApplicationContext(), sdkInfo);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void a(Context context, String str, d dVar) {
        com.alibaba.sdk.android.httpdns.a.a.a(context, str, dVar);
    }

    public void c(Context context, final d dVar) {
        CrashDefendApi.registerCrashDefendSdk(context, "httpdns", "2.0.4", 2, 7, new CrashDefendCallback() { // from class: com.alibaba.sdk.android.httpdns.d.g.1
            @Override // com.alibaba.sdk.android.crashdefend.CrashDefendCallback
            public void onSdkClosed(int i2) {
                dVar.b(true);
                HttpDnsLog.e("sdk will not run any more");
            }

            @Override // com.alibaba.sdk.android.crashdefend.CrashDefendCallback
            public void onSdkStart(int i2, int i3, int i4) {
                dVar.b(false);
            }

            @Override // com.alibaba.sdk.android.crashdefend.CrashDefendCallback
            public void onSdkStop(int i2, int i3, int i4, long j2) {
                dVar.b(true);
                HttpDnsLog.w("sdk is not safe to run");
            }
        });
    }

    public void c(String str) {
        if (this.f2721b.isEnabled()) {
            if (str == null || str.equals("")) {
                HttpDnsLog.e("set empty secret!?");
            }
            this.f26a.c(str);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void cleanHostCache(ArrayList<String> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            this.f29a.clear();
        } else {
            this.f29a.a(arrayList);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void clearSdnsGlobalParams() {
        if (this.f2721b.isEnabled()) {
            this.f28a.clearSdnsGlobalParams();
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.h.a.InterfaceC0021a
    public void e(boolean z2) {
        if (this.f2721b.isEnabled()) {
            if (z2) {
                this.f29a.clear();
            }
            this.f28a.e();
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.net64.Net64Service
    public void enableIPv6(boolean z2) {
    }

    @Override // com.alibaba.sdk.android.httpdns.net.c.b
    public void f(String str) {
        if (this.f2721b.isEnabled()) {
            this.f2721b.m35a().execute(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.g.2
                @Override // java.lang.Runnable
                public void run() {
                    HashMap<String, RequestIpType> mapA = g.this.f29a.a();
                    HttpDnsLog.d("network change, clean record");
                    g.this.f29a.clear();
                    if (g.this.f2722d && g.this.f2721b.isEnabled()) {
                        ArrayList<String> arrayList = new ArrayList<>();
                        ArrayList<String> arrayList2 = new ArrayList<>();
                        ArrayList<String> arrayList3 = new ArrayList<>();
                        for (Map.Entry<String, RequestIpType> entry : mapA.entrySet()) {
                            if (entry.getValue() == RequestIpType.v4) {
                                arrayList.add(entry.getKey());
                            } else {
                                RequestIpType value = entry.getValue();
                                RequestIpType requestIpType = RequestIpType.v6;
                                String key = entry.getKey();
                                if (value == requestIpType) {
                                    arrayList2.add(key);
                                } else {
                                    arrayList3.add(key);
                                }
                            }
                        }
                        if (arrayList.size() > 0) {
                            g.this.f31a.a(arrayList, RequestIpType.v4);
                        }
                        if (arrayList2.size() > 0) {
                            g.this.f31a.a(arrayList2, RequestIpType.v6);
                        }
                        if (arrayList3.size() > 0) {
                            g.this.f31a.a(arrayList3, RequestIpType.both);
                        }
                        if (arrayList.size() > 0 || arrayList2.size() > 0 || arrayList3.size() > 0) {
                            HttpDnsLog.d("network change, resolve hosts");
                        }
                    }
                }
            });
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public HTTPDNSResult getAllByHostAsync(String str) {
        if (!this.f2721b.isEnabled()) {
            HttpDnsLog.i("service is disabled");
            return HTTPDNSResult.empty(str);
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.m57d(str)) {
            HttpDnsLog.i("host is invalid. " + str);
            return HTTPDNSResult.empty(str);
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.e(str)) {
            return this.f30a.a(str, RequestIpType.both, null, null);
        }
        HttpDnsLog.i("host is ip. " + str);
        return HTTPDNSResult.empty(str);
    }

    @Override // com.alibaba.sdk.android.httpdns.SyncService
    public HTTPDNSResult getByHost(String str, RequestIpType requestIpType) {
        if (!this.f2721b.isEnabled()) {
            HttpDnsLog.i("service is disabled");
            return HTTPDNSResult.empty(str);
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.m57d(str)) {
            HttpDnsLog.i("host is invalid. " + str);
            return HTTPDNSResult.empty(str);
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.e(str)) {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                return this.f30a.b(str, requestIpType, null, null);
            }
            HttpDnsLog.d("request in main thread, use async request");
            return this.f30a.a(str, requestIpType, null, null);
        }
        HttpDnsLog.i("host is ip. " + str);
        return HTTPDNSResult.empty(str);
    }

    @Override // com.alibaba.sdk.android.httpdns.net64.Net64Service
    public String getIPv6ByHostAsync(String str) {
        StringBuilder sb;
        String str2;
        String string;
        if (this.f2721b.isEnabled()) {
            if (!com.alibaba.sdk.android.httpdns.j.a.m57d(str)) {
                sb = new StringBuilder();
                str2 = "host is invalid. ";
            } else {
                if (!com.alibaba.sdk.android.httpdns.j.a.e(str)) {
                    String[] iPv6sByHostAsync = getIPv6sByHostAsync(str);
                    if (iPv6sByHostAsync == null || iPv6sByHostAsync.length == 0) {
                        return null;
                    }
                    return iPv6sByHostAsync[0];
                }
                sb = new StringBuilder();
                str2 = "host is ip. ";
            }
            sb.append(str2);
            sb.append(str);
            string = sb.toString();
        } else {
            string = "service is disabled";
        }
        HttpDnsLog.i(string);
        return null;
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String[] getIPv6sByHostAsync(String str) {
        if (!this.f2721b.isEnabled()) {
            HttpDnsLog.i("service is disabled");
            return new String[0];
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.m57d(str)) {
            HttpDnsLog.i("host is invalid. " + str);
            return new String[0];
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.e(str)) {
            return this.f30a.a(str, RequestIpType.v6, null, null).getIpv6s();
        }
        HttpDnsLog.i("host is ip. " + str);
        return new String[0];
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String getIpByHostAsync(String str) {
        StringBuilder sb;
        String str2;
        String string;
        if (this.f2721b.isEnabled()) {
            if (!com.alibaba.sdk.android.httpdns.j.a.m57d(str)) {
                sb = new StringBuilder();
                str2 = "host is invalid. ";
            } else {
                if (!com.alibaba.sdk.android.httpdns.j.a.e(str)) {
                    String[] ipsByHostAsync = getIpsByHostAsync(str);
                    if (ipsByHostAsync == null || ipsByHostAsync.length == 0) {
                        return null;
                    }
                    return ipsByHostAsync[0];
                }
                sb = new StringBuilder();
                str2 = "host is ip. ";
            }
            sb.append(str2);
            sb.append(str);
            string = sb.toString();
        } else {
            string = "service is disabled";
        }
        HttpDnsLog.i(string);
        return null;
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public HTTPDNSResult getIpsByHostAsync(String str, RequestIpType requestIpType, Map<String, String> map, String str2) {
        if (!this.f2721b.isEnabled()) {
            HttpDnsLog.i("service is disabled");
            return HTTPDNSResult.empty(str);
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.m57d(str)) {
            HttpDnsLog.i("host is invalid. " + str);
            return HTTPDNSResult.empty(str);
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.e(str)) {
            return this.f30a.a(str, requestIpType, map, str2);
        }
        HttpDnsLog.i("host is ip. " + str);
        return HTTPDNSResult.empty(str);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public HTTPDNSResult getIpsByHostAsync(String str, Map<String, String> map, String str2) {
        if (!this.f2721b.isEnabled()) {
            HttpDnsLog.i("service is disabled");
            return HTTPDNSResult.empty(str);
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.m57d(str)) {
            HttpDnsLog.i("host is invalid. " + str);
            return HTTPDNSResult.empty(str);
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.e(str)) {
            return this.f30a.a(str, RequestIpType.v4, map, str2);
        }
        HttpDnsLog.i("host is ip. " + str);
        return HTTPDNSResult.empty(str);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String[] getIpsByHostAsync(String str) {
        if (!this.f2721b.isEnabled()) {
            HttpDnsLog.i("service is disabled");
            return new String[0];
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.m57d(str)) {
            HttpDnsLog.i("host is invalid. " + str);
            return new String[0];
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.e(str)) {
            return this.f30a.a(str, RequestIpType.v4, null, null).getIps();
        }
        HttpDnsLog.i("host is ip. " + str);
        return new String[0];
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String getSessionId() {
        return com.alibaba.sdk.android.httpdns.i.a.a().getSessionId();
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setAuthCurrentTime(long j2) {
        if (this.f2721b.isEnabled()) {
            this.f26a.c(j2);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setCachedIPEnabled(boolean z2) {
        if (this.f2721b.isEnabled()) {
            setCachedIPEnabled(z2, false);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setCachedIPEnabled(boolean z2, boolean z3) {
        if (this.f2721b.isEnabled()) {
            this.f29a.setCachedIPEnabled(z2, z3);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setDegradationFilter(DegradationFilter degradationFilter) {
        if (this.f2721b.isEnabled()) {
            this.f27a.a(degradationFilter);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setExpiredIPEnabled(boolean z2) {
        if (this.f2721b.isEnabled()) {
            this.f30a.g(z2);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setHTTPSRequestEnabled(boolean z2) {
        if (this.f2721b.isEnabled()) {
            this.f2721b.setHTTPSRequestEnabled(z2);
            if (z2 && this.f2721b.m37a()) {
                this.f32a.f();
            }
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setIPProbeList(List<IPProbeItem> list) {
        if (this.f2721b.isEnabled()) {
            this.f33a.c(list);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setLogEnabled(boolean z2) {
        if (this.f2721b.isEnabled()) {
            System.out.println("------> log control " + z2 + " account " + this.f2721b.getAccountId());
            HttpDnsLog.enable(z2);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setLogger(ILogger iLogger) {
        HttpDnsLog.setLogger(iLogger);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveAfterNetworkChanged(boolean z2) {
        if (this.f2721b.isEnabled()) {
            this.f2722d = z2;
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveHosts(ArrayList<String> arrayList) {
        if (!this.f2721b.isEnabled()) {
            HttpDnsLog.i("service is disabled");
        } else if (arrayList == null || arrayList.size() == 0) {
            HttpDnsLog.i("setPreResolveHosts empty list");
        } else {
            setPreResolveHosts(arrayList, RequestIpType.v4);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveHosts(ArrayList<String> arrayList, RequestIpType requestIpType) {
        if (!this.f2721b.isEnabled()) {
            HttpDnsLog.i("service is disabled");
        } else if (arrayList == null || arrayList.size() == 0) {
            HttpDnsLog.i("setPreResolveHosts empty list");
        } else {
            this.f31a.a(arrayList, requestIpType);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setRegion(String str) {
        if (this.f2721b.isEnabled()) {
            this.f32a.b(str, false);
        } else {
            HttpDnsLog.i("service is disabled");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setSdnsGlobalParams(Map<String, String> map) {
        if (this.f2721b.isEnabled()) {
            this.f28a.setSdnsGlobalParams(map);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setTimeoutInterval(int i2) {
        if (this.f2721b.isEnabled()) {
            this.f2721b.setTimeout(i2);
        }
    }
}
