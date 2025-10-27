package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.BackpressureKind;
import io.reactivex.rxjava3.annotations.BackpressureSupport;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
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
import io.reactivex.rxjava3.internal.fuseable.FuseToObservable;
import io.reactivex.rxjava3.internal.jdk8.CompletionStageConsumer;
import io.reactivex.rxjava3.internal.jdk8.MaybeFlattenStreamAsFlowable;
import io.reactivex.rxjava3.internal.jdk8.MaybeFlattenStreamAsObservable;
import io.reactivex.rxjava3.internal.jdk8.MaybeFromCompletionStage;
import io.reactivex.rxjava3.internal.jdk8.MaybeMapOptional;
import io.reactivex.rxjava3.internal.observers.BlockingDisposableMultiObserver;
import io.reactivex.rxjava3.internal.observers.BlockingMultiObserver;
import io.reactivex.rxjava3.internal.observers.DisposableAutoReleaseMultiObserver;
import io.reactivex.rxjava3.internal.observers.FutureMultiObserver;
import io.reactivex.rxjava3.internal.observers.SafeMaybeObserver;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableElementAtMaybePublisher;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapMaybePublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeAmb;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeCache;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeCallbackObserver;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeConcatArray;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeConcatArrayDelayError;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeConcatIterable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeContains;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeCount;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeCreate;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDefer;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDelay;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDelayOtherPublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDelaySubscriptionOtherPublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDematerialize;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDetach;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDoAfterSuccess;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDoFinally;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDoOnEvent;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDoOnLifecycle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeDoOnTerminate;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeEmpty;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeEqualSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeError;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeErrorCallable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFilter;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapBiSelector;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapCompletable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapIterableFlowable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapIterableObservable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapNotification;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatMapSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFlatten;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromAction;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromCallable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromCompletable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromFuture;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromRunnable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeFromSupplier;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeHide;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeIgnoreElementCompletable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeIsEmptySingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeJust;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeLift;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeMap;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeMaterialize;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeNever;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeObserveOn;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeOnErrorComplete;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeOnErrorNext;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeOnErrorReturn;
import io.reactivex.rxjava3.internal.operators.maybe.MaybePeek;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeSubscribeOn;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeSwitchIfEmpty;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeSwitchIfEmptySingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTakeUntilMaybe;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTakeUntilPublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTimeInterval;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTimeoutMaybe;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTimeoutPublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeTimer;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToFlowable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToObservable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToPublisher;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToSingle;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeUnsafeCreate;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeUnsubscribeOn;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeUsing;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeZipArray;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeZipIterable;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapMaybePublisher;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapMaybePublisher;
import io.reactivex.rxjava3.internal.operators.mixed.MaybeFlatMapObservable;
import io.reactivex.rxjava3.internal.operators.mixed.MaybeFlatMapPublisher;
import io.reactivex.rxjava3.internal.operators.observable.ObservableElementAtMaybe;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;

