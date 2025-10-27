package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class iw implements jq<iw, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public it f708a;

    /* renamed from: a, reason: collision with other field name */
    public String f709a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f711a;

    /* renamed from: b, reason: collision with other field name */
    public String f712b;

    /* renamed from: c, reason: collision with other field name */
    public String f713c;

    /* renamed from: d, reason: collision with other field name */
    public String f714d;

    /* renamed from: e, reason: collision with other field name */
    public String f715e;

    /* renamed from: f, reason: collision with other field name */
    public String f716f;

    /* renamed from: g, reason: collision with other field name */
    public String f717g;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f706a = new kg("XmPushActionAckNotification");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25308a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25309b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25310c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25311d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25312e = new jy("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25313f = new jy("", (byte) 10, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25314g = new jy("", (byte) 11, 8);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25315h = new jy("", (byte) 13, 9);

    /* renamed from: i, reason: collision with root package name */
    private static final jy f25316i = new jy("", (byte) 11, 10);

    /* renamed from: j, reason: collision with root package name */
    private static final jy f25317j = new jy("", (byte) 11, 11);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f710a = new BitSet(1);

    /* renamed from: a, reason: collision with other field name */
    public long f707a = 0;

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(iw iwVar) {
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
        if (!getClass().equals(iwVar.getClass())) {
            return getClass().getName().compareTo(iwVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m576a()).compareTo(Boolean.valueOf(iwVar.m576a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m576a() && (iA10 = jr.a(this.f709a, iwVar.f709a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(iwVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA9 = jr.a(this.f708a, iwVar.f708a)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(iwVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA8 = jr.a(this.f712b, iwVar.f712b)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(iwVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = jr.a(this.f713c, iwVar.f713c)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(iwVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = jr.a(this.f714d, iwVar.f714d)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(iwVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = jr.a(this.f707a, iwVar.f707a)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(iwVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = jr.a(this.f715e, iwVar.f715e)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(iwVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA3 = jr.a(this.f711a, iwVar.f711a)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(iwVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA2 = jr.a(this.f716f, iwVar.f716f)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(iwVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!j() || (iA = jr.a(this.f717g, iwVar.f717g)) == 0) {
            return 0;
        }
        return iA;
    }

    public String a() {
        return this.f712b;
    }

    /* renamed from: a, reason: collision with other method in class */
    public Map<String, String> m574a() {
        return this.f711a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m575a() throws kc {
        if (this.f712b != null) {
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
                m575a();
                return;
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 11) {
                        this.f709a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f708a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f712b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f713c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 11) {
                        this.f714d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 10) {
                        this.f707a = kbVar.mo661a();
                        a(true);
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 11) {
                        this.f715e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 9:
                    if (b3 == 13) {
                        ka kaVarMo664a = kbVar.mo664a();
                        this.f711a = new HashMap(kaVarMo664a.f932a * 2);
                        for (int i2 = 0; i2 < kaVarMo664a.f932a; i2++) {
                            this.f711a.put(kbVar.mo667a(), kbVar.mo667a());
                        }
                        kbVar.i();
                    }
                    kbVar.h();
                    break;
                case 10:
                    if (b3 == 11) {
                        this.f716f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 11:
                    if (b3 == 11) {
                        this.f717g = kbVar.mo667a();
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
        this.f710a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m576a() {
        return this.f709a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m577a(iw iwVar) {
        if (iwVar == null) {
            return false;
        }
        boolean zM576a = m576a();
        boolean zM576a2 = iwVar.m576a();
        if ((zM576a || zM576a2) && !(zM576a && zM576a2 && this.f709a.equals(iwVar.f709a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = iwVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f708a.m569a(iwVar.f708a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = iwVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f712b.equals(iwVar.f712b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = iwVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f713c.equals(iwVar.f713c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = iwVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f714d.equals(iwVar.f714d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = iwVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f707a == iwVar.f707a)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = iwVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f715e.equals(iwVar.f715e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = iwVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f711a.equals(iwVar.f711a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = iwVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f716f.equals(iwVar.f716f))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = iwVar.j();
        if (zJ || zJ2) {
            return zJ && zJ2 && this.f717g.equals(iwVar.f717g);
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m575a();
        kbVar.a(f706a);
        if (this.f709a != null && m576a()) {
            kbVar.a(f25308a);
            kbVar.a(this.f709a);
            kbVar.b();
        }
        if (this.f708a != null && b()) {
            kbVar.a(f25309b);
            this.f708a.b(kbVar);
            kbVar.b();
        }
        if (this.f712b != null) {
            kbVar.a(f25310c);
            kbVar.a(this.f712b);
            kbVar.b();
        }
        if (this.f713c != null && d()) {
            kbVar.a(f25311d);
            kbVar.a(this.f713c);
            kbVar.b();
        }
        if (this.f714d != null && e()) {
            kbVar.a(f25312e);
            kbVar.a(this.f714d);
            kbVar.b();
        }
        if (f()) {
            kbVar.a(f25313f);
            kbVar.a(this.f707a);
            kbVar.b();
        }
        if (this.f715e != null && g()) {
            kbVar.a(f25314g);
            kbVar.a(this.f715e);
            kbVar.b();
        }
        if (this.f711a != null && h()) {
            kbVar.a(f25315h);
            kbVar.a(new ka((byte) 11, (byte) 11, this.f711a.size()));
            for (Map.Entry<String, String> entry : this.f711a.entrySet()) {
                kbVar.a(entry.getKey());
                kbVar.a(entry.getValue());
            }
            kbVar.d();
            kbVar.b();
        }
        if (this.f716f != null && i()) {
            kbVar.a(f25316i);
            kbVar.a(this.f716f);
            kbVar.b();
        }
        if (this.f717g != null && j()) {
            kbVar.a(f25317j);
            kbVar.a(this.f717g);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public boolean b() {
        return this.f708a != null;
    }

    public boolean c() {
        return this.f712b != null;
    }

    public boolean d() {
        return this.f713c != null;
    }

    public boolean e() {
        return this.f714d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof iw)) {
            return m577a((iw) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f710a.get(0);
    }

    public boolean g() {
        return this.f715e != null;
    }

    public boolean h() {
        return this.f711a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f716f != null;
    }

    public boolean j() {
        return this.f717g != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionAckNotification(");
        boolean z3 = false;
        if (m576a()) {
            sb.append("debug:");
            String str = this.f709a;
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
            it itVar = this.f708a;
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
        String str2 = this.f712b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            String str3 = this.f713c;
            if (str3 == null) {
                sb.append("null");
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("type:");
            String str4 = this.f714d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("errorCode:");
            sb.append(this.f707a);
        }
        if (g()) {
            sb.append(", ");
            sb.append("reason:");
            String str5 = this.f715e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f711a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f716f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.f717g;
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
