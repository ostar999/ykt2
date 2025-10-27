package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class fs implements jq<fs, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public byte f433a;

    /* renamed from: a, reason: collision with other field name */
    public int f434a;

    /* renamed from: a, reason: collision with other field name */
    public String f435a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f436a = new BitSet(6);

    /* renamed from: b, reason: collision with other field name */
    public int f437b;

    /* renamed from: b, reason: collision with other field name */
    public String f438b;

    /* renamed from: c, reason: collision with other field name */
    public int f439c;

    /* renamed from: c, reason: collision with other field name */
    public String f440c;

    /* renamed from: d, reason: collision with other field name */
    public int f441d;

    /* renamed from: d, reason: collision with other field name */
    public String f442d;

    /* renamed from: e, reason: collision with other field name */
    public int f443e;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f432a = new kg("StatsEvent");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f24893a = new jy("", (byte) 3, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f24894b = new jy("", (byte) 8, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f24895c = new jy("", (byte) 8, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f24896d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f24897e = new jy("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f24898f = new jy("", (byte) 8, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f24899g = new jy("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f24900h = new jy("", (byte) 11, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f24901i = new jy("", (byte) 8, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f24902j = new jy("", (byte) 8, 10);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(fs fsVar) {
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
        if (!getClass().equals(fsVar.getClass())) {
            return getClass().getName().compareTo(fsVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m424a()).compareTo(Boolean.valueOf(fsVar.m424a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m424a() && (iA10 = jr.a(this.f433a, fsVar.f433a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(fsVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA9 = jr.a(this.f434a, fsVar.f434a)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(fsVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA8 = jr.a(this.f437b, fsVar.f437b)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(fsVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = jr.a(this.f435a, fsVar.f435a)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(fsVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = jr.a(this.f438b, fsVar.f438b)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(fsVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = jr.a(this.f439c, fsVar.f439c)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(fsVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = jr.a(this.f440c, fsVar.f440c)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(fsVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA3 = jr.a(this.f442d, fsVar.f442d)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(fsVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA2 = jr.a(this.f441d, fsVar.f441d)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(fsVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!j() || (iA = jr.a(this.f443e, fsVar.f443e)) == 0) {
            return 0;
        }
        return iA;
    }

    public fs a(byte b3) {
        this.f433a = b3;
        a(true);
        return this;
    }

    public fs a(int i2) {
        this.f434a = i2;
        b(true);
        return this;
    }

    public fs a(String str) {
        this.f435a = str;
        return this;
    }

    public void a() throws kc {
        if (this.f435a != null) {
            return;
        }
        throw new kc("Required field 'connpt' was not present! Struct: " + toString());
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) throws kc {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                if (!m424a()) {
                    throw new kc("Required field 'chid' was not found in serialized data! Struct: " + toString());
                }
                if (!b()) {
                    throw new kc("Required field 'type' was not found in serialized data! Struct: " + toString());
                }
                if (c()) {
                    a();
                    return;
                }
                throw new kc("Required field 'value' was not found in serialized data! Struct: " + toString());
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 3) {
                        this.f433a = kbVar.a();
                        a(true);
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 8) {
                        this.f434a = kbVar.mo660a();
                        b(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 8) {
                        this.f437b = kbVar.mo660a();
                        c(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f435a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 11) {
                        this.f438b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 8) {
                        this.f439c = kbVar.mo660a();
                        d(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f440c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 11) {
                        this.f442d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 8) {
                        this.f441d = kbVar.mo660a();
                        e(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 8) {
                        this.f443e = kbVar.mo660a();
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
        this.f436a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m424a() {
        return this.f436a.get(0);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m425a(fs fsVar) {
        if (fsVar == null || this.f433a != fsVar.f433a || this.f434a != fsVar.f434a || this.f437b != fsVar.f437b) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = fsVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f435a.equals(fsVar.f435a))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = fsVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f438b.equals(fsVar.f438b))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = fsVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f439c == fsVar.f439c)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = fsVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f440c.equals(fsVar.f440c))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = fsVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f442d.equals(fsVar.f442d))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = fsVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f441d == fsVar.f441d)) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = fsVar.j();
        if (zJ || zJ2) {
            return zJ && zJ2 && this.f443e == fsVar.f443e;
        }
        return true;
    }

    public fs b(int i2) {
        this.f437b = i2;
        c(true);
        return this;
    }

    public fs b(String str) {
        this.f438b = str;
        return this;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) {
        a();
        kbVar.a(f432a);
        kbVar.a(f24893a);
        kbVar.a(this.f433a);
        kbVar.b();
        kbVar.a(f24894b);
        kbVar.mo671a(this.f434a);
        kbVar.b();
        kbVar.a(f24895c);
        kbVar.mo671a(this.f437b);
        kbVar.b();
        if (this.f435a != null) {
            kbVar.a(f24896d);
            kbVar.a(this.f435a);
            kbVar.b();
        }
        if (this.f438b != null && e()) {
            kbVar.a(f24897e);
            kbVar.a(this.f438b);
            kbVar.b();
        }
        if (f()) {
            kbVar.a(f24898f);
            kbVar.mo671a(this.f439c);
            kbVar.b();
        }
        if (this.f440c != null && g()) {
            kbVar.a(f24899g);
            kbVar.a(this.f440c);
            kbVar.b();
        }
        if (this.f442d != null && h()) {
            kbVar.a(f24900h);
            kbVar.a(this.f442d);
            kbVar.b();
        }
        if (i()) {
            kbVar.a(f24901i);
            kbVar.mo671a(this.f441d);
            kbVar.b();
        }
        if (j()) {
            kbVar.a(f24902j);
            kbVar.mo671a(this.f443e);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f436a.set(1, z2);
    }

    public boolean b() {
        return this.f436a.get(1);
    }

    public fs c(int i2) {
        this.f439c = i2;
        d(true);
        return this;
    }

    public fs c(String str) {
        this.f440c = str;
        return this;
    }

    public void c(boolean z2) {
        this.f436a.set(2, z2);
    }

    public boolean c() {
        return this.f436a.get(2);
    }

    public fs d(int i2) {
        this.f441d = i2;
        e(true);
        return this;
    }

    public fs d(String str) {
        this.f442d = str;
        return this;
    }

    public void d(boolean z2) {
        this.f436a.set(3, z2);
    }

    public boolean d() {
        return this.f435a != null;
    }

    public void e(boolean z2) {
        this.f436a.set(4, z2);
    }

    public boolean e() {
        return this.f438b != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof fs)) {
            return m425a((fs) obj);
        }
        return false;
    }

    public void f(boolean z2) {
        this.f436a.set(5, z2);
    }

    public boolean f() {
        return this.f436a.get(3);
    }

    public boolean g() {
        return this.f440c != null;
    }

    public boolean h() {
        return this.f442d != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f436a.get(4);
    }

    public boolean j() {
        return this.f436a.get(5);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StatsEvent(");
        sb.append("chid:");
        sb.append((int) this.f433a);
        sb.append(", ");
        sb.append("type:");
        sb.append(this.f434a);
        sb.append(", ");
        sb.append("value:");
        sb.append(this.f437b);
        sb.append(", ");
        sb.append("connpt:");
        String str = this.f435a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        if (e()) {
            sb.append(", ");
            sb.append("host:");
            String str2 = this.f438b;
            if (str2 == null) {
                sb.append("null");
            } else {
                sb.append(str2);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("subvalue:");
            sb.append(this.f439c);
        }
        if (g()) {
            sb.append(", ");
            sb.append("annotation:");
            String str3 = this.f440c;
            if (str3 == null) {
                sb.append("null");
            } else {
                sb.append(str3);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("user:");
            String str4 = this.f442d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("time:");
            sb.append(this.f441d);
        }
        if (j()) {
            sb.append(", ");
            sb.append("clientIp:");
            sb.append(this.f443e);
        }
        sb.append(")");
        return sb.toString();
    }
}
