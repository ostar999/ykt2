package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class ji implements jq<ji, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public ip f840a;

    /* renamed from: a, reason: collision with other field name */
    public it f841a;

    /* renamed from: a, reason: collision with other field name */
    public String f842a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f844a;

    /* renamed from: b, reason: collision with other field name */
    public String f846b;

    /* renamed from: c, reason: collision with other field name */
    public String f847c;

    /* renamed from: d, reason: collision with other field name */
    public String f848d;

    /* renamed from: e, reason: collision with other field name */
    public String f849e;

    /* renamed from: f, reason: collision with other field name */
    public String f850f;

    /* renamed from: g, reason: collision with other field name */
    public String f851g;

    /* renamed from: h, reason: collision with other field name */
    public String f852h;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f839a = new kg("XmPushActionSendMessage");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25422a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25423b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25424c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25425d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25426e = new jy("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25427f = new jy("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25428g = new jy("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25429h = new jy("", (byte) 12, 8);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25430i = new jy("", (byte) 2, 9);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25431j = new jy("", (byte) 13, 10);

    /* renamed from: k, reason: collision with root package name */
    private static final jy f25432k = new jy("", (byte) 11, 11);

    /* renamed from: l, reason: collision with root package name */
    private static final jy f25433l = new jy("", (byte) 11, 12);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f843a = new BitSet(1);

    /* renamed from: a, reason: collision with other field name */
    public boolean f845a = true;

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ji jiVar) {
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
        int iA12;
        if (!getClass().equals(jiVar.getClass())) {
            return getClass().getName().compareTo(jiVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m632a()).compareTo(Boolean.valueOf(jiVar.m632a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m632a() && (iA12 = jr.a(this.f842a, jiVar.f842a)) != 0) {
            return iA12;
        }
        int iCompareTo2 = Boolean.valueOf(m634b()).compareTo(Boolean.valueOf(jiVar.m634b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m634b() && (iA11 = jr.a(this.f841a, jiVar.f841a)) != 0) {
            return iA11;
        }
        int iCompareTo3 = Boolean.valueOf(m635c()).compareTo(Boolean.valueOf(jiVar.m635c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m635c() && (iA10 = jr.a(this.f846b, jiVar.f846b)) != 0) {
            return iA10;
        }
        int iCompareTo4 = Boolean.valueOf(m636d()).compareTo(Boolean.valueOf(jiVar.m636d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (m636d() && (iA9 = jr.a(this.f847c, jiVar.f847c)) != 0) {
            return iA9;
        }
        int iCompareTo5 = Boolean.valueOf(m637e()).compareTo(Boolean.valueOf(jiVar.m637e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (m637e() && (iA8 = jr.a(this.f848d, jiVar.f848d)) != 0) {
            return iA8;
        }
        int iCompareTo6 = Boolean.valueOf(m638f()).compareTo(Boolean.valueOf(jiVar.m638f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (m638f() && (iA7 = jr.a(this.f849e, jiVar.f849e)) != 0) {
            return iA7;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jiVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA6 = jr.a(this.f850f, jiVar.f850f)) != 0) {
            return iA6;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jiVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA5 = jr.a(this.f840a, jiVar.f840a)) != 0) {
            return iA5;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jiVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA4 = jr.a(this.f845a, jiVar.f845a)) != 0) {
            return iA4;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jiVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA3 = jr.a(this.f844a, jiVar.f844a)) != 0) {
            return iA3;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(jiVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA2 = jr.a(this.f851g, jiVar.f851g)) != 0) {
            return iA2;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(jiVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (!l() || (iA = jr.a(this.f852h, jiVar.f852h)) == 0) {
            return 0;
        }
        return iA;
    }

    public ip a() {
        return this.f840a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m630a() {
        return this.f846b;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m631a() throws kc {
        if (this.f846b == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f847c != null) {
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
                m631a();
                return;
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f842a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f841a = itVar;
                        itVar.a(kbVar);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f846b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f847c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 11) {
                        this.f848d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 11) {
                        this.f849e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f850f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 12) {
                        ip ipVar = new ip();
                        this.f840a = ipVar;
                        ipVar.a(kbVar);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 2) {
                        this.f845a = kbVar.mo672a();
                        a(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 13) {
                        ka kaVarMo664a = kbVar.mo664a();
                        this.f844a = new HashMap(kaVarMo664a.f932a * 2);
                        for (int i2 = 0; i2 < kaVarMo664a.f932a; i2++) {
                            this.f844a.put(kbVar.mo667a(), kbVar.mo667a());
                        }
                        kbVar.i();
                    }
                    kbVar.h();
                    break;
                case 11:
                    if (b3 == 11) {
                        this.f851g = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 12:
                    if (b3 == 11) {
                        this.f852h = kbVar.mo667a();
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
        this.f843a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m632a() {
        return this.f842a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m633a(ji jiVar) {
        if (jiVar == null) {
            return false;
        }
        boolean zM632a = m632a();
        boolean zM632a2 = jiVar.m632a();
        if ((zM632a || zM632a2) && !(zM632a && zM632a2 && this.f842a.equals(jiVar.f842a))) {
            return false;
        }
        boolean zM634b = m634b();
        boolean zM634b2 = jiVar.m634b();
        if ((zM634b || zM634b2) && !(zM634b && zM634b2 && this.f841a.m569a(jiVar.f841a))) {
            return false;
        }
        boolean zM635c = m635c();
        boolean zM635c2 = jiVar.m635c();
        if ((zM635c || zM635c2) && !(zM635c && zM635c2 && this.f846b.equals(jiVar.f846b))) {
            return false;
        }
        boolean zM636d = m636d();
        boolean zM636d2 = jiVar.m636d();
        if ((zM636d || zM636d2) && !(zM636d && zM636d2 && this.f847c.equals(jiVar.f847c))) {
            return false;
        }
        boolean zM637e = m637e();
        boolean zM637e2 = jiVar.m637e();
        if ((zM637e || zM637e2) && !(zM637e && zM637e2 && this.f848d.equals(jiVar.f848d))) {
            return false;
        }
        boolean zM638f = m638f();
        boolean zM638f2 = jiVar.m638f();
        if ((zM638f || zM638f2) && !(zM638f && zM638f2 && this.f849e.equals(jiVar.f849e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jiVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f850f.equals(jiVar.f850f))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jiVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f840a.m549a(jiVar.f840a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jiVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f845a == jiVar.f845a)) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = jiVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f844a.equals(jiVar.f844a))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = jiVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f851g.equals(jiVar.f851g))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = jiVar.l();
        if (zL || zL2) {
            return zL && zL2 && this.f852h.equals(jiVar.f852h);
        }
        return true;
    }

    public String b() {
        return this.f847c;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m631a();
        kbVar.a(f839a);
        if (this.f842a != null && m632a()) {
            kbVar.a(f25422a);
            kbVar.a(this.f842a);
            kbVar.b();
        }
        if (this.f841a != null && m634b()) {
            kbVar.a(f25423b);
            this.f841a.b(kbVar);
            kbVar.b();
        }
        if (this.f846b != null) {
            kbVar.a(f25424c);
            kbVar.a(this.f846b);
            kbVar.b();
        }
        if (this.f847c != null) {
            kbVar.a(f25425d);
            kbVar.a(this.f847c);
            kbVar.b();
        }
        if (this.f848d != null && m637e()) {
            kbVar.a(f25426e);
            kbVar.a(this.f848d);
            kbVar.b();
        }
        if (this.f849e != null && m638f()) {
            kbVar.a(f25427f);
            kbVar.a(this.f849e);
            kbVar.b();
        }
        if (this.f850f != null && g()) {
            kbVar.a(f25428g);
            kbVar.a(this.f850f);
            kbVar.b();
        }
        if (this.f840a != null && h()) {
            kbVar.a(f25429h);
            this.f840a.b(kbVar);
            kbVar.b();
        }
        if (i()) {
            kbVar.a(f25430i);
            kbVar.a(this.f845a);
            kbVar.b();
        }
        if (this.f844a != null && j()) {
            kbVar.a(f25431j);
            kbVar.a(new ka((byte) 11, (byte) 11, this.f844a.size()));
            for (Map.Entry<String, String> entry : this.f844a.entrySet()) {
                kbVar.a(entry.getKey());
                kbVar.a(entry.getValue());
            }
            kbVar.d();
            kbVar.b();
        }
        if (this.f851g != null && k()) {
            kbVar.a(f25432k);
            kbVar.a(this.f851g);
            kbVar.b();
        }
        if (this.f852h != null && l()) {
            kbVar.a(f25433l);
            kbVar.a(this.f852h);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m634b() {
        return this.f841a != null;
    }

    public String c() {
        return this.f849e;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m635c() {
        return this.f846b != null;
    }

    public String d() {
        return this.f850f;
    }

    /* renamed from: d, reason: collision with other method in class */
    public boolean m636d() {
        return this.f847c != null;
    }

    public String e() {
        return this.f851g;
    }

    /* renamed from: e, reason: collision with other method in class */
    public boolean m637e() {
        return this.f848d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ji)) {
            return m633a((ji) obj);
        }
        return false;
    }

    public String f() {
        return this.f852h;
    }

    /* renamed from: f, reason: collision with other method in class */
    public boolean m638f() {
        return this.f849e != null;
    }

    public boolean g() {
        return this.f850f != null;
    }

    public boolean h() {
        return this.f840a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f843a.get(0);
    }

    public boolean j() {
        return this.f844a != null;
    }

    public boolean k() {
        return this.f851g != null;
    }

    public boolean l() {
        return this.f852h != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionSendMessage(");
        boolean z3 = false;
        if (m632a()) {
            sb.append("debug:");
            String str = this.f842a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m634b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            it itVar = this.f841a;
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
        String str2 = this.f846b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f847c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        if (m637e()) {
            sb.append(", ");
            sb.append("packageName:");
            String str4 = this.f848d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (m638f()) {
            sb.append(", ");
            sb.append("topic:");
            String str5 = this.f849e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str6 = this.f850f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("message:");
            ip ipVar = this.f840a;
            if (ipVar == null) {
                sb.append("null");
            } else {
                sb.append(ipVar);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("needAck:");
            sb.append(this.f845a);
        }
        if (j()) {
            sb.append(", ");
            sb.append("params:");
            Map<String, String> map = this.f844a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f851g;
            if (str7 == null) {
                sb.append("null");
            } else {
                sb.append(str7);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("userAccount:");
            String str8 = this.f852h;
            if (str8 == null) {
                sb.append("null");
            } else {
                sb.append(str8);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
