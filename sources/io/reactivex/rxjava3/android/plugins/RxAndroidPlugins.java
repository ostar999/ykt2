package io.reactivex.rxjava3.android.plugins;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import java.util.concurrent.Callable;

/* loaded from: classes8.dex */
public final class RxAndroidPlugins {
    private static volatile Function<Callable<Scheduler>, Scheduler> onInitMainThreadHandler;
    private static volatile Function<Scheduler, Scheduler> onMainThreadHandler;

    private RxAndroidPlugins() {
        throw new AssertionError("No instances.");
    }

    public static <T, R> R apply(Function<T, R> f2, T t2) {
        try {
            return f2.apply(t2);
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    public static Scheduler applyRequireNonNull(Function<Callable<Scheduler>, Scheduler> f2, Callable<Scheduler> s2) {
        Scheduler scheduler = (Scheduler) apply(f2, s2);
        if (scheduler != null) {
            return scheduler;
        }
        throw new NullPointerException("Scheduler Callable returned null");
    }

    public static Scheduler callRequireNonNull(Callable<Scheduler> s2) {
        try {
            Scheduler schedulerCall = s2.call();
            if (schedulerCall != null) {
                return schedulerCall;
            }
            throw new NullPointerException("Scheduler Callable returned null");
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    public static Function<Callable<Scheduler>, Scheduler> getInitMainThreadSchedulerHandler() {
        return onInitMainThreadHandler;
    }

    public static Function<Scheduler, Scheduler> getOnMainThreadSchedulerHandler() {
        return onMainThreadHandler;
    }

    public static Scheduler initMainThreadScheduler(Callable<Scheduler> scheduler) {
        if (scheduler == null) {
            throw new NullPointerException("scheduler == null");
        }
        Function<Callable<Scheduler>, Scheduler> function = onInitMainThreadHandler;
        return function == null ? callRequireNonNull(scheduler) : applyRequireNonNull(function, scheduler);
    }

    public static Scheduler onMainThreadScheduler(Scheduler scheduler) {
        if (scheduler == null) {
            throw new NullPointerException("scheduler == null");
        }
        Function<Scheduler, Scheduler> function = onMainThreadHandler;
        return function == null ? scheduler : (Scheduler) apply(function, scheduler);
    }

    public static void reset() {
        setInitMainThreadSchedulerHandler(null);
        setMainThreadSchedulerHandler(null);
    }

    public static void setInitMainThreadSchedulerHandler(Function<Callable<Scheduler>, Scheduler> handler) {
        onInitMainThreadHandler = handler;
    }

    public static void setMainThreadSchedulerHandler(Function<Scheduler, Scheduler> handler) {
        onMainThreadHandler = handler;
    }
}
