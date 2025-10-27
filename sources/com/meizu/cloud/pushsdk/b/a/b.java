package com.meizu.cloud.pushsdk.b.a;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import cn.hutool.core.text.StrPool;
import com.meizu.cloud.pushsdk.b.a.b;
import com.meizu.cloud.pushsdk.b.c.b;
import com.meizu.cloud.pushsdk.b.c.c;
import com.meizu.cloud.pushsdk.b.c.f;
import com.meizu.cloud.pushsdk.b.c.g;
import com.meizu.cloud.pushsdk.b.c.h;
import com.meizu.cloud.pushsdk.b.c.j;
import com.meizu.cloud.pushsdk.b.c.k;
import com.meizu.cloud.pushsdk.b.g.f;
import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b<T extends b> {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8970a = "b";

    /* renamed from: w, reason: collision with root package name */
    private static final g f8971w = g.a("application/json; charset=utf-8");

    /* renamed from: x, reason: collision with root package name */
    private static final g f8972x = g.a("text/x-markdown; charset=utf-8");

    /* renamed from: z, reason: collision with root package name */
    private static final Object f8973z = new Object();
    private com.meizu.cloud.pushsdk.b.c.a A;
    private int B;
    private boolean C;
    private int D;
    private com.meizu.cloud.pushsdk.b.d.a E;
    private Bitmap.Config F;
    private int G;
    private int H;
    private ImageView.ScaleType I;
    private Executor J;
    private String K;
    private Type L;

    /* renamed from: b, reason: collision with root package name */
    private int f8974b;

    /* renamed from: c, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.b.a.d f8975c;

    /* renamed from: d, reason: collision with root package name */
    private int f8976d;

    /* renamed from: e, reason: collision with root package name */
    private String f8977e;

    /* renamed from: f, reason: collision with root package name */
    private int f8978f;

    /* renamed from: g, reason: collision with root package name */
    private Object f8979g;

    /* renamed from: h, reason: collision with root package name */
    private e f8980h;

    /* renamed from: i, reason: collision with root package name */
    private HashMap<String, String> f8981i;

    /* renamed from: j, reason: collision with root package name */
    private HashMap<String, String> f8982j;

    /* renamed from: k, reason: collision with root package name */
    private HashMap<String, String> f8983k;

    /* renamed from: l, reason: collision with root package name */
    private HashMap<String, String> f8984l;

    /* renamed from: m, reason: collision with root package name */
    private HashMap<String, String> f8985m;

    /* renamed from: n, reason: collision with root package name */
    private HashMap<String, String> f8986n;

    /* renamed from: o, reason: collision with root package name */
    private HashMap<String, File> f8987o;

    /* renamed from: p, reason: collision with root package name */
    private String f8988p;

    /* renamed from: q, reason: collision with root package name */
    private String f8989q;

    /* renamed from: r, reason: collision with root package name */
    private JSONObject f8990r;

    /* renamed from: s, reason: collision with root package name */
    private JSONArray f8991s;

    /* renamed from: t, reason: collision with root package name */
    private String f8992t;

    /* renamed from: u, reason: collision with root package name */
    private byte[] f8993u;

    /* renamed from: v, reason: collision with root package name */
    private File f8994v;

    /* renamed from: y, reason: collision with root package name */
    private g f8995y;

    /* renamed from: com.meizu.cloud.pushsdk.b.a.b$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f8997a;

        static {
            int[] iArr = new int[e.values().length];
            f8997a = iArr;
            try {
                iArr[e.JSON_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f8997a[e.JSON_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f8997a[e.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f8997a[e.BITMAP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f8997a[e.PREFETCH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static class a<T extends a> {

        /* renamed from: b, reason: collision with root package name */
        private String f8999b;

        /* renamed from: c, reason: collision with root package name */
        private Object f9000c;

        /* renamed from: g, reason: collision with root package name */
        private String f9004g;

        /* renamed from: h, reason: collision with root package name */
        private String f9005h;

        /* renamed from: j, reason: collision with root package name */
        private Executor f9007j;

        /* renamed from: k, reason: collision with root package name */
        private String f9008k;

        /* renamed from: a, reason: collision with root package name */
        private com.meizu.cloud.pushsdk.b.a.d f8998a = com.meizu.cloud.pushsdk.b.a.d.MEDIUM;

        /* renamed from: d, reason: collision with root package name */
        private HashMap<String, String> f9001d = new HashMap<>();

        /* renamed from: e, reason: collision with root package name */
        private HashMap<String, String> f9002e = new HashMap<>();

        /* renamed from: f, reason: collision with root package name */
        private HashMap<String, String> f9003f = new HashMap<>();

        /* renamed from: i, reason: collision with root package name */
        private int f9006i = 0;

        public a(String str, String str2, String str3) {
            this.f8999b = str;
            this.f9004g = str2;
            this.f9005h = str3;
        }

        public b a() {
            return new b(this);
        }
    }

    /* renamed from: com.meizu.cloud.pushsdk.b.a.b$b, reason: collision with other inner class name */
    public static class C0193b<T extends C0193b> {

        /* renamed from: b, reason: collision with root package name */
        private int f9010b;

        /* renamed from: c, reason: collision with root package name */
        private String f9011c;

        /* renamed from: d, reason: collision with root package name */
        private Object f9012d;

        /* renamed from: e, reason: collision with root package name */
        private Bitmap.Config f9013e;

        /* renamed from: f, reason: collision with root package name */
        private int f9014f;

        /* renamed from: g, reason: collision with root package name */
        private int f9015g;

        /* renamed from: h, reason: collision with root package name */
        private ImageView.ScaleType f9016h;

        /* renamed from: l, reason: collision with root package name */
        private Executor f9020l;

        /* renamed from: m, reason: collision with root package name */
        private String f9021m;

        /* renamed from: a, reason: collision with root package name */
        private com.meizu.cloud.pushsdk.b.a.d f9009a = com.meizu.cloud.pushsdk.b.a.d.MEDIUM;

        /* renamed from: i, reason: collision with root package name */
        private HashMap<String, String> f9017i = new HashMap<>();

        /* renamed from: j, reason: collision with root package name */
        private HashMap<String, String> f9018j = new HashMap<>();

        /* renamed from: k, reason: collision with root package name */
        private HashMap<String, String> f9019k = new HashMap<>();

        public C0193b(String str) {
            this.f9010b = 0;
            this.f9011c = str;
            this.f9010b = 0;
        }

        public T a(HashMap<String, String> map) {
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    this.f9018j.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    public static class c<T extends c> {

        /* renamed from: b, reason: collision with root package name */
        private String f9023b;

        /* renamed from: c, reason: collision with root package name */
        private Object f9024c;

        /* renamed from: j, reason: collision with root package name */
        private Executor f9031j;

        /* renamed from: k, reason: collision with root package name */
        private String f9032k;

        /* renamed from: l, reason: collision with root package name */
        private String f9033l;

        /* renamed from: a, reason: collision with root package name */
        private com.meizu.cloud.pushsdk.b.a.d f9022a = com.meizu.cloud.pushsdk.b.a.d.MEDIUM;

        /* renamed from: d, reason: collision with root package name */
        private HashMap<String, String> f9025d = new HashMap<>();

        /* renamed from: e, reason: collision with root package name */
        private HashMap<String, String> f9026e = new HashMap<>();

        /* renamed from: f, reason: collision with root package name */
        private HashMap<String, String> f9027f = new HashMap<>();

        /* renamed from: g, reason: collision with root package name */
        private HashMap<String, String> f9028g = new HashMap<>();

        /* renamed from: h, reason: collision with root package name */
        private HashMap<String, File> f9029h = new HashMap<>();

        /* renamed from: i, reason: collision with root package name */
        private int f9030i = 0;

        public c(String str) {
            this.f9023b = str;
        }

        public T a(String str, File file) {
            this.f9029h.put(str, file);
            return this;
        }

        public T a(HashMap<String, String> map) {
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    this.f9026e.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    public static class d<T extends d> {

        /* renamed from: b, reason: collision with root package name */
        private int f9035b;

        /* renamed from: c, reason: collision with root package name */
        private String f9036c;

        /* renamed from: d, reason: collision with root package name */
        private Object f9037d;

        /* renamed from: o, reason: collision with root package name */
        private Executor f9048o;

        /* renamed from: p, reason: collision with root package name */
        private String f9049p;

        /* renamed from: q, reason: collision with root package name */
        private String f9050q;

        /* renamed from: a, reason: collision with root package name */
        private com.meizu.cloud.pushsdk.b.a.d f9034a = com.meizu.cloud.pushsdk.b.a.d.MEDIUM;

        /* renamed from: e, reason: collision with root package name */
        private JSONObject f9038e = null;

        /* renamed from: f, reason: collision with root package name */
        private JSONArray f9039f = null;

        /* renamed from: g, reason: collision with root package name */
        private String f9040g = null;

        /* renamed from: h, reason: collision with root package name */
        private byte[] f9041h = null;

        /* renamed from: i, reason: collision with root package name */
        private File f9042i = null;

        /* renamed from: j, reason: collision with root package name */
        private HashMap<String, String> f9043j = new HashMap<>();

        /* renamed from: k, reason: collision with root package name */
        private HashMap<String, String> f9044k = new HashMap<>();

        /* renamed from: l, reason: collision with root package name */
        private HashMap<String, String> f9045l = new HashMap<>();

        /* renamed from: m, reason: collision with root package name */
        private HashMap<String, String> f9046m = new HashMap<>();

        /* renamed from: n, reason: collision with root package name */
        private HashMap<String, String> f9047n = new HashMap<>();

        public d(String str) {
            this.f9035b = 1;
            this.f9036c = str;
            this.f9035b = 1;
        }

        public T a(HashMap<String, String> map) {
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    this.f9044k.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    public b(a aVar) {
        this.f8981i = new HashMap<>();
        this.f8982j = new HashMap<>();
        this.f8983k = new HashMap<>();
        this.f8984l = new HashMap<>();
        this.f8985m = new HashMap<>();
        this.f8986n = new HashMap<>();
        this.f8987o = new HashMap<>();
        this.f8990r = null;
        this.f8991s = null;
        this.f8992t = null;
        this.f8993u = null;
        this.f8994v = null;
        this.f8995y = null;
        this.D = 0;
        this.J = null;
        this.K = null;
        this.L = null;
        this.f8976d = 1;
        this.f8974b = 0;
        this.f8975c = aVar.f8998a;
        this.f8977e = aVar.f8999b;
        this.f8979g = aVar.f9000c;
        this.f8988p = aVar.f9004g;
        this.f8989q = aVar.f9005h;
        this.f8981i = aVar.f9001d;
        this.f8985m = aVar.f9002e;
        this.f8986n = aVar.f9003f;
        this.D = aVar.f9006i;
        this.J = aVar.f9007j;
        this.K = aVar.f9008k;
    }

    public b(C0193b c0193b) {
        this.f8981i = new HashMap<>();
        this.f8982j = new HashMap<>();
        this.f8983k = new HashMap<>();
        this.f8984l = new HashMap<>();
        this.f8985m = new HashMap<>();
        this.f8986n = new HashMap<>();
        this.f8987o = new HashMap<>();
        this.f8990r = null;
        this.f8991s = null;
        this.f8992t = null;
        this.f8993u = null;
        this.f8994v = null;
        this.f8995y = null;
        this.D = 0;
        this.J = null;
        this.K = null;
        this.L = null;
        this.f8976d = 0;
        this.f8974b = c0193b.f9010b;
        this.f8975c = c0193b.f9009a;
        this.f8977e = c0193b.f9011c;
        this.f8979g = c0193b.f9012d;
        this.f8981i = c0193b.f9017i;
        this.F = c0193b.f9013e;
        this.H = c0193b.f9015g;
        this.G = c0193b.f9014f;
        this.I = c0193b.f9016h;
        this.f8985m = c0193b.f9018j;
        this.f8986n = c0193b.f9019k;
        this.J = c0193b.f9020l;
        this.K = c0193b.f9021m;
    }

    public b(c cVar) {
        this.f8981i = new HashMap<>();
        this.f8982j = new HashMap<>();
        this.f8983k = new HashMap<>();
        this.f8984l = new HashMap<>();
        this.f8985m = new HashMap<>();
        this.f8986n = new HashMap<>();
        this.f8987o = new HashMap<>();
        this.f8990r = null;
        this.f8991s = null;
        this.f8992t = null;
        this.f8993u = null;
        this.f8994v = null;
        this.f8995y = null;
        this.D = 0;
        this.J = null;
        this.K = null;
        this.L = null;
        this.f8976d = 2;
        this.f8974b = 1;
        this.f8975c = cVar.f9022a;
        this.f8977e = cVar.f9023b;
        this.f8979g = cVar.f9024c;
        this.f8981i = cVar.f9025d;
        this.f8985m = cVar.f9027f;
        this.f8986n = cVar.f9028g;
        this.f8984l = cVar.f9026e;
        this.f8987o = cVar.f9029h;
        this.D = cVar.f9030i;
        this.J = cVar.f9031j;
        this.K = cVar.f9032k;
        if (cVar.f9033l != null) {
            this.f8995y = g.a(cVar.f9033l);
        }
    }

    public b(d dVar) {
        this.f8981i = new HashMap<>();
        this.f8982j = new HashMap<>();
        this.f8983k = new HashMap<>();
        this.f8984l = new HashMap<>();
        this.f8985m = new HashMap<>();
        this.f8986n = new HashMap<>();
        this.f8987o = new HashMap<>();
        this.f8990r = null;
        this.f8991s = null;
        this.f8992t = null;
        this.f8993u = null;
        this.f8994v = null;
        this.f8995y = null;
        this.D = 0;
        this.J = null;
        this.K = null;
        this.L = null;
        this.f8976d = 0;
        this.f8974b = dVar.f9035b;
        this.f8975c = dVar.f9034a;
        this.f8977e = dVar.f9036c;
        this.f8979g = dVar.f9037d;
        this.f8981i = dVar.f9043j;
        this.f8982j = dVar.f9044k;
        this.f8983k = dVar.f9045l;
        this.f8985m = dVar.f9046m;
        this.f8986n = dVar.f9047n;
        this.f8990r = dVar.f9038e;
        this.f8991s = dVar.f9039f;
        this.f8992t = dVar.f9040g;
        this.f8994v = dVar.f9042i;
        this.f8993u = dVar.f9041h;
        this.J = dVar.f9048o;
        this.K = dVar.f9049p;
        if (dVar.f9050q != null) {
            this.f8995y = g.a(dVar.f9050q);
        }
    }

    public com.meizu.cloud.pushsdk.b.a.c a() {
        this.f8980h = e.STRING;
        return com.meizu.cloud.pushsdk.b.e.c.a(this);
    }

    public com.meizu.cloud.pushsdk.b.a.c a(k kVar) {
        com.meizu.cloud.pushsdk.b.a.c<Bitmap> cVarA;
        int i2 = AnonymousClass2.f8997a[this.f8980h.ordinal()];
        if (i2 == 1) {
            try {
                return com.meizu.cloud.pushsdk.b.a.c.a(new JSONArray(f.a(kVar.b().a()).h()));
            } catch (Exception e2) {
                return com.meizu.cloud.pushsdk.b.a.c.a(com.meizu.cloud.pushsdk.b.i.b.b(new com.meizu.cloud.pushsdk.b.b.a(e2)));
            }
        }
        if (i2 == 2) {
            try {
                return com.meizu.cloud.pushsdk.b.a.c.a(new JSONObject(f.a(kVar.b().a()).h()));
            } catch (Exception e3) {
                return com.meizu.cloud.pushsdk.b.a.c.a(com.meizu.cloud.pushsdk.b.i.b.b(new com.meizu.cloud.pushsdk.b.b.a(e3)));
            }
        }
        if (i2 == 3) {
            try {
                return com.meizu.cloud.pushsdk.b.a.c.a(f.a(kVar.b().a()).h());
            } catch (Exception e4) {
                return com.meizu.cloud.pushsdk.b.a.c.a(com.meizu.cloud.pushsdk.b.i.b.b(new com.meizu.cloud.pushsdk.b.b.a(e4)));
            }
        }
        if (i2 != 4) {
            if (i2 != 5) {
                return null;
            }
            return com.meizu.cloud.pushsdk.b.a.c.a("prefetch");
        }
        synchronized (f8973z) {
            try {
                try {
                    cVarA = com.meizu.cloud.pushsdk.b.i.b.a(kVar, this.G, this.H, this.F, this.I);
                } catch (Exception e5) {
                    return com.meizu.cloud.pushsdk.b.a.c.a(com.meizu.cloud.pushsdk.b.i.b.b(new com.meizu.cloud.pushsdk.b.b.a(e5)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return cVarA;
    }

    public com.meizu.cloud.pushsdk.b.b.a a(com.meizu.cloud.pushsdk.b.b.a aVar) {
        try {
            if (aVar.a() != null && aVar.a().b() != null && aVar.a().b().a() != null) {
                aVar.b(f.a(aVar.a().b().a()).h());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return aVar;
    }

    public void a(com.meizu.cloud.pushsdk.b.c.a aVar) {
        this.A = aVar;
    }

    public void a(String str) {
        this.K = str;
    }

    public com.meizu.cloud.pushsdk.b.a.c b() {
        this.f8980h = e.BITMAP;
        return com.meizu.cloud.pushsdk.b.e.c.a(this);
    }

    public com.meizu.cloud.pushsdk.b.a.c c() {
        return com.meizu.cloud.pushsdk.b.e.c.a(this);
    }

    public int d() {
        return this.f8974b;
    }

    public String e() {
        String strReplace = this.f8977e;
        for (Map.Entry<String, String> entry : this.f8986n.entrySet()) {
            strReplace = strReplace.replace(StrPool.DELIM_START + entry.getKey() + "}", String.valueOf(entry.getValue()));
        }
        f.a aVarG = com.meizu.cloud.pushsdk.b.c.f.c(strReplace).g();
        for (Map.Entry<String, String> entry2 : this.f8985m.entrySet()) {
            aVarG.a(entry2.getKey(), entry2.getValue());
        }
        return aVarG.b().toString();
    }

    public e f() {
        return this.f8980h;
    }

    public int g() {
        return this.f8976d;
    }

    public String h() {
        return this.K;
    }

    public com.meizu.cloud.pushsdk.b.d.a i() {
        return new com.meizu.cloud.pushsdk.b.d.a() { // from class: com.meizu.cloud.pushsdk.b.a.b.1
            @Override // com.meizu.cloud.pushsdk.b.d.a
            public void a(long j2, long j3) {
                b.this.B = (int) ((100 * j2) / j3);
                if (b.this.E == null || b.this.C) {
                    return;
                }
                b.this.E.a(j2, j3);
            }
        };
    }

    public String j() {
        return this.f8988p;
    }

    public String k() {
        return this.f8989q;
    }

    public com.meizu.cloud.pushsdk.b.c.a l() {
        return this.A;
    }

    public j m() {
        JSONObject jSONObject = this.f8990r;
        if (jSONObject != null) {
            g gVar = this.f8995y;
            return gVar != null ? j.a(gVar, jSONObject.toString()) : j.a(f8971w, jSONObject.toString());
        }
        JSONArray jSONArray = this.f8991s;
        if (jSONArray != null) {
            g gVar2 = this.f8995y;
            return gVar2 != null ? j.a(gVar2, jSONArray.toString()) : j.a(f8971w, jSONArray.toString());
        }
        String str = this.f8992t;
        if (str != null) {
            g gVar3 = this.f8995y;
            return gVar3 != null ? j.a(gVar3, str) : j.a(f8972x, str);
        }
        File file = this.f8994v;
        if (file != null) {
            g gVar4 = this.f8995y;
            return gVar4 != null ? j.a(gVar4, file) : j.a(f8972x, file);
        }
        byte[] bArr = this.f8993u;
        if (bArr != null) {
            g gVar5 = this.f8995y;
            return gVar5 != null ? j.a(gVar5, bArr) : j.a(f8972x, bArr);
        }
        b.a aVar = new b.a();
        try {
            for (Map.Entry<String, String> entry : this.f8982j.entrySet()) {
                if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                    aVar.a(entry.getKey(), entry.getValue());
                }
            }
            for (Map.Entry<String, String> entry2 : this.f8983k.entrySet()) {
                if (!TextUtils.isEmpty(entry2.getKey()) && !TextUtils.isEmpty(entry2.getValue())) {
                    aVar.b(entry2.getKey(), entry2.getValue());
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return aVar.a();
    }

    public j n() {
        h.a aVarA = new h.a().a(h.f9116e);
        try {
            for (Map.Entry<String, String> entry : this.f8984l.entrySet()) {
                aVarA.a(com.meizu.cloud.pushsdk.b.c.c.a("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""), j.a((g) null, entry.getValue()));
            }
            for (Map.Entry<String, File> entry2 : this.f8987o.entrySet()) {
                if (entry2.getValue() != null) {
                    String name = entry2.getValue().getName();
                    aVarA.a(com.meizu.cloud.pushsdk.b.c.c.a("Content-Disposition", "form-data; name=\"" + entry2.getKey() + "\"; filename=\"" + name + "\""), j.a(g.a(com.meizu.cloud.pushsdk.b.i.b.a(name)), entry2.getValue()));
                    g gVar = this.f8995y;
                    if (gVar != null) {
                        aVarA.a(gVar);
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return aVarA.a();
    }

    public com.meizu.cloud.pushsdk.b.c.c o() {
        c.a aVar = new c.a();
        try {
            for (Map.Entry<String, String> entry : this.f8981i.entrySet()) {
                aVar.a(entry.getKey(), entry.getValue());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return aVar.a();
    }

    public String toString() {
        return "ANRequest{sequenceNumber='" + this.f8978f + ", mMethod=" + this.f8974b + ", mPriority=" + this.f8975c + ", mRequestType=" + this.f8976d + ", mUrl=" + this.f8977e + '}';
    }
}
