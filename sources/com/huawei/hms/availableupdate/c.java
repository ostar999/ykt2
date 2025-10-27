package com.huawei.hms.availableupdate;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: b, reason: collision with root package name */
    public static final c f7530b = new c();

    /* renamed from: c, reason: collision with root package name */
    public static final Object f7531c = new Object();

    /* renamed from: a, reason: collision with root package name */
    public final List<Activity> f7532a = new ArrayList(1);

    public void a(Activity activity) {
        synchronized (f7531c) {
            for (Activity activity2 : this.f7532a) {
                if (activity2 != null && activity2 != activity && !activity2.isFinishing()) {
                    activity2.finish();
                }
            }
            this.f7532a.add(activity);
        }
    }

    public void b(Activity activity) {
        synchronized (f7531c) {
            this.f7532a.remove(activity);
        }
    }
}
