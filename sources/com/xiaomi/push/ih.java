package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class ih implements jq<ih, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public double f589a;

    /* renamed from: a, reason: collision with other field name */
    public long f590a;

    /* renamed from: a, reason: collision with other field name */
    public ik f591a;

    /* renamed from: a, reason: collision with other field name */
    public String f592a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f593a = new BitSet(2);

    /* renamed from: a, reason: collision with other field name */
    private static final kg f588a = new kg("GPS");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25187a = new jy("", (byte) 12, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25188b = new jy("", (byte) 11, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25189c = new jy("", (byte) 10, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25190d = new jy("", (byte) 4, 4);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ih ihVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        if (!getClass().equals(ihVar.getClass())) {
            return getClass().getName().compareTo(ihVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m514a()).compareTo(Boolean.valueOf(ihVar.m514a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m514a() && (iA4 = jr.a(this.f591a, ihVar.f591a)) != 0) {
            return iA4;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ihVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA3 = jr.a(this.f592a, ihVar.f592a)) != 0) {
            return iA3;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ihVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA2 = jr.a(this.f590a, ihVar.f590a)) != 0) {
            return iA2;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ihVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (!d() || (iA = jr.a(this.f589a, ihVar.f589a)) == 0) {
            return 0;
        }
        return iA;
    }

    public ih a(double d3) {
        this.f589a = d3;
        b(true);
        return this;
    }

    public ih a(long j2) {
        this.f590a = j2;
        a(true);
        return this;
    }

    public ih a(ik ikVar) {
        this.f591a = ikVar;
        return this;
    }

    public ih a(String str) {
        this.f592a = str;
        return this;
    }

    public void a() throws kc {
        if (this.f591a != null) {
            return;
        }
        throw new kc("Required field 'location' was not present! Struct: " + toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0020  */
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
            if (r1 != 0) goto L12
            r5.g()
            r4.a()
            return
        L12:
            short r0 = r0.f928a
            r2 = 1
            if (r0 == r2) goto L49
            r3 = 2
            if (r0 == r3) goto L3e
            r3 = 3
            if (r0 == r3) goto L30
            r3 = 4
            if (r0 == r3) goto L24
        L20:
            com.xiaomi.push.ke.a(r5, r1)
            goto L57
        L24:
            if (r1 != r3) goto L20
            double r0 = r5.mo659a()
            r4.f589a = r0
            r4.b(r2)
            goto L57
        L30:
            r0 = 10
            if (r1 != r0) goto L20
            long r0 = r5.mo661a()
            r4.f590a = r0
            r4.a(r2)
            goto L57
        L3e:
            r0 = 11
            if (r1 != r0) goto L20
            java.lang.String r0 = r5.mo667a()
            r4.f592a = r0
            goto L57
        L49:
            r0 = 12
            if (r1 != r0) goto L20
            com.xiaomi.push.ik r0 = new com.xiaomi.push.ik
            r0.<init>()
            r4.f591a = r0
            r0.a(r5)
        L57:
            r5.h()
            goto L3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.ih.a(com.xiaomi.push.kb):void");
    }

    public void a(boolean z2) {
        this.f593a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m514a() {
        return this.f591a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m515a(ih ihVar) {
        if (ihVar == null) {
            return false;
        }
        boolean zM514a = m514a();
        boolean zM514a2 = ihVar.m514a();
        if ((zM514a || zM514a2) && !(zM514a && zM514a2 && this.f591a.m530a(ihVar.f591a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = ihVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f592a.equals(ihVar.f592a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = ihVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f590a == ihVar.f590a)) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = ihVar.d();
        if (zD || zD2) {
            return zD && zD2 && this.f589a == ihVar.f589a;
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        a();
        kbVar.a(f588a);
        if (this.f591a != null) {
            kbVar.a(f25187a);
            this.f591a.b(kbVar);
            kbVar.b();
        }
        if (this.f592a != null && b()) {
            kbVar.a(f25188b);
            kbVar.a(this.f592a);
            kbVar.b();
        }
        if (c()) {
            kbVar.a(f25189c);
            kbVar.a(this.f590a);
            kbVar.b();
        }
        if (d()) {
            kbVar.a(f25190d);
            kbVar.a(this.f589a);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f593a.set(1, z2);
    }

    public boolean b() {
        return this.f592a != null;
    }

    public boolean c() {
        return this.f593a.get(0);
    }

    public boolean d() {
        return this.f593a.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ih)) {
            return m515a((ih) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GPS(");
        sb.append("location:");
        ik ikVar = this.f591a;
        if (ikVar == null) {
            sb.append("null");
        } else {
            sb.append(ikVar);
        }
        if (b()) {
            sb.append(", ");
            sb.append("provider:");
            String str = this.f592a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
        }
        if (c()) {
            sb.append(", ");
            sb.append("period:");
            sb.append(this.f590a);
        }
        if (d()) {
            sb.append(", ");
            sb.append("accuracy:");
            sb.append(this.f589a);
        }
        sb.append(")");
        return sb.toString();
    }
}
