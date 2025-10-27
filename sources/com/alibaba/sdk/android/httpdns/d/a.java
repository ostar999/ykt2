package com.alibaba.sdk.android.httpdns.d;

import com.alibaba.sdk.android.httpdns.DegradationFilter;
import com.alibaba.sdk.android.httpdns.HTTPDNSResult;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.alibaba.sdk.android.httpdns.ILogger;
import com.alibaba.sdk.android.httpdns.RequestIpType;
import com.alibaba.sdk.android.httpdns.SyncService;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import com.alibaba.sdk.android.httpdns.probe.IPProbeItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class a implements HttpDnsService, SyncService {
    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void cleanHostCache(ArrayList<String> arrayList) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void clearSdnsGlobalParams() {
    }

    @Override // com.alibaba.sdk.android.httpdns.net64.Net64Service
    public void enableIPv6(boolean z2) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public HTTPDNSResult getAllByHostAsync(String str) {
        HttpDnsLog.w("init error");
        return HTTPDNSResult.empty(str);
    }

    @Override // com.alibaba.sdk.android.httpdns.SyncService
    public HTTPDNSResult getByHost(String str, RequestIpType requestIpType) {
        HttpDnsLog.w("init error");
        return HTTPDNSResult.empty(str);
    }

    @Override // com.alibaba.sdk.android.httpdns.net64.Net64Service
    public String getIPv6ByHostAsync(String str) {
        HttpDnsLog.w("init error");
        return null;
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String[] getIPv6sByHostAsync(String str) {
        HttpDnsLog.w("init error");
        return new String[0];
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String getIpByHostAsync(String str) {
        HttpDnsLog.w("init error");
        return null;
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public HTTPDNSResult getIpsByHostAsync(String str, RequestIpType requestIpType, Map<String, String> map, String str2) {
        HttpDnsLog.w("init error");
        return HTTPDNSResult.empty(str);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public HTTPDNSResult getIpsByHostAsync(String str, Map<String, String> map, String str2) {
        HttpDnsLog.w("init error");
        return HTTPDNSResult.empty(str);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String[] getIpsByHostAsync(String str) {
        HttpDnsLog.w("init error");
        return new String[0];
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String getSessionId() {
        return null;
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setAuthCurrentTime(long j2) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setCachedIPEnabled(boolean z2) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setCachedIPEnabled(boolean z2, boolean z3) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setDegradationFilter(DegradationFilter degradationFilter) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setExpiredIPEnabled(boolean z2) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setHTTPSRequestEnabled(boolean z2) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setIPProbeList(List<IPProbeItem> list) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setLogEnabled(boolean z2) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setLogger(ILogger iLogger) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveAfterNetworkChanged(boolean z2) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveHosts(ArrayList<String> arrayList) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveHosts(ArrayList<String> arrayList, RequestIpType requestIpType) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setRegion(String str) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setSdnsGlobalParams(Map<String, String> map) {
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setTimeoutInterval(int i2) {
    }
}
