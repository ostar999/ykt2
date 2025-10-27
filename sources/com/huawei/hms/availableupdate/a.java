package com.huawei.hms.availableupdate;

import android.app.Activity;
import com.huawei.hms.support.log.HMSLog;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: b, reason: collision with root package name */
    public static final a f7524b = new a();

    /* renamed from: a, reason: collision with root package name */
    public WeakReference<Activity> f7525a;

    public boolean a(Activity activity) {
        HMSLog.i("UpdateAdapterMgr", "onActivityCreate");
        Activity activityA = a();
        if (activityA == null || activityA.isFinishing()) {
            this.f7525a = new WeakReference<>(activity);
            return true;
        }
        activity.finish();
        HMSLog.i("UpdateAdapterMgr", "finish one");
        return false;
    }

    public void b(Activity activity) {
        HMSLog.i("UpdateAdapterMgr", "onActivityDestroy");
        Activity activityA = a();
        if (activity == null || !activity.equals(activityA)) {
            return;
        }
        HMSLog.i("UpdateAdapterMgr", "reset");
        this.f7525a = null;
    }

    public final Activity a() {
        WeakReference<Activity> weakReference = this.f7525a;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }
}
