package io.reactivex.observables;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableAutoConnect;
import io.reactivex.internal.operators.observable.ObservableRefCount;
import io.reactivex.internal.util.ConnectConsumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public abstract class ConnectableObservable<T> extends Observable<T> {
    @NonNull
    public Observable<T> autoConnect() {
        return autoConnect(1);
    }

    public final Disposable connect() {
        ConnectConsumer connectConsumer = new ConnectConsumer();
        connect(connectConsumer);
        return connectConsumer.disposable;
    }

    public abstract void connect(@NonNull Consumer<? super Disposable> consumer);

    @SchedulerSupport("none")
    @CheckReturnValue
    @NonNull
    public Observable<T> refCount() {
        return RxJavaPlugins.onAssembly(new ObservableRefCount(this));
    }

    @NonNull
    public Observable<T> autoConnect(int i2) {
        return autoConnect(i2, Functions.emptyConsumer());
    }

    @SchedulerSupport("none")
    @CheckReturnValue
    public final Observable<T> refCount(int i2) {
        return refCount(i2, 0L, TimeUnit.NANOSECONDS, Schedulers.trampoline());
    }

    @NonNull
    public Observable<T> autoConnect(int i2, @NonNull Consumer<? super Disposable> consumer) {
        if (i2 <= 0) {
            connect(consumer);
            return RxJavaPlugins.onAssembly((ConnectableObservable) this);
        }
        return RxJavaPlugins.onAssembly(new ObservableAutoConnect(this, i2, consumer));
    }

    @SchedulerSupport("io.reactivex:computation")
    @CheckReturnValue
    public final Observable<T> refCount(long j2, TimeUnit timeUnit) {
        return refCount(1, j2, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @CheckReturnValue
    public final Observable<T> refCount(long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return refCount(1, j2, timeUnit, scheduler);
    }

    @SchedulerSupport("io.reactivex:computation")
    @CheckReturnValue
    public final Observable<T> refCount(int i2, long j2, TimeUnit timeUnit) {
        return refCount(i2, j2, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @CheckReturnValue
    public final Observable<T> refCount(int i2, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.verifyPositive(i2, "subscriberCount");
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableRefCount(this, i2, j2, timeUnit, scheduler));
    }
}
