package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.BackpressureKind;
import io.reactivex.rxjava3.annotations.BackpressureSupport;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.SchedulerSupport;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.fuseable.FuseToFlowable;
import io.reactivex.rxjava3.internal.fuseable.FuseToMaybe;
import io.reactivex.rxjava3.internal.fuseable.FuseToObservable;
import io.reactivex.rxjava3.internal.jdk8.CompletableFromCompletionStage;
import io.reactivex.rxjava3.internal.jdk8.CompletionStageConsumer;
import io.reactivex.rxjava3.internal.observers.BlockingDisposableMultiObserver;
import io.reactivex.rxjava3.internal.observers.BlockingMultiObserver;
import io.reactivex.rxjava3.internal.observers.CallbackCompletableObserver;
import io.reactivex.rxjava3.internal.observers.DisposableAutoReleaseMultiObserver;
import io.reactivex.rxjava3.internal.observers.EmptyCompletableObserver;
import io.reactivex.rxjava3.internal.observers.FutureMultiObserver;
import io.reactivex.rxjava3.internal.observers.SafeCompletableObserver;
import io.reactivex.rxjava3.internal.operators.completable.CompletableAmb;
import io.reactivex.rxjava3.internal.operators.completable.CompletableAndThenCompletable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableCache;
import io.reactivex.rxjava3.internal.operators.completable.CompletableConcat;
import io.reactivex.rxjava3.internal.operators.completable.CompletableConcatArray;
import io.reactivex.rxjava3.internal.operators.completable.CompletableConcatIterable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableCreate;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDefer;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDelay;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDetach;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDisposeOn;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDoFinally;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDoOnEvent;
import io.reactivex.rxjava3.internal.operators.completable.CompletableEmpty;
import io.reactivex.rxjava3.internal.operators.completable.CompletableError;
import io.reactivex.rxjava3.internal.operators.completable.CompletableErrorSupplier;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromAction;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromCallable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromObservable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromPublisher;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromRunnable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromSingle;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromSupplier;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromUnsafeSource;
import io.reactivex.rxjava3.internal.operators.completable.CompletableHide;
import io.reactivex.rxjava3.internal.operators.completable.CompletableLift;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMaterialize;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMerge;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMergeArray;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMergeArrayDelayError;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMergeDelayErrorIterable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableMergeIterable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableNever;
import io.reactivex.rxjava3.internal.operators.completable.CompletableObserveOn;
import io.reactivex.rxjava3.internal.operators.completable.CompletableOnErrorComplete;
import io.reactivex.rxjava3.internal.operators.completable.CompletableOnErrorReturn;
import io.reactivex.rxjava3.internal.operators.completable.CompletablePeek;
import io.reactivex.rxjava3.internal.operators.completable.CompletableResumeNext;
import io.reactivex.rxjava3.internal.operators.completable.CompletableSubscribeOn;
import io.reactivex.rxjava3.internal.operators.completable.CompletableTakeUntilCompletable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableTimeout;
import io.reactivex.rxjava3.internal.operators.completable.CompletableTimer;
import io.reactivex.rxjava3.internal.operators.completable.CompletableToFlowable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableToObservable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableToSingle;
import io.reactivex.rxjava3.internal.operators.completable.CompletableUsing;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDelayWithCompletable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromCompletable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeIgnoreElementCompletable;
import io.reactivex.rxjava3.internal.operators.mixed.CompletableAndThenObservable;
import io.reactivex.rxjava3.internal.operators.mixed.CompletableAndThenPublisher;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapCompletablePublisher;
import io.reactivex.rxjava3.internal.operators.single.SingleDelayWithCompletable;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

