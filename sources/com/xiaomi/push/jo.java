package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class jo implements jq<jo, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f908a;

    /* renamed from: a, reason: collision with other field name */
    public it f909a;

    /* renamed from: a, reason: collision with other field name */
    public String f910a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f911a = new BitSet(1);

    /* renamed from: b, reason: collision with other field name */
    public String f912b;

    /* renamed from: c, reason: collision with other field name */
    public String f913c;

    /* renamed from: d, reason: collision with other field name */
    public String f914d;

    /* renamed from: e, reason: collision with other field name */
    public String f915e;

    /* renamed from: f, reason: collision with other field name */
    public String f916f;

    /* renamed from: g, reason: collision with other field name */
    public String f917g;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f907a = new kg("XmPushActionUnSubscriptionResult");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25480a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25481b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25482c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25483d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25484e = new jy("", (byte) 10, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25485f = new jy("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25486g = new jy("", (byte) 11, 8);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25487h = new jy("", (byte) 11, 9);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25488i = new jy("", (byte) 11, 10);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jo joVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        if (!getClass().equals(joVar.getClass())) {
            return getClass().getName().compareTo(joVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m653a()).compareTo(Boolean.valueOf(joVar.m653a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m653a() && (iA9 = jr.a(this.f910a, joVar.f910a)) != 0) {
            return iA9;
        }
        int iCompareTo2 = Boolean.valueOf(m655b()).compareTo(Boolean.valueOf(joVar.m655b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m655b() && (iA8 = jr.a(this.f909a, joVar.f909a)) != 0) {
            return iA8;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(joVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA7 = jr.a(this.f912b, joVar.f912b)) != 0) {
            return iA7;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(joVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA6 = jr.a(this.f913c, joVar.f913c)) != 0) {
            return iA6;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(joVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA5 = jr.a(this.f908a, joVar.f908a)) != 0) {
            return iA5;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(joVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA4 = jr.a(this.f914d, joVar.f914d)) != 0) {
            return iA4;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(joVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA3 = jr.a(this.f915e, joVar.f915e)) != 0) {
            return iA3;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(joVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA2 = jr.a(this.f916f, joVar.f916f)) != 0) {
            return iA2;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(joVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (!i() || (iA = jr.a(this.f917g, joVar.f917g)) == 0) {
            return 0;
        }
        return iA;
    }

    public String a() {
        return this.f915e;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m652a() throws kc {
        if (this.f912b != null) {
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
                m652a();
                return;
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f910a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f909a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f912b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f913c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 10) {
                        this.f908a = kbVar.mo661a();
                        a(true);
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f914d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 11) {
                        this.f915e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 11) {
                        this.f916f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 11) {
                        this.f917g = kbVar.mo667a();
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
        this.f911a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m653a() {
        return this.f910a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m654a(jo joVar) {
        if (joVar == null) {
            return false;
        }
        boolean zM653a = m653a();
        boolean zM653a2 = joVar.m653a();
        if ((zM653a || zM653a2) && !(zM653a && zM653a2 && this.f910a.equals(joVar.f910a))) {
            return false;
        }
        boolean zM655b = m655b();
        boolean zM655b2 = joVar.m655b();
        if ((zM655b || zM655b2) && !(zM655b && zM655b2 && this.f909a.m569a(joVar.f909a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = joVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f912b.equals(joVar.f912b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = joVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f913c.equals(joVar.f913c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = joVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f908a == joVar.f908a)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = joVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f914d.equals(joVar.f914d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = joVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f915e.equals(joVar.f915e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = joVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f916f.equals(joVar.f916f))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = joVar.i();
        if (zI || zI2) {
            return zI && zI2 && this.f917g.equals(joVar.f917g);
        }
        return true;
    }

    public String b() {
        return this.f917g;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m652a();
        kbVar.a(f907a);
        if (this.f910a != null && m653a()) {
            kbVar.a(f25480a);
            kbVar.a(this.f910a);
            kbVar.b();
        }
        if (this.f909a != null && m655b()) {
            kbVar.a(f25481b);
            this.f909a.b(kbVar);
            kbVar.b();
        }
        if (this.f912b != null) {
            kbVar.a(f25482c);
            kbVar.a(this.f912b);
            kbVar.b();
        }
        if (this.f913c != null && d()) {
            kbVar.a(f25483d);
            kbVar.a(this.f913c);
            kbVar.b();
        }
        if (e()) {
            kbVar.a(f25484e);
            kbVar.a(this.f908a);
            kbVar.b();
        }
        if (this.f914d != null && f()) {
            kbVar.a(f25485f);
            kbVar.a(this.f914d);
            kbVar.b();
        }
        if (this.f915e != null && g()) {
            kbVar.a(f25486g);
            kbVar.a(this.f915e);
            kbVar.b();
        }
        if (this.f916f != null && h()) {
            kbVar.a(f25487h);
            kbVar.a(this.f916f);
            kbVar.b();
        }
        if (this.f917g != null && i()) {
            kbVar.a(f25488i);
            kbVar.a(this.f917g);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m655b() {
        return this.f909a != null;
    }

    public boolean c() {
        return this.f912b != null;
    }

    public boolean d() {
        return this.f913c != null;
    }

    public boolean e() {
        return this.f911a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jo)) {
            return m654a((jo) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f914d != null;
    }

    public boolean g() {
        return this.f915e != null;
    }

    public boolean h() {
        return this.f916f != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f917g != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionUnSubscriptionResult(");
        boolean z3 = false;
        if (m653a()) {
            sb.append("debug:");
            String str = this.f910a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m655b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            it itVar = this.f909a;
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
        String str2 = this.f912b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            String str3 = this.f913c;
            if (str3 == null) {
                sb.append("null");
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("errorCode:");
            sb.append(this.f908a);
        }
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f914d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("topic:");
            String str5 = this.f915e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f916f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f917g;
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
