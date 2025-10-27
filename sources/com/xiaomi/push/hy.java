package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class hy implements jq<hy, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f555a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f556a = new BitSet(2);

    /* renamed from: b, reason: collision with other field name */
    public int f557b;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f554a = new kg("Cellular");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25109a = new jy("", (byte) 8, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25110b = new jy("", (byte) 8, 2);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(hy hyVar) {
        int iA;
        int iA2;
        if (!getClass().equals(hyVar.getClass())) {
            return getClass().getName().compareTo(hyVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m497a()).compareTo(Boolean.valueOf(hyVar.m497a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m497a() && (iA2 = jr.a(this.f555a, hyVar.f555a)) != 0) {
            return iA2;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(hyVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (!b() || (iA = jr.a(this.f557b, hyVar.f557b)) == 0) {
            return 0;
        }
        return iA;
    }

    public hy a(int i2) {
        this.f555a = i2;
        a(true);
        return this;
    }

    public void a() {
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005e  */
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
            boolean r6 = r5.m497a()
            if (r6 == 0) goto L39
            boolean r6 = r5.b()
            if (r6 == 0) goto L1e
            r5.a()
            return
        L1e:
            com.xiaomi.push.kc r6 = new com.xiaomi.push.kc
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Required field 'signalStrength' was not found in serialized data! Struct: "
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
            java.lang.String r1 = "Required field 'id' was not found in serialized data! Struct: "
            r0.append(r1)
            java.lang.String r1 = r5.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r6.<init>(r0)
            throw r6
        L54:
            short r0 = r0.f928a
            r2 = 8
            r3 = 1
            if (r0 == r3) goto L6e
            r4 = 2
            if (r0 == r4) goto L62
        L5e:
            com.xiaomi.push.ke.a(r6, r1)
            goto L79
        L62:
            if (r1 != r2) goto L5e
            int r0 = r6.mo660a()
            r5.f557b = r0
            r5.b(r3)
            goto L79
        L6e:
            if (r1 != r2) goto L5e
            int r0 = r6.mo660a()
            r5.f555a = r0
            r5.a(r3)
        L79:
            r6.h()
            goto L3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.hy.a(com.xiaomi.push.kb):void");
    }

    public void a(boolean z2) {
        this.f556a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m497a() {
        return this.f556a.get(0);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m498a(hy hyVar) {
        return hyVar != null && this.f555a == hyVar.f555a && this.f557b == hyVar.f557b;
    }

    public hy b(int i2) {
        this.f557b = i2;
        b(true);
        return this;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) {
        a();
        kbVar.a(f554a);
        kbVar.a(f25109a);
        kbVar.mo671a(this.f555a);
        kbVar.b();
        kbVar.a(f25110b);
        kbVar.mo671a(this.f557b);
        kbVar.b();
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f556a.set(1, z2);
    }

    public boolean b() {
        return this.f556a.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof hy)) {
            return m498a((hy) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "Cellular(id:" + this.f555a + ", signalStrength:" + this.f557b + ")";
    }
}
