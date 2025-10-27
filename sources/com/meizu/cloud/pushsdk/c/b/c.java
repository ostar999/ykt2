package com.meizu.cloud.pushsdk.c.b;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.just.agentweb.DefaultWebClient;
import com.meizu.cloud.pushsdk.b.c.i;
import com.meizu.cloud.pushsdk.b.c.j;
import com.meizu.cloud.pushsdk.b.c.k;
import com.umeng.analytics.pro.am;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes4.dex */
public abstract class c {

    /* renamed from: a, reason: collision with root package name */
    protected int f9294a = 88;

    /* renamed from: b, reason: collision with root package name */
    protected int f9295b = 22;

    /* renamed from: c, reason: collision with root package name */
    protected final com.meizu.cloud.pushsdk.b.c.g f9296c;

    /* renamed from: d, reason: collision with root package name */
    protected Context f9297d;

    /* renamed from: e, reason: collision with root package name */
    protected Uri.Builder f9298e;

    /* renamed from: f, reason: collision with root package name */
    protected f f9299f;

    /* renamed from: g, reason: collision with root package name */
    protected d f9300g;

    /* renamed from: h, reason: collision with root package name */
    protected com.meizu.cloud.pushsdk.c.b.a f9301h;

    /* renamed from: i, reason: collision with root package name */
    protected h f9302i;

    /* renamed from: j, reason: collision with root package name */
    protected SSLSocketFactory f9303j;

    /* renamed from: k, reason: collision with root package name */
    protected HostnameVerifier f9304k;

    /* renamed from: l, reason: collision with root package name */
    protected String f9305l;

    /* renamed from: m, reason: collision with root package name */
    protected int f9306m;

    /* renamed from: n, reason: collision with root package name */
    protected int f9307n;

    /* renamed from: o, reason: collision with root package name */
    protected int f9308o;

    /* renamed from: p, reason: collision with root package name */
    protected long f9309p;

    /* renamed from: q, reason: collision with root package name */
    protected long f9310q;

    /* renamed from: r, reason: collision with root package name */
    protected TimeUnit f9311r;

    /* renamed from: s, reason: collision with root package name */
    protected com.meizu.cloud.pushsdk.b.c.a f9312s;

    /* renamed from: t, reason: collision with root package name */
    protected AtomicBoolean f9313t;

    /* renamed from: u, reason: collision with root package name */
    private final String f9314u;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        protected final String f9315a;

        /* renamed from: b, reason: collision with root package name */
        protected final Context f9316b;

        /* renamed from: m, reason: collision with root package name */
        protected SSLSocketFactory f9327m;

        /* renamed from: n, reason: collision with root package name */
        protected HostnameVerifier f9328n;

        /* renamed from: p, reason: collision with root package name */
        private Class<? extends c> f9330p;

        /* renamed from: c, reason: collision with root package name */
        protected f f9317c = null;

        /* renamed from: d, reason: collision with root package name */
        protected d f9318d = d.POST;

        /* renamed from: e, reason: collision with root package name */
        protected com.meizu.cloud.pushsdk.c.b.a f9319e = com.meizu.cloud.pushsdk.c.b.a.Single;

        /* renamed from: f, reason: collision with root package name */
        protected h f9320f = h.HTTPS;

        /* renamed from: g, reason: collision with root package name */
        protected int f9321g = 5;

        /* renamed from: h, reason: collision with root package name */
        protected int f9322h = 250;

        /* renamed from: i, reason: collision with root package name */
        protected int f9323i = 5;

        /* renamed from: j, reason: collision with root package name */
        protected long f9324j = 40000;

        /* renamed from: k, reason: collision with root package name */
        protected long f9325k = 40000;

        /* renamed from: l, reason: collision with root package name */
        protected TimeUnit f9326l = TimeUnit.SECONDS;

        /* renamed from: o, reason: collision with root package name */
        protected com.meizu.cloud.pushsdk.b.c.a f9329o = new com.meizu.cloud.pushsdk.b.c.e();

        public a(String str, Context context, Class<? extends c> cls) {
            this.f9315a = str;
            this.f9316b = context;
            this.f9330p = cls;
        }

