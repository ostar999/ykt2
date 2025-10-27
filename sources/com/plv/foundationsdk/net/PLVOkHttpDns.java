package com.plv.foundationsdk.net;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.plv.foundationsdk.net.dns.PLVAliHttpDnsStorage;
import com.plv.foundationsdk.utils.PLVAppUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.Dns;

/* loaded from: classes4.dex */
public class PLVOkHttpDns implements IPLVHttpDns, Dns {
    private static volatile PLVOkHttpDns instance = null;
    private static boolean isEnable = true;
    private static boolean isEnableIPV6 = false;
    private final String TAG;
    private HttpDnsService httpdns;

    public PLVOkHttpDns(Context context) {
        String simpleName = getClass().getSimpleName();
        this.TAG = simpleName;
        Log.i(simpleName, "PLVOkHttpDns init enableHttpdns " + isEnable + " enableIPV6 " + isEnableIPV6);
        PLVAliHttpDnsStorage pLVAliHttpDnsStorage = PLVAliHttpDnsStorage.INSTANCE;
        HttpDnsService service = HttpDns.getService(context, pLVAliHttpDnsStorage.getAccountId(), pLVAliHttpDnsStorage.getKey());
        this.httpdns = service;
        service.setPreResolveAfterNetworkChanged(true);
        this.httpdns.setExpiredIPEnabled(true);
        this.httpdns.setHTTPSRequestEnabled(true);
        setPreResolveHosts(new ArrayList<>(Arrays.asList("pull-c1.videocc.net", "pull2.videocc.net", "oss-live-2.videocc.net", "chat.polyv.net", "api.polyv.net", "live.polyv.net", "livejson.polyv.net", "apichat.polyv.net", "player.polyv.net")));
    }

    public static void enableHttpDns(boolean z2) {
        isEnable = z2;
    }

    public static void enableHttpDnsIPV6(boolean z2) {
        isEnableIPV6 = z2;
    }

    public static PLVOkHttpDns getInstance() {
        if (instance == null) {
            synchronized (PLVOkHttpDns.class) {
                if (instance == null) {
                    instance = new PLVOkHttpDns(PLVAppUtils.getApp());
                }
            }
        }
        return instance;
    }

    public static boolean isEnableHttpDns() {
        return isEnable;
    }

    public static boolean isEnableHttpDnsIPV6() {
        return isEnableIPV6;
    }

    @Override // com.plv.foundationsdk.net.IPLVHttpDns
    @Nullable
    public String getIp(String str) {
        return (!isEnable || isEnableIPV6) ? "" : this.httpdns.getIpByHostAsync(str);
    }

    @Override // com.plv.foundationsdk.net.IPLVHttpDns
    public boolean isEnableIPV6() {
        return isEnableIPV6;
    }

    @Override // com.plv.foundationsdk.net.IPLVHttpDns
    public boolean isEnabled() {
        return isEnable;
    }

    @Override // okhttp3.Dns
    public List<InetAddress> lookup(String str) throws UnknownHostException {
        if (!isEnable || isEnableIPV6) {
            return Dns.SYSTEM.lookup(str);
        }
        String ipByHostAsync = this.httpdns.getIpByHostAsync(str);
        return ipByHostAsync != null ? Arrays.asList(InetAddress.getAllByName(ipByHostAsync)) : Dns.SYSTEM.lookup(str);
    }

    @Override // com.plv.foundationsdk.net.IPLVHttpDns
    public void setEnable(boolean z2) {
        isEnable = z2;
    }

    @Override // com.plv.foundationsdk.net.IPLVHttpDns
    public void setEnableIPV6(boolean z2) {
        isEnableIPV6 = z2;
    }

    @Override // com.plv.foundationsdk.net.IPLVHttpDns
    public void setPreResolveHosts(ArrayList<String> arrayList) {
        this.httpdns.setPreResolveHosts(arrayList);
    }
}
