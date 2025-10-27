package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class iq implements jq<iq, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f653a;

    /* renamed from: a, reason: collision with other field name */
    public long f654a;

    /* renamed from: a, reason: collision with other field name */
    public String f655a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f656a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f657a;

    /* renamed from: a, reason: collision with other field name */
    public boolean f658a;

    /* renamed from: b, reason: collision with other field name */
    public int f659b;

    /* renamed from: b, reason: collision with other field name */
    public String f660b;

    /* renamed from: b, reason: collision with other field name */
    public Map<String, String> f661b;

    /* renamed from: c, reason: collision with other field name */
    public int f662c;

    /* renamed from: c, reason: collision with other field name */
    public String f663c;

    /* renamed from: c, reason: collision with other field name */
    public Map<String, String> f664c;

    /* renamed from: d, reason: collision with other field name */
    public String f665d;

    /* renamed from: e, reason: collision with other field name */
    public String f666e;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f652a = new kg("PushMetaInfo");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25262a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25263b = new jy("", (byte) 10, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25264c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25265d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25266e = new jy("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25267f = new jy("", (byte) 8, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25268g = new jy("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25269h = new jy("", (byte) 8, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25270i = new jy("", (byte) 8, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25271j = new jy("", (byte) 13, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final jy f25272k = new jy("", (byte) 13, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final jy f25273l = new jy("", (byte) 2, 12);

    /* renamed from: m, reason: collision with root package name */
    private static final jy f25274m = new jy("", (byte) 13, 13);

    public iq() {
        this.f656a = new BitSet(5);
        this.f658a = false;
    }

    public iq(iq iqVar) {
        BitSet bitSet = new BitSet(5);
        this.f656a = bitSet;
        bitSet.clear();
        this.f656a.or(iqVar.f656a);
        if (iqVar.m557a()) {
            this.f655a = iqVar.f655a;
        }
        this.f654a = iqVar.f654a;
        if (iqVar.m563c()) {
            this.f660b = iqVar.f660b;
        }
        if (iqVar.m564d()) {
            this.f663c = iqVar.f663c;
        }
        if (iqVar.e()) {
            this.f665d = iqVar.f665d;
        }
        this.f653a = iqVar.f653a;
        if (iqVar.g()) {
            this.f666e = iqVar.f666e;
        }
        this.f659b = iqVar.f659b;
        this.f662c = iqVar.f662c;
        if (iqVar.j()) {
            HashMap map = new HashMap();
            for (Map.Entry<String, String> entry : iqVar.f657a.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
            this.f657a = map;
        }
        if (iqVar.k()) {
            HashMap map2 = new HashMap();
            for (Map.Entry<String, String> entry2 : iqVar.f661b.entrySet()) {
                map2.put(entry2.getKey(), entry2.getValue());
            }
            this.f661b = map2;
        }
        this.f658a = iqVar.f658a;
        if (iqVar.n()) {
            HashMap map3 = new HashMap();
            for (Map.Entry<String, String> entry3 : iqVar.f664c.entrySet()) {
                map3.put(entry3.getKey(), entry3.getValue());
            }
            this.f664c = map3;
        }
    }

    public int a() {
        return this.f653a;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(iq iqVar) {
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
        if (!getClass().equals(iqVar.getClass())) {
            return getClass().getName().compareTo(iqVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m557a()).compareTo(Boolean.valueOf(iqVar.m557a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m557a() && (iA13 = jr.a(this.f655a, iqVar.f655a)) != 0) {
            return iA13;
        }
        int iCompareTo2 = Boolean.valueOf(m561b()).compareTo(Boolean.valueOf(iqVar.m561b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m561b() && (iA12 = jr.a(this.f654a, iqVar.f654a)) != 0) {
            return iA12;
        }
        int iCompareTo3 = Boolean.valueOf(m563c()).compareTo(Boolean.valueOf(iqVar.m563c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m563c() && (iA11 = jr.a(this.f660b, iqVar.f660b)) != 0) {
            return iA11;
        }
        int iCompareTo4 = Boolean.valueOf(m564d()).compareTo(Boolean.valueOf(iqVar.m564d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (m564d() && (iA10 = jr.a(this.f663c, iqVar.f663c)) != 0) {
            return iA10;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(iqVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA9 = jr.a(this.f665d, iqVar.f665d)) != 0) {
            return iA9;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(iqVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA8 = jr.a(this.f653a, iqVar.f653a)) != 0) {
            return iA8;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(iqVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA7 = jr.a(this.f666e, iqVar.f666e)) != 0) {
            return iA7;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(iqVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA6 = jr.a(this.f659b, iqVar.f659b)) != 0) {
            return iA6;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(iqVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA5 = jr.a(this.f662c, iqVar.f662c)) != 0) {
            return iA5;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(iqVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA4 = jr.a(this.f657a, iqVar.f657a)) != 0) {
            return iA4;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(iqVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA3 = jr.a(this.f661b, iqVar.f661b)) != 0) {
            return iA3;
        }
        int iCompareTo12 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(iqVar.m()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (m() && (iA2 = jr.a(this.f658a, iqVar.f658a)) != 0) {
            return iA2;
        }
        int iCompareTo13 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(iqVar.n()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (!n() || (iA = jr.a(this.f664c, iqVar.f664c)) == 0) {
            return 0;
        }
        return iA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m552a() {
        return this.f654a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public iq m553a() {
        return new iq(this);
    }

    public iq a(int i2) {
        this.f653a = i2;
        b(true);
        return this;
    }

    public iq a(String str) {
        this.f655a = str;
        return this;
    }

    public iq a(Map<String, String> map) {
        this.f657a = map;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m554a() {
        return this.f655a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public Map<String, String> m555a() {
        return this.f657a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m556a() throws kc {
        if (this.f655a != null) {
            return;
        }
        throw new kc("Required field 'id' was not present! Struct: " + toString());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0041  */
    @Override // com.xiaomi.push.jq
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(com.xiaomi.push.kb r9) throws com.xiaomi.push.kc {
        /*
            Method dump skipped, instructions count: 324
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.iq.a(com.xiaomi.push.kb):void");
    }

    public void a(String str, String str2) {
        if (this.f657a == null) {
            this.f657a = new HashMap();
        }
        this.f657a.put(str, str2);
    }

    public void a(boolean z2) {
        this.f656a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m557a() {
        return this.f655a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m558a(iq iqVar) {
        if (iqVar == null) {
            return false;
        }
        boolean zM557a = m557a();
        boolean zM557a2 = iqVar.m557a();
        if (((zM557a || zM557a2) && !(zM557a && zM557a2 && this.f655a.equals(iqVar.f655a))) || this.f654a != iqVar.f654a) {
            return false;
        }
        boolean zM563c = m563c();
        boolean zM563c2 = iqVar.m563c();
        if ((zM563c || zM563c2) && !(zM563c && zM563c2 && this.f660b.equals(iqVar.f660b))) {
            return false;
        }
        boolean zM564d = m564d();
        boolean zM564d2 = iqVar.m564d();
        if ((zM564d || zM564d2) && !(zM564d && zM564d2 && this.f663c.equals(iqVar.f663c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = iqVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f665d.equals(iqVar.f665d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = iqVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f653a == iqVar.f653a)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = iqVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f666e.equals(iqVar.f666e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = iqVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f659b == iqVar.f659b)) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = iqVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f662c == iqVar.f662c)) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = iqVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f657a.equals(iqVar.f657a))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = iqVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f661b.equals(iqVar.f661b))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = iqVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f658a == iqVar.f658a)) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = iqVar.n();
        if (zN || zN2) {
            return zN && zN2 && this.f664c.equals(iqVar.f664c);
        }
        return true;
    }

    public int b() {
        return this.f659b;
    }

    public iq b(int i2) {
        this.f659b = i2;
        c(true);
        return this;
    }

    public iq b(String str) {
        this.f660b = str;
        return this;
    }

    /* renamed from: b, reason: collision with other method in class */
    public String m559b() {
        return this.f660b;
    }

    /* renamed from: b, reason: collision with other method in class */
    public Map<String, String> m560b() {
        return this.f661b;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m556a();
        kbVar.a(f652a);
        if (this.f655a != null) {
            kbVar.a(f25262a);
            kbVar.a(this.f655a);
            kbVar.b();
        }
        kbVar.a(f25263b);
        kbVar.a(this.f654a);
        kbVar.b();
        if (this.f660b != null && m563c()) {
            kbVar.a(f25264c);
            kbVar.a(this.f660b);
            kbVar.b();
        }
        if (this.f663c != null && m564d()) {
            kbVar.a(f25265d);
            kbVar.a(this.f663c);
            kbVar.b();
        }
        if (this.f665d != null && e()) {
            kbVar.a(f25266e);
            kbVar.a(this.f665d);
            kbVar.b();
        }
        if (f()) {
            kbVar.a(f25267f);
            kbVar.mo671a(this.f653a);
            kbVar.b();
        }
        if (this.f666e != null && g()) {
            kbVar.a(f25268g);
            kbVar.a(this.f666e);
            kbVar.b();
        }
        if (h()) {
            kbVar.a(f25269h);
            kbVar.mo671a(this.f659b);
            kbVar.b();
        }
        if (i()) {
            kbVar.a(f25270i);
            kbVar.mo671a(this.f662c);
            kbVar.b();
        }
        if (this.f657a != null && j()) {
            kbVar.a(f25271j);
            kbVar.a(new ka((byte) 11, (byte) 11, this.f657a.size()));
            for (Map.Entry<String, String> entry : this.f657a.entrySet()) {
                kbVar.a(entry.getKey());
                kbVar.a(entry.getValue());
            }
            kbVar.d();
            kbVar.b();
        }
        if (this.f661b != null && k()) {
            kbVar.a(f25272k);
            kbVar.a(new ka((byte) 11, (byte) 11, this.f661b.size()));
            for (Map.Entry<String, String> entry2 : this.f661b.entrySet()) {
                kbVar.a(entry2.getKey());
                kbVar.a(entry2.getValue());
            }
            kbVar.d();
            kbVar.b();
        }
        if (m()) {
            kbVar.a(f25273l);
            kbVar.a(this.f658a);
            kbVar.b();
        }
        if (this.f664c != null && n()) {
            kbVar.a(f25274m);
            kbVar.a(new ka((byte) 11, (byte) 11, this.f664c.size()));
            for (Map.Entry<String, String> entry3 : this.f664c.entrySet()) {
                kbVar.a(entry3.getKey());
                kbVar.a(entry3.getValue());
            }
            kbVar.d();
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(String str, String str2) {
        if (this.f661b == null) {
            this.f661b = new HashMap();
        }
        this.f661b.put(str, str2);
    }

    public void b(boolean z2) {
        this.f656a.set(1, z2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m561b() {
        return this.f656a.get(0);
    }

    public int c() {
        return this.f662c;
    }

    public iq c(int i2) {
        this.f662c = i2;
        d(true);
        return this;
    }

    public iq c(String str) {
        this.f663c = str;
        return this;
    }

    /* renamed from: c, reason: collision with other method in class */
    public String m562c() {
        return this.f663c;
    }

    public void c(boolean z2) {
        this.f656a.set(2, z2);
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m563c() {
        return this.f660b != null;
    }

    public iq d(String str) {
        this.f665d = str;
        return this;
    }

    public String d() {
        return this.f665d;
    }

    public void d(boolean z2) {
        this.f656a.set(3, z2);
    }

    /* renamed from: d, reason: collision with other method in class */
    public boolean m564d() {
        return this.f663c != null;
    }

    public void e(boolean z2) {
        this.f656a.set(4, z2);
    }

    public boolean e() {
        return this.f665d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof iq)) {
            return m558a((iq) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f656a.get(1);
    }

    public boolean g() {
        return this.f666e != null;
    }

    public boolean h() {
        return this.f656a.get(2);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f656a.get(3);
    }

    public boolean j() {
        return this.f657a != null;
    }

    public boolean k() {
        return this.f661b != null;
    }

    public boolean l() {
        return this.f658a;
    }

    public boolean m() {
        return this.f656a.get(4);
    }

    public boolean n() {
        return this.f664c != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PushMetaInfo(");
        sb.append("id:");
        String str = this.f655a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("messageTs:");
        sb.append(this.f654a);
        if (m563c()) {
            sb.append(", ");
            sb.append("topic:");
            String str2 = this.f660b;
            if (str2 == null) {
                sb.append("null");
            } else {
                sb.append(str2);
            }
        }
        if (m564d()) {
            sb.append(", ");
            sb.append("title:");
            String str3 = this.f663c;
            if (str3 == null) {
                sb.append("null");
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("description:");
            String str4 = this.f665d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("notifyType:");
            sb.append(this.f653a);
        }
        if (g()) {
            sb.append(", ");
            sb.append("url:");
            String str5 = this.f666e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("passThrough:");
            sb.append(this.f659b);
        }
        if (i()) {
            sb.append(", ");
            sb.append("notifyId:");
            sb.append(this.f662c);
        }
        if (j()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f657a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("internal:");
            Map<String, String> map2 = this.f661b;
            if (map2 == null) {
                sb.append("null");
            } else {
                sb.append(map2);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("ignoreRegInfo:");
            sb.append(this.f658a);
        }
        if (n()) {
            sb.append(", ");
            sb.append("apsProperFields:");
            Map<String, String> map3 = this.f664c;
            if (map3 == null) {
                sb.append("null");
            } else {
                sb.append(map3);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
