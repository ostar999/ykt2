package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class jk implements jq<jk, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f863a;

    /* renamed from: a, reason: collision with other field name */
    public it f864a;

    /* renamed from: a, reason: collision with other field name */
    public String f865a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f866a = new BitSet(1);

    /* renamed from: b, reason: collision with other field name */
    public String f867b;

    /* renamed from: c, reason: collision with other field name */
    public String f868c;

    /* renamed from: d, reason: collision with other field name */
    public String f869d;

    /* renamed from: e, reason: collision with other field name */
    public String f870e;

    /* renamed from: f, reason: collision with other field name */
    public String f871f;

    /* renamed from: g, reason: collision with other field name */
    public String f872g;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f862a = new kg("XmPushActionSubscriptionResult");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25442a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25443b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25444c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25445d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25446e = new jy("", (byte) 10, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25447f = new jy("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25448g = new jy("", (byte) 11, 8);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25449h = new jy("", (byte) 11, 9);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25450i = new jy("", (byte) 11, 10);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jk jkVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        if (!getClass().equals(jkVar.getClass())) {
            return getClass().getName().compareTo(jkVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m642a()).compareTo(Boolean.valueOf(jkVar.m642a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m642a() && (iA9 = jr.a(this.f865a, jkVar.f865a)) != 0) {
            return iA9;
        }
        int iCompareTo2 = Boolean.valueOf(m644b()).compareTo(Boolean.valueOf(jkVar.m644b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m644b() && (iA8 = jr.a(this.f864a, jkVar.f864a)) != 0) {
            return iA8;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jkVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA7 = jr.a(this.f867b, jkVar.f867b)) != 0) {
            return iA7;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jkVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA6 = jr.a(this.f868c, jkVar.f868c)) != 0) {
            return iA6;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jkVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA5 = jr.a(this.f863a, jkVar.f863a)) != 0) {
            return iA5;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jkVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA4 = jr.a(this.f869d, jkVar.f869d)) != 0) {
            return iA4;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jkVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA3 = jr.a(this.f870e, jkVar.f870e)) != 0) {
            return iA3;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jkVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA2 = jr.a(this.f871f, jkVar.f871f)) != 0) {
            return iA2;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jkVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (!i() || (iA = jr.a(this.f872g, jkVar.f872g)) == 0) {
            return 0;
        }
        return iA;
    }

    public String a() {
        return this.f870e;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m641a() throws kc {
        if (this.f867b != null) {
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
                m641a();
                return;
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f865a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f864a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f867b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f868c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 10) {
                        this.f863a = kbVar.mo661a();
                        a(true);
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f869d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 11) {
                        this.f870e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 11) {
                        this.f871f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 11) {
                        this.f872g = kbVar.mo667a();
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
        this.f866a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m642a() {
        return this.f865a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m643a(jk jkVar) {
        if (jkVar == null) {
            return false;
        }
        boolean zM642a = m642a();
        boolean zM642a2 = jkVar.m642a();
        if ((zM642a || zM642a2) && !(zM642a && zM642a2 && this.f865a.equals(jkVar.f865a))) {
            return false;
        }
        boolean zM644b = m644b();
        boolean zM644b2 = jkVar.m644b();
        if ((zM644b || zM644b2) && !(zM644b && zM644b2 && this.f864a.m569a(jkVar.f864a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jkVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f867b.equals(jkVar.f867b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jkVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f868c.equals(jkVar.f868c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jkVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f863a == jkVar.f863a)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jkVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f869d.equals(jkVar.f869d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jkVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f870e.equals(jkVar.f870e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jkVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f871f.equals(jkVar.f871f))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jkVar.i();
        if (zI || zI2) {
            return zI && zI2 && this.f872g.equals(jkVar.f872g);
        }
        return true;
    }

    public String b() {
        return this.f872g;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m641a();
        kbVar.a(f862a);
        if (this.f865a != null && m642a()) {
            kbVar.a(f25442a);
            kbVar.a(this.f865a);
            kbVar.b();
        }
        if (this.f864a != null && m644b()) {
            kbVar.a(f25443b);
            this.f864a.b(kbVar);
            kbVar.b();
        }
        if (this.f867b != null) {
            kbVar.a(f25444c);
            kbVar.a(this.f867b);
            kbVar.b();
        }
        if (this.f868c != null && d()) {
            kbVar.a(f25445d);
            kbVar.a(this.f868c);
            kbVar.b();
        }
        if (e()) {
            kbVar.a(f25446e);
            kbVar.a(this.f863a);
            kbVar.b();
        }
        if (this.f869d != null && f()) {
            kbVar.a(f25447f);
            kbVar.a(this.f869d);
            kbVar.b();
        }
        if (this.f870e != null && g()) {
            kbVar.a(f25448g);
            kbVar.a(this.f870e);
            kbVar.b();
        }
        if (this.f871f != null && h()) {
            kbVar.a(f25449h);
            kbVar.a(this.f871f);
            kbVar.b();
        }
        if (this.f872g != null && i()) {
            kbVar.a(f25450i);
            kbVar.a(this.f872g);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m644b() {
        return this.f864a != null;
    }

    public boolean c() {
        return this.f867b != null;
    }

    public boolean d() {
        return this.f868c != null;
    }

    public boolean e() {
        return this.f866a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jk)) {
            return m643a((jk) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f869d != null;
    }

    public boolean g() {
        return this.f870e != null;
    }

    public boolean h() {
        return this.f871f != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f872g != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionSubscriptionResult(");
        boolean z3 = false;
        if (m642a()) {
            sb.append("debug:");
            String str = this.f865a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m644b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            it itVar = this.f864a;
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
        String str2 = this.f867b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            String str3 = this.f868c;
            if (str3 == null) {
                sb.append("null");
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("errorCode:");
            sb.append(this.f863a);
        }
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f869d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("topic:");
            String str5 = this.f870e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f871f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f872g;
            if (str7 == null) {
                sb.append("null");
            } else {
                sb.append(str7);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
