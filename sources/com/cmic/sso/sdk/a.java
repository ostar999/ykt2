package com.cmic.sso.sdk;

import com.mobile.auth.d.a;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private ConcurrentHashMap<String, Object> f6383a;

    public a(int i2) {
        this.f6383a = new ConcurrentHashMap<>(i2);
    }

    public com.cmic.sso.sdk.d.b a() {
        com.cmic.sso.sdk.d.b bVar = (com.cmic.sso.sdk.d.b) this.f6383a.get("logBean");
        return bVar != null ? bVar : new com.cmic.sso.sdk.d.b();
    }

    public void a(com.cmic.sso.sdk.d.b bVar) {
        if (bVar != null) {
            this.f6383a.put("logBean", bVar);
        }
    }

    public void a(com.mobile.auth.d.a aVar) {
        if (aVar != null) {
            this.f6383a.put("current_config", aVar);
        }
    }

    public void a(String str, int i2) {
        if (str != null) {
            this.f6383a.put(str, Integer.valueOf(i2));
        }
    }

    public void a(String str, long j2) {
        if (str != null) {
            this.f6383a.put(str, Long.valueOf(j2));
        }
    }

    public void a(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        this.f6383a.put(str, str2);
    }

    public void a(String str, boolean z2) {
        if (str != null) {
            this.f6383a.put(str, Boolean.valueOf(z2));
        }
    }

    public void a(String str, byte[] bArr) {
        if (str == null || bArr == null) {
            return;
        }
        this.f6383a.put(str, bArr);
    }

    public byte[] a(String str) {
        if (str != null) {
            return (byte[]) this.f6383a.get(str);
        }
        return null;
    }

    public int b(String str, int i2) {
        return (str == null || !this.f6383a.containsKey(str)) ? i2 : ((Integer) this.f6383a.get(str)).intValue();
    }

    public long b(String str, long j2) {
        return (str == null || !this.f6383a.containsKey(str)) ? j2 : ((Long) this.f6383a.get(str)).longValue();
    }

    public com.mobile.auth.d.a b() {
        com.mobile.auth.d.a aVar = (com.mobile.auth.d.a) this.f6383a.get("current_config");
        if (aVar != null) {
            return aVar;
        }
        com.mobile.auth.l.c.a("UmcConfigBean为空", "请核查");
        return new a.C0197a().a();
    }

    public String b(String str) {
        return b(str, "");
    }

    public String b(String str, String str2) {
        return (str == null || !this.f6383a.containsKey(str)) ? str2 : (String) this.f6383a.get(str);
    }

    public boolean b(String str, boolean z2) {
        return (str == null || !this.f6383a.containsKey(str)) ? z2 : ((Boolean) this.f6383a.get(str)).booleanValue();
    }

    public int c(String str) {
        return b(str, 0);
    }
}
