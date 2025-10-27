package com.umeng.analytics.pro;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.tencent.open.SocialOperation;
import com.tencent.tbs.one.TBSOneConfigurationKeys;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes6.dex */
public class an implements av<an, e>, Serializable, Cloneable {
    private static final int A = 2;
    private static final int B = 3;

    /* renamed from: k, reason: collision with root package name */
    public static final Map<e, bh> f22464k;

    /* renamed from: l, reason: collision with root package name */
    private static final long f22465l = 420342210744516016L;

    /* renamed from: m, reason: collision with root package name */
    private static final bz f22466m = new bz("UMEnvelope");

    /* renamed from: n, reason: collision with root package name */
    private static final bp f22467n = new bp("version", (byte) 11, 1);

    /* renamed from: o, reason: collision with root package name */
    private static final bp f22468o = new bp("address", (byte) 11, 2);

    /* renamed from: p, reason: collision with root package name */
    private static final bp f22469p = new bp(SocialOperation.GAME_SIGNATURE, (byte) 11, 3);

    /* renamed from: q, reason: collision with root package name */
    private static final bp f22470q = new bp("serial_num", (byte) 8, 4);

    /* renamed from: r, reason: collision with root package name */
    private static final bp f22471r = new bp("ts_secs", (byte) 8, 5);

    /* renamed from: s, reason: collision with root package name */
    private static final bp f22472s = new bp(SessionDescription.ATTR_LENGTH, (byte) 8, 6);

    /* renamed from: t, reason: collision with root package name */
    private static final bp f22473t = new bp("entity", (byte) 11, 7);

    /* renamed from: u, reason: collision with root package name */
    private static final bp f22474u = new bp(TBSOneConfigurationKeys.GUID, (byte) 11, 8);

    /* renamed from: v, reason: collision with root package name */
    private static final bp f22475v = new bp("checksum", (byte) 11, 9);

    /* renamed from: w, reason: collision with root package name */
    private static final bp f22476w = new bp("codex", (byte) 8, 10);

    /* renamed from: x, reason: collision with root package name */
    private static final Map<Class<? extends cc>, cd> f22477x;

    /* renamed from: y, reason: collision with root package name */
    private static final int f22478y = 0;

    /* renamed from: z, reason: collision with root package name */
    private static final int f22479z = 1;
    private byte C;
    private e[] D;

    /* renamed from: a, reason: collision with root package name */
    public String f22480a;

    /* renamed from: b, reason: collision with root package name */
    public String f22481b;

    /* renamed from: c, reason: collision with root package name */
    public String f22482c;

    /* renamed from: d, reason: collision with root package name */
    public int f22483d;

    /* renamed from: e, reason: collision with root package name */
    public int f22484e;

    /* renamed from: f, reason: collision with root package name */
    public int f22485f;

    /* renamed from: g, reason: collision with root package name */
    public ByteBuffer f22486g;

    /* renamed from: h, reason: collision with root package name */
    public String f22487h;

    /* renamed from: i, reason: collision with root package name */
    public String f22488i;

    /* renamed from: j, reason: collision with root package name */
    public int f22489j;

