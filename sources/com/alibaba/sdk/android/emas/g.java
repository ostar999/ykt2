package com.alibaba.sdk.android.emas;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
class g implements Application.ActivityLifecycleCallbacks {

    /* renamed from: c, reason: collision with root package name */
    private List<a> f2695c;

    /* renamed from: d, reason: collision with root package name */
    private int f2696d = 0;

    /* renamed from: a, reason: collision with root package name */
    private boolean f2694a = false;

    public interface a {
        void b();

        void c();
    }

    public void a(a aVar) {
        if (this.f2695c == null) {
            this.f2695c = new ArrayList();
        }
        this.f2695c.add(aVar);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        this.f2696d++;
        if (this.f2694a) {
            return;
        }
        this.f2694a = true;
        List<a> list = this.f2695c;
        if (list != null) {
            Iterator<a> it = list.iterator();
            while (it.hasNext()) {
                it.next().b();
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        int i2 = this.f2696d - 1;
        this.f2696d = i2;
        if (i2 == 0) {
            this.f2694a = false;
            List<a> list = this.f2695c;
            if (list != null) {
                Iterator<a> it = list.iterator();
                while (it.hasNext()) {
                    it.next().c();
                }
            }
        }
    }
}
