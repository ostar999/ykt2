package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class jc implements jq<jc, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public List<io> f760a;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f759a = new kg("XmPushActionCustomConfig");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25354a = new jy("", (byte) 15, 1);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jc jcVar) {
        int iA;
        if (!getClass().equals(jcVar.getClass())) {
            return getClass().getName().compareTo(jcVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m605a()).compareTo(Boolean.valueOf(jcVar.m605a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!m605a() || (iA = jr.a(this.f760a, jcVar.f760a)) == 0) {
            return 0;
        }
        return iA;
    }

    public List<io> a() {
        return this.f760a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m604a() throws kc {
        if (this.f760a != null) {
            return;
        }
        throw new kc("Required field 'customConfigs' was not present! Struct: " + toString());
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) throws kc {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                m604a();
                return;
            }
            if (jyVarMo662a.f928a == 1 && b3 == 15) {
                jz jzVarMo663a = kbVar.mo663a();
                this.f760a = new ArrayList(jzVarMo663a.f929a);
                for (int i2 = 0; i2 < jzVarMo663a.f929a; i2++) {
                    io ioVar = new io();
                    ioVar.a(kbVar);
                    this.f760a.add(ioVar);
                }
                kbVar.j();
            } else {
                ke.a(kbVar, b3);
            }
            kbVar.h();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m605a() {
        return this.f760a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m606a(jc jcVar) {
        if (jcVar == null) {
            return false;
        }
        boolean zM605a = m605a();
        boolean zM605a2 = jcVar.m605a();
        if (zM605a || zM605a2) {
            return zM605a && zM605a2 && this.f760a.equals(jcVar.f760a);
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m604a();
        kbVar.a(f759a);
        if (this.f760a != null) {
            kbVar.a(f25354a);
            kbVar.a(new jz((byte) 12, this.f760a.size()));
            Iterator<io> it = this.f760a.iterator();
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
        if (obj != null && (obj instanceof jc)) {
            return m606a((jc) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCustomConfig(");
        sb.append("customConfigs:");
        List<io> list = this.f760a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }
}
