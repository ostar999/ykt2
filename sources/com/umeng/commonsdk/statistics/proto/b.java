package com.umeng.commonsdk.statistics.proto;

import com.umeng.analytics.pro.as;
import com.umeng.analytics.pro.av;
import com.umeng.analytics.pro.bb;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.bh;
import com.umeng.analytics.pro.bi;
import com.umeng.analytics.pro.bo;
import com.umeng.analytics.pro.bp;
import com.umeng.analytics.pro.bu;
import com.umeng.analytics.pro.bv;
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
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes6.dex */
public class b implements av<b, e>, Serializable, Cloneable {

    /* renamed from: d, reason: collision with root package name */
    public static final Map<e, bh> f23432d;

    /* renamed from: e, reason: collision with root package name */
    private static final long f23433e = -6496538196005191531L;

    /* renamed from: f, reason: collision with root package name */
    private static final bz f23434f = new bz("IdSnapshot");

    /* renamed from: g, reason: collision with root package name */
    private static final bp f23435g = new bp("identity", (byte) 11, 1);

    /* renamed from: h, reason: collision with root package name */
    private static final bp f23436h = new bp("ts", (byte) 10, 2);

    /* renamed from: i, reason: collision with root package name */
    private static final bp f23437i = new bp("version", (byte) 8, 3);

    /* renamed from: j, reason: collision with root package name */
    private static final Map<Class<? extends cc>, cd> f23438j;

    /* renamed from: k, reason: collision with root package name */
    private static final int f23439k = 0;

    /* renamed from: l, reason: collision with root package name */
    private static final int f23440l = 1;

    /* renamed from: a, reason: collision with root package name */
    public String f23441a;

    /* renamed from: b, reason: collision with root package name */
    public long f23442b;

    /* renamed from: c, reason: collision with root package name */
    public int f23443c;

    /* renamed from: m, reason: collision with root package name */
    private byte f23444m;

