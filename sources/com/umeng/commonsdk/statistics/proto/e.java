package com.umeng.commonsdk.statistics.proto;

import com.tencent.tbs.one.TBSOneConfigurationKeys;
import com.umeng.analytics.pro.as;
import com.umeng.analytics.pro.av;
import com.umeng.analytics.pro.bb;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.bh;
import com.umeng.analytics.pro.bi;
import com.umeng.analytics.pro.bo;
import com.umeng.analytics.pro.bp;
import com.umeng.analytics.pro.bu;
import com.umeng.analytics.pro.bx;
import com.umeng.analytics.pro.bz;
import com.umeng.analytics.pro.ca;
import com.umeng.analytics.pro.cc;
import com.umeng.analytics.pro.cd;
import com.umeng.analytics.pro.ce;
import com.umeng.analytics.pro.cf;
import com.umeng.analytics.pro.cg;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes6.dex */
public class e implements av<e, EnumC0390e>, Serializable, Cloneable {

    /* renamed from: d, reason: collision with root package name */
    public static final Map<EnumC0390e, bh> f23489d;

    /* renamed from: e, reason: collision with root package name */
    private static final long f23490e = 7501688097813630241L;

    /* renamed from: f, reason: collision with root package name */
    private static final bz f23491f = new bz("ImprintValue");

    /* renamed from: g, reason: collision with root package name */
    private static final bp f23492g = new bp("value", (byte) 11, 1);

    /* renamed from: h, reason: collision with root package name */
    private static final bp f23493h = new bp("ts", (byte) 10, 2);

    /* renamed from: i, reason: collision with root package name */
    private static final bp f23494i = new bp(TBSOneConfigurationKeys.GUID, (byte) 11, 3);

    /* renamed from: j, reason: collision with root package name */
    private static final Map<Class<? extends cc>, cd> f23495j;

    /* renamed from: k, reason: collision with root package name */
    private static final int f23496k = 0;

    /* renamed from: a, reason: collision with root package name */
    public String f23497a;

    /* renamed from: b, reason: collision with root package name */
    public long f23498b;

    /* renamed from: c, reason: collision with root package name */
    public String f23499c;

    /* renamed from: l, reason: collision with root package name */
    private byte f23500l;

    /* renamed from: m, reason: collision with root package name */
    private EnumC0390e[] f23501m;

