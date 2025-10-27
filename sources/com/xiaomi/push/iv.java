package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class iv implements jq<iv, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f685a;

    /* renamed from: a, reason: collision with other field name */
    public long f686a;

    /* renamed from: a, reason: collision with other field name */
    public it f687a;

    /* renamed from: a, reason: collision with other field name */
    public ji f688a;

    /* renamed from: a, reason: collision with other field name */
    public String f689a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f691a;

    /* renamed from: a, reason: collision with other field name */
    public short f692a;

    /* renamed from: b, reason: collision with other field name */
    public String f694b;

    /* renamed from: b, reason: collision with other field name */
    public short f695b;

    /* renamed from: c, reason: collision with other field name */
    public String f696c;

    /* renamed from: d, reason: collision with other field name */
    public String f697d;

    /* renamed from: e, reason: collision with other field name */
    public String f698e;

    /* renamed from: f, reason: collision with other field name */
    public String f699f;

    /* renamed from: g, reason: collision with other field name */
    public String f700g;

    /* renamed from: h, reason: collision with other field name */
    public String f701h;

    /* renamed from: i, reason: collision with other field name */
    public String f702i;

    /* renamed from: j, reason: collision with other field name */
    public String f703j;

    /* renamed from: k, reason: collision with other field name */
    public String f704k;

    /* renamed from: l, reason: collision with other field name */
    public String f705l;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f684a = new kg("XmPushActionAckMessage");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25288a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25289b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25290c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25291d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25292e = new jy("", (byte) 10, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25293f = new jy("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25294g = new jy("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25295h = new jy("", (byte) 12, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25296i = new jy("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25297j = new jy("", (byte) 11, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final jy f25298k = new jy("", (byte) 2, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final jy f25299l = new jy("", (byte) 11, 12);

    /* renamed from: m, reason: collision with root package name */
    private static final jy f25300m = new jy("", (byte) 11, 13);

    /* renamed from: n, reason: collision with root package name */
    private static final jy f25301n = new jy("", (byte) 11, 14);

    /* renamed from: o, reason: collision with root package name */
    private static final jy f25302o = new jy("", (byte) 6, 15);

    /* renamed from: p, reason: collision with root package name */
    private static final jy f25303p = new jy("", (byte) 6, 16);

    /* renamed from: q, reason: collision with root package name */
    private static final jy f25304q = new jy("", (byte) 11, 20);

    /* renamed from: r, reason: collision with root package name */
    private static final jy f25305r = new jy("", (byte) 11, 21);

    /* renamed from: s, reason: collision with root package name */
    private static final jy f25306s = new jy("", (byte) 8, 22);

    /* renamed from: t, reason: collision with root package name */
    private static final jy f25307t = new jy("", (byte) 13, 23);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f690a = new BitSet(5);

    /* renamed from: a, reason: collision with other field name */
    public boolean f693a = false;

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(iv ivVar) {
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
        if (!getClass().equals(ivVar.getClass())) {
            return getClass().getName().compareTo(ivVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m572a()).compareTo(Boolean.valueOf(ivVar.m572a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m572a() && (iA20 = jr.a(this.f689a, ivVar.f689a)) != 0) {
            return iA20;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ivVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA19 = jr.a(this.f687a, ivVar.f687a)) != 0) {
            return iA19;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ivVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA18 = jr.a(this.f694b, ivVar.f694b)) != 0) {
            return iA18;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ivVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA17 = jr.a(this.f696c, ivVar.f696c)) != 0) {
            return iA17;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(ivVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA16 = jr.a(this.f686a, ivVar.f686a)) != 0) {
            return iA16;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ivVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA15 = jr.a(this.f697d, ivVar.f697d)) != 0) {
            return iA15;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(ivVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA14 = jr.a(this.f698e, ivVar.f698e)) != 0) {
            return iA14;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(ivVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA13 = jr.a(this.f688a, ivVar.f688a)) != 0) {
            return iA13;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(ivVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA12 = jr.a(this.f699f, ivVar.f699f)) != 0) {
            return iA12;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(ivVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA11 = jr.a(this.f700g, ivVar.f700g)) != 0) {
            return iA11;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(ivVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA10 = jr.a(this.f693a, ivVar.f693a)) != 0) {
            return iA10;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(ivVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (l() && (iA9 = jr.a(this.f701h, ivVar.f701h)) != 0) {
            return iA9;
        }
        int iCompareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(ivVar.m()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (m() && (iA8 = jr.a(this.f702i, ivVar.f702i)) != 0) {
            return iA8;
        }
        int iCompareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(ivVar.n()));
        if (iCompareTo14 != 0) {
            return iCompareTo14;
        }
        if (n() && (iA7 = jr.a(this.f703j, ivVar.f703j)) != 0) {
            return iA7;
        }
        int iCompareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(ivVar.o()));
        if (iCompareTo15 != 0) {
            return iCompareTo15;
        }
        if (o() && (iA6 = jr.a(this.f692a, ivVar.f692a)) != 0) {
            return iA6;
        }
        int iCompareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(ivVar.p()));
        if (iCompareTo16 != 0) {
            return iCompareTo16;
        }
        if (p() && (iA5 = jr.a(this.f695b, ivVar.f695b)) != 0) {
            return iA5;
        }
        int iCompareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(ivVar.q()));
        if (iCompareTo17 != 0) {
            return iCompareTo17;
        }
        if (q() && (iA4 = jr.a(this.f704k, ivVar.f704k)) != 0) {
            return iA4;
        }
        int iCompareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(ivVar.r()));
        if (iCompareTo18 != 0) {
            return iCompareTo18;
        }
        if (r() && (iA3 = jr.a(this.f705l, ivVar.f705l)) != 0) {
            return iA3;
        }
        int iCompareTo19 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(ivVar.s()));
        if (iCompareTo19 != 0) {
            return iCompareTo19;
        }
        if (s() && (iA2 = jr.a(this.f685a, ivVar.f685a)) != 0) {
            return iA2;
        }
        int iCompareTo20 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(ivVar.t()));
        if (iCompareTo20 != 0) {
            return iCompareTo20;
        }
        if (!t() || (iA = jr.a(this.f691a, ivVar.f691a)) == 0) {
            return 0;
        }
        return iA;
    }

    public iv a(long j2) {
        this.f686a = j2;
        a(true);
        return this;
    }

    public iv a(String str) {
        this.f694b = str;
        return this;
    }

    public iv a(short s2) {
        this.f692a = s2;
        c(true);
        return this;
    }

    public void a() throws kc {
        if (this.f694b == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f696c != null) {
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
                    a();
                    return;
                }
                throw new kc("Required field 'messageTs' was not found in serialized data! Struct: " + toString());
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f689a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f687a = itVar;
                        itVar.a(kbVar);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f694b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f696c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 10) {
                        this.f686a = kbVar.mo661a();
                        a(true);
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 11) {
                        this.f697d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f698e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 12) {
                        ji jiVar = new ji();
                        this.f688a = jiVar;
                        jiVar.a(kbVar);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 11) {
                        this.f699f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 11) {
                        this.f700g = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 11:
                    if (b3 == 2) {
                        this.f693a = kbVar.mo672a();
                        b(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 12:
                    if (b3 == 11) {
                        this.f701h = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 13:
                    if (b3 == 11) {
                        this.f702i = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 14:
                    if (b3 == 11) {
                        this.f703j = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 15:
                    if (b3 == 6) {
                        this.f692a = kbVar.mo669a();
                        c(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 16:
                    if (b3 == 6) {
                        this.f695b = kbVar.mo669a();
                        d(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 20:
                    if (b3 == 11) {
                        this.f704k = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 21:
                    if (b3 == 11) {
                        this.f705l = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 22:
                    if (b3 == 8) {
                        this.f685a = kbVar.mo660a();
                        e(true);
                    }
                    kbVar.h();
                    break;
                case 23:
                    if (b3 == 13) {
                        ka kaVarMo664a = kbVar.mo664a();
                        this.f691a = new HashMap(kaVarMo664a.f932a * 2);
                        for (int i2 = 0; i2 < kaVarMo664a.f932a; i2++) {
                            this.f691a.put(kbVar.mo667a(), kbVar.mo667a());
                        }
                        kbVar.i();
                    }
                    kbVar.h();
                    break;
            }
            ke.a(kbVar, b3);
            kbVar.h();
        }
    }

    public void a(boolean z2) {
        this.f690a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m572a() {
        return this.f689a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m573a(iv ivVar) {
        if (ivVar == null) {
            return false;
        }
        boolean zM572a = m572a();
        boolean zM572a2 = ivVar.m572a();
        if ((zM572a || zM572a2) && !(zM572a && zM572a2 && this.f689a.equals(ivVar.f689a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = ivVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f687a.m569a(ivVar.f687a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = ivVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f694b.equals(ivVar.f694b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = ivVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.f696c.equals(ivVar.f696c))) || this.f686a != ivVar.f686a) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = ivVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f697d.equals(ivVar.f697d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = ivVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f698e.equals(ivVar.f698e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = ivVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f688a.m633a(ivVar.f688a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = ivVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f699f.equals(ivVar.f699f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = ivVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f700g.equals(ivVar.f700g))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = ivVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f693a == ivVar.f693a)) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = ivVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.f701h.equals(ivVar.f701h))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = ivVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f702i.equals(ivVar.f702i))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = ivVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.f703j.equals(ivVar.f703j))) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = ivVar.o();
        if ((zO || zO2) && !(zO && zO2 && this.f692a == ivVar.f692a)) {
            return false;
        }
        boolean zP = p();
        boolean zP2 = ivVar.p();
        if ((zP || zP2) && !(zP && zP2 && this.f695b == ivVar.f695b)) {
            return false;
        }
        boolean zQ = q();
        boolean zQ2 = ivVar.q();
        if ((zQ || zQ2) && !(zQ && zQ2 && this.f704k.equals(ivVar.f704k))) {
            return false;
        }
        boolean zR = r();
        boolean zR2 = ivVar.r();
        if ((zR || zR2) && !(zR && zR2 && this.f705l.equals(ivVar.f705l))) {
            return false;
        }
        boolean zS = s();
        boolean zS2 = ivVar.s();
        if ((zS || zS2) && !(zS && zS2 && this.f685a == ivVar.f685a)) {
            return false;
        }
        boolean zT = t();
        boolean zT2 = ivVar.t();
        if (zT || zT2) {
            return zT && zT2 && this.f691a.equals(ivVar.f691a);
        }
        return true;
    }

    public iv b(String str) {
        this.f696c = str;
        return this;
    }

    public iv b(short s2) {
        this.f695b = s2;
        d(true);
        return this;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        a();
        kbVar.a(f684a);
        if (this.f689a != null && m572a()) {
            kbVar.a(f25288a);
            kbVar.a(this.f689a);
            kbVar.b();
        }
        if (this.f687a != null && b()) {
            kbVar.a(f25289b);
            this.f687a.b(kbVar);
            kbVar.b();
        }
        if (this.f694b != null) {
            kbVar.a(f25290c);
            kbVar.a(this.f694b);
            kbVar.b();
        }
        if (this.f696c != null) {
            kbVar.a(f25291d);
            kbVar.a(this.f696c);
            kbVar.b();
        }
        kbVar.a(f25292e);
        kbVar.a(this.f686a);
        kbVar.b();
        if (this.f697d != null && f()) {
            kbVar.a(f25293f);
            kbVar.a(this.f697d);
            kbVar.b();
        }
        if (this.f698e != null && g()) {
            kbVar.a(f25294g);
            kbVar.a(this.f698e);
            kbVar.b();
        }
        if (this.f688a != null && h()) {
            kbVar.a(f25295h);
            this.f688a.b(kbVar);
            kbVar.b();
        }
        if (this.f699f != null && i()) {
            kbVar.a(f25296i);
            kbVar.a(this.f699f);
            kbVar.b();
        }
        if (this.f700g != null && j()) {
            kbVar.a(f25297j);
            kbVar.a(this.f700g);
            kbVar.b();
        }
        if (k()) {
            kbVar.a(f25298k);
            kbVar.a(this.f693a);
            kbVar.b();
        }
        if (this.f701h != null && l()) {
            kbVar.a(f25299l);
            kbVar.a(this.f701h);
            kbVar.b();
        }
        if (this.f702i != null && m()) {
            kbVar.a(f25300m);
            kbVar.a(this.f702i);
            kbVar.b();
        }
        if (this.f703j != null && n()) {
            kbVar.a(f25301n);
            kbVar.a(this.f703j);
            kbVar.b();
        }
        if (o()) {
            kbVar.a(f25302o);
            kbVar.a(this.f692a);
            kbVar.b();
        }
        if (p()) {
            kbVar.a(f25303p);
            kbVar.a(this.f695b);
            kbVar.b();
        }
        if (this.f704k != null && q()) {
            kbVar.a(f25304q);
            kbVar.a(this.f704k);
            kbVar.b();
        }
        if (this.f705l != null && r()) {
            kbVar.a(f25305r);
            kbVar.a(this.f705l);
            kbVar.b();
        }
        if (s()) {
            kbVar.a(f25306s);
            kbVar.mo671a(this.f685a);
            kbVar.b();
        }
        if (this.f691a != null && t()) {
            kbVar.a(f25307t);
            kbVar.a(new ka((byte) 11, (byte) 11, this.f691a.size()));
            for (Map.Entry<String, String> entry : this.f691a.entrySet()) {
                kbVar.a(entry.getKey());
                kbVar.a(entry.getValue());
            }
            kbVar.d();
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f690a.set(1, z2);
    }

    public boolean b() {
        return this.f687a != null;
    }

    public iv c(String str) {
        this.f697d = str;
        return this;
    }

    public void c(boolean z2) {
        this.f690a.set(2, z2);
    }

    public boolean c() {
        return this.f694b != null;
    }

    public iv d(String str) {
        this.f698e = str;
        return this;
    }

    public void d(boolean z2) {
        this.f690a.set(3, z2);
    }

    public boolean d() {
        return this.f696c != null;
    }

    public void e(boolean z2) {
        this.f690a.set(4, z2);
    }

    public boolean e() {
        return this.f690a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof iv)) {
            return m573a((iv) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f697d != null;
    }

    public boolean g() {
        return this.f698e != null;
    }

    public boolean h() {
        return this.f688a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f699f != null;
    }

    public boolean j() {
        return this.f700g != null;
    }

    public boolean k() {
        return this.f690a.get(1);
    }

    public boolean l() {
        return this.f701h != null;
    }

    public boolean m() {
        return this.f702i != null;
    }

    public boolean n() {
        return this.f703j != null;
    }

    public boolean o() {
        return this.f690a.get(2);
    }

    public boolean p() {
        return this.f690a.get(3);
    }

    public boolean q() {
        return this.f704k != null;
    }

    public boolean r() {
        return this.f705l != null;
    }

    public boolean s() {
        return this.f690a.get(4);
    }

    public boolean t() {
        return this.f691a != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionAckMessage(");
        boolean z3 = false;
        if (m572a()) {
            sb.append("debug:");
            String str = this.f689a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            it itVar = this.f687a;
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
        String str2 = this.f694b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f696c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("messageTs:");
        sb.append(this.f686a);
        if (f()) {
            sb.append(", ");
            sb.append("topic:");
            String str4 = this.f697d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str5 = this.f698e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("request:");
            ji jiVar = this.f688a;
            if (jiVar == null) {
                sb.append("null");
            } else {
                sb.append(jiVar);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f699f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f700g;
            if (str7 == null) {
                sb.append("null");
            } else {
                sb.append(str7);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("isOnline:");
            sb.append(this.f693a);
        }
        if (l()) {
            sb.append(", ");
            sb.append("regId:");
            String str8 = this.f701h;
            if (str8 == null) {
                sb.append("null");
            } else {
                sb.append(str8);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("callbackUrl:");
            String str9 = this.f702i;
            if (str9 == null) {
                sb.append("null");
            } else {
                sb.append(str9);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("userAccount:");
            String str10 = this.f703j;
            if (str10 == null) {
                sb.append("null");
            } else {
                sb.append(str10);
            }
        }
        if (o()) {
            sb.append(", ");
            sb.append("deviceStatus:");
            sb.append((int) this.f692a);
        }
        if (p()) {
            sb.append(", ");
            sb.append("geoMsgStatus:");
            sb.append((int) this.f695b);
        }
        if (q()) {
            sb.append(", ");
            sb.append("imeiMd5:");
            String str11 = this.f704k;
            if (str11 == null) {
                sb.append("null");
            } else {
                sb.append(str11);
            }
        }
        if (r()) {
            sb.append(", ");
            sb.append("deviceId:");
            String str12 = this.f705l;
            if (str12 == null) {
                sb.append("null");
            } else {
                sb.append(str12);
            }
        }
        if (s()) {
            sb.append(", ");
            sb.append("passThrough:");
            sb.append(this.f685a);
        }
        if (t()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f691a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
