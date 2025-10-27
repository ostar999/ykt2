package com.google.android.gms.common.api.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
/* loaded from: classes3.dex */
public final class BackgroundDetector implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private static final BackgroundDetector zzat = new BackgroundDetector();
    private final AtomicBoolean zzau = new AtomicBoolean();
    private final AtomicBoolean zzav = new AtomicBoolean();

    @GuardedBy("sInstance")
    private final ArrayList<BackgroundStateChangeListener> zzaw = new ArrayList<>();

    @GuardedBy("sInstance")
    private boolean zzax = false;

    @KeepForSdk
    public interface BackgroundStateChangeListener {
        @KeepForSdk
        void onBackgroundStateChanged(boolean z2);
    }

    @KeepForSdk
    private BackgroundDetector() {
    }

    @KeepForSdk
    public static BackgroundDetector getInstance() {
        return zzat;
    }

    @KeepForSdk
    public static void initialize(Application application) {
        BackgroundDetector backgroundDetector = zzat;
        synchronized (backgroundDetector) {
            if (!backgroundDetector.zzax) {
                application.registerActivityLifecycleCallbacks(backgroundDetector);
                application.registerComponentCallbacks(backgroundDetector);
                backgroundDetector.zzax = true;
            }
        }
    }

    private final void onBackgroundStateChanged(boolean z2) {
        synchronized (zzat) {
            ArrayList<BackgroundStateChangeListener> arrayList = this.zzaw;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                BackgroundStateChangeListener backgroundStateChangeListener = arrayList.get(i2);
                i2++;
                backgroundStateChangeListener.onBackgroundStateChanged(z2);
            }
        }
    }

    @KeepForSdk
    public final void addListener(BackgroundStateChangeListener backgroundStateChangeListener) {
        synchronized (zzat) {
            this.zzaw.add(backgroundStateChangeListener);
        }
    }

    @KeepForSdk
    public final boolean isInBackground() {
        return this.zzau.get();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        boolean zCompareAndSet = this.zzau.compareAndSet(true, false);
        this.zzav.set(true);
        if (zCompareAndSet) {
            onBackgroundStateChanged(false);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        boolean zCompareAndSet = this.zzau.compareAndSet(true, false);
        this.zzav.set(true);
        if (zCompareAndSet) {
            onBackgroundStateChanged(false);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
    }

    @Override // android.content.ComponentCallbacks2
    public final void onTrimMemory(int i2) {
        if (i2 == 20 && this.zzau.compareAndSet(false, true)) {
            this.zzav.set(true);
            onBackgroundStateChanged(true);
        }
    }

    @KeepForSdk
    @TargetApi(16)
    public final boolean readCurrentStateIfPossible(boolean z2) {
        if (!this.zzav.get()) {
            if (!PlatformVersion.isAtLeastJellyBean()) {
                return z2;
            }
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (!this.zzav.getAndSet(true) && runningAppProcessInfo.importance > 100) {
                this.zzau.set(true);
            }
        }
        return isInBackground();
    }
}
