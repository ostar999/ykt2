package com.alibaba.sdk.android.httpdns;

import com.alibaba.sdk.android.httpdns.net64.Net64Service;
import com.alibaba.sdk.android.httpdns.probe.IPProbeItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public interface HttpDnsService extends Net64Service {
    void cleanHostCache(ArrayList<String> arrayList);

    void clearSdnsGlobalParams();

    HTTPDNSResult getAllByHostAsync(String str);

    String[] getIPv6sByHostAsync(String str);

    String getIpByHostAsync(String str);

    HTTPDNSResult getIpsByHostAsync(String str, RequestIpType requestIpType, Map<String, String> map, String str2);

    HTTPDNSResult getIpsByHostAsync(String str, Map<String, String> map, String str2);

    String[] getIpsByHostAsync(String str);

    String getSessionId();

    void setAuthCurrentTime(long j2);

    void setCachedIPEnabled(boolean z2);

    void setCachedIPEnabled(boolean z2, boolean z3);

    void setDegradationFilter(DegradationFilter degradationFilter);

    void setExpiredIPEnabled(boolean z2);

    void setHTTPSRequestEnabled(boolean z2);

    void setIPProbeList(List<IPProbeItem> list);

    @Deprecated
    void setLogEnabled(boolean z2);

    @Deprecated
    void setLogger(ILogger iLogger);

    void setPreResolveAfterNetworkChanged(boolean z2);

    void setPreResolveHosts(ArrayList<String> arrayList);

    void setPreResolveHosts(ArrayList<String> arrayList, RequestIpType requestIpType);

    void setRegion(String str);

    void setSdnsGlobalParams(Map<String, String> map);

    void setTimeoutInterval(int i2);
}
