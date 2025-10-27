package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class iu implements jq<iu, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f680a;

    /* renamed from: a, reason: collision with other field name */
    public String f681a;

    /* renamed from: a, reason: collision with other field name */
    private BitSet f682a = new BitSet(1);

    /* renamed from: b, reason: collision with other field name */
    public String f683b;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f679a = new kg("Wifi");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25285a = new jy("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25286b = new jy("", (byte) 8, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25287c = new jy("", (byte) 11, 3);

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(iu iuVar) {
        int iA;
        int iA2;
        int iA3;
        if (!getClass().equals(iuVar.getClass())) {
            return getClass().getName().compareTo(iuVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m570a()).compareTo(Boolean.valueOf(iuVar.m570a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m570a() && (iA3 = jr.a(this.f681a, iuVar.f681a)) != 0) {
            return iA3;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(iuVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA2 = jr.a(this.f680a, iuVar.f680a)) != 0) {
            return iA2;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(iuVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (!c() || (iA = jr.a(this.f683b, iuVar.f683b)) == 0) {
            return 0;
        }
        return iA;
    }

    public iu a(int i2) {
        this.f680a = i2;
        a(true);
        return this;
    }

    public iu a(String str) {
        this.f681a = str;
        return this;
    }

    public void a() throws kc {
        if (this.f681a != null) {
            return;
        }
        throw new kc("Required field 'macAddress' was not present! Struct: " + toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0040  */
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
            if (r1 != 0) goto L33
            r6.g()
            boolean r6 = r5.b()
            if (r6 == 0) goto L18
            r5.a()
            return
        L18:
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
        L33:
            short r0 = r0.f928a
            r2 = 11
            r3 = 1
            if (r0 == r3) goto L5b
            r4 = 2
            if (r0 == r4) goto L4d
            r3 = 3
            if (r0 == r3) goto L44
        L40:
            com.xiaomi.push.ke.a(r6, r1)
            goto L63
        L44:
            if (r1 != r2) goto L40
            java.lang.String r0 = r6.mo667a()
            r5.f683b = r0
            goto L63
        L4d:
            r0 = 8
            if (r1 != r0) goto L40
            int r0 = r6.mo660a()
            r5.f680a = r0
            r5.a(r3)
            goto L63
        L5b:
            if (r1 != r2) goto L40
            java.lang.String r0 = r6.mo667a()
            r5.f681a = r0
        L63:
            r6.h()
            goto L3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.iu.a(com.xiaomi.push.kb):void");
    }

    public void a(boolean z2) {
        this.f682a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m570a() {
        return this.f681a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m571a(iu iuVar) {
        if (iuVar == null) {
            return false;
        }
        boolean zM570a = m570a();
        boolean zM570a2 = iuVar.m570a();
        if (((zM570a || zM570a2) && !(zM570a && zM570a2 && this.f681a.equals(iuVar.f681a))) || this.f680a != iuVar.f680a) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = iuVar.c();
        if (zC || zC2) {
            return zC && zC2 && this.f683b.equals(iuVar.f683b);
        }
        return true;
    }

    public iu b(String str) {
        this.f683b = str;
        return this;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        a();
        kbVar.a(f679a);
        if (this.f681a != null) {
            kbVar.a(f25285a);
            kbVar.a(this.f681a);
            kbVar.b();
        }
        kbVar.a(f25286b);
        kbVar.mo671a(this.f680a);
        kbVar.b();
        if (this.f683b != null && c()) {
            kbVar.a(f25287c);
            kbVar.a(this.f683b);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    public boolean b() {
        return this.f682a.get(0);
    }

    public boolean c() {
        return this.f683b != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof iu)) {
            return m571a((iu) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Wifi(");
        sb.append("macAddress:");
        String str = this.f681a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("signalStrength:");
        sb.append(this.f680a);
        if (c()) {
            sb.append(", ");
            sb.append("ssid:");
            String str2 = this.f683b;
            if (str2 == null) {
                sb.append("null");
            } else {
                sb.append(str2);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
