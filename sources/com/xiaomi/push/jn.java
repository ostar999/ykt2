package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class jn implements jq<jn, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public it f899a;

    /* renamed from: a, reason: collision with other field name */
    public String f900a;

    /* renamed from: a, reason: collision with other field name */
    public List<String> f901a;

    /* renamed from: b, reason: collision with other field name */
    public String f902b;

    /* renamed from: c, reason: collision with other field name */
    public String f903c;

    /* renamed from: d, reason: collision with other field name */
    public String f904d;

    /* renamed from: e, reason: collision with other field name */
    public String f905e;

    /* renamed from: f, reason: collision with other field name */
    public String f906f;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f898a = new kg("XmPushActionUnSubscription");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25472a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25473b = new jy("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25474c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25475d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25476e = new jy("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25477f = new jy("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25478g = new jy("", (byte) 11, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25479h = new jy("", (byte) 15, 8);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jn jnVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        if (!getClass().equals(jnVar.getClass())) {
            return getClass().getName().compareTo(jnVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m650a()).compareTo(Boolean.valueOf(jnVar.m650a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m650a() && (iA8 = jr.a(this.f900a, jnVar.f900a)) != 0) {
            return iA8;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jnVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA7 = jr.a(this.f899a, jnVar.f899a)) != 0) {
            return iA7;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jnVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA6 = jr.a(this.f902b, jnVar.f902b)) != 0) {
            return iA6;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jnVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA5 = jr.a(this.f903c, jnVar.f903c)) != 0) {
            return iA5;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jnVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA4 = jr.a(this.f904d, jnVar.f904d)) != 0) {
            return iA4;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jnVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA3 = jr.a(this.f905e, jnVar.f905e)) != 0) {
            return iA3;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jnVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA2 = jr.a(this.f906f, jnVar.f906f)) != 0) {
            return iA2;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jnVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (!h() || (iA = jr.a(this.f901a, jnVar.f901a)) == 0) {
            return 0;
        }
        return iA;
    }

    public jn a(String str) {
        this.f902b = str;
        return this;
    }

    public void a() throws kc {
        if (this.f902b == null) {
            throw new kc("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.f903c == null) {
            throw new kc("Required field 'appId' was not present! Struct: " + toString());
        }
        if (this.f904d != null) {
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
                        this.f900a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f899a = itVar;
                        itVar.a(kbVar);
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 11) {
                        this.f902b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f903c = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 11) {
                        this.f904d = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 11) {
                        this.f905e = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 11) {
                        this.f906f = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 15) {
                        jz jzVarMo663a = kbVar.mo663a();
                        this.f901a = new ArrayList(jzVarMo663a.f929a);
                        for (int i2 = 0; i2 < jzVarMo663a.f929a; i2++) {
                            this.f901a.add(kbVar.mo667a());
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
    public boolean m650a() {
        return this.f900a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m651a(jn jnVar) {
        if (jnVar == null) {
            return false;
        }
        boolean zM650a = m650a();
        boolean zM650a2 = jnVar.m650a();
        if ((zM650a || zM650a2) && !(zM650a && zM650a2 && this.f900a.equals(jnVar.f900a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jnVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f899a.m569a(jnVar.f899a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = jnVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f902b.equals(jnVar.f902b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jnVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f903c.equals(jnVar.f903c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jnVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f904d.equals(jnVar.f904d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jnVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f905e.equals(jnVar.f905e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jnVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f906f.equals(jnVar.f906f))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jnVar.h();
        if (zH || zH2) {
            return zH && zH2 && this.f901a.equals(jnVar.f901a);
        }
        return true;
    }

    public jn b(String str) {
        this.f903c = str;
        return this;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        a();
        kbVar.a(f898a);
        if (this.f900a != null && m650a()) {
            kbVar.a(f25472a);
            kbVar.a(this.f900a);
            kbVar.b();
        }
        if (this.f899a != null && b()) {
            kbVar.a(f25473b);
            this.f899a.b(kbVar);
            kbVar.b();
        }
        if (this.f902b != null) {
            kbVar.a(f25474c);
            kbVar.a(this.f902b);
            kbVar.b();
        }
        if (this.f903c != null) {
            kbVar.a(f25475d);
            kbVar.a(this.f903c);
            kbVar.b();
        }
        if (this.f904d != null) {
            kbVar.a(f25476e);
            kbVar.a(this.f904d);
            kbVar.b();
        }
        if (this.f905e != null && f()) {
            kbVar.a(f25477f);
            kbVar.a(this.f905e);
            kbVar.b();
        }
        if (this.f906f != null && g()) {
            kbVar.a(f25478g);
            kbVar.a(this.f906f);
            kbVar.b();
        }
        if (this.f901a != null && h()) {
            kbVar.a(f25479h);
            kbVar.a(new jz((byte) 11, this.f901a.size()));
            Iterator<String> it = this.f901a.iterator();
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
        return this.f899a != null;
    }

    public jn c(String str) {
        this.f904d = str;
        return this;
    }

    public boolean c() {
        return this.f902b != null;
    }

    public jn d(String str) {
        this.f905e = str;
        return this;
    }

    public boolean d() {
        return this.f903c != null;
    }

    public jn e(String str) {
        this.f906f = str;
        return this;
    }

    public boolean e() {
        return this.f904d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jn)) {
            return m651a((jn) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f905e != null;
    }

    public boolean g() {
        return this.f906f != null;
    }

    public boolean h() {
        return this.f901a != null;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionUnSubscription(");
        boolean z3 = false;
        if (m650a()) {
            sb.append("debug:");
            String str = this.f900a;
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
            it itVar = this.f899a;
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
        String str2 = this.f902b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f903c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("topic:");
        String str4 = this.f904d;
        if (str4 == null) {
            sb.append("null");
        } else {
            sb.append(str4);
        }
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.f905e;
            if (str5 == null) {
                sb.append("null");
            } else {
                sb.append(str5);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("category:");
            String str6 = this.f906f;
            if (str6 == null) {
                sb.append("null");
            } else {
                sb.append(str6);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("aliases:");
            List<String> list = this.f901a;
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
