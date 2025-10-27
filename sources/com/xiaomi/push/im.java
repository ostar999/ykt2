package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class im implements jq<im, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f617a;

    /* renamed from: a, reason: collision with other field name */
    public id f618a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f619a = new BitSet(1);

    /* renamed from: a, reason: collision with other field name */
    public List<io> f620a;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f616a = new kg("NormalConfig");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25208a = new jy("", (byte) 8, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25209b = new jy("", (byte) 15, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25210c = new jy("", (byte) 8, 3);

    public int a() {
        return this.f617a;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(im imVar) {
        int iA;
        int iA2;
        int iA3;
        if (!getClass().equals(imVar.getClass())) {
            return getClass().getName().compareTo(imVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m537a()).compareTo(Boolean.valueOf(imVar.m537a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m537a() && (iA3 = jr.a(this.f617a, imVar.f617a)) != 0) {
            return iA3;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(imVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA2 = jr.a(this.f620a, imVar.f620a)) != 0) {
            return iA2;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(imVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (!c() || (iA = jr.a(this.f618a, imVar.f618a)) == 0) {
            return 0;
        }
        return iA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public id m535a() {
        return this.f618a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m536a() throws kc {
        if (this.f620a != null) {
            return;
        }
        throw new kc("Required field 'configItems' was not present! Struct: " + toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0040  */
    @Override // com.xiaomi.push.jq
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(com.xiaomi.push.kb r5) throws com.xiaomi.push.kc {
        /*
            r4 = this;
            r5.mo666a()
        L3:
            com.xiaomi.push.jy r0 = r5.mo662a()
            byte r1 = r0.f25502a
            if (r1 != 0) goto L33
            r5.g()
            boolean r5 = r4.m537a()
            if (r5 == 0) goto L18
            r4.m536a()
            return
        L18:
            com.xiaomi.push.kc r5 = new com.xiaomi.push.kc
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Required field 'version' was not found in serialized data! Struct: "
            r0.append(r1)
            java.lang.String r1 = r4.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L33:
            short r0 = r0.f928a
            r2 = 8
            r3 = 1
            if (r0 == r3) goto L7b
            r3 = 2
            if (r0 == r3) goto L51
            r3 = 3
            if (r0 == r3) goto L44
        L40:
            com.xiaomi.push.ke.a(r5, r1)
            goto L86
        L44:
            if (r1 != r2) goto L40
            int r0 = r5.mo660a()
            com.xiaomi.push.id r0 = com.xiaomi.push.id.a(r0)
            r4.f618a = r0
            goto L86
        L51:
            r0 = 15
            if (r1 != r0) goto L40
            com.xiaomi.push.jz r0 = r5.mo663a()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.f929a
            r1.<init>(r2)
            r4.f620a = r1
            r1 = 0
        L63:
            int r2 = r0.f929a
            if (r1 >= r2) goto L77
            com.xiaomi.push.io r2 = new com.xiaomi.push.io
            r2.<init>()
            r2.a(r5)
            java.util.List<com.xiaomi.push.io> r3 = r4.f620a
            r3.add(r2)
            int r1 = r1 + 1
            goto L63
        L77:
            r5.j()
            goto L86
        L7b:
            if (r1 != r2) goto L40
            int r0 = r5.mo660a()
            r4.f617a = r0
            r4.a(r3)
        L86:
            r5.h()
            goto L3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.im.a(com.xiaomi.push.kb):void");
    }

    public void a(boolean z2) {
        this.f619a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m537a() {
        return this.f619a.get(0);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m538a(im imVar) {
        if (imVar == null || this.f617a != imVar.f617a) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = imVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f620a.equals(imVar.f620a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = imVar.c();
        if (zC || zC2) {
            return zC && zC2 && this.f618a.equals(imVar.f618a);
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m536a();
        kbVar.a(f616a);
        kbVar.a(f25208a);
        kbVar.mo671a(this.f617a);
        kbVar.b();
        if (this.f620a != null) {
            kbVar.a(f25209b);
            kbVar.a(new jz((byte) 12, this.f620a.size()));
            Iterator<io> it = this.f620a.iterator();
            while (it.hasNext()) {
                it.next().b(kbVar);
            }
            kbVar.e();
            kbVar.b();
        }
        if (this.f618a != null && c()) {
            kbVar.a(f25210c);
            kbVar.mo671a(this.f618a.a());
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public boolean b() {
        return this.f620a != null;
    }

    public boolean c() {
        return this.f618a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof im)) {
            return m538a((im) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NormalConfig(");
        sb.append("version:");
        sb.append(this.f617a);
        sb.append(", ");
        sb.append("configItems:");
        List<io> list = this.f620a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        if (c()) {
            sb.append(", ");
            sb.append("type:");
            id idVar = this.f618a;
            if (idVar == null) {
                sb.append("null");
            } else {
                sb.append(idVar);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
