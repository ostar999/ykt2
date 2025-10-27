package com.alibaba.sdk.android.httpdns;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class HTTPDNSResult {
    boolean expired;
    Map<String, String> extra;
    boolean fromDB;
    String host;
    String[] ips;
    String[] ipv6s;

    public HTTPDNSResult(String str, String[] strArr, String[] strArr2, Map<String, String> map, boolean z2, boolean z3) {
        this.host = str;
        this.ips = strArr;
        this.ipv6s = strArr2;
        this.extra = map;
        this.expired = z2;
        this.fromDB = z3;
    }

    public static HTTPDNSResult empty(String str) {
        return new HTTPDNSResult(str, new String[0], new String[0], new HashMap(), false, false);
    }

    public Map<String, String> getExtras() {
        return this.extra;
    }

    public String getHost() {
        return this.host;
    }

    public String[] getIps() {
        return this.ips;
    }

    public String[] getIpv6s() {
        return this.ipv6s;
    }

    public boolean isExpired() {
        return this.expired;
    }

    public boolean isFromDB() {
        return this.fromDB;
    }

    public String toString() {
        return "host:" + this.host + ", ips:" + Arrays.toString(this.ips) + ", ipv6s:" + Arrays.toString(this.ipv6s) + ", extras:" + this.extra + ", expired:" + this.expired + ", fromDB:" + this.fromDB;
    }
}
