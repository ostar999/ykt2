package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class g extends m {

    /* renamed from: k, reason: collision with root package name */
    static byte[] f17826k = null;

    /* renamed from: l, reason: collision with root package name */
    static Map<String, String> f17827l = null;

    /* renamed from: m, reason: collision with root package name */
    static final /* synthetic */ boolean f17828m = true;

    /* renamed from: g, reason: collision with root package name */
    public byte[] f17835g;

    /* renamed from: i, reason: collision with root package name */
    public Map<String, String> f17837i;

    /* renamed from: j, reason: collision with root package name */
    public Map<String, String> f17838j;

    /* renamed from: a, reason: collision with root package name */
    public short f17829a = 0;

    /* renamed from: b, reason: collision with root package name */
    public byte f17830b = 0;

    /* renamed from: c, reason: collision with root package name */
    public int f17831c = 0;

    /* renamed from: d, reason: collision with root package name */
    public int f17832d = 0;

    /* renamed from: e, reason: collision with root package name */
    public String f17833e = null;

    /* renamed from: f, reason: collision with root package name */
    public String f17834f = null;

    /* renamed from: h, reason: collision with root package name */
    public int f17836h = 0;

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f17829a, 1);
        lVar.a(this.f17830b, 2);
        lVar.a(this.f17831c, 3);
        lVar.a(this.f17832d, 4);
        lVar.a(this.f17833e, 5);
        lVar.a(this.f17834f, 6);
        lVar.a(this.f17835g, 7);
        lVar.a(this.f17836h, 8);
        lVar.a((Map) this.f17837i, 9);
        lVar.a((Map) this.f17838j, 10);
    }

    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            if (f17828m) {
                return null;
            }
            throw new AssertionError();
        }
    }

    public final boolean equals(Object obj) {
        g gVar = (g) obj;
        return n.a(1, (int) gVar.f17829a) && n.a(1, (int) gVar.f17830b) && n.a(1, gVar.f17831c) && n.a(1, gVar.f17832d) && n.a((Object) 1, (Object) gVar.f17833e) && n.a((Object) 1, (Object) gVar.f17834f) && n.a((Object) 1, (Object) gVar.f17835g) && n.a(1, gVar.f17836h) && n.a((Object) 1, (Object) gVar.f17837i) && n.a((Object) 1, (Object) gVar.f17838j);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        try {
            this.f17829a = kVar.a(this.f17829a, 1, true);
            this.f17830b = kVar.a(this.f17830b, 2, true);
            this.f17831c = kVar.a(this.f17831c, 3, true);
            this.f17832d = kVar.a(this.f17832d, 4, true);
            this.f17833e = kVar.b(5, true);
            this.f17834f = kVar.b(6, true);
            if (f17826k == null) {
                f17826k = new byte[]{0};
            }
            this.f17835g = kVar.c(7, true);
            this.f17836h = kVar.a(this.f17836h, 8, true);
            if (f17827l == null) {
                HashMap map = new HashMap();
                f17827l = map;
                map.put("", "");
            }
            this.f17837i = (Map) kVar.a((k) f17827l, 9, true);
            if (f17827l == null) {
                HashMap map2 = new HashMap();
                f17827l = map2;
                map2.put("", "");
            }
            this.f17838j = (Map) kVar.a((k) f17827l, 10, true);
        } catch (Exception e2) {
            e2.printStackTrace();
            System.out.println("RequestPacket decode error " + f.a(this.f17835g));
            throw new RuntimeException(e2);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
        i iVar = new i(sb, i2);
        iVar.a(this.f17829a, "iVersion");
        iVar.a(this.f17830b, "cPacketType");
        iVar.a(this.f17831c, "iMessageType");
        iVar.a(this.f17832d, "iRequestId");
        iVar.a(this.f17833e, "sServantName");
        iVar.a(this.f17834f, "sFuncName");
        iVar.a(this.f17835g, "sBuffer");
        iVar.a(this.f17836h, "iTimeout");
        iVar.a((Map) this.f17837i, com.umeng.analytics.pro.d.R);
        iVar.a((Map) this.f17838j, "status");
    }
}