    public static class a extends ce<an> {
        private a() {
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(bu buVar, an anVar) throws bb {
            buVar.j();
            while (true) {
                bp bpVarL = buVar.l();
                byte b3 = bpVarL.f22625b;
                if (b3 == 0) {
                    buVar.k();
                    if (!anVar.m()) {
                        throw new bv("Required field 'serial_num' was not found in serialized data! Struct: " + toString());
                    }
                    if (!anVar.p()) {
                        throw new bv("Required field 'ts_secs' was not found in serialized data! Struct: " + toString());
                    }
                    if (anVar.s()) {
                        anVar.G();
                        return;
                    }
                    throw new bv("Required field 'length' was not found in serialized data! Struct: " + toString());
                }
                switch (bpVarL.f22626c) {
                    case 1:
                        if (b3 != 11) {
                            bx.a(buVar, b3);
                            break;
                        } else {
                            anVar.f22480a = buVar.z();
                            anVar.a(true);
                            break;
                        }
                    case 2:
                        if (b3 != 11) {
                            bx.a(buVar, b3);
                            break;
                        } else {
                            anVar.f22481b = buVar.z();
                            anVar.b(true);
                            break;
                        }
                    case 3:
                        if (b3 != 11) {
                            bx.a(buVar, b3);
                            break;
                        } else {
                            anVar.f22482c = buVar.z();
                            anVar.c(true);
                            break;
                        }
                    case 4:
                        if (b3 != 8) {
                            bx.a(buVar, b3);
                            break;
                        } else {
                            anVar.f22483d = buVar.w();
                            anVar.d(true);
                            break;
                        }
                    case 5:
                        if (b3 != 8) {
                            bx.a(buVar, b3);
                            break;
                        } else {
                            anVar.f22484e = buVar.w();
                            anVar.e(true);
                            break;
                        }
                    case 6:
                        if (b3 != 8) {
                            bx.a(buVar, b3);
                            break;
                        } else {
                            anVar.f22485f = buVar.w();
                            anVar.f(true);
                            break;
                        }
                    case 7:
                        if (b3 != 11) {
                            bx.a(buVar, b3);
                            break;
                        } else {
                            anVar.f22486g = buVar.A();
                            anVar.g(true);
                            break;
                        }
                    case 8:
                        if (b3 != 11) {
                            bx.a(buVar, b3);
                            break;
                        } else {
                            anVar.f22487h = buVar.z();
                            anVar.h(true);
                            break;
                        }
                    case 9:
                        if (b3 != 11) {
                            bx.a(buVar, b3);
                            break;
                        } else {
                            anVar.f22488i = buVar.z();
                            anVar.i(true);
                            break;
                        }
                    case 10:
                        if (b3 != 8) {
                            bx.a(buVar, b3);
                            break;
                        } else {
                            anVar.f22489j = buVar.w();
                            anVar.j(true);
                            break;
                        }
                    default:
                        bx.a(buVar, b3);
                        break;
                }
                buVar.m();
            }
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(bu buVar, an anVar) throws bb {
            anVar.G();
            buVar.a(an.f22466m);
            if (anVar.f22480a != null) {
                buVar.a(an.f22467n);
                buVar.a(anVar.f22480a);
                buVar.c();
            }
            if (anVar.f22481b != null) {
                buVar.a(an.f22468o);
                buVar.a(anVar.f22481b);
                buVar.c();
            }
            if (anVar.f22482c != null) {
                buVar.a(an.f22469p);
                buVar.a(anVar.f22482c);
                buVar.c();
            }
            buVar.a(an.f22470q);
            buVar.a(anVar.f22483d);
            buVar.c();
            buVar.a(an.f22471r);
            buVar.a(anVar.f22484e);
            buVar.c();
            buVar.a(an.f22472s);
            buVar.a(anVar.f22485f);
            buVar.c();
            if (anVar.f22486g != null) {
                buVar.a(an.f22473t);
                buVar.a(anVar.f22486g);
                buVar.c();
            }
            if (anVar.f22487h != null) {
                buVar.a(an.f22474u);
                buVar.a(anVar.f22487h);
                buVar.c();
            }
            if (anVar.f22488i != null) {
                buVar.a(an.f22475v);
                buVar.a(anVar.f22488i);
                buVar.c();
            }
            if (anVar.F()) {
                buVar.a(an.f22476w);
                buVar.a(anVar.f22489j);
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

    public static class c extends cf<an> {
        private c() {
        }

        @Override // com.umeng.analytics.pro.cc
        public void a(bu buVar, an anVar) throws bb {
            ca caVar = (ca) buVar;
            caVar.a(anVar.f22480a);
            caVar.a(anVar.f22481b);
            caVar.a(anVar.f22482c);
            caVar.a(anVar.f22483d);
            caVar.a(anVar.f22484e);
            caVar.a(anVar.f22485f);
            caVar.a(anVar.f22486g);
            caVar.a(anVar.f22487h);
            caVar.a(anVar.f22488i);
            BitSet bitSet = new BitSet();
            if (anVar.F()) {
                bitSet.set(0);
            }
            caVar.a(bitSet, 1);
            if (anVar.F()) {
                caVar.a(anVar.f22489j);
            }
        }

        @Override // com.umeng.analytics.pro.cc
        public void b(bu buVar, an anVar) throws bb {
            ca caVar = (ca) buVar;
            anVar.f22480a = caVar.z();
            anVar.a(true);
            anVar.f22481b = caVar.z();
            anVar.b(true);
            anVar.f22482c = caVar.z();
            anVar.c(true);
            anVar.f22483d = caVar.w();
            anVar.d(true);
            anVar.f22484e = caVar.w();
            anVar.e(true);
            anVar.f22485f = caVar.w();
            anVar.f(true);
            anVar.f22486g = caVar.A();
            anVar.g(true);
            anVar.f22487h = caVar.z();
            anVar.h(true);
            anVar.f22488i = caVar.z();
            anVar.i(true);
            if (caVar.b(1).get(0)) {
                anVar.f22489j = caVar.w();
                anVar.j(true);
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
        f22477x = map;
        map.put(ce.class, new b());
        map.put(cf.class, new d());
        EnumMap enumMap = new EnumMap(e.class);
        enumMap.put((EnumMap) e.VERSION, (e) new bh("version", (byte) 1, new bi((byte) 11)));
        enumMap.put((EnumMap) e.ADDRESS, (e) new bh("address", (byte) 1, new bi((byte) 11)));
        enumMap.put((EnumMap) e.SIGNATURE, (e) new bh(SocialOperation.GAME_SIGNATURE, (byte) 1, new bi((byte) 11)));
        enumMap.put((EnumMap) e.SERIAL_NUM, (e) new bh("serial_num", (byte) 1, new bi((byte) 8)));
        enumMap.put((EnumMap) e.TS_SECS, (e) new bh("ts_secs", (byte) 1, new bi((byte) 8)));
        enumMap.put((EnumMap) e.LENGTH, (e) new bh(SessionDescription.ATTR_LENGTH, (byte) 1, new bi((byte) 8)));
        enumMap.put((EnumMap) e.ENTITY, (e) new bh("entity", (byte) 1, new bi((byte) 11, true)));
        enumMap.put((EnumMap) e.GUID, (e) new bh(TBSOneConfigurationKeys.GUID, (byte) 1, new bi((byte) 11)));
        enumMap.put((EnumMap) e.CHECKSUM, (e) new bh("checksum", (byte) 1, new bi((byte) 11)));
        enumMap.put((EnumMap) e.CODEX, (e) new bh("codex", (byte) 2, new bi((byte) 8)));
        Map<e, bh> mapUnmodifiableMap = Collections.unmodifiableMap(enumMap);
        f22464k = mapUnmodifiableMap;
        bh.a(an.class, mapUnmodifiableMap);
    }

    public an() {
        this.C = (byte) 0;
        this.D = new e[]{e.CODEX};
    }

    public String A() {
        return this.f22488i;
    }

    public void B() {
        this.f22488i = null;
    }

    public boolean C() {
        return this.f22488i != null;
    }

    public int D() {
        return this.f22489j;
    }

    public void E() {
        this.C = as.b(this.C, 3);
    }

    public boolean F() {
        return as.a(this.C, 3);
    }

    public void G() throws bb {
        if (this.f22480a == null) {
            throw new bv("Required field 'version' was not present! Struct: " + toString());
        }
        if (this.f22481b == null) {
            throw new bv("Required field 'address' was not present! Struct: " + toString());
        }
        if (this.f22482c == null) {
            throw new bv("Required field 'signature' was not present! Struct: " + toString());
        }
        if (this.f22486g == null) {
            throw new bv("Required field 'entity' was not present! Struct: " + toString());
        }
        if (this.f22487h == null) {
            throw new bv("Required field 'guid' was not present! Struct: " + toString());
        }
        if (this.f22488i != null) {
            return;
        }
        throw new bv("Required field 'checksum' was not present! Struct: " + toString());
    }

    @Override // com.umeng.analytics.pro.av
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public an deepCopy() {
        return new an(this);
    }

    public String b() {
        return this.f22480a;
    }

    public void c() {
        this.f22480a = null;
    }

    @Override // com.umeng.analytics.pro.av
    public void clear() {
        this.f22480a = null;
        this.f22481b = null;
        this.f22482c = null;
        d(false);
        this.f22483d = 0;
        e(false);
        this.f22484e = 0;
        f(false);
        this.f22485f = 0;
        this.f22486g = null;
        this.f22487h = null;
        this.f22488i = null;
        j(false);
        this.f22489j = 0;
    }

    public boolean d() {
        return this.f22480a != null;
    }

    public String e() {
        return this.f22481b;
    }

    public void f() {
        this.f22481b = null;
    }

    public boolean g() {
        return this.f22481b != null;
    }

    public String h() {
        return this.f22482c;
    }

    public void i() {
        this.f22482c = null;
    }

    public boolean j() {
        return this.f22482c != null;
    }

    public int k() {
        return this.f22483d;
    }

    public void l() {
        this.C = as.b(this.C, 0);
    }

    public boolean m() {
        return as.a(this.C, 0);
    }

    public int n() {
        return this.f22484e;
    }

    public void o() {
        this.C = as.b(this.C, 1);
    }

    public boolean p() {
        return as.a(this.C, 1);
    }

    public int q() {
        return this.f22485f;
    }

    public void r() {
        this.C = as.b(this.C, 2);
    }

    @Override // com.umeng.analytics.pro.av
    public void read(bu buVar) throws bb {
        f22477x.get(buVar.D()).b().b(buVar, this);
    }

    public boolean s() {
        return as.a(this.C, 2);
    }

    public byte[] t() {
        a(aw.c(this.f22486g));
        ByteBuffer byteBuffer = this.f22486g;
        if (byteBuffer == null) {
            return null;
        }
        return byteBuffer.array();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("UMEnvelope(");
        sb.append("version:");
        String str = this.f22480a;
        if (str == null) {
            sb.append("null");
        } else {
            sb.append(str);
        }
        sb.append(", ");
        sb.append("address:");
        String str2 = this.f22481b;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("signature:");
        String str3 = this.f22482c;
        if (str3 == null) {
            sb.append("null");
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("serial_num:");
        sb.append(this.f22483d);
        sb.append(", ");
        sb.append("ts_secs:");
        sb.append(this.f22484e);
        sb.append(", ");
        sb.append("length:");
        sb.append(this.f22485f);
        sb.append(", ");
        sb.append("entity:");
        ByteBuffer byteBuffer = this.f22486g;
        if (byteBuffer == null) {
            sb.append("null");
        } else {
            aw.a(byteBuffer, sb);
        }
        sb.append(", ");
        sb.append("guid:");
        String str4 = this.f22487h;
        if (str4 == null) {
            sb.append("null");
        } else {
            sb.append(str4);
        }
        sb.append(", ");
        sb.append("checksum:");
        String str5 = this.f22488i;
        if (str5 == null) {
            sb.append("null");
        } else {
            sb.append(str5);
        }
        if (F()) {
            sb.append(", ");
            sb.append("codex:");
            sb.append(this.f22489j);
        }
        sb.append(")");
        return sb.toString();
    }

    public ByteBuffer u() {
        return this.f22486g;
    }

    public void v() {
        this.f22486g = null;
    }

    public boolean w() {
        return this.f22486g != null;
    }

    @Override // com.umeng.analytics.pro.av
    public void write(bu buVar) throws bb {
        f22477x.get(buVar.D()).b().a(buVar, this);
    }

    public String x() {
        return this.f22487h;
    }

    public void y() {
        this.f22487h = null;
    }

    public boolean z() {
        return this.f22487h != null;
    }

    public enum e implements bc {
        VERSION(1, "version"),
        ADDRESS(2, "address"),
        SIGNATURE(3, SocialOperation.GAME_SIGNATURE),
        SERIAL_NUM(4, "serial_num"),
        TS_SECS(5, "ts_secs"),
        LENGTH(6, SessionDescription.ATTR_LENGTH),
        ENTITY(7, "entity"),
        GUID(8, TBSOneConfigurationKeys.GUID),
        CHECKSUM(9, "checksum"),
        CODEX(10, "codex");


        /* renamed from: k, reason: collision with root package name */
        private static final Map<String, e> f22500k = new HashMap();

        /* renamed from: l, reason: collision with root package name */
        private final short f22502l;

        /* renamed from: m, reason: collision with root package name */
        private final String f22503m;

        static {
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                f22500k.put(eVar.b(), eVar);
            }
        }

        e(short s2, String str) {
            this.f22502l = s2;
            this.f22503m = str;
        }

        public static e a(int i2) {
            switch (i2) {
                case 1:
                    return VERSION;
                case 2:
                    return ADDRESS;
                case 3:
                    return SIGNATURE;
                case 4:
                    return SERIAL_NUM;
                case 5:
                    return TS_SECS;
                case 6:
                    return LENGTH;
                case 7:
                    return ENTITY;
                case 8:
                    return GUID;
                case 9:
                    return CHECKSUM;
                case 10:
                    return CODEX;
                default:
                    return null;
            }
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
            return this.f22503m;
        }

        public static e a(String str) {
            return f22500k.get(str);
        }

        @Override // com.umeng.analytics.pro.bc
        public short a() {
            return this.f22502l;
        }
    }

    public an a(String str) {
        this.f22480a = str;
        return this;
    }

    public an b(String str) {
        this.f22481b = str;
        return this;
    }

    public an c(String str) {
        this.f22482c = str;
        return this;
    }

    public void d(boolean z2) {
        this.C = as.a(this.C, 0, z2);
    }

    public void e(boolean z2) {
        this.C = as.a(this.C, 1, z2);
    }

    public void f(boolean z2) {
        this.C = as.a(this.C, 2, z2);
    }

    public void g(boolean z2) {
        if (z2) {
            return;
        }
        this.f22486g = null;
    }

    public void h(boolean z2) {
        if (z2) {
            return;
        }
        this.f22487h = null;
    }

    public void i(boolean z2) {
        if (z2) {
            return;
        }
        this.f22488i = null;
    }

    public void j(boolean z2) {
        this.C = as.a(this.C, 3, z2);
    }

    public void a(boolean z2) {
        if (z2) {
            return;
        }
        this.f22480a = null;
    }

    public void b(boolean z2) {
        if (z2) {
            return;
        }
        this.f22481b = null;
    }

    public void c(boolean z2) {
        if (z2) {
            return;
        }
        this.f22482c = null;
    }

    public an d(String str) {
        this.f22487h = str;
        return this;
    }

    public an e(String str) {
        this.f22488i = str;
        return this;
    }

    public an(String str, String str2, String str3, int i2, int i3, int i4, ByteBuffer byteBuffer, String str4, String str5) {
        this();
        this.f22480a = str;
        this.f22481b = str2;
        this.f22482c = str3;
        this.f22483d = i2;
        d(true);
        this.f22484e = i3;
        e(true);
        this.f22485f = i4;
        f(true);
        this.f22486g = byteBuffer;
        this.f22487h = str4;
        this.f22488i = str5;
    }

    public an a(int i2) {
        this.f22483d = i2;
        d(true);
        return this;
    }

    public an b(int i2) {
        this.f22484e = i2;
        e(true);
        return this;
    }

    public an c(int i2) {
        this.f22485f = i2;
        f(true);
        return this;
    }

    public an d(int i2) {
        this.f22489j = i2;
        j(true);
        return this;
    }

    @Override // com.umeng.analytics.pro.av
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public e fieldForId(int i2) {
        return e.a(i2);
    }

    public an a(byte[] bArr) {
        a(bArr == null ? null : ByteBuffer.wrap(bArr));
        return this;
    }

    public an a(ByteBuffer byteBuffer) {
        this.f22486g = byteBuffer;
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
            this.C = (byte) 0;
            read(new bo(new cg(objectInputStream)));
        } catch (bb e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public an(an anVar) {
        this.C = (byte) 0;
        this.D = new e[]{e.CODEX};
        this.C = anVar.C;
        if (anVar.d()) {
            this.f22480a = anVar.f22480a;
        }
        if (anVar.g()) {
            this.f22481b = anVar.f22481b;
        }
        if (anVar.j()) {
            this.f22482c = anVar.f22482c;
        }
        this.f22483d = anVar.f22483d;
        this.f22484e = anVar.f22484e;
        this.f22485f = anVar.f22485f;
        if (anVar.w()) {
            this.f22486g = aw.d(anVar.f22486g);
        }
        if (anVar.z()) {
            this.f22487h = anVar.f22487h;
        }
        if (anVar.C()) {
            this.f22488i = anVar.f22488i;
        }
        this.f22489j = anVar.f22489j;
    }
}
