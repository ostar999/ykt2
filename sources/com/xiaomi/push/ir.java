package com.xiaomi.push;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes6.dex */
public class ir implements jq<ir, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public Set<ii> f668a;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f667a = new kg("RegisteredGeoFencing");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25275a = new jy("", (byte) 14, 1);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ir irVar) {
        int iA;
        if (!getClass().equals(irVar.getClass())) {
            return getClass().getName().compareTo(irVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m566a()).compareTo(Boolean.valueOf(irVar.m566a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!m566a() || (iA = jr.a(this.f668a, irVar.f668a)) == 0) {
            return 0;
        }
        return iA;
    }

    public ir a(Set<ii> set) {
        this.f668a = set;
        return this;
    }

    public Set<ii> a() {
        return this.f668a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m565a() throws kc {
        if (this.f668a != null) {
            return;
        }
        throw new kc("Required field 'geoFencings' was not present! Struct: " + toString());
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) throws kc {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                m565a();
                return;
            }
            if (jyVarMo662a.f928a == 1 && b3 == 14) {
                kf kfVarMo665a = kbVar.mo665a();
                this.f668a = new HashSet(kfVarMo665a.f933a * 2);
                for (int i2 = 0; i2 < kfVarMo665a.f933a; i2++) {
                    ii iiVar = new ii();
                    iiVar.a(kbVar);
                    this.f668a.add(iiVar);
                }
                kbVar.k();
            } else {
                ke.a(kbVar, b3);
            }
            kbVar.h();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m566a() {
        return this.f668a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m567a(ir irVar) {
        if (irVar == null) {
            return false;
        }
        boolean zM566a = m566a();
        boolean zM566a2 = irVar.m566a();
        if (zM566a || zM566a2) {
            return zM566a && zM566a2 && this.f668a.equals(irVar.f668a);
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m565a();
        kbVar.a(f667a);
        if (this.f668a != null) {
            kbVar.a(f25275a);
            kbVar.a(new kf((byte) 12, this.f668a.size()));
            Iterator<ii> it = this.f668a.iterator();
            while (it.hasNext()) {
                it.next().b(kbVar);
            }
            kbVar.f();
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ir)) {
            return m567a((ir) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RegisteredGeoFencing(");
        sb.append("geoFencings:");
        Set<ii> set = this.f668a;
        if (set == null) {
            sb.append("null");
        } else {
            sb.append(set);
        }
        sb.append(")");
        return sb.toString();
    }
}
