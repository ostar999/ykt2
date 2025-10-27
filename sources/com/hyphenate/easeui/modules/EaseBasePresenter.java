package com.hyphenate.easeui.modules;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.hyphenate.easeui.manager.EaseThreadManager;

/* loaded from: classes4.dex */
public abstract class EaseBasePresenter implements LifecycleObserver {
    private static final String TAG = "EaseBasePresenter";
    private boolean isDestroy;

    public abstract void attachView(ILoadDataView iLoadDataView);

    public abstract void detachView();

    public boolean isActive() {
        return !this.isDestroy;
    }

    public boolean isDestroy() {
        return this.isDestroy;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Log.i(TAG, toString() + " onCreate");
        this.isDestroy = false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.i(TAG, toString() + " onDestroy");
        this.isDestroy = true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Log.i(TAG, toString() + " onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Log.i(TAG, toString() + " onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Log.i(TAG, toString() + " onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Log.i(TAG, toString() + " onStop");
    }

    public void runOnIO(Runnable runnable) {
        EaseThreadManager.getInstance().runOnIOThread(runnable);
    }

    public void runOnUI(Runnable runnable) {
        EaseThreadManager.getInstance().runOnMainThread(runnable);
    }
}
