package com.tencent.tbs.one.impl.e;

import android.content.Context;
import android.os.Bundle;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneComponent;
import com.tencent.tbs.one.TBSOneDebugger;
import com.tencent.tbs.one.TBSOneDelegate;
import com.tencent.tbs.one.TBSOneManager;
import com.tencent.tbs.one.TBSOneOnlineService;
import com.tencent.tbs.one.impl.a.m;
import com.tencent.tbs.one.impl.common.Statistics;
import com.tencent.tbs.one.impl.common.d;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes6.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    public final Context f22204a;

    /* renamed from: b, reason: collision with root package name */
    public final String f22205b;

    /* renamed from: c, reason: collision with root package name */
    public final File f22206c;

    /* renamed from: d, reason: collision with root package name */
    public final b f22207d = new b(this);

    /* renamed from: e, reason: collision with root package name */
    public final Map<String, Object> f22208e = new ConcurrentHashMap();

    /* renamed from: f, reason: collision with root package name */
    public final Map<String, com.tencent.tbs.one.impl.c.b> f22209f = new ConcurrentHashMap();

    /* renamed from: g, reason: collision with root package name */
    public final Map<String, List<com.tencent.tbs.one.impl.common.g>> f22210g = new ConcurrentHashMap();

    /* renamed from: h, reason: collision with root package name */
    public volatile TBSOneManager.Policy f22211h = TBSOneManager.Policy.AUTO;

    /* renamed from: i, reason: collision with root package name */
    public volatile boolean f22212i = false;

    /* renamed from: j, reason: collision with root package name */
    public volatile long f22213j = 86400000;

    /* renamed from: k, reason: collision with root package name */
    public volatile boolean f22214k = true;

    /* renamed from: l, reason: collision with root package name */
    public boolean f22215l = false;

    /* renamed from: m, reason: collision with root package name */
    public boolean f22216m = false;

    /* renamed from: n, reason: collision with root package name */
    public TBSOneDelegate f22217n;

    /* renamed from: o, reason: collision with root package name */
    public com.tencent.tbs.one.impl.common.d f22218o;

    /* renamed from: p, reason: collision with root package name */
    public com.tencent.tbs.one.impl.a.b<com.tencent.tbs.one.impl.common.d> f22219p;

    public h(Context context, String str) {
        if (context.getApplicationContext() != null) {
            this.f22204a = context.getApplicationContext();
        } else {
            this.f22204a = context;
        }
        this.f22205b = str;
        File fileA = com.tencent.tbs.one.impl.common.f.a(context.getDir("tbs", 0), str);
        this.f22206c = fileA;
        com.tencent.tbs.one.impl.a.d.e(fileA);
        com.tencent.tbs.one.impl.a.d.e(com.tencent.tbs.one.impl.common.f.d(fileA));
    }

    private com.tencent.tbs.one.impl.c.b h(String str) {
        com.tencent.tbs.one.impl.c.b bVar = this.f22209f.get(str);
        if (bVar != null) {
            return bVar;
        }
        com.tencent.tbs.one.impl.c.b bVar2 = new com.tencent.tbs.one.impl.c.b(this, str);
        this.f22209f.put(str, bVar2);
        return bVar2;
    }

    public TBSOneManager.Policy a() {
        return this.f22211h;
    }

    public com.tencent.tbs.one.impl.a.b<e<com.tencent.tbs.one.impl.common.d>> a(Bundle bundle, m<e<com.tencent.tbs.one.impl.common.d>> mVar) {
        com.tencent.tbs.one.impl.a.g.a("[%s] Installing DEPS", this.f22205b);
        com.tencent.tbs.one.impl.e.c.b bVar = new com.tencent.tbs.one.impl.e.c.b(this.f22204a, this.f22205b, com.tencent.tbs.one.impl.common.f.b(this.f22206c));
        bVar.a((m) mVar);
        return bVar;
    }

    public com.tencent.tbs.one.impl.a.b<e<File>> a(Bundle bundle, d.a aVar, m<e<File>> mVar) {
        com.tencent.tbs.one.impl.a.g.a("[%s] Installing component %s, version: %d", this.f22205b, aVar.f21992a, Integer.valueOf(aVar.f21994c));
        File fileA = a(aVar.f21992a, aVar.f21994c);
        com.tencent.tbs.one.impl.a.d.e(fileA);
        com.tencent.tbs.one.impl.e.c.a aVar2 = new com.tencent.tbs.one.impl.e.c.a(this.f22204a, this.f22205b, aVar, fileA, bundle, "");
        aVar2.a((m) mVar);
        return aVar2;
    }

    public final File a(String str) {
        return com.tencent.tbs.one.impl.common.f.b(this.f22206c, str);
    }

    public final File a(String str, int i2) {
        return new File(com.tencent.tbs.one.impl.common.f.b(this.f22206c, str), String.valueOf(i2));
    }

    public void a(Bundle bundle, String str, TBSOneCallback<File> tBSOneCallback) {
        h(str).a(bundle, tBSOneCallback);
    }

    public void a(e<com.tencent.tbs.one.impl.common.d> eVar) {
        com.tencent.tbs.one.impl.a.g.a("[%s] Finished loading DEPS#%d from %s", this.f22205b, Integer.valueOf(eVar.f22177b.f21989a), eVar.f22176a);
        this.f22218o = eVar.f22177b;
    }

    public void a(String str, Bundle bundle, TBSOneCallback<TBSOneComponent> tBSOneCallback) {
        h(str).b(bundle, tBSOneCallback);
    }

    public void a(String str, Object obj) {
        if (str == null) {
            Statistics.create(Statistics.EVENT_ERROR, 510).report();
        } else if (obj == null) {
            Statistics.create(Statistics.EVENT_ERROR, 510).report();
        } else {
            this.f22208e.put(str, obj);
        }
    }

    public void a(boolean z2) {
        this.f22212i = z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean a(java.lang.String r2, java.lang.String r3) {
        /*
            r1 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r2)
            java.lang.String r2 = "."
            r0.append(r2)
            r0.append(r3)
            java.lang.String r2 = r0.toString()
            java.util.Map<java.lang.String, java.lang.Object> r0 = r1.f22208e
            boolean r0 = r0.containsKey(r2)
            if (r0 == 0) goto L2d
            java.util.Map<java.lang.String, java.lang.Object> r0 = r1.f22208e
            java.lang.Object r2 = r0.get(r2)
            boolean r0 = r2 instanceof java.lang.Boolean
            if (r0 == 0) goto L2d
        L26:
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            return r2
        L2d:
            java.util.Map<java.lang.String, java.lang.Object> r2 = r1.f22208e
            boolean r2 = r2.containsKey(r3)
            if (r2 == 0) goto L40
            java.util.Map<java.lang.String, java.lang.Object> r2 = r1.f22208e
            java.lang.Object r2 = r2.get(r3)
            boolean r3 = r2 instanceof java.lang.Boolean
            if (r3 == 0) goto L40
            goto L26
        L40:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.one.impl.e.h.a(java.lang.String, java.lang.String):boolean");
    }

    public void b(String str, Bundle bundle, TBSOneCallback<File> tBSOneCallback) {
        h(str).a(bundle, tBSOneCallback);
    }

    public final boolean b() {
        TBSOneManager.Policy policyA = a();
        return (policyA == TBSOneManager.Policy.BUILTIN_ONLY || policyA == TBSOneManager.Policy.LOCAL_ONLY) ? false : true;
    }

    public boolean b(String str) {
        return true;
    }

    public TBSOneOnlineService c() {
        return null;
    }

    public int[] c(String str) {
        return new int[]{-1};
    }

    public TBSOneDebugger d() {
        return null;
    }

    public final void d(String str) {
        com.tencent.tbs.one.impl.c.b bVarE = e(str);
        if (bVarE != null) {
            bVarE.b();
        }
    }

    public final com.tencent.tbs.one.impl.c.b e(String str) {
        return this.f22209f.get(str);
    }

    public void e() {
    }

    public final com.tencent.tbs.one.impl.c.a f(String str) {
        com.tencent.tbs.one.impl.c.b bVarE = e(str);
        if (bVarE == null) {
            return null;
        }
        return bVarE.f21894j;
    }

    public final Object g(String str) {
        return this.f22208e.get(str);
    }
}
