package io.reactivex.rxjava3.flowables;

import io.reactivex.rxjava3.annotations.BackpressureKind;
import io.reactivex.rxjava3.annotations.BackpressureSupport;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.SchedulerSupport;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableAutoConnect;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableRefCount;
import io.reactivex.rxjava3.internal.util.ConnectConsumer;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public abstract class ConnectableFlowable<T> extends Flowable<T> {
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public Flowable<T> autoConnect() {
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
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public Flowable<T> refCount() {
        return RxJavaPlugins.onAssembly(new FlowableRefCount(this));
    }

    @SchedulerSupport("none")
    public abstract void reset();

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public Flowable<T> autoConnect(int numberOfSubscribers) {
        return autoConnect(numberOfSubscribers, Functions.emptyConsumer());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> refCount(int subscriberCount) {
        return refCount(subscriberCount, 0L, TimeUnit.NANOSECONDS, Schedulers.trampoline());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public Flowable<T> autoConnect(int numberOfSubscribers, @NonNull Consumer<? super Disposable> connection) {
        Objects.requireNonNull(connection, "connection is null");
        if (numberOfSubscribers <= 0) {
            connect(connection);
            return RxJavaPlugins.onAssembly((ConnectableFlowable) this);
        }
        return RxJavaPlugins.onAssembly(new FlowableAutoConnect(this, numberOfSubscribers, connection));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> refCount(long timeout, @NonNull TimeUnit unit) {
        return refCount(1, timeout, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("custom")
    public final Flowable<T> refCount(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return refCount(1, timeout, unit, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> refCount(int subscriberCount, long timeout, @NonNull TimeUnit unit) {
        return refCount(subscriberCount, timeout, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("custom")
    public final Flowable<T> refCount(int subscriberCount, long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        ObjectHelper.verifyPositive(subscriberCount, "subscriberCount");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableRefCount(this, subscriberCount, timeout, unit, scheduler));
    }
}
