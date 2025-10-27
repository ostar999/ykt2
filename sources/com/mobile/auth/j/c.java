package com.mobile.auth.j;

import android.net.Network;
import com.mobile.auth.BuildConfig;
import com.mobile.auth.i.g;
import com.mobile.auth.l.e;
import com.psychiatrygarden.utils.MimeTypes;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    String f10398a;

    /* renamed from: b, reason: collision with root package name */
    private final String f10399b;

    /* renamed from: c, reason: collision with root package name */
    private final Map<String, String> f10400c;

    /* renamed from: d, reason: collision with root package name */
    private final String f10401d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f10402e;

    /* renamed from: f, reason: collision with root package name */
    private final String f10403f;

    /* renamed from: g, reason: collision with root package name */
    private Network f10404g;

    /* renamed from: h, reason: collision with root package name */
    private long f10405h;

    /* renamed from: i, reason: collision with root package name */
    private final String f10406i;

    /* renamed from: j, reason: collision with root package name */
    private int f10407j;

    /* renamed from: k, reason: collision with root package name */
    private final g f10408k;

    public c(String str, g gVar, String str2, String str3) {
        this(str, null, gVar, str2, str3);
    }

    private c(String str, Map<String, String> map, g gVar, String str2, String str3) {
        this.f10402e = false;
        this.f10399b = str;
        this.f10408k = gVar;
        this.f10400c = map == null ? new HashMap<>() : map;
        this.f10398a = gVar == null ? "" : gVar.b().toString();
        this.f10401d = str2;
        this.f10403f = str3;
        this.f10406i = gVar != null ? gVar.a() : "";
        l();
    }

    private void l() {
        this.f10400c.put(com.heytap.mcssdk.constant.b.C, BuildConfig.CMCC_SDK_VERSION);
        this.f10400c.put("Content-Type", MimeTypes.APP_JSON);
        this.f10400c.put("CMCC-EncryptType", "STD");
        this.f10400c.put("traceId", this.f10403f);
        this.f10400c.put("appid", this.f10406i);
        this.f10400c.put("Connection", "close");
    }

    public String a() {
        return this.f10399b;
    }

    public void a(long j2) {
        this.f10405h = j2;
    }

    public void a(Network network) {
        this.f10404g = network;
    }

    public void a(String str, String str2) {
        this.f10400c.put(str, str2);
    }

    public void a(boolean z2) {
        this.f10402e = z2;
    }

    public boolean b() {
        return this.f10402e;
    }

    public Map<String, String> c() {
        return this.f10400c;
    }

    public String d() {
        return this.f10398a;
    }

    public String e() {
        return this.f10401d;
    }

    public String f() {
        return this.f10403f;
    }

    public boolean g() {
        return !e.a(this.f10403f) || this.f10399b.contains("logReport") || this.f10399b.contains("uniConfig");
    }

    public Network h() {
        return this.f10404g;
    }

    public long i() {
        return this.f10405h;
    }

    public boolean j() {
        int i2 = this.f10407j;
        this.f10407j = i2 + 1;
        return i2 < 2;
    }

    public g k() {
        return this.f10408k;
    }
}
