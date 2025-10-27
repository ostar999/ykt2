package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class il implements jq<il, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public ih f613a;

    /* renamed from: a, reason: collision with other field name */
    public List<iu> f614a;

    /* renamed from: b, reason: collision with other field name */
    public List<hy> f615b;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f612a = new kg("LocationInfo");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25205a = new jy("", (byte) 15, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25206b = new jy("", (byte) 15, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25207c = new jy("", (byte) 12, 3);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(il ilVar) {
        int iA;
        int iA2;
        int iA3;
        if (!getClass().equals(ilVar.getClass())) {
            return getClass().getName().compareTo(ilVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m533a()).compareTo(Boolean.valueOf(ilVar.m533a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m533a() && (iA3 = jr.a(this.f614a, ilVar.f614a)) != 0) {
            return iA3;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ilVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA2 = jr.a(this.f615b, ilVar.f615b)) != 0) {
            return iA2;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ilVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (!c() || (iA = jr.a(this.f613a, ilVar.f613a)) == 0) {
            return 0;
        }
        return iA;
    }

    public ih a() {
        return this.f613a;
    }

    public il a(ih ihVar) {
        this.f613a = ihVar;
        return this;
    }

    public il a(List<iu> list) {
        this.f614a = list;
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m532a() {
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) throws kc {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                m532a();
                return;
            }
            short s2 = jyVarMo662a.f928a;
            int i2 = 0;
            if (s2 != 1) {
                if (s2 != 2) {
                    if (s2 == 3 && b3 == 12) {
                        ih ihVar = new ih();
                        this.f613a = ihVar;
                        ihVar.a(kbVar);
                    }
                } else if (b3 == 15) {
                    jz jzVarMo663a = kbVar.mo663a();
                    this.f615b = new ArrayList(jzVarMo663a.f929a);
                    while (i2 < jzVarMo663a.f929a) {
                        hy hyVar = new hy();
                        hyVar.a(kbVar);
                        this.f615b.add(hyVar);
                        i2++;
                    }
                    kbVar.j();
                }
                ke.a(kbVar, b3);
            } else {
                if (b3 == 15) {
                    jz jzVarMo663a2 = kbVar.mo663a();
                    this.f614a = new ArrayList(jzVarMo663a2.f929a);
                    while (i2 < jzVarMo663a2.f929a) {
                        iu iuVar = new iu();
                        iuVar.a(kbVar);
                        this.f614a.add(iuVar);
                        i2++;
                    }
                    kbVar.j();
                }
                ke.a(kbVar, b3);
            }
            kbVar.h();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m533a() {
        return this.f614a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m534a(il ilVar) {
        if (ilVar == null) {
            return false;
        }
        boolean zM533a = m533a();
        boolean zM533a2 = ilVar.m533a();
        if ((zM533a || zM533a2) && !(zM533a && zM533a2 && this.f614a.equals(ilVar.f614a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = ilVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f615b.equals(ilVar.f615b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = ilVar.c();
        if (zC || zC2) {
            return zC && zC2 && this.f613a.m515a(ilVar.f613a);
        }
        return true;
    }

    public il b(List<hy> list) {
        this.f615b = list;
        return this;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m532a();
        kbVar.a(f612a);
        if (this.f614a != null && m533a()) {
            kbVar.a(f25205a);
            kbVar.a(new jz((byte) 12, this.f614a.size()));
            Iterator<iu> it = this.f614a.iterator();
            while (it.hasNext()) {
                it.next().b(kbVar);
            }
            kbVar.e();
            kbVar.b();
        }
        if (this.f615b != null && b()) {
            kbVar.a(f25206b);
            kbVar.a(new jz((byte) 12, this.f615b.size()));
            Iterator<hy> it2 = this.f615b.iterator();
            while (it2.hasNext()) {
                it2.next().b(kbVar);
            }
            kbVar.e();
            kbVar.b();
        }
        if (this.f613a != null && c()) {
            kbVar.a(f25207c);
            this.f613a.b(kbVar);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public boolean b() {
        return this.f615b != null;
    }

    public boolean c() {
        return this.f613a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof il)) {
            return m534a((il) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("LocationInfo(");
        boolean z3 = false;
        if (m533a()) {
            sb.append("wifiList:");
            List<iu> list = this.f614a;
            if (list == null) {
                sb.append("null");
            } else {
                sb.append(list);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("cellList:");
            List<hy> list2 = this.f615b;
            if (list2 == null) {
                sb.append("null");
            } else {
                sb.append(list2);
            }
        } else {
            z3 = z2;
        }
        if (c()) {
            if (!z3) {
                sb.append(", ");
            }
            sb.append("gps:");
            ih ihVar = this.f613a;
            if (ihVar == null) {
                sb.append("null");
            } else {
                sb.append(ihVar);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
