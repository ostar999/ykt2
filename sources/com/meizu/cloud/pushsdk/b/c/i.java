package com.meizu.cloud.pushsdk.b.c;

import com.meizu.cloud.pushsdk.b.c.c;

/* loaded from: classes4.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private final f f9130a;

    /* renamed from: b, reason: collision with root package name */
    private final String f9131b;

    /* renamed from: c, reason: collision with root package name */
    private final c f9132c;

    /* renamed from: d, reason: collision with root package name */
    private final j f9133d;

    /* renamed from: e, reason: collision with root package name */
    private final Object f9134e;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private f f9135a;

        /* renamed from: b, reason: collision with root package name */
        private String f9136b = "GET";

        /* renamed from: c, reason: collision with root package name */
        private c.a f9137c = new c.a();

        /* renamed from: d, reason: collision with root package name */
        private j f9138d;

        /* renamed from: e, reason: collision with root package name */
        private Object f9139e;

        public a a() {
            return a("GET", (j) null);
        }

        public a a(c cVar) {
            this.f9137c = cVar.c();
            return this;
        }

        public a a(f fVar) {
            if (fVar == null) {
                throw new IllegalArgumentException("url == null");
            }
            this.f9135a = fVar;
            return this;
        }

        public a a(j jVar) {
            return a("POST", jVar);
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0045  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x004a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.meizu.cloud.pushsdk.b.c.i.a a(java.lang.String r7) {
            /*
                r6 = this;
                if (r7 == 0) goto L61
                r1 = 1
                r2 = 0
                java.lang.String r3 = "ws:"
                r4 = 0
                r5 = 3
                r0 = r7
                boolean r0 = r0.regionMatches(r1, r2, r3, r4, r5)
                if (r0 == 0) goto L26
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "http:"
                r0.append(r1)
                r1 = 3
            L1a:
                java.lang.String r7 = r7.substring(r1)
                r0.append(r7)
                java.lang.String r7 = r0.toString()
                goto L3f
            L26:
                r1 = 1
                r2 = 0
                java.lang.String r3 = "wss:"
                r4 = 0
                r5 = 4
                r0 = r7
                boolean r0 = r0.regionMatches(r1, r2, r3, r4, r5)
                if (r0 == 0) goto L3f
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "https:"
                r0.append(r1)
                r1 = 4
                goto L1a
            L3f:
                com.meizu.cloud.pushsdk.b.c.f r0 = com.meizu.cloud.pushsdk.b.c.f.c(r7)
                if (r0 == 0) goto L4a
                com.meizu.cloud.pushsdk.b.c.i$a r7 = r6.a(r0)
                return r7
            L4a:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "unexpected url: "
                r1.append(r2)
                r1.append(r7)
                java.lang.String r7 = r1.toString()
                r0.<init>(r7)
                throw r0
            L61:
                java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
                java.lang.String r0 = "url == null"
                r7.<init>(r0)
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.b.c.i.a.a(java.lang.String):com.meizu.cloud.pushsdk.b.c.i$a");
        }

        public a a(String str, j jVar) {
            if (str == null || str.length() == 0) {
                throw new IllegalArgumentException("method == null || method.length() == 0");
            }
            if (jVar != null && !d.b(str)) {
                throw new IllegalArgumentException("method " + str + " must not have a request body.");
            }
            if (jVar != null || !d.a(str)) {
                this.f9136b = str;
                this.f9138d = jVar;
                return this;
            }
            throw new IllegalArgumentException("method " + str + " must have a request body.");
        }

        public a a(String str, String str2) {
            this.f9137c.a(str, str2);
            return this;
        }

        public a b() {
            return a("HEAD", (j) null);
        }

        public a b(j jVar) {
            return a("DELETE", jVar);
        }

        public a c(j jVar) {
            return a("PUT", jVar);
        }

        public i c() {
            if (this.f9135a != null) {
                return new i(this);
            }
            throw new IllegalStateException("url == null");
        }

        public a d(j jVar) {
            return a("PATCH", jVar);
        }
    }

    private i(a aVar) {
        this.f9130a = aVar.f9135a;
        this.f9131b = aVar.f9136b;
        this.f9132c = aVar.f9137c.a();
        this.f9133d = aVar.f9138d;
        this.f9134e = aVar.f9139e != null ? aVar.f9139e : this;
    }

    public f a() {
        return this.f9130a;
    }

    public String a(String str) {
        return this.f9132c.a(str);
    }

    public String b() {
        return this.f9131b;
    }

    public int c() {
        if ("GET".equals(b())) {
            return 0;
        }
        if ("POST".equals(b())) {
            return 1;
        }
        if ("PUT".equals(b())) {
            return 2;
        }
        if ("DELETE".equals(b())) {
            return 3;
        }
        if ("HEAD".equals(b())) {
            return 4;
        }
        return "PATCH".equals(b()) ? 5 : 0;
    }

    public c d() {
        return this.f9132c;
    }

    public j e() {
        return this.f9133d;
    }

    public boolean f() {
        return this.f9130a.a();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request{method=");
        sb.append(this.f9131b);
        sb.append(", url=");
        sb.append(this.f9130a);
        sb.append(", tag=");
        Object obj = this.f9134e;
        if (obj == this) {
            obj = null;
        }
        sb.append(obj);
        sb.append('}');
        return sb.toString();
    }
}
