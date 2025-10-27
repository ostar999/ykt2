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
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.cookie.ClientCookie;

/* loaded from: classes6.dex */
public class a implements av<a, e>, Serializable, Cloneable {

    /* renamed from: e, reason: collision with root package name */
    public static final Map<e, bh> f23409e;

    /* renamed from: f, reason: collision with root package name */
    private static final long f23410f = 9132678615281394583L;

    /* renamed from: g, reason: collision with root package name */
    private static final bz f23411g = new bz("IdJournal");

    /* renamed from: h, reason: collision with root package name */
    private static final bp f23412h = new bp(ClientCookie.DOMAIN_ATTR, (byte) 11, 1);

    /* renamed from: i, reason: collision with root package name */
    private static final bp f23413i = new bp("old_id", (byte) 11, 2);

    /* renamed from: j, reason: collision with root package name */
    private static final bp f23414j = new bp("new_id", (byte) 11, 3);

    /* renamed from: k, reason: collision with root package name */
    private static final bp f23415k = new bp("ts", (byte) 10, 4);

    /* renamed from: l, reason: collision with root package name */
    private static final Map<Class<? extends cc>, cd> f23416l;

    /* renamed from: m, reason: collision with root package name */
    private static final int f23417m = 0;

    /* renamed from: a, reason: collision with root package name */
    public String f23418a;

    /* renamed from: b, reason: collision with root package name */
    public String f23419b;

    /* renamed from: c, reason: collision with root package name */
    public String f23420c;

    /* renamed from: d, reason: collision with root package name */
    public long f23421d;

    /* renamed from: n, reason: collision with root package name */
    private byte f23422n;

    /* renamed from: o, reason: collision with root package name */
    private e[] f23423o;

