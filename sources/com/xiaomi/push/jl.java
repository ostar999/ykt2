package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class jl implements jq<jl, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f874a;

    /* renamed from: a, reason: collision with other field name */
    public it f875a;

    /* renamed from: a, reason: collision with other field name */
    public String f876a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f877a = new BitSet(2);

    /* renamed from: a, reason: collision with other field name */
    public boolean f878a = true;

    /* renamed from: b, reason: collision with other field name */
    public String f879b;

    /* renamed from: c, reason: collision with other field name */
    public String f880c;

    /* renamed from: d, reason: collision with other field name */
    public String f881d;

    /* renamed from: e, reason: collision with other field name */
    public String f882e;

    /* renamed from: f, reason: collision with other field name */
    public String f883f;

    /* renamed from: g, reason: collision with other field name */
    public String f884g;

    /* renamed from: h, reason: collision with other field name */
    public String f885h;

    /* renamed from: i, reason: collision with other field name */
    public String f886i;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f873a = new kg("XmPushActionUnRegistration");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25451a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25452b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25453c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25454d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25455e = new jy("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25456f = new jy("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25457g = new jy("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25458h = new jy("", (byte) 11, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25459i = new jy("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25460j = new jy("", (byte) 11, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final jy f25461k = new jy("", (byte) 2, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final jy f25462l = new jy("", (byte) 10, 12);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jl jlVar) {
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
        if (!getClass().equals(jlVar.getClass())) {
            return getClass().getName().compareTo(jlVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m645a()).compareTo(Boolean.valueOf(jlVar.m645a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m645a() && (iA12 = jr.a(this.f876a, jlVar.f876a)) != 0) {
            return iA12;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jlVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA11 = jr.a(this.f875a, jlVar.f875a)) != 0) {
            return iA11;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jlVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA10 = jr.a(this.f879b, jlVar.f879b)) != 0) {
            return iA10;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jlVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA9 = jr.a(this.f880c, jlVar.f880c)) != 0) {
            return iA9;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jlVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA8 = jr.a(this.f881d, jlVar.f881d)) != 0) {
            return iA8;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jlVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA7 = jr.a(this.f882e, jlVar.f882e)) != 0) {
            return iA7;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jlVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA6 = jr.a(this.f883f, jlVar.f883f)) != 0) {
            return iA6;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jlVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA5 = jr.a(this.f884g, jlVar.f884g)) != 0) {
            return iA5;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jlVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA4 = jr.a(this.f885h, jlVar.f885h)) != 0) {
            return iA4;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jlVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA3 = jr.a(this.f886i, jlVar.f886i)) != 0) {
            return iA3;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(jlVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA2 = jr.a(this.f878a, jlVar.f878a)) != 0) {
            return iA2;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(jlVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (!l() || (iA = jr.a(this.f874a, jlVar.f874a)) == 0) {
            return 0;
        }
        return iA;
    }

    public jl a(String str) {
        this.f879b = str;
        return this;
    }

    public void a() throws kc {
        if (this.f879b == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f880c != null) {
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
                a();
                return;
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f876a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f875a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f879b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f880c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 11) {
                        this.f881d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 11) {
                        this.f882e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f883f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 11) {
                        this.f884g = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 11) {
                        this.f885h = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 11) {
                        this.f886i = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 11:
                    if (b3 == 2) {
                        this.f878a = kbVar.mo672a();
                        a(true);
                    }
                    kbVar.h();
                    break;
                case 12:
                    if (b3 == 10) {
                        this.f874a = kbVar.mo661a();
                        b(true);
                    }
                    kbVar.h();
                    break;
            }
            ke.a(kbVar, b3);
            kbVar.h();
        }
    }

    public void a(boolean z2) {
        this.f877a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m645a() {
        return this.f876a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m646a(jl jlVar) {
        if (jlVar == null) {
            return false;
        }
        boolean zM645a = m645a();
        boolean zM645a2 = jlVar.m645a();
        if ((zM645a || zM645a2) && !(zM645a && zM645a2 && this.f876a.equals(jlVar.f876a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jlVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f875a.m569a(jlVar.f875a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jlVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f879b.equals(jlVar.f879b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jlVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f880c.equals(jlVar.f880c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jlVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f881d.equals(jlVar.f881d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jlVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f882e.equals(jlVar.f882e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jlVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f883f.equals(jlVar.f883f))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jlVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f884g.equals(jlVar.f884g))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jlVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f885h.equals(jlVar.f885h))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jlVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f886i.equals(jlVar.f886i))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = jlVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f878a == jlVar.f878a)) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = jlVar.l();
        if (zL || zL2) {
            return zL && zL2 && this.f874a == jlVar.f874a;
        }
        return true;
    }

    public jl b(String str) {
        this.f880c = str;
        return this;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        a();
        kbVar.a(f873a);
        if (this.f876a != null && m645a()) {
            kbVar.a(f25451a);
            kbVar.a(this.f876a);
            kbVar.b();
        }
        if (this.f875a != null && b()) {
            kbVar.a(f25452b);
            this.f875a.b(kbVar);
            kbVar.b();
        }
        if (this.f879b != null) {
            kbVar.a(f25453c);
            kbVar.a(this.f879b);
            kbVar.b();
        }
        if (this.f880c != null) {
            kbVar.a(f25454d);
            kbVar.a(this.f880c);
            kbVar.b();
        }
        if (this.f881d != null && e()) {
            kbVar.a(f25455e);
            kbVar.a(this.f881d);
            kbVar.b();
        }
        if (this.f882e != null && f()) {
            kbVar.a(f25456f);
            kbVar.a(this.f882e);
            kbVar.b();
        }
        if (this.f883f != null && g()) {
            kbVar.a(f25457g);
            kbVar.a(this.f883f);
            kbVar.b();
        }
        if (this.f884g != null && h()) {
            kbVar.a(f25458h);
            kbVar.a(this.f884g);
            kbVar.b();
        }
        if (this.f885h != null && i()) {
            kbVar.a(f25459i);
            kbVar.a(this.f885h);
            kbVar.b();
        }
        if (this.f886i != null && j()) {
            kbVar.a(f25460j);
            kbVar.a(this.f886i);
            kbVar.b();
        }
        if (k()) {
            kbVar.a(f25461k);
            kbVar.a(this.f878a);
            kbVar.b();
        }
        if (l()) {
            kbVar.a(f25462l);
            kbVar.a(this.f874a);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f877a.set(1, z2);
    }

    public boolean b() {
        return this.f875a != null;
    }

    public jl c(String str) {
        this.f881d = str;
        return this;
    }

    public boolean c() {
        return this.f879b != null;
    }

    public jl d(String str) {
        this.f883f = str;
        return this;
    }

    public boolean d() {
        return this.f880c != null;
    }

    public jl e(String str) {
        this.f884g = str;
        return this;
    }

    public boolean e() {
        return this.f881d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jl)) {
            return m646a((jl) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f882e != null;
    }

    public boolean g() {
        return this.f883f != null;
    }

    public boolean h() {
        return this.f884g != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f885h != null;
    }

    public boolean j() {
        return this.f886i != null;
    }

    public boolean k() {
        return this.f877a.get(0);
    }

    public boolean l() {
        return this.f877a.get(1);
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionUnRegistration(");
        boolean z3 = false;
        if (m645a()) {
            sb.append("debug:");
            String str = this.f876a;
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
            it itVar = this.f875a;
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
        String str2 = this.f879b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f880c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("regId:");
            String str4 = this.f881d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("appVersion:");
            String str5 = this.f882e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f883f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("token:");
            String str7 = this.f884g;
            if (str7 == null) {
                sb.append("null");
            } else {
                sb.append(str7);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("deviceId:");
            String str8 = this.f885h;
            if (str8 == null) {
                sb.append("null");
            } else {
                sb.append(str8);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str9 = this.f886i;
            if (str9 == null) {
                sb.append("null");
            } else {
                sb.append(str9);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("needAck:");
            sb.append(this.f878a);
        }
        if (l()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.f874a);
        }
        sb.append(")");
        return sb.toString();
    }
}
