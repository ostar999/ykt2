package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class jm implements jq<jm, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f888a;

    /* renamed from: a, reason: collision with other field name */
    public it f889a;

    /* renamed from: a, reason: collision with other field name */
    public String f890a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f891a = new BitSet(3);

    /* renamed from: b, reason: collision with other field name */
    public long f892b;

    /* renamed from: b, reason: collision with other field name */
    public String f893b;

    /* renamed from: c, reason: collision with other field name */
    public long f894c;

    /* renamed from: c, reason: collision with other field name */
    public String f895c;

    /* renamed from: d, reason: collision with other field name */
    public String f896d;

    /* renamed from: e, reason: collision with other field name */
    public String f897e;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f887a = new kg("XmPushActionUnRegistrationResult");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25463a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25464b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25465c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25466d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25467e = new jy("", (byte) 10, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25468f = new jy("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25469g = new jy("", (byte) 11, 8);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25470h = new jy("", (byte) 10, 9);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25471i = new jy("", (byte) 10, 10);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jm jmVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        if (!getClass().equals(jmVar.getClass())) {
            return getClass().getName().compareTo(jmVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m648a()).compareTo(Boolean.valueOf(jmVar.m648a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m648a() && (iA9 = jr.a(this.f890a, jmVar.f890a)) != 0) {
            return iA9;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jmVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA8 = jr.a(this.f889a, jmVar.f889a)) != 0) {
            return iA8;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jmVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA7 = jr.a(this.f893b, jmVar.f893b)) != 0) {
            return iA7;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jmVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA6 = jr.a(this.f895c, jmVar.f895c)) != 0) {
            return iA6;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jmVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA5 = jr.a(this.f888a, jmVar.f888a)) != 0) {
            return iA5;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jmVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA4 = jr.a(this.f896d, jmVar.f896d)) != 0) {
            return iA4;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jmVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA3 = jr.a(this.f897e, jmVar.f897e)) != 0) {
            return iA3;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jmVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA2 = jr.a(this.f892b, jmVar.f892b)) != 0) {
            return iA2;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jmVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (!i() || (iA = jr.a(this.f894c, jmVar.f894c)) == 0) {
            return 0;
        }
        return iA;
    }

    public String a() {
        return this.f897e;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m647a() throws kc {
        if (this.f893b == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f895c != null) {
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
                    m647a();
                    return;
                }
                throw new kc("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f890a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f889a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f893b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f895c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 10) {
                        this.f888a = kbVar.mo661a();
                        a(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f896d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 11) {
                        this.f897e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 10) {
                        this.f892b = kbVar.mo661a();
                        b(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 10) {
                        this.f894c = kbVar.mo661a();
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

    public void a(boolean z2) {
        this.f891a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m648a() {
        return this.f890a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m649a(jm jmVar) {
        if (jmVar == null) {
            return false;
        }
        boolean zM648a = m648a();
        boolean zM648a2 = jmVar.m648a();
        if ((zM648a || zM648a2) && !(zM648a && zM648a2 && this.f890a.equals(jmVar.f890a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jmVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f889a.m569a(jmVar.f889a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jmVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f893b.equals(jmVar.f893b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jmVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.f895c.equals(jmVar.f895c))) || this.f888a != jmVar.f888a) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jmVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f896d.equals(jmVar.f896d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jmVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f897e.equals(jmVar.f897e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jmVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f892b == jmVar.f892b)) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jmVar.i();
        if (zI || zI2) {
            return zI && zI2 && this.f894c == jmVar.f894c;
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m647a();
        kbVar.a(f887a);
        if (this.f890a != null && m648a()) {
            kbVar.a(f25463a);
            kbVar.a(this.f890a);
            kbVar.b();
        }
        if (this.f889a != null && b()) {
            kbVar.a(f25464b);
            this.f889a.b(kbVar);
            kbVar.b();
        }
        if (this.f893b != null) {
            kbVar.a(f25465c);
            kbVar.a(this.f893b);
            kbVar.b();
        }
        if (this.f895c != null) {
            kbVar.a(f25466d);
            kbVar.a(this.f895c);
            kbVar.b();
        }
        kbVar.a(f25467e);
        kbVar.a(this.f888a);
        kbVar.b();
        if (this.f896d != null && f()) {
            kbVar.a(f25468f);
            kbVar.a(this.f896d);
            kbVar.b();
        }
        if (this.f897e != null && g()) {
            kbVar.a(f25469g);
            kbVar.a(this.f897e);
            kbVar.b();
        }
        if (h()) {
            kbVar.a(f25470h);
            kbVar.a(this.f892b);
            kbVar.b();
        }
        if (i()) {
            kbVar.a(f25471i);
            kbVar.a(this.f894c);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f891a.set(1, z2);
    }

    public boolean b() {
        return this.f889a != null;
    }

    public void c(boolean z2) {
        this.f891a.set(2, z2);
    }

    public boolean c() {
        return this.f893b != null;
    }

    public boolean d() {
        return this.f895c != null;
    }

    public boolean e() {
        return this.f891a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jm)) {
            return m649a((jm) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f896d != null;
    }

    public boolean g() {
        return this.f897e != null;
    }

    public boolean h() {
        return this.f891a.get(1);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f891a.get(2);
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionUnRegistrationResult(");
        boolean z3 = false;
        if (m648a()) {
            sb.append("debug:");
            String str = this.f890a;
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
            it itVar = this.f889a;
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
        String str2 = this.f893b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f895c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f888a);
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f896d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f897e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("unRegisteredAt:");
            sb.append(this.f892b);
        }
        if (i()) {
            sb.append(", ");
            sb.append("costTime:");
            sb.append(this.f894c);
        }
        sb.append(")");
        return sb.toString();
    }
}
