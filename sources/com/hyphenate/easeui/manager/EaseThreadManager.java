package com.hyphenate.easeui.manager;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class EaseThreadManager {
    private static volatile EaseThreadManager instance;
    private Executor mIOThreadExecutor;
    private Handler mMainThreadHandler;

    private EaseThreadManager() {
        init();
    }

    public static EaseThreadManager getInstance() {
        if (instance == null) {
            synchronized (EaseThreadManager.class) {
                if (instance == null) {
                    instance = new EaseThreadManager();
                }
            }
        }
        return instance;
    }

    private void init() {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        this.mIOThreadExecutor = new ThreadPoolExecutor(iAvailableProcessors, iAvailableProcessors * 2, 1, TimeUnit.SECONDS, new LinkedBlockingDeque(), new BackgroundThreadFactory(10));
        this.mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public void runOnIOThread(Runnable runnable) {
        this.mIOThreadExecutor.execute(runnable);
    }

    public void runOnMainThread(Runnable runnable) {
        this.mMainThreadHandler.post(runnable);
    }
}
