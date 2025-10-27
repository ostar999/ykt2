package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class ig implements jq<ig, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public long f584a;

    /* renamed from: a, reason: collision with other field name */
    public hz f585a;

    /* renamed from: a, reason: collision with other field name */
    public String f586a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f587a = new BitSet(1);

    /* renamed from: a, reason: collision with other field name */
    private static final kg f583a = new kg("DataCollectionItem");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25184a = new jy("", (byte) 10, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25185b = new jy("", (byte) 8, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25186c = new jy("", (byte) 11, 3);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ig igVar) {
        int iA;
        int iA2;
        int iA3;
        if (!getClass().equals(igVar.getClass())) {
            return getClass().getName().compareTo(igVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m512a()).compareTo(Boolean.valueOf(igVar.m512a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m512a() && (iA3 = jr.a(this.f584a, igVar.f584a)) != 0) {
            return iA3;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(igVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA2 = jr.a(this.f585a, igVar.f585a)) != 0) {
            return iA2;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(igVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (!c() || (iA = jr.a(this.f586a, igVar.f586a)) == 0) {
            return 0;
        }
        return iA;
    }

    public ig a(long j2) {
        this.f584a = j2;
        a(true);
        return this;
    }

    public ig a(hz hzVar) {
        this.f585a = hzVar;
        return this;
    }

    public ig a(String str) {
        this.f586a = str;
        return this;
    }

    public String a() {
        return this.f586a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m511a() throws kc {
        if (this.f585a == null) {
            throw new kc("Required field 'collectionType' was not present! Struct: " + toString());
        }
        if (this.f586a != null) {
            return;
        }
        throw new kc("Required field 'content' was not present! Struct: " + toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003e  */
    @Override // com.xiaomi.push.jq
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(com.xiaomi.push.kb r4) throws com.xiaomi.push.kc {
        /*
            r3 = this;
            r4.mo666a()
        L3:
            com.xiaomi.push.jy r0 = r4.mo662a()
            byte r1 = r0.f25502a
            if (r1 != 0) goto L33
            r4.g()
            boolean r4 = r3.m512a()
            if (r4 == 0) goto L18
            r3.m511a()
            return
        L18:
            com.xiaomi.push.kc r4 = new com.xiaomi.push.kc
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Required field 'collectedAt' was not found in serialized data! Struct: "
            r0.append(r1)
            java.lang.String r1 = r3.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r4.<init>(r0)
            throw r4
        L33:
            short r0 = r0.f928a
            r2 = 1
            if (r0 == r2) goto L5c
            r2 = 2
            if (r0 == r2) goto L4d
            r2 = 3
            if (r0 == r2) goto L42
        L3e:
            com.xiaomi.push.ke.a(r4, r1)
            goto L69
        L42:
            r0 = 11
            if (r1 != r0) goto L3e
            java.lang.String r0 = r4.mo667a()
            r3.f586a = r0
            goto L69
        L4d:
            r0 = 8
            if (r1 != r0) goto L3e
            int r0 = r4.mo660a()
            com.xiaomi.push.hz r0 = com.xiaomi.push.hz.a(r0)
            r3.f585a = r0
            goto L69
        L5c:
            r0 = 10
            if (r1 != r0) goto L3e
            long r0 = r4.mo661a()
            r3.f584a = r0
            r3.a(r2)
        L69:
            r4.h()
            goto L3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.ig.a(com.xiaomi.push.kb):void");
    }

    public void a(boolean z2) {
        this.f587a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m512a() {
        return this.f587a.get(0);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m513a(ig igVar) {
        if (igVar == null || this.f584a != igVar.f584a) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = igVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f585a.equals(igVar.f585a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = igVar.c();
        if (zC || zC2) {
            return zC && zC2 && this.f586a.equals(igVar.f586a);
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m511a();
        kbVar.a(f583a);
        kbVar.a(f25184a);
        kbVar.a(this.f584a);
        kbVar.b();
        if (this.f585a != null) {
            kbVar.a(f25185b);
            kbVar.mo671a(this.f585a.a());
            kbVar.b();
        }
        if (this.f586a != null) {
            kbVar.a(f25186c);
            kbVar.a(this.f586a);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public boolean b() {
        return this.f585a != null;
    }

    public boolean c() {
        return this.f586a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ig)) {
            return m513a((ig) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DataCollectionItem(");
        sb.append("collectedAt:");
        sb.append(this.f584a);
        sb.append(", ");
        sb.append("collectionType:");
        hz hzVar = this.f585a;
        if (hzVar == null) {
            sb.append("null");
        } else {
            sb.append(hzVar);
        }
        sb.append(", ");
        sb.append("content:");
        String str = this.f586a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        sb.append(")");
        return sb.toString();
    }
}
