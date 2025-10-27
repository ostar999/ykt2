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
import io.reactivex.rxjava3.internal.jdk8.ObservableCollectWithCollectorSingle;
import io.reactivex.rxjava3.internal.jdk8.ObservableFirstStageObserver;
import io.reactivex.rxjava3.internal.jdk8.ObservableFlatMapStream;
import io.reactivex.rxjava3.internal.jdk8.ObservableFromCompletionStage;
import io.reactivex.rxjava3.internal.jdk8.ObservableFromStream;
import io.reactivex.rxjava3.internal.jdk8.ObservableLastStageObserver;
import io.reactivex.rxjava3.internal.jdk8.ObservableMapOptional;
import io.reactivex.rxjava3.internal.jdk8.ObservableSingleStageObserver;
import io.reactivex.rxjava3.internal.observers.BlockingFirstObserver;
import io.reactivex.rxjava3.internal.observers.BlockingLastObserver;
import io.reactivex.rxjava3.internal.observers.DisposableAutoReleaseObserver;
import io.reactivex.rxjava3.internal.observers.ForEachWhileObserver;
import io.reactivex.rxjava3.internal.observers.FutureObserver;
import io.reactivex.rxjava3.internal.observers.LambdaObserver;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromObservable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableOnBackpressureError;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToObservable;
import io.reactivex.rxjava3.internal.operators.mixed.ObservableConcatMapCompletable;
import io.reactivex.rxjava3.internal.operators.mixed.ObservableConcatMapMaybe;
import io.reactivex.rxjava3.internal.operators.mixed.ObservableConcatMapSingle;
import io.reactivex.rxjava3.internal.operators.mixed.ObservableSwitchMapCompletable;
import io.reactivex.rxjava3.internal.operators.mixed.ObservableSwitchMapMaybe;
import io.reactivex.rxjava3.internal.operators.mixed.ObservableSwitchMapSingle;
import io.reactivex.rxjava3.internal.operators.observable.BlockingObservableIterable;
import io.reactivex.rxjava3.internal.operators.observable.BlockingObservableLatest;
import io.reactivex.rxjava3.internal.operators.observable.BlockingObservableMostRecent;
import io.reactivex.rxjava3.internal.operators.observable.BlockingObservableNext;
import io.reactivex.rxjava3.internal.operators.observable.ObservableAllSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableAmb;
import io.reactivex.rxjava3.internal.operators.observable.ObservableAnySingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableBlockingSubscribe;
import io.reactivex.rxjava3.internal.operators.observable.ObservableBuffer;
import io.reactivex.rxjava3.internal.operators.observable.ObservableBufferBoundary;
import io.reactivex.rxjava3.internal.operators.observable.ObservableBufferExactBoundary;
import io.reactivex.rxjava3.internal.operators.observable.ObservableBufferTimed;
import io.reactivex.rxjava3.internal.operators.observable.ObservableCache;
import io.reactivex.rxjava3.internal.operators.observable.ObservableCollectSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableCombineLatest;
import io.reactivex.rxjava3.internal.operators.observable.ObservableConcatMap;
import io.reactivex.rxjava3.internal.operators.observable.ObservableConcatMapEager;
import io.reactivex.rxjava3.internal.operators.observable.ObservableConcatMapScheduler;
import io.reactivex.rxjava3.internal.operators.observable.ObservableConcatWithCompletable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableConcatWithMaybe;
import io.reactivex.rxjava3.internal.operators.observable.ObservableConcatWithSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableCountSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableCreate;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDebounce;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDebounceTimed;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDefer;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDelay;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDelaySubscriptionOther;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDematerialize;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDetach;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDistinct;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDistinctUntilChanged;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDoAfterNext;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDoFinally;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDoOnEach;
import io.reactivex.rxjava3.internal.operators.observable.ObservableDoOnLifecycle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableElementAtMaybe;
import io.reactivex.rxjava3.internal.operators.observable.ObservableElementAtSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableEmpty;
import io.reactivex.rxjava3.internal.operators.observable.ObservableError;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFilter;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFlatMap;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFlatMapCompletableCompletable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFlatMapMaybe;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFlatMapSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFlattenIterable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromAction;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromArray;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromCallable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromCompletable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromFuture;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromIterable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromPublisher;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromRunnable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromSupplier;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromUnsafeSource;
import io.reactivex.rxjava3.internal.operators.observable.ObservableGenerate;
import io.reactivex.rxjava3.internal.operators.observable.ObservableGroupBy;
import io.reactivex.rxjava3.internal.operators.observable.ObservableGroupJoin;
import io.reactivex.rxjava3.internal.operators.observable.ObservableHide;
import io.reactivex.rxjava3.internal.operators.observable.ObservableIgnoreElements;
import io.reactivex.rxjava3.internal.operators.observable.ObservableIgnoreElementsCompletable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableInternalHelper;
import io.reactivex.rxjava3.internal.operators.observable.ObservableInterval;
import io.reactivex.rxjava3.internal.operators.observable.ObservableIntervalRange;
import io.reactivex.rxjava3.internal.operators.observable.ObservableJoin;
import io.reactivex.rxjava3.internal.operators.observable.ObservableJust;
import io.reactivex.rxjava3.internal.operators.observable.ObservableLastMaybe;
import io.reactivex.rxjava3.internal.operators.observable.ObservableLastSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableLift;
import io.reactivex.rxjava3.internal.operators.observable.ObservableMap;
import io.reactivex.rxjava3.internal.operators.observable.ObservableMapNotification;
import io.reactivex.rxjava3.internal.operators.observable.ObservableMaterialize;
import io.reactivex.rxjava3.internal.operators.observable.ObservableMergeWithCompletable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableMergeWithMaybe;
import io.reactivex.rxjava3.internal.operators.observable.ObservableMergeWithSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableNever;
import io.reactivex.rxjava3.internal.operators.observable.ObservableObserveOn;
import io.reactivex.rxjava3.internal.operators.observable.ObservableOnErrorComplete;
import io.reactivex.rxjava3.internal.operators.observable.ObservableOnErrorNext;
import io.reactivex.rxjava3.internal.operators.observable.ObservableOnErrorReturn;
import io.reactivex.rxjava3.internal.operators.observable.ObservablePublish;
import io.reactivex.rxjava3.internal.operators.observable.ObservablePublishSelector;
import io.reactivex.rxjava3.internal.operators.observable.ObservableRange;
import io.reactivex.rxjava3.internal.operators.observable.ObservableRangeLong;
import io.reactivex.rxjava3.internal.operators.observable.ObservableReduceMaybe;
import io.reactivex.rxjava3.internal.operators.observable.ObservableReduceSeedSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableReduceWithSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableRepeat;
import io.reactivex.rxjava3.internal.operators.observable.ObservableRepeatUntil;
import io.reactivex.rxjava3.internal.operators.observable.ObservableRepeatWhen;
import io.reactivex.rxjava3.internal.operators.observable.ObservableReplay;
import io.reactivex.rxjava3.internal.operators.observable.ObservableRetryBiPredicate;
import io.reactivex.rxjava3.internal.operators.observable.ObservableRetryPredicate;
import io.reactivex.rxjava3.internal.operators.observable.ObservableRetryWhen;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSampleTimed;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSampleWithObservable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableScalarXMap;
import io.reactivex.rxjava3.internal.operators.observable.ObservableScan;
import io.reactivex.rxjava3.internal.operators.observable.ObservableScanSeed;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSequenceEqualSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSerialized;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSingleMaybe;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSingleSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSkip;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSkipLast;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSkipLastTimed;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSkipUntil;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSkipWhile;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSubscribeOn;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSwitchIfEmpty;
import io.reactivex.rxjava3.internal.operators.observable.ObservableSwitchMap;
import io.reactivex.rxjava3.internal.operators.observable.ObservableTake;
import io.reactivex.rxjava3.internal.operators.observable.ObservableTakeLast;
import io.reactivex.rxjava3.internal.operators.observable.ObservableTakeLastOne;
import io.reactivex.rxjava3.internal.operators.observable.ObservableTakeLastTimed;
import io.reactivex.rxjava3.internal.operators.observable.ObservableTakeUntil;
import io.reactivex.rxjava3.internal.operators.observable.ObservableTakeUntilPredicate;
import io.reactivex.rxjava3.internal.operators.observable.ObservableTakeWhile;
import io.reactivex.rxjava3.internal.operators.observable.ObservableThrottleFirstTimed;
import io.reactivex.rxjava3.internal.operators.observable.ObservableThrottleLatest;
import io.reactivex.rxjava3.internal.operators.observable.ObservableTimeInterval;
import io.reactivex.rxjava3.internal.operators.observable.ObservableTimeout;
import io.reactivex.rxjava3.internal.operators.observable.ObservableTimeoutTimed;
import io.reactivex.rxjava3.internal.operators.observable.ObservableTimer;
import io.reactivex.rxjava3.internal.operators.observable.ObservableToListSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableUnsubscribeOn;
import io.reactivex.rxjava3.internal.operators.observable.ObservableUsing;
import io.reactivex.rxjava3.internal.operators.observable.ObservableWindow;
import io.reactivex.rxjava3.internal.operators.observable.ObservableWindowBoundary;
import io.reactivex.rxjava3.internal.operators.observable.ObservableWindowBoundarySelector;
import io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed;
import io.reactivex.rxjava3.internal.operators.observable.ObservableWithLatestFrom;
import io.reactivex.rxjava3.internal.operators.observable.ObservableWithLatestFromMany;
import io.reactivex.rxjava3.internal.operators.observable.ObservableZip;
import io.reactivex.rxjava3.internal.operators.observable.ObservableZipIterable;
import io.reactivex.rxjava3.internal.operators.single.SingleToObservable;
import io.reactivex.rxjava3.internal.util.ArrayListSupplier;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.HashMapSupplier;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.observables.GroupedObservable;
import io.reactivex.rxjava3.observers.SafeObserver;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.operators.ScalarSupplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterators;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.reactivestreams.Publisher;

/* loaded from: classes8.dex */
public abstract class Observable<T> implements ObservableSource<T> {