    public static class a extends ce<b> {
        private a() {
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(bu buVar, b bVar) throws bb {
            buVar.j();
            while (true) {
                bp bpVarL = buVar.l();
                byte b3 = bpVarL.f22625b;
                if (b3 == 0) {
                    break;
                }
                short s2 = bpVarL.f22626c;
                if (s2 != 1) {
                    if (s2 != 2) {
                        if (s2 != 3) {
                            bx.a(buVar, b3);
                        } else if (b3 == 8) {
                            bVar.f23443c = buVar.w();
                            bVar.c(true);
                        } else {
                            bx.a(buVar, b3);
                        }
                    } else if (b3 == 10) {
                        bVar.f23442b = buVar.x();
                        bVar.b(true);
                    } else {
                        bx.a(buVar, b3);
                    }
                } else if (b3 == 11) {
                    bVar.f23441a = buVar.z();
                    bVar.a(true);
                } else {
                    bx.a(buVar, b3);
                }
                buVar.m();
            }
            buVar.k();
            if (!bVar.g()) {
                throw new bv("Required field 'ts' was not found in serialized data! Struct: " + toString());
            }
            if (bVar.j()) {
                bVar.k();
                return;
            }
            throw new bv("Required field 'version' was not found in serialized data! Struct: " + toString());
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(bu buVar, b bVar) throws bb {
            bVar.k();
            buVar.a(b.f23434f);
            if (bVar.f23441a != null) {
                buVar.a(b.f23435g);
                buVar.a(bVar.f23441a);
                buVar.c();
            }
            buVar.a(b.f23436h);
            buVar.a(bVar.f23442b);
            buVar.c();
            buVar.a(b.f23437i);
            buVar.a(bVar.f23443c);
            buVar.c();
            buVar.d();
            buVar.b();
        }
    }

    /* renamed from: com.umeng.commonsdk.statistics.proto.b$b, reason: collision with other inner class name */
    public static class C0387b implements cd {
        private C0387b() {
        }

        @Override // com.umeng.analytics.pro.cd
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a b() {
            return new a();
        }
    }

    public static class c extends cf<b> {
        private c() {
        }

        @Override // com.umeng.analytics.pro.cc
        public void a(bu buVar, b bVar) throws bb {
            ca caVar = (ca) buVar;
            caVar.a(bVar.f23441a);
            caVar.a(bVar.f23442b);
            caVar.a(bVar.f23443c);
        }

        @Override // com.umeng.analytics.pro.cc
        public void b(bu buVar, b bVar) throws bb {
            ca caVar = (ca) buVar;
            bVar.f23441a = caVar.z();
            bVar.a(true);
            bVar.f23442b = caVar.x();
            bVar.b(true);
            bVar.f23443c = caVar.w();
            bVar.c(true);
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
        f23438j = map;
        map.put(ce.class, new C0387b());
        map.put(cf.class, new d());
        EnumMap enumMap = new EnumMap(e.class);
        enumMap.put((EnumMap) e.IDENTITY, (e) new bh("identity", (byte) 1, new bi((byte) 11)));
        enumMap.put((EnumMap) e.TS, (e) new bh("ts", (byte) 1, new bi((byte) 10)));
        enumMap.put((EnumMap) e.VERSION, (e) new bh("version", (byte) 1, new bi((byte) 8)));
        Map<e, bh> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f23432d = mapUnmodifiableMap;
        bh.a(b.class, mapUnmodifiableMap);
    }

    public b() {
        this.f23444m = (byte) 0;
    }

    @Override // com.umeng.analytics.pro.av
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public b deepCopy() {
        return new b(this);
    }

    public String b() {
        return this.f23441a;
    }

    public void c() {
        this.f23441a = null;
    }

    @Override // com.umeng.analytics.pro.av
    public void clear() {
        this.f23441a = null;
        b(false);
        this.f23442b = 0L;
        c(false);
        this.f23443c = 0;
    }

    public boolean d() {
        return this.f23441a != null;
    }

    public long e() {
        return this.f23442b;
    }

    public void f() {
        this.f23444m = as.b(this.f23444m, 0);
    }

    public boolean g() {
        return as.a(this.f23444m, 0);
    }

    public int h() {
        return this.f23443c;
    }

    public void i() {
        this.f23444m = as.b(this.f23444m, 1);
    }

    public boolean j() {
        return as.a(this.f23444m, 1);
    }

    public void k() throws bb {
        if (this.f23441a != null) {
            return;
        }
        throw new bv("Required field 'identity' was not present! Struct: " + toString());
    }

    @Override // com.umeng.analytics.pro.av
    public void read(bu buVar) throws bb {
        f23438j.get(buVar.D()).b().b(buVar, this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("IdSnapshot(");
        sb.append("identity:");
        String str = this.f23441a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("ts:");
        sb.append(this.f23442b);
        sb.append(", ");
        sb.append("version:");
        sb.append(this.f23443c);
        sb.append(")");
        return sb.toString();
    }

    @Override // com.umeng.analytics.pro.av
    public void write(bu buVar) throws bb {
        f23438j.get(buVar.D()).b().a(buVar, this);
    }

    public enum e implements bc {
        IDENTITY(1, "identity"),
        TS(2, "ts"),
        VERSION(3, "version");


        /* renamed from: d, reason: collision with root package name */
        private static final Map<String, e> f23448d = new HashMap();

        /* renamed from: e, reason: collision with root package name */
        private final short f23450e;

        /* renamed from: f, reason: collision with root package name */
        private final String f23451f;

        static {
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                f23448d.put(eVar.b(), eVar);
            }
        }

        e(short s2, String str) {
            this.f23450e = s2;
            this.f23451f = str;
        }

        public static e a(int i2) {
            if (i2 == 1) {
                return IDENTITY;
            }
            if (i2 == 2) {
                return TS;
            }
            if (i2 != 3) {
                return null;
            }
            return VERSION;
        }

        public static e b(int i2) {
            e eVarA = a(i2);
            if (eVarA != null) {
                return eVarA;
            }
            throw new IllegalArgumentException("Field " + i2 + " doesn't exist!");
        }

        @Override // com.umeng.analytics.pro.bc
        public String b() {
            return this.f23451f;
        }

        public static e a(String str) {
            return f23448d.get(str);
        }

        @Override // com.umeng.analytics.pro.bc
        public short a() {
            return this.f23450e;
        }
    }

    public b a(String str) {
        this.f23441a = str;
        return this;
    }

    public void b(boolean z2) {
        this.f23444m = as.a(this.f23444m, 0, z2);
    }

    public void c(boolean z2) {
        this.f23444m = as.a(this.f23444m, 1, z2);
    }

    public b(String str, long j2, int i2) {
        this();
        this.f23441a = str;
        this.f23442b = j2;
        b(true);
        this.f23443c = i2;
        c(true);
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f23441a = null;
    }

    @Override // com.umeng.analytics.pro.av
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public e fieldForId(int i2) {
        return e.a(i2);
    }

    public b a(long j2) {
        this.f23442b = j2;
        b(true);
        return this;
    }

    public b a(int i2) {
        this.f23443c = i2;
        c(true);
        return this;
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            write(new bo(new cg(objectOutputStream)));
        } catch (bb e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public b(b bVar) {
        this.f23444m = (byte) 0;
        this.f23444m = bVar.f23444m;
        if (bVar.d()) {
            this.f23441a = bVar.f23441a;
        }
        this.f23442b = bVar.f23442b;
        this.f23443c = bVar.f23443c;
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f23444m = (byte) 0;
            read(new bo(new cg(objectInputStream)));
        } catch (bb e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
