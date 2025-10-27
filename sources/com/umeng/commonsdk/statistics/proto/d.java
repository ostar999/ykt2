package com.umeng.commonsdk.statistics.proto;

import com.umeng.analytics.pro.as;
import com.umeng.analytics.pro.av;
import com.umeng.analytics.pro.bb;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.bh;
import com.umeng.analytics.pro.bi;
import com.umeng.analytics.pro.bk;
import com.umeng.analytics.pro.bm;
import com.umeng.analytics.pro.bo;
import com.umeng.analytics.pro.bp;
import com.umeng.analytics.pro.br;
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
public class d implements av<d, e>, Serializable, Cloneable {

    /* renamed from: d, reason: collision with root package name */
    public static final Map<e, bh> f23470d;

    /* renamed from: e, reason: collision with root package name */
    private static final long f23471e = 2846460275012375038L;

    /* renamed from: f, reason: collision with root package name */
    private static final bz f23472f = new bz("Imprint");

    /* renamed from: g, reason: collision with root package name */
    private static final bp f23473g = new bp("property", (byte) 13, 1);

    /* renamed from: h, reason: collision with root package name */
    private static final bp f23474h = new bp("version", (byte) 8, 2);

    /* renamed from: i, reason: collision with root package name */
    private static final bp f23475i = new bp("checksum", (byte) 11, 3);

    /* renamed from: j, reason: collision with root package name */
    private static final Map<Class<? extends cc>, cd> f23476j;

    /* renamed from: k, reason: collision with root package name */
    private static final int f23477k = 0;

    /* renamed from: a, reason: collision with root package name */
    public Map<String, com.umeng.commonsdk.statistics.proto.e> f23478a;

    /* renamed from: b, reason: collision with root package name */
    public int f23479b;

    /* renamed from: c, reason: collision with root package name */
    public String f23480c;

    /* renamed from: l, reason: collision with root package name */
    private byte f23481l;