    /* renamed from: io.reactivex.rxjava3.core.Observable$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy;

        static {
            int[] iArr = new int[BackpressureStrategy.values().length];
            $SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy = iArr;
            try {
                iArr[BackpressureStrategy.DROP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy[BackpressureStrategy.LATEST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy[BackpressureStrategy.MISSING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy[BackpressureStrategy.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> amb(@NonNull Iterable<? extends ObservableSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new ObservableAmb(null, sources));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> ambArray(@NonNull ObservableSource<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        int length = sources.length;
        return length == 0 ? empty() : length == 1 ? wrap(sources[0]) : RxJavaPlugins.onAssembly(new ObservableAmb(sources, null));
    }

    @CheckReturnValue
    public static int bufferSize() {
        return Flowable.bufferSize();
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Observable<R> combineLatest(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> combiner) {
        return combineLatest(sources, combiner, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Observable<R> combineLatestArray(@NonNull ObservableSource<? extends T>[] sources, @NonNull Function<? super Object[], ? extends R> combiner) {
        return combineLatestArray(sources, combiner, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Observable<R> combineLatestArrayDelayError(@NonNull ObservableSource<? extends T>[] sources, @NonNull Function<? super Object[], ? extends R> combiner) {
        return combineLatestArrayDelayError(sources, combiner, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Observable<R> combineLatestDelayError(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> combiner) {
        return combineLatestDelayError(sources, combiner, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concat(@NonNull Iterable<? extends ObservableSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return fromIterable(sources).concatMapDelayError(Functions.identity(), false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> concatArray(@NonNull ObservableSource<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? empty() : sources.length == 1 ? wrap(sources[0]) : RxJavaPlugins.onAssembly(new ObservableConcatMap(fromArray(sources), Functions.identity(), bufferSize(), ErrorMode.BOUNDARY));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> concatArrayDelayError(@NonNull ObservableSource<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? empty() : sources.length == 1 ? wrap(sources[0]) : concatDelayError(fromArray(sources));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> concatArrayEager(@NonNull ObservableSource<? extends T>... sources) {
        return concatArrayEager(bufferSize(), bufferSize(), sources);
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> concatArrayEagerDelayError(@NonNull ObservableSource<? extends T>... sources) {
        return concatArrayEagerDelayError(bufferSize(), bufferSize(), sources);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concatDelayError(@NonNull Iterable<? extends ObservableSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return concatDelayError(fromIterable(sources));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concatEager(@NonNull Iterable<? extends ObservableSource<? extends T>> sources) {
        return concatEager(sources, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concatEagerDelayError(@NonNull Iterable<? extends ObservableSource<? extends T>> sources) {
        return concatEagerDelayError(sources, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> create(@NonNull ObservableOnSubscribe<T> source) {
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new ObservableCreate(source));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> defer(@NonNull Supplier<? extends ObservableSource<? extends T>> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new ObservableDefer(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    private Observable<T> doOnEach(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete, @NonNull Action onAfterTerminate) {
        Objects.requireNonNull(onNext, "onNext is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        Objects.requireNonNull(onAfterTerminate, "onAfterTerminate is null");
        return RxJavaPlugins.onAssembly(new ObservableDoOnEach(this, onNext, onError, onComplete, onAfterTerminate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> empty() {
        return RxJavaPlugins.onAssembly(ObservableEmpty.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> error(@NonNull Supplier<? extends Throwable> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new ObservableError(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromAction(@NonNull Action action) {
        Objects.requireNonNull(action, "action is null");
        return RxJavaPlugins.onAssembly(new ObservableFromAction(action));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> fromArray(@NonNull T... items) {
        Objects.requireNonNull(items, "items is null");
        return items.length == 0 ? empty() : items.length == 1 ? just(items[0]) : RxJavaPlugins.onAssembly(new ObservableFromArray(items));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromCallable(@NonNull Callable<? extends T> callable) {
        Objects.requireNonNull(callable, "callable is null");
        return RxJavaPlugins.onAssembly(new ObservableFromCallable(callable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromCompletable(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "completableSource is null");
        return RxJavaPlugins.onAssembly(new ObservableFromCompletable(completableSource));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromCompletionStage(@NonNull CompletionStage<T> stage) {
        Objects.requireNonNull(stage, "stage is null");
        return RxJavaPlugins.onAssembly(new ObservableFromCompletionStage(stage));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromFuture(@NonNull Future<? extends T> future) {
        Objects.requireNonNull(future, "future is null");
        return RxJavaPlugins.onAssembly(new ObservableFromFuture(future, 0L, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromIterable(@NonNull Iterable<? extends T> source) {
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new ObservableFromIterable(source));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromMaybe(@NonNull MaybeSource<T> maybe) {
        Objects.requireNonNull(maybe, "maybe is null");
        return RxJavaPlugins.onAssembly(new MaybeToObservable(maybe));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromOptional(@NonNull Optional<T> optional) {
        Objects.requireNonNull(optional, "optional is null");
        return (Observable) optional.map(new java.util.function.Function() { // from class: io.reactivex.rxjava3.core.f
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Observable.just(obj);
            }
        }).orElseGet(new java.util.function.Supplier() { // from class: io.reactivex.rxjava3.core.g
            @Override // java.util.function.Supplier
            public final Object get() {
                return Observable.empty();
            }
        });
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public static <T> Observable<T> fromPublisher(@NonNull Publisher<? extends T> publisher) {
        Objects.requireNonNull(publisher, "publisher is null");
        return RxJavaPlugins.onAssembly(new ObservableFromPublisher(publisher));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromRunnable(@NonNull Runnable run) {
        Objects.requireNonNull(run, "run is null");
        return RxJavaPlugins.onAssembly(new ObservableFromRunnable(run));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromSingle(@NonNull SingleSource<T> source) {
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new SingleToObservable(source));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromStream(@NonNull Stream<T> stream) {
        Objects.requireNonNull(stream, "stream is null");
        return RxJavaPlugins.onAssembly(new ObservableFromStream(stream));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromSupplier(@NonNull Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new ObservableFromSupplier(supplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> generate(@NonNull Consumer<Emitter<T>> generator) {
        Objects.requireNonNull(generator, "generator is null");
        return generate(Functions.nullSupplier(), ObservableInternalHelper.simpleGenerator(generator), Functions.emptyConsumer());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public static Observable<Long> interval(long initialDelay, long period, @NonNull TimeUnit unit) {
        return interval(initialDelay, period, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public static Observable<Long> intervalRange(long start, long count, long initialDelay, long period, @NonNull TimeUnit unit) {
        return intervalRange(start, count, initialDelay, period, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> just(@NonNull T item) {
        Objects.requireNonNull(item, "item is null");
        return RxJavaPlugins.onAssembly(new ObservableJust(item));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> merge(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int bufferSize) {
        return fromIterable(sources).flatMap(Functions.identity(), false, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeArray(int maxConcurrency, int bufferSize, @NonNull ObservableSource<? extends T>... sources) {
        return fromArray(sources).flatMap(Functions.identity(), false, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeArrayDelayError(int maxConcurrency, int bufferSize, @NonNull ObservableSource<? extends T>... sources) {
        return fromArray(sources).flatMap(Functions.identity(), true, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeDelayError(@NonNull Iterable<? extends ObservableSource<? extends T>> sources) {
        return fromIterable(sources).flatMap(Functions.identity(), true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> never() {
        return RxJavaPlugins.onAssembly(ObservableNever.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Observable<Integer> range(int start, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count >= 0 required but it was " + count);
        }
        if (count == 0) {
            return empty();
        }
        if (count == 1) {
            return just(Integer.valueOf(start));
        }
        if (start + (count - 1) <= 2147483647L) {
            return RxJavaPlugins.onAssembly(new ObservableRange(start, count));
        }
        throw new IllegalArgumentException("Integer overflow");
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static Observable<Long> rangeLong(long start, long count) {
        if (count < 0) {
            throw new IllegalArgumentException("count >= 0 required but it was " + count);
        }
        if (count == 0) {
            return empty();
        }
        if (count == 1) {
            return just(Long.valueOf(start));
        }
        long j2 = (count - 1) + start;
        if (start <= 0 || j2 >= 0) {
            return RxJavaPlugins.onAssembly(new ObservableRangeLong(start, count));
        }
        throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2) {
        return sequenceEqual(source1, source2, ObjectHelper.equalsPredicate(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> switchOnNext(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources, int bufferSize) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableSwitchMap(sources, Functions.identity(), bufferSize, false));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> switchOnNextDelayError(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources) {
        return switchOnNextDelayError(sources, bufferSize());
    }

    @NonNull
    private Observable<T> timeout0(long timeout, @NonNull TimeUnit unit, @Nullable ObservableSource<? extends T> fallback, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableTimeoutTimed(this, timeout, unit, scheduler, fallback));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public static Observable<Long> timer(long delay, @NonNull TimeUnit unit) {
        return timer(delay, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> unsafeCreate(@NonNull ObservableSource<T> onSubscribe) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        if (onSubscribe instanceof Observable) {
            throw new IllegalArgumentException("unsafeCreate(Observable) should be upgraded");
        }
        return RxJavaPlugins.onAssembly(new ObservableFromUnsafeSource(onSubscribe));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, D> Observable<T> using(@NonNull Supplier<? extends D> resourceSupplier, @NonNull Function<? super D, ? extends ObservableSource<? extends T>> sourceSupplier, @NonNull Consumer<? super D> resourceCleanup) {
        return using(resourceSupplier, sourceSupplier, resourceCleanup, true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> wrap(@NonNull ObservableSource<T> source) {
        Objects.requireNonNull(source, "source is null");
        return source instanceof Observable ? RxJavaPlugins.onAssembly((Observable) source) : RxJavaPlugins.onAssembly(new ObservableFromUnsafeSource(source));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Observable<R> zip(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> zipper) {
        Objects.requireNonNull(zipper, "zipper is null");
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new ObservableZip(null, sources, zipper, bufferSize(), false));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T, R> Observable<R> zipArray(@NonNull Function<? super Object[], ? extends R> zipper, boolean delayError, int bufferSize, @NonNull ObservableSource<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        if (sources.length == 0) {
            return empty();
        }
        Objects.requireNonNull(zipper, "zipper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableZip(sources, null, zipper, bufferSize, delayError));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> all(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new ObservableAllSingle(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> ambWith(@NonNull ObservableSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return ambArray(this, other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> any(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new ObservableAnySingle(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final T blockingFirst() throws InterruptedException {
        BlockingFirstObserver blockingFirstObserver = new BlockingFirstObserver();
        subscribe(blockingFirstObserver);
        T tBlockingGet = blockingFirstObserver.blockingGet();
        if (tBlockingGet != null) {
            return tBlockingGet;
        }
        throw new NoSuchElementException();
    }

    @SchedulerSupport("none")
    public final void blockingForEach(@NonNull Consumer<? super T> onNext) {
        blockingForEach(onNext, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Iterable<T> blockingIterable() {
        return blockingIterable(bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final T blockingLast() throws InterruptedException {
        BlockingLastObserver blockingLastObserver = new BlockingLastObserver();
        subscribe(blockingLastObserver);
        T tBlockingGet = blockingLastObserver.blockingGet();
        if (tBlockingGet != null) {
            return tBlockingGet;
        }
        throw new NoSuchElementException();
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Iterable<T> blockingLatest() {
        return new BlockingObservableLatest(this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Iterable<T> blockingMostRecent(@NonNull T initialItem) {
        Objects.requireNonNull(initialItem, "initialItem is null");
        return new BlockingObservableMostRecent(this, initialItem);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Iterable<T> blockingNext() {
        return new BlockingObservableNext(this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final T blockingSingle() {
        T tBlockingGet = singleElement().blockingGet();
        if (tBlockingGet != null) {
            return tBlockingGet;
        }
        throw new NoSuchElementException();
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Stream<T> blockingStream() {
        return blockingStream(bufferSize());
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe() {
        ObservableBlockingSubscribe.subscribe(this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<List<T>> buffer(int count) {
        return buffer(count, count);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> cache() {
        return cacheWithInitialCapacity(16);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> cacheWithInitialCapacity(int initialCapacity) {
        ObjectHelper.verifyPositive(initialCapacity, "initialCapacity");
        return RxJavaPlugins.onAssembly(new ObservableCache(this, initialCapacity));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<U> cast(@NonNull Class<U> cls) {
        Objects.requireNonNull(cls, "clazz is null");
        return (Observable<U>) map(Functions.castFunction(cls));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Single<U> collect(@NonNull Supplier<? extends U> initialItemSupplier, @NonNull BiConsumer<? super U, ? super T> collector) {
        Objects.requireNonNull(initialItemSupplier, "initialItemSupplier is null");
        Objects.requireNonNull(collector, "collector is null");
        return RxJavaPlugins.onAssembly(new ObservableCollectSingle(this, initialItemSupplier, collector));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Single<U> collectInto(@NonNull U initialItem, @NonNull BiConsumer<? super U, ? super T> collector) {
        Objects.requireNonNull(initialItem, "initialItem is null");
        return collect(Functions.justSupplier(initialItem), collector);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> compose(@NonNull ObservableTransformer<? super T, ? extends R> composer) {
        Objects.requireNonNull(composer, "composer is null");
        return wrap(composer.apply(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
        return concatMap(mapper, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable concatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        return concatMapCompletable(mapper, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable concatMapCompletableDelayError(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        return concatMapCompletableDelayError(mapper, true, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapDelayError(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
        return concatMapDelayError(mapper, true, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapEager(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
        return concatMapEager(mapper, Integer.MAX_VALUE, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapEagerDelayError(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean tillTheEnd) {
        return concatMapEagerDelayError(mapper, tillTheEnd, Integer.MAX_VALUE, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<U> concatMapIterable(@NonNull Function<? super T, ? extends Iterable<? extends U>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableFlattenIterable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        return concatMapMaybe(mapper, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapMaybeDelayError(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        return concatMapMaybeDelayError(mapper, true, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        return concatMapSingle(mapper, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapSingleDelayError(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        return concatMapSingleDelayError(mapper, true, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapStream(@NonNull Function<? super T, ? extends Stream<? extends R>> mapper) {
        return flatMapStream(mapper);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> concatWith(@NonNull ObservableSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return concat(this, other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> contains(@NonNull Object item) {
        Objects.requireNonNull(item, "item is null");
        return any(Functions.equalsWith(item));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Long> count() {
        return RxJavaPlugins.onAssembly(new ObservableCountSingle(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<T> debounce(@NonNull Function<? super T, ? extends ObservableSource<U>> debounceIndicator) {
        Objects.requireNonNull(debounceIndicator, "debounceIndicator is null");
        return RxJavaPlugins.onAssembly(new ObservableDebounce(this, debounceIndicator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> defaultIfEmpty(@NonNull T defaultItem) {
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        return switchIfEmpty(just(defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<T> delay(@NonNull Function<? super T, ? extends ObservableSource<U>> function) {
        Objects.requireNonNull(function, "itemDelayIndicator is null");
        return (Observable<T>) flatMap(ObservableInternalHelper.itemDelay(function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<T> delaySubscription(@NonNull ObservableSource<U> subscriptionIndicator) {
        Objects.requireNonNull(subscriptionIndicator, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new ObservableDelaySubscriptionOther(this, subscriptionIndicator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> dematerialize(@NonNull Function<? super T, Notification<R>> selector) {
        Objects.requireNonNull(selector, "selector is null");
        return RxJavaPlugins.onAssembly(new ObservableDematerialize(this, selector));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> distinct() {
        return distinct(Functions.identity(), Functions.createHashSet());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> distinctUntilChanged() {
        return distinctUntilChanged(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doAfterNext(@NonNull Consumer<? super T> onAfterNext) {
        Objects.requireNonNull(onAfterNext, "onAfterNext is null");
        return RxJavaPlugins.onAssembly(new ObservableDoAfterNext(this, onAfterNext));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doAfterTerminate(@NonNull Action onAfterTerminate) {
        Objects.requireNonNull(onAfterTerminate, "onAfterTerminate is null");
        return doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, onAfterTerminate);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doFinally(@NonNull Action onFinally) {
        Objects.requireNonNull(onFinally, "onFinally is null");
        return RxJavaPlugins.onAssembly(new ObservableDoFinally(this, onFinally));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doOnComplete(@NonNull Action onComplete) {
        return doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), onComplete, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doOnDispose(@NonNull Action onDispose) {
        return doOnLifecycle(Functions.emptyConsumer(), onDispose);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doOnError(@NonNull Consumer<? super Throwable> onError) {
        Consumer<? super T> consumerEmptyConsumer = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnEach(consumerEmptyConsumer, onError, action, action);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doOnLifecycle(@NonNull Consumer<? super Disposable> onSubscribe, @NonNull Action onDispose) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        Objects.requireNonNull(onDispose, "onDispose is null");
        return RxJavaPlugins.onAssembly(new ObservableDoOnLifecycle(this, onSubscribe, onDispose));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doOnNext(@NonNull Consumer<? super T> onNext) {
        Consumer<? super Throwable> consumerEmptyConsumer = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnEach(onNext, consumerEmptyConsumer, action, action);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doOnSubscribe(@NonNull Consumer<? super Disposable> onSubscribe) {
        return doOnLifecycle(onSubscribe, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doOnTerminate(@NonNull Action onTerminate) {
        Objects.requireNonNull(onTerminate, "onTerminate is null");
        return doOnEach(Functions.emptyConsumer(), Functions.actionConsumer(onTerminate), onTerminate, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> elementAt(long index) {
        if (index >= 0) {
            return RxJavaPlugins.onAssembly(new ObservableElementAtMaybe(this, index));
        }
        throw new IndexOutOfBoundsException("index >= 0 required but it was " + index);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> elementAtOrError(long index) {
        if (index >= 0) {
            return RxJavaPlugins.onAssembly(new ObservableElementAtSingle(this, index, null));
        }
        throw new IndexOutOfBoundsException("index >= 0 required but it was " + index);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> filter(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new ObservableFilter(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> first(@NonNull T defaultItem) {
        return elementAt(0L, defaultItem);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> firstElement() {
        return elementAt(0L);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> firstOrError() {
        return elementAtOrError(0L);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final CompletionStage<T> firstOrErrorStage() {
        return (CompletionStage) subscribeWith(new ObservableFirstStageObserver(false, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final CompletionStage<T> firstStage(@Nullable T defaultItem) {
        return (CompletionStage) subscribeWith(new ObservableFirstStageObserver(true, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
        return flatMap((Function) mapper, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable flatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        return flatMapCompletable(mapper, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<U> flatMapIterable(@NonNull Function<? super T, ? extends Iterable<? extends U>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableFlattenIterable(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        return flatMapMaybe(mapper, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        return flatMapSingle(mapper, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMapStream(@NonNull Function<? super T, ? extends Stream<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableFlatMapStream(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable forEach(@NonNull Consumer<? super T> onNext) {
        return subscribe(onNext);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable forEachWhile(@NonNull Predicate<? super T> onNext) {
        return forEachWhile(onNext, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K> Observable<GroupedObservable<K, T>> groupBy(@NonNull Function<? super T, ? extends K> function) {
        return (Observable<GroupedObservable<K, T>>) groupBy(function, Functions.identity(), false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <TRight, TLeftEnd, TRightEnd, R> Observable<R> groupJoin(@NonNull ObservableSource<? extends TRight> other, @NonNull Function<? super T, ? extends ObservableSource<TLeftEnd>> leftEnd, @NonNull Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd, @NonNull BiFunction<? super T, ? super Observable<TRight>, ? extends R> resultSelector) {
        Objects.requireNonNull(other, "other is null");
        Objects.requireNonNull(leftEnd, "leftEnd is null");
        Objects.requireNonNull(rightEnd, "rightEnd is null");
        Objects.requireNonNull(resultSelector, "resultSelector is null");
        return RxJavaPlugins.onAssembly(new ObservableGroupJoin(this, other, leftEnd, rightEnd, resultSelector));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> hide() {
        return RxJavaPlugins.onAssembly(new ObservableHide(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable ignoreElements() {
        return RxJavaPlugins.onAssembly(new ObservableIgnoreElementsCompletable(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<Boolean> isEmpty() {
        return all(Functions.alwaysFalse());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <TRight, TLeftEnd, TRightEnd, R> Observable<R> join(@NonNull ObservableSource<? extends TRight> other, @NonNull Function<? super T, ? extends ObservableSource<TLeftEnd>> leftEnd, @NonNull Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd, @NonNull BiFunction<? super T, ? super TRight, ? extends R> resultSelector) {
        Objects.requireNonNull(other, "other is null");
        Objects.requireNonNull(leftEnd, "leftEnd is null");
        Objects.requireNonNull(rightEnd, "rightEnd is null");
        Objects.requireNonNull(resultSelector, "resultSelector is null");
        return RxJavaPlugins.onAssembly(new ObservableJoin(this, other, leftEnd, rightEnd, resultSelector));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> last(@NonNull T defaultItem) {
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        return RxJavaPlugins.onAssembly(new ObservableLastSingle(this, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> lastElement() {
        return RxJavaPlugins.onAssembly(new ObservableLastMaybe(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> lastOrError() {
        return RxJavaPlugins.onAssembly(new ObservableLastSingle(this, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final CompletionStage<T> lastOrErrorStage() {
        return (CompletionStage) subscribeWith(new ObservableLastStageObserver(false, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final CompletionStage<T> lastStage(@Nullable T defaultItem) {
        return (CompletionStage) subscribeWith(new ObservableLastStageObserver(true, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> lift(@NonNull ObservableOperator<? extends R, ? super T> lifter) {
        Objects.requireNonNull(lifter, "lifter is null");
        return RxJavaPlugins.onAssembly(new ObservableLift(this, lifter));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> map(@NonNull Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableMap(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> mapOptional(@NonNull Function<? super T, Optional<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableMapOptional(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Notification<T>> materialize() {
        return RxJavaPlugins.onAssembly(new ObservableMaterialize(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> mergeWith(@NonNull ObservableSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return merge(this, other);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> observeOn(@NonNull Scheduler scheduler) {
        return observeOn(scheduler, false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<U> ofType(@NonNull Class<U> clazz) {
        Objects.requireNonNull(clazz, "clazz is null");
        return filter(Functions.isInstanceOf(clazz)).cast(clazz);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> onErrorComplete() {
        return onErrorComplete(Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> onErrorResumeNext(@NonNull Function<? super Throwable, ? extends ObservableSource<? extends T>> fallbackSupplier) {
        Objects.requireNonNull(fallbackSupplier, "fallbackSupplier is null");
        return RxJavaPlugins.onAssembly(new ObservableOnErrorNext(this, fallbackSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> onErrorResumeWith(@NonNull ObservableSource<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return onErrorResumeNext(Functions.justFunction(fallback));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> onErrorReturn(@NonNull Function<? super Throwable, ? extends T> itemSupplier) {
        Objects.requireNonNull(itemSupplier, "itemSupplier is null");
        return RxJavaPlugins.onAssembly(new ObservableOnErrorReturn(this, itemSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> onErrorReturnItem(@NonNull T item) {
        Objects.requireNonNull(item, "item is null");
        return onErrorReturn(Functions.justFunction(item));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> onTerminateDetach() {
        return RxJavaPlugins.onAssembly(new ObservableDetach(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final ConnectableObservable<T> publish() {
        return RxJavaPlugins.onAssembly((ConnectableObservable) new ObservablePublish(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> reduce(@NonNull BiFunction<T, T, T> reducer) {
        Objects.requireNonNull(reducer, "reducer is null");
        return RxJavaPlugins.onAssembly(new ObservableReduceMaybe(this, reducer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> reduceWith(@NonNull Supplier<R> seedSupplier, @NonNull BiFunction<R, ? super T, R> reducer) {
        Objects.requireNonNull(seedSupplier, "seedSupplier is null");
        Objects.requireNonNull(reducer, "reducer is null");
        return RxJavaPlugins.onAssembly(new ObservableReduceWithSingle(this, seedSupplier, reducer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> repeat() {
        return repeat(Long.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> repeatUntil(@NonNull BooleanSupplier stop) {
        Objects.requireNonNull(stop, "stop is null");
        return RxJavaPlugins.onAssembly(new ObservableRepeatUntil(this, stop));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> repeatWhen(@NonNull Function<? super Observable<Object>, ? extends ObservableSource<?>> handler) {
        Objects.requireNonNull(handler, "handler is null");
        return RxJavaPlugins.onAssembly(new ObservableRepeatWhen(this, handler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final ConnectableObservable<T> replay() {
        return ObservableReplay.createFrom(this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> retry() {
        return retry(Long.MAX_VALUE, Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> retryUntil(@NonNull BooleanSupplier stop) {
        Objects.requireNonNull(stop, "stop is null");
        return retry(Long.MAX_VALUE, Functions.predicateReverseFor(stop));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> retryWhen(@NonNull Function<? super Observable<Throwable>, ? extends ObservableSource<?>> handler) {
        Objects.requireNonNull(handler, "handler is null");
        return RxJavaPlugins.onAssembly(new ObservableRetryWhen(this, handler));
    }

    @SchedulerSupport("none")
    public final void safeSubscribe(@NonNull Observer<? super T> observer) {
        Objects.requireNonNull(observer, "observer is null");
        if (observer instanceof SafeObserver) {
            subscribe(observer);
        } else {
            subscribe(new SafeObserver(observer));
        }
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> sample(long period, @NonNull TimeUnit unit) {
        return sample(period, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> scan(@NonNull BiFunction<T, T, T> accumulator) {
        Objects.requireNonNull(accumulator, "accumulator is null");
        return RxJavaPlugins.onAssembly(new ObservableScan(this, accumulator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> scanWith(@NonNull Supplier<R> seedSupplier, @NonNull BiFunction<R, ? super T, R> accumulator) {
        Objects.requireNonNull(seedSupplier, "seedSupplier is null");
        Objects.requireNonNull(accumulator, "accumulator is null");
        return RxJavaPlugins.onAssembly(new ObservableScanSeed(this, seedSupplier, accumulator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> serialize() {
        return RxJavaPlugins.onAssembly(new ObservableSerialized(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> share() {
        return publish().refCount();
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> single(@NonNull T defaultItem) {
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        return RxJavaPlugins.onAssembly(new ObservableSingleSingle(this, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Maybe<T> singleElement() {
        return RxJavaPlugins.onAssembly(new ObservableSingleMaybe(this));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> singleOrError() {
        return RxJavaPlugins.onAssembly(new ObservableSingleSingle(this, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final CompletionStage<T> singleOrErrorStage() {
        return (CompletionStage) subscribeWith(new ObservableSingleStageObserver(false, null));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final CompletionStage<T> singleStage(@Nullable T defaultItem) {
        return (CompletionStage) subscribeWith(new ObservableSingleStageObserver(true, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> skip(long count) {
        if (count >= 0) {
            return count == 0 ? RxJavaPlugins.onAssembly(this) : RxJavaPlugins.onAssembly(new ObservableSkip(this, count));
        }
        throw new IllegalArgumentException("count >= 0 expected but it was " + count);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> skipLast(int count) {
        if (count >= 0) {
            return count == 0 ? RxJavaPlugins.onAssembly(this) : RxJavaPlugins.onAssembly(new ObservableSkipLast(this, count));
        }
        throw new IllegalArgumentException("count >= 0 required but it was " + count);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<T> skipUntil(@NonNull ObservableSource<U> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new ObservableSkipUntil(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> skipWhile(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new ObservableSkipWhile(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> sorted() {
        return toList().toObservable().map(Functions.listSorter(Functions.naturalComparator())).flatMapIterable(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> startWith(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return concat(Completable.wrap(other).toObservable(), this);
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public final Observable<T> startWithArray(@NonNull T... items) {
        Observable observableFromArray = fromArray(items);
        return observableFromArray == empty() ? RxJavaPlugins.onAssembly(this) : concatArray(observableFromArray, this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> startWithItem(@NonNull T item) {
        return concatArray(just(item), this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> startWithIterable(@NonNull Iterable<? extends T> items) {
        return concatArray(fromIterable(items), this);
    }

    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe() {
        return subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    public abstract void subscribeActual(@NonNull Observer<? super T> observer);

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> subscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableSubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <E extends Observer<? super T>> E subscribeWith(E observer) {
        subscribe(observer);
        return observer;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> switchIfEmpty(@NonNull ObservableSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new ObservableSwitchIfEmpty(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> switchMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
        return switchMap(mapper, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable switchMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableSwitchMapCompletable(this, mapper, false));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable switchMapCompletableDelayError(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableSwitchMapCompletable(this, mapper, true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> switchMapDelayError(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
        return switchMapDelayError(mapper, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> switchMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableSwitchMapMaybe(this, mapper, false));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> switchMapMaybeDelayError(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableSwitchMapMaybe(this, mapper, true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> switchMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableSwitchMapSingle(this, mapper, false));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> switchMapSingleDelayError(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableSwitchMapSingle(this, mapper, true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> take(long count) {
        if (count >= 0) {
            return RxJavaPlugins.onAssembly(new ObservableTake(this, count));
        }
        throw new IllegalArgumentException("count >= 0 required but it was " + count);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> takeLast(int count) {
        if (count >= 0) {
            return count == 0 ? RxJavaPlugins.onAssembly(new ObservableIgnoreElements(this)) : count == 1 ? RxJavaPlugins.onAssembly(new ObservableTakeLastOne(this)) : RxJavaPlugins.onAssembly(new ObservableTakeLast(this, count));
        }
        throw new IllegalArgumentException("count >= 0 required but it was " + count);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<T> takeUntil(@NonNull ObservableSource<U> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new ObservableTakeUntil(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> takeWhile(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new ObservableTakeWhile(this, predicate));
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
    public final Observable<T> throttleFirst(long windowDuration, @NonNull TimeUnit unit) {
        return throttleFirst(windowDuration, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> throttleLast(long intervalDuration, @NonNull TimeUnit unit) {
        return sample(intervalDuration, unit);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> throttleLatest(long timeout, @NonNull TimeUnit unit) {
        return throttleLatest(timeout, unit, Schedulers.computation(), false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> throttleWithTimeout(long timeout, @NonNull TimeUnit unit) {
        return debounce(timeout, unit);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Timed<T>> timeInterval() {
        return timeInterval(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <V> Observable<T> timeout(@NonNull Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator) {
        return timeout0(null, itemTimeoutIndicator, null);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Timed<T>> timestamp() {
        return timestamp(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> R to(@NonNull ObservableConverter<T, ? extends R> converter) {
        Objects.requireNonNull(converter, "converter is null");
        return converter.apply(this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final Flowable<T> toFlowable(@NonNull BackpressureStrategy strategy) {
        Objects.requireNonNull(strategy, "strategy is null");
        FlowableFromObservable flowableFromObservable = new FlowableFromObservable(this);
        int i2 = AnonymousClass1.$SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy[strategy.ordinal()];
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? flowableFromObservable.onBackpressureBuffer() : RxJavaPlugins.onAssembly(new FlowableOnBackpressureError(flowableFromObservable)) : flowableFromObservable : flowableFromObservable.onBackpressureLatest() : flowableFromObservable.onBackpressureDrop();
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Future<T> toFuture() {
        return (Future) subscribeWith(new FutureObserver());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<List<T>> toList() {
        return toList(16);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K> Single<Map<K, T>> toMap(@NonNull Function<? super T, ? extends K> function) {
        Objects.requireNonNull(function, "keySelector is null");
        return (Single<Map<K, T>>) collect(HashMapSupplier.asSupplier(), Functions.toMapKeySelector(function));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K> Single<Map<K, Collection<T>>> toMultimap(@NonNull Function<? super T, ? extends K> function) {
        return (Single<Map<K, Collection<T>>>) toMultimap(function, Functions.identity(), HashMapSupplier.asSupplier(), ArrayListSupplier.asFunction());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<List<T>> toSortedList() {
        return toSortedList(Functions.naturalComparator());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> unsubscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableUnsubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Observable<T>> window(long count) {
        return window(count, count, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Observable<R> withLatestFrom(@NonNull ObservableSource<? extends U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner) {
        Objects.requireNonNull(other, "other is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return RxJavaPlugins.onAssembly(new ObservableWithLatestFrom(this, combiner, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Observable<R> zipWith(@NonNull Iterable<U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> zipper) {
        Objects.requireNonNull(other, "other is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return RxJavaPlugins.onAssembly(new ObservableZipIterable(this, other, zipper));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Observable<R> combineLatest(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> combiner, int bufferSize) {
        Objects.requireNonNull(sources, "sources is null");
        Objects.requireNonNull(combiner, "combiner is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableCombineLatest(null, sources, combiner, bufferSize << 1, false));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Observable<R> combineLatestArray(@NonNull ObservableSource<? extends T>[] sources, @NonNull Function<? super Object[], ? extends R> combiner, int bufferSize) {
        Objects.requireNonNull(sources, "sources is null");
        if (sources.length == 0) {
            return empty();
        }
        Objects.requireNonNull(combiner, "combiner is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableCombineLatest(sources, null, combiner, bufferSize << 1, false));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Observable<R> combineLatestArrayDelayError(@NonNull ObservableSource<? extends T>[] sources, @NonNull Function<? super Object[], ? extends R> combiner, int bufferSize) {
        Objects.requireNonNull(sources, "sources is null");
        Objects.requireNonNull(combiner, "combiner is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return sources.length == 0 ? empty() : RxJavaPlugins.onAssembly(new ObservableCombineLatest(sources, null, combiner, bufferSize << 1, true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Observable<R> combineLatestDelayError(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> combiner, int bufferSize) {
        Objects.requireNonNull(sources, "sources is null");
        Objects.requireNonNull(combiner, "combiner is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableCombineLatest(null, sources, combiner, bufferSize << 1, true));
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> concatArrayEager(int maxConcurrency, int bufferSize, @NonNull ObservableSource<? extends T>... sources) {
        return fromArray(sources).concatMapEagerDelayError(Functions.identity(), false, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> concatArrayEagerDelayError(int maxConcurrency, int bufferSize, @NonNull ObservableSource<? extends T>... sources) {
        return fromArray(sources).concatMapEagerDelayError(Functions.identity(), true, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concatEager(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int bufferSize) {
        return fromIterable(sources).concatMapEagerDelayError(Functions.identity(), false, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concatEagerDelayError(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int bufferSize) {
        return fromIterable(sources).concatMapEagerDelayError(Functions.identity(), true, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public static Observable<Long> interval(long initialDelay, long period, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableInterval(Math.max(0L, initialDelay), Math.max(0L, period), unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public static Observable<Long> intervalRange(long start, long count, long initialDelay, long period, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        if (count < 0) {
            throw new IllegalArgumentException("count >= 0 required but it was " + count);
        }
        if (count == 0) {
            return empty().delay(initialDelay, unit, scheduler);
        }
        long j2 = start + (count - 1);
        if (start > 0 && j2 < 0) {
            throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
        }
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableIntervalRange(start, j2, Math.max(0L, initialDelay), Math.max(0L, period), unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> merge(@NonNull Iterable<? extends ObservableSource<? extends T>> sources) {
        return fromIterable(sources).flatMap(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeArray(@NonNull ObservableSource<? extends T>... sources) {
        return fromArray(sources).flatMap(Functions.identity(), sources.length);
    }

    @CheckReturnValue
    @NonNull
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeArrayDelayError(@NonNull ObservableSource<? extends T>... sources) {
        return fromArray(sources).flatMap(Functions.identity(), true, sources.length);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeDelayError(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int bufferSize) {
        return fromIterable(sources).flatMap(Functions.identity(), true, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2, @NonNull BiPredicate<? super T, ? super T> isEqual) {
        return sequenceEqual(source1, source2, isEqual, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> switchOnNextDelayError(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources, int bufferSize) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableSwitchMap(sources, Functions.identity(), bufferSize, true));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public static Observable<Long> timer(long delay, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableTimer(Math.max(delay, 0L), unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, D> Observable<T> using(@NonNull Supplier<? extends D> resourceSupplier, @NonNull Function<? super D, ? extends ObservableSource<? extends T>> sourceSupplier, @NonNull Consumer<? super D> resourceCleanup, boolean eager) {
        Objects.requireNonNull(resourceSupplier, "resourceSupplier is null");
        Objects.requireNonNull(sourceSupplier, "sourceSupplier is null");
        Objects.requireNonNull(resourceCleanup, "resourceCleanup is null");
        return RxJavaPlugins.onAssembly(new ObservableUsing(resourceSupplier, sourceSupplier, resourceCleanup, eager));
    }

    @SchedulerSupport("none")
    public final void blockingForEach(@NonNull Consumer<? super T> onNext, int capacityHint) {
        Objects.requireNonNull(onNext, "onNext is null");
        Iterator<T> it = blockingIterable(capacityHint).iterator();
        while (it.hasNext()) {
            try {
                onNext.accept(it.next());
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                ((Disposable) it).dispose();
                throw ExceptionHelper.wrapOrThrow(th);
            }
        }
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Iterable<T> blockingIterable(int capacityHint) {
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return new BlockingObservableIterable(this, capacityHint);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Stream<T> blockingStream(int capacityHint) {
        Iterator<T> it = blockingIterable(capacityHint).iterator();
        Stream stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, 0), false);
        Disposable disposable = (Disposable) it;
        disposable.getClass();
        return (Stream) stream.onClose(new a(disposable));
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onNext) {
        ObservableBlockingSubscribe.subscribe(this, onNext, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<List<T>> buffer(int i2, int i3) {
        return (Observable<List<T>>) buffer(i2, i3, ArrayListSupplier.asSupplier());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        if (!(this instanceof ScalarSupplier)) {
            return RxJavaPlugins.onAssembly(new ObservableConcatMap(this, mapper, bufferSize, ErrorMode.IMMEDIATE));
        }
        Object obj = ((ScalarSupplier) this).get();
        return obj == null ? empty() : ObservableScalarXMap.scalarXMap(obj, mapper);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable concatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper, int capacityHint) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapCompletable(this, mapper, ErrorMode.IMMEDIATE, capacityHint));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable concatMapCompletableDelayError(@NonNull Function<? super T, ? extends CompletableSource> mapper, boolean tillTheEnd) {
        return concatMapCompletableDelayError(mapper, tillTheEnd, 2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapDelayError(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean tillTheEnd, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        if (!(this instanceof ScalarSupplier)) {
            return RxJavaPlugins.onAssembly(new ObservableConcatMap(this, mapper, bufferSize, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY));
        }
        Object obj = ((ScalarSupplier) this).get();
        return obj == null ? empty() : ObservableScalarXMap.scalarXMap(obj, mapper);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapEager(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, int maxConcurrency, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapEager(this, mapper, ErrorMode.IMMEDIATE, maxConcurrency, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapEagerDelayError(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean tillTheEnd, int maxConcurrency, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapEager(this, mapper, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, maxConcurrency, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapMaybe(this, mapper, ErrorMode.IMMEDIATE, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapMaybeDelayError(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean tillTheEnd) {
        return concatMapMaybeDelayError(mapper, tillTheEnd, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapSingle(this, mapper, ErrorMode.IMMEDIATE, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapSingleDelayError(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean tillTheEnd) {
        return concatMapSingleDelayError(mapper, tillTheEnd, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K> Observable<T> distinct(@NonNull Function<? super T, K> keySelector) {
        return distinct(keySelector, Functions.createHashSet());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K> Observable<T> distinctUntilChanged(@NonNull Function<? super T, K> keySelector) {
        Objects.requireNonNull(keySelector, "keySelector is null");
        return RxJavaPlugins.onAssembly(new ObservableDistinctUntilChanged(this, keySelector, ObjectHelper.equalsPredicate()));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean delayErrors) {
        return flatMap(mapper, delayErrors, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable flatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper, boolean delayErrors) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableFlatMapCompletableCompletable(this, mapper, delayErrors));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean delayErrors) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableFlatMapMaybe(this, mapper, delayErrors));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean delayErrors) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableFlatMapSingle(this, mapper, delayErrors));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable forEachWhile(@NonNull Predicate<? super T> onNext, @NonNull Consumer<? super Throwable> onError) {
        return forEachWhile(onNext, onError, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K> Observable<GroupedObservable<K, T>> groupBy(@NonNull Function<? super T, ? extends K> function, boolean z2) {
        return (Observable<GroupedObservable<K, T>>) groupBy(function, Functions.identity(), z2, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> observeOn(@NonNull Scheduler scheduler, boolean delayError) {
        return observeOn(scheduler, delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> onErrorComplete(@NonNull Predicate<? super Throwable> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new ObservableOnErrorComplete(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> publish(@NonNull Function<? super Observable<T>, ? extends ObservableSource<R>> selector) {
        Objects.requireNonNull(selector, "selector is null");
        return RxJavaPlugins.onAssembly(new ObservablePublishSelector(this, selector));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> repeat(long times) {
        if (times >= 0) {
            return times == 0 ? empty() : RxJavaPlugins.onAssembly(new ObservableRepeat(this, times));
        }
        throw new IllegalArgumentException("times >= 0 required but it was " + times);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> replay(@NonNull Function<? super Observable<T>, ? extends ObservableSource<R>> selector) {
        Objects.requireNonNull(selector, "selector is null");
        return ObservableReplay.multicastSelector(ObservableInternalHelper.replaySupplier(this), selector);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> retry(@NonNull BiPredicate<? super Integer, ? super Throwable> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new ObservableRetryBiPredicate(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> sample(long period, @NonNull TimeUnit unit, boolean emitLast) {
        return sample(period, unit, Schedulers.computation(), emitLast);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> sorted(@NonNull Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator is null");
        return toList().toObservable().map(Functions.listSorter(comparator)).flatMapIterable(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onNext) {
        return subscribe(onNext, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> switchMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        if (!(this instanceof ScalarSupplier)) {
            return RxJavaPlugins.onAssembly(new ObservableSwitchMap(this, mapper, bufferSize, false));
        }
        Object obj = ((ScalarSupplier) this).get();
        return obj == null ? empty() : ObservableScalarXMap.scalarXMap(obj, mapper);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> switchMapDelayError(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        if (!(this instanceof ScalarSupplier)) {
            return RxJavaPlugins.onAssembly(new ObservableSwitchMap(this, mapper, bufferSize, true));
        }
        Object obj = ((ScalarSupplier) this).get();
        return obj == null ? empty() : ObservableScalarXMap.scalarXMap(obj, mapper);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> throttleFirst(long skipDuration, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableThrottleFirstTimed(this, skipDuration, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> throttleLast(long intervalDuration, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return sample(intervalDuration, unit, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> throttleLatest(long timeout, @NonNull TimeUnit unit, boolean emitLast) {
        return throttleLatest(timeout, unit, Schedulers.computation(), emitLast);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> throttleWithTimeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return debounce(timeout, unit, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Timed<T>> timeInterval(@NonNull Scheduler scheduler) {
        return timeInterval(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <V> Observable<T> timeout(@NonNull Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator, @NonNull ObservableSource<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(null, itemTimeoutIndicator, fallback);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Timed<T>> timestamp(@NonNull Scheduler scheduler) {
        return timestamp(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<List<T>> toList(int capacityHint) {
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return RxJavaPlugins.onAssembly(new ObservableToListSingle(this, capacityHint));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<List<T>> toSortedList(@NonNull Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator is null");
        return (Single<List<T>>) toList().map(Functions.listSorter(comparator));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Observable<T>> window(long count, long skip) {
        return window(count, skip, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concat(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources) {
        return concat(sources, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concatDelayError(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources) {
        return concatDelayError(sources, bufferSize(), true);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concatEager(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources) {
        return concatEager(sources, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concatEagerDelayError(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources) {
        return concatEagerDelayError(sources, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> error(@NonNull Throwable throwable) {
        Objects.requireNonNull(throwable, "throwable is null");
        return error((Supplier<? extends Throwable>) Functions.justSupplier(throwable));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> fromFuture(@NonNull Future<? extends T> future, long timeout, @NonNull TimeUnit unit) {
        Objects.requireNonNull(future, "future is null");
        Objects.requireNonNull(unit, "unit is null");
        return RxJavaPlugins.onAssembly(new ObservableFromFuture(future, timeout, unit));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> just(@NonNull T item1, @NonNull T item2) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        return fromArray(item1, item2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> merge(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency) {
        return fromIterable(sources).flatMap(Functions.identity(), maxConcurrency);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeDelayError(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency) {
        return fromIterable(sources).flatMap(Functions.identity(), true, maxConcurrency);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2, @NonNull BiPredicate<? super T, ? super T> isEqual, int bufferSize) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(isEqual, "isEqual is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableSequenceEqualSingle(source1, source2, isEqual, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final T blockingSingle(@NonNull T defaultItem) {
        return single(defaultItem).blockingGet();
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError) {
        ObservableBlockingSubscribe.subscribe(this, onNext, onError, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U extends Collection<? super T>> Observable<U> buffer(int count, int skip, @NonNull Supplier<U> bufferSupplier) {
        ObjectHelper.verifyPositive(count, "count");
        ObjectHelper.verifyPositive(skip, "skip");
        Objects.requireNonNull(bufferSupplier, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new ObservableBuffer(this, count, skip, bufferSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Completable concatMapCompletableDelayError(@NonNull Function<? super T, ? extends CompletableSource> mapper, boolean tillTheEnd, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapCompletable(this, mapper, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapMaybeDelayError(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean tillTheEnd, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapMaybe(this, mapper, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> concatMapSingleDelayError(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean tillTheEnd, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapSingle(this, mapper, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> concatWith(@NonNull SingleSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new ObservableConcatWithSingle(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> debounce(long timeout, @NonNull TimeUnit unit) {
        return debounce(timeout, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> delay(long time, @NonNull TimeUnit unit) {
        return delay(time, unit, Schedulers.computation(), false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> delaySubscription(long time, @NonNull TimeUnit unit) {
        return delaySubscription(time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K> Observable<T> distinct(@NonNull Function<? super T, K> keySelector, @NonNull Supplier<? extends Collection<? super K>> collectionSupplier) {
        Objects.requireNonNull(keySelector, "keySelector is null");
        Objects.requireNonNull(collectionSupplier, "collectionSupplier is null");
        return RxJavaPlugins.onAssembly(new ObservableDistinct(this, keySelector, collectionSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<T> elementAt(long index, @NonNull T defaultItem) {
        if (index >= 0) {
            Objects.requireNonNull(defaultItem, "defaultItem is null");
            return RxJavaPlugins.onAssembly(new ObservableElementAtSingle(this, index, defaultItem));
        }
        throw new IndexOutOfBoundsException("index >= 0 required but it was " + index);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean delayErrors, int maxConcurrency) {
        return flatMap(mapper, delayErrors, maxConcurrency, bufferSize());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, V> Observable<V> flatMapIterable(@NonNull Function<? super T, ? extends Iterable<? extends U>> function, @NonNull BiFunction<? super T, ? super U, ? extends V> biFunction) {
        Objects.requireNonNull(function, "mapper is null");
        Objects.requireNonNull(biFunction, "combiner is null");
        return (Observable<V>) flatMap(ObservableInternalHelper.flatMapIntoIterable(function), biFunction, false, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable forEachWhile(@NonNull Predicate<? super T> onNext, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete) {
        Objects.requireNonNull(onNext, "onNext is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        ForEachWhileObserver forEachWhileObserver = new ForEachWhileObserver(onNext, onError, onComplete);
        subscribe(forEachWhileObserver);
        return forEachWhileObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K, V> Observable<GroupedObservable<K, V>> groupBy(@NonNull Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector) {
        return groupBy(keySelector, valueSelector, false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> mergeWith(@NonNull SingleSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new ObservableMergeWithSingle(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> observeOn(@NonNull Scheduler scheduler, boolean delayError, int bufferSize) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableObserveOn(this, scheduler, delayError, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Single<R> reduce(R seed, @NonNull BiFunction<R, ? super T, R> reducer) {
        Objects.requireNonNull(seed, "seed is null");
        Objects.requireNonNull(reducer, "reducer is null");
        return RxJavaPlugins.onAssembly(new ObservableReduceSeedSingle(this, seed, reducer));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> sample(long period, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableSampleTimed(this, period, unit, scheduler, false));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> scan(@NonNull R initialValue, @NonNull BiFunction<R, ? super T, R> accumulator) {
        Objects.requireNonNull(initialValue, "initialValue is null");
        return scanWith(Functions.justSupplier(initialValue), accumulator);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> startWith(@NonNull SingleSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return concat(Single.wrap(other).toObservable(), this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError) {
        return subscribe(onNext, onError, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> take(long time, @NonNull TimeUnit unit) {
        return takeUntil(timer(time, unit));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> takeUntil(@NonNull Predicate<? super T> stopPredicate) {
        Objects.requireNonNull(stopPredicate, "stopPredicate is null");
        return RxJavaPlugins.onAssembly(new ObservableTakeUntilPredicate(this, stopPredicate));
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
    @SchedulerSupport("custom")
    public final Observable<T> throttleLatest(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return throttleLatest(timeout, unit, scheduler, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Timed<T>> timeInterval(@NonNull TimeUnit unit) {
        return timeInterval(unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Timed<T>> timestamp(@NonNull TimeUnit unit) {
        return timestamp(unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K, V> Single<Map<K, V>> toMap(@NonNull Function<? super T, ? extends K> function, @NonNull Function<? super T, ? extends V> function2) {
        Objects.requireNonNull(function, "keySelector is null");
        Objects.requireNonNull(function2, "valueSelector is null");
        return (Single<Map<K, V>>) collect(HashMapSupplier.asSupplier(), Functions.toMapKeyValueSelector(function, function2));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Observable<T>> window(long count, long skip, int bufferSize) {
        ObjectHelper.verifyPositive(count, "count");
        ObjectHelper.verifyPositive(skip, "skip");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableWindow(this, count, skip, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concat(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources, int bufferSize) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableConcatMap(sources, Functions.identity(), bufferSize, ErrorMode.IMMEDIATE));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concatDelayError(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources, int bufferSize, boolean tillTheEnd) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize is null");
        return RxJavaPlugins.onAssembly(new ObservableConcatMap(sources, Functions.identity(), bufferSize, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concatEager(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int bufferSize) {
        return wrap(sources).concatMapEager(Functions.identity(), maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concatEagerDelayError(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int bufferSize) {
        return wrap(sources).concatMapEagerDelayError(Functions.identity(), true, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> merge(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new ObservableFlatMap(sources, Functions.identity(), false, Integer.MAX_VALUE, bufferSize()));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeDelayError(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new ObservableFlatMap(sources, Functions.identity(), true, Integer.MAX_VALUE, bufferSize()));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> switchOnNext(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources) {
        return switchOnNext(sources, bufferSize());
    }

    @NonNull
    private <U, V> Observable<T> timeout0(@NonNull ObservableSource<U> firstTimeoutIndicator, @NonNull Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator, @Nullable ObservableSource<? extends T> fallback) {
        Objects.requireNonNull(itemTimeoutIndicator, "itemTimeoutIndicator is null");
        return RxJavaPlugins.onAssembly(new ObservableTimeout(this, firstTimeoutIndicator, itemTimeoutIndicator, fallback));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, R> Observable<R> zip(@NonNull Iterable<? extends ObservableSource<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> zipper, boolean delayError, int bufferSize) {
        Objects.requireNonNull(zipper, "zipper is null");
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableZip(null, sources, zipper, bufferSize, delayError));
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete) {
        ObservableBlockingSubscribe.subscribe(this, onNext, onError, onComplete);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R, A> Single<R> collect(@NonNull Collector<? super T, A, R> collector) {
        Objects.requireNonNull(collector, "collector is null");
        return RxJavaPlugins.onAssembly(new ObservableCollectWithCollectorSingle(this, collector));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> debounce(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableDebounceTimed(this, timeout, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> delay(long time, @NonNull TimeUnit unit, boolean delayError) {
        return delay(time, unit, Schedulers.computation(), delayError);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> delaySubscription(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return delaySubscription(timer(time, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> distinctUntilChanged(@NonNull BiPredicate<? super T, ? super T> comparer) {
        Objects.requireNonNull(comparer, "comparer is null");
        return RxJavaPlugins.onAssembly(new ObservableDistinctUntilChanged(this, Functions.identity(), comparer));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        if (this instanceof ScalarSupplier) {
            Object obj = ((ScalarSupplier) this).get();
            if (obj == null) {
                return empty();
            }
            return ObservableScalarXMap.scalarXMap(obj, mapper);
        }
        return RxJavaPlugins.onAssembly(new ObservableFlatMap(this, mapper, delayErrors, maxConcurrency, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K, V> Observable<GroupedObservable<K, V>> groupBy(@NonNull Function<? super T, ? extends K> keySelector, @NonNull Function<? super T, ? extends V> valueSelector, boolean delayError) {
        return groupBy(keySelector, valueSelector, delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> replay(@NonNull Function<? super Observable<T>, ? extends ObservableSource<R>> selector, int bufferSize) {
        Objects.requireNonNull(selector, "selector is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return ObservableReplay.multicastSelector(ObservableInternalHelper.replaySupplier(this, bufferSize, false), selector);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> retry(long times) {
        return retry(times, Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> skip(long time, @NonNull TimeUnit unit) {
        return skipUntil(timer(time, unit));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:trampoline")
    public final Observable<T> skipLast(long time, @NonNull TimeUnit unit) {
        return skipLast(time, unit, Schedulers.trampoline(), false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete) {
        Objects.requireNonNull(onNext, "onNext is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        LambdaObserver lambdaObserver = new LambdaObserver(onNext, onError, onComplete, Functions.emptyConsumer());
        subscribe(lambdaObserver);
        return lambdaObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> take(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return takeUntil(timer(time, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> throttleLatest(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean emitLast) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableThrottleLatest(this, timeout, unit, scheduler, emitLast));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Timed<T>> timeInterval(@NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableTimeInterval(this, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> timeout(long timeout, @NonNull TimeUnit unit) {
        return timeout0(timeout, unit, null, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<Timed<T>> timestamp(@NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return (Observable<Timed<T>>) map(Functions.timestampWith(timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U extends Collection<? super T>> Single<U> toList(@NonNull Supplier<U> collectionSupplier) {
        Objects.requireNonNull(collectionSupplier, "collectionSupplier is null");
        return RxJavaPlugins.onAssembly(new ObservableToListSingle(this, collectionSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<List<T>> toSortedList(@NonNull Comparator<? super T> comparator, int i2) {
        Objects.requireNonNull(comparator, "comparator is null");
        return (Single<List<T>>) toList(i2).map(Functions.listSorter(comparator));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T1, T2, R> Observable<R> withLatestFrom(@NonNull ObservableSource<T1> source1, @NonNull ObservableSource<T2> source2, @NonNull Function3<? super T, ? super T1, ? super T2, R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return withLatestFrom((ObservableSource<?>[]) new ObservableSource[]{source1, source2}, Functions.toFunction(combiner));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Observable<R> zipWith(@NonNull ObservableSource<? extends U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> zipper) {
        Objects.requireNonNull(other, "other is null");
        return zip(this, other, zipper);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, S> Observable<T> generate(@NonNull Supplier<S> initialState, @NonNull BiConsumer<S, Emitter<T>> generator) {
        Objects.requireNonNull(generator, "generator is null");
        return generate(initialState, ObservableInternalHelper.simpleBiGenerator(generator), Functions.emptyConsumer());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public static Observable<Long> interval(long period, @NonNull TimeUnit unit) {
        return interval(period, period, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final T blockingFirst(@NonNull T defaultItem) throws InterruptedException {
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        BlockingFirstObserver blockingFirstObserver = new BlockingFirstObserver();
        subscribe(blockingFirstObserver);
        T tBlockingGet = blockingFirstObserver.blockingGet();
        return tBlockingGet != null ? tBlockingGet : defaultItem;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final T blockingLast(@NonNull T defaultItem) throws InterruptedException {
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        BlockingLastObserver blockingLastObserver = new BlockingLastObserver();
        subscribe(blockingLastObserver);
        T tBlockingGet = blockingLastObserver.blockingGet();
        return tBlockingGet != null ? tBlockingGet : defaultItem;
    }

    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Observer<? super T> observer) {
        Objects.requireNonNull(observer, "observer is null");
        ObservableBlockingSubscribe.subscribe(this, observer);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> concatWith(@NonNull MaybeSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new ObservableConcatWithMaybe(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> delay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return delay(time, unit, scheduler, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K, V> Observable<GroupedObservable<K, V>> groupBy(@NonNull Function<? super T, ? extends K> keySelector, @NonNull Function<? super T, ? extends V> valueSelector, boolean delayError, int bufferSize) {
        Objects.requireNonNull(keySelector, "keySelector is null");
        Objects.requireNonNull(valueSelector, "valueSelector is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableGroupBy(this, keySelector, valueSelector, bufferSize, delayError));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> mergeWith(@NonNull MaybeSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new ObservableMergeWithMaybe(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> retry(long times, @NonNull Predicate<? super Throwable> predicate) {
        if (times >= 0) {
            Objects.requireNonNull(predicate, "predicate is null");
            return RxJavaPlugins.onAssembly(new ObservableRetryPredicate(this, times, predicate));
        }
        throw new IllegalArgumentException("times >= 0 required but it was " + times);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> skip(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return skipUntil(timer(time, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:trampoline")
    public final Observable<T> skipLast(long time, @NonNull TimeUnit unit, boolean delayError) {
        return skipLast(time, unit, Schedulers.trampoline(), delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> startWith(@NonNull MaybeSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return concat(Maybe.wrap(other).toObservable(), this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:trampoline")
    public final Observable<T> takeLast(long count, long time, @NonNull TimeUnit unit) {
        return takeLast(count, time, unit, Schedulers.trampoline(), false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull ObservableSource<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(timeout, unit, fallback, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K, V> Single<Map<K, Collection<V>>> toMultimap(@NonNull Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector) {
        return toMultimap(keySelector, valueSelector, HashMapSupplier.asSupplier(), ArrayListSupplier.asFunction());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, R> Observable<R> combineLatest(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new ObservableSource[]{source1, source2}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public static Observable<Long> interval(long period, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return interval(period, period, unit, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> just(@NonNull T item1, @NonNull T item2, @NonNull T item3) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        Objects.requireNonNull(item3, "item3 is null");
        return fromArray(item1, item2, item3);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> merge(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources, int maxConcurrency) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new ObservableFlatMap(sources, Functions.identity(), false, maxConcurrency, bufferSize()));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeDelayError(@NonNull ObservableSource<? extends ObservableSource<? extends T>> sources, int maxConcurrency) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new ObservableFlatMap(sources, Functions.identity(), true, maxConcurrency, bufferSize()));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> delay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableDelay(this, time, unit, scheduler, delayError));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doOnEach(@NonNull Consumer<? super Notification<T>> onNotification) {
        Objects.requireNonNull(onNotification, "onNotification is null");
        return doOnEach(Functions.notificationOnNext(onNotification), Functions.notificationOnError(onNotification), Functions.notificationOnComplete(onNotification), Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> sample(long period, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean emitLast) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableSampleTimed(this, period, unit, scheduler, emitLast));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> skipLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return skipLast(time, unit, scheduler, false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> takeLast(long count, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return takeLast(count, time, unit, scheduler, false, bufferSize());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K, V> Single<Map<K, V>> toMap(@NonNull Function<? super T, ? extends K> function, @NonNull Function<? super T, ? extends V> function2, @NonNull Supplier<? extends Map<K, V>> supplier) {
        Objects.requireNonNull(function, "keySelector is null");
        Objects.requireNonNull(function2, "valueSelector is null");
        Objects.requireNonNull(supplier, "mapSupplier is null");
        return (Single<Map<K, V>>) collect(supplier, Functions.toMapKeyValueSelector(function, function2));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Single<List<T>> toSortedList(int capacityHint) {
        return toSortedList(Functions.naturalComparator(), capacityHint);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Observable<R> zipWith(@NonNull ObservableSource<? extends U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> zipper, boolean delayError) {
        return zip(this, other, zipper, delayError);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concat(@NonNull ObservableSource<? extends T> source1, ObservableSource<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return concatArray(source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, S> Observable<T> generate(@NonNull Supplier<S> initialState, @NonNull BiConsumer<S, Emitter<T>> generator, @NonNull Consumer<? super S> disposeState) {
        Objects.requireNonNull(generator, "generator is null");
        return generate(initialState, ObservableInternalHelper.simpleBiGenerator(generator), disposeState);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U extends Collection<? super T>> Observable<U> buffer(int count, @NonNull Supplier<U> bufferSupplier) {
        return buffer(count, count, bufferSupplier);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> concatWith(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new ObservableConcatWithCompletable(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> mergeWith(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new ObservableMergeWithCompletable(this, other));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> replay(@NonNull Function<? super Observable<T>, ? extends ObservableSource<R>> selector, int bufferSize, boolean eagerTruncate) {
        Objects.requireNonNull(selector, "selector is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return ObservableReplay.multicastSelector(ObservableInternalHelper.replaySupplier(this, bufferSize, eagerTruncate), selector);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> skipLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError) {
        return skipLast(time, unit, scheduler, delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> startWith(@NonNull ObservableSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return concatArray(other, this);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> takeLast(long count, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError, int bufferSize) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        if (count >= 0) {
            return RxJavaPlugins.onAssembly(new ObservableTakeLastTimed(this, count, time, unit, scheduler, bufferSize, delayError));
        }
        throw new IllegalArgumentException("count >= 0 required but it was " + count);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, @NonNull ObservableSource<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(timeout, unit, fallback, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<Observable<T>> window(long timespan, long timeskip, @NonNull TimeUnit unit) {
        return window(timespan, timeskip, unit, Schedulers.computation(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Observable<R> zipWith(@NonNull ObservableSource<? extends U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> zipper, boolean delayError, int bufferSize) {
        return zip(this, other, zipper, delayError, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2, int bufferSize) {
        return sequenceEqual(source1, source2, ObjectHelper.equalsPredicate(), bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, R> Observable<R> zip(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<List<T>> buffer(long j2, long j3, @NonNull TimeUnit timeUnit) {
        return (Observable<List<T>>) buffer(j2, j3, timeUnit, Schedulers.computation(), ArrayListSupplier.asSupplier());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> retry(@NonNull Predicate<? super Throwable> predicate) {
        return retry(Long.MAX_VALUE, predicate);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> skipLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError, int bufferSize) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableSkipLastTimed(this, time, unit, scheduler, bufferSize << 1, delayError));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K, V> Single<Map<K, Collection<V>>> toMultimap(@NonNull Function<? super T, ? extends K> function, @NonNull Function<? super T, ? extends V> function2, @NonNull Supplier<? extends Map<K, Collection<V>>> supplier, @NonNull Function<? super K, ? extends Collection<? super V>> function3) {
        Objects.requireNonNull(function, "keySelector is null");
        Objects.requireNonNull(function2, "valueSelector is null");
        Objects.requireNonNull(supplier, "mapSupplier is null");
        Objects.requireNonNull(function3, "collectionFactory is null");
        return (Single<Map<K, Collection<V>>>) collect(supplier, Functions.toMultimapKeyValueSelector(function, function2, function3));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<Observable<T>> window(long timespan, long timeskip, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return window(timespan, timeskip, unit, scheduler, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, S> Observable<T> generate(@NonNull Supplier<S> initialState, @NonNull BiFunction<S, Emitter<T>, S> generator) {
        return generate(initialState, generator, Functions.emptyConsumer());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> merge(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return fromArray(source1, source2).flatMap(Functions.identity(), false, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeDelayError(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return fromArray(source1, source2).flatMap(Functions.identity(), true, 2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<List<T>> buffer(long j2, long j3, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return (Observable<List<T>>) buffer(j2, j3, timeUnit, scheduler, ArrayListSupplier.asSupplier());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final <R> Observable<R> concatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, int bufferSize, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapScheduler(this, mapper, bufferSize, ErrorMode.IMMEDIATE, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final <R> Observable<R> concatMapDelayError(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean tillTheEnd, int bufferSize, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableConcatMapScheduler(this, mapper, bufferSize, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, scheduler));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, V> Observable<T> delay(@NonNull ObservableSource<U> subscriptionIndicator, @NonNull Function<? super T, ? extends ObservableSource<V>> itemDelayIndicator) {
        return delaySubscription(subscriptionIndicator).delay(itemDelayIndicator);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<T> sample(@NonNull ObservableSource<U> sampler) {
        Objects.requireNonNull(sampler, "sampler is null");
        return RxJavaPlugins.onAssembly(new ObservableSampleWithObservable(this, sampler, false));
    }

    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete, @NonNull DisposableContainer container) {
        Objects.requireNonNull(onNext, "onNext is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        Objects.requireNonNull(container, "container is null");
        DisposableAutoReleaseObserver disposableAutoReleaseObserver = new DisposableAutoReleaseObserver(container, onNext, onError, onComplete);
        container.add(disposableAutoReleaseObserver);
        subscribe(disposableAutoReleaseObserver);
        return disposableAutoReleaseObserver;
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return timeout0(timeout, unit, null, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<Observable<T>> window(long timespan, long timeskip, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, int bufferSize) {
        ObjectHelper.verifyPositive(timespan, "timespan");
        ObjectHelper.verifyPositive(timeskip, "timeskip");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(scheduler, "scheduler is null");
        Objects.requireNonNull(unit, "unit is null");
        return RxJavaPlugins.onAssembly(new ObservableWindowTimed(this, timespan, timeskip, unit, scheduler, Long.MAX_VALUE, bufferSize, false));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T1, T2, T3, R> Observable<R> withLatestFrom(@NonNull ObservableSource<T1> source1, @NonNull ObservableSource<T2> source2, @NonNull ObservableSource<T3> source3, @NonNull Function4<? super T, ? super T1, ? super T2, ? super T3, R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return withLatestFrom((ObservableSource<?>[]) new ObservableSource[]{source1, source2, source3}, Functions.toFunction(combiner));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, R> Observable<R> combineLatest(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull Function3<? super T1, ? super T2, ? super T3, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new ObservableSource[]{source1, source2, source3}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concat(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2, @NonNull ObservableSource<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return concatArray(source1, source2, source3);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T, S> Observable<T> generate(@NonNull Supplier<S> initialState, @NonNull BiFunction<S, Emitter<T>, S> generator, @NonNull Consumer<? super S> disposeState) {
        Objects.requireNonNull(initialState, "initialState is null");
        Objects.requireNonNull(generator, "generator is null");
        Objects.requireNonNull(disposeState, "disposeState is null");
        return RxJavaPlugins.onAssembly(new ObservableGenerate(initialState, generator, disposeState));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> just(@NonNull T item1, @NonNull T item2, @NonNull T item3, @NonNull T item4) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        Objects.requireNonNull(item3, "item3 is null");
        Objects.requireNonNull(item4, "item4 is null");
        return fromArray(item1, item2, item3, item4);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final <U extends Collection<? super T>> Observable<U> buffer(long timespan, long timeskip, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, @NonNull Supplier<U> bufferSupplier) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        Objects.requireNonNull(bufferSupplier, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new ObservableBufferTimed(this, timespan, timeskip, unit, scheduler, bufferSupplier, Integer.MAX_VALUE, false));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final <R> Observable<R> replay(@NonNull Function<? super Observable<T>, ? extends ObservableSource<R>> selector, int bufferSize, long time, @NonNull TimeUnit unit) {
        return replay(selector, bufferSize, time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, V> Observable<T> timeout(@NonNull ObservableSource<U> firstTimeoutIndicator, @NonNull Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator) {
        Objects.requireNonNull(firstTimeoutIndicator, "firstTimeoutIndicator is null");
        return timeout0(firstTimeoutIndicator, itemTimeoutIndicator, null);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final Observable<T> doOnEach(@NonNull Observer<? super T> observer) {
        Objects.requireNonNull(observer, "observer is null");
        return doOnEach(ObservableInternalHelper.observerOnNext(observer), ObservableInternalHelper.observerOnError(observer), ObservableInternalHelper.observerOnComplete(observer), Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final <R> Observable<R> replay(@NonNull Function<? super Observable<T>, ? extends ObservableSource<R>> selector, int bufferSize, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(selector, "selector is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return ObservableReplay.multicastSelector(ObservableInternalHelper.replaySupplier(this, bufferSize, time, unit, scheduler, false), selector);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U> Observable<T> sample(@NonNull ObservableSource<U> sampler, boolean emitLast) {
        Objects.requireNonNull(sampler, "sampler is null");
        return RxJavaPlugins.onAssembly(new ObservableSampleWithObservable(this, sampler, emitLast));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> merge(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2, @NonNull ObservableSource<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return fromArray(source1, source2, source3).flatMap(Functions.identity(), false, 3);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeDelayError(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2, @NonNull ObservableSource<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return fromArray(source1, source2, source3).flatMap(Functions.identity(), true, 3);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, R> Observable<R> zip(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> zipper, boolean delayError) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), delayError, bufferSize(), source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper, @NonNull Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper, @NonNull Supplier<? extends ObservableSource<? extends R>> onCompleteSupplier) {
        Objects.requireNonNull(onNextMapper, "onNextMapper is null");
        Objects.requireNonNull(onErrorMapper, "onErrorMapper is null");
        Objects.requireNonNull(onCompleteSupplier, "onCompleteSupplier is null");
        return merge(new ObservableMapNotification(this, onNextMapper, onErrorMapper, onCompleteSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:trampoline")
    public final Observable<T> takeLast(long time, @NonNull TimeUnit unit) {
        return takeLast(time, unit, Schedulers.trampoline(), false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, V> Observable<T> timeout(@NonNull ObservableSource<U> firstTimeoutIndicator, @NonNull Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator, @NonNull ObservableSource<? extends T> fallback) {
        Objects.requireNonNull(firstTimeoutIndicator, "firstTimeoutIndicator is null");
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(firstTimeoutIndicator, itemTimeoutIndicator, fallback);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:trampoline")
    public final Observable<T> takeLast(long time, @NonNull TimeUnit unit, boolean delayError) {
        return takeLast(time, unit, Schedulers.trampoline(), delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <K, V> Single<Map<K, Collection<V>>> toMultimap(@NonNull Function<? super T, ? extends K> keySelector, @NonNull Function<? super T, ? extends V> valueSelector, @NonNull Supplier<Map<K, Collection<V>>> mapSupplier) {
        return toMultimap(keySelector, valueSelector, mapSupplier, ArrayListSupplier.asFunction());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> concat(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2, @NonNull ObservableSource<? extends T> source3, @NonNull ObservableSource<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return concatArray(source1, source2, source3, source4);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<List<T>> buffer(long timespan, @NonNull TimeUnit unit) {
        return buffer(timespan, unit, Schedulers.computation(), Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> takeLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return takeLast(time, unit, scheduler, false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, R> Observable<R> combineLatest(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new ObservableSource[]{source1, source2, source3, source4}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> just(@NonNull T item1, @NonNull T item2, @NonNull T item3, @NonNull T item4, @NonNull T item5) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        Objects.requireNonNull(item3, "item3 is null");
        Objects.requireNonNull(item4, "item4 is null");
        Objects.requireNonNull(item5, "item5 is null");
        return fromArray(item1, item2, item3, item4, item5);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<List<T>> buffer(long timespan, @NonNull TimeUnit unit, int count) {
        return buffer(timespan, unit, Schedulers.computation(), count);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> takeLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError) {
        return takeLast(time, unit, scheduler, delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<Observable<T>> window(long timespan, @NonNull TimeUnit unit) {
        return window(timespan, unit, Schedulers.computation(), Long.MAX_VALUE, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <T1, T2, T3, T4, R> Observable<R> withLatestFrom(@NonNull ObservableSource<T1> source1, @NonNull ObservableSource<T2> source2, @NonNull ObservableSource<T3> source3, @NonNull ObservableSource<T4> source4, @NonNull Function5<? super T, ? super T1, ? super T2, ? super T3, ? super T4, R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return withLatestFrom((ObservableSource<?>[]) new ObservableSource[]{source1, source2, source3, source4}, Functions.toFunction(combiner));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> merge(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2, @NonNull ObservableSource<? extends T> source3, @NonNull ObservableSource<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return fromArray(source1, source2, source3, source4).flatMap(Functions.identity(), false, 4);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> mergeDelayError(@NonNull ObservableSource<? extends T> source1, @NonNull ObservableSource<? extends T> source2, @NonNull ObservableSource<? extends T> source3, @NonNull ObservableSource<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return fromArray(source1, source2, source3, source4).flatMap(Functions.identity(), true, 4);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, R> Observable<R> zip(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> zipper, boolean delayError, int bufferSize) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), delayError, bufferSize, source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<List<T>> buffer(long j2, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler, int i2) {
        return (Observable<List<T>>) buffer(j2, timeUnit, scheduler, i2, ArrayListSupplier.asSupplier(), false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper, @NonNull Function<Throwable, ? extends ObservableSource<? extends R>> onErrorMapper, @NonNull Supplier<? extends ObservableSource<? extends R>> onCompleteSupplier, int maxConcurrency) {
        Objects.requireNonNull(onNextMapper, "onNextMapper is null");
        Objects.requireNonNull(onErrorMapper, "onErrorMapper is null");
        Objects.requireNonNull(onCompleteSupplier, "onCompleteSupplier is null");
        return merge(new ObservableMapNotification(this, onNextMapper, onErrorMapper, onCompleteSupplier), maxConcurrency);
    }

    @Override // io.reactivex.rxjava3.core.ObservableSource
    @SchedulerSupport("none")
    public final void subscribe(@NonNull Observer<? super T> observer) {
        Objects.requireNonNull(observer, "observer is null");
        try {
            Observer<? super T> observerOnSubscribe = RxJavaPlugins.onSubscribe(this, observer);
            Objects.requireNonNull(observerOnSubscribe, "The RxJavaPlugins.onSubscribe hook returned a null Observer. Please change the handler provided to RxJavaPlugins.setOnObservableSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
            subscribeActual(observerOnSubscribe);
        } catch (NullPointerException e2) {
            throw e2;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
            NullPointerException nullPointerException = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            nullPointerException.initCause(th);
            throw nullPointerException;
        }
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<T> takeLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError, int bufferSize) {
        return takeLast(Long.MAX_VALUE, time, unit, scheduler, delayError, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<Observable<T>> window(long timespan, @NonNull TimeUnit unit, long count) {
        return window(timespan, unit, Schedulers.computation(), count, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final <U extends Collection<? super T>> Observable<U> buffer(long timespan, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, int count, @NonNull Supplier<U> bufferSupplier, boolean restartTimerOnMaxSize) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        Objects.requireNonNull(bufferSupplier, "bufferSupplier is null");
        ObjectHelper.verifyPositive(count, "count");
        return RxJavaPlugins.onAssembly(new ObservableBufferTimed(this, timespan, timespan, unit, scheduler, bufferSupplier, count, restartTimerOnMaxSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final <R> Observable<R> replay(@NonNull Function<? super Observable<T>, ? extends ObservableSource<R>> selector, int bufferSize, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean eagerTruncate) {
        Objects.requireNonNull(selector, "selector is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return ObservableReplay.multicastSelector(ObservableInternalHelper.replaySupplier(this, bufferSize, time, unit, scheduler, eagerTruncate), selector);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final Observable<Observable<T>> window(long timespan, @NonNull TimeUnit unit, long count, boolean restart) {
        return window(timespan, unit, Schedulers.computation(), count, restart);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<Observable<T>> window(long timespan, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return window(timespan, unit, scheduler, Long.MAX_VALUE, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<Observable<T>> window(long timespan, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, long count) {
        return window(timespan, unit, scheduler, count, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, R> Observable<R> zip(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends R>> mapper, int maxConcurrency) {
        return flatMap((Function) mapper, false, maxConcurrency, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<Observable<T>> window(long timespan, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, long count, boolean restart) {
        return window(timespan, unit, scheduler, count, restart, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, R> Observable<R> combineLatest(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull ObservableSource<? extends T5> source5, @NonNull Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new ObservableSource[]{source1, source2, source3, source4, source5}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> just(@NonNull T item1, @NonNull T item2, @NonNull T item3, @NonNull T item4, @NonNull T item5, @NonNull T item6) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        Objects.requireNonNull(item3, "item3 is null");
        Objects.requireNonNull(item4, "item4 is null");
        Objects.requireNonNull(item5, "item5 is null");
        Objects.requireNonNull(item6, "item6 is null");
        return fromArray(item1, item2, item3, item4, item5, item6);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner) {
        return flatMap(mapper, combiner, false, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<Observable<T>> window(long timespan, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, long count, boolean restart, int bufferSize) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(scheduler, "scheduler is null");
        Objects.requireNonNull(unit, "unit is null");
        ObjectHelper.verifyPositive(count, "count");
        return RxJavaPlugins.onAssembly(new ObservableWindowTimed(this, timespan, timespan, unit, scheduler, count, bufferSize, restart));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final Observable<List<T>> buffer(long j2, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return (Observable<List<T>>) buffer(j2, timeUnit, scheduler, Integer.MAX_VALUE, ArrayListSupplier.asSupplier(), false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors) {
        return flatMap(mapper, combiner, delayErrors, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> withLatestFrom(@NonNull ObservableSource<?>[] others, @NonNull Function<? super Object[], R> combiner) {
        Objects.requireNonNull(others, "others is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return RxJavaPlugins.onAssembly(new ObservableWithLatestFromMany(this, others, combiner));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <TOpening, TClosing> Observable<List<T>> buffer(@NonNull ObservableSource<? extends TOpening> observableSource, @NonNull Function<? super TOpening, ? extends ObservableSource<? extends TClosing>> function) {
        return (Observable<List<T>>) buffer(observableSource, function, ArrayListSupplier.asSupplier());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors, int maxConcurrency) {
        return flatMap(mapper, combiner, delayErrors, maxConcurrency, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final <R> Observable<R> replay(@NonNull Function<? super Observable<T>, ? extends ObservableSource<R>> selector, long time, @NonNull TimeUnit unit) {
        return replay(selector, time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <TOpening, TClosing, U extends Collection<? super T>> Observable<U> buffer(@NonNull ObservableSource<? extends TOpening> openingIndicator, @NonNull Function<? super TOpening, ? extends ObservableSource<? extends TClosing>> closingIndicator, @NonNull Supplier<U> bufferSupplier) {
        Objects.requireNonNull(openingIndicator, "openingIndicator is null");
        Objects.requireNonNull(closingIndicator, "closingIndicator is null");
        Objects.requireNonNull(bufferSupplier, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new ObservableBufferBoundary(this, openingIndicator, closingIndicator, bufferSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors, int maxConcurrency, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return flatMap(ObservableInternalHelper.flatMapWithCombiner(mapper, combiner), delayErrors, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final <R> Observable<R> replay(@NonNull Function<? super Observable<T>, ? extends ObservableSource<R>> selector, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(selector, "selector is null");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return ObservableReplay.multicastSelector(ObservableInternalHelper.replaySupplier(this, time, unit, scheduler, false), selector);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, R> Observable<R> zip(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <R> Observable<R> withLatestFrom(@NonNull Iterable<? extends ObservableSource<?>> others, @NonNull Function<? super Object[], R> combiner) {
        Objects.requireNonNull(others, "others is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return RxJavaPlugins.onAssembly(new ObservableWithLatestFromMany(this, others, combiner));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <B> Observable<Observable<T>> window(@NonNull ObservableSource<B> boundaryIndicator) {
        return window(boundaryIndicator, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, R> Observable<R> flatMap(@NonNull Function<? super T, ? extends ObservableSource<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner, int maxConcurrency) {
        return flatMap(mapper, combiner, false, maxConcurrency, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <B> Observable<Observable<T>> window(@NonNull ObservableSource<B> boundaryIndicator, int bufferSize) {
        Objects.requireNonNull(boundaryIndicator, "boundaryIndicator is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableWindowBoundary(this, boundaryIndicator, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, R> Observable<R> combineLatest(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull ObservableSource<? extends T5> source5, @NonNull ObservableSource<? extends T6> source6, @NonNull Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new ObservableSource[]{source1, source2, source3, source4, source5, source6}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> just(@NonNull T item1, @NonNull T item2, @NonNull T item3, @NonNull T item4, @NonNull T item5, @NonNull T item6, @NonNull T item7) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        Objects.requireNonNull(item3, "item3 is null");
        Objects.requireNonNull(item4, "item4 is null");
        Objects.requireNonNull(item5, "item5 is null");
        Objects.requireNonNull(item6, "item6 is null");
        Objects.requireNonNull(item7, "item7 is null");
        return fromArray(item1, item2, item3, item4, item5, item6, item7);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <B> Observable<List<T>> buffer(@NonNull ObservableSource<B> observableSource) {
        return (Observable<List<T>>) buffer(observableSource, ArrayListSupplier.asSupplier());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final <R> Observable<R> replay(@NonNull Function<? super Observable<T>, ? extends ObservableSource<R>> selector, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean eagerTruncate) {
        Objects.requireNonNull(selector, "selector is null");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return ObservableReplay.multicastSelector(ObservableInternalHelper.replaySupplier(this, time, unit, scheduler, eagerTruncate), selector);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <B> Observable<List<T>> buffer(@NonNull ObservableSource<B> observableSource, int i2) {
        ObjectHelper.verifyPositive(i2, "initialCapacity");
        return (Observable<List<T>>) buffer(observableSource, Functions.createArrayList(i2));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, V> Observable<Observable<T>> window(@NonNull ObservableSource<U> openingIndicator, @NonNull Function<? super U, ? extends ObservableSource<V>> closingIndicator) {
        return window(openingIndicator, closingIndicator, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, R> Observable<R> zip(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull ObservableSource<? extends T5> source5, @NonNull Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4, source5);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <B, U extends Collection<? super T>> Observable<U> buffer(@NonNull ObservableSource<B> boundaryIndicator, @NonNull Supplier<U> bufferSupplier) {
        Objects.requireNonNull(boundaryIndicator, "boundaryIndicator is null");
        Objects.requireNonNull(bufferSupplier, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new ObservableBufferExactBoundary(this, boundaryIndicator, bufferSupplier));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final <U, V> Observable<Observable<T>> window(@NonNull ObservableSource<U> openingIndicator, @NonNull Function<? super U, ? extends ObservableSource<V>> closingIndicator, int bufferSize) {
        Objects.requireNonNull(openingIndicator, "openingIndicator is null");
        Objects.requireNonNull(closingIndicator, "closingIndicator is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableWindowBoundarySelector(this, openingIndicator, closingIndicator, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final ConnectableObservable<T> replay(int bufferSize) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return ObservableReplay.create(this, bufferSize, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public final ConnectableObservable<T> replay(int bufferSize, boolean eagerTruncate) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return ObservableReplay.create(this, bufferSize, eagerTruncate);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> combineLatest(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull ObservableSource<? extends T5> source5, @NonNull ObservableSource<? extends T6> source6, @NonNull ObservableSource<? extends T7> source7, @NonNull Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(source7, "source7 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new ObservableSource[]{source1, source2, source3, source4, source5, source6, source7}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> just(@NonNull T item1, @NonNull T item2, @NonNull T item3, @NonNull T item4, @NonNull T item5, @NonNull T item6, @NonNull T item7, @NonNull T item8) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        Objects.requireNonNull(item3, "item3 is null");
        Objects.requireNonNull(item4, "item4 is null");
        Objects.requireNonNull(item5, "item5 is null");
        Objects.requireNonNull(item6, "item6 is null");
        Objects.requireNonNull(item7, "item7 is null");
        Objects.requireNonNull(item8, "item8 is null");
        return fromArray(item1, item2, item3, item4, item5, item6, item7, item8);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final ConnectableObservable<T> replay(int bufferSize, long time, @NonNull TimeUnit unit) {
        return replay(bufferSize, time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final ConnectableObservable<T> replay(int bufferSize, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return ObservableReplay.create(this, time, unit, scheduler, bufferSize, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, R> Observable<R> zip(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull ObservableSource<? extends T5> source5, @NonNull ObservableSource<? extends T6> source6, @NonNull Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4, source5, source6);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final ConnectableObservable<T> replay(int bufferSize, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean eagerTruncate) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return ObservableReplay.create(this, time, unit, scheduler, bufferSize, eagerTruncate);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> combineLatest(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull ObservableSource<? extends T5> source5, @NonNull ObservableSource<? extends T6> source6, @NonNull ObservableSource<? extends T7> source7, @NonNull ObservableSource<? extends T8> source8, @NonNull Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(source7, "source7 is null");
        Objects.requireNonNull(source8, "source8 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new ObservableSource[]{source1, source2, source3, source4, source5, source6, source7, source8}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> just(@NonNull T item1, @NonNull T item2, @NonNull T item3, @NonNull T item4, @NonNull T item5, @NonNull T item6, @NonNull T item7, @NonNull T item8, @NonNull T item9) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        Objects.requireNonNull(item3, "item3 is null");
        Objects.requireNonNull(item4, "item4 is null");
        Objects.requireNonNull(item5, "item5 is null");
        Objects.requireNonNull(item6, "item6 is null");
        Objects.requireNonNull(item7, "item7 is null");
        Objects.requireNonNull(item8, "item8 is null");
        Objects.requireNonNull(item9, "item9 is null");
        return fromArray(item1, item2, item3, item4, item5, item6, item7, item8, item9);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("io.reactivex:computation")
    public final ConnectableObservable<T> replay(long time, @NonNull TimeUnit unit) {
        return replay(time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> zip(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull ObservableSource<? extends T5> source5, @NonNull ObservableSource<? extends T6> source6, @NonNull ObservableSource<? extends T7> source7, @NonNull Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(source7, "source7 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4, source5, source6, source7);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final ConnectableObservable<T> replay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return ObservableReplay.create(this, time, unit, scheduler, false);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("custom")
    public final ConnectableObservable<T> replay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean eagerTruncate) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return ObservableReplay.create(this, time, unit, scheduler, eagerTruncate);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> combineLatest(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull ObservableSource<? extends T5> source5, @NonNull ObservableSource<? extends T6> source6, @NonNull ObservableSource<? extends T7> source7, @NonNull ObservableSource<? extends T8> source8, @NonNull ObservableSource<? extends T9> source9, @NonNull Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(source7, "source7 is null");
        Objects.requireNonNull(source8, "source8 is null");
        Objects.requireNonNull(source9, "source9 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new ObservableSource[]{source1, source2, source3, source4, source5, source6, source7, source8, source9}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T> Observable<T> just(@NonNull T item1, @NonNull T item2, @NonNull T item3, @NonNull T item4, @NonNull T item5, @NonNull T item6, @NonNull T item7, @NonNull T item8, @NonNull T item9, @NonNull T item10) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        Objects.requireNonNull(item3, "item3 is null");
        Objects.requireNonNull(item4, "item4 is null");
        Objects.requireNonNull(item5, "item5 is null");
        Objects.requireNonNull(item6, "item6 is null");
        Objects.requireNonNull(item7, "item7 is null");
        Objects.requireNonNull(item8, "item8 is null");
        Objects.requireNonNull(item9, "item9 is null");
        Objects.requireNonNull(item10, "item10 is null");
        return fromArray(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> zip(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull ObservableSource<? extends T5> source5, @NonNull ObservableSource<? extends T6> source6, @NonNull ObservableSource<? extends T7> source7, @NonNull ObservableSource<? extends T8> source8, @NonNull Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(source7, "source7 is null");
        Objects.requireNonNull(source8, "source8 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4, source5, source6, source7, source8);
    }

    @CheckReturnValue
    @NonNull
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> zip(@NonNull ObservableSource<? extends T1> source1, @NonNull ObservableSource<? extends T2> source2, @NonNull ObservableSource<? extends T3> source3, @NonNull ObservableSource<? extends T4> source4, @NonNull ObservableSource<? extends T5> source5, @NonNull ObservableSource<? extends T6> source6, @NonNull ObservableSource<? extends T7> source7, @NonNull ObservableSource<? extends T8> source8, @NonNull ObservableSource<? extends T9> source9, @NonNull Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper) {
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
        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4, source5, source6, source7, source8, source9);
    }
}
