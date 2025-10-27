package com.umeng.commonsdk.statistics.proto;

import com.umeng.analytics.pro.av;
import com.umeng.analytics.pro.bb;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.bh;
import com.umeng.analytics.pro.bi;
import com.umeng.analytics.pro.bj;
import com.umeng.analytics.pro.bk;
import com.umeng.analytics.pro.bm;
import com.umeng.analytics.pro.bo;
import com.umeng.analytics.pro.bp;
import com.umeng.analytics.pro.bq;
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
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class c implements av<c, e>, Serializable, Cloneable {

    /* renamed from: d, reason: collision with root package name */
    public static final Map<e, bh> f23452d;

    /* renamed from: e, reason: collision with root package name */
    private static final long f23453e = -5764118265293965743L;

    /* renamed from: f, reason: collision with root package name */
    private static final bz f23454f = new bz("IdTracking");

    /* renamed from: g, reason: collision with root package name */
    private static final bp f23455g = new bp("snapshots", (byte) 13, 1);

    /* renamed from: h, reason: collision with root package name */
    private static final bp f23456h = new bp("journals", (byte) 15, 2);

    /* renamed from: i, reason: collision with root package name */
    private static final bp f23457i = new bp("checksum", (byte) 11, 3);

    /* renamed from: j, reason: collision with root package name */
    private static final Map<Class<? extends cc>, cd> f23458j;

    /* renamed from: a, reason: collision with root package name */
    public Map<String, com.umeng.commonsdk.statistics.proto.b> f23459a;

    /* renamed from: b, reason: collision with root package name */
    public List<com.umeng.commonsdk.statistics.proto.a> f23460b;

    /* renamed from: c, reason: collision with root package name */
    public String f23461c;

    /* renamed from: k, reason: collision with root package name */
    private e[] f23462k;

    public static class a extends ce<c> {
        private a() {
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(bu buVar, c cVar) throws bb {
            buVar.j();
            while (true) {
                bp bpVarL = buVar.l();
                byte b3 = bpVarL.f22625b;
                if (b3 == 0) {
                    buVar.k();
                    cVar.n();
                    return;
                }
                short s2 = bpVarL.f22626c;
                int i2 = 0;
                if (s2 != 1) {
                    if (s2 != 2) {
                        if (s2 != 3) {
                            bx.a(buVar, b3);
                        } else if (b3 == 11) {
                            cVar.f23461c = buVar.z();
                            cVar.c(true);
                        } else {
                            bx.a(buVar, b3);
                        }
                    } else if (b3 == 15) {
                        bq bqVarP = buVar.p();
                        cVar.f23460b = new ArrayList(bqVarP.f22628b);
                        while (i2 < bqVarP.f22628b) {
                            com.umeng.commonsdk.statistics.proto.a aVar = new com.umeng.commonsdk.statistics.proto.a();
                            aVar.read(buVar);
                            cVar.f23460b.add(aVar);
                            i2++;
                        }
                        buVar.q();
                        cVar.b(true);
                    } else {
                        bx.a(buVar, b3);
                    }
                } else if (b3 == 13) {
                    br brVarN = buVar.n();
                    cVar.f23459a = new HashMap(brVarN.f22631c * 2);
                    while (i2 < brVarN.f22631c) {
                        String strZ = buVar.z();
                        com.umeng.commonsdk.statistics.proto.b bVar = new com.umeng.commonsdk.statistics.proto.b();
                        bVar.read(buVar);
                        cVar.f23459a.put(strZ, bVar);
                        i2++;
                    }
                    buVar.o();
                    cVar.a(true);
                } else {
                    bx.a(buVar, b3);
                }
                buVar.m();
            }
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(bu buVar, c cVar) throws bb {
            cVar.n();
            buVar.a(c.f23454f);
            if (cVar.f23459a != null) {
                buVar.a(c.f23455g);
                buVar.a(new br((byte) 11, (byte) 12, cVar.f23459a.size()));
                for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.b> entry : cVar.f23459a.entrySet()) {
                    buVar.a(entry.getKey());
                    entry.getValue().write(buVar);
                }
                buVar.e();
                buVar.c();
            }
            if (cVar.f23460b != null && cVar.j()) {
                buVar.a(c.f23456h);
                buVar.a(new bq((byte) 12, cVar.f23460b.size()));
                Iterator<com.umeng.commonsdk.statistics.proto.a> it = cVar.f23460b.iterator();
                while (it.hasNext()) {
                    it.next().write(buVar);
                }
                buVar.f();
                buVar.c();
            }
            if (cVar.f23461c != null && cVar.m()) {
                buVar.a(c.f23457i);
                buVar.a(cVar.f23461c);
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

    /* renamed from: com.umeng.commonsdk.statistics.proto.c$c, reason: collision with other inner class name */
    public static class C0388c extends cf<c> {
        private C0388c() {
        }

        @Override // com.umeng.analytics.pro.cc
        public void a(bu buVar, c cVar) throws bb {
            ca caVar = (ca) buVar;
            caVar.a(cVar.f23459a.size());
            for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.b> entry : cVar.f23459a.entrySet()) {
                caVar.a(entry.getKey());
                entry.getValue().write(caVar);
            }
            BitSet bitSet = new BitSet();
            if (cVar.j()) {
                bitSet.set(0);
            }
            if (cVar.m()) {
                bitSet.set(1);
            }
            caVar.a(bitSet, 2);
            if (cVar.j()) {
                caVar.a(cVar.f23460b.size());
                Iterator<com.umeng.commonsdk.statistics.proto.a> it = cVar.f23460b.iterator();
                while (it.hasNext()) {
                    it.next().write(caVar);
                }
            }
            if (cVar.m()) {
                caVar.a(cVar.f23461c);
            }
        }

        @Override // com.umeng.analytics.pro.cc
        public void b(bu buVar, c cVar) throws bb {
            ca caVar = (ca) buVar;
            br brVar = new br((byte) 11, (byte) 12, caVar.w());
            cVar.f23459a = new HashMap(brVar.f22631c * 2);
            for (int i2 = 0; i2 < brVar.f22631c; i2++) {
                String strZ = caVar.z();
                com.umeng.commonsdk.statistics.proto.b bVar = new com.umeng.commonsdk.statistics.proto.b();
                bVar.read(caVar);
                cVar.f23459a.put(strZ, bVar);
            }
            cVar.a(true);
            BitSet bitSetB = caVar.b(2);
            if (bitSetB.get(0)) {
                bq bqVar = new bq((byte) 12, caVar.w());
                cVar.f23460b = new ArrayList(bqVar.f22628b);
                for (int i3 = 0; i3 < bqVar.f22628b; i3++) {
                    com.umeng.commonsdk.statistics.proto.a aVar = new com.umeng.commonsdk.statistics.proto.a();
                    aVar.read(caVar);
                    cVar.f23460b.add(aVar);
                }
                cVar.b(true);
            }
            if (bitSetB.get(1)) {
                cVar.f23461c = caVar.z();
                cVar.c(true);
            }
        }
    }

    public static class d implements cd {
        private d() {
        }

        @Override // com.umeng.analytics.pro.cd
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public C0388c b() {
            return new C0388c();
        }
    }

    static {
        HashMap map = new HashMap();
        f23458j = map;
        map.put(ce.class, new b());
        map.put(cf.class, new d());
        EnumMap enumMap = new EnumMap(e.class);
        enumMap.put((EnumMap) e.SNAPSHOTS, (e) new bh("snapshots", (byte) 1, new bk((byte) 13, new bi((byte) 11), new bm((byte) 12, com.umeng.commonsdk.statistics.proto.b.class))));
        enumMap.put((EnumMap) e.JOURNALS, (e) new bh("journals", (byte) 2, new bj((byte) 15, new bm((byte) 12, com.umeng.commonsdk.statistics.proto.a.class))));
        enumMap.put((EnumMap) e.CHECKSUM, (e) new bh("checksum", (byte) 2, new bi((byte) 11)));
        Map<e, bh> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f23452d = mapUnmodifiableMap;
        bh.a(c.class, mapUnmodifiableMap);
    }

    public c() {
        this.f23462k = new e[]{e.JOURNALS, e.CHECKSUM};
    }

    @Override // com.umeng.analytics.pro.av
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public c deepCopy() {
        return new c(this);
    }

    public int b() {
        Map<String, com.umeng.commonsdk.statistics.proto.b> map = this.f23459a;
        if (map == null) {
            return 0;
        }
        return map.size();
    }

    public Map<String, com.umeng.commonsdk.statistics.proto.b> c() {
        return this.f23459a;
    }

    @Override // com.umeng.analytics.pro.av
    public void clear() {
        this.f23459a = null;
        this.f23460b = null;
        this.f23461c = null;
    }

    public void d() {
        this.f23459a = null;
    }

    public boolean e() {
        return this.f23459a != null;
    }

    public int f() {
        List<com.umeng.commonsdk.statistics.proto.a> list = this.f23460b;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public Iterator<com.umeng.commonsdk.statistics.proto.a> g() {
        List<com.umeng.commonsdk.statistics.proto.a> list = this.f23460b;
        if (list == null) {
            return null;
        }
        return list.iterator();
    }

    public List<com.umeng.commonsdk.statistics.proto.a> h() {
        return this.f23460b;
    }

    public void i() {
        this.f23460b = null;
    }

    public boolean j() {
        return this.f23460b != null;
    }

    public String k() {
        return this.f23461c;
    }

    public void l() {
        this.f23461c = null;
    }

    public boolean m() {
        return this.f23461c != null;
    }

    public void n() throws bb {
        if (this.f23459a != null) {
            return;
        }
        throw new bv("Required field 'snapshots' was not present! Struct: " + toString());
    }

    @Override // com.umeng.analytics.pro.av
    public void read(bu buVar) throws bb {
        f23458j.get(buVar.D()).b().b(buVar, this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("IdTracking(");
        sb.append("snapshots:");
        Map<String, com.umeng.commonsdk.statistics.proto.b> map = this.f23459a;
        if (map == null) {
            sb.append("null");
        } else {
            sb.append(map);
        }
        if (j()) {
            sb.append(", ");
            sb.append("journals:");
            List<com.umeng.commonsdk.statistics.proto.a> list = this.f23460b;
            if (list == null) {
                sb.append("null");
            } else {
                sb.append(list);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("checksum:");
            String str = this.f23461c;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override // com.umeng.analytics.pro.av
    public void write(bu buVar) throws bb {
        f23458j.get(buVar.D()).b().a(buVar, this);
    }

    public enum e implements bc {
        SNAPSHOTS(1, "snapshots"),
        JOURNALS(2, "journals"),
        CHECKSUM(3, "checksum");


        /* renamed from: d, reason: collision with root package name */
        private static final Map<String, e> f23466d = new HashMap();

        /* renamed from: e, reason: collision with root package name */
        private final short f23468e;

        /* renamed from: f, reason: collision with root package name */
        private final String f23469f;

        static {
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                f23466d.put(eVar.b(), eVar);
            }
        }

        e(short s2, String str) {
            this.f23468e = s2;
            this.f23469f = str;
        }

        public static e a(int i2) {
            if (i2 == 1) {
                return SNAPSHOTS;
            }
            if (i2 == 2) {
                return JOURNALS;
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
            return this.f23469f;
        }

        public static e a(String str) {
            return f23466d.get(str);
        }

        @Override // com.umeng.analytics.pro.bc
        public short a() {
            return this.f23468e;
        }
    }

    public void a(String str, com.umeng.commonsdk.statistics.proto.b bVar) {
        if (this.f23459a == null) {
            this.f23459a = new HashMap();
        }
        this.f23459a.put(str, bVar);
    }

    public void b(boolean z2) {
        if (z2) {
            return;
        }
        this.f23460b = null;
    }

    public void c(boolean z2) {
        if (z2) {
            return;
        }
        this.f23461c = null;
    }

    public c(Map<String, com.umeng.commonsdk.statistics.proto.b> map) {
        this();
        this.f23459a = map;
    }

    public c(c cVar) {
        this.f23462k = new e[]{e.JOURNALS, e.CHECKSUM};
        if (cVar.e()) {
            HashMap map = new HashMap();
            for (Map.Entry<String, com.umeng.commonsdk.statistics.proto.b> entry : cVar.f23459a.entrySet()) {
                map.put(entry.getKey(), new com.umeng.commonsdk.statistics.proto.b(entry.getValue()));
            }
            this.f23459a = map;
        }
        if (cVar.j()) {
            ArrayList arrayList = new ArrayList();
            Iterator<com.umeng.commonsdk.statistics.proto.a> it = cVar.f23460b.iterator();
            while (it.hasNext()) {
                arrayList.add(new com.umeng.commonsdk.statistics.proto.a(it.next()));
            }
            this.f23460b = arrayList;
        }
        if (cVar.m()) {
            this.f23461c = cVar.f23461c;
        }
    }

    public c a(Map<String, com.umeng.commonsdk.statistics.proto.b> map) {
        this.f23459a = map;
        return this;
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f23459a = null;
    }

    public void a(com.umeng.commonsdk.statistics.proto.a aVar) {
        if (this.f23460b == null) {
            this.f23460b = new ArrayList();
        }
        this.f23460b.add(aVar);
    }

    public c a(List<com.umeng.commonsdk.statistics.proto.a> list) {
        this.f23460b = list;
        return this;
    }

    public c a(String str) {
        this.f23461c = str;
        return this;
    }

    @Override // com.umeng.analytics.pro.av
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public e fieldForId(int i2) {
        return e.a(i2);
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
            read(new bo(new cg(objectInputStream)));
        } catch (bb e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
