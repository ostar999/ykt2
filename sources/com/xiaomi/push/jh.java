package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class jh implements jq<jh, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f831a;

    /* renamed from: a, reason: collision with other field name */
    public it f832a;

    /* renamed from: a, reason: collision with other field name */
    public String f833a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f834a = new BitSet(1);

    /* renamed from: b, reason: collision with other field name */
    public String f835b;

    /* renamed from: c, reason: collision with other field name */
    public String f836c;

    /* renamed from: d, reason: collision with other field name */
    public String f837d;

    /* renamed from: e, reason: collision with other field name */
    public String f838e;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f830a = new kg("XmPushActionSendFeedbackResult");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25415a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25416b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25417c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25418d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25419e = new jy("", (byte) 10, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25420f = new jy("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25421g = new jy("", (byte) 11, 8);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jh jhVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        if (!getClass().equals(jhVar.getClass())) {
            return getClass().getName().compareTo(jhVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m628a()).compareTo(Boolean.valueOf(jhVar.m628a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m628a() && (iA7 = jr.a(this.f833a, jhVar.f833a)) != 0) {
            return iA7;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jhVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA6 = jr.a(this.f832a, jhVar.f832a)) != 0) {
            return iA6;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jhVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA5 = jr.a(this.f835b, jhVar.f835b)) != 0) {
            return iA5;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jhVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA4 = jr.a(this.f836c, jhVar.f836c)) != 0) {
            return iA4;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jhVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA3 = jr.a(this.f831a, jhVar.f831a)) != 0) {
            return iA3;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jhVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA2 = jr.a(this.f837d, jhVar.f837d)) != 0) {
            return iA2;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jhVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (!g() || (iA = jr.a(this.f838e, jhVar.f838e)) == 0) {
            return 0;
        }
        return iA;
    }

    public void a() throws kc {
        if (this.f835b == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f836c != null) {
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
                throw new kc("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f833a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f832a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f835b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f836c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 10) {
                        this.f831a = kbVar.mo661a();
                        a(true);
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f837d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 11) {
                        this.f838e = kbVar.mo667a();
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
        this.f834a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m628a() {
        return this.f833a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m629a(jh jhVar) {
        if (jhVar == null) {
            return false;
        }
        boolean zM628a = m628a();
        boolean zM628a2 = jhVar.m628a();
        if ((zM628a || zM628a2) && !(zM628a && zM628a2 && this.f833a.equals(jhVar.f833a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jhVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f832a.m569a(jhVar.f832a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jhVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f835b.equals(jhVar.f835b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jhVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.f836c.equals(jhVar.f836c))) || this.f831a != jhVar.f831a) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jhVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f837d.equals(jhVar.f837d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jhVar.g();
        if (zG || zG2) {
            return zG && zG2 && this.f838e.equals(jhVar.f838e);
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        a();
        kbVar.a(f830a);
        if (this.f833a != null && m628a()) {
            kbVar.a(f25415a);
            kbVar.a(this.f833a);
            kbVar.b();
        }
        if (this.f832a != null && b()) {
            kbVar.a(f25416b);
            this.f832a.b(kbVar);
            kbVar.b();
        }
        if (this.f835b != null) {
            kbVar.a(f25417c);
            kbVar.a(this.f835b);
            kbVar.b();
        }
        if (this.f836c != null) {
            kbVar.a(f25418d);
            kbVar.a(this.f836c);
            kbVar.b();
        }
        kbVar.a(f25419e);
        kbVar.a(this.f831a);
        kbVar.b();
        if (this.f837d != null && f()) {
            kbVar.a(f25420f);
            kbVar.a(this.f837d);
            kbVar.b();
        }
        if (this.f838e != null && g()) {
            kbVar.a(f25421g);
            kbVar.a(this.f838e);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public boolean b() {
        return this.f832a != null;
    }

    public boolean c() {
        return this.f835b != null;
    }

    public boolean d() {
        return this.f836c != null;
    }

    public boolean e() {
        return this.f834a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jh)) {
            return m629a((jh) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f837d != null;
    }

    public boolean g() {
        return this.f838e != null;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionSendFeedbackResult(");
        boolean z3 = false;
        if (m628a()) {
            sb.append("debug:");
            String str = this.f833a;
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
            it itVar = this.f832a;
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
        String str2 = this.f835b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f836c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f831a);
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f837d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("category:");
            String str5 = this.f838e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