    /* renamed from: com.umeng.commonsdk.statistics.proto.a$a, reason: collision with other inner class name */
    public static class C0386a extends ce<a> {
        private C0386a() {
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(bu buVar, a aVar) throws bb {
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
                            if (s2 != 4) {
                                bx.a(buVar, b3);
                            } else if (b3 == 10) {
                                aVar.f23421d = buVar.x();
                                aVar.d(true);
                            } else {
                                bx.a(buVar, b3);
                            }
                        } else if (b3 == 11) {
                            aVar.f23420c = buVar.z();
                            aVar.c(true);
                        } else {
                            bx.a(buVar, b3);
                        }
                    } else if (b3 == 11) {
                        aVar.f23419b = buVar.z();
                        aVar.b(true);
                    } else {
                        bx.a(buVar, b3);
                    }
                } else if (b3 == 11) {
                    aVar.f23418a = buVar.z();
                    aVar.a(true);
                } else {
                    bx.a(buVar, b3);
                }
                buVar.m();
            }
            buVar.k();
            if (aVar.m()) {
                aVar.n();
                return;
            }
            throw new bv("Required field 'ts' was not found in serialized data! Struct: " + toString());
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(bu buVar, a aVar) throws bb {
            aVar.n();
            buVar.a(a.f23411g);
            if (aVar.f23418a != null) {
                buVar.a(a.f23412h);
                buVar.a(aVar.f23418a);
                buVar.c();
            }
            if (aVar.f23419b != null && aVar.g()) {
                buVar.a(a.f23413i);
                buVar.a(aVar.f23419b);
                buVar.c();
            }
            if (aVar.f23420c != null) {
                buVar.a(a.f23414j);
                buVar.a(aVar.f23420c);
                buVar.c();
            }
            buVar.a(a.f23415k);
            buVar.a(aVar.f23421d);
            buVar.c();
            buVar.d();
            buVar.b();
        }
    }

    public static class b implements cd {
        private b() {
        }

        @Override // com.umeng.analytics.pro.cd
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public C0386a b() {
            return new C0386a();
        }
    }

    public static class c extends cf<a> {
        private c() {
        }

        @Override // com.umeng.analytics.pro.cc
        public void a(bu buVar, a aVar) throws bb {
            ca caVar = (ca) buVar;
            caVar.a(aVar.f23418a);
            caVar.a(aVar.f23420c);
            caVar.a(aVar.f23421d);
            BitSet bitSet = new BitSet();
            if (aVar.g()) {
                bitSet.set(0);
            }
            caVar.a(bitSet, 1);
            if (aVar.g()) {
                caVar.a(aVar.f23419b);
            }
        }

        @Override // com.umeng.analytics.pro.cc
        public void b(bu buVar, a aVar) throws bb {
            ca caVar = (ca) buVar;
            aVar.f23418a = caVar.z();
            aVar.a(true);
            aVar.f23420c = caVar.z();
            aVar.c(true);
            aVar.f23421d = caVar.x();
            aVar.d(true);
            if (caVar.b(1).get(0)) {
                aVar.f23419b = caVar.z();
                aVar.b(true);
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
        f23416l = map;
        map.put(ce.class, new b());
        map.put(cf.class, new d());
        EnumMap enumMap = new EnumMap(e.class);
        enumMap.put((EnumMap) e.DOMAIN, (e) new bh(ClientCookie.DOMAIN_ATTR, (byte) 1, new bi((byte) 11)));
        enumMap.put((EnumMap) e.OLD_ID, (e) new bh("old_id", (byte) 2, new bi((byte) 11)));
        enumMap.put((EnumMap) e.NEW_ID, (e) new bh("new_id", (byte) 1, new bi((byte) 11)));
        enumMap.put((EnumMap) e.TS, (e) new bh("ts", (byte) 1, new bi((byte) 10)));
        Map<e, bh> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f23409e = mapUnmodifiableMap;
        bh.a(a.class, mapUnmodifiableMap);
    }

    public a() {
        this.f23422n = (byte) 0;
        this.f23423o = new e[]{e.OLD_ID};
    }

    @Override // com.umeng.analytics.pro.av
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public a deepCopy() {
        return new a(this);
    }

    public String b() {
        return this.f23418a;
    }

    public void c() {
        this.f23418a = null;
    }

    @Override // com.umeng.analytics.pro.av
    public void clear() {
        this.f23418a = null;
        this.f23419b = null;
        this.f23420c = null;
        d(false);
        this.f23421d = 0L;
    }

    public boolean d() {
        return this.f23418a != null;
    }

    public String e() {
        return this.f23419b;
    }

    public void f() {
        this.f23419b = null;
    }

    public boolean g() {
        return this.f23419b != null;
    }

    public String h() {
        return this.f23420c;
    }

    public void i() {
        this.f23420c = null;
    }

    public boolean j() {
        return this.f23420c != null;
    }

    public long k() {
        return this.f23421d;
    }

    public void l() {
        this.f23422n = as.b(this.f23422n, 0);
    }

    public boolean m() {
        return as.a(this.f23422n, 0);
    }

    public void n() throws bb {
        if (this.f23418a == null) {
            throw new bv("Required field 'domain' was not present! Struct: " + toString());
        }
        if (this.f23420c != null) {
            return;
        }
        throw new bv("Required field 'new_id' was not present! Struct: " + toString());
    }

    @Override // com.umeng.analytics.pro.av
    public void read(bu buVar) throws bb {
        f23416l.get(buVar.D()).b().b(buVar, this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("IdJournal(");
        sb.append("domain:");
        String str = this.f23418a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        if (g()) {
            sb.append(", ");
            sb.append("old_id:");
            String str2 = this.f23419b;
            if (str2 == null) {
                sb.append("null");
            } else {
                sb.append(str2);
            }
        }
        sb.append(", ");
        sb.append("new_id:");
        String str3 = this.f23420c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("ts:");
        sb.append(this.f23421d);
        sb.append(")");
        return sb.toString();
    }

    @Override // com.umeng.analytics.pro.av
    public void write(bu buVar) throws bb {
        f23416l.get(buVar.D()).b().a(buVar, this);
    }

    public enum e implements bc {
        DOMAIN(1, ClientCookie.DOMAIN_ATTR),
        OLD_ID(2, "old_id"),
        NEW_ID(3, "new_id"),
        TS(4, "ts");


        /* renamed from: e, reason: collision with root package name */
        private static final Map<String, e> f23428e = new HashMap();

        /* renamed from: f, reason: collision with root package name */
        private final short f23430f;

        /* renamed from: g, reason: collision with root package name */
        private final String f23431g;

        static {
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                f23428e.put(eVar.b(), eVar);
            }
        }

        e(short s2, String str) {
            this.f23430f = s2;
            this.f23431g = str;
        }

        public static e a(int i2) {
            if (i2 == 1) {
                return DOMAIN;
            }
            if (i2 == 2) {
                return OLD_ID;
            }
            if (i2 == 3) {
                return NEW_ID;
            }
            if (i2 != 4) {
                return null;
            }
            return TS;
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
            return this.f23431g;
        }

        public static e a(String str) {
            return f23428e.get(str);
        }

        @Override // com.umeng.analytics.pro.bc
        public short a() {
            return this.f23430f;
        }
    }

    public a a(String str) {
        this.f23418a = str;
        return this;
    }

    public a b(String str) {
        this.f23419b = str;
        return this;
    }

    public a c(String str) {
        this.f23420c = str;
        return this;
    }

    public void d(boolean z2) {
        this.f23422n = as.a(this.f23422n, 0, z2);
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f23418a = null;
    }

    public void b(boolean z2) {
        if (z2) {
            return;
        }
        this.f23419b = null;
    }

    public void c(boolean z2) {
        if (z2) {
            return;
        }
        this.f23420c = null;
    }

    public a(String str, String str2, long j2) {
        this();
        this.f23418a = str;
        this.f23420c = str2;
        this.f23421d = j2;
        d(true);
    }

    public a a(long j2) {
        this.f23421d = j2;
        d(true);
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

    public a(a aVar) {
        this.f23422n = (byte) 0;
        this.f23423o = new e[]{e.OLD_ID};
        this.f23422n = aVar.f23422n;
        if (aVar.d()) {
            this.f23418a = aVar.f23418a;
        }
        if (aVar.g()) {
            this.f23419b = aVar.f23419b;
        }
        if (aVar.j()) {
            this.f23420c = aVar.f23420c;
        }
        this.f23421d = aVar.f23421d;
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f23422n = (byte) 0;
            read(new bo(new cg(objectInputStream)));
        } catch (bb e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
