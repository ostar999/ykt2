package io.reactivex.rxjava3.android.schedulers;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import java.util.concurrent.Callable;

/* loaded from: classes8.dex */
public final class AndroidSchedulers {
    private static final Scheduler MAIN_THREAD = RxAndroidPlugins.initMainThreadScheduler(new Callable() { // from class: io.reactivex.rxjava3.android.schedulers.a
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return AndroidSchedulers.MainHolder.DEFAULT;
        }
    });

    public static final class MainHolder {
        static final Scheduler DEFAULT = AndroidSchedulers.internalFrom(Looper.getMainLooper(), true);

        private MainHolder() {
        }
    }

    private AndroidSchedulers() {
        throw new AssertionError("No instances.");
    }

    public static Scheduler from(Looper looper) {
        return from(looper, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public static Scheduler internalFrom(Looper looper, boolean async) {
        return new HandlerScheduler(new Handler(looper), async);
    }

    public static Scheduler mainThread() {
        return RxAndroidPlugins.onMainThreadScheduler(MAIN_THREAD);
    }

    public static Scheduler from(Looper looper, boolean async) {
        if (looper != null) {
            return internalFrom(looper, async);
        }
        throw new NullPointerException("looper == null");
    }
}
