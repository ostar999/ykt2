package com.xiaomi.push;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class je implements jq<je, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f764a;

    /* renamed from: a, reason: collision with other field name */
    public it f765a;

    /* renamed from: a, reason: collision with other field name */
    public String f766a;

    /* renamed from: a, reason: collision with other field name */
    public ByteBuffer f767a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f768a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f769a;

    /* renamed from: a, reason: collision with other field name */
    public boolean f770a;

    /* renamed from: b, reason: collision with other field name */
    public String f771b;

    /* renamed from: b, reason: collision with other field name */
    public boolean f772b;

    /* renamed from: c, reason: collision with other field name */
    public String f773c;

    /* renamed from: d, reason: collision with other field name */
    public String f774d;

    /* renamed from: e, reason: collision with other field name */
    public String f775e;

    /* renamed from: f, reason: collision with other field name */
    public String f776f;

    /* renamed from: g, reason: collision with other field name */
    public String f777g;

    /* renamed from: h, reason: collision with other field name */
    public String f778h;

    /* renamed from: i, reason: collision with other field name */
    public String f779i;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f763a = new kg("XmPushActionNotification");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25356a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25357b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25358c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25359d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25360e = new jy("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25361f = new jy("", (byte) 2, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25362g = new jy("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25363h = new jy("", (byte) 13, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25364i = new jy("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25365j = new jy("", (byte) 11, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final jy f25366k = new jy("", (byte) 11, 12);

    /* renamed from: l, reason: collision with root package name */
    private static final jy f25367l = new jy("", (byte) 11, 13);

    /* renamed from: m, reason: collision with root package name */
    private static final jy f25368m = new jy("", (byte) 11, 14);

    /* renamed from: n, reason: collision with root package name */
    private static final jy f25369n = new jy("", (byte) 10, 15);

    /* renamed from: o, reason: collision with root package name */
    private static final jy f25370o = new jy("", (byte) 2, 20);

    public je() {
        this.f768a = new BitSet(3);
        this.f770a = true;
        this.f772b = false;
    }

    public je(String str, boolean z2) {
        this();
        this.f771b = str;
        this.f770a = z2;
        m612a(true);
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(je jeVar) {
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
        if (!getClass().equals(jeVar.getClass())) {
            return getClass().getName().compareTo(jeVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m613a()).compareTo(Boolean.valueOf(jeVar.m613a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m613a() && (iA15 = jr.a(this.f766a, jeVar.f766a)) != 0) {
            return iA15;
        }
        int iCompareTo2 = Boolean.valueOf(m616b()).compareTo(Boolean.valueOf(jeVar.m616b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m616b() && (iA14 = jr.a(this.f765a, jeVar.f765a)) != 0) {
            return iA14;
        }
        int iCompareTo3 = Boolean.valueOf(m617c()).compareTo(Boolean.valueOf(jeVar.m617c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m617c() && (iA13 = jr.a(this.f771b, jeVar.f771b)) != 0) {
            return iA13;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jeVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA12 = jr.a(this.f773c, jeVar.f773c)) != 0) {
            return iA12;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jeVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA11 = jr.a(this.f774d, jeVar.f774d)) != 0) {
            return iA11;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jeVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA10 = jr.a(this.f770a, jeVar.f770a)) != 0) {
            return iA10;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jeVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA9 = jr.a(this.f775e, jeVar.f775e)) != 0) {
            return iA9;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jeVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA8 = jr.a(this.f769a, jeVar.f769a)) != 0) {
            return iA8;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jeVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA7 = jr.a(this.f776f, jeVar.f776f)) != 0) {
            return iA7;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jeVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA6 = jr.a(this.f777g, jeVar.f777g)) != 0) {
            return iA6;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(jeVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA5 = jr.a(this.f778h, jeVar.f778h)) != 0) {
            return iA5;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(jeVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (l() && (iA4 = jr.a(this.f779i, jeVar.f779i)) != 0) {
            return iA4;
        }
        int iCompareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(jeVar.m()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (m() && (iA3 = jr.a(this.f767a, jeVar.f767a)) != 0) {
            return iA3;
        }
        int iCompareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(jeVar.n()));
        if (iCompareTo14 != 0) {
            return iCompareTo14;
        }
        if (n() && (iA2 = jr.a(this.f764a, jeVar.f764a)) != 0) {
            return iA2;
        }
        int iCompareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(jeVar.o()));
        if (iCompareTo15 != 0) {
            return iCompareTo15;
        }
        if (!o() || (iA = jr.a(this.f772b, jeVar.f772b)) == 0) {
            return 0;
        }
        return iA;
    }

    public je a(String str) {
        this.f771b = str;
        return this;
    }

    public je a(ByteBuffer byteBuffer) {
        this.f767a = byteBuffer;
        return this;
    }

    public je a(Map<String, String> map) {
        this.f769a = map;
        return this;
    }

    public je a(boolean z2) {
        this.f770a = z2;
        m612a(true);
        return this;
    }

    public je a(byte[] bArr) {
        a(ByteBuffer.wrap(bArr));
        return this;
    }

    public String a() {
        return this.f771b;
    }

    /* renamed from: a, reason: collision with other method in class */
    public Map<String, String> m610a() {
        return this.f769a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m611a() throws kc {
        if (this.f771b != null) {
            return;
        }
        throw new kc("Required field 'id' was not present! Struct: " + toString());
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) throws kc {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                if (f()) {
                    m611a();
                    return;
                }
                throw new kc("Required field 'requireAck' was not found in serialized data! Struct: " + toString());
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f766a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f765a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f771b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f773c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 11) {
                        this.f774d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 2) {
                        this.f770a = kbVar.mo672a();
                        m612a(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f775e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 13) {
                        ka kaVarMo664a = kbVar.mo664a();
                        this.f769a = new HashMap(kaVarMo664a.f932a * 2);
                        for (int i2 = 0; i2 < kaVarMo664a.f932a; i2++) {
                            this.f769a.put(kbVar.mo667a(), kbVar.mo667a());
                        }
                        kbVar.i();
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 11) {
                        this.f776f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 11) {
                        this.f777g = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 12:
                    if (b3 == 11) {
                        this.f778h = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 13:
                    if (b3 == 11) {
                        this.f779i = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 14:
                    if (b3 == 11) {
                        this.f767a = kbVar.mo668a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 15:
                    if (b3 == 10) {
                        this.f764a = kbVar.mo661a();
                        b(true);
                    }
                    kbVar.h();
                    break;
                case 20:
                    if (b3 == 2) {
                        this.f772b = kbVar.mo672a();
                        c(true);
                        continue;
                    }
                    kbVar.h();
                    break;
            }
            ke.a(kbVar, b3);
            kbVar.h();
        }
    }

    public void a(String str, String str2) {
        if (this.f769a == null) {
            this.f769a = new HashMap();
        }
        this.f769a.put(str, str2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m612a(boolean z2) {
        this.f768a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m613a() {
        return this.f766a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m614a(je jeVar) {
        if (jeVar == null) {
            return false;
        }
        boolean zM613a = m613a();
        boolean zM613a2 = jeVar.m613a();
        if ((zM613a || zM613a2) && !(zM613a && zM613a2 && this.f766a.equals(jeVar.f766a))) {
            return false;
        }
        boolean zM616b = m616b();
        boolean zM616b2 = jeVar.m616b();
        if ((zM616b || zM616b2) && !(zM616b && zM616b2 && this.f765a.m569a(jeVar.f765a))) {
            return false;
        }
        boolean zM617c = m617c();
        boolean zM617c2 = jeVar.m617c();
        if ((zM617c || zM617c2) && !(zM617c && zM617c2 && this.f771b.equals(jeVar.f771b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jeVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f773c.equals(jeVar.f773c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jeVar.e();
        if (((zE || zE2) && !(zE && zE2 && this.f774d.equals(jeVar.f774d))) || this.f770a != jeVar.f770a) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jeVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f775e.equals(jeVar.f775e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jeVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f769a.equals(jeVar.f769a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jeVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f776f.equals(jeVar.f776f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jeVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f777g.equals(jeVar.f777g))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = jeVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f778h.equals(jeVar.f778h))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = jeVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.f779i.equals(jeVar.f779i))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = jeVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f767a.equals(jeVar.f767a))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = jeVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.f764a == jeVar.f764a)) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = jeVar.o();
        if (zO || zO2) {
            return zO && zO2 && this.f772b == jeVar.f772b;
        }
        return true;
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m615a() {
        a(jr.a(this.f767a));
        return this.f767a.array();
    }

    public je b(String str) {
        this.f773c = str;
        return this;
    }

    public String b() {
        return this.f773c;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m611a();
        kbVar.a(f763a);
        if (this.f766a != null && m613a()) {
            kbVar.a(f25356a);
            kbVar.a(this.f766a);
            kbVar.b();
        }
        if (this.f765a != null && m616b()) {
            kbVar.a(f25357b);
            this.f765a.b(kbVar);
            kbVar.b();
        }
        if (this.f771b != null) {
            kbVar.a(f25358c);
            kbVar.a(this.f771b);
            kbVar.b();
        }
        if (this.f773c != null && d()) {
            kbVar.a(f25359d);
            kbVar.a(this.f773c);
            kbVar.b();
        }
        if (this.f774d != null && e()) {
            kbVar.a(f25360e);
            kbVar.a(this.f774d);
            kbVar.b();
        }
        kbVar.a(f25361f);
        kbVar.a(this.f770a);
        kbVar.b();
        if (this.f775e != null && g()) {
            kbVar.a(f25362g);
            kbVar.a(this.f775e);
            kbVar.b();
        }
        if (this.f769a != null && h()) {
            kbVar.a(f25363h);
            kbVar.a(new ka((byte) 11, (byte) 11, this.f769a.size()));
            for (Map.Entry<String, String> entry : this.f769a.entrySet()) {
                kbVar.a(entry.getKey());
                kbVar.a(entry.getValue());
            }
            kbVar.d();
            kbVar.b();
        }
        if (this.f776f != null && i()) {
            kbVar.a(f25364i);
            kbVar.a(this.f776f);
            kbVar.b();
        }
        if (this.f777g != null && j()) {
            kbVar.a(f25365j);
            kbVar.a(this.f777g);
            kbVar.b();
        }
        if (this.f778h != null && k()) {
            kbVar.a(f25366k);
            kbVar.a(this.f778h);
            kbVar.b();
        }
        if (this.f779i != null && l()) {
            kbVar.a(f25367l);
            kbVar.a(this.f779i);
            kbVar.b();
        }
        if (this.f767a != null && m()) {
            kbVar.a(f25368m);
            kbVar.a(this.f767a);
            kbVar.b();
        }
        if (n()) {
            kbVar.a(f25369n);
            kbVar.a(this.f764a);
            kbVar.b();
        }
        if (o()) {
            kbVar.a(f25370o);
            kbVar.a(this.f772b);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f768a.set(1, z2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m616b() {
        return this.f765a != null;
    }

    public je c(String str) {
        this.f774d = str;
        return this;
    }

    public String c() {
        return this.f776f;
    }

    public void c(boolean z2) {
        this.f768a.set(2, z2);
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m617c() {
        return this.f771b != null;
    }

    public je d(String str) {
        this.f776f = str;
        return this;
    }

    public boolean d() {
        return this.f773c != null;
    }

    public boolean e() {
        return this.f774d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof je)) {
            return m614a((je) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f768a.get(0);
    }

    public boolean g() {
        return this.f775e != null;
    }

    public boolean h() {
        return this.f769a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f776f != null;
    }

    public boolean j() {
        return this.f777g != null;
    }

    public boolean k() {
        return this.f778h != null;
    }

    public boolean l() {
        return this.f779i != null;
    }

    public boolean m() {
        return this.f767a != null;
    }

    public boolean n() {
        return this.f768a.get(1);
    }

    public boolean o() {
        return this.f768a.get(2);
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionNotification(");
        boolean z3 = false;
        if (m613a()) {
            sb.append("debug:");
            String str = this.f766a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m616b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            it itVar = this.f765a;
            if (itVar == null) {
                sb.append("null");
            } else {
                sb.append(itVar);
            }
        } else {
            z3 = z2;
        }
        if (!z3) {
            sb.append(", ");
        }
        sb.append("id:");
        String str2 = this.f771b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            String str3 = this.f773c;
            if (str3 == null) {
                sb.append("null");
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("type:");
            String str4 = this.f774d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        sb.append(", ");
        sb.append("requireAck:");
        sb.append(this.f770a);
        if (g()) {
            sb.append(", ");
            sb.append("payload:");
            String str5 = this.f775e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f769a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f776f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f777g;
            if (str7 == null) {
                sb.append("null");
            } else {
                sb.append(str7);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("regId:");
            String str8 = this.f778h;
            if (str8 == null) {
                sb.append("null");
            } else {
                sb.append(str8);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str9 = this.f779i;
            if (str9 == null) {
                sb.append("null");
            } else {
                sb.append(str9);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("binaryExtra:");
            ByteBuffer byteBuffer = this.f767a;
            if (byteBuffer == null) {
                sb.append("null");
            } else {
                jr.a(byteBuffer, sb);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.f764a);
        }
        if (o()) {
            sb.append(", ");
            sb.append("alreadyLogClickInXmq:");
            sb.append(this.f772b);
        }
        sb.append(")");
        return sb.toString();
    }
}