/* loaded from: classes8.dex */
public abstract class Completable implements CompletableSource {
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable amb(@NonNull Iterable<? extends CompletableSource> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new CompletableAmb(null, sources));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static Completable ambArray(@NonNull CompletableSource... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? complete() : sources.length == 1 ? wrap(sources[0]) : RxJavaPlugins.onAssembly(new CompletableAmb(sources, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable complete() {
        return RxJavaPlugins.onAssembly(CompletableEmpty.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable concat(@NonNull Iterable<? extends CompletableSource> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new CompletableConcatIterable(sources));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static Completable concatArray(@NonNull CompletableSource... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? complete() : sources.length == 1 ? wrap(sources[0]) : RxJavaPlugins.onAssembly(new CompletableConcatArray(sources));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static Completable concatArrayDelayError(@NonNull CompletableSource... sources) {
        return Flowable.fromArray(sources).concatMapCompletableDelayError(Functions.identity(), true, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable concatDelayError(@NonNull Iterable<? extends CompletableSource> sources) {
        return Flowable.fromIterable(sources).concatMapCompletableDelayError(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable create(@NonNull CompletableOnSubscribe source) {
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new CompletableCreate(source));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable defer(@NonNull Supplier<? extends CompletableSource> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new CompletableDefer(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable error(@NonNull Supplier<? extends Throwable> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new CompletableErrorSupplier(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromAction(@NonNull Action action) {
        Objects.requireNonNull(action, "action is null");
        return RxJavaPlugins.onAssembly(new CompletableFromAction(action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromCallable(@NonNull Callable<?> callable) {
        Objects.requireNonNull(callable, "callable is null");
        return RxJavaPlugins.onAssembly(new CompletableFromCallable(callable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromCompletionStage(@NonNull CompletionStage<?> stage) {
        Objects.requireNonNull(stage, "stage is null");
        return RxJavaPlugins.onAssembly(new CompletableFromCompletionStage(stage));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromFuture(@NonNull Future<?> future) {
        Objects.requireNonNull(future, "future is null");
        return fromAction(Functions.futureAction(future));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Completable fromMaybe(@NonNull MaybeSource<T> maybe) {
        Objects.requireNonNull(maybe, "maybe is null");
        return RxJavaPlugins.onAssembly(new MaybeIgnoreElementCompletable(maybe));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Completable fromObservable(@NonNull ObservableSource<T> observable) {
        Objects.requireNonNull(observable, "observable is null");
        return RxJavaPlugins.onAssembly(new CompletableFromObservable(observable));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static <T> Completable fromPublisher(@NonNull Publisher<T> publisher) {
        Objects.requireNonNull(publisher, "publisher is null");
        return RxJavaPlugins.onAssembly(new CompletableFromPublisher(publisher));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromRunnable(@NonNull Runnable run) {
        Objects.requireNonNull(run, "run is null");
        return RxJavaPlugins.onAssembly(new CompletableFromRunnable(run));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Completable fromSingle(@NonNull SingleSource<T> single) {
        Objects.requireNonNull(single, "single is null");
        return RxJavaPlugins.onAssembly(new CompletableFromSingle(single));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable fromSupplier(@NonNull Supplier<?> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new CompletableFromSupplier(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable merge(@NonNull Iterable<? extends CompletableSource> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new CompletableMergeIterable(sources));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    private static Completable merge0(@NonNull Publisher<? extends CompletableSource> sources, int maxConcurrency, boolean delayErrors) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new CompletableMerge(sources, maxConcurrency, delayErrors));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static Completable mergeArray(@NonNull CompletableSource... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? complete() : sources.length == 1 ? wrap(sources[0]) : RxJavaPlugins.onAssembly(new CompletableMergeArray(sources));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static Completable mergeArrayDelayError(@NonNull CompletableSource... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new CompletableMergeArrayDelayError(sources));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable mergeDelayError(@NonNull Iterable<? extends CompletableSource> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new CompletableMergeDelayErrorIterable(sources));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable never() {
        return RxJavaPlugins.onAssembly(CompletableNever.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Single<Boolean> sequenceEqual(@NonNull CompletableSource source1, @NonNull CompletableSource source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return mergeArrayDelayError(source1, source2).andThen(Single.just(Boolean.TRUE));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static Completable switchOnNext(@NonNull Publisher<? extends CompletableSource> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapCompletablePublisher(sources, Functions.identity(), false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static Completable switchOnNextDelayError(@NonNull Publisher<? extends CompletableSource> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapCompletablePublisher(sources, Functions.identity(), true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    private Completable timeout0(long timeout, TimeUnit unit, Scheduler scheduler, CompletableSource fallback) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableTimeout(this, timeout, unit, scheduler, fallback));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public static Completable timer(long delay, @NonNull TimeUnit unit) {
        return timer(delay, unit, Schedulers.computation());
    }

    private static NullPointerException toNpe(Throwable ex) {
        NullPointerException nullPointerException = new NullPointerException("Actually not, but can't pass out an exception otherwise...");
        nullPointerException.initCause(ex);
        return nullPointerException;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable unsafeCreate(@NonNull CompletableSource onSubscribe) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        if (onSubscribe instanceof Completable) {
            throw new IllegalArgumentException("Use of unsafeCreate(Completable)!");
        }
        return RxJavaPlugins.onAssembly(new CompletableFromUnsafeSource(onSubscribe));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <R> Completable using(@NonNull Supplier<R> resourceSupplier, @NonNull Function<? super R, ? extends CompletableSource> sourceSupplier, @NonNull Consumer<? super R> resourceCleanup) {
        return using(resourceSupplier, sourceSupplier, resourceCleanup, true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable wrap(@NonNull CompletableSource source) {
        Objects.requireNonNull(source, "source is null");
        return source instanceof Completable ? RxJavaPlugins.onAssembly((Completable) source) : RxJavaPlugins.onAssembly(new CompletableFromUnsafeSource(source));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable ambWith(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return ambArray(this, other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Observable<T> andThen(@NonNull ObservableSource<T> next) {
        Objects.requireNonNull(next, "next is null");
        return RxJavaPlugins.onAssembly(new CompletableAndThenObservable(this, next));
    }

    @SchedulerSupport("none")
    public final void blockingAwait() {
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        blockingMultiObserver.blockingGet();
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe() {
        blockingSubscribe(Functions.EMPTY_ACTION, Functions.ERROR_CONSUMER);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable cache() {
        return RxJavaPlugins.onAssembly(new CompletableCache(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable compose(@NonNull CompletableTransformer transformer) {
        Objects.requireNonNull(transformer, "transformer is null");
        return wrap(transformer.apply(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable concatWith(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new CompletableAndThenCompletable(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Completable delay(long time, @NonNull TimeUnit unit) {
        return delay(time, unit, Schedulers.computation(), false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Completable delaySubscription(long time, @NonNull TimeUnit unit) {
        return delaySubscription(time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doAfterTerminate(@NonNull Action onAfterTerminate) {
        Consumer<? super Disposable> consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer<? super Throwable> consumerEmptyConsumer2 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnLifecycle(consumerEmptyConsumer, consumerEmptyConsumer2, action, action, onAfterTerminate, action);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doFinally(@NonNull Action onFinally) {
        Objects.requireNonNull(onFinally, "onFinally is null");
        return RxJavaPlugins.onAssembly(new CompletableDoFinally(this, onFinally));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnComplete(@NonNull Action onComplete) {
        Consumer<? super Disposable> consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer<? super Throwable> consumerEmptyConsumer2 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnLifecycle(consumerEmptyConsumer, consumerEmptyConsumer2, onComplete, action, action, action);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnDispose(@NonNull Action onDispose) {
        Consumer<? super Disposable> consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer<? super Throwable> consumerEmptyConsumer2 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnLifecycle(consumerEmptyConsumer, consumerEmptyConsumer2, action, action, action, onDispose);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnError(@NonNull Consumer<? super Throwable> onError) {
        Consumer<? super Disposable> consumerEmptyConsumer = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnLifecycle(consumerEmptyConsumer, onError, action, action, action, action);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnEvent(@NonNull Consumer<? super Throwable> onEvent) {
        Objects.requireNonNull(onEvent, "onEvent is null");
        return RxJavaPlugins.onAssembly(new CompletableDoOnEvent(this, onEvent));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnLifecycle(@NonNull Consumer<? super Disposable> onSubscribe, @NonNull Action onDispose) {
        Consumer<? super Throwable> consumerEmptyConsumer = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnLifecycle(onSubscribe, consumerEmptyConsumer, action, action, action, onDispose);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnSubscribe(@NonNull Consumer<? super Disposable> onSubscribe) {
        Consumer<? super Throwable> consumerEmptyConsumer = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnLifecycle(onSubscribe, consumerEmptyConsumer, action, action, action, action);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable doOnTerminate(@NonNull Action onTerminate) {
        Consumer<? super Disposable> consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer<? super Throwable> consumerEmptyConsumer2 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnLifecycle(consumerEmptyConsumer, consumerEmptyConsumer2, action, onTerminate, action, action);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable hide() {
        return RxJavaPlugins.onAssembly(new CompletableHide(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable lift(@NonNull CompletableOperator onLift) {
        Objects.requireNonNull(onLift, "onLift is null");
        return RxJavaPlugins.onAssembly(new CompletableLift(this, onLift));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Single<Notification<T>> materialize() {
        return RxJavaPlugins.onAssembly(new CompletableMaterialize(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable mergeWith(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return mergeArray(this, other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable observeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableObserveOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable onErrorComplete() {
        return onErrorComplete(Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable onErrorResumeNext(@NonNull Function<? super Throwable, ? extends CompletableSource> fallbackSupplier) {
        Objects.requireNonNull(fallbackSupplier, "fallbackSupplier is null");
        return RxJavaPlugins.onAssembly(new CompletableResumeNext(this, fallbackSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable onErrorResumeWith(@NonNull CompletableSource fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return onErrorResumeNext(Functions.justFunction(fallback));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Maybe<T> onErrorReturn(@NonNull Function<? super Throwable, ? extends T> itemSupplier) {
        Objects.requireNonNull(itemSupplier, "itemSupplier is null");
        return RxJavaPlugins.onAssembly(new CompletableOnErrorReturn(this, itemSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Maybe<T> onErrorReturnItem(@NonNull T item) {
        Objects.requireNonNull(item, "item is null");
        return onErrorReturn(Functions.justFunction(item));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable onTerminateDetach() {
        return RxJavaPlugins.onAssembly(new CompletableDetach(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable repeat() {
        return fromPublisher(toFlowable().repeat());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable repeatUntil(@NonNull BooleanSupplier stop) {
        return fromPublisher(toFlowable().repeatUntil(stop));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable repeatWhen(@NonNull Function<? super Flowable<Object>, ? extends Publisher<?>> handler) {
        return fromPublisher(toFlowable().repeatWhen(handler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retry() {
        return fromPublisher(toFlowable().retry());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retryUntil(@NonNull BooleanSupplier stop) {
        Objects.requireNonNull(stop, "stop is null");
        return retry(Long.MAX_VALUE, Functions.predicateReverseFor(stop));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retryWhen(@NonNull Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler) {
        return fromPublisher(toFlowable().retryWhen(handler));
    }

    @SchedulerSupport("none")
    public final void safeSubscribe(@NonNull CompletableObserver observer) {
        Objects.requireNonNull(observer, "observer is null");
        subscribe(new SafeCompletableObserver(observer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable startWith(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return concatArray(other, this);
    }

    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe() {
        EmptyCompletableObserver emptyCompletableObserver = new EmptyCompletableObserver();
        subscribe(emptyCompletableObserver);
        return emptyCompletableObserver;
    }

    public abstract void subscribeActual(@NonNull CompletableObserver observer);

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable subscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableSubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <E extends CompletableObserver> E subscribeWith(E observer) {
        subscribe(observer);
        return observer;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable takeUntil(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new CompletableTakeUntilCompletable(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final TestObserver<Void> test() {
        TestObserver<Void> testObserver = new TestObserver<>();
        subscribe(testObserver);
        return testObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Completable timeout(long timeout, @NonNull TimeUnit unit) {
        return timeout0(timeout, unit, Schedulers.computation(), null);
    }

    @CheckReturnValue
    @SchedulerSupport("none")
    public final <R> R to(@NonNull CompletableConverter<? extends R> converter) {
        Objects.requireNonNull(converter, "converter is null");
        return converter.apply(this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> CompletionStage<T> toCompletionStage(T defaultItem) {
        return (CompletionStage) subscribeWith(new CompletionStageConsumer(true, defaultItem));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <T> Flowable<T> toFlowable() {
        return this instanceof FuseToFlowable ? ((FuseToFlowable) this).fuseToFlowable() : RxJavaPlugins.onAssembly(new CompletableToFlowable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Future<Void> toFuture() {
        return (Future) subscribeWith(new FutureMultiObserver());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Maybe<T> toMaybe() {
        return this instanceof FuseToMaybe ? ((FuseToMaybe) this).fuseToMaybe() : RxJavaPlugins.onAssembly(new MaybeFromCompletable(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Observable<T> toObservable() {
        return this instanceof FuseToObservable ? ((FuseToObservable) this).fuseToObservable() : RxJavaPlugins.onAssembly(new CompletableToObservable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Single<T> toSingle(@NonNull Supplier<? extends T> completionValueSupplier) {
        Objects.requireNonNull(completionValueSupplier, "completionValueSupplier is null");
        return RxJavaPlugins.onAssembly(new CompletableToSingle(this, completionValueSupplier, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Single<T> toSingleDefault(T completionValue) {
        Objects.requireNonNull(completionValue, "completionValue is null");
        return RxJavaPlugins.onAssembly(new CompletableToSingle(this, null, completionValue));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable unsubscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableDisposeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable concatDelayError(@NonNull Publisher<? extends CompletableSource> sources) {
        return concatDelayError(sources, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    private Completable doOnLifecycle(final Consumer<? super Disposable> onSubscribe, final Consumer<? super Throwable> onError, final Action onComplete, final Action onTerminate, final Action onAfterTerminate, final Action onDispose) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        Objects.requireNonNull(onTerminate, "onTerminate is null");
        Objects.requireNonNull(onAfterTerminate, "onAfterTerminate is null");
        Objects.requireNonNull(onDispose, "onDispose is null");
        return RxJavaPlugins.onAssembly(new CompletablePeek(this, onSubscribe, onError, onComplete, onTerminate, onAfterTerminate, onDispose));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public static Completable timer(long delay, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableTimer(delay, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <R> Completable using(@NonNull Supplier<R> resourceSupplier, @NonNull Function<? super R, ? extends CompletableSource> sourceSupplier, @NonNull Consumer<? super R> resourceCleanup, boolean eager) {
        Objects.requireNonNull(resourceSupplier, "resourceSupplier is null");
        Objects.requireNonNull(sourceSupplier, "sourceSupplier is null");
        Objects.requireNonNull(resourceCleanup, "resourceCleanup is null");
        return RxJavaPlugins.onAssembly(new CompletableUsing(resourceSupplier, sourceSupplier, resourceCleanup, eager));
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Action onComplete) {
        blockingSubscribe(onComplete, Functions.ERROR_CONSUMER);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable delay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return delay(time, unit, scheduler, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable delaySubscription(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return timer(time, unit, scheduler).andThen(this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable onErrorComplete(@NonNull Predicate<? super Throwable> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new CompletableOnErrorComplete(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable repeat(long times) {
        return fromPublisher(toFlowable().repeat(times));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retry(@NonNull BiPredicate<? super Integer, ? super Throwable> predicate) {
        return fromPublisher(toFlowable().retry(predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Completable timeout(long timeout, @NonNull TimeUnit unit, @NonNull CompletableSource fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(timeout, unit, Schedulers.computation(), fallback);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable concat(@NonNull Publisher<? extends CompletableSource> sources) {
        return concat(sources, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable concatDelayError(@NonNull Publisher<? extends CompletableSource> sources, int prefetch) {
        return Flowable.fromPublisher(sources).concatMapCompletableDelayError(Functions.identity(), true, prefetch);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Completable error(@NonNull Throwable throwable) {
        Objects.requireNonNull(throwable, "throwable is null");
        return RxJavaPlugins.onAssembly(new CompletableError(throwable));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static Completable merge(@NonNull Publisher<? extends CompletableSource> sources) {
        return merge0(sources, Integer.MAX_VALUE, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static Completable mergeDelayError(@NonNull Publisher<? extends CompletableSource> sources) {
        return merge0(sources, Integer.MAX_VALUE, true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <T> Flowable<T> andThen(@NonNull Publisher<T> next) {
        Objects.requireNonNull(next, "next is null");
        return RxJavaPlugins.onAssembly(new CompletableAndThenPublisher(this, next));
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Action onComplete, @NonNull Consumer<? super Throwable> onError) {
        Objects.requireNonNull(onComplete, "onComplete is null");
        Objects.requireNonNull(onError, "onError is null");
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        blockingMultiObserver.blockingConsume(Functions.emptyConsumer(), onError, onComplete);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable delay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableDelay(this, time, unit, scheduler, delayError));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retry(long times) {
        return fromPublisher(toFlowable().retry(times));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <T> Flowable<T> startWith(@NonNull SingleSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return Flowable.concat(Single.wrap(other).toFlowable(), toFlowable());
    }

    @Override // io.reactivex.rxjava3.core.CompletableSource
    @SchedulerSupport("none")
    public final void subscribe(@NonNull CompletableObserver observer) {
        Objects.requireNonNull(observer, "observer is null");
        try {
            CompletableObserver completableObserverOnSubscribe = RxJavaPlugins.onSubscribe(this, observer);
            Objects.requireNonNull(completableObserverOnSubscribe, "The RxJavaPlugins.onSubscribe hook returned a null CompletableObserver. Please check the handler provided to RxJavaPlugins.setOnCompletableSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
            subscribeActual(completableObserverOnSubscribe);
        } catch (NullPointerException e2) {
            throw e2;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
            throw toNpe(th);
        }
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final TestObserver<Void> test(boolean dispose) {
        TestObserver<Void> testObserver = new TestObserver<>();
        if (dispose) {
            testObserver.dispose();
        }
        subscribe(testObserver);
        return testObserver;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable concat(@NonNull Publisher<? extends CompletableSource> sources, int prefetch) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new CompletableConcat(sources, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable merge(@NonNull Publisher<? extends CompletableSource> sources, int maxConcurrency) {
        return merge0(sources, maxConcurrency, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Completable mergeDelayError(@NonNull Publisher<? extends CompletableSource> sources, int maxConcurrency) {
        return merge0(sources, maxConcurrency, true);
    }

    @CheckReturnValue
    @SchedulerSupport("none")
    public final boolean blockingAwait(long timeout, @NonNull TimeUnit unit) {
        Objects.requireNonNull(unit, "unit is null");
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        return blockingMultiObserver.blockingAwait(timeout, unit);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retry(long times, @NonNull Predicate<? super Throwable> predicate) {
        return fromPublisher(toFlowable().retry(times, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable timeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return timeout0(timeout, unit, scheduler, null);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Single<T> andThen(@NonNull SingleSource<T> next) {
        Objects.requireNonNull(next, "next is null");
        return RxJavaPlugins.onAssembly(new SingleDelayWithCompletable(next, this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable retry(@NonNull Predicate<? super Throwable> predicate) {
        return fromPublisher(toFlowable().retry(predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <T> Flowable<T> startWith(@NonNull MaybeSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return Flowable.concat(Maybe.wrap(other).toFlowable(), toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Completable timeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, @NonNull CompletableSource fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(timeout, unit, scheduler, fallback);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Maybe<T> andThen(@NonNull MaybeSource<T> next) {
        Objects.requireNonNull(next, "next is null");
        return RxJavaPlugins.onAssembly(new MaybeDelayWithCompletable(next, this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T> Observable<T> startWith(@NonNull ObservableSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return Observable.wrap(other).concatWith(toObservable());
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull CompletableObserver observer) {
        Objects.requireNonNull(observer, "observer is null");
        BlockingDisposableMultiObserver blockingDisposableMultiObserver = new BlockingDisposableMultiObserver();
        observer.onSubscribe(blockingDisposableMultiObserver);
        subscribe(blockingDisposableMultiObserver);
        blockingDisposableMultiObserver.blockingConsume(observer);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable andThen(@NonNull CompletableSource next) {
        Objects.requireNonNull(next, "next is null");
        return RxJavaPlugins.onAssembly(new CompletableAndThenCompletable(this, next));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <T> Flowable<T> startWith(@NonNull Publisher<T> other) {
        Objects.requireNonNull(other, "other is null");
        return toFlowable().startWith(other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Action onComplete, @NonNull Consumer<? super Throwable> onError) {
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        CallbackCompletableObserver callbackCompletableObserver = new CallbackCompletableObserver(onError, onComplete);
        subscribe(callbackCompletableObserver);
        return callbackCompletableObserver;
    }

    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Action onComplete, @NonNull Consumer<? super Throwable> onError, @NonNull DisposableContainer container) {
        Objects.requireNonNull(onComplete, "onComplete is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(container, "container is null");
        DisposableAutoReleaseMultiObserver disposableAutoReleaseMultiObserver = new DisposableAutoReleaseMultiObserver(container, Functions.emptyConsumer(), onError, onComplete);
        container.add(disposableAutoReleaseMultiObserver);
        subscribe(disposableAutoReleaseMultiObserver);
        return disposableAutoReleaseMultiObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Action onComplete) {
        return subscribe(onComplete, Functions.ON_ERROR_MISSING);
    }
}
