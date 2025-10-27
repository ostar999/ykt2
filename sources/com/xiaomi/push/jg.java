package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class jg implements jq<jg, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f811a;

    /* renamed from: a, reason: collision with other field name */
    public long f812a;

    /* renamed from: a, reason: collision with other field name */
    public it f813a;

    /* renamed from: a, reason: collision with other field name */
    public String f814a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f815a = new BitSet(5);

    /* renamed from: b, reason: collision with other field name */
    public int f816b;

    /* renamed from: b, reason: collision with other field name */
    public long f817b;

    /* renamed from: b, reason: collision with other field name */
    public String f818b;

    /* renamed from: c, reason: collision with other field name */
    public long f819c;

    /* renamed from: c, reason: collision with other field name */
    public String f820c;

    /* renamed from: d, reason: collision with other field name */
    public String f821d;

    /* renamed from: e, reason: collision with other field name */
    public String f822e;

    /* renamed from: f, reason: collision with other field name */
    public String f823f;

    /* renamed from: g, reason: collision with other field name */
    public String f824g;

    /* renamed from: h, reason: collision with other field name */
    public String f825h;

    /* renamed from: i, reason: collision with other field name */
    public String f826i;

    /* renamed from: j, reason: collision with other field name */
    public String f827j;

    /* renamed from: k, reason: collision with other field name */
    public String f828k;

    /* renamed from: l, reason: collision with other field name */
    public String f829l;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f810a = new kg("XmPushActionRegistrationResult");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25397a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25398b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25399c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25400d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25401e = new jy("", (byte) 10, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25402f = new jy("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25403g = new jy("", (byte) 11, 8);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25404h = new jy("", (byte) 11, 9);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25405i = new jy("", (byte) 11, 10);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25406j = new jy("", (byte) 10, 11);

    /* renamed from: k, reason: collision with root package name */
    private static final jy f25407k = new jy("", (byte) 11, 12);

    /* renamed from: l, reason: collision with root package name */
    private static final jy f25408l = new jy("", (byte) 11, 13);

    /* renamed from: m, reason: collision with root package name */
    private static final jy f25409m = new jy("", (byte) 10, 14);

    /* renamed from: n, reason: collision with root package name */
    private static final jy f25410n = new jy("", (byte) 11, 15);

    /* renamed from: o, reason: collision with root package name */
    private static final jy f25411o = new jy("", (byte) 8, 16);

    /* renamed from: p, reason: collision with root package name */
    private static final jy f25412p = new jy("", (byte) 11, 17);

    /* renamed from: q, reason: collision with root package name */
    private static final jy f25413q = new jy("", (byte) 8, 18);

    /* renamed from: r, reason: collision with root package name */
    private static final jy f25414r = new jy("", (byte) 11, 19);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jg jgVar) {
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
        if (!getClass().equals(jgVar.getClass())) {
            return getClass().getName().compareTo(jgVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m625a()).compareTo(Boolean.valueOf(jgVar.m625a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m625a() && (iA18 = jr.a(this.f814a, jgVar.f814a)) != 0) {
            return iA18;
        }
        int iCompareTo2 = Boolean.valueOf(m627b()).compareTo(Boolean.valueOf(jgVar.m627b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m627b() && (iA17 = jr.a(this.f813a, jgVar.f813a)) != 0) {
            return iA17;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jgVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA16 = jr.a(this.f818b, jgVar.f818b)) != 0) {
            return iA16;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jgVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA15 = jr.a(this.f820c, jgVar.f820c)) != 0) {
            return iA15;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jgVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA14 = jr.a(this.f812a, jgVar.f812a)) != 0) {
            return iA14;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jgVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA13 = jr.a(this.f821d, jgVar.f821d)) != 0) {
            return iA13;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jgVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA12 = jr.a(this.f822e, jgVar.f822e)) != 0) {
            return iA12;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jgVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA11 = jr.a(this.f823f, jgVar.f823f)) != 0) {
            return iA11;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jgVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA10 = jr.a(this.f824g, jgVar.f824g)) != 0) {
            return iA10;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jgVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA9 = jr.a(this.f817b, jgVar.f817b)) != 0) {
            return iA9;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(jgVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA8 = jr.a(this.f825h, jgVar.f825h)) != 0) {
            return iA8;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(jgVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (l() && (iA7 = jr.a(this.f826i, jgVar.f826i)) != 0) {
            return iA7;
        }
        int iCompareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(jgVar.m()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (m() && (iA6 = jr.a(this.f819c, jgVar.f819c)) != 0) {
            return iA6;
        }
        int iCompareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(jgVar.n()));
        if (iCompareTo14 != 0) {
            return iCompareTo14;
        }
        if (n() && (iA5 = jr.a(this.f827j, jgVar.f827j)) != 0) {
            return iA5;
        }
        int iCompareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(jgVar.o()));
        if (iCompareTo15 != 0) {
            return iCompareTo15;
        }
        if (o() && (iA4 = jr.a(this.f811a, jgVar.f811a)) != 0) {
            return iA4;
        }
        int iCompareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(jgVar.p()));
        if (iCompareTo16 != 0) {
            return iCompareTo16;
        }
        if (p() && (iA3 = jr.a(this.f828k, jgVar.f828k)) != 0) {
            return iA3;
        }
        int iCompareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(jgVar.q()));
        if (iCompareTo17 != 0) {
            return iCompareTo17;
        }
        if (q() && (iA2 = jr.a(this.f816b, jgVar.f816b)) != 0) {
            return iA2;
        }
        int iCompareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(jgVar.r()));
        if (iCompareTo18 != 0) {
            return iCompareTo18;
        }
        if (!r() || (iA = jr.a(this.f829l, jgVar.f829l)) == 0) {
            return 0;
        }
        return iA;
    }

    public long a() {
        return this.f812a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m623a() {
        return this.f818b;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m624a() throws kc {
        if (this.f818b == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f820c != null) {
            return;
        }
        throw new kc("Required field 'appId' was not present! Struct: " + toString());
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) throws kc {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                if (e()) {
                    m624a();
                    return;
                }
                throw new kc("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f814a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f813a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f818b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f820c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 10) {
                        this.f812a = kbVar.mo661a();
                        a(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f821d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 11) {
                        this.f822e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 11) {
                        this.f823f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 11) {
                        this.f824g = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 11:
                    if (b3 == 10) {
                        this.f817b = kbVar.mo661a();
                        b(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 12:
                    if (b3 == 11) {
                        this.f825h = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 13:
                    if (b3 == 11) {
                        this.f826i = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 14:
                    if (b3 == 10) {
                        this.f819c = kbVar.mo661a();
                        c(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 15:
                    if (b3 == 11) {
                        this.f827j = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 16:
                    if (b3 == 8) {
                        this.f811a = kbVar.mo660a();
                        d(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 17:
                    if (b3 == 11) {
                        this.f828k = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 18:
                    if (b3 == 8) {
                        this.f816b = kbVar.mo660a();
                        e(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 19:
                    if (b3 == 11) {
                        this.f829l = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
            }
            ke.a(kbVar, b3);
            kbVar.h();
        }
    }

    public void a(boolean z2) {
        this.f815a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m625a() {
        return this.f814a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m626a(jg jgVar) {
        if (jgVar == null) {
            return false;
        }
        boolean zM625a = m625a();
        boolean zM625a2 = jgVar.m625a();
        if ((zM625a || zM625a2) && !(zM625a && zM625a2 && this.f814a.equals(jgVar.f814a))) {
            return false;
        }
        boolean zM627b = m627b();
        boolean zM627b2 = jgVar.m627b();
        if ((zM627b || zM627b2) && !(zM627b && zM627b2 && this.f813a.m569a(jgVar.f813a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jgVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f818b.equals(jgVar.f818b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jgVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.f820c.equals(jgVar.f820c))) || this.f812a != jgVar.f812a) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jgVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f821d.equals(jgVar.f821d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jgVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f822e.equals(jgVar.f822e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jgVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f823f.equals(jgVar.f823f))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jgVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f824g.equals(jgVar.f824g))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jgVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f817b == jgVar.f817b)) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = jgVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f825h.equals(jgVar.f825h))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = jgVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.f826i.equals(jgVar.f826i))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = jgVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f819c == jgVar.f819c)) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = jgVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.f827j.equals(jgVar.f827j))) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = jgVar.o();
        if ((zO || zO2) && !(zO && zO2 && this.f811a == jgVar.f811a)) {
            return false;
        }
        boolean zP = p();
        boolean zP2 = jgVar.p();
        if ((zP || zP2) && !(zP && zP2 && this.f828k.equals(jgVar.f828k))) {
            return false;
        }
        boolean zQ = q();
        boolean zQ2 = jgVar.q();
        if ((zQ || zQ2) && !(zQ && zQ2 && this.f816b == jgVar.f816b)) {
            return false;
        }
        boolean zR = r();
        boolean zR2 = jgVar.r();
        if (zR || zR2) {
            return zR && zR2 && this.f829l.equals(jgVar.f829l);
        }
        return true;
    }

    public String b() {
        return this.f824g;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m624a();
        kbVar.a(f810a);
        if (this.f814a != null && m625a()) {
            kbVar.a(f25397a);
            kbVar.a(this.f814a);
            kbVar.b();
        }
        if (this.f813a != null && m627b()) {
            kbVar.a(f25398b);
            this.f813a.b(kbVar);
            kbVar.b();
        }
        if (this.f818b != null) {
            kbVar.a(f25399c);
            kbVar.a(this.f818b);
            kbVar.b();
        }
        if (this.f820c != null) {
            kbVar.a(f25400d);
            kbVar.a(this.f820c);
            kbVar.b();
        }
        kbVar.a(f25401e);
        kbVar.a(this.f812a);
        kbVar.b();
        if (this.f821d != null && f()) {
            kbVar.a(f25402f);
            kbVar.a(this.f821d);
            kbVar.b();
        }
        if (this.f822e != null && g()) {
            kbVar.a(f25403g);
            kbVar.a(this.f822e);
            kbVar.b();
        }
        if (this.f823f != null && h()) {
            kbVar.a(f25404h);
            kbVar.a(this.f823f);
            kbVar.b();
        }
        if (this.f824g != null && i()) {
            kbVar.a(f25405i);
            kbVar.a(this.f824g);
            kbVar.b();
        }
        if (j()) {
            kbVar.a(f25406j);
            kbVar.a(this.f817b);
            kbVar.b();
        }
        if (this.f825h != null && k()) {
            kbVar.a(f25407k);
            kbVar.a(this.f825h);
            kbVar.b();
        }
        if (this.f826i != null && l()) {
            kbVar.a(f25408l);
            kbVar.a(this.f826i);
            kbVar.b();
        }
        if (m()) {
            kbVar.a(f25409m);
            kbVar.a(this.f819c);
            kbVar.b();
        }
        if (this.f827j != null && n()) {
            kbVar.a(f25410n);
            kbVar.a(this.f827j);
            kbVar.b();
        }
        if (o()) {
            kbVar.a(f25411o);
            kbVar.mo671a(this.f811a);
            kbVar.b();
        }
        if (this.f828k != null && p()) {
            kbVar.a(f25412p);
            kbVar.a(this.f828k);
            kbVar.b();
        }
        if (q()) {
            kbVar.a(f25413q);
            kbVar.mo671a(this.f816b);
            kbVar.b();
        }
        if (this.f829l != null && r()) {
            kbVar.a(f25414r);
            kbVar.a(this.f829l);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f815a.set(1, z2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m627b() {
        return this.f813a != null;
    }

    public void c(boolean z2) {
        this.f815a.set(2, z2);
    }

    public boolean c() {
        return this.f818b != null;
    }

    public void d(boolean z2) {
        this.f815a.set(3, z2);
    }

    public boolean d() {
        return this.f820c != null;
    }

    public void e(boolean z2) {
        this.f815a.set(4, z2);
    }

    public boolean e() {
        return this.f815a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jg)) {
            return m626a((jg) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f821d != null;
    }

    public boolean g() {
        return this.f822e != null;
    }

    public boolean h() {
        return this.f823f != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f824g != null;
    }

    public boolean j() {
        return this.f815a.get(1);
    }

    public boolean k() {
        return this.f825h != null;
    }

    public boolean l() {
        return this.f826i != null;
    }

    public boolean m() {
        return this.f815a.get(2);
    }

    public boolean n() {
        return this.f827j != null;
    }

    public boolean o() {
        return this.f815a.get(3);
    }

    public boolean p() {
        return this.f828k != null;
    }

    public boolean q() {
        return this.f815a.get(4);
    }

    public boolean r() {
        return this.f829l != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionRegistrationResult(");
        boolean z3 = false;
        if (m625a()) {
            sb.append("debug:");
            String str = this.f814a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m627b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            it itVar = this.f813a;
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
        String str2 = this.f818b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f820c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f812a);
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f821d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("regId:");
            String str5 = this.f822e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("regSecret:");
            String str6 = this.f823f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            String str7 = this.f824g;
            if (str7 == null) {
                sb.append("null");
            } else {
                sb.append(str7);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("registeredAt:");
            sb.append(this.f817b);
        }
        if (k()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str8 = this.f825h;
            if (str8 == null) {
                sb.append("null");
            } else {
                sb.append(str8);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("clientId:");
            String str9 = this.f826i;
            if (str9 == null) {
                sb.append("null");
            } else {
                sb.append(str9);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("costTime:");
            sb.append(this.f819c);
        }
        if (n()) {
            sb.append(", ");
            sb.append("appVersion:");
            String str10 = this.f827j;
            if (str10 == null) {
                sb.append("null");
            } else {
                sb.append(str10);
            }
        }
        if (o()) {
            sb.append(", ");
            sb.append("pushSdkVersionCode:");
            sb.append(this.f811a);
        }
        if (p()) {
            sb.append(", ");
            sb.append("hybridPushEndpoint:");
            String str11 = this.f828k;
            if (str11 == null) {
                sb.append("null");
            } else {
                sb.append(str11);
            }
        }
        if (q()) {
            sb.append(", ");
            sb.append("appVersionCode:");
            sb.append(this.f816b);
        }
        if (r()) {
            sb.append(", ");
            sb.append("region:");
            String str12 = this.f829l;
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
