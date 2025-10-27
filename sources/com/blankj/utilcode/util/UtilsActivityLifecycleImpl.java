package com.blankj.utilcode.util;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.Utils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
final class UtilsActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {
    static final UtilsActivityLifecycleImpl INSTANCE = new UtilsActivityLifecycleImpl();
    private static final Activity STUB = new Activity();
    private final LinkedList<Activity> mActivityList = new LinkedList<>();
    private final List<Utils.OnAppStatusChangedListener> mStatusListeners = new CopyOnWriteArrayList();
    private final Map<Activity, List<Utils.ActivityLifecycleCallbacks>> mActivityLifecycleCallbacksMap = new ConcurrentHashMap();
    private int mForegroundCount = 0;
    private int mConfigCount = 0;
    private boolean mIsBackground = false;

    /* JADX INFO: Access modifiers changed from: private */
    public void addActivityLifecycleCallbacksInner(Activity activity, Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        List<Utils.ActivityLifecycleCallbacks> copyOnWriteArrayList = this.mActivityLifecycleCallbacksMap.get(activity);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            this.mActivityLifecycleCallbacksMap.put(activity, copyOnWriteArrayList);
        } else if (copyOnWriteArrayList.contains(activityLifecycleCallbacks)) {
            return;
        }
        copyOnWriteArrayList.add(activityLifecycleCallbacks);
    }

    private void consumeActivityLifecycleCallbacks(Activity activity, Lifecycle.Event event) {
        consumeLifecycle(activity, event, this.mActivityLifecycleCallbacksMap.get(activity));
        consumeLifecycle(activity, event, this.mActivityLifecycleCallbacksMap.get(STUB));
    }

    private void consumeLifecycle(Activity activity, Lifecycle.Event event, List<Utils.ActivityLifecycleCallbacks> list) {
        if (list == null) {
            return;
        }
        for (Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks : list) {
            activityLifecycleCallbacks.onLifecycleChanged(activity, event);
            if (event.equals(Lifecycle.Event.ON_CREATE)) {
                activityLifecycleCallbacks.onActivityCreated(activity);
            } else if (event.equals(Lifecycle.Event.ON_START)) {
                activityLifecycleCallbacks.onActivityStarted(activity);
            } else if (event.equals(Lifecycle.Event.ON_RESUME)) {
                activityLifecycleCallbacks.onActivityResumed(activity);
            } else if (event.equals(Lifecycle.Event.ON_PAUSE)) {
                activityLifecycleCallbacks.onActivityPaused(activity);
            } else if (event.equals(Lifecycle.Event.ON_STOP)) {
                activityLifecycleCallbacks.onActivityStopped(activity);
            } else if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                activityLifecycleCallbacks.onActivityDestroyed(activity);
            }
        }
        if (event.equals(Lifecycle.Event.ON_DESTROY)) {
            this.mActivityLifecycleCallbacksMap.remove(activity);
        }
    }

    private List<Activity> getActivitiesByReflect() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Object activityThread;
        LinkedList linkedList = new LinkedList();
        Activity activity = null;
        try {
            activityThread = getActivityThread();
        } catch (Exception e2) {
            Log.e("UtilsActivityLifecycle", "getActivitiesByReflect: " + e2.getMessage());
        }
        if (activityThread == null) {
            return linkedList;
        }
        Field declaredField = activityThread.getClass().getDeclaredField("mActivities");
        declaredField.setAccessible(true);
        Object obj = declaredField.get(activityThread);
        if (!(obj instanceof Map)) {
            return linkedList;
        }
        for (Object obj2 : ((Map) obj).values()) {
            Class<?> cls = obj2.getClass();
            Field declaredField2 = cls.getDeclaredField(PushConstants.INTENT_ACTIVITY_NAME);
            declaredField2.setAccessible(true);
            Activity activity2 = (Activity) declaredField2.get(obj2);
            if (activity == null) {
                Field declaredField3 = cls.getDeclaredField("paused");
                declaredField3.setAccessible(true);
                if (declaredField3.getBoolean(obj2)) {
                    linkedList.addFirst(activity2);
                } else {
                    activity = activity2;
                }
            } else {
                linkedList.addFirst(activity2);
            }
        }
        if (activity != null) {
            linkedList.addFirst(activity);
        }
        return linkedList;
    }

    private Object getActivityThread() throws NoSuchFieldException, SecurityException {
        Object activityThreadInActivityThreadStaticField = getActivityThreadInActivityThreadStaticField();
        return activityThreadInActivityThreadStaticField != null ? activityThreadInActivityThreadStaticField : getActivityThreadInActivityThreadStaticMethod();
    }

    private Object getActivityThreadInActivityThreadStaticField() throws NoSuchFieldException, SecurityException {
        try {
            Field declaredField = Class.forName("android.app.ActivityThread").getDeclaredField("sCurrentActivityThread");
            declaredField.setAccessible(true);
            return declaredField.get(null);
        } catch (Exception e2) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticField: " + e2.getMessage());
            return null;
        }
    }

    private Object getActivityThreadInActivityThreadStaticMethod() {
        try {
            return Class.forName("android.app.ActivityThread").getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e2) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticMethod: " + e2.getMessage());
            return null;
        }
    }

    private void postStatus(Activity activity, boolean z2) {
        if (this.mStatusListeners.isEmpty()) {
            return;
        }
        for (Utils.OnAppStatusChangedListener onAppStatusChangedListener : this.mStatusListeners) {
            if (z2) {
                onAppStatusChangedListener.onForeground(activity);
            } else {
                onAppStatusChangedListener.onBackground(activity);
            }
        }
    }

    private void processHideSoftInputOnActivityDestroy(final Activity activity, boolean z2) {
        try {
            if (z2) {
                Window window = activity.getWindow();
                window.getDecorView().setTag(-123, Integer.valueOf(window.getAttributes().softInputMode));
                window.setSoftInputMode(3);
            } else {
                final Object tag = activity.getWindow().getDecorView().getTag(-123);
                if (!(tag instanceof Integer)) {
                } else {
                    UtilsBridge.runOnUiThreadDelayed(new Runnable() { // from class: com.blankj.utilcode.util.UtilsActivityLifecycleImpl.4
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                Window window2 = activity.getWindow();
                                if (window2 != null) {
                                    window2.setSoftInputMode(((Integer) tag).intValue());
                                }
                            } catch (Exception unused) {
                            }
                        }
                    }, 100L);
                }
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeActivityLifecycleCallbacksInner(Activity activity, Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        List<Utils.ActivityLifecycleCallbacks> list = this.mActivityLifecycleCallbacksMap.get(activity);
        if (list == null || list.isEmpty()) {
            return;
        }
        list.remove(activityLifecycleCallbacks);
    }

    private static void setAnimatorsEnabled() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (Build.VERSION.SDK_INT < 26 || !ValueAnimator.areAnimatorsEnabled()) {
            try {
                Field declaredField = ValueAnimator.class.getDeclaredField("sDurationScale");
                declaredField.setAccessible(true);
                if (((Float) declaredField.get(null)).floatValue() == 0.0f) {
                    declaredField.set(null, Float.valueOf(1.0f));
                    Log.i("UtilsActivityLifecycle", "setAnimatorsEnabled: Animators are enabled now!");
                }
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (NoSuchFieldException e3) {
                e3.printStackTrace();
            }
        }
    }

    private void setTopActivity(Activity activity) {
        if (!this.mActivityList.contains(activity)) {
            this.mActivityList.addFirst(activity);
        } else {
            if (this.mActivityList.getFirst().equals(activity)) {
                return;
            }
            this.mActivityList.remove(activity);
            this.mActivityList.addFirst(activity);
        }
    }

    public void addActivityLifecycleCallbacks(Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        addActivityLifecycleCallbacks(STUB, activityLifecycleCallbacks);
    }

    public void addOnAppStatusChangedListener(Utils.OnAppStatusChangedListener onAppStatusChangedListener) {
        this.mStatusListeners.add(onAppStatusChangedListener);
    }

    public List<Activity> getActivityList() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (!this.mActivityList.isEmpty()) {
            return new LinkedList(this.mActivityList);
        }
        this.mActivityList.addAll(getActivitiesByReflect());
        return new LinkedList(this.mActivityList);
    }

    public Application getApplicationByReflect() throws ClassNotFoundException {
        Object objInvoke;
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object activityThread = getActivityThread();
            if (activityThread == null || (objInvoke = cls.getMethod("getApplication", new Class[0]).invoke(activityThread, new Object[0])) == null) {
                return null;
            }
            return (Application) objInvoke;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public Activity getTopActivity() {
        for (Activity activity : getActivityList()) {
            if (UtilsBridge.isActivityAlive(activity)) {
                return activity;
            }
        }
        return null;
    }

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(this);
    }

    public boolean isAppForeground() {
        return !this.mIsBackground;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(@NonNull Activity activity, Bundle bundle) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (this.mActivityList.size() == 0) {
            postStatus(activity, true);
        }
        LanguageUtils.applyLanguage(activity);
        setAnimatorsEnabled();
        setTopActivity(activity);
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_CREATE);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(@NonNull Activity activity) {
        this.mActivityList.remove(activity);
        UtilsBridge.fixSoftInputLeaks(activity);
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_DESTROY);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(@NonNull Activity activity) {
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_PAUSE);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPostCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPostDestroyed(@NonNull Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPostPaused(@NonNull Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPostResumed(@NonNull Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPostSaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPostStarted(@NonNull Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPostStopped(@NonNull Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPreDestroyed(@NonNull Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPrePaused(@NonNull Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPreResumed(@NonNull Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPreSaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPreStarted(@NonNull Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPreStopped(@NonNull Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(@NonNull Activity activity) {
        setTopActivity(activity);
        if (this.mIsBackground) {
            this.mIsBackground = false;
            postStatus(activity, true);
        }
        processHideSoftInputOnActivityDestroy(activity, false);
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_RESUME);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(@NonNull Activity activity) {
        if (!this.mIsBackground) {
            setTopActivity(activity);
        }
        int i2 = this.mConfigCount;
        if (i2 < 0) {
            this.mConfigCount = i2 + 1;
        } else {
            this.mForegroundCount++;
        }
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_START);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        if (activity.isChangingConfigurations()) {
            this.mConfigCount--;
        } else {
            int i2 = this.mForegroundCount - 1;
            this.mForegroundCount = i2;
            if (i2 <= 0) {
                this.mIsBackground = true;
                postStatus(activity, false);
            }
        }
        processHideSoftInputOnActivityDestroy(activity, true);
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_STOP);
    }

    public void removeActivityLifecycleCallbacks(Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        removeActivityLifecycleCallbacks(STUB, activityLifecycleCallbacks);
    }

    public void removeOnAppStatusChangedListener(Utils.OnAppStatusChangedListener onAppStatusChangedListener) {
        this.mStatusListeners.remove(onAppStatusChangedListener);
    }

    public void unInit(Application application) {
        this.mActivityList.clear();
        application.unregisterActivityLifecycleCallbacks(this);
    }

    public void addActivityLifecycleCallbacks(final Activity activity, final Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        if (activity == null || activityLifecycleCallbacks == null) {
            return;
        }
        UtilsBridge.runOnUiThread(new Runnable() { // from class: com.blankj.utilcode.util.UtilsActivityLifecycleImpl.1
            @Override // java.lang.Runnable
            public void run() {
                UtilsActivityLifecycleImpl.this.addActivityLifecycleCallbacksInner(activity, activityLifecycleCallbacks);
            }
        });
    }

    public void removeActivityLifecycleCallbacks(final Activity activity) {
        if (activity == null) {
            return;
        }
        UtilsBridge.runOnUiThread(new Runnable() { // from class: com.blankj.utilcode.util.UtilsActivityLifecycleImpl.2
            @Override // java.lang.Runnable
            public void run() {
                UtilsActivityLifecycleImpl.this.mActivityLifecycleCallbacksMap.remove(activity);
            }
        });
    }

    public void removeActivityLifecycleCallbacks(final Activity activity, final Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        if (activity == null || activityLifecycleCallbacks == null) {
            return;
        }
        UtilsBridge.runOnUiThread(new Runnable() { // from class: com.blankj.utilcode.util.UtilsActivityLifecycleImpl.3
            @Override // java.lang.Runnable
            public void run() {
                UtilsActivityLifecycleImpl.this.removeActivityLifecycleCallbacksInner(activity, activityLifecycleCallbacks);
            }
        });
    }
}
