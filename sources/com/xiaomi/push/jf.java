package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class jf implements jq<jf, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f781a;

    /* renamed from: a, reason: collision with other field name */
    public long f782a;

    /* renamed from: a, reason: collision with other field name */
    public is f783a;

    /* renamed from: a, reason: collision with other field name */
    public it f784a;

    /* renamed from: a, reason: collision with other field name */
    public String f785a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f787a;

    /* renamed from: b, reason: collision with other field name */
    public int f789b;

    /* renamed from: b, reason: collision with other field name */
    public long f790b;

    /* renamed from: b, reason: collision with other field name */
    public String f791b;

    /* renamed from: c, reason: collision with other field name */
    public int f793c;

    /* renamed from: c, reason: collision with other field name */
    public String f794c;

    /* renamed from: d, reason: collision with other field name */
    public String f795d;

    /* renamed from: e, reason: collision with other field name */
    public String f796e;

    /* renamed from: f, reason: collision with other field name */
    public String f797f;

    /* renamed from: g, reason: collision with other field name */
    public String f798g;

    /* renamed from: h, reason: collision with other field name */
    public String f799h;

    /* renamed from: i, reason: collision with other field name */
    public String f800i;

    /* renamed from: j, reason: collision with other field name */
    public String f801j;

    /* renamed from: k, reason: collision with other field name */
    public String f802k;

    /* renamed from: l, reason: collision with other field name */
    public String f803l;

    /* renamed from: m, reason: collision with other field name */
    public String f804m;

    /* renamed from: n, reason: collision with other field name */
    public String f805n;

    /* renamed from: o, reason: collision with other field name */
    public String f806o;

    /* renamed from: p, reason: collision with other field name */
    public String f807p;

    /* renamed from: q, reason: collision with other field name */
    public String f808q;

    /* renamed from: r, reason: collision with other field name */
    public String f809r;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f780a = new kg("XmPushActionRegistration");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25371a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25372b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25373c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25374d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25375e = new jy("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25376f = new jy("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25377g = new jy("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25378h = new jy("", (byte) 11, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25379i = new jy("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25380j = new jy("", (byte) 11, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final jy f25381k = new jy("", (byte) 11, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final jy f25382l = new jy("", (byte) 11, 12);

    /* renamed from: m, reason: collision with root package name */
    private static final jy f25383m = new jy("", (byte) 8, 13);

    /* renamed from: n, reason: collision with root package name */
    private static final jy f25384n = new jy("", (byte) 8, 14);

    /* renamed from: o, reason: collision with root package name */
    private static final jy f25385o = new jy("", (byte) 11, 15);

    /* renamed from: p, reason: collision with root package name */
    private static final jy f25386p = new jy("", (byte) 11, 16);

    /* renamed from: q, reason: collision with root package name */
    private static final jy f25387q = new jy("", (byte) 11, 17);

    /* renamed from: r, reason: collision with root package name */
    private static final jy f25388r = new jy("", (byte) 11, 18);

    /* renamed from: s, reason: collision with root package name */
    private static final jy f25389s = new jy("", (byte) 8, 19);

    /* renamed from: t, reason: collision with root package name */
    private static final jy f25390t = new jy("", (byte) 8, 20);

    /* renamed from: u, reason: collision with root package name */
    private static final jy f25391u = new jy("", (byte) 2, 21);

    /* renamed from: v, reason: collision with root package name */
    private static final jy f25392v = new jy("", (byte) 10, 22);

    /* renamed from: w, reason: collision with root package name */
    private static final jy f25393w = new jy("", (byte) 10, 23);

    /* renamed from: x, reason: collision with root package name */
    private static final jy f25394x = new jy("", (byte) 11, 24);

    /* renamed from: y, reason: collision with root package name */
    private static final jy f25395y = new jy("", (byte) 11, 25);

    /* renamed from: z, reason: collision with root package name */
    private static final jy f25396z = new jy("", (byte) 13, 100);
    private static final jy A = new jy("", (byte) 2, 101);
    private static final jy B = new jy("", (byte) 11, 102);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f786a = new BitSet(7);

    /* renamed from: a, reason: collision with other field name */
    public boolean f788a = true;

    /* renamed from: b, reason: collision with other field name */
    public boolean f792b = false;

    public boolean A() {
        return this.f786a.get(6);
    }

    public boolean B() {
        return this.f809r != null;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jf jfVar) {
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
        int iA19;
        int iA20;
        int iA21;
        int iA22;
        int iA23;
        int iA24;
        int iA25;
        int iA26;
        int iA27;
        int iA28;
        if (!getClass().equals(jfVar.getClass())) {
            return getClass().getName().compareTo(jfVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m619a()).compareTo(Boolean.valueOf(jfVar.m619a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m619a() && (iA28 = jr.a(this.f785a, jfVar.f785a)) != 0) {
            return iA28;
        }
        int iCompareTo2 = Boolean.valueOf(m621b()).compareTo(Boolean.valueOf(jfVar.m621b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m621b() && (iA27 = jr.a(this.f784a, jfVar.f784a)) != 0) {
            return iA27;
        }
        int iCompareTo3 = Boolean.valueOf(m622c()).compareTo(Boolean.valueOf(jfVar.m622c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m622c() && (iA26 = jr.a(this.f791b, jfVar.f791b)) != 0) {
            return iA26;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jfVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA25 = jr.a(this.f794c, jfVar.f794c)) != 0) {
            return iA25;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jfVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA24 = jr.a(this.f795d, jfVar.f795d)) != 0) {
            return iA24;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jfVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA23 = jr.a(this.f796e, jfVar.f796e)) != 0) {
            return iA23;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jfVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA22 = jr.a(this.f797f, jfVar.f797f)) != 0) {
            return iA22;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jfVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA21 = jr.a(this.f798g, jfVar.f798g)) != 0) {
            return iA21;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jfVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA20 = jr.a(this.f799h, jfVar.f799h)) != 0) {
            return iA20;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jfVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA19 = jr.a(this.f800i, jfVar.f800i)) != 0) {
            return iA19;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(jfVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA18 = jr.a(this.f801j, jfVar.f801j)) != 0) {
            return iA18;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(jfVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (l() && (iA17 = jr.a(this.f802k, jfVar.f802k)) != 0) {
            return iA17;
        }
        int iCompareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(jfVar.m()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (m() && (iA16 = jr.a(this.f781a, jfVar.f781a)) != 0) {
            return iA16;
        }
        int iCompareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(jfVar.n()));
        if (iCompareTo14 != 0) {
            return iCompareTo14;
        }
        if (n() && (iA15 = jr.a(this.f789b, jfVar.f789b)) != 0) {
            return iA15;
        }
        int iCompareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(jfVar.o()));
        if (iCompareTo15 != 0) {
            return iCompareTo15;
        }
        if (o() && (iA14 = jr.a(this.f803l, jfVar.f803l)) != 0) {
            return iA14;
        }
        int iCompareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(jfVar.p()));
        if (iCompareTo16 != 0) {
            return iCompareTo16;
        }
        if (p() && (iA13 = jr.a(this.f804m, jfVar.f804m)) != 0) {
            return iA13;
        }
        int iCompareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(jfVar.q()));
        if (iCompareTo17 != 0) {
            return iCompareTo17;
        }
        if (q() && (iA12 = jr.a(this.f805n, jfVar.f805n)) != 0) {
            return iA12;
        }
        int iCompareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(jfVar.r()));
        if (iCompareTo18 != 0) {
            return iCompareTo18;
        }
        if (r() && (iA11 = jr.a(this.f806o, jfVar.f806o)) != 0) {
            return iA11;
        }
        int iCompareTo19 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(jfVar.s()));
        if (iCompareTo19 != 0) {
            return iCompareTo19;
        }
        if (s() && (iA10 = jr.a(this.f793c, jfVar.f793c)) != 0) {
            return iA10;
        }
        int iCompareTo20 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(jfVar.t()));
        if (iCompareTo20 != 0) {
            return iCompareTo20;
        }
        if (t() && (iA9 = jr.a(this.f783a, jfVar.f783a)) != 0) {
            return iA9;
        }
        int iCompareTo21 = Boolean.valueOf(u()).compareTo(Boolean.valueOf(jfVar.u()));
        if (iCompareTo21 != 0) {
            return iCompareTo21;
        }
        if (u() && (iA8 = jr.a(this.f788a, jfVar.f788a)) != 0) {
            return iA8;
        }
        int iCompareTo22 = Boolean.valueOf(v()).compareTo(Boolean.valueOf(jfVar.v()));
        if (iCompareTo22 != 0) {
            return iCompareTo22;
        }
        if (v() && (iA7 = jr.a(this.f782a, jfVar.f782a)) != 0) {
            return iA7;
        }
        int iCompareTo23 = Boolean.valueOf(w()).compareTo(Boolean.valueOf(jfVar.w()));
        if (iCompareTo23 != 0) {
            return iCompareTo23;
        }
        if (w() && (iA6 = jr.a(this.f790b, jfVar.f790b)) != 0) {
            return iA6;
        }
        int iCompareTo24 = Boolean.valueOf(x()).compareTo(Boolean.valueOf(jfVar.x()));
        if (iCompareTo24 != 0) {
            return iCompareTo24;
        }
        if (x() && (iA5 = jr.a(this.f807p, jfVar.f807p)) != 0) {
            return iA5;
        }
        int iCompareTo25 = Boolean.valueOf(y()).compareTo(Boolean.valueOf(jfVar.y()));
        if (iCompareTo25 != 0) {
            return iCompareTo25;
        }
        if (y() && (iA4 = jr.a(this.f808q, jfVar.f808q)) != 0) {
            return iA4;
        }
        int iCompareTo26 = Boolean.valueOf(z()).compareTo(Boolean.valueOf(jfVar.z()));
        if (iCompareTo26 != 0) {
            return iCompareTo26;
        }
        if (z() && (iA3 = jr.a(this.f787a, jfVar.f787a)) != 0) {
            return iA3;
        }
        int iCompareTo27 = Boolean.valueOf(A()).compareTo(Boolean.valueOf(jfVar.A()));
        if (iCompareTo27 != 0) {
            return iCompareTo27;
        }
        if (A() && (iA2 = jr.a(this.f792b, jfVar.f792b)) != 0) {
            return iA2;
        }
        int iCompareTo28 = Boolean.valueOf(B()).compareTo(Boolean.valueOf(jfVar.B()));
        if (iCompareTo28 != 0) {
            return iCompareTo28;
        }
        if (!B() || (iA = jr.a(this.f809r, jfVar.f809r)) == 0) {
            return 0;
        }
        return iA;
    }

    public jf a(int i2) {
        this.f781a = i2;
        a(true);
        return this;
    }

    public jf a(is isVar) {
        this.f783a = isVar;
        return this;
    }

    public jf a(String str) {
        this.f791b = str;
        return this;
    }

    public String a() {
        return this.f791b;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m618a() throws kc {
        if (this.f791b == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f794c == null) {
            throw new kc("Required field 'appId' was not present! Struct: " + toString());
        }
        if (this.f797f != null) {
            return;
        }
        throw new kc("Required field 'token' was not present! Struct: " + toString());
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) throws kc {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                m618a();
                return;
            }
            short s2 = jyVarMo662a.f928a;
            switch (s2) {
                case 1:
                    if (b3 == 11) {
                        this.f785a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f784a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f791b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f794c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 11) {
                        this.f795d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 11) {
                        this.f796e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f797f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 11) {
                        this.f798g = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 11) {
                        this.f799h = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 11) {
                        this.f800i = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 11:
                    if (b3 == 11) {
                        this.f801j = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 12:
                    if (b3 == 11) {
                        this.f802k = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 13:
                    if (b3 == 8) {
                        this.f781a = kbVar.mo660a();
                        a(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 14:
                    if (b3 == 8) {
                        this.f789b = kbVar.mo660a();
                        b(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 15:
                    if (b3 == 11) {
                        this.f803l = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 16:
                    if (b3 == 11) {
                        this.f804m = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 17:
                    if (b3 == 11) {
                        this.f805n = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 18:
                    if (b3 == 11) {
                        this.f806o = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 19:
                    if (b3 == 8) {
                        this.f793c = kbVar.mo660a();
                        c(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 20:
                    if (b3 == 8) {
                        this.f783a = is.a(kbVar.mo660a());
                        continue;
                    }
                    kbVar.h();
                    break;
                case 21:
                    if (b3 == 2) {
                        this.f788a = kbVar.mo672a();
                        d(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 22:
                    if (b3 == 10) {
                        this.f782a = kbVar.mo661a();
                        e(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 23:
                    if (b3 == 10) {
                        this.f790b = kbVar.mo661a();
                        f(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 24:
                    if (b3 == 11) {
                        this.f807p = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 25:
                    if (b3 == 11) {
                        this.f808q = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                default:
                    switch (s2) {
                        case 100:
                            if (b3 == 13) {
                                ka kaVarMo664a = kbVar.mo664a();
                                this.f787a = new HashMap(kaVarMo664a.f932a * 2);
                                for (int i2 = 0; i2 < kaVarMo664a.f932a; i2++) {
                                    this.f787a.put(kbVar.mo667a(), kbVar.mo667a());
                                }
                                kbVar.i();
                            }
                            kbVar.h();
                            break;
                        case 101:
                            if (b3 == 2) {
                                this.f792b = kbVar.mo672a();
                                g(true);
                                continue;
                            }
                            kbVar.h();
                            break;
                        case 102:
                            if (b3 == 11) {
                                this.f809r = kbVar.mo667a();
                                continue;
                            }
                            kbVar.h();
                            break;
                    }
            }
            ke.a(kbVar, b3);
            kbVar.h();
        }
    }

    public void a(boolean z2) {
        this.f786a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m619a() {
        return this.f785a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m620a(jf jfVar) {
        if (jfVar == null) {
            return false;
        }
        boolean zM619a = m619a();
        boolean zM619a2 = jfVar.m619a();
        if ((zM619a || zM619a2) && !(zM619a && zM619a2 && this.f785a.equals(jfVar.f785a))) {
            return false;
        }
        boolean zM621b = m621b();
        boolean zM621b2 = jfVar.m621b();
        if ((zM621b || zM621b2) && !(zM621b && zM621b2 && this.f784a.m569a(jfVar.f784a))) {
            return false;
        }
        boolean zM622c = m622c();
        boolean zM622c2 = jfVar.m622c();
        if ((zM622c || zM622c2) && !(zM622c && zM622c2 && this.f791b.equals(jfVar.f791b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jfVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f794c.equals(jfVar.f794c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jfVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f795d.equals(jfVar.f795d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jfVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f796e.equals(jfVar.f796e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jfVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f797f.equals(jfVar.f797f))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jfVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f798g.equals(jfVar.f798g))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jfVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f799h.equals(jfVar.f799h))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jfVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f800i.equals(jfVar.f800i))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = jfVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f801j.equals(jfVar.f801j))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = jfVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.f802k.equals(jfVar.f802k))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = jfVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f781a == jfVar.f781a)) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = jfVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.f789b == jfVar.f789b)) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = jfVar.o();
        if ((zO || zO2) && !(zO && zO2 && this.f803l.equals(jfVar.f803l))) {
            return false;
        }
        boolean zP = p();
        boolean zP2 = jfVar.p();
        if ((zP || zP2) && !(zP && zP2 && this.f804m.equals(jfVar.f804m))) {
            return false;
        }
        boolean zQ = q();
        boolean zQ2 = jfVar.q();
        if ((zQ || zQ2) && !(zQ && zQ2 && this.f805n.equals(jfVar.f805n))) {
            return false;
        }
        boolean zR = r();
        boolean zR2 = jfVar.r();
        if ((zR || zR2) && !(zR && zR2 && this.f806o.equals(jfVar.f806o))) {
            return false;
        }
        boolean zS = s();
        boolean zS2 = jfVar.s();
        if ((zS || zS2) && !(zS && zS2 && this.f793c == jfVar.f793c)) {
            return false;
        }
        boolean zT = t();
        boolean zT2 = jfVar.t();
        if ((zT || zT2) && !(zT && zT2 && this.f783a.equals(jfVar.f783a))) {
            return false;
        }
        boolean zU = u();
        boolean zU2 = jfVar.u();
        if ((zU || zU2) && !(zU && zU2 && this.f788a == jfVar.f788a)) {
            return false;
        }
        boolean zV = v();
        boolean zV2 = jfVar.v();
        if ((zV || zV2) && !(zV && zV2 && this.f782a == jfVar.f782a)) {
            return false;
        }
        boolean zW = w();
        boolean zW2 = jfVar.w();
        if ((zW || zW2) && !(zW && zW2 && this.f790b == jfVar.f790b)) {
            return false;
        }
        boolean zX = x();
        boolean zX2 = jfVar.x();
        if ((zX || zX2) && !(zX && zX2 && this.f807p.equals(jfVar.f807p))) {
            return false;
        }
        boolean zY = y();
        boolean zY2 = jfVar.y();
        if ((zY || zY2) && !(zY && zY2 && this.f808q.equals(jfVar.f808q))) {
            return false;
        }
        boolean z2 = z();
        boolean z3 = jfVar.z();
        if ((z2 || z3) && !(z2 && z3 && this.f787a.equals(jfVar.f787a))) {
            return false;
        }
        boolean zA = A();
        boolean zA2 = jfVar.A();
        if ((zA || zA2) && !(zA && zA2 && this.f792b == jfVar.f792b)) {
            return false;
        }
        boolean zB = B();
        boolean zB2 = jfVar.B();
        if (zB || zB2) {
            return zB && zB2 && this.f809r.equals(jfVar.f809r);
        }
        return true;
    }

    public jf b(int i2) {
        this.f789b = i2;
        b(true);
        return this;
    }

    public jf b(String str) {
        this.f794c = str;
        return this;
    }

    public String b() {
        return this.f794c;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m618a();
        kbVar.a(f780a);
        if (this.f785a != null && m619a()) {
            kbVar.a(f25371a);
            kbVar.a(this.f785a);
            kbVar.b();
        }
        if (this.f784a != null && m621b()) {
            kbVar.a(f25372b);
            this.f784a.b(kbVar);
            kbVar.b();
        }
        if (this.f791b != null) {
            kbVar.a(f25373c);
            kbVar.a(this.f791b);
            kbVar.b();
        }
        if (this.f794c != null) {
            kbVar.a(f25374d);
            kbVar.a(this.f794c);
            kbVar.b();
        }
        if (this.f795d != null && e()) {
            kbVar.a(f25375e);
            kbVar.a(this.f795d);
            kbVar.b();
        }
        if (this.f796e != null && f()) {
            kbVar.a(f25376f);
            kbVar.a(this.f796e);
            kbVar.b();
        }
        if (this.f797f != null) {
            kbVar.a(f25377g);
            kbVar.a(this.f797f);
            kbVar.b();
        }
        if (this.f798g != null && h()) {
            kbVar.a(f25378h);
            kbVar.a(this.f798g);
            kbVar.b();
        }
        if (this.f799h != null && i()) {
            kbVar.a(f25379i);
            kbVar.a(this.f799h);
            kbVar.b();
        }
        if (this.f800i != null && j()) {
            kbVar.a(f25380j);
            kbVar.a(this.f800i);
            kbVar.b();
        }
        if (this.f801j != null && k()) {
            kbVar.a(f25381k);
            kbVar.a(this.f801j);
            kbVar.b();
        }
        if (this.f802k != null && l()) {
            kbVar.a(f25382l);
            kbVar.a(this.f802k);
            kbVar.b();
        }
        if (m()) {
            kbVar.a(f25383m);
            kbVar.mo671a(this.f781a);
            kbVar.b();
        }
        if (n()) {
            kbVar.a(f25384n);
            kbVar.mo671a(this.f789b);
            kbVar.b();
        }
        if (this.f803l != null && o()) {
            kbVar.a(f25385o);
            kbVar.a(this.f803l);
            kbVar.b();
        }
        if (this.f804m != null && p()) {
            kbVar.a(f25386p);
            kbVar.a(this.f804m);
            kbVar.b();
        }
        if (this.f805n != null && q()) {
            kbVar.a(f25387q);
            kbVar.a(this.f805n);
            kbVar.b();
        }
        if (this.f806o != null && r()) {
            kbVar.a(f25388r);
            kbVar.a(this.f806o);
            kbVar.b();
        }
        if (s()) {
            kbVar.a(f25389s);
            kbVar.mo671a(this.f793c);
            kbVar.b();
        }
        if (this.f783a != null && t()) {
            kbVar.a(f25390t);
            kbVar.mo671a(this.f783a.a());
            kbVar.b();
        }
        if (u()) {
            kbVar.a(f25391u);
            kbVar.a(this.f788a);
            kbVar.b();
        }
        if (v()) {
            kbVar.a(f25392v);
            kbVar.a(this.f782a);
            kbVar.b();
        }
        if (w()) {
            kbVar.a(f25393w);
            kbVar.a(this.f790b);
            kbVar.b();
        }
        if (this.f807p != null && x()) {
            kbVar.a(f25394x);
            kbVar.a(this.f807p);
            kbVar.b();
        }
        if (this.f808q != null && y()) {
            kbVar.a(f25395y);
            kbVar.a(this.f808q);
            kbVar.b();
        }
        if (this.f787a != null && z()) {
            kbVar.a(f25396z);
            kbVar.a(new ka((byte) 11, (byte) 11, this.f787a.size()));
            for (Map.Entry<String, String> entry : this.f787a.entrySet()) {
                kbVar.a(entry.getKey());
                kbVar.a(entry.getValue());
            }
            kbVar.d();
            kbVar.b();
        }
        if (A()) {
            kbVar.a(A);
            kbVar.a(this.f792b);
            kbVar.b();
        }
        if (this.f809r != null && B()) {
            kbVar.a(B);
            kbVar.a(this.f809r);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f786a.set(1, z2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m621b() {
        return this.f784a != null;
    }

    public jf c(int i2) {
        this.f793c = i2;
        c(true);
        return this;
    }

    public jf c(String str) {
        this.f795d = str;
        return this;
    }

    public String c() {
        return this.f797f;
    }

    public void c(boolean z2) {
        this.f786a.set(2, z2);
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m622c() {
        return this.f791b != null;
    }

    public jf d(String str) {
        this.f796e = str;
        return this;
    }

    public void d(boolean z2) {
        this.f786a.set(3, z2);
    }

    public boolean d() {
        return this.f794c != null;
    }

    public jf e(String str) {
        this.f797f = str;
        return this;
    }

    public void e(boolean z2) {
        this.f786a.set(4, z2);
    }

    public boolean e() {
        return this.f795d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jf)) {
            return m620a((jf) obj);
        }
        return false;
    }

    public jf f(String str) {
        this.f798g = str;
        return this;
    }

    public void f(boolean z2) {
        this.f786a.set(5, z2);
    }

    public boolean f() {
        return this.f796e != null;
    }

    public jf g(String str) {
        this.f802k = str;
        return this;
    }

    public void g(boolean z2) {
        this.f786a.set(6, z2);
    }

    public boolean g() {
        return this.f797f != null;
    }

    public jf h(String str) {
        this.f803l = str;
        return this;
    }

    public boolean h() {
        return this.f798g != null;
    }

    public int hashCode() {
        return 0;
    }

    public jf i(String str) {
        this.f804m = str;
        return this;
    }

    public boolean i() {
        return this.f799h != null;
    }

    public jf j(String str) {
        this.f805n = str;
        return this;
    }

    public boolean j() {
        return this.f800i != null;
    }

    public jf k(String str) {
        this.f806o = str;
        return this;
    }

    public boolean k() {
        return this.f801j != null;
    }

    public boolean l() {
        return this.f802k != null;
    }

    public boolean m() {
        return this.f786a.get(0);
    }

    public boolean n() {
        return this.f786a.get(1);
    }

    public boolean o() {
        return this.f803l != null;
    }

    public boolean p() {
        return this.f804m != null;
    }

    public boolean q() {
        return this.f805n != null;
    }

    public boolean r() {
        return this.f806o != null;
    }

    public boolean s() {
        return this.f786a.get(2);
    }

    public boolean t() {
        return this.f783a != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionRegistration(");
        boolean z3 = false;
        if (m619a()) {
            sb.append("debug:");
            String str = this.f785a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m621b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            it itVar = this.f784a;
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
        String str2 = this.f791b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f794c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("appVersion:");
            String str4 = this.f795d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f796e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        sb.append(", ");
        sb.append("token:");
        String str6 = this.f797f;
        if (str6 == null) {
            sb.append("null");
        } else {
            sb.append(str6);
        }
        if (h()) {
            sb.append(", ");
            sb.append("deviceId:");
            String str7 = this.f798g;
            if (str7 == null) {
                sb.append("null");
            } else {
                sb.append(str7);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str8 = this.f799h;
            if (str8 == null) {
                sb.append("null");
            } else {
                sb.append(str8);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("sdkVersion:");
            String str9 = this.f800i;
            if (str9 == null) {
                sb.append("null");
            } else {
                sb.append(str9);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("regId:");
            String str10 = this.f801j;
            if (str10 == null) {
                sb.append("null");
            } else {
                sb.append(str10);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("pushSdkVersionName:");
            String str11 = this.f802k;
            if (str11 == null) {
                sb.append("null");
            } else {
                sb.append(str11);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("pushSdkVersionCode:");
            sb.append(this.f781a);
        }
        if (n()) {
            sb.append(", ");
            sb.append("appVersionCode:");
            sb.append(this.f789b);
        }
        if (o()) {
            sb.append(", ");
            sb.append("androidId:");
            String str12 = this.f803l;
            if (str12 == null) {
                sb.append("null");
            } else {
                sb.append(str12);
            }
        }
        if (p()) {
            sb.append(", ");
            sb.append("imei:");
            String str13 = this.f804m;
            if (str13 == null) {
                sb.append("null");
            } else {
                sb.append(str13);
            }
        }
        if (q()) {
            sb.append(", ");
            sb.append("serial:");
            String str14 = this.f805n;
            if (str14 == null) {
                sb.append("null");
            } else {
                sb.append(str14);
            }
        }
        if (r()) {
            sb.append(", ");
            sb.append("imeiMd5:");
            String str15 = this.f806o;
            if (str15 == null) {
                sb.append("null");
            } else {
                sb.append(str15);
            }
        }
        if (s()) {
            sb.append(", ");
            sb.append("spaceId:");
            sb.append(this.f793c);
        }
        if (t()) {
            sb.append(", ");
            sb.append("reason:");
            is isVar = this.f783a;
            if (isVar == null) {
                sb.append("null");
            } else {
                sb.append(isVar);
            }
        }
        if (u()) {
            sb.append(", ");
            sb.append("validateToken:");
            sb.append(this.f788a);
        }
        if (v()) {
            sb.append(", ");
            sb.append("miid:");
            sb.append(this.f782a);
        }
        if (w()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.f790b);
        }
        if (x()) {
            sb.append(", ");
            sb.append("subImei:");
            String str16 = this.f807p;
            if (str16 == null) {
                sb.append("null");
            } else {
                sb.append(str16);
            }
        }
        if (y()) {
            sb.append(", ");
            sb.append("subImeiMd5:");
            String str17 = this.f808q;
            if (str17 == null) {
                sb.append("null");
            } else {
                sb.append(str17);
            }
        }
        if (z()) {
            sb.append(", ");
            sb.append("connectionAttrs:");
            Map<String, String> map = this.f787a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        }
        if (A()) {
            sb.append(", ");
            sb.append("cleanOldRegInfo:");
            sb.append(this.f792b);
        }
        if (B()) {
            sb.append(", ");
            sb.append("oldRegId:");
            String str18 = this.f809r;
            if (str18 == null) {
                sb.append("null");
            } else {
                sb.append(str18);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public boolean u() {
        return this.f786a.get(3);
    }

    public boolean v() {
        return this.f786a.get(4);
    }

    public boolean w() {
        return this.f786a.get(5);
    }

    public boolean x() {
        return this.f807p != null;
    }

    public boolean y() {
        return this.f808q != null;
    }

    public boolean z() {
        return this.f787a != null;
    }
}
