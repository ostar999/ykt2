package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class io implements jq<io, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f624a;

    /* renamed from: a, reason: collision with other field name */
    public long f625a;

    /* renamed from: a, reason: collision with other field name */
    public String f626a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f627a = new BitSet(6);

    /* renamed from: a, reason: collision with other field name */
    public boolean f628a;

    /* renamed from: b, reason: collision with other field name */
    public int f629b;

    /* renamed from: b, reason: collision with other field name */
    public boolean f630b;

    /* renamed from: c, reason: collision with other field name */
    public int f631c;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f623a = new kg("OnlineConfigItem");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25237a = new jy("", (byte) 8, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25238b = new jy("", (byte) 8, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25239c = new jy("", (byte) 2, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25240d = new jy("", (byte) 8, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25241e = new jy("", (byte) 10, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25242f = new jy("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25243g = new jy("", (byte) 2, 7);

    public int a() {
        return this.f624a;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(io ioVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        if (!getClass().equals(ioVar.getClass())) {
            return getClass().getName().compareTo(ioVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m542a()).compareTo(Boolean.valueOf(ioVar.m542a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m542a() && (iA7 = jr.a(this.f624a, ioVar.f624a)) != 0) {
            return iA7;
        }
        int iCompareTo2 = Boolean.valueOf(m544b()).compareTo(Boolean.valueOf(ioVar.m544b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m544b() && (iA6 = jr.a(this.f629b, ioVar.f629b)) != 0) {
            return iA6;
        }
        int iCompareTo3 = Boolean.valueOf(m545c()).compareTo(Boolean.valueOf(ioVar.m545c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m545c() && (iA5 = jr.a(this.f628a, ioVar.f628a)) != 0) {
            return iA5;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ioVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA4 = jr.a(this.f631c, ioVar.f631c)) != 0) {
            return iA4;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(ioVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA3 = jr.a(this.f625a, ioVar.f625a)) != 0) {
            return iA3;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ioVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA2 = jr.a(this.f626a, ioVar.f626a)) != 0) {
            return iA2;
        }
        int iCompareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(ioVar.h()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (!h() || (iA = jr.a(this.f630b, ioVar.f630b)) == 0) {
            return 0;
        }
        return iA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m539a() {
        return this.f625a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m540a() {
        return this.f626a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m541a() {
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                m541a();
                return;
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 8) {
                        this.f624a = kbVar.mo660a();
                        a(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 8) {
                        this.f629b = kbVar.mo660a();
                        b(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 2) {
                        this.f628a = kbVar.mo672a();
                        c(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 8) {
                        this.f631c = kbVar.mo660a();
                        d(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 10) {
                        this.f625a = kbVar.mo661a();
                        e(true);
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 11) {
                        this.f626a = kbVar.mo667a();
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 2) {
                        this.f630b = kbVar.mo672a();
                        f(true);
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
        this.f627a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m542a() {
        return this.f627a.get(0);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m543a(io ioVar) {
        if (ioVar == null) {
            return false;
        }
        boolean zM542a = m542a();
        boolean zM542a2 = ioVar.m542a();
        if ((zM542a || zM542a2) && !(zM542a && zM542a2 && this.f624a == ioVar.f624a)) {
            return false;
        }
        boolean zM544b = m544b();
        boolean zM544b2 = ioVar.m544b();
        if ((zM544b || zM544b2) && !(zM544b && zM544b2 && this.f629b == ioVar.f629b)) {
            return false;
        }
        boolean zM545c = m545c();
        boolean zM545c2 = ioVar.m545c();
        if ((zM545c || zM545c2) && !(zM545c && zM545c2 && this.f628a == ioVar.f628a)) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = ioVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f631c == ioVar.f631c)) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = ioVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f625a == ioVar.f625a)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = ioVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f626a.equals(ioVar.f626a))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = ioVar.h();
        if (zH || zH2) {
            return zH && zH2 && this.f630b == ioVar.f630b;
        }
        return true;
    }

    public int b() {
        return this.f629b;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) {
        m541a();
        kbVar.a(f623a);
        if (m542a()) {
            kbVar.a(f25237a);
            kbVar.mo671a(this.f624a);
            kbVar.b();
        }
        if (m544b()) {
            kbVar.a(f25238b);
            kbVar.mo671a(this.f629b);
            kbVar.b();
        }
        if (m545c()) {
            kbVar.a(f25239c);
            kbVar.a(this.f628a);
            kbVar.b();
        }
        if (d()) {
            kbVar.a(f25240d);
            kbVar.mo671a(this.f631c);
            kbVar.b();
        }
        if (e()) {
            kbVar.a(f25241e);
            kbVar.a(this.f625a);
            kbVar.b();
        }
        if (this.f626a != null && f()) {
            kbVar.a(f25242f);
            kbVar.a(this.f626a);
            kbVar.b();
        }
        if (h()) {
            kbVar.a(f25243g);
            kbVar.a(this.f630b);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f627a.set(1, z2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m544b() {
        return this.f627a.get(1);
    }

    public int c() {
        return this.f631c;
    }

    public void c(boolean z2) {
        this.f627a.set(2, z2);
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m545c() {
        return this.f627a.get(2);
    }

    public void d(boolean z2) {
        this.f627a.set(3, z2);
    }

    public boolean d() {
        return this.f627a.get(3);
    }

    public void e(boolean z2) {
        this.f627a.set(4, z2);
    }

    public boolean e() {
        return this.f627a.get(4);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof io)) {
            return m543a((io) obj);
        }
        return false;
    }

    public void f(boolean z2) {
        this.f627a.set(5, z2);
    }

    public boolean f() {
        return this.f626a != null;
    }

    public boolean g() {
        return this.f630b;
    }

    public boolean h() {
        return this.f627a.get(5);
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("OnlineConfigItem(");
        boolean z3 = false;
        if (m542a()) {
            sb.append("key:");
            sb.append(this.f624a);
            z2 = false;
        } else {
            z2 = true;
        }
        if (m544b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("type:");
            sb.append(this.f629b);
            z2 = false;
        }
        if (m545c()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("clear:");
            sb.append(this.f628a);
            z2 = false;
        }
        if (d()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("intValue:");
            sb.append(this.f631c);
            z2 = false;
        }
        if (e()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("longValue:");
            sb.append(this.f625a);
            z2 = false;
        }
        if (f()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("stringValue:");
            String str = this.f626a;
            if (str == null) {
                str = "null";
            }
            sb.append(str);
        } else {
            z3 = z2;
        }
        if (h()) {
            if (!z3) {
                sb.append(", ");
            }
            sb.append("boolValue:");
            sb.append(this.f630b);
        }
        sb.append(")");
        return sb.toString();
    }
}
