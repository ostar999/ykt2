package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ia implements jq<ia, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public List<ib> f561a;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f560a = new kg("ClientUploadData");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25135a = new jy("", (byte) 15, 1);

    public int a() {
        List<ib> list = this.f561a;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ia iaVar) {
        int iA;
        if (!getClass().equals(iaVar.getClass())) {
            return getClass().getName().compareTo(iaVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m500a()).compareTo(Boolean.valueOf(iaVar.m500a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!m500a() || (iA = jr.a(this.f561a, iaVar.f561a)) == 0) {
            return 0;
        }
        return iA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m499a() throws kc {
        if (this.f561a != null) {
            return;
        }
        throw new kc("Required field 'uploadDataItems' was not present! Struct: " + toString());
    }

    public void a(ib ibVar) {
        if (this.f561a == null) {
            this.f561a = new ArrayList();
        }
        this.f561a.add(ibVar);
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) throws kc {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                m499a();
                return;
            }
            if (jyVarMo662a.f928a == 1 && b3 == 15) {
                jz jzVarMo663a = kbVar.mo663a();
                this.f561a = new ArrayList(jzVarMo663a.f929a);
                for (int i2 = 0; i2 < jzVarMo663a.f929a; i2++) {
                    ib ibVar = new ib();
                    ibVar.a(kbVar);
                    this.f561a.add(ibVar);
                }
                kbVar.j();
            } else {
                ke.a(kbVar, b3);
            }
            kbVar.h();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m500a() {
        return this.f561a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m501a(ia iaVar) {
        if (iaVar == null) {
            return false;
        }
        boolean zM500a = m500a();
        boolean zM500a2 = iaVar.m500a();
        if (zM500a || zM500a2) {
            return zM500a && zM500a2 && this.f561a.equals(iaVar.f561a);
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m499a();
        kbVar.a(f560a);
        if (this.f561a != null) {
            kbVar.a(f25135a);
            kbVar.a(new jz((byte) 12, this.f561a.size()));
            Iterator<ib> it = this.f561a.iterator();
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
        if (obj != null && (obj instanceof ia)) {
            return m501a((ia) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ClientUploadData(");
        sb.append("uploadDataItems:");
        List<ib> list = this.f561a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }
}
