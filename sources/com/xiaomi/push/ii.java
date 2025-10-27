package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ii implements jq<ii, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public double f595a;

    /* renamed from: a, reason: collision with other field name */
    public long f596a;

    /* renamed from: a, reason: collision with other field name */
    public Cif f597a;

    /* renamed from: a, reason: collision with other field name */
    public ij f598a;

    /* renamed from: a, reason: collision with other field name */
    public ik f599a;

    /* renamed from: a, reason: collision with other field name */
    public String f600a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f601a = new BitSet(3);

    /* renamed from: a, reason: collision with other field name */
    public List<ik> f602a;

    /* renamed from: b, reason: collision with other field name */
    public long f603b;

    /* renamed from: b, reason: collision with other field name */
    public String f604b;

    /* renamed from: c, reason: collision with other field name */
    public String f605c;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f594a = new kg("GeoFencing");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25191a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25192b = new jy("", (byte) 11, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25193c = new jy("", (byte) 10, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25194d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25195e = new jy("", (byte) 10, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25196f = new jy("", (byte) 8, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25197g = new jy("", (byte) 12, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25198h = new jy("", (byte) 4, 9);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25199i = new jy("", (byte) 15, 10);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25200j = new jy("", (byte) 8, 11);

    public double a() {
        return this.f595a;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ii iiVar) {
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
        if (!getClass().equals(iiVar.getClass())) {
            return getClass().getName().compareTo(iiVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m523a()).compareTo(Boolean.valueOf(iiVar.m523a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m523a() && (iA10 = jr.a(this.f600a, iiVar.f600a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(m526b()).compareTo(Boolean.valueOf(iiVar.m526b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m526b() && (iA9 = jr.a(this.f604b, iiVar.f604b)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(m527c()).compareTo(Boolean.valueOf(iiVar.m527c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m527c() && (iA8 = jr.a(this.f596a, iiVar.f596a)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(iiVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = jr.a(this.f605c, iiVar.f605c)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(iiVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = jr.a(this.f603b, iiVar.f603b)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(iiVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = jr.a(this.f598a, iiVar.f598a)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(iiVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = jr.a(this.f599a, iiVar.f599a)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(iiVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA3 = jr.a(this.f595a, iiVar.f595a)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(iiVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA2 = jr.a(this.f602a, iiVar.f602a)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(iiVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!j() || (iA = jr.a(this.f597a, iiVar.f597a)) == 0) {
            return 0;
        }
        return iA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m516a() {
        return this.f596a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public Cif m517a() {
        return this.f597a;
    }

    public ii a(double d3) {
        this.f595a = d3;
        c(true);
        return this;
    }

    public ii a(long j2) {
        this.f596a = j2;
        a(true);
        return this;
    }

    public ii a(Cif cif) {
        this.f597a = cif;
        return this;
    }

    public ii a(ij ijVar) {
        this.f598a = ijVar;
        return this;
    }

    public ii a(ik ikVar) {
        this.f599a = ikVar;
        return this;
    }

    public ii a(String str) {
        this.f600a = str;
        return this;
    }

    public ii a(List<ik> list) {
        this.f602a = list;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public ij m518a() {
        return this.f598a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public ik m519a() {
        return this.f599a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m520a() {
        return this.f600a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public List<ik> m521a() {
        return this.f602a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m522a() throws kc {
        if (this.f600a == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f604b == null) {
            throw new kc("Required field 'name' was not present! Struct: " + toString());
        }
        if (this.f605c == null) {
            throw new kc("Required field 'packageName' was not present! Struct: " + toString());
        }
        if (this.f598a == null) {
            throw new kc("Required field 'type' was not present! Struct: " + toString());
        }
        if (this.f597a != null) {
            return;
        }
        throw new kc("Required field 'coordinateProvider' was not present! Struct: " + toString());
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) throws kc {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                if (!m527c()) {
                    throw new kc("Required field 'appId' was not found in serialized data! Struct: " + toString());
                }
                if (e()) {
                    m522a();
                    return;
                }
                throw new kc("Required field 'createTime' was not found in serialized data! Struct: " + toString());
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f600a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 11) {
                        this.f604b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 10) {
                        this.f596a = kbVar.mo661a();
                        a(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f605c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 10) {
                        this.f603b = kbVar.mo661a();
                        b(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 8) {
                        this.f598a = ij.a(kbVar.mo660a());
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 12) {
                        ik ikVar = new ik();
                        this.f599a = ikVar;
                        ikVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 4) {
                        this.f595a = kbVar.mo659a();
                        c(true);
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 15) {
                        jz jzVarMo663a = kbVar.mo663a();
                        this.f602a = new ArrayList(jzVarMo663a.f929a);
                        for (int i2 = 0; i2 < jzVarMo663a.f929a; i2++) {
                            ik ikVar2 = new ik();
                            ikVar2.a(kbVar);
                            this.f602a.add(ikVar2);
                        }
                        kbVar.j();
                    }
                    kbVar.h();
                    break;
                case 11:
                    if (b3 == 8) {
                        this.f597a = Cif.a(kbVar.mo660a());
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
        this.f601a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m523a() {
        return this.f600a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m524a(ii iiVar) {
        if (iiVar == null) {
            return false;
        }
        boolean zM523a = m523a();
        boolean zM523a2 = iiVar.m523a();
        if ((zM523a || zM523a2) && !(zM523a && zM523a2 && this.f600a.equals(iiVar.f600a))) {
            return false;
        }
        boolean zM526b = m526b();
        boolean zM526b2 = iiVar.m526b();
        if (((zM526b || zM526b2) && !(zM526b && zM526b2 && this.f604b.equals(iiVar.f604b))) || this.f596a != iiVar.f596a) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = iiVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.f605c.equals(iiVar.f605c))) || this.f603b != iiVar.f603b) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = iiVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f598a.equals(iiVar.f598a))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = iiVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f599a.m530a(iiVar.f599a))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = iiVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f595a == iiVar.f595a)) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = iiVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f602a.equals(iiVar.f602a))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = iiVar.j();
        if (zJ || zJ2) {
            return zJ && zJ2 && this.f597a.equals(iiVar.f597a);
        }
        return true;
    }

    public long b() {
        return this.f603b;
    }

    public ii b(long j2) {
        this.f603b = j2;
        b(true);
        return this;
    }

    public ii b(String str) {
        this.f604b = str;
        return this;
    }

    /* renamed from: b, reason: collision with other method in class */
    public String m525b() {
        return this.f604b;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m522a();
        kbVar.a(f594a);
        if (this.f600a != null) {
            kbVar.a(f25191a);
            kbVar.a(this.f600a);
            kbVar.b();
        }
        if (this.f604b != null) {
            kbVar.a(f25192b);
            kbVar.a(this.f604b);
            kbVar.b();
        }
        kbVar.a(f25193c);
        kbVar.a(this.f596a);
        kbVar.b();
        if (this.f605c != null) {
            kbVar.a(f25194d);
            kbVar.a(this.f605c);
            kbVar.b();
        }
        kbVar.a(f25195e);
        kbVar.a(this.f603b);
        kbVar.b();
        if (this.f598a != null) {
            kbVar.a(f25196f);
            kbVar.mo671a(this.f598a.a());
            kbVar.b();
        }
        if (this.f599a != null && g()) {
            kbVar.a(f25197g);
            this.f599a.b(kbVar);
            kbVar.b();
        }
        if (h()) {
            kbVar.a(f25198h);
            kbVar.a(this.f595a);
            kbVar.b();
        }
        if (this.f602a != null && i()) {
            kbVar.a(f25199i);
            kbVar.a(new jz((byte) 12, this.f602a.size()));
            Iterator<ik> it = this.f602a.iterator();
            while (it.hasNext()) {
                it.next().b(kbVar);
            }
            kbVar.e();
            kbVar.b();
        }
        if (this.f597a != null) {
            kbVar.a(f25200j);
            kbVar.mo671a(this.f597a.a());
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f601a.set(1, z2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m526b() {
        return this.f604b != null;
    }

    public ii c(String str) {
        this.f605c = str;
        return this;
    }

    public String c() {
        return this.f605c;
    }

    public void c(boolean z2) {
        this.f601a.set(2, z2);
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m527c() {
        return this.f601a.get(0);
    }

    public boolean d() {
        return this.f605c != null;
    }

    public boolean e() {
        return this.f601a.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ii)) {
            return m524a((ii) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f598a != null;
    }

    public boolean g() {
        return this.f599a != null;
    }

    public boolean h() {
        return this.f601a.get(2);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f602a != null;
    }

    public boolean j() {
        return this.f597a != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GeoFencing(");
        sb.append("id:");
        String str = this.f600a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("name:");
        String str2 = this.f604b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        sb.append(this.f596a);
        sb.append(", ");
        sb.append("packageName:");
        String str3 = this.f605c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("createTime:");
        sb.append(this.f603b);
        sb.append(", ");
        sb.append("type:");
        ij ijVar = this.f598a;
        if (ijVar == null) {
            sb.append("null");
        } else {
            sb.append(ijVar);
        }
        if (g()) {
            sb.append(", ");
            sb.append("circleCenter:");
            ik ikVar = this.f599a;
            if (ikVar == null) {
                sb.append("null");
            } else {
                sb.append(ikVar);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("circleRadius:");
            sb.append(this.f595a);
        }
        if (i()) {
            sb.append(", ");
            sb.append("polygonPoints:");
            List<ik> list = this.f602a;
            if (list == null) {
                sb.append("null");
            } else {
                sb.append(list);
            }
        }
        sb.append(", ");
        sb.append("coordinateProvider:");
        Cif cif = this.f597a;
        if (cif == null) {
            sb.append("null");
        } else {
            sb.append(cif);
        }
        sb.append(")");
        return sb.toString();
    }
}