    public static class a extends ce<e> {
        private a() {
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(bu buVar, e eVar) throws bb {
            buVar.j();
            while (true) {
                bp bpVarL = buVar.l();
                byte b3 = bpVarL.f22625b;
                if (b3 == 0) {
                    buVar.k();
                    eVar.k();
                    return;
                }
                short s2 = bpVarL.f22626c;
                if (s2 != 1) {
                    if (s2 != 2) {
                        if (s2 != 3) {
                            bx.a(buVar, b3);
                        } else if (b3 == 11) {
                            eVar.f23499c = buVar.z();
                            eVar.c(true);
                        } else {
                            bx.a(buVar, b3);
                        }
                    } else if (b3 == 10) {
                        eVar.f23498b = buVar.x();
                        eVar.b(true);
                    } else {
                        bx.a(buVar, b3);
                    }
                } else if (b3 == 11) {
                    eVar.f23497a = buVar.z();
                    eVar.a(true);
                } else {
                    bx.a(buVar, b3);
                }
                buVar.m();
            }
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(bu buVar, e eVar) throws bb {
            eVar.k();
            buVar.a(e.f23491f);
            if (eVar.f23497a != null && eVar.d()) {
                buVar.a(e.f23492g);
                buVar.a(eVar.f23497a);
                buVar.c();
            }
            if (eVar.g()) {
                buVar.a(e.f23493h);
                buVar.a(eVar.f23498b);
                buVar.c();
            }
            if (eVar.f23499c != null && eVar.j()) {
                buVar.a(e.f23494i);
                buVar.a(eVar.f23499c);
                buVar.c();
            }
            buVar.d();
            buVar.b();
        }
    }

    public static class b implements cd {
        private b() {
        }

        @Override // com.umeng.analytics.pro.cd
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a b() {
            return new a();
        }
    }

    public static class c extends cf<e> {
        private c() {
        }

        @Override // com.umeng.analytics.pro.cc
        public void a(bu buVar, e eVar) throws bb {
            ca caVar = (ca) buVar;
            BitSet bitSet = new BitSet();
            if (eVar.d()) {
                bitSet.set(0);
            }
            if (eVar.g()) {
                bitSet.set(1);
            }
            if (eVar.j()) {
                bitSet.set(2);
            }
            caVar.a(bitSet, 3);
            if (eVar.d()) {
                caVar.a(eVar.f23497a);
            }
            if (eVar.g()) {
                caVar.a(eVar.f23498b);
            }
            if (eVar.j()) {
                caVar.a(eVar.f23499c);
            }
        }

        @Override // com.umeng.analytics.pro.cc
        public void b(bu buVar, e eVar) throws bb {
            ca caVar = (ca) buVar;
            BitSet bitSetB = caVar.b(3);
            if (bitSetB.get(0)) {
                eVar.f23497a = caVar.z();
                eVar.a(true);
            }
            if (bitSetB.get(1)) {
                eVar.f23498b = caVar.x();
                eVar.b(true);
            }
            if (bitSetB.get(2)) {
                eVar.f23499c = caVar.z();
                eVar.c(true);
            }
        }
    }

    public static class d implements cd {
        private d() {
        }

        @Override // com.umeng.analytics.pro.cd
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c b() {
            return new c();
        }
    }

    static {
        HashMap map = new HashMap();
        f23495j = map;
        map.put(ce.class, new b());
        map.put(cf.class, new d());
        EnumMap enumMap = new EnumMap(EnumC0390e.class);
        enumMap.put((EnumMap) EnumC0390e.VALUE, (EnumC0390e) new bh("value", (byte) 2, new bi((byte) 11)));
        enumMap.put((EnumMap) EnumC0390e.TS, (EnumC0390e) new bh("ts", (byte) 2, new bi((byte) 10)));
        enumMap.put((EnumMap) EnumC0390e.GUID, (EnumC0390e) new bh(TBSOneConfigurationKeys.GUID, (byte) 2, new bi((byte) 11)));
        Map<EnumC0390e, bh> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f23489d = mapUnmodifiableMap;
        bh.a(e.class, mapUnmodifiableMap);
    }

    public e() {
        this.f23500l = (byte) 0;
        this.f23501m = new EnumC0390e[]{EnumC0390e.VALUE, EnumC0390e.TS, EnumC0390e.GUID};
    }

    @Override // com.umeng.analytics.pro.av
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public e deepCopy() {
        return new e(this);
    }

    public String b() {
        return this.f23497a;
    }

    public void c() {
        this.f23497a = null;
    }

    @Override // com.umeng.analytics.pro.av
    public void clear() {
        this.f23497a = null;
        b(false);
        this.f23498b = 0L;
        this.f23499c = null;
    }

    public boolean d() {
        return this.f23497a != null;
    }

    public long e() {
        return this.f23498b;
    }

    public void f() {
        this.f23500l = as.b(this.f23500l, 0);
    }

    public boolean g() {
        return as.a(this.f23500l, 0);
    }

    public String h() {
        return this.f23499c;
    }

    public void i() {
        this.f23499c = null;
    }

    public boolean j() {
        return this.f23499c != null;
    }

    public void k() throws bb {
    }

    @Override // com.umeng.analytics.pro.av
    public void read(bu buVar) throws bb {
        f23495j.get(buVar.D()).b().b(buVar, this);
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("ImprintValue(");
        if (d()) {
            sb.append("value:");
            String str = this.f23497a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z2) {
            sb.append(", ");
        }
        sb.append("ts:");
        sb.append(this.f23498b);
        sb.append(", ");
        sb.append("guid:");
        String str2 = this.f23499c;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override // com.umeng.analytics.pro.av
    public void write(bu buVar) throws bb {
        f23495j.get(buVar.D()).b().a(buVar, this);
    }

    /* renamed from: com.umeng.commonsdk.statistics.proto.e$e, reason: collision with other inner class name */
    public enum EnumC0390e implements bc {
        VALUE(1, "value"),
        TS(2, "ts"),
        GUID(3, TBSOneConfigurationKeys.GUID);


        /* renamed from: d, reason: collision with root package name */
        private static final Map<String, EnumC0390e> f23505d = new HashMap();

        /* renamed from: e, reason: collision with root package name */
        private final short f23507e;

        /* renamed from: f, reason: collision with root package name */
        private final String f23508f;

        static {
            Iterator it = EnumSet.allOf(EnumC0390e.class).iterator();
            while (it.hasNext()) {
                EnumC0390e enumC0390e = (EnumC0390e) it.next();
                f23505d.put(enumC0390e.b(), enumC0390e);
            }
        }

        EnumC0390e(short s2, String str) {
            this.f23507e = s2;
            this.f23508f = str;
        }

        public static EnumC0390e a(int i2) {
            if (i2 == 1) {
                return VALUE;
            }
            if (i2 == 2) {
                return TS;
            }
            if (i2 != 3) {
                return null;
            }
            return GUID;
        }

        public static EnumC0390e b(int i2) {
            EnumC0390e enumC0390eA = a(i2);
            if (enumC0390eA != null) {
                return enumC0390eA;
            }
            throw new IllegalArgumentException("Field " + i2 + " doesn't exist!");
        }

        @Override // com.umeng.analytics.pro.bc
        public String b() {
            return this.f23508f;
        }

        public static EnumC0390e a(String str) {
            return f23505d.get(str);
        }

        @Override // com.umeng.analytics.pro.bc
        public short a() {
            return this.f23507e;
        }
    }

    public e a(String str) {
        this.f23497a = str;
        return this;
    }

    public void b(boolean z2) {
        this.f23500l = as.a(this.f23500l, 0, z2);
    }

    public void c(boolean z2) {
        if (z2) {
            return;
        }
        this.f23499c = null;
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f23497a = null;
    }

    public e b(String str) {
        this.f23499c = str;
        return this;
    }

    public e(long j2, String str) {
        this();
        this.f23498b = j2;
        b(true);
        this.f23499c = str;
    }

    public e a(long j2) {
        this.f23498b = j2;
        b(true);
        return this;
    }

    @Override // com.umeng.analytics.pro.av
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public EnumC0390e fieldForId(int i2) {
        return EnumC0390e.a(i2);
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            write(new bo(new cg(objectOutputStream)));
        } catch (bb e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public e(e eVar) {
        this.f23500l = (byte) 0;
        this.f23501m = new EnumC0390e[]{EnumC0390e.VALUE, EnumC0390e.TS, EnumC0390e.GUID};
        this.f23500l = eVar.f23500l;
        if (eVar.d()) {
            this.f23497a = eVar.f23497a;
        }
        this.f23498b = eVar.f23498b;
        if (eVar.j()) {
            this.f23499c = eVar.f23499c;
        }
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f23500l = (byte) 0;
            read(new bo(new cg(objectInputStream)));
        } catch (bb e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
