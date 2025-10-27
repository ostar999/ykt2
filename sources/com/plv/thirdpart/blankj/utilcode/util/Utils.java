package com.plv.thirdpart.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.plv.foundationsdk.utils.PLVAppUtils;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes5.dex */
public class Utils {

    @SuppressLint({"StaticFieldLeak"})
    private static Application sApplication;
    public static WeakReference<Activity> sTopActivityWeakRef;
    public static List<Activity> sActivityList = new LinkedList();
    private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() { // from class: com.plv.thirdpart.blankj.utilcode.util.Utils.1
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
            Utils.sActivityList.add(activity);
            Utils.setTopActivityWeakRef(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            Utils.sActivityList.remove(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            Utils.setTopActivityWeakRef(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            Utils.setTopActivityWeakRef(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }
    };

    public Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Application getApp() {
        Application application = sApplication;
        if (application != null) {
            return application;
        }
        if (PLVAppUtils.getApp() != null) {
            return PLVAppUtils.getApp();
        }
        throw new NullPointerException("u should init first");
    }

    public static void init(@NonNull Application application) {
        sApplication = application;
        application.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setTopActivityWeakRef(Activity activity) {
        WeakReference<Activity> weakReference = sTopActivityWeakRef;
        if (weakReference == null || !activity.equals(weakReference.get())) {
            sTopActivityWeakRef = new WeakReference<>(activity);
        }
    }
}
