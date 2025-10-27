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
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Function3;
import io.reactivex.rxjava3.functions.Function4;
import io.reactivex.rxjava3.functions.Function5;
import io.reactivex.rxjava3.functions.Function6;
import io.reactivex.rxjava3.functions.Function7;
import io.reactivex.rxjava3.functions.Function8;
import io.reactivex.rxjava3.functions.Function9;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.fuseable.FuseToFlowable;
import io.reactivex.rxjava3.internal.fuseable.FuseToMaybe;
import io.reactivex.rxjava3.internal.fuseable.FuseToObservable;
import io.reactivex.rxjava3.internal.jdk8.CompletionStageConsumer;
import io.reactivex.rxjava3.internal.jdk8.SingleFlattenStreamAsFlowable;
import io.reactivex.rxjava3.internal.jdk8.SingleFlattenStreamAsObservable;
import io.reactivex.rxjava3.internal.jdk8.SingleFromCompletionStage;
import io.reactivex.rxjava3.internal.jdk8.SingleMapOptional;
import io.reactivex.rxjava3.internal.observers.BiConsumerSingleObserver;
import io.reactivex.rxjava3.internal.observers.BlockingDisposableMultiObserver;
import io.reactivex.rxjava3.internal.observers.BlockingMultiObserver;
import io.reactivex.rxjava3.internal.observers.ConsumerSingleObserver;
import io.reactivex.rxjava3.internal.observers.DisposableAutoReleaseMultiObserver;
import io.reactivex.rxjava3.internal.observers.FutureMultiObserver;
import io.reactivex.rxjava3.internal.observers.SafeSingleObserver;
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromSingle;
import io.reactivex.rxjava3.internal.operators.completable.CompletableToFlowable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapSinglePublisher;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSingleSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFilterSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToSingle;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapSinglePublisher;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapSinglePublisher;
import io.reactivex.rxjava3.internal.operators.mixed.ObservableConcatMapSingle;
import io.reactivex.rxjava3.internal.operators.mixed.SingleFlatMapObservable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSingleSingle;
import io.reactivex.rxjava3.internal.operators.single.SingleAmb;
import io.reactivex.rxjava3.internal.operators.single.SingleCache;
import io.reactivex.rxjava3.internal.operators.single.SingleContains;
import io.reactivex.rxjava3.internal.operators.single.SingleCreate;
import io.reactivex.rxjava3.internal.operators.single.SingleDefer;
import io.reactivex.rxjava3.internal.operators.single.SingleDelay;
import io.reactivex.rxjava3.internal.operators.single.SingleDelayWithCompletable;
import io.reactivex.rxjava3.internal.operators.single.SingleDelayWithObservable;
import io.reactivex.rxjava3.internal.operators.single.SingleDelayWithPublisher;
import io.reactivex.rxjava3.internal.operators.single.SingleDelayWithSingle;
import io.reactivex.rxjava3.internal.operators.single.SingleDematerialize;
import io.reactivex.rxjava3.internal.operators.single.SingleDetach;
import io.reactivex.rxjava3.internal.operators.single.SingleDoAfterSuccess;
import io.reactivex.rxjava3.internal.operators.single.SingleDoAfterTerminate;
import io.reactivex.rxjava3.internal.operators.single.SingleDoFinally;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnDispose;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnError;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnEvent;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnLifecycle;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnSubscribe;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnSuccess;
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnTerminate;
import io.reactivex.rxjava3.internal.operators.single.SingleEquals;
import io.reactivex.rxjava3.internal.operators.single.SingleError;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMap;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapBiSelector;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapCompletable;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapIterableFlowable;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapIterableObservable;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapMaybe;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapNotification;
import io.reactivex.rxjava3.internal.operators.single.SingleFlatMapPublisher;
import io.reactivex.rxjava3.internal.operators.single.SingleFromCallable;
import io.reactivex.rxjava3.internal.operators.single.SingleFromPublisher;
import io.reactivex.rxjava3.internal.operators.single.SingleFromSupplier;
import io.reactivex.rxjava3.internal.operators.single.SingleFromUnsafeSource;
import io.reactivex.rxjava3.internal.operators.single.SingleHide;
import io.reactivex.rxjava3.internal.operators.single.SingleInternalHelper;
import io.reactivex.rxjava3.internal.operators.single.SingleJust;
import io.reactivex.rxjava3.internal.operators.single.SingleLift;
import io.reactivex.rxjava3.internal.operators.single.SingleMap;
import io.reactivex.rxjava3.internal.operators.single.SingleMaterialize;
import io.reactivex.rxjava3.internal.operators.single.SingleNever;
import io.reactivex.rxjava3.internal.operators.single.SingleObserveOn;
import io.reactivex.rxjava3.internal.operators.single.SingleOnErrorComplete;
import io.reactivex.rxjava3.internal.operators.single.SingleOnErrorReturn;
import io.reactivex.rxjava3.internal.operators.single.SingleResumeNext;
import io.reactivex.rxjava3.internal.operators.single.SingleSubscribeOn;
import io.reactivex.rxjava3.internal.operators.single.SingleTakeUntil;
import io.reactivex.rxjava3.internal.operators.single.SingleTimeInterval;
import io.reactivex.rxjava3.internal.operators.single.SingleTimeout;
import io.reactivex.rxjava3.internal.operators.single.SingleTimer;
import io.reactivex.rxjava3.internal.operators.single.SingleToFlowable;
import io.reactivex.rxjava3.internal.operators.single.SingleToObservable;
import io.reactivex.rxjava3.internal.operators.single.SingleUnsubscribeOn;
import io.reactivex.rxjava3.internal.operators.single.SingleUsing;
import io.reactivex.rxjava3.internal.operators.single.SingleZipArray;
import io.reactivex.rxjava3.internal.operators.single.SingleZipIterable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;

