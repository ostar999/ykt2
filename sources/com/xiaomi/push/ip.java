package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class ip implements jq<ip, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f633a;

    /* renamed from: a, reason: collision with other field name */
    public iq f634a;

    /* renamed from: a, reason: collision with other field name */
    public it f635a;

    /* renamed from: a, reason: collision with other field name */
    public String f636a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f637a = new BitSet(4);

    /* renamed from: a, reason: collision with other field name */
    public boolean f638a = false;

    /* renamed from: b, reason: collision with other field name */
    public long f639b;

    /* renamed from: b, reason: collision with other field name */
    public String f640b;

    /* renamed from: c, reason: collision with other field name */
    public long f641c;

    /* renamed from: c, reason: collision with other field name */
    public String f642c;

    /* renamed from: d, reason: collision with other field name */
    public String f643d;

    /* renamed from: e, reason: collision with other field name */
    public String f644e;

    /* renamed from: f, reason: collision with other field name */
    public String f645f;

    /* renamed from: g, reason: collision with other field name */
    public String f646g;

    /* renamed from: h, reason: collision with other field name */
    public String f647h;

    /* renamed from: i, reason: collision with other field name */
    public String f648i;

    /* renamed from: j, reason: collision with other field name */
    public String f649j;

    /* renamed from: k, reason: collision with other field name */
    public String f650k;

    /* renamed from: l, reason: collision with other field name */
    public String f651l;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f632a = new kg("PushMessage");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25244a = new jy("", (byte) 12, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25245b = new jy("", (byte) 11, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25246c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25247d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25248e = new jy("", (byte) 10, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25249f = new jy("", (byte) 10, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25250g = new jy("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25251h = new jy("", (byte) 11, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25252i = new jy("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25253j = new jy("", (byte) 11, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final jy f25254k = new jy("", (byte) 11, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final jy f25255l = new jy("", (byte) 12, 12);

    /* renamed from: m, reason: collision with root package name */
    private static final jy f25256m = new jy("", (byte) 11, 13);

    /* renamed from: n, reason: collision with root package name */
    private static final jy f25257n = new jy("", (byte) 2, 14);

    /* renamed from: o, reason: collision with root package name */
    private static final jy f25258o = new jy("", (byte) 11, 15);

    /* renamed from: p, reason: collision with root package name */
    private static final jy f25259p = new jy("", (byte) 10, 16);

    /* renamed from: q, reason: collision with root package name */
    private static final jy f25260q = new jy("", (byte) 11, 20);

    /* renamed from: r, reason: collision with root package name */
    private static final jy f25261r = new jy("", (byte) 11, 21);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ip ipVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        int iA10;
        int iA11;
        int iA12;
        int iA13;
        int iA14;
        int iA15;
        int iA16;
        int iA17;
        int iA18;
        if (!getClass().equals(ipVar.getClass())) {
            return getClass().getName().compareTo(ipVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m548a()).compareTo(Boolean.valueOf(ipVar.m548a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m548a() && (iA18 = jr.a(this.f635a, ipVar.f635a)) != 0) {
            return iA18;
        }
        int iCompareTo2 = Boolean.valueOf(m550b()).compareTo(Boolean.valueOf(ipVar.m550b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m550b() && (iA17 = jr.a(this.f636a, ipVar.f636a)) != 0) {
            return iA17;
        }
        int iCompareTo3 = Boolean.valueOf(m551c()).compareTo(Boolean.valueOf(ipVar.m551c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m551c() && (iA16 = jr.a(this.f640b, ipVar.f640b)) != 0) {
            return iA16;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ipVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA15 = jr.a(this.f642c, ipVar.f642c)) != 0) {
            return iA15;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(ipVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA14 = jr.a(this.f633a, ipVar.f633a)) != 0) {
            return iA14;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ipVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA13 = jr.a(this.f639b, ipVar.f639b)) != 0) {
            return iA13;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(ipVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA12 = jr.a(this.f643d, ipVar.f643d)) != 0) {
            return iA12;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(ipVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA11 = jr.a(this.f644e, ipVar.f644e)) != 0) {
            return iA11;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(ipVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA10 = jr.a(this.f645f, ipVar.f645f)) != 0) {
            return iA10;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(ipVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA9 = jr.a(this.f646g, ipVar.f646g)) != 0) {
            return iA9;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(ipVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA8 = jr.a(this.f647h, ipVar.f647h)) != 0) {
            return iA8;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(ipVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (l() && (iA7 = jr.a(this.f634a, ipVar.f634a)) != 0) {
            return iA7;
        }
        int iCompareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(ipVar.m()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (m() && (iA6 = jr.a(this.f648i, ipVar.f648i)) != 0) {
            return iA6;
        }
        int iCompareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(ipVar.n()));
        if (iCompareTo14 != 0) {
            return iCompareTo14;
        }
        if (n() && (iA5 = jr.a(this.f638a, ipVar.f638a)) != 0) {
            return iA5;
        }
        int iCompareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(ipVar.o()));
        if (iCompareTo15 != 0) {
            return iCompareTo15;
        }
        if (o() && (iA4 = jr.a(this.f649j, ipVar.f649j)) != 0) {
            return iA4;
        }
        int iCompareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(ipVar.p()));
        if (iCompareTo16 != 0) {
            return iCompareTo16;
        }
        if (p() && (iA3 = jr.a(this.f641c, ipVar.f641c)) != 0) {
            return iA3;
        }
        int iCompareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(ipVar.q()));
        if (iCompareTo17 != 0) {
            return iCompareTo17;
        }
        if (q() && (iA2 = jr.a(this.f650k, ipVar.f650k)) != 0) {
            return iA2;
        }
        int iCompareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(ipVar.r()));
        if (iCompareTo18 != 0) {
            return iCompareTo18;
        }
        if (!r() || (iA = jr.a(this.f651l, ipVar.f651l)) == 0) {
            return 0;
        }
        return iA;
    }

    public long a() {
        return this.f633a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m546a() {
        return this.f636a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m547a() throws kc {
        if (this.f636a == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f640b == null) {
            throw new kc("Required field 'appId' was not present! Struct: " + toString());
        }
        if (this.f642c != null) {
            return;
        }
        throw new kc("Required field 'payload' was not present! Struct: " + toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0026  */
    @Override // com.xiaomi.push.jq
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(com.xiaomi.push.kb r7) throws com.xiaomi.push.kc {
        /*
            Method dump skipped, instructions count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.ip.a(com.xiaomi.push.kb):void");
    }

    public void a(boolean z2) {
        this.f637a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m548a() {
        return this.f635a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m549a(ip ipVar) {
        if (ipVar == null) {
            return false;
        }
        boolean zM548a = m548a();
        boolean zM548a2 = ipVar.m548a();
        if ((zM548a || zM548a2) && !(zM548a && zM548a2 && this.f635a.m569a(ipVar.f635a))) {
            return false;
        }
        boolean zM550b = m550b();
        boolean zM550b2 = ipVar.m550b();
        if ((zM550b || zM550b2) && !(zM550b && zM550b2 && this.f636a.equals(ipVar.f636a))) {
            return false;
        }
        boolean zM551c = m551c();
        boolean zM551c2 = ipVar.m551c();
        if ((zM551c || zM551c2) && !(zM551c && zM551c2 && this.f640b.equals(ipVar.f640b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = ipVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f642c.equals(ipVar.f642c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = ipVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f633a == ipVar.f633a)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = ipVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f639b == ipVar.f639b)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = ipVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f643d.equals(ipVar.f643d))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = ipVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f644e.equals(ipVar.f644e))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = ipVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f645f.equals(ipVar.f645f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = ipVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f646g.equals(ipVar.f646g))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = ipVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f647h.equals(ipVar.f647h))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = ipVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.f634a.m558a(ipVar.f634a))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = ipVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f648i.equals(ipVar.f648i))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = ipVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.f638a == ipVar.f638a)) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = ipVar.o();
        if ((zO || zO2) && !(zO && zO2 && this.f649j.equals(ipVar.f649j))) {
            return false;
        }
        boolean zP = p();
        boolean zP2 = ipVar.p();
        if ((zP || zP2) && !(zP && zP2 && this.f641c == ipVar.f641c)) {
            return false;
        }
        boolean zQ = q();
        boolean zQ2 = ipVar.q();
        if ((zQ || zQ2) && !(zQ && zQ2 && this.f650k.equals(ipVar.f650k))) {
            return false;
        }
        boolean zR = r();
        boolean zR2 = ipVar.r();
        if (zR || zR2) {
            return zR && zR2 && this.f651l.equals(ipVar.f651l);
        }
        return true;
    }

    public String b() {
        return this.f640b;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m547a();
        kbVar.a(f632a);
        if (this.f635a != null && m548a()) {
            kbVar.a(f25244a);
            this.f635a.b(kbVar);
            kbVar.b();
        }
        if (this.f636a != null) {
            kbVar.a(f25245b);
            kbVar.a(this.f636a);
            kbVar.b();
        }
        if (this.f640b != null) {
            kbVar.a(f25246c);
            kbVar.a(this.f640b);
            kbVar.b();
        }
        if (this.f642c != null) {
            kbVar.a(f25247d);
            kbVar.a(this.f642c);
            kbVar.b();
        }
        if (e()) {
            kbVar.a(f25248e);
            kbVar.a(this.f633a);
            kbVar.b();
        }
        if (f()) {
            kbVar.a(f25249f);
            kbVar.a(this.f639b);
            kbVar.b();
        }
        if (this.f643d != null && g()) {
            kbVar.a(f25250g);
            kbVar.a(this.f643d);
            kbVar.b();
        }
        if (this.f644e != null && h()) {
            kbVar.a(f25251h);
            kbVar.a(this.f644e);
            kbVar.b();
        }
        if (this.f645f != null && i()) {
            kbVar.a(f25252i);
            kbVar.a(this.f645f);
            kbVar.b();
        }
        if (this.f646g != null && j()) {
            kbVar.a(f25253j);
            kbVar.a(this.f646g);
            kbVar.b();
        }
        if (this.f647h != null && k()) {
            kbVar.a(f25254k);
            kbVar.a(this.f647h);
            kbVar.b();
        }
        if (this.f634a != null && l()) {
            kbVar.a(f25255l);
            this.f634a.b(kbVar);
            kbVar.b();
        }
        if (this.f648i != null && m()) {
            kbVar.a(f25256m);
            kbVar.a(this.f648i);
            kbVar.b();
        }
        if (n()) {
            kbVar.a(f25257n);
            kbVar.a(this.f638a);
            kbVar.b();
        }
        if (this.f649j != null && o()) {
            kbVar.a(f25258o);
            kbVar.a(this.f649j);
            kbVar.b();
        }
        if (p()) {
            kbVar.a(f25259p);
            kbVar.a(this.f641c);
            kbVar.b();
        }
        if (this.f650k != null && q()) {
            kbVar.a(f25260q);
            kbVar.a(this.f650k);
            kbVar.b();
        }
        if (this.f651l != null && r()) {
            kbVar.a(f25261r);
            kbVar.a(this.f651l);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f637a.set(1, z2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m550b() {
        return this.f636a != null;
    }

    public String c() {
        return this.f642c;
    }

    public void c(boolean z2) {
        this.f637a.set(2, z2);
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m551c() {
        return this.f640b != null;
    }

    public void d(boolean z2) {
        this.f637a.set(3, z2);
    }

    public boolean d() {
        return this.f642c != null;
    }

    public boolean e() {
        return this.f637a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ip)) {
            return m549a((ip) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f637a.get(1);
    }

    public boolean g() {
        return this.f643d != null;
    }

    public boolean h() {
        return this.f644e != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f645f != null;
    }

    public boolean j() {
        return this.f646g != null;
    }

    public boolean k() {
        return this.f647h != null;
    }

    public boolean l() {
        return this.f634a != null;
    }

    public boolean m() {
        return this.f648i != null;
    }

    public boolean n() {
        return this.f637a.get(2);
    }

    public boolean o() {
        return this.f649j != null;
    }

    public boolean p() {
        return this.f637a.get(3);
    }

    public boolean q() {
        return this.f650k != null;
    }

    public boolean r() {
        return this.f651l != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("PushMessage(");
        if (m548a()) {
            sb.append("to:");
            it itVar = this.f635a;
            if (itVar == null) {
                sb.append("null");
            } else {
                sb.append(itVar);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z2) {
            sb.append(", ");
        }
        sb.append("id:");
        String str = this.f636a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("appId:");
        String str2 = this.f640b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("payload:");
        String str3 = this.f642c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("createAt:");
            sb.append(this.f633a);
        }
        if (f()) {
            sb.append(", ");
            sb.append("ttl:");
            sb.append(this.f639b);
        }
        if (g()) {
            sb.append(", ");
            sb.append("collapseKey:");
            String str4 = this.f643d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f644e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("regId:");
            String str6 = this.f645f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f646g;
            if (str7 == null) {
                sb.append("null");
            } else {
                sb.append(str7);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("topic:");
            String str8 = this.f647h;
            if (str8 == null) {
                sb.append("null");
            } else {
                sb.append(str8);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("metaInfo:");
            iq iqVar = this.f634a;
            if (iqVar == null) {
                sb.append("null");
            } else {
                sb.append(iqVar);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str9 = this.f648i;
            if (str9 == null) {
                sb.append("null");
            } else {
                sb.append(str9);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("isOnline:");
            sb.append(this.f638a);
        }
        if (o()) {
            sb.append(", ");
            sb.append("userAccount:");
            String str10 = this.f649j;
            if (str10 == null) {
                sb.append("null");
            } else {
                sb.append(str10);
            }
        }
        if (p()) {
            sb.append(", ");
            sb.append("miid:");
            sb.append(this.f641c);
        }
        if (q()) {
            sb.append(", ");
            sb.append("imeiMd5:");
            String str11 = this.f650k;
            if (str11 == null) {
                sb.append("null");
            } else {
                sb.append(str11);
            }
        }
        if (r()) {
            sb.append(", ");
            sb.append("deviceId:");
            String str12 = this.f651l;
            if (str12 == null) {
                sb.append("null");
            } else {
                sb.append(str12);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
