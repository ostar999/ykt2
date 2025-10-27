package com.xiaomi.push;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ft implements jq<ft, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public String f445a;

    /* renamed from: a, reason: collision with other field name */
    public List<fs> f446a;

    /* renamed from: b, reason: collision with other field name */
    public String f447b;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f444a = new kg("StatsEvents");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f24903a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f24904b = new jy("", (byte) 11, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f24905c = new jy("", (byte) 15, 3);

    public ft() {
    }

    public ft(String str, List<fs> list) {
        this();
        this.f445a = str;
        this.f446a = list;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ft ftVar) {
        int iA;
        int iA2;
        int iA3;
        if (!getClass().equals(ftVar.getClass())) {
            return getClass().getName().compareTo(ftVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m426a()).compareTo(Boolean.valueOf(ftVar.m426a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m426a() && (iA3 = jr.a(this.f445a, ftVar.f445a)) != 0) {
            return iA3;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ftVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA2 = jr.a(this.f447b, ftVar.f447b)) != 0) {
            return iA2;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ftVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (!c() || (iA = jr.a(this.f446a, ftVar.f446a)) == 0) {
            return 0;
        }
        return iA;
    }

    public ft a(String str) {
        this.f447b = str;
        return this;
    }

    public void a() throws kc {
        if (this.f445a == null) {
            throw new kc("Required field 'uuid' was not present! Struct: " + toString());
        }
        if (this.f446a != null) {
            return;
        }
        throw new kc("Required field 'events' was not present! Struct: " + toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001f  */
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
            r2 = 11
            r3 = 1
            if (r0 == r3) goto L56
            r3 = 2
            if (r0 == r3) goto L4d
            r2 = 3
            if (r0 == r2) goto L23
        L1f:
            com.xiaomi.push.ke.a(r5, r1)
            goto L5e
        L23:
            r0 = 15
            if (r1 != r0) goto L1f
            com.xiaomi.push.jz r0 = r5.mo663a()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.f929a
            r1.<init>(r2)
            r4.f446a = r1
            r1 = 0
        L35:
            int r2 = r0.f929a
            if (r1 >= r2) goto L49
            com.xiaomi.push.fs r2 = new com.xiaomi.push.fs
            r2.<init>()
            r2.a(r5)
            java.util.List<com.xiaomi.push.fs> r3 = r4.f446a
            r3.add(r2)
            int r1 = r1 + 1
            goto L35
        L49:
            r5.j()
            goto L5e
        L4d:
            if (r1 != r2) goto L1f
            java.lang.String r0 = r5.mo667a()
            r4.f447b = r0
            goto L5e
        L56:
            if (r1 != r2) goto L1f
            java.lang.String r0 = r5.mo667a()
            r4.f445a = r0
        L5e:
            r5.h()
            goto L3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.ft.a(com.xiaomi.push.kb):void");
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m426a() {
        return this.f445a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m427a(ft ftVar) {
        if (ftVar == null) {
            return false;
        }
        boolean zM426a = m426a();
        boolean zM426a2 = ftVar.m426a();
        if ((zM426a || zM426a2) && !(zM426a && zM426a2 && this.f445a.equals(ftVar.f445a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = ftVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f447b.equals(ftVar.f447b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = ftVar.c();
        if (zC || zC2) {
            return zC && zC2 && this.f446a.equals(ftVar.f446a);
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) {
        a();
        kbVar.a(f444a);
        if (this.f445a != null) {
            kbVar.a(f24903a);
            kbVar.a(this.f445a);
            kbVar.b();
        }
        if (this.f447b != null && b()) {
            kbVar.a(f24904b);
            kbVar.a(this.f447b);
            kbVar.b();
        }
        if (this.f446a != null) {
            kbVar.a(f24905c);
            kbVar.a(new jz((byte) 12, this.f446a.size()));
            Iterator<fs> it = this.f446a.iterator();
            while (it.hasNext()) {
                it.next().b(kbVar);
            }
            kbVar.e();
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public boolean b() {
        return this.f447b != null;
    }

    public boolean c() {
        return this.f446a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ft)) {
            return m427a((ft) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StatsEvents(");
        sb.append("uuid:");
        String str = this.f445a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        if (b()) {
            sb.append(", ");
            sb.append("operator:");
            String str2 = this.f447b;
            if (str2 == null) {
                sb.append("null");
            } else {
                sb.append(str2);
            }
        }
        sb.append(", ");
        sb.append("events:");
        List<fs> list = this.f446a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }
}
