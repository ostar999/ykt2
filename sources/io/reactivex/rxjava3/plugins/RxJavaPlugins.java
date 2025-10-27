package io.reactivex.rxjava3.plugins;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.exceptions.OnErrorNotImplementedException;
import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.schedulers.ComputationScheduler;
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler;
import io.reactivex.rxjava3.internal.schedulers.IoScheduler;
import io.reactivex.rxjava3.internal.schedulers.NewThreadScheduler;
import io.reactivex.rxjava3.internal.schedulers.SingleScheduler;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class RxJavaPlugins {

    @Nullable
    static volatile Consumer<? super Throwable> errorHandler;
    static volatile boolean failNonBlockingScheduler;
    static volatile boolean lockdown;

    @Nullable
    static volatile BooleanSupplier onBeforeBlocking;

    @Nullable
    static volatile Function<? super Completable, ? extends Completable> onCompletableAssembly;

    @Nullable
    static volatile BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> onCompletableSubscribe;

    @Nullable
    static volatile Function<? super Scheduler, ? extends Scheduler> onComputationHandler;

    @Nullable
    static volatile Function<? super ConnectableFlowable, ? extends ConnectableFlowable> onConnectableFlowableAssembly;

    @Nullable
    static volatile Function<? super ConnectableObservable, ? extends ConnectableObservable> onConnectableObservableAssembly;

    @Nullable
    static volatile Function<? super Flowable, ? extends Flowable> onFlowableAssembly;

    @Nullable
    static volatile BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> onFlowableSubscribe;

    @Nullable
    static volatile Function<? super Supplier<Scheduler>, ? extends Scheduler> onInitComputationHandler;

    @Nullable
    static volatile Function<? super Supplier<Scheduler>, ? extends Scheduler> onInitIoHandler;

    @Nullable
    static volatile Function<? super Supplier<Scheduler>, ? extends Scheduler> onInitNewThreadHandler;

    @Nullable
    static volatile Function<? super Supplier<Scheduler>, ? extends Scheduler> onInitSingleHandler;

    @Nullable
    static volatile Function<? super Scheduler, ? extends Scheduler> onIoHandler;

    @Nullable
    static volatile Function<? super Maybe, ? extends Maybe> onMaybeAssembly;

    @Nullable
    static volatile BiFunction<? super Maybe, ? super MaybeObserver, ? extends MaybeObserver> onMaybeSubscribe;

    @Nullable
    static volatile Function<? super Scheduler, ? extends Scheduler> onNewThreadHandler;

    @Nullable
    static volatile Function<? super Observable, ? extends Observable> onObservableAssembly;

    @Nullable
    static volatile BiFunction<? super Observable, ? super Observer, ? extends Observer> onObservableSubscribe;

    @Nullable
    static volatile Function<? super ParallelFlowable, ? extends ParallelFlowable> onParallelAssembly;

    @Nullable
    static volatile BiFunction<? super ParallelFlowable, ? super Subscriber[], ? extends Subscriber[]> onParallelSubscribe;

    @Nullable
    static volatile Function<? super Runnable, ? extends Runnable> onScheduleHandler;

    @Nullable
    static volatile Function<? super Single, ? extends Single> onSingleAssembly;

    @Nullable
    static volatile Function<? super Scheduler, ? extends Scheduler> onSingleHandler;

    @Nullable
    static volatile BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> onSingleSubscribe;

    private RxJavaPlugins() {
        throw new IllegalStateException("No instances!");
    }

    @NonNull
    public static <T, R> R apply(@NonNull Function<T, R> f2, @NonNull T t2) {
        try {
            return f2.apply(t2);
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @NonNull
    public static Scheduler applyRequireNonNull(@NonNull Function<? super Supplier<Scheduler>, ? extends Scheduler> f2, Supplier<Scheduler> s2) {
        Object objApply = apply(f2, s2);
        Objects.requireNonNull(objApply, "Scheduler Supplier result can't be null");
        return (Scheduler) objApply;
    }

    @NonNull
    public static Scheduler callRequireNonNull(@NonNull Supplier<Scheduler> s2) {
        try {
            Scheduler scheduler = s2.get();
            Objects.requireNonNull(scheduler, "Scheduler Supplier result can't be null");
            return scheduler;
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @NonNull
    public static Scheduler createComputationScheduler(@NonNull ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "threadFactory is null");
        return new ComputationScheduler(threadFactory);
    }

    @NonNull
    public static Scheduler createExecutorScheduler(@NonNull Executor executor, boolean interruptibleWorker, boolean fair) {
        return new ExecutorScheduler(executor, interruptibleWorker, fair);
    }

    @NonNull
    public static Scheduler createIoScheduler(@NonNull ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "threadFactory is null");
        return new IoScheduler(threadFactory);
    }

    @NonNull
    public static Scheduler createNewThreadScheduler(@NonNull ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "threadFactory is null");
        return new NewThreadScheduler(threadFactory);
    }

    @NonNull
    public static Scheduler createSingleScheduler(@NonNull ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "threadFactory is null");
        return new SingleScheduler(threadFactory);
    }

    @Nullable
    public static Function<? super Scheduler, ? extends Scheduler> getComputationSchedulerHandler() {
        return onComputationHandler;
    }

    @Nullable
    public static Consumer<? super Throwable> getErrorHandler() {
        return errorHandler;
    }

    @Nullable
    public static Function<? super Supplier<Scheduler>, ? extends Scheduler> getInitComputationSchedulerHandler() {
        return onInitComputationHandler;
    }

    @Nullable
    public static Function<? super Supplier<Scheduler>, ? extends Scheduler> getInitIoSchedulerHandler() {
        return onInitIoHandler;
    }

    @Nullable
    public static Function<? super Supplier<Scheduler>, ? extends Scheduler> getInitNewThreadSchedulerHandler() {
        return onInitNewThreadHandler;
    }

    @Nullable
    public static Function<? super Supplier<Scheduler>, ? extends Scheduler> getInitSingleSchedulerHandler() {
        return onInitSingleHandler;
    }

    @Nullable
    public static Function<? super Scheduler, ? extends Scheduler> getIoSchedulerHandler() {
        return onIoHandler;
    }

    @Nullable
    public static Function<? super Scheduler, ? extends Scheduler> getNewThreadSchedulerHandler() {
        return onNewThreadHandler;
    }

    @Nullable
    public static BooleanSupplier getOnBeforeBlocking() {
        return onBeforeBlocking;
    }

    @Nullable
    public static Function<? super Completable, ? extends Completable> getOnCompletableAssembly() {
        return onCompletableAssembly;
    }

    @Nullable
    public static BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> getOnCompletableSubscribe() {
        return onCompletableSubscribe;
    }

    @Nullable
    public static Function<? super ConnectableFlowable, ? extends ConnectableFlowable> getOnConnectableFlowableAssembly() {
        return onConnectableFlowableAssembly;
    }

    @Nullable
    public static Function<? super ConnectableObservable, ? extends ConnectableObservable> getOnConnectableObservableAssembly() {
        return onConnectableObservableAssembly;
    }

    @Nullable
    public static Function<? super Flowable, ? extends Flowable> getOnFlowableAssembly() {
        return onFlowableAssembly;
    }

    @Nullable
    public static BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> getOnFlowableSubscribe() {
        return onFlowableSubscribe;
    }

    @Nullable
    public static Function<? super Maybe, ? extends Maybe> getOnMaybeAssembly() {
        return onMaybeAssembly;
    }

    @Nullable
    public static BiFunction<? super Maybe, ? super MaybeObserver, ? extends MaybeObserver> getOnMaybeSubscribe() {
        return onMaybeSubscribe;
    }

    @Nullable
    public static Function<? super Observable, ? extends Observable> getOnObservableAssembly() {
        return onObservableAssembly;
    }

    @Nullable
    public static BiFunction<? super Observable, ? super Observer, ? extends Observer> getOnObservableSubscribe() {
        return onObservableSubscribe;
    }

    @Nullable
    public static Function<? super ParallelFlowable, ? extends ParallelFlowable> getOnParallelAssembly() {
        return onParallelAssembly;
    }

    @Nullable
    public static BiFunction<? super ParallelFlowable, ? super Subscriber[], ? extends Subscriber[]> getOnParallelSubscribe() {
        return onParallelSubscribe;
    }

    @Nullable
    public static Function<? super Single, ? extends Single> getOnSingleAssembly() {
        return onSingleAssembly;
    }

    @Nullable
    public static BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> getOnSingleSubscribe() {
        return onSingleSubscribe;
    }

    @Nullable
    public static Function<? super Runnable, ? extends Runnable> getScheduleHandler() {
        return onScheduleHandler;
    }

    @Nullable
    public static Function<? super Scheduler, ? extends Scheduler> getSingleSchedulerHandler() {
        return onSingleHandler;
    }

    @NonNull
    public static Scheduler initComputationScheduler(@NonNull Supplier<Scheduler> defaultScheduler) {
        Objects.requireNonNull(defaultScheduler, "Scheduler Supplier can't be null");
        Function<? super Supplier<Scheduler>, ? extends Scheduler> function = onInitComputationHandler;
        return function == null ? callRequireNonNull(defaultScheduler) : applyRequireNonNull(function, defaultScheduler);
    }

    @NonNull
    public static Scheduler initIoScheduler(@NonNull Supplier<Scheduler> defaultScheduler) {
        Objects.requireNonNull(defaultScheduler, "Scheduler Supplier can't be null");
        Function<? super Supplier<Scheduler>, ? extends Scheduler> function = onInitIoHandler;
        return function == null ? callRequireNonNull(defaultScheduler) : applyRequireNonNull(function, defaultScheduler);
    }

    @NonNull
    public static Scheduler initNewThreadScheduler(@NonNull Supplier<Scheduler> defaultScheduler) {
        Objects.requireNonNull(defaultScheduler, "Scheduler Supplier can't be null");
        Function<? super Supplier<Scheduler>, ? extends Scheduler> function = onInitNewThreadHandler;
        return function == null ? callRequireNonNull(defaultScheduler) : applyRequireNonNull(function, defaultScheduler);
    }

    @NonNull
    public static Scheduler initSingleScheduler(@NonNull Supplier<Scheduler> defaultScheduler) {
        Objects.requireNonNull(defaultScheduler, "Scheduler Supplier can't be null");
        Function<? super Supplier<Scheduler>, ? extends Scheduler> function = onInitSingleHandler;
        return function == null ? callRequireNonNull(defaultScheduler) : applyRequireNonNull(function, defaultScheduler);
    }

    public static boolean isBug(Throwable error) {
        return (error instanceof OnErrorNotImplementedException) || (error instanceof MissingBackpressureException) || (error instanceof IllegalStateException) || (error instanceof NullPointerException) || (error instanceof IllegalArgumentException) || (error instanceof CompositeException);
    }

    public static boolean isFailOnNonBlockingScheduler() {
        return failNonBlockingScheduler;
    }

    public static boolean isLockdown() {
        return lockdown;
    }

    public static void lockdown() {
        lockdown = true;
    }

    @NonNull
    public static <T> Maybe<T> onAssembly(@NonNull Maybe<T> source) {
        Function<? super Maybe, ? extends Maybe> function = onMaybeAssembly;
        return function != null ? (Maybe) apply(function, source) : source;
    }

    public static boolean onBeforeBlocking() {
        BooleanSupplier booleanSupplier = onBeforeBlocking;
        if (booleanSupplier == null) {
            return false;
        }
        try {
            return booleanSupplier.getAsBoolean();
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @NonNull
    public static Scheduler onComputationScheduler(@NonNull Scheduler defaultScheduler) {
        Function<? super Scheduler, ? extends Scheduler> function = onComputationHandler;
        return function == null ? defaultScheduler : (Scheduler) apply(function, defaultScheduler);
    }

    public static void onError(@NonNull Throwable error) {
        Consumer<? super Throwable> consumer = errorHandler;
        if (error == null) {
            error = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
        } else if (!isBug(error)) {
            error = new UndeliverableException(error);
        }
        if (consumer != null) {
            try {
                consumer.accept(error);
                return;
            } catch (Throwable th) {
                th.printStackTrace();
                uncaught(th);
            }
        }
        error.printStackTrace();
        uncaught(error);
    }

    @NonNull
    public static Scheduler onIoScheduler(@NonNull Scheduler defaultScheduler) {
        Function<? super Scheduler, ? extends Scheduler> function = onIoHandler;
        return function == null ? defaultScheduler : (Scheduler) apply(function, defaultScheduler);
    }

    @NonNull
    public static Scheduler onNewThreadScheduler(@NonNull Scheduler defaultScheduler) {
        Function<? super Scheduler, ? extends Scheduler> function = onNewThreadHandler;
        return function == null ? defaultScheduler : (Scheduler) apply(function, defaultScheduler);
    }

    @NonNull
    public static Runnable onSchedule(@NonNull Runnable run) {
        Objects.requireNonNull(run, "run is null");
        Function<? super Runnable, ? extends Runnable> function = onScheduleHandler;
        return function == null ? run : (Runnable) apply(function, run);
    }

    @NonNull
    public static Scheduler onSingleScheduler(@NonNull Scheduler defaultScheduler) {
        Function<? super Scheduler, ? extends Scheduler> function = onSingleHandler;
        return function == null ? defaultScheduler : (Scheduler) apply(function, defaultScheduler);
    }

    @NonNull
    public static <T> Subscriber<? super T> onSubscribe(@NonNull Flowable<T> source, @NonNull Subscriber<? super T> subscriber) {
        BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> biFunction = onFlowableSubscribe;
        return biFunction != null ? (Subscriber) apply(biFunction, source, subscriber) : subscriber;
    }

    public static void reset() {
        setErrorHandler(null);
        setScheduleHandler(null);
        setComputationSchedulerHandler(null);
        setInitComputationSchedulerHandler(null);
        setIoSchedulerHandler(null);
        setInitIoSchedulerHandler(null);
        setSingleSchedulerHandler(null);
        setInitSingleSchedulerHandler(null);
        setNewThreadSchedulerHandler(null);
        setInitNewThreadSchedulerHandler(null);
        setOnFlowableAssembly(null);
        setOnFlowableSubscribe(null);
        setOnObservableAssembly(null);
        setOnObservableSubscribe(null);
        setOnSingleAssembly(null);
        setOnSingleSubscribe(null);
        setOnCompletableAssembly(null);
        setOnCompletableSubscribe(null);
        setOnConnectableFlowableAssembly(null);
        setOnConnectableObservableAssembly(null);
        setOnMaybeAssembly(null);
        setOnMaybeSubscribe(null);
        setOnParallelAssembly(null);
        setOnParallelSubscribe(null);
        setFailOnNonBlockingScheduler(false);
        setOnBeforeBlocking(null);
    }

    public static void setComputationSchedulerHandler(@Nullable Function<? super Scheduler, ? extends Scheduler> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onComputationHandler = handler;
    }

    public static void setErrorHandler(@Nullable Consumer<? super Throwable> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        errorHandler = handler;
    }

    public static void setFailOnNonBlockingScheduler(boolean enable) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        failNonBlockingScheduler = enable;
    }

    public static void setInitComputationSchedulerHandler(@Nullable Function<? super Supplier<Scheduler>, ? extends Scheduler> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onInitComputationHandler = handler;
    }

    public static void setInitIoSchedulerHandler(@Nullable Function<? super Supplier<Scheduler>, ? extends Scheduler> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onInitIoHandler = handler;
    }

    public static void setInitNewThreadSchedulerHandler(@Nullable Function<? super Supplier<Scheduler>, ? extends Scheduler> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onInitNewThreadHandler = handler;
    }

    public static void setInitSingleSchedulerHandler(@Nullable Function<? super Supplier<Scheduler>, ? extends Scheduler> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onInitSingleHandler = handler;
    }

    public static void setIoSchedulerHandler(@Nullable Function<? super Scheduler, ? extends Scheduler> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onIoHandler = handler;
    }

    public static void setNewThreadSchedulerHandler(@Nullable Function<? super Scheduler, ? extends Scheduler> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onNewThreadHandler = handler;
    }

    public static void setOnBeforeBlocking(@Nullable BooleanSupplier handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onBeforeBlocking = handler;
    }

    public static void setOnCompletableAssembly(@Nullable Function<? super Completable, ? extends Completable> onCompletableAssembly2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onCompletableAssembly = onCompletableAssembly2;
    }

    public static void setOnCompletableSubscribe(@Nullable BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> onCompletableSubscribe2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onCompletableSubscribe = onCompletableSubscribe2;
    }

    public static void setOnConnectableFlowableAssembly(@Nullable Function<? super ConnectableFlowable, ? extends ConnectableFlowable> onConnectableFlowableAssembly2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onConnectableFlowableAssembly = onConnectableFlowableAssembly2;
    }

    public static void setOnConnectableObservableAssembly(@Nullable Function<? super ConnectableObservable, ? extends ConnectableObservable> onConnectableObservableAssembly2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onConnectableObservableAssembly = onConnectableObservableAssembly2;
    }

    public static void setOnFlowableAssembly(@Nullable Function<? super Flowable, ? extends Flowable> onFlowableAssembly2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onFlowableAssembly = onFlowableAssembly2;
    }

    public static void setOnFlowableSubscribe(@Nullable BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> onFlowableSubscribe2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onFlowableSubscribe = onFlowableSubscribe2;
    }

    public static void setOnMaybeAssembly(@Nullable Function<? super Maybe, ? extends Maybe> onMaybeAssembly2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onMaybeAssembly = onMaybeAssembly2;
    }

    public static void setOnMaybeSubscribe(@Nullable BiFunction<? super Maybe, MaybeObserver, ? extends MaybeObserver> onMaybeSubscribe2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onMaybeSubscribe = onMaybeSubscribe2;
    }

    public static void setOnObservableAssembly(@Nullable Function<? super Observable, ? extends Observable> onObservableAssembly2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onObservableAssembly = onObservableAssembly2;
    }

    public static void setOnObservableSubscribe(@Nullable BiFunction<? super Observable, ? super Observer, ? extends Observer> onObservableSubscribe2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onObservableSubscribe = onObservableSubscribe2;
    }

    public static void setOnParallelAssembly(@Nullable Function<? super ParallelFlowable, ? extends ParallelFlowable> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onParallelAssembly = handler;
    }

    public static void setOnParallelSubscribe(@Nullable BiFunction<? super ParallelFlowable, ? super Subscriber[], ? extends Subscriber[]> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onParallelSubscribe = handler;
    }

    public static void setOnSingleAssembly(@Nullable Function<? super Single, ? extends Single> onSingleAssembly2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onSingleAssembly = onSingleAssembly2;
    }

    public static void setOnSingleSubscribe(@Nullable BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> onSingleSubscribe2) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onSingleSubscribe = onSingleSubscribe2;
    }

    public static void setScheduleHandler(@Nullable Function<? super Runnable, ? extends Runnable> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onScheduleHandler = handler;
    }

    public static void setSingleSchedulerHandler(@Nullable Function<? super Scheduler, ? extends Scheduler> handler) {
        if (lockdown) {
            throw new IllegalStateException("Plugins can't be changed anymore");
        }
        onSingleHandler = handler;
    }

    public static void uncaught(@NonNull Throwable error) {
        Thread threadCurrentThread = Thread.currentThread();
        threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, error);
    }

    public static void unlock() {
        lockdown = false;
    }

    @NonNull
    public static <T, U, R> R apply(@NonNull BiFunction<T, U, R> f2, @NonNull T t2, @NonNull U u2) {
        try {
            return f2.apply(t2, u2);
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @NonNull
    public static <T> Flowable<T> onAssembly(@NonNull Flowable<T> source) {
        Function<? super Flowable, ? extends Flowable> function = onFlowableAssembly;
        return function != null ? (Flowable) apply(function, source) : source;
    }

    @NonNull
    public static <T> Observer<? super T> onSubscribe(@NonNull Observable<T> source, @NonNull Observer<? super T> observer) {
        BiFunction<? super Observable, ? super Observer, ? extends Observer> biFunction = onObservableSubscribe;
        return biFunction != null ? (Observer) apply(biFunction, source, observer) : observer;
    }

    @NonNull
    public static <T> ConnectableFlowable<T> onAssembly(@NonNull ConnectableFlowable<T> source) {
        Function<? super ConnectableFlowable, ? extends ConnectableFlowable> function = onConnectableFlowableAssembly;
        return function != null ? (ConnectableFlowable) apply(function, source) : source;
    }

    @NonNull
    public static <T> SingleObserver<? super T> onSubscribe(@NonNull Single<T> source, @NonNull SingleObserver<? super T> observer) {
        BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> biFunction = onSingleSubscribe;
        return biFunction != null ? (SingleObserver) apply(biFunction, source, observer) : observer;
    }

    @NonNull
    public static <T> Observable<T> onAssembly(@NonNull Observable<T> source) {
        Function<? super Observable, ? extends Observable> function = onObservableAssembly;
        return function != null ? (Observable) apply(function, source) : source;
    }

    @NonNull
    public static CompletableObserver onSubscribe(@NonNull Completable source, @NonNull CompletableObserver observer) {
        BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> biFunction = onCompletableSubscribe;
        return biFunction != null ? (CompletableObserver) apply(biFunction, source, observer) : observer;
    }

    @NonNull
    public static <T> ConnectableObservable<T> onAssembly(@NonNull ConnectableObservable<T> source) {
        Function<? super ConnectableObservable, ? extends ConnectableObservable> function = onConnectableObservableAssembly;
        return function != null ? (ConnectableObservable) apply(function, source) : source;
    }

    @NonNull
    public static <T> MaybeObserver<? super T> onSubscribe(@NonNull Maybe<T> source, @NonNull MaybeObserver<? super T> observer) {
        BiFunction<? super Maybe, ? super MaybeObserver, ? extends MaybeObserver> biFunction = onMaybeSubscribe;
        return biFunction != null ? (MaybeObserver) apply(biFunction, source, observer) : observer;
    }

    @NonNull
    public static <T> Single<T> onAssembly(@NonNull Single<T> source) {
        Function<? super Single, ? extends Single> function = onSingleAssembly;
        return function != null ? (Single) apply(function, source) : source;
    }

    @NonNull
    public static <T> Subscriber<? super T>[] onSubscribe(@NonNull ParallelFlowable<T> source, @NonNull Subscriber<? super T>[] subscribers) {
        BiFunction<? super ParallelFlowable, ? super Subscriber[], ? extends Subscriber[]> biFunction = onParallelSubscribe;
        return biFunction != null ? (Subscriber[]) apply(biFunction, source, subscribers) : subscribers;
    }

    @NonNull
    public static Completable onAssembly(@NonNull Completable source) {
        Function<? super Completable, ? extends Completable> function = onCompletableAssembly;
        return function != null ? (Completable) apply(function, source) : source;
    }

    @NonNull
    public static <T> ParallelFlowable<T> onAssembly(@NonNull ParallelFlowable<T> source) {
        Function<? super ParallelFlowable, ? extends ParallelFlowable> function = onParallelAssembly;
        return function != null ? (ParallelFlowable) apply(function, source) : source;
    }
}
