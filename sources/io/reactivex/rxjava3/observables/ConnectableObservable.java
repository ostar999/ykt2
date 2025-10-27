package io.reactivex.rxjava3.observables;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.SchedulerSupport;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.operators.observable.ObservableAutoConnect;
import io.reactivex.rxjava3.internal.operators.observable.ObservableRefCount;
import io.reactivex.rxjava3.internal.util.ConnectConsumer;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public abstract class ConnectableObservable<T> extends Observable<T> {
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public Observable<T> autoConnect() {
        return autoConnect(1);
    }

    @NonNull
    @SchedulerSupport("none")
    public final Disposable connect() {
        ConnectConsumer connectConsumer = new ConnectConsumer();
        connect(connectConsumer);
        return connectConsumer.disposable;
    }

    @SchedulerSupport("none")
    public abstract void connect(@NonNull Consumer<? super Disposable> connection);

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public Observable<T> refCount() {
        return RxJavaPlugins.onAssembly(new ObservableRefCount(this));
    }

    @SchedulerSupport("none")
    public abstract void reset();

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public Observable<T> autoConnect(int numberOfObservers) {
        return autoConnect(numberOfObservers, Functions.emptyConsumer());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> refCount(int observerCount) {
        return refCount(observerCount, 0L, TimeUnit.NANOSECONDS, Schedulers.trampoline());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public Observable<T> autoConnect(int numberOfObservers, @NonNull Consumer<? super Disposable> connection) {
        Objects.requireNonNull(connection, "connection is null");
        if (numberOfObservers <= 0) {
            connect(connection);
            return RxJavaPlugins.onAssembly((ConnectableObservable) this);
        }
        return RxJavaPlugins.onAssembly(new ObservableAutoConnect(this, numberOfObservers, connection));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> refCount(long timeout, @NonNull TimeUnit unit) {
        return refCount(1, timeout, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> refCount(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return refCount(1, timeout, unit, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> refCount(int observerCount, long timeout, @NonNull TimeUnit unit) {
        return refCount(observerCount, timeout, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> refCount(int observerCount, long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        ObjectHelper.verifyPositive(observerCount, "observerCount");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableRefCount(this, observerCount, timeout, unit, scheduler));
    }
}
