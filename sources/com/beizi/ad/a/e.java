package com.beizi.ad.a;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.beizi.ad.a.a.i;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class e {

    /* renamed from: f, reason: collision with root package name */
    private String f3721f;

    /* renamed from: i, reason: collision with root package name */
    private c f3724i;

    /* renamed from: a, reason: collision with root package name */
    private String f3716a = "OnLineStateClass";

    /* renamed from: b, reason: collision with root package name */
    private int f3717b = 0;

    /* renamed from: c, reason: collision with root package name */
    private boolean f3718c = true;

    /* renamed from: d, reason: collision with root package name */
    private boolean f3719d = false;

    /* renamed from: e, reason: collision with root package name */
    private boolean f3720e = false;

    /* renamed from: g, reason: collision with root package name */
    private Map<String, String> f3722g = new HashMap();

    /* renamed from: h, reason: collision with root package name */
    private long f3723h = 0;

    public static /* synthetic */ int d(e eVar) {
        int i2 = eVar.f3717b;
        eVar.f3717b = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int h(e eVar) {
        int i2 = eVar.f3717b;
        eVar.f3717b = i2 - 1;
        return i2;
    }

    public void a(Context context) {
        Application application = (Application) context.getApplicationContext();
        this.f3723h = System.currentTimeMillis() / 1000;
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: com.beizi.ad.a.e.1
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
                e.this.f3721f = activity.getClass().getSimpleName();
                e.this.f3722g.put(e.this.f3721f, e.this.f3721f);
                e.this.f3718c = true;
                e.this.f3719d = false;
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
                e.this.f3722g.remove(activity.getClass().getSimpleName());
                if (e.this.f3722g.size() == 0 && e.this.f3718c) {
                    long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                    if (e.this.f3724i != null) {
                        long unused = e.this.f3723h;
                        e.this.f3724i.a(jCurrentTimeMillis, e.this.f3723h);
                        e.this.f3723h = System.currentTimeMillis() / 1000;
                    }
                    e.this.f3718c = false;
                }
                if (e.this.f3722g.size() == 0) {
                    e.this.f3720e = true;
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
                e.this.f3719d = !activity.getClass().getSimpleName().equals(e.this.f3721f);
                e.this.f3721f = activity.getClass().getSimpleName();
                if (!e.this.f3718c || e.this.f3720e) {
                    e.this.f3720e = false;
                    if (e.this.f3724i != null) {
                        e.this.f3724i.a();
                    }
                    e.this.f3723h = System.currentTimeMillis() / 1000;
                    e.this.f3718c = true;
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                i.a(e.this.f3716a, "onActivityStarted");
                e.d(e.this);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                i.a(e.this.f3716a, "onActivityStopped");
                e.h(e.this);
                if (activity.getClass().getSimpleName().equals(e.this.f3721f)) {
                    if (!e.this.f3719d || e.this.f3722g.size() == 1) {
                        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                        if (e.this.f3724i != null) {
                            long unused = e.this.f3723h;
                            e.this.f3724i.a(e.this.f3723h, jCurrentTimeMillis);
                            e.this.f3723h = System.currentTimeMillis() / 1000;
                        }
                        e.this.f3718c = false;
                    }
                }
            }
        });
    }

    public void a(c cVar) {
        this.f3724i = cVar;
    }
}
