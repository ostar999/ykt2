package com.alibaba.sdk.android.httpdns.b;

import cn.hutool.core.text.CharPool;
import com.alibaba.sdk.android.httpdns.RequestIpType;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private int f2706a;

    /* renamed from: a, reason: collision with other field name */
    private long f12a;

    /* renamed from: b, reason: collision with root package name */
    private String f2707b;

    /* renamed from: c, reason: collision with root package name */
    private String f2708c;
    private String host;
    private String[] ips;
    private int type;
    private long id = -1;
    private boolean fromDB = false;

    public static a a(String str, RequestIpType requestIpType, String str2, String str3, String[] strArr, int i2) {
        a aVar = new a();
        aVar.host = str;
        aVar.type = requestIpType.ordinal();
        aVar.ips = strArr;
        aVar.f2706a = i2;
        aVar.f12a = System.currentTimeMillis();
        aVar.f2707b = str2;
        aVar.f2708c = str3;
        return aVar;
    }

    public int a() {
        return this.f2706a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m30a() {
        return this.f12a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m31a() {
        return this.f2708c;
    }

    public void a(int i2) {
        this.f2706a = i2;
    }

    public void a(long j2) {
        this.f12a = j2;
    }

    public void a(String str) {
        this.f2707b = str;
    }

    public void a(boolean z2) {
        this.fromDB = z2;
    }

    public void a(String[] strArr) {
        this.ips = strArr;
    }

    public void b(long j2) {
        this.id = j2;
    }

    public void b(String str) {
        this.f2708c = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        a aVar = (a) obj;
        return this.id == aVar.id && this.type == aVar.type && this.f2706a == aVar.f2706a && this.f12a == aVar.f12a && com.alibaba.sdk.android.httpdns.j.a.equals(this.host, aVar.host) && Arrays.equals(this.ips, aVar.ips) && com.alibaba.sdk.android.httpdns.j.a.equals(this.f2707b, aVar.f2707b) && com.alibaba.sdk.android.httpdns.j.a.equals(this.f2708c, aVar.f2708c);
    }

    public String getExtra() {
        return this.f2707b;
    }

    public String getHost() {
        return this.host;
    }

    public long getId() {
        return this.id;
    }

    public String[] getIps() {
        return this.ips;
    }

    public int getType() {
        return this.type;
    }

    public int hashCode() {
        return (Arrays.hashCode(new Object[]{Long.valueOf(this.id), this.host, Integer.valueOf(this.type), Integer.valueOf(this.f2706a), Long.valueOf(this.f12a), this.f2707b, this.f2708c}) * 31) + Arrays.hashCode(this.ips);
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > this.f12a + ((long) (this.f2706a * 1000));
    }

    public boolean isFromDB() {
        return this.fromDB;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setType(int i2) {
        this.type = i2;
    }

    public String toString() {
        return "HostRecord{id=" + this.id + ", host='" + this.host + CharPool.SINGLE_QUOTE + ", ips=" + Arrays.toString(this.ips) + ", type=" + this.type + ", ttl=" + this.f2706a + ", queryTime=" + this.f12a + ", extra='" + this.f2707b + CharPool.SINGLE_QUOTE + ", cacheKey='" + this.f2708c + CharPool.SINGLE_QUOTE + ", fromDB=" + this.fromDB + '}';
    }
}
