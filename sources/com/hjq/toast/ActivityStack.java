package com.hjq.toast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/* loaded from: classes4.dex */
final class ActivityStack implements Application.ActivityLifecycleCallbacks {

    @SuppressLint({"StaticFieldLeak"})
    private static volatile ActivityStack sInstance;
    private Activity mForegroundActivity;

    private ActivityStack() {
    }

    public static ActivityStack getInstance() {
        if (sInstance == null) {
            synchronized (ActivityStack.class) {
                if (sInstance == null) {
                    sInstance = new ActivityStack();
                }
            }
        }
        return sInstance;
    }

    public Activity getForegroundActivity() {
        return this.mForegroundActivity;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        if (this.mForegroundActivity != activity) {
            return;
        }
        this.mForegroundActivity = null;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.mForegroundActivity = activity;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    public void register(Application application) {
        if (application == null) {
            return;
        }
        application.registerActivityLifecycleCallbacks(this);
    }
}
