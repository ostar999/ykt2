package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class jd implements jq<jd, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public List<im> f762a;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f761a = new kg("XmPushActionNormalConfig");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25355a = new jy("", (byte) 15, 1);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jd jdVar) {
        int iA;
        if (!getClass().equals(jdVar.getClass())) {
            return getClass().getName().compareTo(jdVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m608a()).compareTo(Boolean.valueOf(jdVar.m608a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!m608a() || (iA = jr.a(this.f762a, jdVar.f762a)) == 0) {
            return 0;
        }
        return iA;
    }

    public List<im> a() {
        return this.f762a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m607a() throws kc {
        if (this.f762a != null) {
            return;
        }
        throw new kc("Required field 'normalConfigs' was not present! Struct: " + toString());
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) throws kc {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                m607a();
                return;
            }
            if (jyVarMo662a.f928a == 1 && b3 == 15) {
                jz jzVarMo663a = kbVar.mo663a();
                this.f762a = new ArrayList(jzVarMo663a.f929a);
                for (int i2 = 0; i2 < jzVarMo663a.f929a; i2++) {
                    im imVar = new im();
                    imVar.a(kbVar);
                    this.f762a.add(imVar);
                }
                kbVar.j();
            } else {
                ke.a(kbVar, b3);
            }
            kbVar.h();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m608a() {
        return this.f762a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m609a(jd jdVar) {
        if (jdVar == null) {
            return false;
        }
        boolean zM608a = m608a();
        boolean zM608a2 = jdVar.m608a();
        if (zM608a || zM608a2) {
            return zM608a && zM608a2 && this.f762a.equals(jdVar.f762a);
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m607a();
        kbVar.a(f761a);
        if (this.f762a != null) {
            kbVar.a(f25355a);
            kbVar.a(new jz((byte) 12, this.f762a.size()));
            Iterator<im> it = this.f762a.iterator();
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
        if (obj != null && (obj instanceof jd)) {
            return m609a((jd) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionNormalConfig(");
        sb.append("normalConfigs:");
        List<im> list = this.f762a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }
}
