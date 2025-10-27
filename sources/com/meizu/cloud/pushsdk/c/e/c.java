package com.meizu.cloud.pushsdk.c.e;

import android.content.Context;
import com.meizu.cloud.pushsdk.PushManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public abstract class c {

    /* renamed from: n, reason: collision with root package name */
    private static final String f9401n = "c";

    /* renamed from: b, reason: collision with root package name */
    protected com.meizu.cloud.pushsdk.c.b.c f9403b;

    /* renamed from: c, reason: collision with root package name */
    protected b f9404c;

    /* renamed from: d, reason: collision with root package name */
    protected com.meizu.cloud.pushsdk.c.e.a f9405d;

    /* renamed from: e, reason: collision with root package name */
    protected String f9406e;

    /* renamed from: f, reason: collision with root package name */
    protected String f9407f;

    /* renamed from: g, reason: collision with root package name */
    protected boolean f9408g;

    /* renamed from: h, reason: collision with root package name */
    protected com.meizu.cloud.pushsdk.c.f.b f9409h;

    /* renamed from: i, reason: collision with root package name */
    protected boolean f9410i;

    /* renamed from: j, reason: collision with root package name */
    protected long f9411j;

    /* renamed from: k, reason: collision with root package name */
    protected int f9412k;

    /* renamed from: l, reason: collision with root package name */
    protected TimeUnit f9413l;

    /* renamed from: a, reason: collision with root package name */
    protected final String f9402a = PushManager.TAG;

    /* renamed from: m, reason: collision with root package name */
    protected AtomicBoolean f9414m = new AtomicBoolean(true);

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        protected final com.meizu.cloud.pushsdk.c.b.c f9415a;

        /* renamed from: b, reason: collision with root package name */
        protected final String f9416b;

        /* renamed from: c, reason: collision with root package name */
        protected final String f9417c;

        /* renamed from: d, reason: collision with root package name */
        protected final Context f9418d;

        /* renamed from: e, reason: collision with root package name */
        protected b f9419e = null;

        /* renamed from: f, reason: collision with root package name */
        protected boolean f9420f = false;

        /* renamed from: g, reason: collision with root package name */
        protected com.meizu.cloud.pushsdk.c.f.b f9421g = com.meizu.cloud.pushsdk.c.f.b.OFF;

        /* renamed from: h, reason: collision with root package name */
        protected boolean f9422h = false;

        /* renamed from: i, reason: collision with root package name */
        protected long f9423i = 600;

        /* renamed from: j, reason: collision with root package name */
        protected long f9424j = 300;

        /* renamed from: k, reason: collision with root package name */
        protected long f9425k = 15;

        /* renamed from: l, reason: collision with root package name */
        protected int f9426l = 10;

        /* renamed from: m, reason: collision with root package name */
        protected TimeUnit f9427m = TimeUnit.SECONDS;

        /* renamed from: n, reason: collision with root package name */
        private Class<? extends c> f9428n;

        public a(com.meizu.cloud.pushsdk.c.b.c cVar, String str, String str2, Context context, Class<? extends c> cls) {
            this.f9415a = cVar;
            this.f9416b = str;
            this.f9417c = str2;
            this.f9418d = context;
            this.f9428n = cls;
        }

        public a a(int i2) {
            this.f9426l = i2;
            return this;
        }

        public a a(b bVar) {
            this.f9419e = bVar;
            return this;
        }

        public a a(com.meizu.cloud.pushsdk.c.f.b bVar) {
            this.f9421g = bVar;
            return this;
        }

        public a a(Boolean bool) {
            this.f9420f = bool.booleanValue();
            return this;
        }
    }

    public c(a aVar) {
        this.f9403b = aVar.f9415a;
        this.f9407f = aVar.f9417c;
        this.f9408g = aVar.f9420f;
        this.f9406e = aVar.f9416b;
        this.f9404c = aVar.f9419e;
        this.f9409h = aVar.f9421g;
        boolean z2 = aVar.f9422h;
        this.f9410i = z2;
        this.f9411j = aVar.f9425k;
        int i2 = aVar.f9426l;
        this.f9412k = i2 < 2 ? 2 : i2;
        this.f9413l = aVar.f9427m;
        if (z2) {
            this.f9405d = new com.meizu.cloud.pushsdk.c.e.a(aVar.f9423i, aVar.f9424j, aVar.f9427m, aVar.f9418d);
        }
        com.meizu.cloud.pushsdk.c.f.c.a(aVar.f9421g);
        com.meizu.cloud.pushsdk.c.f.c.c(f9401n, "Tracker created successfully.", new Object[0]);
    }

    private com.meizu.cloud.pushsdk.c.a.b a(List<com.meizu.cloud.pushsdk.c.a.b> list) {
        if (this.f9410i) {
            list.add(this.f9405d.a());
        }
        b bVar = this.f9404c;
        if (bVar != null) {
            if (!bVar.a().isEmpty()) {
                list.add(new com.meizu.cloud.pushsdk.c.a.b("geolocation", this.f9404c.a()));
            }
            if (!this.f9404c.b().isEmpty()) {
                list.add(new com.meizu.cloud.pushsdk.c.a.b("mobileinfo", this.f9404c.b()));
            }
        }
        LinkedList linkedList = new LinkedList();
        Iterator<com.meizu.cloud.pushsdk.c.a.b> it = list.iterator();
        while (it.hasNext()) {
            linkedList.add(it.next().a());
        }
        return new com.meizu.cloud.pushsdk.c.a.b("push_extra_info", linkedList);
    }

    private void a(com.meizu.cloud.pushsdk.c.a.c cVar, List<com.meizu.cloud.pushsdk.c.a.b> list, boolean z2) {
        if (this.f9404c != null) {
            cVar.a(new HashMap(this.f9404c.c()));
            cVar.a("et", a(list).a());
        }
        com.meizu.cloud.pushsdk.c.f.c.c(f9401n, "Adding new payload to event storage: %s", cVar);
        this.f9403b.a(cVar, z2);
    }

    public void a() {
        if (this.f9414m.get()) {
            b().a();
        }
    }

    public void a(com.meizu.cloud.pushsdk.c.c.b bVar, boolean z2) {
        if (this.f9414m.get()) {
            a(bVar.e(), bVar.a(), z2);
        }
    }

    public void a(b bVar) {
        this.f9404c = bVar;
    }

    public com.meizu.cloud.pushsdk.c.b.c b() {
        return this.f9403b;
    }
}
