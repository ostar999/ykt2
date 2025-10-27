package com.huawei.hms.api;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
class a {

    /* renamed from: b, reason: collision with root package name */
    static final a f7519b = new a();

    /* renamed from: c, reason: collision with root package name */
    private static final Object f7520c = new Object();

    /* renamed from: a, reason: collision with root package name */
    List<Activity> f7521a = new ArrayList(1);

    public void a(Activity activity) {
        synchronized (f7520c) {
            for (Activity activity2 : this.f7521a) {
                if (activity2 != null && activity2 != activity && !activity2.isFinishing()) {
                    activity2.finish();
                }
            }
            this.f7521a.add(activity);
        }
    }

    public void b(Activity activity) {
        synchronized (f7520c) {
            this.f7521a.remove(activity);
        }
    }
}
