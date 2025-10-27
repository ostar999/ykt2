package cn.webdemo.com.supporfragment.eventbusactivityscope;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes.dex */
public class EventBusActivityScope {
    private static final String TAG = "EventBusActivityScope";
    private static volatile EventBus sInvalidEventBus;
    private static AtomicBoolean sInitialized = new AtomicBoolean(false);
    private static final Map<Activity, LazyEventBusInstance> sActivityEventBusScopePool = new ConcurrentHashMap();

    public static class LazyEventBusInstance {
        private volatile EventBus eventBus;

        public EventBus getInstance() {
            if (this.eventBus == null) {
                synchronized (this) {
                    if (this.eventBus == null) {
                        this.eventBus = new EventBus();
                    }
                }
            }
            return this.eventBus;
        }
    }

    public static EventBus getDefault(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Can't find the Activity, the Activity is null!");
            return invalidEventBus();
        }
        LazyEventBusInstance lazyEventBusInstance = sActivityEventBusScopePool.get(activity);
        if (lazyEventBusInstance != null) {
            return lazyEventBusInstance.getInstance();
        }
        Log.e(TAG, "Can't find the Activity, it has been removed!");
        return invalidEventBus();
    }

    public static void init(Context context) {
        if (sInitialized.getAndSet(true)) {
            return;
        }
        ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: cn.webdemo.com.supporfragment.eventbusactivityscope.EventBusActivityScope.1
            private Handler mainHandler = new Handler(Looper.getMainLooper());

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
                EventBusActivityScope.sActivityEventBusScopePool.put(activity, new LazyEventBusInstance());
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(final Activity activity) {
                if (EventBusActivityScope.sActivityEventBusScopePool.containsKey(activity)) {
                    this.mainHandler.post(new Runnable() { // from class: cn.webdemo.com.supporfragment.eventbusactivityscope.EventBusActivityScope.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            EventBusActivityScope.sActivityEventBusScopePool.remove(activity);
                        }
                    });
                }
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
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
            }
        });
    }

    private static EventBus invalidEventBus() {
        if (sInvalidEventBus == null) {
            synchronized (EventBusActivityScope.class) {
                if (sInvalidEventBus == null) {
                    sInvalidEventBus = new EventBus();
                }
            }
        }
        return sInvalidEventBus;
    }
}
