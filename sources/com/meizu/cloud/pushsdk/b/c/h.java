package com.meizu.cloud.pushsdk.b.c;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes4.dex */
public final class h extends j {

    /* renamed from: a, reason: collision with root package name */
    public static final g f9112a = g.a("multipart/mixed");

    /* renamed from: b, reason: collision with root package name */
    public static final g f9113b = g.a("multipart/alternative");

    /* renamed from: c, reason: collision with root package name */
    public static final g f9114c = g.a("multipart/digest");

    /* renamed from: d, reason: collision with root package name */
    public static final g f9115d = g.a("multipart/parallel");

    /* renamed from: e, reason: collision with root package name */
    public static final g f9116e = g.a("multipart/form-data");

    /* renamed from: f, reason: collision with root package name */
    private static final byte[] f9117f = {HttpTokens.COLON, 32};

    /* renamed from: g, reason: collision with root package name */
    private static final byte[] f9118g = {13, 10};

    /* renamed from: h, reason: collision with root package name */
    private static final byte[] f9119h = {45, 45};

    /* renamed from: i, reason: collision with root package name */
    private final com.meizu.cloud.pushsdk.b.g.d f9120i;

    /* renamed from: j, reason: collision with root package name */
    private final g f9121j;

    /* renamed from: k, reason: collision with root package name */
    private final g f9122k;

    /* renamed from: l, reason: collision with root package name */
    private final List<b> f9123l;

    /* renamed from: m, reason: collision with root package name */
    private long f9124m = -1;

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        private final com.meizu.cloud.pushsdk.b.g.d f9125a;

        /* renamed from: b, reason: collision with root package name */
        private g f9126b;

        /* renamed from: c, reason: collision with root package name */
        private final List<b> f9127c;

        public a() {
            this(UUID.randomUUID().toString());
        }

        public a(String str) {
            this.f9126b = h.f9112a;
            this.f9127c = new ArrayList();
            this.f9125a = com.meizu.cloud.pushsdk.b.g.d.a(str);
        }

        public a a(c cVar, j jVar) {
            return a(b.a(cVar, jVar));
        }

        public a a(g gVar) {
            if (gVar == null) {
                throw new NullPointerException("type == null");
            }
            if (gVar.a().equals("multipart")) {
                this.f9126b = gVar;
                return this;
            }
            throw new IllegalArgumentException("multipart != " + gVar);
        }

        public a a(b bVar) {
            if (bVar == null) {
                throw new NullPointerException("part == null");
            }
            this.f9127c.add(bVar);
            return this;
        }

        public h a() {
            if (this.f9127c.isEmpty()) {
                throw new IllegalStateException("Multipart body must have at least one part.");
            }
            return new h(this.f9125a, this.f9126b, this.f9127c);
        }
    }

    public static final class b {

        /* renamed from: a, reason: collision with root package name */
        private final c f9128a;

        /* renamed from: b, reason: collision with root package name */
        private final j f9129b;

        private b(c cVar, j jVar) {
            this.f9128a = cVar;
            this.f9129b = jVar;
        }

        public static b a(c cVar, j jVar) {
            if (jVar == null) {
                throw new NullPointerException("body == null");
            }
            if (cVar != null && cVar.a("Content-Type") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Type");
            }
            if (cVar == null || cVar.a("Content-Length") == null) {
                return new b(cVar, jVar);
            }
            throw new IllegalArgumentException("Unexpected header: Content-Length");
        }
    }

    public h(com.meizu.cloud.pushsdk.b.g.d dVar, g gVar, List<b> list) {
        this.f9120i = dVar;
        this.f9121j = gVar;
        this.f9122k = g.a(gVar + "; boundary=" + dVar.a());
        this.f9123l = m.a(list);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private long a(com.meizu.cloud.pushsdk.b.g.b bVar, boolean z2) throws IOException {
        com.meizu.cloud.pushsdk.b.g.a aVar;
        if (z2) {
            bVar = new com.meizu.cloud.pushsdk.b.g.a();
            aVar = bVar;
        } else {
            aVar = 0;
        }
        int size = this.f9123l.size();
        long j2 = 0;
        for (int i2 = 0; i2 < size; i2++) {
            b bVar2 = this.f9123l.get(i2);
            c cVar = bVar2.f9128a;
            j jVar = bVar2.f9129b;
            bVar.c(f9119h);
            bVar.b(this.f9120i);
            bVar.c(f9118g);
            if (cVar != null) {
                int iA = cVar.a();
                for (int i3 = 0; i3 < iA; i3++) {
                    bVar.b(cVar.a(i3)).c(f9117f).b(cVar.b(i3)).c(f9118g);
                }
            }
            g gVarA = jVar.a();
            if (gVarA != null) {
                bVar.b("Content-Type: ").b(gVarA.toString()).c(f9118g);
            }
            long jB = jVar.b();
            if (jB != -1) {
                bVar.b("Content-Length: ").e(jB).c(f9118g);
            } else if (z2) {
                aVar.j();
                return -1L;
            }
            byte[] bArr = f9118g;
            bVar.c(bArr);
            if (z2) {
                j2 += jB;
            } else {
                jVar.a(bVar);
            }
            bVar.c(bArr);
        }
        byte[] bArr2 = f9119h;
        bVar.c(bArr2);
        bVar.b(this.f9120i);
        bVar.c(bArr2);
        bVar.c(f9118g);
        if (!z2) {
            return j2;
        }
        long jA = j2 + aVar.a();
        aVar.j();
        return jA;
    }

    @Override // com.meizu.cloud.pushsdk.b.c.j
    public g a() {
        return this.f9122k;
    }

    @Override // com.meizu.cloud.pushsdk.b.c.j
    public void a(com.meizu.cloud.pushsdk.b.g.b bVar) throws IOException {
        a(bVar, false);
    }

    @Override // com.meizu.cloud.pushsdk.b.c.j
    public long b() throws IOException {
        long j2 = this.f9124m;
        if (j2 != -1) {
            return j2;
        }
        long jA = a((com.meizu.cloud.pushsdk.b.g.b) null, true);
        this.f9124m = jA;
        return jA;
    }
}
