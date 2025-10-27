package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class ik implements jq<ik, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public double f609a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f610a = new BitSet(2);

    /* renamed from: b, reason: collision with other field name */
    public double f611b;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f608a = new kg("Location");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25203a = new jy("", (byte) 4, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25204b = new jy("", (byte) 4, 2);

    public double a() {
        return this.f609a;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ik ikVar) {
        int iA;
        int iA2;
        if (!getClass().equals(ikVar.getClass())) {
            return getClass().getName().compareTo(ikVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m529a()).compareTo(Boolean.valueOf(ikVar.m529a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m529a() && (iA2 = jr.a(this.f609a, ikVar.f609a)) != 0) {
            return iA2;
        }
        int iCompareTo2 = Boolean.valueOf(m531b()).compareTo(Boolean.valueOf(ikVar.m531b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (!m531b() || (iA = jr.a(this.f611b, ikVar.f611b)) == 0) {
            return 0;
        }
        return iA;
    }

    public ik a(double d3) {
        this.f609a = d3;
        a(true);
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m528a() {
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005d  */
    @Override // com.xiaomi.push.jq
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(com.xiaomi.push.kb r6) throws com.xiaomi.push.kc {
        /*
            r5 = this;
            r6.mo666a()
        L3:
            com.xiaomi.push.jy r0 = r6.mo662a()
            byte r1 = r0.f25502a
            if (r1 != 0) goto L54
            r6.g()
            boolean r6 = r5.m529a()
            if (r6 == 0) goto L39
            boolean r6 = r5.m531b()
            if (r6 == 0) goto L1e
            r5.m528a()
            return
        L1e:
            com.xiaomi.push.kc r6 = new com.xiaomi.push.kc
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Required field 'latitude' was not found in serialized data! Struct: "
            r0.append(r1)
            java.lang.String r1 = r5.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r6.<init>(r0)
            throw r6
        L39:
            com.xiaomi.push.kc r6 = new com.xiaomi.push.kc
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Required field 'longitude' was not found in serialized data! Struct: "
            r0.append(r1)
            java.lang.String r1 = r5.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r6.<init>(r0)
            throw r6
        L54:
            short r0 = r0.f928a
            r2 = 4
            r3 = 1
            if (r0 == r3) goto L6d
            r4 = 2
            if (r0 == r4) goto L61
        L5d:
            com.xiaomi.push.ke.a(r6, r1)
            goto L78
        L61:
            if (r1 != r2) goto L5d
            double r0 = r6.mo659a()
            r5.f611b = r0
            r5.b(r3)
            goto L78
        L6d:
            if (r1 != r2) goto L5d
            double r0 = r6.mo659a()
            r5.f609a = r0
            r5.a(r3)
        L78:
            r6.h()
            goto L3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.ik.a(com.xiaomi.push.kb):void");
    }

    public void a(boolean z2) {
        this.f610a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m529a() {
        return this.f610a.get(0);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m530a(ik ikVar) {
        return ikVar != null && this.f609a == ikVar.f609a && this.f611b == ikVar.f611b;
    }

    public double b() {
        return this.f611b;
    }

    public ik b(double d3) {
        this.f611b = d3;
        b(true);
        return this;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) {
        m528a();
        kbVar.a(f608a);
        kbVar.a(f25203a);
        kbVar.a(this.f609a);
        kbVar.b();
        kbVar.a(f25204b);
        kbVar.a(this.f611b);
        kbVar.b();
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f610a.set(1, z2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m531b() {
        return this.f610a.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ik)) {
            return m530a((ik) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "Location(longitude:" + this.f609a + ", latitude:" + this.f611b + ")";
    }
}