        public a a(int i2) {
            this.f9321g = i2;
            return this;
        }

        public a a(com.meizu.cloud.pushsdk.b.c.a aVar) {
            if (aVar != null) {
                this.f9329o = aVar;
                com.meizu.cloud.pushsdk.c.f.c.c(a.class.getSimpleName(), "set new call " + aVar, new Object[0]);
            }
            return this;
        }

        public a a(com.meizu.cloud.pushsdk.c.b.a aVar) {
            this.f9319e = aVar;
            return this;
        }

        public a a(f fVar) {
            this.f9317c = fVar;
            return this;
        }

        public a b(int i2) {
            this.f9322h = i2;
            return this;
        }

        public a c(int i2) {
            this.f9323i = i2;
            return this;
        }
    }

    public c(a aVar) {
        String simpleName = c.class.getSimpleName();
        this.f9314u = simpleName;
        this.f9296c = com.meizu.cloud.pushsdk.b.c.g.a("application/json; charset=utf-8");
        this.f9313t = new AtomicBoolean(false);
        this.f9300g = aVar.f9318d;
        this.f9299f = aVar.f9317c;
        this.f9297d = aVar.f9316b;
        this.f9301h = aVar.f9319e;
        this.f9302i = aVar.f9320f;
        this.f9303j = aVar.f9327m;
        this.f9304k = aVar.f9328n;
        this.f9306m = aVar.f9321g;
        this.f9307n = aVar.f9323i;
        this.f9308o = aVar.f9322h;
        this.f9309p = aVar.f9324j;
        this.f9310q = aVar.f9325k;
        this.f9305l = aVar.f9315a;
        this.f9311r = aVar.f9326l;
        this.f9312s = aVar.f9329o;
        c();
        com.meizu.cloud.pushsdk.c.f.c.c(simpleName, "Emitter created successfully!", new Object[0]);
    }

    private i a(com.meizu.cloud.pushsdk.c.a.a aVar) {
        a(aVar, "");
        this.f9298e.clearQuery();
        HashMap map = (HashMap) aVar.a();
        for (String str : map.keySet()) {
            this.f9298e.appendQueryParameter(str, (String) map.get(str));
        }
        return new i.a().a(this.f9298e.build().toString()).a().c();
    }

    private i a(ArrayList<com.meizu.cloud.pushsdk.c.a.a> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<com.meizu.cloud.pushsdk.c.a.a> it = arrayList.iterator();
        while (it.hasNext()) {
            com.meizu.cloud.pushsdk.c.a.a next = it.next();
            stringBuffer.append(next.toString());
            arrayList2.add(next.a());
        }
        com.meizu.cloud.pushsdk.c.a.b bVar = new com.meizu.cloud.pushsdk.c.a.b("push_group_data", arrayList2);
        com.meizu.cloud.pushsdk.c.f.c.b(this.f9314u, "final SelfDescribingJson " + bVar, new Object[0]);
        return new i.a().a(this.f9298e.build().toString()).a(j.a(this.f9296c, bVar.toString())).c();
    }

    private void a(com.meizu.cloud.pushsdk.c.a.a aVar, String str) {
        if (str.equals("")) {
            str = com.meizu.cloud.pushsdk.c.f.e.a();
        }
        aVar.a("stm", str);
    }

    private void c() {
        StringBuilder sb;
        String str;
        com.meizu.cloud.pushsdk.c.f.c.a(this.f9314u, "security " + this.f9302i, new Object[0]);
        if (this.f9302i == h.HTTP) {
            sb = new StringBuilder();
            str = DefaultWebClient.HTTP_SCHEME;
        } else {
            sb = new StringBuilder();
            str = DefaultWebClient.HTTPS_SCHEME;
        }
        sb.append(str);
        sb.append(this.f9305l);
        this.f9298e = Uri.parse(sb.toString()).buildUpon();
        if (this.f9300g == d.GET) {
            this.f9298e.appendPath(am.aC);
        } else {
            this.f9298e.appendEncodedPath("push_data_report/mobile");
        }
    }

    public int a(i iVar) {
        k kVarA = null;
        try {
            try {
                com.meizu.cloud.pushsdk.c.f.c.b(this.f9314u, "Sending request: %s", iVar);
                kVarA = this.f9312s.a(iVar);
                return kVarA.a();
            } catch (IOException e2) {
                com.meizu.cloud.pushsdk.c.f.c.a(this.f9314u, "Request sending failed: %s", Log.getStackTraceString(e2));
                a(kVarA);
                return -1;
            }
        } finally {
            a(kVarA);
        }
    }

    public LinkedList<e> a(b bVar) {
        int i2;
        int size = bVar.a().size();
        LinkedList<Long> linkedListB = bVar.b();
        LinkedList<e> linkedList = new LinkedList<>();
        if (this.f9300g == d.GET) {
            for (int i3 = 0; i3 < size; i3++) {
                LinkedList linkedList2 = new LinkedList();
                linkedList2.add(linkedListB.get(i3));
                com.meizu.cloud.pushsdk.c.a.a aVar = bVar.a().get(i3);
                linkedList.add(new e(aVar.b() + ((long) this.f9295b) > this.f9309p, a(aVar), linkedList2));
            }
        } else {
            int iA = 0;
            while (iA < size) {
                LinkedList linkedList3 = new LinkedList();
                ArrayList<com.meizu.cloud.pushsdk.c.a.a> arrayList = new ArrayList<>();
                long j2 = 0;
                int i4 = iA;
                while (i4 < this.f9301h.a() + iA && i4 < size) {
                    com.meizu.cloud.pushsdk.c.a.a aVar2 = bVar.a().get(i4);
                    ArrayList<com.meizu.cloud.pushsdk.c.a.a> arrayList2 = arrayList;
                    long jB = aVar2.b() + this.f9295b;
                    int i5 = this.f9294a;
                    int i6 = iA;
                    LinkedList linkedList4 = linkedList3;
                    if (i5 + jB > this.f9310q) {
                        ArrayList<com.meizu.cloud.pushsdk.c.a.a> arrayList3 = new ArrayList<>();
                        LinkedList linkedList5 = new LinkedList();
                        arrayList3.add(aVar2);
                        linkedList5.add(linkedListB.get(i4));
                        linkedList.add(new e(true, a(arrayList3), linkedList5));
                        i2 = i6;
                        linkedList3 = linkedList4;
                        arrayList = arrayList2;
                    } else {
                        j2 += jB;
                        i2 = i6;
                        if (i5 + j2 + (arrayList2.size() - 1) > this.f9310q) {
                            linkedList.add(new e(false, a(arrayList2), linkedList4));
                            ArrayList<com.meizu.cloud.pushsdk.c.a.a> arrayList4 = new ArrayList<>();
                            linkedList3 = new LinkedList();
                            arrayList4.add(aVar2);
                            linkedList3.add(linkedListB.get(i4));
                            arrayList = arrayList4;
                            j2 = jB;
                        } else {
                            arrayList = arrayList2;
                            arrayList.add(aVar2);
                            linkedList4.add(linkedListB.get(i4));
                            linkedList3 = linkedList4;
                        }
                    }
                    i4++;
                    iA = i2;
                }
                int i7 = iA;
                LinkedList linkedList6 = linkedList3;
                if (!arrayList.isEmpty()) {
                    linkedList.add(new e(false, a(arrayList), linkedList6));
                }
                iA = i7 + this.f9301h.a();
            }
        }
        return linkedList;
    }

    public abstract void a();

    public void a(k kVar) {
        if (kVar != null) {
            try {
                if (kVar.b() != null) {
                    kVar.b().close();
                }
            } catch (Exception unused) {
                com.meizu.cloud.pushsdk.c.f.c.b(this.f9314u, "Unable to close source data", new Object[0]);
            }
        }
    }

    public abstract void a(com.meizu.cloud.pushsdk.c.a.a aVar, boolean z2);

    public boolean a(int i2) {
        return i2 >= 200 && i2 < 300;
    }

    public String b() {
        return this.f9298e.clearQuery().build().toString();
    }
}
