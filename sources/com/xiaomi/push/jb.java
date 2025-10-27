package com.xiaomi.push;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;

/* loaded from: classes6.dex */
public class jb implements jq<jb, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public hw f750a;

    /* renamed from: a, reason: collision with other field name */
    public iq f751a;

    /* renamed from: a, reason: collision with other field name */
    public it f752a;

    /* renamed from: a, reason: collision with other field name */
    public String f753a;

    /* renamed from: a, reason: collision with other field name */
    public ByteBuffer f754a;

    /* renamed from: b, reason: collision with other field name */
    public String f757b;

    /* renamed from: a, reason: collision with other field name */
    private static final kg f749a = new kg("XmPushActionContainer");

    /* renamed from: a, reason: collision with root package name */
    private static final jy f25346a = new jy("", (byte) 8, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final jy f25347b = new jy("", (byte) 2, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final jy f25348c = new jy("", (byte) 2, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final jy f25349d = new jy("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final jy f25350e = new jy("", (byte) 11, 5);

    /* renamed from: f, reason: collision with root package name */
    private static final jy f25351f = new jy("", (byte) 11, 6);

    /* renamed from: g, reason: collision with root package name */
    private static final jy f25352g = new jy("", (byte) 12, 7);

    /* renamed from: h, reason: collision with root package name */
    private static final jy f25353h = new jy("", (byte) 12, 8);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f755a = new BitSet(2);

    /* renamed from: a, reason: collision with other field name */
    public boolean f756a = true;

    /* renamed from: b, reason: collision with other field name */
    public boolean f758b = true;

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jb jbVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        if (!getClass().equals(jbVar.getClass())) {
            return getClass().getName().compareTo(jbVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m599a()).compareTo(Boolean.valueOf(jbVar.m599a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m599a() && (iA8 = jr.a(this.f750a, jbVar.f750a)) != 0) {
            return iA8;
        }
        int iCompareTo2 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(jbVar.c()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (c() && (iA7 = jr.a(this.f756a, jbVar.f756a)) != 0) {
            return iA7;
        }
        int iCompareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jbVar.d()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (d() && (iA6 = jr.a(this.f758b, jbVar.f758b)) != 0) {
            return iA6;
        }
        int iCompareTo4 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(jbVar.e()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (e() && (iA5 = jr.a(this.f754a, jbVar.f754a)) != 0) {
            return iA5;
        }
        int iCompareTo5 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jbVar.f()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (f() && (iA4 = jr.a(this.f753a, jbVar.f753a)) != 0) {
            return iA4;
        }
        int iCompareTo6 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(jbVar.g()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (g() && (iA3 = jr.a(this.f757b, jbVar.f757b)) != 0) {
            return iA3;
        }
        int iCompareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jbVar.h()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (h() && (iA2 = jr.a(this.f752a, jbVar.f752a)) != 0) {
            return iA2;
        }
        int iCompareTo8 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(jbVar.i()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (!i() || (iA = jr.a(this.f751a, jbVar.f751a)) == 0) {
            return 0;
        }
        return iA;
    }

    public hw a() {
        return this.f750a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public iq m595a() {
        return this.f751a;
    }

    public jb a(hw hwVar) {
        this.f750a = hwVar;
        return this;
    }

    public jb a(iq iqVar) {
        this.f751a = iqVar;
        return this;
    }

    public jb a(it itVar) {
        this.f752a = itVar;
        return this;
    }

    public jb a(String str) {
        this.f753a = str;
        return this;
    }

    public jb a(ByteBuffer byteBuffer) {
        this.f754a = byteBuffer;
        return this;
    }

    public jb a(boolean z2) {
        this.f756a = z2;
        m598a(true);
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m596a() {
        return this.f753a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m597a() throws kc {
        if (this.f750a == null) {
            throw new kc("Required field 'action' was not present! Struct: " + toString());
        }
        if (this.f754a == null) {
            throw new kc("Required field 'pushAction' was not present! Struct: " + toString());
        }
        if (this.f752a != null) {
            return;
        }
        throw new kc("Required field 'target' was not present! Struct: " + toString());
    }

    @Override // com.xiaomi.push.jq
    public void a(kb kbVar) throws kc {
        kbVar.mo666a();
        while (true) {
            jy jyVarMo662a = kbVar.mo662a();
            byte b3 = jyVarMo662a.f25502a;
            if (b3 == 0) {
                kbVar.g();
                if (!c()) {
                    throw new kc("Required field 'encryptAction' was not found in serialized data! Struct: " + toString());
                }
                if (d()) {
                    m597a();
                    return;
                }
                throw new kc("Required field 'isRequest' was not found in serialized data! Struct: " + toString());
            }
            switch (jyVarMo662a.f928a) {
                case 1:
                    if (b3 == 8) {
                        this.f750a = hw.a(kbVar.mo660a());
                    }
                    kbVar.h();
                    break;
                case 2:
                    if (b3 == 2) {
                        this.f756a = kbVar.mo672a();
                        m598a(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 3:
                    if (b3 == 2) {
                        this.f758b = kbVar.mo672a();
                        m602b(true);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 4:
                    if (b3 == 11) {
                        this.f754a = kbVar.mo668a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 5:
                    if (b3 == 11) {
                        this.f753a = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 6:
                    if (b3 == 11) {
                        this.f757b = kbVar.mo667a();
                        continue;
                    }
                    kbVar.h();
                    break;
                case 7:
                    if (b3 == 12) {
                        it itVar = new it();
                        this.f752a = itVar;
                        itVar.a(kbVar);
                        continue;
                    }
                    kbVar.h();
                    break;
                case 8:
                    if (b3 == 12) {
                        iq iqVar = new iq();
                        this.f751a = iqVar;
                        iqVar.a(kbVar);
                        continue;
                    }
                    kbVar.h();
                    break;
            }
            ke.a(kbVar, b3);
            kbVar.h();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m598a(boolean z2) {
        this.f755a.set(0, z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m599a() {
        return this.f750a != null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m600a(jb jbVar) {
        if (jbVar == null) {
            return false;
        }
        boolean zM599a = m599a();
        boolean zM599a2 = jbVar.m599a();
        if (((zM599a || zM599a2) && (!zM599a || !zM599a2 || !this.f750a.equals(jbVar.f750a))) || this.f756a != jbVar.f756a || this.f758b != jbVar.f758b) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = jbVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.f754a.equals(jbVar.f754a))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = jbVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f753a.equals(jbVar.f753a))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = jbVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f757b.equals(jbVar.f757b))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jbVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f752a.m569a(jbVar.f752a))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = jbVar.i();
        if (zI || zI2) {
            return zI && zI2 && this.f751a.m558a(jbVar.f751a);
        }
        return true;
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m601a() {
        a(jr.a(this.f754a));
        return this.f754a.array();
    }

    public jb b(String str) {
        this.f757b = str;
        return this;
    }

    public jb b(boolean z2) {
        this.f758b = z2;
        m602b(true);
        return this;
    }

    public String b() {
        return this.f757b;
    }

    @Override // com.xiaomi.push.jq
    public void b(kb kbVar) throws kc {
        m597a();
        kbVar.a(f749a);
        if (this.f750a != null) {
            kbVar.a(f25346a);
            kbVar.mo671a(this.f750a.a());
            kbVar.b();
        }
        kbVar.a(f25347b);
        kbVar.a(this.f756a);
        kbVar.b();
        kbVar.a(f25348c);
        kbVar.a(this.f758b);
        kbVar.b();
        if (this.f754a != null) {
            kbVar.a(f25349d);
            kbVar.a(this.f754a);
            kbVar.b();
        }
        if (this.f753a != null && f()) {
            kbVar.a(f25350e);
            kbVar.a(this.f753a);
            kbVar.b();
        }
        if (this.f757b != null && g()) {
            kbVar.a(f25351f);
            kbVar.a(this.f757b);
            kbVar.b();
        }
        if (this.f752a != null) {
            kbVar.a(f25352g);
            this.f752a.b(kbVar);
            kbVar.b();
        }
        if (this.f751a != null && i()) {
            kbVar.a(f25353h);
            this.f751a.b(kbVar);
            kbVar.b();
        }
        kbVar.c();
        kbVar.mo670a();
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m602b(boolean z2) {
        this.f755a.set(1, z2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m603b() {
        return this.f756a;
    }

    public boolean c() {
        return this.f755a.get(0);
    }

    public boolean d() {
        return this.f755a.get(1);
    }

    public boolean e() {
        return this.f754a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jb)) {
            return m600a((jb) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f753a != null;
    }

    public boolean g() {
        return this.f757b != null;
    }

    public boolean h() {
        return this.f752a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f751a != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionContainer(");
        sb.append("action:");
        hw hwVar = this.f750a;
        if (hwVar == null) {
            sb.append("null");
        } else {
            sb.append(hwVar);
        }
        sb.append(", ");
        sb.append("encryptAction:");
        sb.append(this.f756a);
        sb.append(", ");
        sb.append("isRequest:");
        sb.append(this.f758b);
        sb.append(", ");
        sb.append("pushAction:");
        ByteBuffer byteBuffer = this.f754a;
        if (byteBuffer == null) {
            sb.append("null");
        } else {
            jr.a(byteBuffer, sb);
        }
        if (f()) {
            sb.append(", ");
            sb.append("appid:");
            String str = this.f753a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            String str2 = this.f757b;
            if (str2 == null) {
                sb.append("null");
            } else {
                sb.append(str2);
            }
        }
        sb.append(", ");
        sb.append("target:");
        it itVar = this.f752a;
        if (itVar == null) {
            sb.append("null");
        } else {
            sb.append(itVar);
        }
        if (i()) {
            sb.append(", ");
            sb.append("metaInfo:");
            iq iqVar = this.f751a;
            if (iqVar == null) {
                sb.append("null");
            } else {
                sb.append(iqVar);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
