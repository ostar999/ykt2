package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ja implements jq<ja, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f738a;

    /* renamed from: a, reason: collision with other field name */
    public it f739a;

    /* renamed from: a, reason: collision with other field name */
    public String f740a;

    /* renamed from: a, reason: collision with other field name */
    public List<String> f742a;

    /* renamed from: b, reason: collision with other field name */
    public String f744b;

    /* renamed from: c, reason: collision with other field name */
    public String f745c;

    /* renamed from: d, reason: collision with other field name */
    public String f746d;

    /* renamed from: e, reason: collision with other field name */
    public String f747e;

    /* renamed from: f, reason: collision with other field name */
    public String f748f;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f737a = new kg("XmPushActionCommandResult");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25336a = new jy("", (byte) 12, 2);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25337b = new jy("", (byte) 11, 3);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25338c = new jy("", (byte) 11, 4);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25339d = new jy("", (byte) 11, 5);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25340e = new jy("", (byte) 10, 7);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25341f = new jy("", (byte) 11, 8);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25342g = new jy("", (byte) 11, 9);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25343h = new jy("", (byte) 15, 10);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25344i = new jy("", (byte) 11, 12);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25345j = new jy("", (byte) 2, 13);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f741a = new BitSet(2);

    /* renamed from: a, reason: collision with other field name */
    public boolean f743a = true;

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ja jaVar) {
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
        if (!getClass().equals(jaVar.getClass())) {
            return getClass().getName().compareTo(jaVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m592a()).compareTo(Boolean.valueOf(jaVar.m592a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m592a() && (iA10 = jr.a(this.f739a, jaVar.f739a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(m594b()).compareTo(Boolean.valueOf(jaVar.m594b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m594b() && (iA9 = jr.a(this.f740a, jaVar.f740a)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jaVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA8 = jr.a(this.f744b, jaVar.f744b)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jaVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = jr.a(this.f745c, jaVar.f745c)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jaVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = jr.a(this.f738a, jaVar.f738a)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jaVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = jr.a(this.f746d, jaVar.f746d)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jaVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = jr.a(this.f747e, jaVar.f747e)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jaVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA3 = jr.a(this.f742a, jaVar.f742a)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jaVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA2 = jr.a(this.f748f, jaVar.f748f)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jaVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!j() || (iA = jr.a(this.f743a, jaVar.f743a)) == 0) {
            return 0;
        }
        return iA;
    }

    public String a() {
        return this.f745c;
    }

    /* renamed from: a, reason: collision with other method in class */
    public List<String> m590a() {
        return this.f742a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m591a() throws kc {
        if (this.f740a == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f744b == null) {
            throw new kc("Required field 'appId' was not present! Struct: " + toString());
        }
        if (this.f745c != null) {
            return;
        }
        throw new kc("Required field 'cmdName' was not present! Struct: " + toString());
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
                    m591a();
                    return;
                }
                throw new kc("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (jyVarMo662a.f928a) {
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f739a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f740a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f744b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 11) {
                        this.f745c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 10) {
                        this.f738a = kbVar.mo661a();
                        a(true);
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 11) {
                        this.f746d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 11) {
                        this.f747e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 15) {
                        jz jzVarMo663a = kbVar.mo663a();
                        this.f742a = new ArrayList(jzVarMo663a.f929a);
                        for (int i2 = 0; i2 < jzVarMo663a.f929a; i2++) {
                            this.f742a.add(kbVar.mo667a());
                        }
                        kbVar.j();
                    }
                    kbVar.h();
                    break;
                case 12:
                    if (b3 == 11) {
                        this.f748f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 13:
                    if (b3 == 2) {
                        this.f743a = kbVar.mo672a();
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
        this.f741a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m592a() {
        return this.f739a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m593a(ja jaVar) {
        if (jaVar == null) {
            return false;
        }
        boolean zM592a = m592a();
        boolean zM592a2 = jaVar.m592a();
        if ((zM592a || zM592a2) && !(zM592a && zM592a2 && this.f739a.m569a(jaVar.f739a))) {
            return false;
        }
        boolean zM594b = m594b();
        boolean zM594b2 = jaVar.m594b();
        if ((zM594b || zM594b2) && !(zM594b && zM594b2 && this.f740a.equals(jaVar.f740a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jaVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f744b.equals(jaVar.f744b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jaVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.f745c.equals(jaVar.f745c))) || this.f738a != jaVar.f738a) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jaVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f746d.equals(jaVar.f746d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jaVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f747e.equals(jaVar.f747e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jaVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f742a.equals(jaVar.f742a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jaVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f748f.equals(jaVar.f748f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jaVar.j();
        if (zJ || zJ2) {
            return zJ && zJ2 && this.f743a == jaVar.f743a;
        }
        return true;
    }

    public String b() {
        return this.f748f;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m591a();
        kbVar.a(f737a);
        if (this.f739a != null && m592a()) {
            kbVar.a(f25336a);
            this.f739a.b(kbVar);
            kbVar.b();
        }
        if (this.f740a != null) {
            kbVar.a(f25337b);
            kbVar.a(this.f740a);
            kbVar.b();
        }
        if (this.f744b != null) {
            kbVar.a(f25338c);
            kbVar.a(this.f744b);
            kbVar.b();
        }
        if (this.f745c != null) {
            kbVar.a(f25339d);
            kbVar.a(this.f745c);
            kbVar.b();
        }
        kbVar.a(f25340e);
        kbVar.a(this.f738a);
        kbVar.b();
        if (this.f746d != null && f()) {
            kbVar.a(f25341f);
            kbVar.a(this.f746d);
            kbVar.b();
        }
        if (this.f747e != null && g()) {
            kbVar.a(f25342g);
            kbVar.a(this.f747e);
            kbVar.b();
        }
        if (this.f742a != null && h()) {
            kbVar.a(f25343h);
            kbVar.a(new jz((byte) 11, this.f742a.size()));
            Iterator<String> it = this.f742a.iterator();
            while (it.hasNext()) {
                kbVar.a(it.next());
            }
            kbVar.e();
            kbVar.b();
        }
        if (this.f748f != null && i()) {
            kbVar.a(f25344i);
            kbVar.a(this.f748f);
            kbVar.b();
        }
        if (j()) {
            kbVar.a(f25345j);
            kbVar.a(this.f743a);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f741a.set(1, z2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m594b() {
        return this.f740a != null;
    }

    public boolean c() {
        return this.f744b != null;
    }

    public boolean d() {
        return this.f745c != null;
    }

    public boolean e() {
        return this.f741a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ja)) {
            return m593a((ja) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f746d != null;
    }

    public boolean g() {
        return this.f747e != null;
    }

    public boolean h() {
        return this.f742a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f748f != null;
    }

    public boolean j() {
        return this.f741a.get(1);
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionCommandResult(");
        if (m592a()) {
            sb.append("target:");
            it itVar = this.f739a;
            if (itVar == null) {
                sb.append("null");
            } else {
                sb.append(itVar);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z2) {
            sb.append(", ");
        }
        sb.append("id:");
        String str = this.f740a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("appId:");
        String str2 = this.f744b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("cmdName:");
        String str3 = this.f745c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f738a);
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f746d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f747e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("cmdArgs:");
            List<String> list = this.f742a;
            if (list == null) {
                sb.append("null");
            } else {
                sb.append(list);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("category:");
            String str6 = this.f748f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("response2Client:");
            sb.append(this.f743a);
        }
        sb.append(")");
        return sb.toString();
    }
}
