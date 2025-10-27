package com.meizu.cloud.pushsdk.c.e;

import android.content.Context;
import com.meizu.cloud.pushsdk.c.f.e;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static String f9378a = "a";

    /* renamed from: b, reason: collision with root package name */
    private String f9379b;

    /* renamed from: c, reason: collision with root package name */
    private String f9380c;

    /* renamed from: d, reason: collision with root package name */
    private String f9381d;

    /* renamed from: e, reason: collision with root package name */
    private int f9382e;

    /* renamed from: f, reason: collision with root package name */
    private String f9383f = "SQLITE";

    /* renamed from: g, reason: collision with root package name */
    private AtomicBoolean f9384g = new AtomicBoolean(false);

    /* renamed from: h, reason: collision with root package name */
    private long f9385h;

    /* renamed from: i, reason: collision with root package name */
    private long f9386i;

    /* renamed from: j, reason: collision with root package name */
    private long f9387j;

    /* renamed from: k, reason: collision with root package name */
    private Context f9388k;

    public a(long j2, long j3, TimeUnit timeUnit, Context context) {
        this.f9380c = null;
        this.f9382e = 0;
        this.f9386i = timeUnit.toMillis(j2);
        this.f9387j = timeUnit.toMillis(j3);
        this.f9388k = context;
        Map mapF = f();
        if (mapF == null) {
            this.f9379b = e.b();
        } else {
            try {
                String string = mapF.get("userId").toString();
                String string2 = mapF.get(PLVLinkMicManager.SESSION_ID).toString();
                int iIntValue = ((Integer) mapF.get("sessionIndex")).intValue();
                this.f9379b = string;
                this.f9382e = iIntValue;
                this.f9380c = string2;
            } catch (Exception e2) {
                com.meizu.cloud.pushsdk.c.f.c.a(f9378a, "Exception occurred retrieving session info from file: %s", e2.getMessage());
            }
        }
        d();
        g();
        com.meizu.cloud.pushsdk.c.f.c.c(f9378a, "Tracker Session Object created.", new Object[0]);
    }

    private void d() {
        this.f9381d = this.f9380c;
        this.f9380c = e.b();
        this.f9382e++;
        com.meizu.cloud.pushsdk.c.f.c.b(f9378a, "Session information is updated:", new Object[0]);
        com.meizu.cloud.pushsdk.c.f.c.b(f9378a, " + Session ID: %s", this.f9380c);
        com.meizu.cloud.pushsdk.c.f.c.b(f9378a, " + Previous Session ID: %s", this.f9381d);
        com.meizu.cloud.pushsdk.c.f.c.b(f9378a, " + Session Index: %s", Integer.valueOf(this.f9382e));
        e();
    }

    private boolean e() {
        return com.meizu.cloud.pushsdk.c.f.a.a("snowplow_session_vars", c(), this.f9388k);
    }

    private Map f() {
        return com.meizu.cloud.pushsdk.c.f.a.a("snowplow_session_vars", this.f9388k);
    }

    private void g() {
        this.f9385h = System.currentTimeMillis();
    }

    public com.meizu.cloud.pushsdk.c.a.b a() {
        com.meizu.cloud.pushsdk.c.f.c.c(f9378a, "Getting session context...", new Object[0]);
        g();
        return new com.meizu.cloud.pushsdk.c.a.b("client_session", c());
    }

    public void b() {
        com.meizu.cloud.pushsdk.c.f.c.b(f9378a, "Checking and updating session information.", new Object[0]);
        if (e.a(this.f9385h, System.currentTimeMillis(), this.f9384g.get() ? this.f9387j : this.f9386i)) {
            return;
        }
        d();
        g();
    }

    public Map c() {
        HashMap map = new HashMap();
        map.put("userId", this.f9379b);
        map.put(PLVLinkMicManager.SESSION_ID, this.f9380c);
        map.put("previousSessionId", this.f9381d);
        map.put("sessionIndex", Integer.valueOf(this.f9382e));
        map.put("storageMechanism", this.f9383f);
        return map;
    }
}