/* loaded from: classes8.dex */
public abstract class Maybe<T> implements MaybeSource<T> {
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> amb(@NonNull Iterable<? extends MaybeSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new MaybeAmb(null, sources));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Maybe<T> ambArray(@NonNull MaybeSource<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? empty() : sources.length == 1 ? wrap(sources[0]) : RxJavaPlugins.onAssembly(new MaybeAmb(sources, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Iterable<? extends MaybeSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new MaybeConcatIterable(sources));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArray(@NonNull MaybeSource<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? Flowable.empty() : sources.length == 1 ? RxJavaPlugins.onAssembly(new MaybeToFlowable(sources[0])) : RxJavaPlugins.onAssembly(new MaybeConcatArray(sources));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayDelayError(@NonNull MaybeSource<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? Flowable.empty() : sources.length == 1 ? RxJavaPlugins.onAssembly(new MaybeToFlowable(sources[0])) : RxJavaPlugins.onAssembly(new MaybeConcatArrayDelayError(sources));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEager(@NonNull MaybeSource<? extends T>... sources) {
        return Flowable.fromArray(sources).concatMapEager(MaybeToPublisher.instance());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEagerDelayError(@NonNull MaybeSource<? extends T>... sources) {
        return Flowable.fromArray(sources).concatMapEagerDelayError(MaybeToPublisher.instance(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Iterable<? extends MaybeSource<? extends T>> sources) {
        return Flowable.fromIterable(sources).concatMapMaybeDelayError(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Iterable<? extends MaybeSource<? extends T>> sources) {
        return Flowable.fromIterable(sources).concatMapEagerDelayError(MaybeToPublisher.instance(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Iterable<? extends MaybeSource<? extends T>> sources) {
        return Flowable.fromIterable(sources).concatMapEagerDelayError(MaybeToPublisher.instance(), true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> create(@NonNull MaybeOnSubscribe<T> onSubscribe) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        return RxJavaPlugins.onAssembly(new MaybeCreate(onSubscribe));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> defer(@NonNull Supplier<? extends MaybeSource<? extends T>> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new MaybeDefer(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> empty() {
        return RxJavaPlugins.onAssembly(MaybeEmpty.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> error(@NonNull Throwable throwable) {
        Objects.requireNonNull(throwable, "throwable is null");
        return RxJavaPlugins.onAssembly(new MaybeError(throwable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromAction(@NonNull Action action) {
        Objects.requireNonNull(action, "action is null");
        return RxJavaPlugins.onAssembly(new MaybeFromAction(action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromCallable(@NonNull Callable<? extends T> callable) {
        Objects.requireNonNull(callable, "callable is null");
        return RxJavaPlugins.onAssembly(new MaybeFromCallable(callable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromCompletable(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "completableSource is null");
        return RxJavaPlugins.onAssembly(new MaybeFromCompletable(completableSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromCompletionStage(@NonNull CompletionStage<T> stage) {
        Objects.requireNonNull(stage, "stage is null");
        return RxJavaPlugins.onAssembly(new MaybeFromCompletionStage(stage));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromFuture(@NonNull Future<? extends T> future) {
        Objects.requireNonNull(future, "future is null");
        return RxJavaPlugins.onAssembly(new MaybeFromFuture(future, 0L, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromObservable(@NonNull ObservableSource<T> source) {
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new ObservableElementAtMaybe(source, 0L));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromOptional(@NonNull Optional<T> optional) {
        Objects.requireNonNull(optional, "optional is null");
        return (Maybe) optional.map(new Function() { // from class: io.reactivex.rxjava3.core.d
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Maybe.just(obj);
            }
        }).orElseGet(new java.util.function.Supplier() { // from class: io.reactivex.rxjava3.core.e
            @Override // java.util.function.Supplier
            public final Object get() {
                return Maybe.empty();
            }
        });
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromPublisher(@NonNull Publisher<T> source) {
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new FlowableElementAtMaybePublisher(source, 0L));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromRunnable(@NonNull Runnable run) {
        Objects.requireNonNull(run, "run is null");
        return RxJavaPlugins.onAssembly(new MaybeFromRunnable(run));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromSingle(@NonNull SingleSource<T> single) {
        Objects.requireNonNull(single, "single is null");
        return RxJavaPlugins.onAssembly(new MaybeFromSingle(single));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromSupplier(@NonNull Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new MaybeFromSupplier(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> just(T item) {
        Objects.requireNonNull(item, "item is null");
        return RxJavaPlugins.onAssembly(new MaybeJust(item));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Iterable<? extends MaybeSource<? extends T>> sources) {
        return Flowable.fromIterable(sources).flatMapMaybe(Functions.identity(), false, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArray(MaybeSource<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? Flowable.empty() : sources.length == 1 ? RxJavaPlugins.onAssembly(new MaybeToFlowable(sources[0])) : RxJavaPlugins.onAssembly(new MaybeMergeArray(sources));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArrayDelayError(@NonNull MaybeSource<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return Flowable.fromArray(sources).flatMapMaybe(Functions.identity(), true, Math.max(1, sources.length));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Iterable<? extends MaybeSource<? extends T>> sources) {
        return Flowable.fromIterable(sources).flatMapMaybe(Functions.identity(), true, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> never() {
        return RxJavaPlugins.onAssembly(MaybeNever.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull MaybeSource<? extends T> source1, @NonNull MaybeSource<? extends T> source2) {
        return sequenceEqual(source1, source2, ObjectHelper.equalsPredicate());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNext(@NonNull Publisher<? extends MaybeSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapMaybePublisher(sources, Functions.identity(), false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNextDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapMaybePublisher(sources, Functions.identity(), true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public static Maybe<Long> timer(long delay, @NonNull TimeUnit unit) {
        return timer(delay, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> unsafeCreate(@NonNull MaybeSource<T> onSubscribe) {
        if (onSubscribe instanceof Maybe) {
            throw new IllegalArgumentException("unsafeCreate(Maybe) should be upgraded");
        }
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        return RxJavaPlugins.onAssembly(new MaybeUnsafeCreate(onSubscribe));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, D> Maybe<T> using(@NonNull Supplier<? extends D> resourceSupplier, @NonNull io.reactivex.rxjava3.functions.Function<? super D, ? extends MaybeSource<? extends T>> sourceSupplier, @NonNull Consumer<? super D> resourceCleanup) {
        return using(resourceSupplier, sourceSupplier, resourceCleanup, true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> wrap(@NonNull MaybeSource<T> source) {
        if (source instanceof Maybe) {
            return RxJavaPlugins.onAssembly((Maybe) source);
        }
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new MaybeUnsafeCreate(source));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Maybe<R> zip(@NonNull Iterable<? extends MaybeSource<? extends T>> sources, @NonNull io.reactivex.rxjava3.functions.Function<? super Object[], ? extends R> zipper) {
        Objects.requireNonNull(zipper, "zipper is null");
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new MaybeZipIterable(sources, zipper));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T, R> Maybe<R> zipArray(@NonNull io.reactivex.rxjava3.functions.Function<? super Object[], ? extends R> zipper, @NonNull MaybeSource<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        if (sources.length == 0) {
            return empty();
        }
        Objects.requireNonNull(zipper, "zipper is null");
        return RxJavaPlugins.onAssembly(new MaybeZipArray(sources, zipper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> ambWith(@NonNull MaybeSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return ambArray(this, other);
    }

    @CheckReturnValue
    @Nullable
    @SchedulerSupport("none")
    public final T blockingGet() {
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        return (T) blockingMultiObserver.blockingGet();
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe() {
        blockingSubscribe(Functions.emptyConsumer(), Functions.ERROR_CONSUMER, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> cache() {
        return RxJavaPlugins.onAssembly(new MaybeCache(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Maybe<U> cast(@NonNull Class<? extends U> cls) {
        Objects.requireNonNull(cls, "clazz is null");
        return (Maybe<U>) map(Functions.castFunction(cls));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> compose(@NonNull MaybeTransformer<? super T, ? extends R> transformer) {
        Objects.requireNonNull(transformer, "transformer is null");
        return wrap(transformer.apply(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> concatMap(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        return flatMap(mapper);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable concatMapCompletable(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends CompletableSource> mapper) {
        return flatMapCompletable(mapper);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> concatMapSingle(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        return flatMapSingle(mapper);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> concatWith(@NonNull MaybeSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return concat(this, other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> contains(@NonNull Object item) {
        Objects.requireNonNull(item, "item is null");
        return RxJavaPlugins.onAssembly(new MaybeContains(this, item));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Long> count() {
        return RxJavaPlugins.onAssembly(new MaybeCount(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> defaultIfEmpty(@NonNull T defaultItem) {
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        return RxJavaPlugins.onAssembly(new MaybeToSingle(this, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<T> delay(long time, @NonNull TimeUnit unit) {
        return delay(time, unit, Schedulers.computation(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Maybe<T> delaySubscription(@NonNull Publisher<U> subscriptionIndicator) {
        Objects.requireNonNull(subscriptionIndicator, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new MaybeDelaySubscriptionOtherPublisher(this, subscriptionIndicator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> dematerialize(@NonNull io.reactivex.rxjava3.functions.Function<? super T, Notification<R>> selector) {
        Objects.requireNonNull(selector, "selector is null");
        return RxJavaPlugins.onAssembly(new MaybeDematerialize(this, selector));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doAfterSuccess(@NonNull Consumer<? super T> onAfterSuccess) {
        Objects.requireNonNull(onAfterSuccess, "onAfterSuccess is null");
        return RxJavaPlugins.onAssembly(new MaybeDoAfterSuccess(this, onAfterSuccess));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doAfterTerminate(@NonNull Action onAfterTerminate) {
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer3 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        Objects.requireNonNull(onAfterTerminate, "onAfterTerminate is null");
        return RxJavaPlugins.onAssembly(new MaybePeek(this, consumerEmptyConsumer, consumerEmptyConsumer2, consumerEmptyConsumer3, action, onAfterTerminate, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doFinally(@NonNull Action onFinally) {
        Objects.requireNonNull(onFinally, "onFinally is null");
        return RxJavaPlugins.onAssembly(new MaybeDoFinally(this, onFinally));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnComplete(@NonNull Action onComplete) {
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer3 = Functions.emptyConsumer();
        Objects.requireNonNull(onComplete, "onComplete is null");
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new MaybePeek(this, consumerEmptyConsumer, consumerEmptyConsumer2, consumerEmptyConsumer3, onComplete, action, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnDispose(@NonNull Action onDispose) {
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer3 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        Objects.requireNonNull(onDispose, "onDispose is null");
        return RxJavaPlugins.onAssembly(new MaybePeek(this, consumerEmptyConsumer, consumerEmptyConsumer2, consumerEmptyConsumer3, action, action, onDispose));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnError(@NonNull Consumer<? super Throwable> onError) {
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Objects.requireNonNull(onError, "onError is null");
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new MaybePeek(this, consumerEmptyConsumer, consumerEmptyConsumer2, onError, action, action, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnEvent(@NonNull BiConsumer<? super T, ? super Throwable> onEvent) {
        Objects.requireNonNull(onEvent, "onEvent is null");
        return RxJavaPlugins.onAssembly(new MaybeDoOnEvent(this, onEvent));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnLifecycle(@NonNull Consumer<? super Disposable> onSubscribe, @NonNull Action onDispose) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        Objects.requireNonNull(onDispose, "onDispose is null");
        return RxJavaPlugins.onAssembly(new MaybeDoOnLifecycle(this, onSubscribe, onDispose));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnSubscribe(@NonNull Consumer<? super Disposable> onSubscribe) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new MaybePeek(this, onSubscribe, consumerEmptyConsumer, consumerEmptyConsumer2, action, action, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnSuccess(@NonNull Consumer<? super T> onSuccess) {
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Objects.requireNonNull(onSuccess, "onSuccess is null");
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new MaybePeek(this, consumerEmptyConsumer, onSuccess, consumerEmptyConsumer2, action, action, action));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> doOnTerminate(@NonNull Action onTerminate) {
        Objects.requireNonNull(onTerminate, "onTerminate is null");
        return RxJavaPlugins.onAssembly(new MaybeDoOnTerminate(this, onTerminate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> filter(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new MaybeFilter(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> flatMap(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatten(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable flatMapCompletable(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends CompletableSource> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapCompletable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMapObservable(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapObservable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMapPublisher(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends Publisher<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapPublisher(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> flatMapSingle(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapSingle(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Flowable<U> flattenAsFlowable(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends Iterable<? extends U>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapIterableFlowable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<U> flattenAsObservable(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends Iterable<? extends U>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapIterableObservable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flattenStreamAsFlowable(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends Stream<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlattenStreamAsFlowable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flattenStreamAsObservable(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends Stream<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlattenStreamAsObservable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> hide() {
        return RxJavaPlugins.onAssembly(new MaybeHide(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable ignoreElement() {
        return RxJavaPlugins.onAssembly(new MaybeIgnoreElementCompletable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> isEmpty() {
        return RxJavaPlugins.onAssembly(new MaybeIsEmptySingle(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> lift(@NonNull MaybeOperator<? extends R, ? super T> lift) {
        Objects.requireNonNull(lift, "lift is null");
        return RxJavaPlugins.onAssembly(new MaybeLift(this, lift));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> map(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeMap(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> mapOptional(@NonNull io.reactivex.rxjava3.functions.Function<? super T, Optional<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeMapOptional(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Notification<T>> materialize() {
        return RxJavaPlugins.onAssembly(new MaybeMaterialize(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> mergeWith(@NonNull MaybeSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return merge(this, other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> observeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeObserveOn(this, scheduler));
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
    public final Maybe<T> onErrorResumeNext(@NonNull io.reactivex.rxjava3.functions.Function<? super Throwable, ? extends MaybeSource<? extends T>> fallbackSupplier) {
        Objects.requireNonNull(fallbackSupplier, "fallbackSupplier is null");
        return RxJavaPlugins.onAssembly(new MaybeOnErrorNext(this, fallbackSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorResumeWith(@NonNull MaybeSource<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return onErrorResumeNext(Functions.justFunction(fallback));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorReturn(@NonNull io.reactivex.rxjava3.functions.Function<? super Throwable, ? extends T> itemSupplier) {
        Objects.requireNonNull(itemSupplier, "itemSupplier is null");
        return RxJavaPlugins.onAssembly(new MaybeOnErrorReturn(this, itemSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorReturnItem(@NonNull T item) {
        Objects.requireNonNull(item, "item is null");
        return onErrorReturn(Functions.justFunction(item));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onTerminateDetach() {
        return RxJavaPlugins.onAssembly(new MaybeDetach(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeat() {
        return repeat(Long.MAX_VALUE);
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
    public final Flowable<T> repeatWhen(@NonNull io.reactivex.rxjava3.functions.Function<? super Flowable<Object>, ? extends Publisher<?>> handler) {
        return toFlowable().repeatWhen(handler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retry() {
        return retry(Long.MAX_VALUE, Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retryUntil(@NonNull BooleanSupplier stop) {
        Objects.requireNonNull(stop, "stop is null");
        return retry(Long.MAX_VALUE, Functions.predicateReverseFor(stop));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retryWhen(@NonNull io.reactivex.rxjava3.functions.Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler) {
        return toFlowable().retryWhen(handler).singleElement();
    }

    @SchedulerSupport("none")
    public final void safeSubscribe(@NonNull MaybeObserver<? super T> observer) {
        Objects.requireNonNull(observer, "observer is null");
        subscribe(new SafeMaybeObserver(observer));
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
        return subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    public abstract void subscribeActual(@NonNull MaybeObserver<? super T> observer);

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> subscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeSubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <E extends MaybeObserver<? super T>> E subscribeWith(E observer) {
        subscribe(observer);
        return observer;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> switchIfEmpty(@NonNull MaybeSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new MaybeSwitchIfEmpty(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Maybe<T> takeUntil(@NonNull MaybeSource<U> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new MaybeTakeUntilMaybe(this, other));
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
    public final Maybe<Timed<T>> timeInterval() {
        return timeInterval(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<T> timeout(long timeout, @NonNull TimeUnit unit) {
        return timeout(timeout, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<Timed<T>> timestamp() {
        return timestamp(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @SchedulerSupport("none")
    public final <R> R to(@NonNull MaybeConverter<T, ? extends R> converter) {
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
        return this instanceof FuseToFlowable ? ((FuseToFlowable) this).fuseToFlowable() : RxJavaPlugins.onAssembly(new MaybeToFlowable(this));
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
    public final Observable<T> toObservable() {
        return this instanceof FuseToObservable ? ((FuseToObservable) this).fuseToObservable() : RxJavaPlugins.onAssembly(new MaybeToObservable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> toSingle() {
        return RxJavaPlugins.onAssembly(new MaybeToSingle(this, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> unsubscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeUnsubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Maybe<R> zipWith(@NonNull MaybeSource<? extends U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> zipper) {
        Objects.requireNonNull(other, "other is null");
        return zip(this, other, zipper);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> sources) {
        return Flowable.fromPublisher(sources).concatMapMaybeDelayError(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Iterable<? extends MaybeSource<? extends T>> sources, int maxConcurrency) {
        return Flowable.fromIterable(sources).concatMapEagerDelayError(MaybeToPublisher.instance(), false, maxConcurrency, 1);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Iterable<? extends MaybeSource<? extends T>> sources, int maxConcurrency) {
        return Flowable.fromIterable(sources).concatMapEagerDelayError(MaybeToPublisher.instance(), true, maxConcurrency, 1);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Publisher<? extends MaybeSource<? extends T>> sources) {
        return merge(sources, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> sources) {
        return mergeDelayError(sources, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull MaybeSource<? extends T> source1, @NonNull MaybeSource<? extends T> source2, @NonNull BiPredicate<? super T, ? super T> isEqual) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(isEqual, "isEqual is null");
        return RxJavaPlugins.onAssembly(new MaybeEqualSingle(source1, source2, isEqual));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public static Maybe<Long> timer(long delay, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeTimer(Math.max(0L, delay), unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, D> Maybe<T> using(@NonNull Supplier<? extends D> resourceSupplier, @NonNull io.reactivex.rxjava3.functions.Function<? super D, ? extends MaybeSource<? extends T>> sourceSupplier, @NonNull Consumer<? super D> resourceCleanup, boolean eager) {
        Objects.requireNonNull(resourceSupplier, "resourceSupplier is null");
        Objects.requireNonNull(sourceSupplier, "sourceSupplier is null");
        Objects.requireNonNull(resourceCleanup, "resourceCleanup is null");
        return RxJavaPlugins.onAssembly(new MaybeUsing(resourceSupplier, sourceSupplier, resourceCleanup, eager));
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onSuccess) {
        blockingSubscribe(onSuccess, Functions.ERROR_CONSUMER, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<T> delay(long time, @NonNull TimeUnit unit, boolean delayError) {
        return delay(time, unit, Schedulers.computation(), delayError);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> onErrorComplete(@NonNull Predicate<? super Throwable> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new MaybeOnErrorComplete(this, predicate));
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
    public final Maybe<T> retry(@NonNull BiPredicate<? super Integer, ? super Throwable> predicate) {
        return toFlowable().retry(predicate).singleElement();
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onSuccess) {
        return subscribe(onSuccess, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<Timed<T>> timeInterval(@NonNull Scheduler scheduler) {
        return timeInterval(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull MaybeSource<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout(timeout, unit, Schedulers.computation(), fallback);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<Timed<T>> timestamp(@NonNull Scheduler scheduler) {
        return timestamp(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final CompletionStage<T> toCompletionStage(@Nullable T defaultItem) {
        return (CompletionStage) subscribeWith(new CompletionStageConsumer(true, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull MaybeSource<? extends T> source1, @NonNull MaybeSource<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return concatArray(source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> sources, int prefetch) {
        return Flowable.fromPublisher(sources).concatMapMaybeDelayError(Functions.identity(), true, prefetch);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Publisher<? extends MaybeSource<? extends T>> sources) {
        return Flowable.fromPublisher(sources).concatMapEager(MaybeToPublisher.instance());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> sources) {
        return Flowable.fromPublisher(sources).concatMapEagerDelayError(MaybeToPublisher.instance(), true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> error(@NonNull Supplier<? extends Throwable> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new MaybeErrorCallable(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> fromFuture(@NonNull Future<? extends T> future, long timeout, @NonNull TimeUnit unit) {
        Objects.requireNonNull(future, "future is null");
        Objects.requireNonNull(unit, "unit is null");
        return RxJavaPlugins.onAssembly(new MaybeFromFuture(future, timeout, unit));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Publisher<? extends MaybeSource<? extends T>> sources, int maxConcurrency) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapMaybePublisher(sources, Functions.identity(), false, maxConcurrency));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> sources, int maxConcurrency) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapMaybePublisher(sources, Functions.identity(), true, maxConcurrency));
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onSuccess, @NonNull Consumer<? super Throwable> onError) {
        blockingSubscribe(onSuccess, onError, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> delay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return delay(time, unit, scheduler, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<T> delaySubscription(long time, @NonNull TimeUnit unit) {
        return delaySubscription(time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Maybe<R> flatMap(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends MaybeSource<? extends R>> onSuccessMapper, @NonNull io.reactivex.rxjava3.functions.Function<? super Throwable, ? extends MaybeSource<? extends R>> onErrorMapper, @NonNull Supplier<? extends MaybeSource<? extends R>> onCompleteSupplier) {
        Objects.requireNonNull(onSuccessMapper, "onSuccessMapper is null");
        Objects.requireNonNull(onErrorMapper, "onErrorMapper is null");
        Objects.requireNonNull(onCompleteSupplier, "onCompleteSupplier is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapNotification(this, onSuccessMapper, onErrorMapper, onCompleteSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retry(long times) {
        return retry(times, Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull SingleSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return Flowable.concat(Single.wrap(other).toFlowable(), toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onSuccess, @NonNull Consumer<? super Throwable> onError) {
        return subscribe(onSuccess, onError, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> switchIfEmpty(@NonNull SingleSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new MaybeSwitchIfEmptySingle(this, other));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Maybe<T> takeUntil(@NonNull Publisher<U> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new MaybeTakeUntilPublisher(this, other));
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
    public final Maybe<Timed<T>> timeInterval(@NonNull TimeUnit unit) {
        return timeInterval(unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Maybe<Timed<T>> timestamp(@NonNull TimeUnit unit) {
        return timestamp(unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Publisher<? extends MaybeSource<? extends T>> sources, int maxConcurrency) {
        return Flowable.fromPublisher(sources).concatMapEager(MaybeToPublisher.instance(), maxConcurrency, 1);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Publisher<? extends MaybeSource<? extends T>> sources, int maxConcurrency) {
        return Flowable.fromPublisher(sources).concatMapEagerDelayError(MaybeToPublisher.instance(), true, maxConcurrency, 1);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> source1, @NonNull MaybeSource<? extends T2> source2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final T blockingGet(@NonNull T t2) {
        Objects.requireNonNull(t2, "defaultValue is null");
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        return (T) blockingMultiObserver.blockingGet(t2);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onSuccess, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete) {
        Objects.requireNonNull(onSuccess, "onSuccess is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        subscribe(blockingMultiObserver);
        blockingMultiObserver.blockingConsume(onSuccess, onError, onComplete);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> delay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeDelay(this, Math.max(0L, time), unit, scheduler, delayError));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> delaySubscription(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return delaySubscription(Flowable.timer(time, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retry(long times, @NonNull Predicate<? super Throwable> predicate) {
        return toFlowable().retry(times, predicate).singleElement();
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onSuccess, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete) {
        Objects.requireNonNull(onSuccess, "onSuccess is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        return (Disposable) subscribeWith(new MaybeCallbackObserver(onSuccess, onError, onComplete));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<Timed<T>> timeInterval(@NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeInterval(this, unit, scheduler, true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, @NonNull MaybeSource<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout(timer(timeout, unit, scheduler), fallback);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<Timed<T>> timestamp(@NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeInterval(this, unit, scheduler, false));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> retry(@NonNull Predicate<? super Throwable> predicate) {
        return retry(Long.MAX_VALUE, predicate);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull MaybeSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return Flowable.concat(wrap(other).toFlowable(), toFlowable());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull MaybeSource<? extends T> source1, @NonNull MaybeSource<? extends T> source2, @NonNull MaybeSource<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return concatArray(source1, source2, source3);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Maybe<T> merge(@NonNull MaybeSource<? extends MaybeSource<? extends T>> source) {
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatten(source, Functions.identity()));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull MaybeSource<? extends T> source1, @NonNull MaybeSource<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return mergeArrayDelayError(source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Maybe<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return timeout(timer(timeout, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Maybe<T> delay(@NonNull Publisher<U> delayIndicator) {
        Objects.requireNonNull(delayIndicator, "delayIndicator is null");
        return RxJavaPlugins.onAssembly(new MaybeDelayOtherPublisher(this, delayIndicator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Maybe<R> flatMap(@NonNull io.reactivex.rxjava3.functions.Function<? super T, ? extends MaybeSource<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner) {
        Objects.requireNonNull(mapper, "mapper is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatMapBiSelector(this, mapper, combiner));
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
    @SchedulerSupport("none")
    public final <U> Maybe<T> timeout(@NonNull MaybeSource<U> timeoutIndicator) {
        Objects.requireNonNull(timeoutIndicator, "timeoutIndicator is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeoutMaybe(this, timeoutIndicator, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull MaybeSource<? extends T> source1, @NonNull MaybeSource<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return mergeArray(source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> source1, @NonNull MaybeSource<? extends T2> source2, @NonNull MaybeSource<? extends T3> source3, @NonNull Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), source1, source2, source3);
    }

    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onSuccess, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete, @NonNull DisposableContainer container) {
        Objects.requireNonNull(onSuccess, "onSuccess is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        Objects.requireNonNull(container, "container is null");
        DisposableAutoReleaseMultiObserver disposableAutoReleaseMultiObserver = new DisposableAutoReleaseMultiObserver(container, onSuccess, onError, onComplete);
        container.add(disposableAutoReleaseMultiObserver);
        subscribe(disposableAutoReleaseMultiObserver);
        return disposableAutoReleaseMultiObserver;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull MaybeSource<? extends T> source1, @NonNull MaybeSource<? extends T> source2, @NonNull MaybeSource<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return mergeArrayDelayError(source1, source2, source3);
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
    @SchedulerSupport("none")
    public final <U> Maybe<T> timeout(@NonNull MaybeSource<U> timeoutIndicator, @NonNull MaybeSource<? extends T> fallback) {
        Objects.requireNonNull(timeoutIndicator, "timeoutIndicator is null");
        Objects.requireNonNull(fallback, "fallback is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeoutMaybe(this, timeoutIndicator, fallback));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull MaybeSource<? extends T> source1, @NonNull MaybeSource<? extends T> source2, @NonNull MaybeSource<? extends T> source3, @NonNull MaybeSource<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return concatArray(source1, source2, source3, source4);
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull MaybeObserver<? super T> observer) {
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
    public static <T> Flowable<T> merge(@NonNull MaybeSource<? extends T> source1, @NonNull MaybeSource<? extends T> source2, @NonNull MaybeSource<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return mergeArray(source1, source2, source3);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Maybe<T> timeout(@NonNull Publisher<U> timeoutIndicator) {
        Objects.requireNonNull(timeoutIndicator, "timeoutIndicator is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeoutPublisher(this, timeoutIndicator, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull MaybeSource<? extends T> source1, @NonNull MaybeSource<? extends T> source2, @NonNull MaybeSource<? extends T> source3, @NonNull MaybeSource<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return mergeArrayDelayError(source1, source2, source3, source4);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> source1, @NonNull MaybeSource<? extends T2> source2, @NonNull MaybeSource<? extends T3> source3, @NonNull MaybeSource<? extends T4> source4, @NonNull Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), source1, source2, source3, source4);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Maybe<T> timeout(@NonNull Publisher<U> timeoutIndicator, @NonNull MaybeSource<? extends T> fallback) {
        Objects.requireNonNull(timeoutIndicator, "timeoutIndicator is null");
        Objects.requireNonNull(fallback, "fallback is null");
        return RxJavaPlugins.onAssembly(new MaybeTimeoutPublisher(this, timeoutIndicator, fallback));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends MaybeSource<? extends T>> sources) {
        return concat(sources, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull MaybeSource<? extends T> source1, @NonNull MaybeSource<? extends T> source2, @NonNull MaybeSource<? extends T> source3, @NonNull MaybeSource<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return mergeArray(source1, source2, source3, source4);
    }

    @Override // io.reactivex.rxjava3.core.MaybeSource
    @SchedulerSupport("none")
    public final void subscribe(@NonNull MaybeObserver<? super T> observer) {
        Objects.requireNonNull(observer, "observer is null");
        MaybeObserver<? super T> maybeObserverOnSubscribe = RxJavaPlugins.onSubscribe(this, observer);
        Objects.requireNonNull(maybeObserverOnSubscribe, "The RxJavaPlugins.onSubscribe hook returned a null MaybeObserver. Please check the handler provided to RxJavaPlugins.setOnMaybeSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
        try {
            subscribeActual(maybeObserverOnSubscribe);
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
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends MaybeSource<? extends T>> sources, int prefetch) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapMaybePublisher(sources, Functions.identity(), ErrorMode.IMMEDIATE, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> source1, @NonNull MaybeSource<? extends T2> source2, @NonNull MaybeSource<? extends T3> source3, @NonNull MaybeSource<? extends T4> source4, @NonNull MaybeSource<? extends T5> source5, @NonNull Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper) {
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
    public static <T1, T2, T3, T4, T5, T6, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> source1, @NonNull MaybeSource<? extends T2> source2, @NonNull MaybeSource<? extends T3> source3, @NonNull MaybeSource<? extends T4> source4, @NonNull MaybeSource<? extends T5> source5, @NonNull MaybeSource<? extends T6> source6, @NonNull Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper) {
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
    public static <T1, T2, T3, T4, T5, T6, T7, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> source1, @NonNull MaybeSource<? extends T2> source2, @NonNull MaybeSource<? extends T3> source3, @NonNull MaybeSource<? extends T4> source4, @NonNull MaybeSource<? extends T5> source5, @NonNull MaybeSource<? extends T6> source6, @NonNull MaybeSource<? extends T7> source7, @NonNull Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper) {
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
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> source1, @NonNull MaybeSource<? extends T2> source2, @NonNull MaybeSource<? extends T3> source3, @NonNull MaybeSource<? extends T4> source4, @NonNull MaybeSource<? extends T5> source5, @NonNull MaybeSource<? extends T6> source6, @NonNull MaybeSource<? extends T7> source7, @NonNull MaybeSource<? extends T8> source8, @NonNull Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper) {
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
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Maybe<R> zip(@NonNull MaybeSource<? extends T1> source1, @NonNull MaybeSource<? extends T2> source2, @NonNull MaybeSource<? extends T3> source3, @NonNull MaybeSource<? extends T4> source4, @NonNull MaybeSource<? extends T5> source5, @NonNull MaybeSource<? extends T6> source6, @NonNull MaybeSource<? extends T7> source7, @NonNull MaybeSource<? extends T8> source8, @NonNull MaybeSource<? extends T9> source9, @NonNull Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper) {
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
