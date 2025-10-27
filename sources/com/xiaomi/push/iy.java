package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class iy implements jq<iy, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public List<ig> f723a;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f722a = new kg("XmPushActionCollectData");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25320a = new jy("", (byte) 15, 1);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(iy iyVar) {
        int iA;
        if (!getClass().equals(iyVar.getClass())) {
            return getClass().getName().compareTo(iyVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m580a()).compareTo(Boolean.valueOf(iyVar.m580a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!m580a() || (iA = jr.a(this.f723a, iyVar.f723a)) == 0) {
            return 0;
        }
        return iA;
    }

    public iy a(List<ig> list) {
        this.f723a = list;
        return this;
    }

    public void a() throws kc {
        if (this.f723a != null) {
            return;
        }
        throw new kc("Required field 'dataCollectionItems' was not present! Struct: " + toString());
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
            if (jyVarMo662a.f928a == 1 && b3 == 15) {
                jz jzVarMo663a = kbVar.mo663a();
                this.f723a = new ArrayList(jzVarMo663a.f929a);
                for (int i2 = 0; i2 < jzVarMo663a.f929a; i2++) {
                    ig igVar = new ig();
                    igVar.a(kbVar);
                    this.f723a.add(igVar);
                }
                kbVar.j();
            } else {
                ke.a(kbVar, b3);
            }
            kbVar.h();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m580a() {
        return this.f723a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m581a(iy iyVar) {
        if (iyVar == null) {
            return false;
        }
        boolean zM580a = m580a();
        boolean zM580a2 = iyVar.m580a();
        if (zM580a || zM580a2) {
            return zM580a && zM580a2 && this.f723a.equals(iyVar.f723a);
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        a();
        kbVar.a(f722a);
        if (this.f723a != null) {
            kbVar.a(f25320a);
            kbVar.a(new jz((byte) 12, this.f723a.size()));
            Iterator<ig> it = this.f723a.iterator();
            while (it.hasNext()) {
                it.next().b(kbVar);
            }
            kbVar.e();
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof iy)) {
            return m581a((iy) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCollectData(");
        sb.append("dataCollectionItems:");
        List<ig> list = this.f723a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }
}