    public static class a extends ce<d> {
        private a() {
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(bu buVar, d dVar) throws bb {
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
                        } else if (b3 == 11) {
                            dVar.f23480c = buVar.z();
                            dVar.c(true);
                        } else {
                            bx.a(buVar, b3);
                        }
                    } else if (b3 == 8) {
                        dVar.f23479b = buVar.w();
                        dVar.b(true);
                    } else {
                        bx.a(buVar, b3);
                    }
                } else if (b3 == 13) {
                    br brVarN = buVar.n();
                    dVar.f23478a = new HashMap(brVarN.f22631c * 2);
                    for (int i2 = 0; i2 < brVarN.f22631c; i2++) {
                        String strZ = buVar.z();
                        com.umeng.commonsdk.statistics.proto.e eVar = new com.umeng.commonsdk.statistics.proto.e();
                        eVar.read(buVar);
                        dVar.f23478a.put(strZ, eVar);
                    }
                    buVar.o();
                    dVar.a(true);
                } else {
                    bx.a(buVar, b3);
                }
                buVar.m();
            }
            buVar.k();
            if (dVar.h()) {
                dVar.l();
                return;
            }
            throw new bv("Required field 'version' was not found in serialized data! Struct: " + toString());
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(bu buVar, d dVar) throws bb {
            dVar.l();
            buVar.a(d.f23472f);
            if (dVar.f23478a != null) {
                buVar.a(d.f23473g);
                buVar.a(new br((byte) 11, (byte) 12, dVar.f23478a.size()));
                for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.e> entry : dVar.f23478a.entrySet()) {
                    buVar.a(entry.getKey());
                    entry.getValue().write(buVar);
                }
                buVar.e();
                buVar.c();
            }
            buVar.a(d.f23474h);
            buVar.a(dVar.f23479b);
            buVar.c();
            if (dVar.f23480c != null) {
                buVar.a(d.f23475i);
                buVar.a(dVar.f23480c);
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

    public static class c extends cf<d> {
        private c() {
        }

        @Override // com.umeng.analytics.pro.cc
        public void a(bu buVar, d dVar) throws bb {
            ca caVar = (ca) buVar;
            caVar.a(dVar.f23478a.size());
            for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.e> entry : dVar.f23478a.entrySet()) {
                caVar.a(entry.getKey());
                entry.getValue().write(caVar);
            }
            caVar.a(dVar.f23479b);
            caVar.a(dVar.f23480c);
        }

        @Override // com.umeng.analytics.pro.cc
        public void b(bu buVar, d dVar) throws bb {
            ca caVar = (ca) buVar;
            br brVar = new br((byte) 11, (byte) 12, caVar.w());
            dVar.f23478a = new HashMap(brVar.f22631c * 2);
            for (int i2 = 0; i2 < brVar.f22631c; i2++) {
                String strZ = caVar.z();
                com.umeng.commonsdk.statistics.proto.e eVar = new com.umeng.commonsdk.statistics.proto.e();
                eVar.read(caVar);
                dVar.f23478a.put(strZ, eVar);
            }
            dVar.a(true);
            dVar.f23479b = caVar.w();
            dVar.b(true);
            dVar.f23480c = caVar.z();
            dVar.c(true);
        }
    }

    /* renamed from: com.umeng.commonsdk.statistics.proto.d$d, reason: collision with other inner class name */
    public static class C0389d implements cd {
        private C0389d() {
        }

        @Override // com.umeng.analytics.pro.cd
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c b() {
            return new c();
        }
    }

    static {
        HashMap map = new HashMap();
        f23476j = map;
        map.put(ce.class, new b());
        map.put(cf.class, new C0389d());
        EnumMap enumMap = new EnumMap(e.class);
        enumMap.put((EnumMap) e.PROPERTY, (e) new bh("property", (byte) 1, new bk((byte) 13, new bi((byte) 11), new bm((byte) 12, com.umeng.commonsdk.statistics.proto.e.class))));
        enumMap.put((EnumMap) e.VERSION, (e) new bh("version", (byte) 1, new bi((byte) 8)));
        enumMap.put((EnumMap) e.CHECKSUM, (e) new bh("checksum", (byte) 1, new bi((byte) 11)));
        Map<e, bh> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f23470d = mapUnmodifiableMap;
        bh.a(d.class, mapUnmodifiableMap);
    }

    public d() {
        this.f23481l = (byte) 0;
    }

    @Override // com.umeng.analytics.pro.av
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public d deepCopy() {
        return new d(this);
    }

    public int b() {
        Map<String, com.umeng.commonsdk.statistics.proto.e> map = this.f23478a;
        if (map == null) {
            return 0;
        }
        return map.size();
    }

    public Map<String, com.umeng.commonsdk.statistics.proto.e> c() {
        return this.f23478a;
    }

    @Override // com.umeng.analytics.pro.av
    public void clear() {
        this.f23478a = null;
        b(false);
        this.f23479b = 0;
        this.f23480c = null;
    }

    public void d() {
        this.f23478a = null;
    }

    public boolean e() {
        return this.f23478a != null;
    }

    public int f() {
        return this.f23479b;
    }

    public void g() {
        this.f23481l = as.b(this.f23481l, 0);
    }

    public boolean h() {
        return as.a(this.f23481l, 0);
    }

    public String i() {
        return this.f23480c;
    }

    public void j() {
        this.f23480c = null;
    }

    public boolean k() {
        return this.f23480c != null;
    }

    public void l() throws bb {
        if (this.f23478a == null) {
            throw new bv("Required field 'property' was not present! Struct: " + toString());
        }
        if (this.f23480c != null) {
            return;
        }
        throw new bv("Required field 'checksum' was not present! Struct: " + toString());
    }

    @Override // com.umeng.analytics.pro.av
    public void read(bu buVar) throws bb {
        f23476j.get(buVar.D()).b().b(buVar, this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Imprint(");
        sb.append("property:");
        Map<String, com.umeng.commonsdk.statistics.proto.e> map = this.f23478a;
        if (map == null) {
            sb.append("null");
        } else {
            sb.append(map);
        }
        sb.append(", ");
        sb.append("version:");
        sb.append(this.f23479b);
        sb.append(", ");
        sb.append("checksum:");
        String str = this.f23480c;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override // com.umeng.analytics.pro.av
    public void write(bu buVar) throws bb {
        f23476j.get(buVar.D()).b().a(buVar, this);
    }

    public enum e implements bc {
        PROPERTY(1, "property"),
        VERSION(2, "version"),
        CHECKSUM(3, "checksum");


        /* renamed from: d, reason: collision with root package name */
        private static final Map<String, e> f23485d = new HashMap();

        /* renamed from: e, reason: collision with root package name */
        private final short f23487e;

        /* renamed from: f, reason: collision with root package name */
        private final String f23488f;

        static {
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                f23485d.put(eVar.b(), eVar);
            }
        }

        e(short s2, String str) {
            this.f23487e = s2;
            this.f23488f = str;
        }

        public static e a(int i2) {
            if (i2 == 1) {
                return PROPERTY;
            }
            if (i2 == 2) {
                return VERSION;
            }
            if (i2 != 3) {
                return null;
            }
            return CHECKSUM;
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
            return this.f23488f;
        }

        public static e a(String str) {
            return f23485d.get(str);
        }

        @Override // com.umeng.analytics.pro.bc
        public short a() {
            return this.f23487e;
        }
    }

    public void a(String str, com.umeng.commonsdk.statistics.proto.e eVar) {
        if (this.f23478a == null) {
            this.f23478a = new HashMap();
        }
        this.f23478a.put(str, eVar);
    }

    public void b(boolean z2) {
        this.f23481l = as.a(this.f23481l, 0, z2);
    }

    public void c(boolean z2) {
        if (z2) {
            return;
        }
        this.f23480c = null;
    }

    public d(Map<String, com.umeng.commonsdk.statistics.proto.e> map, int i2, String str) {
        this();
        this.f23478a = map;
        this.f23479b = i2;
        b(true);
        this.f23480c = str;
    }

    @Override // com.umeng.analytics.pro.av
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public e fieldForId(int i2) {
        return e.a(i2);
    }

    public d a(Map<String, com.umeng.commonsdk.statistics.proto.e> map) {
        this.f23478a = map;
        return this;
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f23478a = null;
    }

    public d a(int i2) {
        this.f23479b = i2;
        b(true);
        return this;
    }

    public d(d dVar) {
        this.f23481l = (byte) 0;
        this.f23481l = dVar.f23481l;
        if (dVar.e()) {
            HashMap map = new HashMap();
            for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.e> entry : dVar.f23478a.entrySet()) {
                map.put(entry.getKey(), new com.umeng.commonsdk.statistics.proto.e(entry.getValue()));
            }
            this.f23478a = map;
        }
        this.f23479b = dVar.f23479b;
        if (dVar.k()) {
            this.f23480c = dVar.f23480c;
        }
    }

    public d a(String str) {
        this.f23480c = str;
        return this;
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            write(new bo(new cg(objectOutputStream)));
        } catch (bb e2) {
            throw new IOException(e2.getMessage());
        }
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f23481l = (byte) 0;
            read(new bo(new cg(objectInputStream)));
        } catch (bb e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
