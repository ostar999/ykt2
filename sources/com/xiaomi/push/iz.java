package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class iz implements jq<iz, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f725a;

    /* renamed from: a, reason: collision with other field name */
    public it f726a;

    /* renamed from: a, reason: collision with other field name */
    public String f727a;

    /* renamed from: a, reason: collision with other field name */
    public List<String> f729a;

    /* renamed from: b, reason: collision with other field name */
    public String f731b;

    /* renamed from: c, reason: collision with other field name */
    public String f733c;

    /* renamed from: d, reason: collision with other field name */
    public String f734d;

    /* renamed from: e, reason: collision with other field name */
    public String f735e;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f724a = new kg("XmPushActionCommand");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25321a = new jy("", (byte) 12, 2);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25322b = new jy("", (byte) 11, 3);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25323c = new jy("", (byte) 11, 4);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25324d = new jy("", (byte) 11, 5);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25325e = new jy("", (byte) 15, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25326f = new jy("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25327g = new jy("", (byte) 11, 9);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25328h = new jy("", (byte) 2, 10);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25329i = new jy("", (byte) 2, 11);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25330j = new jy("", (byte) 10, 12);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f728a = new BitSet(3);

    /* renamed from: a, reason: collision with other field name */
    public boolean f730a = false;

    /* renamed from: b, reason: collision with other field name */
    public boolean f732b = true;

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(iz izVar) {
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
        if (!getClass().equals(izVar.getClass())) {
            return getClass().getName().compareTo(izVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m584a()).compareTo(Boolean.valueOf(izVar.m584a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m584a() && (iA10 = jr.a(this.f726a, izVar.f726a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(izVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA9 = jr.a(this.f727a, izVar.f727a)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(izVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA8 = jr.a(this.f731b, izVar.f731b)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(izVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = jr.a(this.f733c, izVar.f733c)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(izVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = jr.a(this.f729a, izVar.f729a)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(izVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = jr.a(this.f734d, izVar.f734d)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(izVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = jr.a(this.f735e, izVar.f735e)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(izVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA3 = jr.a(this.f730a, izVar.f730a)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(izVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA2 = jr.a(this.f732b, izVar.f732b)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(izVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!j() || (iA = jr.a(this.f725a, izVar.f725a)) == 0) {
            return 0;
        }
        return iA;
    }

    public iz a(String str) {
        this.f727a = str;
        return this;
    }

    public iz a(List<String> list) {
        this.f729a = list;
        return this;
    }

    public String a() {
        return this.f733c;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m582a() throws kc {
        if (this.f727a == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f731b == null) {
            throw new kc("Required field 'appId' was not present! Struct: " + toString());
        }
        if (this.f733c != null) {
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
                m582a();
                return;
            }
            switch (jyVarMo662a.f928a) {
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f726a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f727a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f731b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 11) {
                        this.f733c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 15) {
                        jz jzVarMo663a = kbVar.mo663a();
                        this.f729a = new ArrayList(jzVarMo663a.f929a);
                        for (int i2 = 0; i2 < jzVarMo663a.f929a; i2++) {
                            this.f729a.add(kbVar.mo667a());
                        }
                        kbVar.j();
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f734d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 11) {
                        this.f735e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 2) {
                        this.f730a = kbVar.mo672a();
                        a(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 11:
                    if (b3 == 2) {
                        this.f732b = kbVar.mo672a();
                        b(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 12:
                    if (b3 == 10) {
                        this.f725a = kbVar.mo661a();
                        c(true);
                    }
                    kbVar.h();
                    break;
            }
            ke.a(kbVar, b3);
            kbVar.h();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m583a(String str) {
        if (this.f729a == null) {
            this.f729a = new ArrayList();
        }
        this.f729a.add(str);
    }

    public void a(boolean z2) {
        this.f728a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m584a() {
        return this.f726a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m585a(iz izVar) {
        if (izVar == null) {
            return false;
        }
        boolean zM584a = m584a();
        boolean zM584a2 = izVar.m584a();
        if ((zM584a || zM584a2) && !(zM584a && zM584a2 && this.f726a.m569a(izVar.f726a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = izVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f727a.equals(izVar.f727a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = izVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f731b.equals(izVar.f731b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = izVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f733c.equals(izVar.f733c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = izVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f729a.equals(izVar.f729a))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = izVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f734d.equals(izVar.f734d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = izVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f735e.equals(izVar.f735e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = izVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f730a == izVar.f730a)) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = izVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f732b == izVar.f732b)) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = izVar.j();
        if (zJ || zJ2) {
            return zJ && zJ2 && this.f725a == izVar.f725a;
        }
        return true;
    }

    public iz b(String str) {
        this.f731b = str;
        return this;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m582a();
        kbVar.a(f724a);
        if (this.f726a != null && m584a()) {
            kbVar.a(f25321a);
            this.f726a.b(kbVar);
            kbVar.b();
        }
        if (this.f727a != null) {
            kbVar.a(f25322b);
            kbVar.a(this.f727a);
            kbVar.b();
        }
        if (this.f731b != null) {
            kbVar.a(f25323c);
            kbVar.a(this.f731b);
            kbVar.b();
        }
        if (this.f733c != null) {
            kbVar.a(f25324d);
            kbVar.a(this.f733c);
            kbVar.b();
        }
        if (this.f729a != null && e()) {
            kbVar.a(f25325e);
            kbVar.a(new jz((byte) 11, this.f729a.size()));
            Iterator<String> it = this.f729a.iterator();
            while (it.hasNext()) {
                kbVar.a(it.next());
            }
            kbVar.e();
            kbVar.b();
        }
        if (this.f734d != null && f()) {
            kbVar.a(f25326f);
            kbVar.a(this.f734d);
            kbVar.b();
        }
        if (this.f735e != null && g()) {
            kbVar.a(f25327g);
            kbVar.a(this.f735e);
            kbVar.b();
        }
        if (h()) {
            kbVar.a(f25328h);
            kbVar.a(this.f730a);
            kbVar.b();
        }
        if (i()) {
            kbVar.a(f25329i);
            kbVar.a(this.f732b);
            kbVar.b();
        }
        if (j()) {
            kbVar.a(f25330j);
            kbVar.a(this.f725a);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f728a.set(1, z2);
    }

    public boolean b() {
        return this.f727a != null;
    }

    public iz c(String str) {
        this.f733c = str;
        return this;
    }

    public void c(boolean z2) {
        this.f728a.set(2, z2);
    }

    public boolean c() {
        return this.f731b != null;
    }

    public iz d(String str) {
        this.f734d = str;
        return this;
    }

    public boolean d() {
        return this.f733c != null;
    }

    public iz e(String str) {
        this.f735e = str;
        return this;
    }

    public boolean e() {
        return this.f729a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof iz)) {
            return m585a((iz) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f734d != null;
    }

    public boolean g() {
        return this.f735e != null;
    }

    public boolean h() {
        return this.f728a.get(0);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f728a.get(1);
    }

    public boolean j() {
        return this.f728a.get(2);
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionCommand(");
        if (m584a()) {
            sb.append("target:");
            it itVar = this.f726a;
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
        String str = this.f727a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("appId:");
        String str2 = this.f731b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("cmdName:");
        String str3 = this.f733c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("cmdArgs:");
            List<String> list = this.f729a;
            if (list == null) {
                sb.append("null");
            } else {
                sb.append(list);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            String str4 = this.f734d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("category:");
            String str5 = this.f735e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("updateCache:");
            sb.append(this.f730a);
        }
        if (i()) {
            sb.append(", ");
            sb.append("response2Client:");
            sb.append(this.f732b);
        }
        if (j()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.f725a);
        }
        sb.append(")");
        return sb.toString();
    }
}
