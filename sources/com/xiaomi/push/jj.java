package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class jj implements jq<jj, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public it f854a;

    /* renamed from: a, reason: collision with other field name */
    public String f855a;

    /* renamed from: a, reason: collision with other field name */
    public List<String> f856a;

    /* renamed from: b, reason: collision with other field name */
    public String f857b;

    /* renamed from: c, reason: collision with other field name */
    public String f858c;

    /* renamed from: d, reason: collision with other field name */
    public String f859d;

    /* renamed from: e, reason: collision with other field name */
    public String f860e;

    /* renamed from: f, reason: collision with other field name */
    public String f861f;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f853a = new kg("XmPushActionSubscription");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25434a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25435b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25436c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25437d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25438e = new jy("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25439f = new jy("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25440g = new jy("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25441h = new jy("", (byte) 15, 8);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jj jjVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        if (!getClass().equals(jjVar.getClass())) {
            return getClass().getName().compareTo(jjVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m639a()).compareTo(Boolean.valueOf(jjVar.m639a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m639a() && (iA8 = jr.a(this.f855a, jjVar.f855a)) != 0) {
            return iA8;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jjVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA7 = jr.a(this.f854a, jjVar.f854a)) != 0) {
            return iA7;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jjVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA6 = jr.a(this.f857b, jjVar.f857b)) != 0) {
            return iA6;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jjVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA5 = jr.a(this.f858c, jjVar.f858c)) != 0) {
            return iA5;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jjVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA4 = jr.a(this.f859d, jjVar.f859d)) != 0) {
            return iA4;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jjVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA3 = jr.a(this.f860e, jjVar.f860e)) != 0) {
            return iA3;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jjVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA2 = jr.a(this.f861f, jjVar.f861f)) != 0) {
            return iA2;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jjVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (!h() || (iA = jr.a(this.f856a, jjVar.f856a)) == 0) {
            return 0;
        }
        return iA;
    }

    public jj a(String str) {
        this.f857b = str;
        return this;
    }

    public void a() throws kc {
        if (this.f857b == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f858c == null) {
            throw new kc("Required field 'appId' was not present! Struct: " + toString());
        }
        if (this.f859d != null) {
            return;
        }
        throw new kc("Required field 'topic' was not present! Struct: " + toString());
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
                        this.f855a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f854a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f857b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f858c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 11) {
                        this.f859d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 11) {
                        this.f860e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f861f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 15) {
                        jz jzVarMo663a = kbVar.mo663a();
                        this.f856a = new ArrayList(jzVarMo663a.f929a);
                        for (int i2 = 0; i2 < jzVarMo663a.f929a; i2++) {
                            this.f856a.add(kbVar.mo667a());
                        }
                        kbVar.j();
                    }
                    kbVar.h();
                    break;
            }
            ke.a(kbVar, b3);
            kbVar.h();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m639a() {
        return this.f855a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m640a(jj jjVar) {
        if (jjVar == null) {
            return false;
        }
        boolean zM639a = m639a();
        boolean zM639a2 = jjVar.m639a();
        if ((zM639a || zM639a2) && !(zM639a && zM639a2 && this.f855a.equals(jjVar.f855a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jjVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f854a.m569a(jjVar.f854a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jjVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f857b.equals(jjVar.f857b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jjVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f858c.equals(jjVar.f858c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jjVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f859d.equals(jjVar.f859d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jjVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f860e.equals(jjVar.f860e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jjVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f861f.equals(jjVar.f861f))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jjVar.h();
        if (zH || zH2) {
            return zH && zH2 && this.f856a.equals(jjVar.f856a);
        }
        return true;
    }

    public jj b(String str) {
        this.f858c = str;
        return this;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        a();
        kbVar.a(f853a);
        if (this.f855a != null && m639a()) {
            kbVar.a(f25434a);
            kbVar.a(this.f855a);
            kbVar.b();
        }
        if (this.f854a != null && b()) {
            kbVar.a(f25435b);
            this.f854a.b(kbVar);
            kbVar.b();
        }
        if (this.f857b != null) {
            kbVar.a(f25436c);
            kbVar.a(this.f857b);
            kbVar.b();
        }
        if (this.f858c != null) {
            kbVar.a(f25437d);
            kbVar.a(this.f858c);
            kbVar.b();
        }
        if (this.f859d != null) {
            kbVar.a(f25438e);
            kbVar.a(this.f859d);
            kbVar.b();
        }
        if (this.f860e != null && f()) {
            kbVar.a(f25439f);
            kbVar.a(this.f860e);
            kbVar.b();
        }
        if (this.f861f != null && g()) {
            kbVar.a(f25440g);
            kbVar.a(this.f861f);
            kbVar.b();
        }
        if (this.f856a != null && h()) {
            kbVar.a(f25441h);
            kbVar.a(new jz((byte) 11, this.f856a.size()));
            Iterator<String> it = this.f856a.iterator();
            while (it.hasNext()) {
                kbVar.a(it.next());
            }
            kbVar.e();
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public boolean b() {
        return this.f854a != null;
    }

    public jj c(String str) {
        this.f859d = str;
        return this;
    }

    public boolean c() {
        return this.f857b != null;
    }

    public jj d(String str) {
        this.f860e = str;
        return this;
    }

    public boolean d() {
        return this.f858c != null;
    }

    public jj e(String str) {
        this.f861f = str;
        return this;
    }

    public boolean e() {
        return this.f859d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jj)) {
            return m640a((jj) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f860e != null;
    }

    public boolean g() {
        return this.f861f != null;
    }

    public boolean h() {
        return this.f856a != null;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionSubscription(");
        boolean z3 = false;
        if (m639a()) {
            sb.append("debug:");
            String str = this.f855a;
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
            it itVar = this.f854a;
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
        String str2 = this.f857b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f858c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("topic:");
        String str4 = this.f859d;
        if (str4 == null) {
            sb.append("null");
        } else {
            sb.append(str4);
        }
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f860e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("category:");
            String str6 = this.f861f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("aliases:");
            List<String> list = this.f856a;
            if (list == null) {
                sb.append("null");
            } else {
                sb.append(list);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
