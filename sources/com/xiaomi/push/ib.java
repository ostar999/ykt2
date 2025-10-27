package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class ib implements jq<ib, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f563a;

    /* renamed from: a, reason: collision with other field name */
    public String f564a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f565a = new BitSet(3);

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f566a;

    /* renamed from: a, reason: collision with other field name */
    public boolean f567a;

    /* renamed from: b, reason: collision with other field name */
    public long f568b;

    /* renamed from: b, reason: collision with other field name */
    public String f569b;

    /* renamed from: c, reason: collision with other field name */
    public String f570c;

    /* renamed from: d, reason: collision with other field name */
    public String f571d;

    /* renamed from: e, reason: collision with other field name */
    public String f572e;

    /* renamed from: f, reason: collision with other field name */
    public String f573f;

    /* renamed from: g, reason: collision with other field name */
    public String f574g;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f562a = new kg("ClientUploadDataItem");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25136a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25137b = new jy("", (byte) 11, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25138c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25139d = new jy("", (byte) 10, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25140e = new jy("", (byte) 10, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25141f = new jy("", (byte) 2, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25142g = new jy("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25143h = new jy("", (byte) 11, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25144i = new jy("", (byte) 11, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25145j = new jy("", (byte) 13, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final jy f25146k = new jy("", (byte) 11, 11);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ib ibVar) {
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
        if (!getClass().equals(ibVar.getClass())) {
            return getClass().getName().compareTo(ibVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m505a()).compareTo(Boolean.valueOf(ibVar.m505a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m505a() && (iA11 = jr.a(this.f564a, ibVar.f564a)) != 0) {
            return iA11;
        }
        int iCompareTo2 = Boolean.valueOf(m507b()).compareTo(Boolean.valueOf(ibVar.m507b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m507b() && (iA10 = jr.a(this.f569b, ibVar.f569b)) != 0) {
            return iA10;
        }
        int iCompareTo3 = Boolean.valueOf(m508c()).compareTo(Boolean.valueOf(ibVar.m508c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m508c() && (iA9 = jr.a(this.f570c, ibVar.f570c)) != 0) {
            return iA9;
        }
        int iCompareTo4 = Boolean.valueOf(m509d()).compareTo(Boolean.valueOf(ibVar.m509d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (m509d() && (iA8 = jr.a(this.f563a, ibVar.f563a)) != 0) {
            return iA8;
        }
        int iCompareTo5 = Boolean.valueOf(m510e()).compareTo(Boolean.valueOf(ibVar.m510e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (m510e() && (iA7 = jr.a(this.f568b, ibVar.f568b)) != 0) {
            return iA7;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ibVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA6 = jr.a(this.f567a, ibVar.f567a)) != 0) {
            return iA6;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(ibVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA5 = jr.a(this.f571d, ibVar.f571d)) != 0) {
            return iA5;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(ibVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA4 = jr.a(this.f572e, ibVar.f572e)) != 0) {
            return iA4;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(ibVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA3 = jr.a(this.f573f, ibVar.f573f)) != 0) {
            return iA3;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(ibVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA2 = jr.a(this.f566a, ibVar.f566a)) != 0) {
            return iA2;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(ibVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (!k() || (iA = jr.a(this.f574g, ibVar.f574g)) == 0) {
            return 0;
        }
        return iA;
    }

    public long a() {
        return this.f568b;
    }

    public ib a(long j2) {
        this.f563a = j2;
        m504a(true);
        return this;
    }

    public ib a(String str) {
        this.f564a = str;
        return this;
    }

    public ib a(boolean z2) {
        this.f567a = z2;
        c(true);
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m502a() {
        return this.f564a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m503a() {
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                m503a();
                return;
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f564a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 11) {
                        this.f569b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f570c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 10) {
                        this.f563a = kbVar.mo661a();
                        m504a(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 10) {
                        this.f568b = kbVar.mo661a();
                        b(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 2) {
                        this.f567a = kbVar.mo672a();
                        c(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f571d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 11) {
                        this.f572e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 11) {
                        this.f573f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 13) {
                        ka kaVarMo664a = kbVar.mo664a();
                        this.f566a = new HashMap(kaVarMo664a.f932a * 2);
                        for (int i2 = 0; i2 < kaVarMo664a.f932a; i2++) {
                            this.f566a.put(kbVar.mo667a(), kbVar.mo667a());
                        }
                        kbVar.i();
                    }
                    kbVar.h();
                    break;
                case 11:
                    if (b3 == 11) {
                        this.f574g = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
            }
            ke.a(kbVar, b3);
            kbVar.h();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m504a(boolean z2) {
        this.f565a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m505a() {
        return this.f564a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m506a(ib ibVar) {
        if (ibVar == null) {
            return false;
        }
        boolean zM505a = m505a();
        boolean zM505a2 = ibVar.m505a();
        if ((zM505a || zM505a2) && !(zM505a && zM505a2 && this.f564a.equals(ibVar.f564a))) {
            return false;
        }
        boolean zM507b = m507b();
        boolean zM507b2 = ibVar.m507b();
        if ((zM507b || zM507b2) && !(zM507b && zM507b2 && this.f569b.equals(ibVar.f569b))) {
            return false;
        }
        boolean zM508c = m508c();
        boolean zM508c2 = ibVar.m508c();
        if ((zM508c || zM508c2) && !(zM508c && zM508c2 && this.f570c.equals(ibVar.f570c))) {
            return false;
        }
        boolean zM509d = m509d();
        boolean zM509d2 = ibVar.m509d();
        if ((zM509d || zM509d2) && !(zM509d && zM509d2 && this.f563a == ibVar.f563a)) {
            return false;
        }
        boolean zM510e = m510e();
        boolean zM510e2 = ibVar.m510e();
        if ((zM510e || zM510e2) && !(zM510e && zM510e2 && this.f568b == ibVar.f568b)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = ibVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f567a == ibVar.f567a)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = ibVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f571d.equals(ibVar.f571d))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = ibVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f572e.equals(ibVar.f572e))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = ibVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f573f.equals(ibVar.f573f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = ibVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f566a.equals(ibVar.f566a))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = ibVar.k();
        if (zK || zK2) {
            return zK && zK2 && this.f574g.equals(ibVar.f574g);
        }
        return true;
    }

    public ib b(long j2) {
        this.f568b = j2;
        b(true);
        return this;
    }

    public ib b(String str) {
        this.f569b = str;
        return this;
    }

    public String b() {
        return this.f570c;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) {
        m503a();
        kbVar.a(f562a);
        if (this.f564a != null && m505a()) {
            kbVar.a(f25136a);
            kbVar.a(this.f564a);
            kbVar.b();
        }
        if (this.f569b != null && m507b()) {
            kbVar.a(f25137b);
            kbVar.a(this.f569b);
            kbVar.b();
        }
        if (this.f570c != null && m508c()) {
            kbVar.a(f25138c);
            kbVar.a(this.f570c);
            kbVar.b();
        }
        if (m509d()) {
            kbVar.a(f25139d);
            kbVar.a(this.f563a);
            kbVar.b();
        }
        if (m510e()) {
            kbVar.a(f25140e);
            kbVar.a(this.f568b);
            kbVar.b();
        }
        if (f()) {
            kbVar.a(f25141f);
            kbVar.a(this.f567a);
            kbVar.b();
        }
        if (this.f571d != null && g()) {
            kbVar.a(f25142g);
            kbVar.a(this.f571d);
            kbVar.b();
        }
        if (this.f572e != null && h()) {
            kbVar.a(f25143h);
            kbVar.a(this.f572e);
            kbVar.b();
        }
        if (this.f573f != null && i()) {
            kbVar.a(f25144i);
            kbVar.a(this.f573f);
            kbVar.b();
        }
        if (this.f566a != null && j()) {
            kbVar.a(f25145j);
            kbVar.a(new ka((byte) 11, (byte) 11, this.f566a.size()));
            for (Map.Entry<String, String> entry : this.f566a.entrySet()) {
                kbVar.a(entry.getKey());
                kbVar.a(entry.getValue());
            }
            kbVar.d();
            kbVar.b();
        }
        if (this.f574g != null && k()) {
            kbVar.a(f25146k);
            kbVar.a(this.f574g);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f565a.set(1, z2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m507b() {
        return this.f569b != null;
    }

    public ib c(String str) {
        this.f570c = str;
        return this;
    }

    public String c() {
        return this.f572e;
    }

    public void c(boolean z2) {
        this.f565a.set(2, z2);
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m508c() {
        return this.f570c != null;
    }

    public ib d(String str) {
        this.f571d = str;
        return this;
    }

    public String d() {
        return this.f573f;
    }

    /* renamed from: d, reason: collision with other method in class */
    public boolean m509d() {
        return this.f565a.get(0);
    }

    public ib e(String str) {
        this.f572e = str;
        return this;
    }

    public String e() {
        return this.f574g;
    }

    /* renamed from: e, reason: collision with other method in class */
    public boolean m510e() {
        return this.f565a.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ib)) {
            return m506a((ib) obj);
        }
        return false;
    }

    public ib f(String str) {
        this.f573f = str;
        return this;
    }

    public boolean f() {
        return this.f565a.get(2);
    }

    public ib g(String str) {
        this.f574g = str;
        return this;
    }

    public boolean g() {
        return this.f571d != null;
    }

    public boolean h() {
        return this.f572e != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f573f != null;
    }

    public boolean j() {
        return this.f566a != null;
    }

    public boolean k() {
        return this.f574g != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("ClientUploadDataItem(");
        boolean z3 = false;
        if (m505a()) {
            sb.append("channel:");
            String str = this.f564a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m507b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("data:");
            String str2 = this.f569b;
            if (str2 == null) {
                sb.append("null");
            } else {
                sb.append(str2);
            }
            z2 = false;
        }
        if (m508c()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("name:");
            String str3 = this.f570c;
            if (str3 == null) {
                sb.append("null");
            } else {
                sb.append(str3);
            }
            z2 = false;
        }
        if (m509d()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("counter:");
            sb.append(this.f563a);
            z2 = false;
        }
        if (m510e()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("timestamp:");
            sb.append(this.f568b);
            z2 = false;
        }
        if (f()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("fromSdk:");
            sb.append(this.f567a);
            z2 = false;
        }
        if (g()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("category:");
            String str4 = this.f571d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
            z2 = false;
        }
        if (h()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("sourcePackage:");
            String str5 = this.f572e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
            z2 = false;
        }
        if (i()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("id:");
            String str6 = this.f573f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
            z2 = false;
        }
        if (j()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("extra:");
            Map<String, String> map = this.f566a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        } else {
            z3 = z2;
        }
        if (k()) {
            if (!z3) {
                sb.append(", ");
            }
            sb.append("pkgName:");
            String str7 = this.f574g;
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
