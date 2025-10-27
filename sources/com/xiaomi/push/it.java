package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class it implements jq<it, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public String f673a;

    /* renamed from: d, reason: collision with other field name */
    public String f678d;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f671a = new kg("Target");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25279a = new jy("", (byte) 10, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25280b = new jy("", (byte) 11, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25281c = new jy("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25282d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25283e = new jy("", (byte) 2, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25284f = new jy("", (byte) 11, 7);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f674a = new BitSet(2);

    /* renamed from: a, reason: collision with other field name */
    public long f672a = 5;

    /* renamed from: b, reason: collision with other field name */
    public String f676b = "xiaomi.com";

    /* renamed from: c, reason: collision with other field name */
    public String f677c = "";

    /* renamed from: a, reason: collision with other field name */
    public boolean f675a = false;

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(it itVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        if (!getClass().equals(itVar.getClass())) {
            return getClass().getName().compareTo(itVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m568a()).compareTo(Boolean.valueOf(itVar.m568a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m568a() && (iA6 = jr.a(this.f672a, itVar.f672a)) != 0) {
            return iA6;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(itVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA5 = jr.a(this.f673a, itVar.f673a)) != 0) {
            return iA5;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(itVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA4 = jr.a(this.f676b, itVar.f676b)) != 0) {
            return iA4;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(itVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA3 = jr.a(this.f677c, itVar.f677c)) != 0) {
            return iA3;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(itVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA2 = jr.a(this.f675a, itVar.f675a)) != 0) {
            return iA2;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(itVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (!f() || (iA = jr.a(this.f678d, itVar.f678d)) == 0) {
            return 0;
        }
        return iA;
    }

    public void a() throws kc {
        if (this.f673a != null) {
            return;
        }
        throw new kc("Required field 'userId' was not present! Struct: " + toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0049  */
    @Override // com.xiaomi.push.jq
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(com.xiaomi.push.kb r7) {
        /*
            r6 = this;
            r7.mo666a()
        L3:
            com.xiaomi.push.jy r0 = r7.mo662a()
            byte r1 = r0.f25502a
            if (r1 != 0) goto L33
            r7.g()
            boolean r7 = r6.m568a()
            if (r7 == 0) goto L18
            r6.a()
            return
        L18:
            com.xiaomi.push.kc r7 = new com.xiaomi.push.kc
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Required field 'channelId' was not found in serialized data! Struct: "
            r0.append(r1)
            java.lang.String r1 = r6.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.<init>(r0)
            throw r7
        L33:
            short r0 = r0.f928a
            r2 = 1
            if (r0 == r2) goto L7d
            r3 = 2
            r4 = 11
            if (r0 == r3) goto L74
            r5 = 3
            if (r0 == r5) goto L6b
            r5 = 4
            if (r0 == r5) goto L62
            r5 = 5
            if (r0 == r5) goto L56
            r2 = 7
            if (r0 == r2) goto L4d
        L49:
            com.xiaomi.push.ke.a(r7, r1)
            goto L8a
        L4d:
            if (r1 != r4) goto L49
            java.lang.String r0 = r7.mo667a()
            r6.f678d = r0
            goto L8a
        L56:
            if (r1 != r3) goto L49
            boolean r0 = r7.mo672a()
            r6.f675a = r0
            r6.b(r2)
            goto L8a
        L62:
            if (r1 != r4) goto L49
            java.lang.String r0 = r7.mo667a()
            r6.f677c = r0
            goto L8a
        L6b:
            if (r1 != r4) goto L49
            java.lang.String r0 = r7.mo667a()
            r6.f676b = r0
            goto L8a
        L74:
            if (r1 != r4) goto L49
            java.lang.String r0 = r7.mo667a()
            r6.f673a = r0
            goto L8a
        L7d:
            r0 = 10
            if (r1 != r0) goto L49
            long r0 = r7.mo661a()
            r6.f672a = r0
            r6.a(r2)
        L8a:
            r7.h()
            goto L3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.it.a(com.xiaomi.push.kb):void");
    }

    public void a(boolean z2) {
        this.f674a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m568a() {
        return this.f674a.get(0);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m569a(it itVar) {
        if (itVar == null || this.f672a != itVar.f672a) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = itVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.f673a.equals(itVar.f673a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = itVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.f676b.equals(itVar.f676b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = itVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.f677c.equals(itVar.f677c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = itVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f675a == itVar.f675a)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = itVar.f();
        if (zF || zF2) {
            return zF && zF2 && this.f678d.equals(itVar.f678d);
        }
        return true;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) {
        a();
        kbVar.a(f671a);
        kbVar.a(f25279a);
        kbVar.a(this.f672a);
        kbVar.b();
        if (this.f673a != null) {
            kbVar.a(f25280b);
            kbVar.a(this.f673a);
            kbVar.b();
        }
        if (this.f676b != null && c()) {
            kbVar.a(f25281c);
            kbVar.a(this.f676b);
            kbVar.b();
        }
        if (this.f677c != null && d()) {
            kbVar.a(f25282d);
            kbVar.a(this.f677c);
            kbVar.b();
        }
        if (e()) {
            kbVar.a(f25283e);
            kbVar.a(this.f675a);
            kbVar.b();
        }
        if (this.f678d != null && f()) {
            kbVar.a(f25284f);
            kbVar.a(this.f678d);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public void b(boolean z2) {
        this.f674a.set(1, z2);
    }

    public boolean b() {
        return this.f673a != null;
    }

    public boolean c() {
        return this.f676b != null;
    }

    public boolean d() {
        return this.f677c != null;
    }

    public boolean e() {
        return this.f674a.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof it)) {
            return m569a((it) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f678d != null;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Target(");
        sb.append("channelId:");
        sb.append(this.f672a);
        sb.append(", ");
        sb.append("userId:");
        String str = this.f673a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        if (c()) {
            sb.append(", ");
            sb.append("server:");
            String str2 = this.f676b;
            if (str2 == null) {
                sb.append("null");
            } else {
                sb.append(str2);
            }
        }
        if (d()) {
            sb.append(", ");
            sb.append("resource:");
            String str3 = this.f677c;
            if (str3 == null) {
                sb.append("null");
            } else {
                sb.append(str3);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("isPreview:");
            sb.append(this.f675a);
        }
        if (f()) {
            sb.append(", ");
            sb.append("token:");
            String str4 = this.f678d;
            if (str4 == null) {
                sb.append("null");
            } else {
                sb.append(str4);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
