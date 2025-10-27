package com.huawei.hms.availableupdate;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: c, reason: collision with root package name */
    public static final b f7526c = new b();

    /* renamed from: d, reason: collision with root package name */
    public static final Object f7527d = new Object();

    /* renamed from: a, reason: collision with root package name */
    public final AtomicBoolean f7528a = new AtomicBoolean(false);

    /* renamed from: b, reason: collision with root package name */
    public List<Activity> f7529b = new ArrayList(1);

    public void a(Activity activity) {
        synchronized (f7527d) {
            for (Activity activity2 : this.f7529b) {
                if (activity2 != null && activity2 != activity && !activity2.isFinishing()) {
                    activity2.finish();
                }
            }
            this.f7529b.add(activity);
        }
    }

    public void b(Activity activity) {
        synchronized (f7527d) {
            this.f7529b.remove(activity);
        }
    }

    public void a(boolean z2) {
        this.f7528a.set(z2);
    }

    public AtomicBoolean a() {
        return this.f7528a;
    }
}