/* loaded from: classes8.dex */
public abstract class Single<T> implements SingleSource<T> {
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> amb(@NonNull Iterable<? extends SingleSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new SingleAmb(null, sources));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Single<T> ambArray(@NonNull SingleSource<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? error(SingleInternalHelper.emptyThrower()) : sources.length == 1 ? wrap(sources[0]) : RxJavaPlugins.onAssembly(new SingleAmb(sources, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Iterable<? extends SingleSource<? extends T>> sources) {
        return Flowable.fromIterable(sources).concatMapSingleDelayError(Functions.identity(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArray(@NonNull SingleSource<? extends T>... sources) {
        return Flowable.fromArray(sources).concatMapSingleDelayError(Functions.identity(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayDelayError(@NonNull SingleSource<? extends T>... sources) {
        return Flowable.fromArray(sources).concatMapSingleDelayError(Functions.identity(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEager(@NonNull SingleSource<? extends T>... sources) {
        return Flowable.fromArray(sources).concatMapEager(SingleInternalHelper.toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEagerDelayError(@NonNull SingleSource<? extends T>... sources) {
        return Flowable.fromArray(sources).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Iterable<? extends SingleSource<? extends T>> sources) {
        return Flowable.fromIterable(sources).concatMapSingleDelayError(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Iterable<? extends SingleSource<? extends T>> sources) {
        return Flowable.fromIterable(sources).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Iterable<? extends SingleSource<? extends T>> sources) {
        return Flowable.fromIterable(sources).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> create(@NonNull SingleOnSubscribe<T> source) {
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new SingleCreate(source));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> defer(@NonNull Supplier<? extends SingleSource<? extends T>> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new SingleDefer(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> error(@NonNull Supplier<? extends Throwable> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new SingleError(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromCallable(@NonNull Callable<? extends T> callable) {
        Objects.requireNonNull(callable, "callable is null");
        return RxJavaPlugins.onAssembly(new SingleFromCallable(callable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromCompletionStage(@NonNull CompletionStage<T> stage) {
        Objects.requireNonNull(stage, "stage is null");
        return RxJavaPlugins.onAssembly(new SingleFromCompletionStage(stage));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromFuture(@NonNull Future<? extends T> future) {
        return toSingle(Flowable.fromFuture(future));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromMaybe(@NonNull MaybeSource<T> maybe) {
        Objects.requireNonNull(maybe, "maybe is null");
        return RxJavaPlugins.onAssembly(new MaybeToSingle(maybe, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromObservable(@NonNull ObservableSource<? extends T> observable) {
        Objects.requireNonNull(observable, "observable is null");
        return RxJavaPlugins.onAssembly(new ObservableSingleSingle(observable, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static <T> Single<T> fromPublisher(@NonNull Publisher<? extends T> publisher) {
        Objects.requireNonNull(publisher, "publisher is null");
        return RxJavaPlugins.onAssembly(new SingleFromPublisher(publisher));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromSupplier(@NonNull Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new SingleFromSupplier(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> just(T item) {
        Objects.requireNonNull(item, "item is null");
        return RxJavaPlugins.onAssembly(new SingleJust(item));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Iterable<? extends SingleSource<? extends T>> sources) {
        return Flowable.fromIterable(sources).flatMapSingle(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArray(SingleSource<? extends T>... sources) {
        return Flowable.fromArray(sources).flatMapSingle(Functions.identity(), false, Math.max(1, sources.length));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArrayDelayError(@NonNull SingleSource<? extends T>... sources) {
        return Flowable.fromArray(sources).flatMapSingle(Functions.identity(), true, Math.max(1, sources.length));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Iterable<? extends SingleSource<? extends T>> sources) {
        return Flowable.fromIterable(sources).flatMapSingle(Functions.identity(), true, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> never() {
        return RxJavaPlugins.onAssembly(SingleNever.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull SingleSource<? extends T> source1, @NonNull SingleSource<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return RxJavaPlugins.onAssembly(new SingleEquals(source1, source2));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNext(@NonNull Publisher<? extends SingleSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapSinglePublisher(sources, Functions.identity(), false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNextDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapSinglePublisher(sources, Functions.identity(), true));
    }

    private Single<T> timeout0(final long timeout, final TimeUnit unit, final Scheduler scheduler, final SingleSource<? extends T> fallback) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleTimeout(this, timeout, unit, scheduler, fallback));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public static Single<Long> timer(long delay, @NonNull TimeUnit unit) {
        return timer(delay, unit, Schedulers.computation());
    }

    @NonNull
    private static <T> Single<T> toSingle(@NonNull Flowable<T> source) {
        return RxJavaPlugins.onAssembly(new FlowableSingleSingle(source, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> unsafeCreate(@NonNull SingleSource<T> onSubscribe) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        if (onSubscribe instanceof Single) {
            throw new IllegalArgumentException("unsafeCreate(Single) should be upgraded");
        }
        return RxJavaPlugins.onAssembly(new SingleFromUnsafeSource(onSubscribe));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, U> Single<T> using(@NonNull Supplier<U> resourceSupplier, @NonNull Function<? super U, ? extends SingleSource<? extends T>> sourceSupplier, @NonNull Consumer<? super U> resourceCleanup) {
        return using(resourceSupplier, sourceSupplier, resourceCleanup, true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> wrap(@NonNull SingleSource<T> source) {
        Objects.requireNonNull(source, "source is null");
        return source instanceof Single ? RxJavaPlugins.onAssembly((Single) source) : RxJavaPlugins.onAssembly(new SingleFromUnsafeSource(source));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Single<R> zip(@NonNull Iterable<? extends SingleSource<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> zipper) {
        Objects.requireNonNull(zipper, "zipper is null");
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new SingleZipIterable(sources, zipper));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T, R> Single<R> zipArray(@NonNull Function<? super Object[], ? extends R> zipper, @NonNull SingleSource<? extends T>... sources) {
        Objects.requireNonNull(zipper, "zipper is null");
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? error(new NoSuchElementException()) : RxJavaPlugins.onAssembly(new SingleZipArray(sources, zipper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> ambWith(@NonNull SingleSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return ambArray(this, other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final T blockingGet() {
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        return (T) blockingMultiObserver.blockingGet();
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe() {
        blockingSubscribe(Functions.emptyConsumer(), Functions.ERROR_CONSUMER);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> cache() {
        return RxJavaPlugins.onAssembly(new SingleCache(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Single<U> cast(@NonNull Class<? extends U> cls) {
        Objects.requireNonNull(cls, "clazz is null");
        return (Single<U>) map(Functions.castFunction(cls));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> compose(@NonNull SingleTransformer<? super T, ? extends R> transformer) {
        Objects.requireNonNull(transformer, "transformer is null");
        return wrap(transformer.apply(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> concatMap(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMap(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable concatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        return flatMapCompletable(mapper);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> concatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        return flatMapMaybe(mapper);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> concatWith(@NonNull SingleSource<? extends T> other) {
        return concat(this, other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> contains(@NonNull Object item) {
        return contains(item, ObjectHelper.equalsPredicate());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<T> delay(long time, @NonNull TimeUnit unit) {
        return delay(time, unit, Schedulers.computation(), false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> delaySubscription(@NonNull CompletableSource subscriptionIndicator) {
        Objects.requireNonNull(subscriptionIndicator, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new SingleDelayWithCompletable(this, subscriptionIndicator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> dematerialize(@NonNull Function<? super T, Notification<R>> selector) {
        Objects.requireNonNull(selector, "selector is null");
        return RxJavaPlugins.onAssembly(new SingleDematerialize(this, selector));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doAfterSuccess(@NonNull Consumer<? super T> onAfterSuccess) {
        Objects.requireNonNull(onAfterSuccess, "onAfterSuccess is null");
        return RxJavaPlugins.onAssembly(new SingleDoAfterSuccess(this, onAfterSuccess));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doAfterTerminate(@NonNull Action onAfterTerminate) {
        Objects.requireNonNull(onAfterTerminate, "onAfterTerminate is null");
        return RxJavaPlugins.onAssembly(new SingleDoAfterTerminate(this, onAfterTerminate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doFinally(@NonNull Action onFinally) {
        Objects.requireNonNull(onFinally, "onFinally is null");
        return RxJavaPlugins.onAssembly(new SingleDoFinally(this, onFinally));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnDispose(@NonNull Action onDispose) {
        Objects.requireNonNull(onDispose, "onDispose is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnDispose(this, onDispose));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnError(@NonNull Consumer<? super Throwable> onError) {
        Objects.requireNonNull(onError, "onError is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnError(this, onError));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnEvent(@NonNull BiConsumer<? super T, ? super Throwable> onEvent) {
        Objects.requireNonNull(onEvent, "onEvent is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnEvent(this, onEvent));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnLifecycle(@NonNull Consumer<? super Disposable> onSubscribe, @NonNull Action onDispose) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        Objects.requireNonNull(onDispose, "onDispose is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnLifecycle(this, onSubscribe, onDispose));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnSubscribe(@NonNull Consumer<? super Disposable> onSubscribe) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnSubscribe(this, onSubscribe));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnSuccess(@NonNull Consumer<? super T> onSuccess) {
        Objects.requireNonNull(onSuccess, "onSuccess is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnSuccess(this, onSuccess));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> doOnTerminate(@NonNull Action onTerminate) {
        Objects.requireNonNull(onTerminate, "onTerminate is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnTerminate(this, onTerminate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> filter(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new MaybeFilterSingle(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> flatMap(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMap(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable flatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapCompletable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> flatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapMaybe(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMapObservable(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapObservable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMapPublisher(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapPublisher(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Flowable<U> flattenAsFlowable(@NonNull Function<? super T, ? extends Iterable<? extends U>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapIterableFlowable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<U> flattenAsObservable(@NonNull Function<? super T, ? extends Iterable<? extends U>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapIterableObservable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flattenStreamAsFlowable(@NonNull Function<? super T, ? extends Stream<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlattenStreamAsFlowable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flattenStreamAsObservable(@NonNull Function<? super T, ? extends Stream<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlattenStreamAsObservable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> hide() {
        return RxJavaPlugins.onAssembly(new SingleHide(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable ignoreElement() {
        return RxJavaPlugins.onAssembly(new CompletableFromSingle(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> lift(@NonNull SingleOperator<? extends R, ? super T> lift) {
        Objects.requireNonNull(lift, "lift is null");
        return RxJavaPlugins.onAssembly(new SingleLift(this, lift));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> map(@NonNull Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleMap(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> mapOptional(@NonNull Function<? super T, Optional<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleMapOptional(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Notification<T>> materialize() {
        return RxJavaPlugins.onAssembly(new SingleMaterialize(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> mergeWith(@NonNull SingleSource<? extends T> other) {
        return merge(this, other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> observeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleObserveOn(this, scheduler));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Maybe<U> ofType(@NonNull Class<U> clazz) {
        Objects.requireNonNull(clazz, "clazz is null");
        return filter(Functions.isInstanceOf(clazz)).cast(clazz);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorComplete() {
        return onErrorComplete(Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> onErrorResumeNext(@NonNull Function<? super Throwable, ? extends SingleSource<? extends T>> fallbackSupplier) {
        Objects.requireNonNull(fallbackSupplier, "fallbackSupplier is null");
        return RxJavaPlugins.onAssembly(new SingleResumeNext(this, fallbackSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> onErrorResumeWith(@NonNull SingleSource<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return onErrorResumeNext(Functions.justFunction(fallback));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> onErrorReturn(@NonNull Function<Throwable, ? extends T> itemSupplier) {
        Objects.requireNonNull(itemSupplier, "itemSupplier is null");
        return RxJavaPlugins.onAssembly(new SingleOnErrorReturn(this, itemSupplier, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> onErrorReturnItem(@NonNull T item) {
        Objects.requireNonNull(item, "item is null");
        return RxJavaPlugins.onAssembly(new SingleOnErrorReturn(this, null, item));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> onTerminateDetach() {
        return RxJavaPlugins.onAssembly(new SingleDetach(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeat() {
        return toFlowable().repeat();
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeatUntil(@NonNull BooleanSupplier stop) {
        return toFlowable().repeatUntil(stop);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeatWhen(@NonNull Function<? super Flowable<Object>, ? extends Publisher<?>> handler) {
        return toFlowable().repeatWhen(handler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retry() {
        return toSingle(toFlowable().retry());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retryUntil(@NonNull BooleanSupplier stop) {
        Objects.requireNonNull(stop, "stop is null");
        return retry(Long.MAX_VALUE, Functions.predicateReverseFor(stop));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retryWhen(@NonNull Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler) {
        return toSingle(toFlowable().retryWhen(handler));
    }

    @SchedulerSupport("none")
    public final void safeSubscribe(@NonNull SingleObserver<? super T> observer) {
        Objects.requireNonNull(observer, "observer is null");
        subscribe(new SafeSingleObserver(observer));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return Flowable.concat(Completable.wrap(other).toFlowable(), toFlowable());
    }

    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe() {
        return subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING);
    }

    public abstract void subscribeActual(@NonNull SingleObserver<? super T> observer);

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> subscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleSubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <E extends SingleObserver<? super T>> E subscribeWith(E observer) {
        subscribe(observer);
        return observer;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> takeUntil(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return takeUntil(new CompletableToFlowable(other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final TestObserver<T> test() {
        TestObserver<T> testObserver = new TestObserver<>();
        subscribe(testObserver);
        return testObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<Timed<T>> timeInterval() {
        return timeInterval(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<T> timeout(long timeout, @NonNull TimeUnit unit) {
        return timeout0(timeout, unit, Schedulers.computation(), null);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<Timed<T>> timestamp() {
        return timestamp(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @SchedulerSupport("none")
    public final <R> R to(@NonNull SingleConverter<T, ? extends R> converter) {
        Objects.requireNonNull(converter, "converter is null");
        return converter.apply(this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final CompletionStage<T> toCompletionStage() {
        return (CompletionStage) subscribeWith(new CompletionStageConsumer(false, null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> toFlowable() {
        return this instanceof FuseToFlowable ? ((FuseToFlowable) this).fuseToFlowable() : RxJavaPlugins.onAssembly(new SingleToFlowable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Future<T> toFuture() {
        return (Future) subscribeWith(new FutureMultiObserver());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> toMaybe() {
        return this instanceof FuseToMaybe ? ((FuseToMaybe) this).fuseToMaybe() : RxJavaPlugins.onAssembly(new MaybeFromSingle(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> toObservable() {
        return this instanceof FuseToObservable ? ((FuseToObservable) this).fuseToObservable() : RxJavaPlugins.onAssembly(new SingleToObservable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> unsubscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleUnsubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Single<R> zipWith(@NonNull SingleSource<U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> zipper) {
        return zip(this, other, zipper);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concat(@NonNull ObservableSource<? extends SingleSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapSingle(sources, Functions.identity(), ErrorMode.IMMEDIATE, 2));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> sources) {
        return Flowable.fromPublisher(sources).concatMapSingleDelayError(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Iterable<? extends SingleSource<? extends T>> sources, int maxConcurrency) {
        return Flowable.fromIterable(sources).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), false, maxConcurrency, 1);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Iterable<? extends SingleSource<? extends T>> sources, int maxConcurrency) {
        return Flowable.fromIterable(sources).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), true, maxConcurrency, 1);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromFuture(@NonNull Future<? extends T> future, long timeout, @NonNull TimeUnit unit) {
        return toSingle(Flowable.fromFuture(future, timeout, unit));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Publisher<? extends SingleSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapSinglePublisher(sources, Functions.identity(), false, Integer.MAX_VALUE));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapSinglePublisher(sources, Functions.identity(), true, Integer.MAX_VALUE));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public static Single<Long> timer(long delay, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleTimer(delay, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, U> Single<T> using(@NonNull Supplier<U> resourceSupplier, @NonNull Function<? super U, ? extends SingleSource<? extends T>> sourceSupplier, @NonNull Consumer<? super U> resourceCleanup, boolean eager) {
        Objects.requireNonNull(resourceSupplier, "resourceSupplier is null");
        Objects.requireNonNull(sourceSupplier, "sourceSupplier is null");
        Objects.requireNonNull(resourceCleanup, "resourceCleanup is null");
        return RxJavaPlugins.onAssembly(new SingleUsing(resourceSupplier, sourceSupplier, resourceCleanup, eager));
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onSuccess) {
        blockingSubscribe(onSuccess, Functions.ERROR_CONSUMER);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> contains(@NonNull Object item, @NonNull BiPredicate<Object, Object> comparer) {
        Objects.requireNonNull(item, "item is null");
        Objects.requireNonNull(comparer, "comparer is null");
        return RxJavaPlugins.onAssembly(new SingleContains(this, item, comparer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<T> delay(long time, @NonNull TimeUnit unit, boolean delayError) {
        return delay(time, unit, Schedulers.computation(), delayError);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorComplete(@NonNull Predicate<? super Throwable> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new SingleOnErrorComplete(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeat(long times) {
        return toFlowable().repeat(times);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retry(long times) {
        return toSingle(toFlowable().retry(times));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull BiConsumer<? super T, ? super Throwable> onCallback) {
        Objects.requireNonNull(onCallback, "onCallback is null");
        BiConsumerSingleObserver biConsumerSingleObserver = new BiConsumerSingleObserver(onCallback);
        subscribe(biConsumerSingleObserver);
        return biConsumerSingleObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<Timed<T>> timeInterval(@NonNull Scheduler scheduler) {
        return timeInterval(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return timeout0(timeout, unit, scheduler, null);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<Timed<T>> timestamp(@NonNull Scheduler scheduler) {
        return timestamp(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> sources, int prefetch) {
        return Flowable.fromPublisher(sources).concatMapSingleDelayError(Functions.identity(), true, prefetch);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Publisher<? extends SingleSource<? extends T>> sources) {
        return Flowable.fromPublisher(sources).concatMapEager(SingleInternalHelper.toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> sources) {
        return Flowable.fromPublisher(sources).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> error(@NonNull Throwable throwable) {
        Objects.requireNonNull(throwable, "throwable is null");
        return error((Supplier<? extends Throwable>) Functions.justSupplier(throwable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> fromMaybe(@NonNull MaybeSource<T> maybe, @NonNull T defaultItem) {
        Objects.requireNonNull(maybe, "maybe is null");
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        return RxJavaPlugins.onAssembly(new MaybeToSingle(maybe, defaultItem));
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onSuccess, @NonNull Consumer<? super Throwable> onError) {
        Objects.requireNonNull(onSuccess, "onSuccess is null");
        Objects.requireNonNull(onError, "onError is null");
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        blockingMultiObserver.blockingConsume(onSuccess, onError, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> delay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return delay(time, unit, scheduler, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Single<T> delaySubscription(@NonNull SingleSource<U> subscriptionIndicator) {
        Objects.requireNonNull(subscriptionIndicator, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new SingleDelayWithSingle(this, subscriptionIndicator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Single<R> flatMap(@NonNull Function<? super T, ? extends SingleSource<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner) {
        Objects.requireNonNull(mapper, "mapper is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapBiSelector(this, mapper, combiner));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retry(@NonNull BiPredicate<? super Integer, ? super Throwable> predicate) {
        return toSingle(toFlowable().retry(predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull SingleSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return Flowable.concat(wrap(other).toFlowable(), toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <E> Single<T> takeUntil(@NonNull Publisher<E> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new SingleTakeUntil(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final TestObserver<T> test(boolean dispose) {
        TestObserver<T> testObserver = new TestObserver<>();
        if (dispose) {
            testObserver.dispose();
        }
        subscribe(testObserver);
        return testObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<Timed<T>> timeInterval(@NonNull TimeUnit unit) {
        return timeInterval(unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, @NonNull SingleSource<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(timeout, unit, scheduler, fallback);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<Timed<T>> timestamp(@NonNull TimeUnit unit) {
        return timestamp(unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends SingleSource<? extends T>> sources) {
        return concat(sources, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Publisher<? extends SingleSource<? extends T>> sources, int maxConcurrency) {
        return Flowable.fromPublisher(sources).concatMapEager(SingleInternalHelper.toFlowable(), maxConcurrency, 1);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Publisher<? extends SingleSource<? extends T>> sources, int maxConcurrency) {
        return Flowable.fromPublisher(sources).concatMapEagerDelayError(SingleInternalHelper.toFlowable(), true, maxConcurrency, 1);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<T> merge(@NonNull SingleSource<? extends SingleSource<? extends T>> source) {
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMap(source, Functions.identity()));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull SingleSource<? extends T> source1, @NonNull SingleSource<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return Flowable.fromArray(source1, source2).flatMapSingle(Functions.identity(), true, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, R> Single<R> zip(@NonNull SingleSource<? extends T1> source1, @NonNull SingleSource<? extends T2> source2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> delay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleDelay(this, time, unit, scheduler, delayError));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retry(long times, @NonNull Predicate<? super Throwable> predicate) {
        return toSingle(toFlowable().retry(times, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<Timed<T>> timeInterval(@NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleTimeInterval(this, unit, scheduler, true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<Timed<T>> timestamp(@NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleTimeInterval(this, unit, scheduler, false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends SingleSource<? extends T>> sources, int prefetch) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapSinglePublisher(sources, Functions.identity(), ErrorMode.IMMEDIATE, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Single<T> delaySubscription(@NonNull ObservableSource<U> subscriptionIndicator) {
        Objects.requireNonNull(subscriptionIndicator, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new SingleDelayWithObservable(this, subscriptionIndicator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> retry(@NonNull Predicate<? super Throwable> predicate) {
        return toSingle(toFlowable().retry(predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull MaybeSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return Flowable.concat(Maybe.wrap(other).toFlowable(), toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onSuccess) {
        return subscribe(onSuccess, Functions.ON_ERROR_MISSING);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <E> Single<T> takeUntil(@NonNull SingleSource<? extends E> other) {
        Objects.requireNonNull(other, "other is null");
        return takeUntil(new SingleToFlowable(other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull SingleSource<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(timeout, unit, Schedulers.computation(), fallback);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull SingleSource<? extends T> source1, @NonNull SingleSource<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return Flowable.fromArray(source1, source2).flatMapSingle(Functions.identity(), false, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> flatMap(@NonNull Function<? super T, ? extends SingleSource<? extends R>> onSuccessMapper, @NonNull Function<? super Throwable, ? extends SingleSource<? extends R>> onErrorMapper) {
        Objects.requireNonNull(onSuccessMapper, "onSuccessMapper is null");
        Objects.requireNonNull(onErrorMapper, "onErrorMapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapNotification(this, onSuccessMapper, onErrorMapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onSuccess, @NonNull Consumer<? super Throwable> onError) {
        Objects.requireNonNull(onSuccess, "onSuccess is null");
        Objects.requireNonNull(onError, "onError is null");
        ConsumerSingleObserver consumerSingleObserver = new ConsumerSingleObserver(onSuccess, onError);
        subscribe(consumerSingleObserver);
        return consumerSingleObserver;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull SingleSource<? extends T> source1, @NonNull SingleSource<? extends T> source2, @NonNull SingleSource<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return Flowable.fromArray(source1, source2, source3).flatMapSingle(Functions.identity(), true, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Single<T> delaySubscription(@NonNull Publisher<U> subscriptionIndicator) {
        Objects.requireNonNull(subscriptionIndicator, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new SingleDelayWithPublisher(this, subscriptionIndicator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> startWith(@NonNull ObservableSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return Observable.wrap(other).concatWith(toObservable());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull SingleSource<? extends T> source1, @NonNull SingleSource<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return Flowable.fromArray(source1, source2).concatMapSingleDelayError(Functions.identity(), false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, R> Single<R> zip(@NonNull SingleSource<? extends T1> source1, @NonNull SingleSource<? extends T2> source2, @NonNull SingleSource<? extends T3> source3, @NonNull Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), source1, source2, source3);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull SingleObserver<? super T> observer) {
        Objects.requireNonNull(observer, "observer is null");
        BlockingDisposableMultiObserver blockingDisposableMultiObserver = new BlockingDisposableMultiObserver();
        observer.onSubscribe(blockingDisposableMultiObserver);
        subscribe(blockingDisposableMultiObserver);
        blockingDisposableMultiObserver.blockingConsume(observer);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull SingleSource<? extends T> source1, @NonNull SingleSource<? extends T> source2, @NonNull SingleSource<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return Flowable.fromArray(source1, source2, source3).flatMapSingle(Functions.identity(), false, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Single<T> delaySubscription(long time, @NonNull TimeUnit unit) {
        return delaySubscription(time, unit, Schedulers.computation());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull Publisher<T> other) {
        Objects.requireNonNull(other, "other is null");
        return toFlowable().startWith(other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Single<T> delaySubscription(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return delaySubscription(Observable.timer(time, unit, scheduler));
    }

    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onSuccess, @NonNull Consumer<? super Throwable> onError, @NonNull DisposableContainer container) {
        Objects.requireNonNull(onSuccess, "onSuccess is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(container, "container is null");
        DisposableAutoReleaseMultiObserver disposableAutoReleaseMultiObserver = new DisposableAutoReleaseMultiObserver(container, onSuccess, onError, Functions.EMPTY_ACTION);
        container.add(disposableAutoReleaseMultiObserver);
        subscribe(disposableAutoReleaseMultiObserver);
        return disposableAutoReleaseMultiObserver;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull SingleSource<? extends T> source1, @NonNull SingleSource<? extends T> source2, @NonNull SingleSource<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return Flowable.fromArray(source1, source2, source3).concatMapSingleDelayError(Functions.identity(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull SingleSource<? extends T> source1, @NonNull SingleSource<? extends T> source2, @NonNull SingleSource<? extends T> source3, @NonNull SingleSource<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return Flowable.fromArray(source1, source2, source3, source4).flatMapSingle(Functions.identity(), true, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull SingleSource<? extends T> source1, @NonNull SingleSource<? extends T> source2, @NonNull SingleSource<? extends T> source3, @NonNull SingleSource<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return Flowable.fromArray(source1, source2, source3, source4).flatMapSingle(Functions.identity(), false, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, R> Single<R> zip(@NonNull SingleSource<? extends T1> source1, @NonNull SingleSource<? extends T2> source2, @NonNull SingleSource<? extends T3> source3, @NonNull SingleSource<? extends T4> source4, @NonNull Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), source1, source2, source3, source4);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull SingleSource<? extends T> source1, @NonNull SingleSource<? extends T> source2, @NonNull SingleSource<? extends T> source3, @NonNull SingleSource<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return Flowable.fromArray(source1, source2, source3, source4).concatMapSingleDelayError(Functions.identity(), false);
    }

    @Override // io.reactivex.rxjava3.core.SingleSource
    @SchedulerSupport("none")
    public final void subscribe(@NonNull SingleObserver<? super T> observer) {
        Objects.requireNonNull(observer, "observer is null");
        SingleObserver<? super T> singleObserverOnSubscribe = RxJavaPlugins.onSubscribe(this, observer);
        Objects.requireNonNull(singleObserverOnSubscribe, "The RxJavaPlugins.onSubscribe hook returned a null SingleObserver. Please check the handler provided to RxJavaPlugins.setOnSingleSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
        try {
            subscribeActual(singleObserverOnSubscribe);
        } catch (NullPointerException e2) {
            throw e2;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            NullPointerException nullPointerException = new NullPointerException("subscribeActual failed");
            nullPointerException.initCause(th);
            throw nullPointerException;
        }
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, R> Single<R> zip(@NonNull SingleSource<? extends T1> source1, @NonNull SingleSource<? extends T2> source2, @NonNull SingleSource<? extends T3> source3, @NonNull SingleSource<? extends T4> source4, @NonNull SingleSource<? extends T5> source5, @NonNull Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), source1, source2, source3, source4, source5);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, R> Single<R> zip(@NonNull SingleSource<? extends T1> source1, @NonNull SingleSource<? extends T2> source2, @NonNull SingleSource<? extends T3> source3, @NonNull SingleSource<? extends T4> source4, @NonNull SingleSource<? extends T5> source5, @NonNull SingleSource<? extends T6> source6, @NonNull Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), source1, source2, source3, source4, source5, source6);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, R> Single<R> zip(@NonNull SingleSource<? extends T1> source1, @NonNull SingleSource<? extends T2> source2, @NonNull SingleSource<? extends T3> source3, @NonNull SingleSource<? extends T4> source4, @NonNull SingleSource<? extends T5> source5, @NonNull SingleSource<? extends T6> source6, @NonNull SingleSource<? extends T7> source7, @NonNull Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(source7, "source7 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), source1, source2, source3, source4, source5, source6, source7);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Single<R> zip(@NonNull SingleSource<? extends T1> source1, @NonNull SingleSource<? extends T2> source2, @NonNull SingleSource<? extends T3> source3, @NonNull SingleSource<? extends T4> source4, @NonNull SingleSource<? extends T5> source5, @NonNull SingleSource<? extends T6> source6, @NonNull SingleSource<? extends T7> source7, @NonNull SingleSource<? extends T8> source8, @NonNull Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(source7, "source7 is null");
        Objects.requireNonNull(source8, "source8 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), source1, source2, source3, source4, source5, source6, source7, source8);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Single<R> zip(@NonNull SingleSource<? extends T1> source1, @NonNull SingleSource<? extends T2> source2, @NonNull SingleSource<? extends T3> source3, @NonNull SingleSource<? extends T4> source4, @NonNull SingleSource<? extends T5> source5, @NonNull SingleSource<? extends T6> source6, @NonNull SingleSource<? extends T7> source7, @NonNull SingleSource<? extends T8> source8, @NonNull SingleSource<? extends T9> source9, @NonNull Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(source7, "source7 is null");
        Objects.requireNonNull(source8, "source8 is null");
        Objects.requireNonNull(source9, "source9 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), source1, source2, source3, source4, source5, source6, source7, source8, source9);
    }
}
