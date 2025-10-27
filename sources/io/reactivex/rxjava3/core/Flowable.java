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
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.flowables.GroupedFlowable;
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
import io.reactivex.rxjava3.functions.LongConsumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.jdk8.FlowableCollectWithCollectorSingle;
import io.reactivex.rxjava3.internal.jdk8.FlowableFirstStageSubscriber;
import io.reactivex.rxjava3.internal.jdk8.FlowableFlatMapStream;
import io.reactivex.rxjava3.internal.jdk8.FlowableFromCompletionStage;
import io.reactivex.rxjava3.internal.jdk8.FlowableFromStream;
import io.reactivex.rxjava3.internal.jdk8.FlowableLastStageSubscriber;
import io.reactivex.rxjava3.internal.jdk8.FlowableMapOptional;
import io.reactivex.rxjava3.internal.jdk8.FlowableSingleStageSubscriber;
import io.reactivex.rxjava3.internal.operators.flowable.BlockingFlowableIterable;
import io.reactivex.rxjava3.internal.operators.flowable.BlockingFlowableLatest;
import io.reactivex.rxjava3.internal.operators.flowable.BlockingFlowableMostRecent;
import io.reactivex.rxjava3.internal.operators.flowable.BlockingFlowableNext;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableAllSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableAmb;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableAnySingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableBlockingSubscribe;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableBuffer;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableBufferBoundary;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableBufferExactBoundary;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableBufferTimed;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableCache;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableCollectSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableCombineLatest;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatArray;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMapEager;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMapEagerPublisher;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMapScheduler;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatWithCompletable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatWithMaybe;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatWithSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableCountSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDebounce;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDebounceTimed;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDefer;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDelay;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDelaySubscriptionOther;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDematerialize;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDetach;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDistinct;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDistinctUntilChanged;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDoAfterNext;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDoFinally;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDoOnEach;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDoOnLifecycle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableElementAtMaybe;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableElementAtSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableEmpty;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableError;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFilter;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMap;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapCompletableCompletable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapMaybe;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlattenIterable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromAction;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromArray;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromCallable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromCompletable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromFuture;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromIterable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromObservable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromPublisher;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromRunnable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromSupplier;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableGenerate;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupBy;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableHide;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableIgnoreElements;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableIgnoreElementsCompletable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableInternalHelper;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableInterval;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableIntervalRange;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableJoin;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableJust;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableLastMaybe;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableLastSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableLift;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableMap;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableMapNotification;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableMaterialize;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableMergeWithCompletable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableMergeWithMaybe;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableMergeWithSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableNever;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableOnBackpressureBuffer;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableOnBackpressureBufferStrategy;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableOnBackpressureDrop;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableOnBackpressureError;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableOnBackpressureLatest;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableOnBackpressureReduce;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableOnBackpressureReduceWith;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableOnErrorComplete;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableOnErrorNext;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableOnErrorReturn;
import io.reactivex.rxjava3.internal.operators.flowable.FlowablePublish;
import io.reactivex.rxjava3.internal.operators.flowable.FlowablePublishMulticast;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableRange;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableRangeLong;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableReduceMaybe;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableReduceSeedSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableReduceWithSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableRepeat;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableRepeatUntil;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableRepeatWhen;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableRetryBiPredicate;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableRetryPredicate;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableRetryWhen;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSamplePublisher;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSampleTimed;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableScalarXMap;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableScan;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableScanSeed;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSequenceEqualSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSerialized;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSingleMaybe;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSingleSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSkip;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSkipLast;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSkipLastTimed;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSkipUntil;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSkipWhile;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSubscribeOn;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSwitchIfEmpty;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSwitchMap;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTake;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTakeLast;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTakeLastOne;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTakeLastTimed;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTakeUntil;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTakeUntilPredicate;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTakeWhile;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableThrottleFirstTimed;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableThrottleLatest;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTimeInterval;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTimeout;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTimeoutTimed;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTimer;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableToListSingle;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableUnsubscribeOn;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableUsing;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableWindow;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowBoundary;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowBoundarySelector;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableWithLatestFrom;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableWithLatestFromMany;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableZip;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableZipIterable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToFlowable;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapCompletable;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapMaybe;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapSingle;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapCompletable;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapMaybe;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapSingle;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromPublisher;
import io.reactivex.rxjava3.internal.operators.single.SingleToFlowable;
import io.reactivex.rxjava3.internal.schedulers.ImmediateThinScheduler;
import io.reactivex.rxjava3.internal.subscribers.BlockingFirstSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BlockingLastSubscriber;
import io.reactivex.rxjava3.internal.subscribers.DisposableAutoReleaseSubscriber;
import io.reactivex.rxjava3.internal.subscribers.ForEachWhileSubscriber;
import io.reactivex.rxjava3.internal.subscribers.FutureSubscriber;
import io.reactivex.rxjava3.internal.subscribers.LambdaSubscriber;
import io.reactivex.rxjava3.internal.subscribers.StrictSubscriber;
import io.reactivex.rxjava3.internal.util.ArrayListSupplier;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.HashMapSupplier;
import io.reactivex.rxjava3.operators.ScalarSupplier;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.Timed;
import io.reactivex.rxjava3.subscribers.SafeSubscriber;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
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
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public abstract class Flowable<T> implements Publisher<T> {
    static final int BUFFER_SIZE = Math.max(1, Integer.getInteger("rx3.buffer-size", 128).intValue());

    /* renamed from: io.reactivex.rxjava3.core.Flowable$1, reason: invalid class name */
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
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T> Flowable<T> amb(@NonNull Iterable<? extends Publisher<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableAmb(null, sources));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> ambArray(@NonNull Publisher<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        int length = sources.length;
        return length == 0 ? empty() : length == 1 ? fromPublisher(sources[0]) : RxJavaPlugins.onAssembly(new FlowableAmb(sources, null));
    }

    @CheckReturnValue
    public static int bufferSize() {
        return BUFFER_SIZE;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, R> Flowable<R> combineLatest(@NonNull Iterable<? extends Publisher<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> combiner) {
        return combineLatest(sources, combiner, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, R> Flowable<R> combineLatestArray(@NonNull Publisher<? extends T>[] sources, @NonNull Function<? super Object[], ? extends R> combiner) {
        return combineLatestArray(sources, combiner, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, R> Flowable<R> combineLatestArrayDelayError(@NonNull Publisher<? extends T>[] sources, @NonNull Function<? super Object[], ? extends R> combiner) {
        return combineLatestArrayDelayError(sources, combiner, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, R> Flowable<R> combineLatestDelayError(@NonNull Iterable<? extends Publisher<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> combiner) {
        return combineLatestDelayError(sources, combiner, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Iterable<? extends Publisher<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return fromIterable(sources).concatMapDelayError(Functions.identity(), false, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArray(@NonNull Publisher<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? empty() : sources.length == 1 ? fromPublisher(sources[0]) : RxJavaPlugins.onAssembly(new FlowableConcatArray(sources, false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayDelayError(@NonNull Publisher<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        return sources.length == 0 ? empty() : sources.length == 1 ? fromPublisher(sources[0]) : RxJavaPlugins.onAssembly(new FlowableConcatArray(sources, true));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEager(@NonNull Publisher<? extends T>... sources) {
        return concatArrayEager(bufferSize(), bufferSize(), sources);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEagerDelayError(@NonNull Publisher<? extends T>... sources) {
        return concatArrayEagerDelayError(bufferSize(), bufferSize(), sources);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Iterable<? extends Publisher<? extends T>> sources) {
        Objects.requireNonNull(sources, "sources is null");
        return fromIterable(sources).concatMapDelayError(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Iterable<? extends Publisher<? extends T>> sources) {
        return concatEager(sources, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Iterable<? extends Publisher<? extends T>> sources) {
        return concatEagerDelayError(sources, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> create(@NonNull FlowableOnSubscribe<T> source, @NonNull BackpressureStrategy mode) {
        Objects.requireNonNull(source, "source is null");
        Objects.requireNonNull(mode, "mode is null");
        return RxJavaPlugins.onAssembly(new FlowableCreate(source, mode));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T> Flowable<T> defer(@NonNull Supplier<? extends Publisher<? extends T>> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new FlowableDefer(supplier));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    private Flowable<T> doOnEach(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError, Action onComplete, Action onAfterTerminate) {
        Objects.requireNonNull(onNext, "onNext is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        Objects.requireNonNull(onAfterTerminate, "onAfterTerminate is null");
        return RxJavaPlugins.onAssembly(new FlowableDoOnEach(this, onNext, onError, onComplete, onAfterTerminate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T> Flowable<T> empty() {
        return RxJavaPlugins.onAssembly(FlowableEmpty.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T> Flowable<T> error(@NonNull Supplier<? extends Throwable> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new FlowableError(supplier));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromAction(@NonNull Action action) {
        Objects.requireNonNull(action, "action is null");
        return RxJavaPlugins.onAssembly(new FlowableFromAction(action));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromArray(@NonNull T... items) {
        Objects.requireNonNull(items, "items is null");
        return items.length == 0 ? empty() : items.length == 1 ? just(items[0]) : RxJavaPlugins.onAssembly(new FlowableFromArray(items));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromCallable(@NonNull Callable<? extends T> callable) {
        Objects.requireNonNull(callable, "callable is null");
        return RxJavaPlugins.onAssembly(new FlowableFromCallable(callable));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromCompletable(@NonNull CompletableSource completableSource) {
        Objects.requireNonNull(completableSource, "completableSource is null");
        return RxJavaPlugins.onAssembly(new FlowableFromCompletable(completableSource));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromCompletionStage(@NonNull CompletionStage<T> stage) {
        Objects.requireNonNull(stage, "stage is null");
        return RxJavaPlugins.onAssembly(new FlowableFromCompletionStage(stage));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromFuture(@NonNull Future<? extends T> future) {
        Objects.requireNonNull(future, "future is null");
        return RxJavaPlugins.onAssembly(new FlowableFromFuture(future, 0L, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromIterable(@NonNull Iterable<? extends T> source) {
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new FlowableFromIterable(source));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromMaybe(@NonNull MaybeSource<T> maybe) {
        Objects.requireNonNull(maybe, "maybe is null");
        return RxJavaPlugins.onAssembly(new MaybeToFlowable(maybe));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromObservable(@NonNull ObservableSource<T> source, @NonNull BackpressureStrategy strategy) {
        Objects.requireNonNull(source, "source is null");
        Objects.requireNonNull(strategy, "strategy is null");
        FlowableFromObservable flowableFromObservable = new FlowableFromObservable(source);
        int i2 = AnonymousClass1.$SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy[strategy.ordinal()];
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? flowableFromObservable.onBackpressureBuffer() : RxJavaPlugins.onAssembly(new FlowableOnBackpressureError(flowableFromObservable)) : flowableFromObservable : flowableFromObservable.onBackpressureLatest() : flowableFromObservable.onBackpressureDrop();
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromOptional(@NonNull Optional<T> optional) {
        Objects.requireNonNull(optional, "optional is null");
        return (Flowable) optional.map(new java.util.function.Function() { // from class: io.reactivex.rxjava3.core.b
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Flowable.just(obj);
            }
        }).orElseGet(new java.util.function.Supplier() { // from class: io.reactivex.rxjava3.core.c
            @Override // java.util.function.Supplier
            public final Object get() {
                return Flowable.empty();
            }
        });
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromPublisher(@NonNull Publisher<? extends T> publisher) {
        if (publisher instanceof Flowable) {
            return RxJavaPlugins.onAssembly((Flowable) publisher);
        }
        Objects.requireNonNull(publisher, "publisher is null");
        return RxJavaPlugins.onAssembly(new FlowableFromPublisher(publisher));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromRunnable(@NonNull Runnable run) {
        Objects.requireNonNull(run, "run is null");
        return RxJavaPlugins.onAssembly(new FlowableFromRunnable(run));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromSingle(@NonNull SingleSource<T> source) {
        Objects.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new SingleToFlowable(source));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromStream(@NonNull Stream<T> stream) {
        Objects.requireNonNull(stream, "stream is null");
        return RxJavaPlugins.onAssembly(new FlowableFromStream(stream));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromSupplier(@NonNull Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return RxJavaPlugins.onAssembly(new FlowableFromSupplier(supplier));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> generate(@NonNull Consumer<Emitter<T>> generator) {
        Objects.requireNonNull(generator, "generator is null");
        return generate(Functions.nullSupplier(), FlowableInternalHelper.simpleGenerator(generator), Functions.emptyConsumer());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public static Flowable<Long> interval(long initialDelay, long period, @NonNull TimeUnit unit) {
        return interval(initialDelay, period, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public static Flowable<Long> intervalRange(long start, long count, long initialDelay, long period, @NonNull TimeUnit unit) {
        return intervalRange(start, count, initialDelay, period, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> just(T item) {
        Objects.requireNonNull(item, "item is null");
        return RxJavaPlugins.onAssembly(new FlowableJust(item));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Iterable<? extends Publisher<? extends T>> sources, int maxConcurrency, int bufferSize) {
        return fromIterable(sources).flatMap(Functions.identity(), false, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArray(int maxConcurrency, int bufferSize, @NonNull Publisher<? extends T>... sources) {
        return fromArray(sources).flatMap(Functions.identity(), false, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArrayDelayError(int maxConcurrency, int bufferSize, @NonNull Publisher<? extends T>... sources) {
        return fromArray(sources).flatMap(Functions.identity(), true, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Iterable<? extends Publisher<? extends T>> sources) {
        return fromIterable(sources).flatMap(Functions.identity(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T> Flowable<T> never() {
        return RxJavaPlugins.onAssembly(FlowableNever.INSTANCE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Flowable<Integer> range(int start, int count) {
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
            return RxJavaPlugins.onAssembly(new FlowableRange(start, count));
        }
        throw new IllegalArgumentException("Integer overflow");
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static Flowable<Long> rangeLong(long start, long count) {
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
            return RxJavaPlugins.onAssembly(new FlowableRangeLong(start, count));
        }
        throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2) {
        return sequenceEqual(source1, source2, ObjectHelper.equalsPredicate(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNext(@NonNull Publisher<? extends Publisher<? extends T>> sources, int bufferSize) {
        return fromPublisher(sources).switchMap(Functions.identity(), bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNextDelayError(@NonNull Publisher<? extends Publisher<? extends T>> sources) {
        return switchOnNextDelayError(sources, bufferSize());
    }

    private Flowable<T> timeout0(long timeout, TimeUnit unit, Publisher<? extends T> fallback, Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableTimeoutTimed(this, timeout, unit, scheduler, fallback));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public static Flowable<Long> timer(long delay, @NonNull TimeUnit unit) {
        return timer(delay, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.NONE)
    @SchedulerSupport("none")
    public static <T> Flowable<T> unsafeCreate(@NonNull Publisher<T> onSubscribe) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        if (onSubscribe instanceof Flowable) {
            throw new IllegalArgumentException("unsafeCreate(Flowable) should be upgraded");
        }
        return RxJavaPlugins.onAssembly(new FlowableFromPublisher(onSubscribe));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T, D> Flowable<T> using(@NonNull Supplier<? extends D> resourceSupplier, @NonNull Function<? super D, ? extends Publisher<? extends T>> sourceSupplier, @NonNull Consumer<? super D> resourceCleanup) {
        return using(resourceSupplier, sourceSupplier, resourceCleanup, true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, R> Flowable<R> zip(@NonNull Iterable<? extends Publisher<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> zipper) {
        Objects.requireNonNull(zipper, "zipper is null");
        Objects.requireNonNull(sources, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableZip(null, sources, zipper, bufferSize(), false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T, R> Flowable<R> zipArray(@NonNull Function<? super Object[], ? extends R> zipper, boolean delayError, int bufferSize, @NonNull Publisher<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        if (sources.length == 0) {
            return empty();
        }
        Objects.requireNonNull(zipper, "zipper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableZip(sources, null, zipper, bufferSize, delayError));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<Boolean> all(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableAllSingle(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> ambWith(@NonNull Publisher<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return ambArray(this, other);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<Boolean> any(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableAnySingle(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final T blockingFirst() throws InterruptedException {
        BlockingFirstSubscriber blockingFirstSubscriber = new BlockingFirstSubscriber();
        subscribe((FlowableSubscriber) blockingFirstSubscriber);
        T tBlockingGet = blockingFirstSubscriber.blockingGet();
        if (tBlockingGet != null) {
            return tBlockingGet;
        }
        throw new NoSuchElementException();
    }

    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final void blockingForEach(@NonNull Consumer<? super T> onNext) {
        blockingForEach(onNext, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Iterable<T> blockingIterable() {
        return blockingIterable(bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final T blockingLast() throws InterruptedException {
        BlockingLastSubscriber blockingLastSubscriber = new BlockingLastSubscriber();
        subscribe((FlowableSubscriber) blockingLastSubscriber);
        T tBlockingGet = blockingLastSubscriber.blockingGet();
        if (tBlockingGet != null) {
            return tBlockingGet;
        }
        throw new NoSuchElementException();
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Iterable<T> blockingLatest() {
        return new BlockingFlowableLatest(this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Iterable<T> blockingMostRecent(@NonNull T initialItem) {
        Objects.requireNonNull(initialItem, "initialItem is null");
        return new BlockingFlowableMostRecent(this, initialItem);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Iterable<T> blockingNext() {
        return new BlockingFlowableNext(this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final T blockingSingle() {
        return singleOrError().blockingGet();
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Stream<T> blockingStream() {
        return blockingStream(bufferSize());
    }

    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final void blockingSubscribe() {
        FlowableBlockingSubscribe.subscribe(this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<List<T>> buffer(int count) {
        return buffer(count, count);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> cache() {
        return cacheWithInitialCapacity(16);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> cacheWithInitialCapacity(int initialCapacity) {
        ObjectHelper.verifyPositive(initialCapacity, "initialCapacity");
        return RxJavaPlugins.onAssembly(new FlowableCache(this, initialCapacity));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <U> Flowable<U> cast(@NonNull Class<U> cls) {
        Objects.requireNonNull(cls, "clazz is null");
        return (Flowable<U>) map(Functions.castFunction(cls));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Single<U> collect(@NonNull Supplier<? extends U> initialItemSupplier, @NonNull BiConsumer<? super U, ? super T> collector) {
        Objects.requireNonNull(initialItemSupplier, "initialItemSupplier is null");
        Objects.requireNonNull(collector, "collector is null");
        return RxJavaPlugins.onAssembly(new FlowableCollectSingle(this, initialItemSupplier, collector));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U> Single<U> collectInto(U initialItem, @NonNull BiConsumer<? super U, ? super T> collector) {
        Objects.requireNonNull(initialItem, "initialItem is null");
        return collect(Functions.justSupplier(initialItem), collector);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> Flowable<R> compose(@NonNull FlowableTransformer<? super T, ? extends R> composer) {
        Objects.requireNonNull(composer, "composer is null");
        return fromPublisher(composer.apply(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return concatMap(mapper, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Completable concatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        return concatMapCompletable(mapper, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Completable concatMapCompletableDelayError(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        return concatMapCompletableDelayError(mapper, true, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapDelayError(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return concatMapDelayError(mapper, true, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapEager(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return concatMapEager(mapper, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapEagerDelayError(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, boolean tillTheEnd) {
        return concatMapEagerDelayError(mapper, tillTheEnd, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Flowable<U> concatMapIterable(@NonNull Function<? super T, ? extends Iterable<? extends U>> mapper) {
        return concatMapIterable(mapper, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        return concatMapMaybe(mapper, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapMaybeDelayError(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        return concatMapMaybeDelayError(mapper, true, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        return concatMapSingle(mapper, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapSingleDelayError(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        return concatMapSingleDelayError(mapper, true, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapStream(@NonNull Function<? super T, ? extends Stream<? extends R>> mapper) {
        return flatMapStream(mapper, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> concatWith(@NonNull Publisher<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return concat(this, other);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<Boolean> contains(@NonNull Object item) {
        Objects.requireNonNull(item, "item is null");
        return any(Functions.equalsWith(item));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<Long> count() {
        return RxJavaPlugins.onAssembly(new FlowableCountSingle(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <U> Flowable<T> debounce(@NonNull Function<? super T, ? extends Publisher<U>> debounceIndicator) {
        Objects.requireNonNull(debounceIndicator, "debounceIndicator is null");
        return RxJavaPlugins.onAssembly(new FlowableDebounce(this, debounceIndicator));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> defaultIfEmpty(@NonNull T defaultItem) {
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        return switchIfEmpty(just(defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Flowable<T> delay(@NonNull Function<? super T, ? extends Publisher<U>> function) {
        Objects.requireNonNull(function, "itemDelayIndicator is null");
        return (Flowable<T>) flatMap(FlowableInternalHelper.itemDelay(function));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Flowable<T> delaySubscription(@NonNull Publisher<U> subscriptionIndicator) {
        Objects.requireNonNull(subscriptionIndicator, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new FlowableDelaySubscriptionOther(this, subscriptionIndicator));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> Flowable<R> dematerialize(@NonNull Function<? super T, Notification<R>> selector) {
        Objects.requireNonNull(selector, "selector is null");
        return RxJavaPlugins.onAssembly(new FlowableDematerialize(this, selector));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> distinct() {
        return distinct(Functions.identity(), Functions.createHashSet());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> distinctUntilChanged() {
        return distinctUntilChanged(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doAfterNext(@NonNull Consumer<? super T> onAfterNext) {
        Objects.requireNonNull(onAfterNext, "onAfterNext is null");
        return RxJavaPlugins.onAssembly(new FlowableDoAfterNext(this, onAfterNext));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doAfterTerminate(@NonNull Action onAfterTerminate) {
        return doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, onAfterTerminate);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doFinally(@NonNull Action onFinally) {
        Objects.requireNonNull(onFinally, "onFinally is null");
        return RxJavaPlugins.onAssembly(new FlowableDoFinally(this, onFinally));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doOnCancel(@NonNull Action onCancel) {
        return doOnLifecycle(Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, onCancel);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doOnComplete(@NonNull Action onComplete) {
        return doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), onComplete, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doOnError(@NonNull Consumer<? super Throwable> onError) {
        Consumer<? super T> consumerEmptyConsumer = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnEach(consumerEmptyConsumer, onError, action, action);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doOnLifecycle(@NonNull Consumer<? super Subscription> onSubscribe, @NonNull LongConsumer onRequest, @NonNull Action onCancel) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        Objects.requireNonNull(onRequest, "onRequest is null");
        Objects.requireNonNull(onCancel, "onCancel is null");
        return RxJavaPlugins.onAssembly(new FlowableDoOnLifecycle(this, onSubscribe, onRequest, onCancel));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doOnNext(@NonNull Consumer<? super T> onNext) {
        Consumer<? super Throwable> consumerEmptyConsumer = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnEach(onNext, consumerEmptyConsumer, action, action);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doOnRequest(@NonNull LongConsumer onRequest) {
        return doOnLifecycle(Functions.emptyConsumer(), onRequest, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doOnSubscribe(@NonNull Consumer<? super Subscription> onSubscribe) {
        return doOnLifecycle(onSubscribe, Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doOnTerminate(@NonNull Action onTerminate) {
        return doOnEach(Functions.emptyConsumer(), Functions.actionConsumer(onTerminate), onTerminate, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Maybe<T> elementAt(long index) {
        if (index >= 0) {
            return RxJavaPlugins.onAssembly(new FlowableElementAtMaybe(this, index));
        }
        throw new IndexOutOfBoundsException("index >= 0 required but it was " + index);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Single<T> elementAtOrError(long index) {
        if (index >= 0) {
            return RxJavaPlugins.onAssembly(new FlowableElementAtSingle(this, index, null));
        }
        throw new IndexOutOfBoundsException("index >= 0 required but it was " + index);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> filter(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableFilter(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Single<T> first(@NonNull T defaultItem) {
        return elementAt(0L, defaultItem);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Maybe<T> firstElement() {
        return elementAt(0L);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Single<T> firstOrError() {
        return elementAtOrError(0L);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final CompletionStage<T> firstOrErrorStage() {
        return (CompletionStage) subscribeWith(new FlowableFirstStageSubscriber(false, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final CompletionStage<T> firstStage(@Nullable T defaultItem) {
        return (CompletionStage) subscribeWith(new FlowableFirstStageSubscriber(true, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return flatMap((Function) mapper, false, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Completable flatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        return flatMapCompletable(mapper, false, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Flowable<U> flatMapIterable(@NonNull Function<? super T, ? extends Iterable<? extends U>> mapper) {
        return flatMapIterable(mapper, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        return flatMapMaybe(mapper, false, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        return flatMapSingle(mapper, false, Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMapStream(@NonNull Function<? super T, ? extends Stream<? extends R>> mapper) {
        return flatMapStream(mapper, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.NONE)
    @SchedulerSupport("none")
    public final Disposable forEach(@NonNull Consumer<? super T> onNext) {
        return subscribe(onNext);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.NONE)
    @SchedulerSupport("none")
    public final Disposable forEachWhile(@NonNull Predicate<? super T> onNext) {
        return forEachWhile(onNext, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <K> Flowable<GroupedFlowable<K, T>> groupBy(@NonNull Function<? super T, ? extends K> function) {
        return (Flowable<GroupedFlowable<K, T>>) groupBy(function, Functions.identity(), false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <TRight, TLeftEnd, TRightEnd, R> Flowable<R> groupJoin(@NonNull Publisher<? extends TRight> other, @NonNull Function<? super T, ? extends Publisher<TLeftEnd>> leftEnd, @NonNull Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd, @NonNull BiFunction<? super T, ? super Flowable<TRight>, ? extends R> resultSelector) {
        Objects.requireNonNull(other, "other is null");
        Objects.requireNonNull(leftEnd, "leftEnd is null");
        Objects.requireNonNull(rightEnd, "rightEnd is null");
        Objects.requireNonNull(resultSelector, "resultSelector is null");
        return RxJavaPlugins.onAssembly(new FlowableGroupJoin(this, other, leftEnd, rightEnd, resultSelector));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> hide() {
        return RxJavaPlugins.onAssembly(new FlowableHide(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Completable ignoreElements() {
        return RxJavaPlugins.onAssembly(new FlowableIgnoreElementsCompletable(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<Boolean> isEmpty() {
        return all(Functions.alwaysFalse());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <TRight, TLeftEnd, TRightEnd, R> Flowable<R> join(@NonNull Publisher<? extends TRight> other, @NonNull Function<? super T, ? extends Publisher<TLeftEnd>> leftEnd, @NonNull Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd, @NonNull BiFunction<? super T, ? super TRight, ? extends R> resultSelector) {
        Objects.requireNonNull(other, "other is null");
        Objects.requireNonNull(leftEnd, "leftEnd is null");
        Objects.requireNonNull(rightEnd, "rightEnd is null");
        Objects.requireNonNull(resultSelector, "resultSelector is null");
        return RxJavaPlugins.onAssembly(new FlowableJoin(this, other, leftEnd, rightEnd, resultSelector));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<T> last(@NonNull T defaultItem) {
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        return RxJavaPlugins.onAssembly(new FlowableLastSingle(this, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Maybe<T> lastElement() {
        return RxJavaPlugins.onAssembly(new FlowableLastMaybe(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<T> lastOrError() {
        return RxJavaPlugins.onAssembly(new FlowableLastSingle(this, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final CompletionStage<T> lastOrErrorStage() {
        return (CompletionStage) subscribeWith(new FlowableLastStageSubscriber(false, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final CompletionStage<T> lastStage(@Nullable T defaultItem) {
        return (CompletionStage) subscribeWith(new FlowableLastStageSubscriber(true, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> lift(@NonNull FlowableOperator<? extends R, ? super T> lifter) {
        Objects.requireNonNull(lifter, "lifter is null");
        return RxJavaPlugins.onAssembly(new FlowableLift(this, lifter));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> Flowable<R> map(@NonNull Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new FlowableMap(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> mapOptional(@NonNull Function<? super T, Optional<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new FlowableMapOptional(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<Notification<T>> materialize() {
        return RxJavaPlugins.onAssembly(new FlowableMaterialize(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> mergeWith(@NonNull Publisher<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return merge(this, other);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> observeOn(@NonNull Scheduler scheduler) {
        return observeOn(scheduler, false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <U> Flowable<U> ofType(@NonNull Class<U> clazz) {
        Objects.requireNonNull(clazz, "clazz is null");
        return filter(Functions.isInstanceOf(clazz)).cast(clazz);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureBuffer() {
        return onBackpressureBuffer(bufferSize(), false, true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureDrop() {
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureDrop(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureLatest() {
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureLatest(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureReduce(@NonNull BiFunction<T, T, T> reducer) {
        Objects.requireNonNull(reducer, "reducer is null");
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureReduce(this, reducer));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> onErrorComplete() {
        return onErrorComplete(Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> onErrorResumeNext(@NonNull Function<? super Throwable, ? extends Publisher<? extends T>> fallbackSupplier) {
        Objects.requireNonNull(fallbackSupplier, "fallbackSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableOnErrorNext(this, fallbackSupplier));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> onErrorResumeWith(@NonNull Publisher<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return onErrorResumeNext(Functions.justFunction(fallback));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> onErrorReturn(@NonNull Function<? super Throwable, ? extends T> itemSupplier) {
        Objects.requireNonNull(itemSupplier, "itemSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableOnErrorReturn(this, itemSupplier));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> onErrorReturnItem(@NonNull T item) {
        Objects.requireNonNull(item, "item is null");
        return onErrorReturn(Functions.justFunction(item));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> onTerminateDetach() {
        return RxJavaPlugins.onAssembly(new FlowableDetach(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> parallel() {
        return ParallelFlowable.from(this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final ConnectableFlowable<T> publish() {
        return publish(bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> rebatchRequests(int n2) {
        return observeOn(ImmediateThinScheduler.INSTANCE, true, n2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Maybe<T> reduce(@NonNull BiFunction<T, T, T> reducer) {
        Objects.requireNonNull(reducer, "reducer is null");
        return RxJavaPlugins.onAssembly(new FlowableReduceMaybe(this, reducer));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> Single<R> reduceWith(@NonNull Supplier<R> seedSupplier, @NonNull BiFunction<R, ? super T, R> reducer) {
        Objects.requireNonNull(seedSupplier, "seedSupplier is null");
        Objects.requireNonNull(reducer, "reducer is null");
        return RxJavaPlugins.onAssembly(new FlowableReduceWithSingle(this, seedSupplier, reducer));
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
        Objects.requireNonNull(stop, "stop is null");
        return RxJavaPlugins.onAssembly(new FlowableRepeatUntil(this, stop));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeatWhen(@NonNull Function<? super Flowable<Object>, ? extends Publisher<?>> handler) {
        Objects.requireNonNull(handler, "handler is null");
        return RxJavaPlugins.onAssembly(new FlowableRepeatWhen(this, handler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final ConnectableFlowable<T> replay() {
        return FlowableReplay.createFrom(this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> retry() {
        return retry(Long.MAX_VALUE, Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> retryUntil(@NonNull BooleanSupplier stop) {
        Objects.requireNonNull(stop, "stop is null");
        return retry(Long.MAX_VALUE, Functions.predicateReverseFor(stop));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> retryWhen(@NonNull Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler) {
        Objects.requireNonNull(handler, "handler is null");
        return RxJavaPlugins.onAssembly(new FlowableRetryWhen(this, handler));
    }

    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final void safeSubscribe(@NonNull Subscriber<? super T> subscriber) {
        Objects.requireNonNull(subscriber, "subscriber is null");
        if (subscriber instanceof SafeSubscriber) {
            subscribe((FlowableSubscriber) subscriber);
        } else {
            subscribe((FlowableSubscriber) new SafeSubscriber(subscriber));
        }
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> sample(long period, @NonNull TimeUnit unit) {
        return sample(period, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> scan(@NonNull BiFunction<T, T, T> accumulator) {
        Objects.requireNonNull(accumulator, "accumulator is null");
        return RxJavaPlugins.onAssembly(new FlowableScan(this, accumulator));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> scanWith(@NonNull Supplier<R> seedSupplier, @NonNull BiFunction<R, ? super T, R> accumulator) {
        Objects.requireNonNull(seedSupplier, "seedSupplier is null");
        Objects.requireNonNull(accumulator, "accumulator is null");
        return RxJavaPlugins.onAssembly(new FlowableScanSeed(this, seedSupplier, accumulator));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> serialize() {
        return RxJavaPlugins.onAssembly(new FlowableSerialized(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> share() {
        return publish().refCount();
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<T> single(@NonNull T defaultItem) {
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        return RxJavaPlugins.onAssembly(new FlowableSingleSingle(this, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Maybe<T> singleElement() {
        return RxJavaPlugins.onAssembly(new FlowableSingleMaybe(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<T> singleOrError() {
        return RxJavaPlugins.onAssembly(new FlowableSingleSingle(this, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final CompletionStage<T> singleOrErrorStage() {
        return (CompletionStage) subscribeWith(new FlowableSingleStageSubscriber(false, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final CompletionStage<T> singleStage(@Nullable T defaultItem) {
        return (CompletionStage) subscribeWith(new FlowableSingleStageSubscriber(true, defaultItem));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> skip(long count) {
        if (count >= 0) {
            return count == 0 ? RxJavaPlugins.onAssembly(this) : RxJavaPlugins.onAssembly(new FlowableSkip(this, count));
        }
        throw new IllegalArgumentException("count >= 0 expected but it was " + count);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> skipLast(int count) {
        if (count >= 0) {
            return count == 0 ? RxJavaPlugins.onAssembly(this) : RxJavaPlugins.onAssembly(new FlowableSkipLast(this, count));
        }
        throw new IllegalArgumentException("count >= 0 required but it was " + count);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Flowable<T> skipUntil(@NonNull Publisher<U> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableSkipUntil(this, other));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> skipWhile(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableSkipWhile(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> sorted() {
        return toList().toFlowable().map(Functions.listSorter(Functions.naturalComparator())).flatMapIterable(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return concat(Completable.wrap(other).toFlowable(), this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public final Flowable<T> startWithArray(@NonNull T... items) {
        Flowable flowableFromArray = fromArray(items);
        return flowableFromArray == empty() ? RxJavaPlugins.onAssembly(this) : concatArray(flowableFromArray, this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWithItem(@NonNull T item) {
        Objects.requireNonNull(item, "item is null");
        return concatArray(just(item), this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWithIterable(@NonNull Iterable<? extends T> items) {
        return concatArray(fromIterable(items), this);
    }

    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @NonNull
    @SchedulerSupport("none")
    public final Disposable subscribe() {
        return subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    public abstract void subscribeActual(@NonNull Subscriber<? super T> subscriber);

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("custom")
    public final Flowable<T> subscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return subscribeOn(scheduler, !(this instanceof FlowableCreate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final <E extends Subscriber<? super T>> E subscribeWith(E subscriber) {
        subscribe(subscriber);
        return subscriber;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> switchIfEmpty(@NonNull Publisher<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchIfEmpty(this, other));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> switchMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return switchMap(mapper, bufferSize());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <R> Flowable<R> switchMap0(Function<? super T, ? extends Publisher<? extends R>> mapper, int bufferSize, boolean delayError) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        if (!(this instanceof ScalarSupplier)) {
            return RxJavaPlugins.onAssembly(new FlowableSwitchMap(this, mapper, bufferSize, delayError));
        }
        Object obj = ((ScalarSupplier) this).get();
        return obj == null ? empty() : FlowableScalarXMap.scalarXMap(obj, mapper);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Completable switchMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapCompletable(this, mapper, false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Completable switchMapCompletableDelayError(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapCompletable(this, mapper, true));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> switchMapDelayError(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return switchMapDelayError(mapper, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> Flowable<R> switchMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapMaybe(this, mapper, false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> Flowable<R> switchMapMaybeDelayError(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapMaybe(this, mapper, true));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> Flowable<R> switchMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapSingle(this, mapper, false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> Flowable<R> switchMapSingleDelayError(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchMapSingle(this, mapper, true));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> take(long count) {
        if (count >= 0) {
            return RxJavaPlugins.onAssembly(new FlowableTake(this, count));
        }
        throw new IllegalArgumentException("count >= 0 required but it was " + count);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> takeLast(int count) {
        if (count >= 0) {
            return count == 0 ? RxJavaPlugins.onAssembly(new FlowableIgnoreElements(this)) : count == 1 ? RxJavaPlugins.onAssembly(new FlowableTakeLastOne(this)) : RxJavaPlugins.onAssembly(new FlowableTakeLast(this, count));
        }
        throw new IllegalArgumentException("count >= 0 required but it was " + count);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> takeUntil(@NonNull Predicate<? super T> stopPredicate) {
        Objects.requireNonNull(stopPredicate, "stopPredicate is null");
        return RxJavaPlugins.onAssembly(new FlowableTakeUntilPredicate(this, stopPredicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> takeWhile(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableTakeWhile(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final TestSubscriber<T> test() {
        TestSubscriber<T> testSubscriber = new TestSubscriber<>();
        subscribe((FlowableSubscriber) testSubscriber);
        return testSubscriber;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> throttleFirst(long windowDuration, @NonNull TimeUnit unit) {
        return throttleFirst(windowDuration, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> throttleLast(long intervalDuration, @NonNull TimeUnit unit) {
        return sample(intervalDuration, unit);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> throttleLatest(long timeout, @NonNull TimeUnit unit) {
        return throttleLatest(timeout, unit, Schedulers.computation(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> throttleWithTimeout(long timeout, @NonNull TimeUnit unit) {
        return debounce(timeout, unit);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<Timed<T>> timeInterval() {
        return timeInterval(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <V> Flowable<T> timeout(@NonNull Function<? super T, ? extends Publisher<V>> itemTimeoutIndicator) {
        return timeout0(null, itemTimeoutIndicator, null);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<Timed<T>> timestamp() {
        return timestamp(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final <R> R to(@NonNull FlowableConverter<T, ? extends R> converter) {
        Objects.requireNonNull(converter, "converter is null");
        return converter.apply(this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Future<T> toFuture() {
        return (Future) subscribeWith(new FutureSubscriber());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<List<T>> toList() {
        return RxJavaPlugins.onAssembly(new FlowableToListSingle(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <K> Single<Map<K, T>> toMap(@NonNull Function<? super T, ? extends K> function) {
        Objects.requireNonNull(function, "keySelector is null");
        return (Single<Map<K, T>>) collect(HashMapSupplier.asSupplier(), Functions.toMapKeySelector(function));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <K> Single<Map<K, Collection<T>>> toMultimap(@NonNull Function<? super T, ? extends K> function) {
        return (Single<Map<K, Collection<T>>>) toMultimap(function, Functions.identity(), HashMapSupplier.asSupplier(), ArrayListSupplier.asFunction());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Observable<T> toObservable() {
        return RxJavaPlugins.onAssembly(new ObservableFromPublisher(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<List<T>> toSortedList() {
        return toSortedList(Functions.naturalComparator());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("custom")
    public final Flowable<T> unsubscribeOn(@NonNull Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableUnsubscribeOn(this, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<Flowable<T>> window(long count) {
        return window(count, count, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <U, R> Flowable<R> withLatestFrom(@NonNull Publisher<? extends U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner) {
        Objects.requireNonNull(other, "other is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return RxJavaPlugins.onAssembly(new FlowableWithLatestFrom(this, combiner, other));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, R> Flowable<R> zipWith(@NonNull Iterable<U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> zipper) {
        Objects.requireNonNull(other, "other is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return RxJavaPlugins.onAssembly(new FlowableZipIterable(this, other, zipper));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, R> Flowable<R> combineLatest(@NonNull Iterable<? extends Publisher<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> combiner, int bufferSize) {
        Objects.requireNonNull(sources, "sources is null");
        Objects.requireNonNull(combiner, "combiner is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableCombineLatest((Iterable) sources, (Function) combiner, bufferSize, false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, R> Flowable<R> combineLatestArray(@NonNull Publisher<? extends T>[] sources, @NonNull Function<? super Object[], ? extends R> combiner, int bufferSize) {
        Objects.requireNonNull(sources, "sources is null");
        if (sources.length == 0) {
            return empty();
        }
        Objects.requireNonNull(combiner, "combiner is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableCombineLatest((Publisher[]) sources, (Function) combiner, bufferSize, false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, R> Flowable<R> combineLatestArrayDelayError(@NonNull Publisher<? extends T>[] sources, @NonNull Function<? super Object[], ? extends R> combiner, int bufferSize) {
        Objects.requireNonNull(sources, "sources is null");
        Objects.requireNonNull(combiner, "combiner is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return sources.length == 0 ? empty() : RxJavaPlugins.onAssembly(new FlowableCombineLatest((Publisher[]) sources, (Function) combiner, bufferSize, true));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, R> Flowable<R> combineLatestDelayError(@NonNull Iterable<? extends Publisher<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> combiner, int bufferSize) {
        Objects.requireNonNull(sources, "sources is null");
        Objects.requireNonNull(combiner, "combiner is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableCombineLatest((Iterable) sources, (Function) combiner, bufferSize, true));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEager(int maxConcurrency, int prefetch, @NonNull Publisher<? extends T>... sources) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(new FlowableFromArray(sources), Functions.identity(), maxConcurrency, prefetch, ErrorMode.IMMEDIATE));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatArrayEagerDelayError(int maxConcurrency, int prefetch, @NonNull Publisher<? extends T>... sources) {
        return fromArray(sources).concatMapEagerDelayError(Functions.identity(), true, maxConcurrency, prefetch);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Iterable<? extends Publisher<? extends T>> sources, int maxConcurrency, int prefetch) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(new FlowableFromIterable(sources), Functions.identity(), maxConcurrency, prefetch, ErrorMode.BOUNDARY));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Iterable<? extends Publisher<? extends T>> sources, int maxConcurrency, int prefetch) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(new FlowableFromIterable(sources), Functions.identity(), maxConcurrency, prefetch, ErrorMode.END));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public static Flowable<Long> interval(long initialDelay, long period, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableInterval(Math.max(0L, initialDelay), Math.max(0L, period), unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public static Flowable<Long> intervalRange(long start, long count, long initialDelay, long period, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
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
        return RxJavaPlugins.onAssembly(new FlowableIntervalRange(start, j2, Math.max(0L, initialDelay), Math.max(0L, period), unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Iterable<? extends Publisher<? extends T>> sources) {
        return fromIterable(sources).flatMap(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArray(@NonNull Publisher<? extends T>... sources) {
        return fromArray(sources).flatMap(Functions.identity(), sources.length);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeArrayDelayError(@NonNull Publisher<? extends T>... sources) {
        return fromArray(sources).flatMap(Functions.identity(), true, sources.length);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Iterable<? extends Publisher<? extends T>> sources, int maxConcurrency, int bufferSize) {
        return fromIterable(sources).flatMap(Functions.identity(), true, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2, @NonNull BiPredicate<? super T, ? super T> isEqual) {
        return sequenceEqual(source1, source2, isEqual, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNext(@NonNull Publisher<? extends Publisher<? extends T>> sources) {
        return fromPublisher(sources).switchMap(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> switchOnNextDelayError(@NonNull Publisher<? extends Publisher<? extends T>> sources, int prefetch) {
        return fromPublisher(sources).switchMapDelayError(Functions.identity(), prefetch);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public static Flowable<Long> timer(long delay, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableTimer(Math.max(0L, delay), unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T, D> Flowable<T> using(@NonNull Supplier<? extends D> resourceSupplier, @NonNull Function<? super D, ? extends Publisher<? extends T>> sourceSupplier, @NonNull Consumer<? super D> resourceCleanup, boolean eager) {
        Objects.requireNonNull(resourceSupplier, "resourceSupplier is null");
        Objects.requireNonNull(sourceSupplier, "sourceSupplier is null");
        Objects.requireNonNull(resourceCleanup, "resourceCleanup is null");
        return RxJavaPlugins.onAssembly(new FlowableUsing(resourceSupplier, sourceSupplier, resourceCleanup, eager));
    }

    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final void blockingForEach(@NonNull Consumer<? super T> onNext, int bufferSize) {
        Objects.requireNonNull(onNext, "onNext is null");
        Iterator<T> it = blockingIterable(bufferSize).iterator();
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
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Iterable<T> blockingIterable(int bufferSize) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return new BlockingFlowableIterable(this, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final T blockingSingle(@NonNull T defaultItem) {
        return single(defaultItem).blockingGet();
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Stream<T> blockingStream(int prefetch) {
        Iterator<T> it = blockingIterable(prefetch).iterator();
        Stream stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, 0), false);
        Disposable disposable = (Disposable) it;
        disposable.getClass();
        return (Stream) stream.onClose(new a(disposable));
    }

    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onNext) {
        FlowableBlockingSubscribe.subscribe(this, onNext, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<List<T>> buffer(int i2, int i3) {
        return (Flowable<List<T>>) buffer(i2, i3, ArrayListSupplier.asSupplier());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        if (!(this instanceof ScalarSupplier)) {
            return RxJavaPlugins.onAssembly(new FlowableConcatMap(this, mapper, prefetch, ErrorMode.IMMEDIATE));
        }
        Object obj = ((ScalarSupplier) this).get();
        return obj == null ? empty() : FlowableScalarXMap.scalarXMap(obj, mapper);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Completable concatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapCompletable(this, mapper, ErrorMode.IMMEDIATE, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Completable concatMapCompletableDelayError(@NonNull Function<? super T, ? extends CompletableSource> mapper, boolean tillTheEnd) {
        return concatMapCompletableDelayError(mapper, tillTheEnd, 2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapDelayError(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, boolean tillTheEnd, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        if (!(this instanceof ScalarSupplier)) {
            return RxJavaPlugins.onAssembly(new FlowableConcatMap(this, mapper, prefetch, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY));
        }
        Object obj = ((ScalarSupplier) this).get();
        return obj == null ? empty() : FlowableScalarXMap.scalarXMap(obj, mapper);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapEager(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(this, mapper, maxConcurrency, prefetch, ErrorMode.IMMEDIATE));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapEagerDelayError(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, boolean tillTheEnd, int maxConcurrency, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(this, mapper, maxConcurrency, prefetch, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Flowable<U> concatMapIterable(@NonNull Function<? super T, ? extends Iterable<? extends U>> mapper, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableFlattenIterable(this, mapper, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapMaybe(this, mapper, ErrorMode.IMMEDIATE, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapMaybeDelayError(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean tillTheEnd) {
        return concatMapMaybeDelayError(mapper, tillTheEnd, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapSingle(this, mapper, ErrorMode.IMMEDIATE, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapSingleDelayError(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean tillTheEnd) {
        return concatMapSingleDelayError(mapper, tillTheEnd, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapStream(@NonNull Function<? super T, ? extends Stream<? extends R>> mapper, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapStream(this, mapper, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <K> Flowable<T> distinct(@NonNull Function<? super T, K> keySelector) {
        return distinct(keySelector, Functions.createHashSet());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <K> Flowable<T> distinctUntilChanged(@NonNull Function<? super T, K> keySelector) {
        Objects.requireNonNull(keySelector, "keySelector is null");
        return RxJavaPlugins.onAssembly(new FlowableDistinctUntilChanged(this, keySelector, ObjectHelper.equalsPredicate()));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayErrors) {
        return flatMap(mapper, delayErrors, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Completable flatMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper, boolean delayErrors, int maxConcurrency) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapCompletableCompletable(this, mapper, delayErrors, maxConcurrency));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> Flowable<U> flatMapIterable(@NonNull Function<? super T, ? extends Iterable<? extends U>> mapper, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableFlattenIterable(this, mapper, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean delayErrors, int maxConcurrency) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapMaybe(this, mapper, delayErrors, maxConcurrency));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean delayErrors, int maxConcurrency) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapSingle(this, mapper, delayErrors, maxConcurrency));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMapStream(@NonNull Function<? super T, ? extends Stream<? extends R>> mapper, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapStream(this, mapper, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.NONE)
    @SchedulerSupport("none")
    public final Disposable forEachWhile(@NonNull Predicate<? super T> onNext, @NonNull Consumer<? super Throwable> onError) {
        return forEachWhile(onNext, onError, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <K> Flowable<GroupedFlowable<K, T>> groupBy(@NonNull Function<? super T, ? extends K> function, boolean z2) {
        return (Flowable<GroupedFlowable<K, T>>) groupBy(function, Functions.identity(), z2, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> observeOn(@NonNull Scheduler scheduler, boolean delayError) {
        return observeOn(scheduler, delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureBuffer(boolean delayError) {
        return onBackpressureBuffer(bufferSize(), delayError, true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureDrop(@NonNull Consumer<? super T> onDrop) {
        Objects.requireNonNull(onDrop, "onDrop is null");
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureDrop(this, onDrop));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> onErrorComplete(@NonNull Predicate<? super Throwable> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableOnErrorComplete(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> parallel(int parallelism) {
        return ParallelFlowable.from(this, parallelism);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> publish(@NonNull Function<? super Flowable<T>, ? extends Publisher<R>> selector) {
        return publish(selector, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> repeat(long times) {
        if (times >= 0) {
            return times == 0 ? empty() : RxJavaPlugins.onAssembly(new FlowableRepeat(this, times));
        }
        throw new IllegalArgumentException("times >= 0 required but it was " + times);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> replay(@NonNull Function<? super Flowable<T>, ? extends Publisher<R>> selector) {
        Objects.requireNonNull(selector, "selector is null");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replaySupplier(this), selector);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> retry(@NonNull BiPredicate<? super Integer, ? super Throwable> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableRetryBiPredicate(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> sample(long period, @NonNull TimeUnit unit, boolean emitLast) {
        return sample(period, unit, Schedulers.computation(), emitLast);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> sorted(@NonNull Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator is null");
        return toList().toFlowable().map(Functions.listSorter(comparator)).flatMapIterable(Functions.identity());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onNext) {
        return subscribe(onNext, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> switchMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, int bufferSize) {
        return switchMap0(mapper, bufferSize, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> switchMapDelayError(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, int bufferSize) {
        return switchMap0(mapper, bufferSize, true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<T> throttleFirst(long skipDuration, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableThrottleFirstTimed(this, skipDuration, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<T> throttleLast(long intervalDuration, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return sample(intervalDuration, unit, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> throttleLatest(long timeout, @NonNull TimeUnit unit, boolean emitLast) {
        return throttleLatest(timeout, unit, Schedulers.computation(), emitLast);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<T> throttleWithTimeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return debounce(timeout, unit, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<Timed<T>> timeInterval(@NonNull Scheduler scheduler) {
        return timeInterval(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <V> Flowable<T> timeout(@NonNull Function<? super T, ? extends Publisher<V>> itemTimeoutIndicator, @NonNull Publisher<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(null, itemTimeoutIndicator, fallback);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<Timed<T>> timestamp(@NonNull Scheduler scheduler) {
        return timestamp(TimeUnit.MILLISECONDS, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<List<T>> toList(int capacityHint) {
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return RxJavaPlugins.onAssembly(new FlowableToListSingle(this, Functions.createArrayList(capacityHint)));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<List<T>> toSortedList(@NonNull Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator is null");
        return (Single<List<T>>) toList().map(Functions.listSorter(comparator));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<Flowable<T>> window(long count, long skip) {
        return window(count, skip, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends Publisher<? extends T>> sources) {
        return concat(sources, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Publisher<? extends Publisher<? extends T>> sources) {
        return concatDelayError(sources, bufferSize(), true);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public static <T> Flowable<T> error(@NonNull Throwable throwable) {
        Objects.requireNonNull(throwable, "throwable is null");
        return error((Supplier<? extends Throwable>) Functions.justSupplier(throwable));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> fromFuture(@NonNull Future<? extends T> future, long timeout, @NonNull TimeUnit unit) {
        Objects.requireNonNull(future, "future is null");
        Objects.requireNonNull(unit, "unit is null");
        return RxJavaPlugins.onAssembly(new FlowableFromFuture(future, timeout, unit));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> just(T item1, T item2) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        return fromArray(item1, item2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Iterable<? extends Publisher<? extends T>> sources, int maxConcurrency) {
        return fromIterable(sources).flatMap(Functions.identity(), maxConcurrency);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Iterable<? extends Publisher<? extends T>> sources, int maxConcurrency) {
        return fromIterable(sources).flatMap(Functions.identity(), true, maxConcurrency);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2, @NonNull BiPredicate<? super T, ? super T> isEqual, int bufferSize) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(isEqual, "isEqual is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableSequenceEqualSingle(source1, source2, isEqual, bufferSize));
    }

    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onNext, int bufferSize) {
        FlowableBlockingSubscribe.subscribe(this, onNext, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U extends Collection<? super T>> Flowable<U> buffer(int count, int skip, @NonNull Supplier<U> bufferSupplier) {
        ObjectHelper.verifyPositive(count, "count");
        ObjectHelper.verifyPositive(skip, "skip");
        Objects.requireNonNull(bufferSupplier, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableBuffer(this, count, skip, bufferSupplier));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Completable concatMapCompletableDelayError(@NonNull Function<? super T, ? extends CompletableSource> mapper, boolean tillTheEnd, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapCompletable(this, mapper, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapMaybeDelayError(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean tillTheEnd, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapMaybe(this, mapper, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> concatMapSingleDelayError(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean tillTheEnd, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapSingle(this, mapper, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> concatWith(@NonNull SingleSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableConcatWithSingle(this, other));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> debounce(long timeout, @NonNull TimeUnit unit) {
        return debounce(timeout, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> delay(long time, @NonNull TimeUnit unit) {
        return delay(time, unit, Schedulers.computation(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> delaySubscription(long time, @NonNull TimeUnit unit) {
        return delaySubscription(time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <K> Flowable<T> distinct(@NonNull Function<? super T, K> keySelector, @NonNull Supplier<? extends Collection<? super K>> collectionSupplier) {
        Objects.requireNonNull(keySelector, "keySelector is null");
        Objects.requireNonNull(collectionSupplier, "collectionSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableDistinct(this, keySelector, collectionSupplier));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Single<T> elementAt(long index, @NonNull T defaultItem) {
        if (index >= 0) {
            Objects.requireNonNull(defaultItem, "defaultItem is null");
            return RxJavaPlugins.onAssembly(new FlowableElementAtSingle(this, index, defaultItem));
        }
        throw new IndexOutOfBoundsException("index >= 0 required but it was " + index);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency) {
        return flatMap((Function) mapper, false, maxConcurrency, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.NONE)
    @SchedulerSupport("none")
    public final Disposable forEachWhile(@NonNull Predicate<? super T> onNext, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete) {
        Objects.requireNonNull(onNext, "onNext is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        ForEachWhileSubscriber forEachWhileSubscriber = new ForEachWhileSubscriber(onNext, onError, onComplete);
        subscribe((FlowableSubscriber) forEachWhileSubscriber);
        return forEachWhileSubscriber;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <K, V> Flowable<GroupedFlowable<K, V>> groupBy(@NonNull Function<? super T, ? extends K> keySelector, @NonNull Function<? super T, ? extends V> valueSelector) {
        return groupBy(keySelector, valueSelector, false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> mergeWith(@NonNull SingleSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableMergeWithSingle(this, other));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> observeOn(@NonNull Scheduler scheduler, boolean delayError, int bufferSize) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableObserveOn(this, scheduler, delayError, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureBuffer(int capacity) {
        return onBackpressureBuffer(capacity, false, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> Flowable<R> onBackpressureReduce(@NonNull Supplier<R> supplier, @NonNull BiFunction<R, ? super T, R> reducer) {
        Objects.requireNonNull(supplier, "supplier is null");
        Objects.requireNonNull(reducer, "reducer is null");
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureReduceWith(this, supplier, reducer));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> parallel(int parallelism, int prefetch) {
        return ParallelFlowable.from(this, parallelism, prefetch);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> publish(@NonNull Function<? super Flowable<T>, ? extends Publisher<? extends R>> selector, int prefetch) {
        Objects.requireNonNull(selector, "selector is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowablePublishMulticast(this, selector, prefetch, false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> Single<R> reduce(R seed, @NonNull BiFunction<R, ? super T, R> reducer) {
        Objects.requireNonNull(seed, "seed is null");
        Objects.requireNonNull(reducer, "reducer is null");
        return RxJavaPlugins.onAssembly(new FlowableReduceSeedSingle(this, seed, reducer));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<T> sample(long period, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableSampleTimed(this, period, unit, scheduler, false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> scan(R initialValue, @NonNull BiFunction<R, ? super T, R> accumulator) {
        Objects.requireNonNull(initialValue, "initialValue is null");
        return scanWith(Functions.justSupplier(initialValue), accumulator);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull SingleSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return concat(Single.wrap(other).toFlowable(), this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError) {
        return subscribe(onNext, onError, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("custom")
    public final Flowable<T> subscribeOn(@NonNull Scheduler scheduler, boolean requestOn) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableSubscribeOn(this, scheduler, requestOn));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> take(long time, @NonNull TimeUnit unit) {
        return takeUntil(timer(time, unit));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <U> Flowable<T> takeUntil(@NonNull Publisher<U> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableTakeUntil(this, other));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final TestSubscriber<T> test(long initialRequest) {
        TestSubscriber<T> testSubscriber = new TestSubscriber<>(initialRequest);
        subscribe((FlowableSubscriber) testSubscriber);
        return testSubscriber;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<T> throttleLatest(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return throttleLatest(timeout, unit, scheduler, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<Timed<T>> timeInterval(@NonNull TimeUnit unit) {
        return timeInterval(unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<Timed<T>> timestamp(@NonNull TimeUnit unit) {
        return timestamp(unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <K, V> Single<Map<K, V>> toMap(@NonNull Function<? super T, ? extends K> function, @NonNull Function<? super T, ? extends V> function2) {
        Objects.requireNonNull(function, "keySelector is null");
        Objects.requireNonNull(function2, "valueSelector is null");
        return (Single<Map<K, V>>) collect(HashMapSupplier.asSupplier(), Functions.toMapKeyValueSelector(function, function2));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<Flowable<T>> window(long count, long skip, int bufferSize) {
        ObjectHelper.verifyPositive(skip, "skip");
        ObjectHelper.verifyPositive(count, "count");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableWindow(this, count, skip, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends Publisher<? extends T>> sources, int prefetch) {
        return fromPublisher(sources).concatMap(Functions.identity(), prefetch);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatDelayError(@NonNull Publisher<? extends Publisher<? extends T>> sources, int prefetch, boolean tillTheEnd) {
        return fromPublisher(sources).concatMapDelayError(Functions.identity(), tillTheEnd, prefetch);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Publisher<? extends Publisher<? extends T>> sources) {
        return merge(sources, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Publisher<? extends Publisher<? extends T>> sources) {
        return mergeDelayError(sources, bufferSize());
    }

    private <U, V> Flowable<T> timeout0(Publisher<U> firstTimeoutIndicator, Function<? super T, ? extends Publisher<V>> itemTimeoutIndicator, Publisher<? extends T> fallback) {
        Objects.requireNonNull(itemTimeoutIndicator, "itemTimeoutIndicator is null");
        return RxJavaPlugins.onAssembly(new FlowableTimeout(this, firstTimeoutIndicator, itemTimeoutIndicator, fallback));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, R> Flowable<R> zip(@NonNull Iterable<? extends Publisher<? extends T>> sources, @NonNull Function<? super Object[], ? extends R> zipper, boolean delayError, int bufferSize) {
        Objects.requireNonNull(zipper, "zipper is null");
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableZip(null, sources, zipper, bufferSize, delayError));
    }

    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError) {
        FlowableBlockingSubscribe.subscribe(this, onNext, onError, Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R, A> Single<R> collect(@NonNull Collector<? super T, A, R> collector) {
        Objects.requireNonNull(collector, "collector is null");
        return RxJavaPlugins.onAssembly(new FlowableCollectWithCollectorSingle(this, collector));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<T> debounce(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableDebounceTimed(this, timeout, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> delay(long time, @NonNull TimeUnit unit, boolean delayError) {
        return delay(time, unit, Schedulers.computation(), delayError);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> delaySubscription(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return delaySubscription(timer(time, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> distinctUntilChanged(@NonNull BiPredicate<? super T, ? super T> comparer) {
        Objects.requireNonNull(comparer, "comparer is null");
        return RxJavaPlugins.onAssembly(new FlowableDistinctUntilChanged(this, Functions.identity(), comparer));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayErrors, int maxConcurrency) {
        return flatMap(mapper, delayErrors, maxConcurrency, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <K, V> Flowable<GroupedFlowable<K, V>> groupBy(@NonNull Function<? super T, ? extends K> keySelector, @NonNull Function<? super T, ? extends V> valueSelector, boolean delayError) {
        return groupBy(keySelector, valueSelector, delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureBuffer(int capacity, boolean delayError) {
        return onBackpressureBuffer(capacity, delayError, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> replay(@NonNull Function<? super Flowable<T>, ? extends Publisher<R>> selector, int bufferSize) {
        Objects.requireNonNull(selector, "selector is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replaySupplier(this, bufferSize, false), selector);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> retry(long times) {
        return retry(times, Functions.alwaysTrue());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> skip(long time, @NonNull TimeUnit unit) {
        return skipUntil(timer(time, unit));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<T> skipLast(long time, @NonNull TimeUnit unit) {
        return skipLast(time, unit, Schedulers.computation(), false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete) {
        Objects.requireNonNull(onNext, "onNext is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        LambdaSubscriber lambdaSubscriber = new LambdaSubscriber(onNext, onError, onComplete, FlowableInternalHelper.RequestMax.INSTANCE);
        subscribe((FlowableSubscriber) lambdaSubscriber);
        return lambdaSubscriber;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("custom")
    public final Flowable<T> take(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return takeUntil(timer(time, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<T> throttleLatest(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean emitLast) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableThrottleLatest(this, timeout, unit, scheduler, emitLast));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<Timed<T>> timeInterval(@NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableTimeInterval(this, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> timeout(long timeout, @NonNull TimeUnit unit) {
        return timeout0(timeout, unit, null, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<Timed<T>> timestamp(@NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(timeUnit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return (Flowable<Timed<T>>) map(Functions.timestampWith(timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <U extends Collection<? super T>> Single<U> toList(@NonNull Supplier<U> collectionSupplier) {
        Objects.requireNonNull(collectionSupplier, "collectionSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableToListSingle(this, collectionSupplier));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<List<T>> toSortedList(@NonNull Comparator<? super T> comparator, int i2) {
        Objects.requireNonNull(comparator, "comparator is null");
        return (Single<List<T>>) toList(i2).map(Functions.listSorter(comparator));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <T1, T2, R> Flowable<R> withLatestFrom(@NonNull Publisher<T1> source1, @NonNull Publisher<T2> source2, @NonNull Function3<? super T, ? super T1, ? super T2, R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return withLatestFrom((Publisher<?>[]) new Publisher[]{source1, source2}, Functions.toFunction(combiner));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, R> Flowable<R> zipWith(@NonNull Publisher<? extends U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> zipper) {
        Objects.requireNonNull(other, "other is null");
        return zip(this, other, zipper);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return concatArray(source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public static Flowable<Long> interval(long period, @NonNull TimeUnit unit) {
        return interval(period, period, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Publisher<? extends Publisher<? extends T>> sources, int maxConcurrency) {
        return fromPublisher(sources).flatMap(Functions.identity(), maxConcurrency);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Publisher<? extends Publisher<? extends T>> sources, int maxConcurrency) {
        return fromPublisher(sources).flatMap(Functions.identity(), true, maxConcurrency);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final T blockingFirst(@NonNull T defaultItem) throws InterruptedException {
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        BlockingFirstSubscriber blockingFirstSubscriber = new BlockingFirstSubscriber();
        subscribe((FlowableSubscriber) blockingFirstSubscriber);
        T tBlockingGet = blockingFirstSubscriber.blockingGet();
        return tBlockingGet != null ? tBlockingGet : defaultItem;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final T blockingLast(@NonNull T defaultItem) throws InterruptedException {
        Objects.requireNonNull(defaultItem, "defaultItem is null");
        BlockingLastSubscriber blockingLastSubscriber = new BlockingLastSubscriber();
        subscribe((FlowableSubscriber) blockingLastSubscriber);
        T tBlockingGet = blockingLastSubscriber.blockingGet();
        return tBlockingGet != null ? tBlockingGet : defaultItem;
    }

    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError, int bufferSize) {
        FlowableBlockingSubscribe.subscribe(this, onNext, onError, Functions.EMPTY_ACTION, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> concatWith(@NonNull MaybeSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableConcatWithMaybe(this, other));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> delay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return delay(time, unit, scheduler, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        if (this instanceof ScalarSupplier) {
            Object obj = ((ScalarSupplier) this).get();
            if (obj == null) {
                return empty();
            }
            return FlowableScalarXMap.scalarXMap(obj, mapper);
        }
        return RxJavaPlugins.onAssembly(new FlowableFlatMap(this, mapper, delayErrors, maxConcurrency, bufferSize));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, V> Flowable<V> flatMapIterable(@NonNull Function<? super T, ? extends Iterable<? extends U>> function, @NonNull BiFunction<? super T, ? super U, ? extends V> biFunction) {
        Objects.requireNonNull(function, "mapper is null");
        Objects.requireNonNull(biFunction, "combiner is null");
        return (Flowable<V>) flatMap(FlowableInternalHelper.flatMapIntoIterable(function), biFunction, false, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final <K, V> Flowable<GroupedFlowable<K, V>> groupBy(@NonNull Function<? super T, ? extends K> keySelector, @NonNull Function<? super T, ? extends V> valueSelector, boolean delayError, int bufferSize) {
        Objects.requireNonNull(keySelector, "keySelector is null");
        Objects.requireNonNull(valueSelector, "valueSelector is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableGroupBy(this, keySelector, valueSelector, bufferSize, delayError, null));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> mergeWith(@NonNull MaybeSource<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableMergeWithMaybe(this, other));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureBuffer(int capacity, boolean delayError, boolean unbounded) {
        ObjectHelper.verifyPositive(capacity, "capacity");
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureBuffer(this, capacity, unbounded, delayError, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> retry(long times, @NonNull Predicate<? super Throwable> predicate) {
        if (times >= 0) {
            Objects.requireNonNull(predicate, "predicate is null");
            return RxJavaPlugins.onAssembly(new FlowableRetryPredicate(this, times, predicate));
        }
        throw new IllegalArgumentException("times >= 0 required but it was " + times);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> skip(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return skipUntil(timer(time, unit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<T> skipLast(long time, @NonNull TimeUnit unit, boolean delayError) {
        return skipLast(time, unit, Schedulers.computation(), delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull MaybeSource<T> other) {
        Objects.requireNonNull(other, "other is null");
        return concat(Maybe.wrap(other).toFlowable(), this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> takeLast(long count, long time, @NonNull TimeUnit unit) {
        return takeLast(count, time, unit, Schedulers.computation(), false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final TestSubscriber<T> test(long initialRequest, boolean cancel) {
        TestSubscriber<T> testSubscriber = new TestSubscriber<>(initialRequest);
        if (cancel) {
            testSubscriber.cancel();
        }
        subscribe((FlowableSubscriber) testSubscriber);
        return testSubscriber;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull Publisher<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(timeout, unit, fallback, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <K, V> Single<Map<K, Collection<V>>> toMultimap(@NonNull Function<? super T, ? extends K> keySelector, @NonNull Function<? super T, ? extends V> valueSelector) {
        return toMultimap(keySelector, valueSelector, HashMapSupplier.asSupplier(), ArrayListSupplier.asFunction());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, R> Flowable<R> combineLatest(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new Publisher[]{source1, source2}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Publisher<? extends Publisher<? extends T>> sources) {
        return concatEager(sources, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Publisher<? extends Publisher<? extends T>> sources) {
        return concatEagerDelayError(sources, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, S> Flowable<T> generate(@NonNull Supplier<S> initialState, @NonNull BiConsumer<S, Emitter<T>> generator) {
        Objects.requireNonNull(generator, "generator is null");
        return generate(initialState, FlowableInternalHelper.simpleBiGenerator(generator), Functions.emptyConsumer());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public static Flowable<Long> interval(long period, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return interval(period, period, unit, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> just(T item1, T item2, T item3) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        Objects.requireNonNull(item3, "item3 is null");
        return fromArray(item1, item2, item3);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return fromArray(source1, source2).flatMap(Functions.identity(), false, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        return fromArray(source1, source2).flatMap(Functions.identity(), true, 2);
    }

    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete) {
        FlowableBlockingSubscribe.subscribe(this, onNext, onError, onComplete);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> delay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableDelay(this, Math.max(0L, time), unit, scheduler, delayError));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doOnEach(@NonNull Consumer<? super Notification<T>> onNotification) {
        Objects.requireNonNull(onNotification, "onNotification is null");
        return doOnEach(Functions.notificationOnNext(onNotification), Functions.notificationOnError(onNotification), Functions.notificationOnComplete(onNotification), Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final ConnectableFlowable<T> publish(int bufferSize) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly((ConnectableFlowable) new FlowablePublish(this, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<T> sample(long period, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean emitLast) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableSampleTimed(this, period, unit, scheduler, emitLast));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("custom")
    public final Flowable<T> skipLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return skipLast(time, unit, scheduler, false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> takeLast(long count, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return takeLast(count, time, unit, scheduler, false, bufferSize());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <K, V> Single<Map<K, V>> toMap(@NonNull Function<? super T, ? extends K> function, @NonNull Function<? super T, ? extends V> function2, @NonNull Supplier<? extends Map<K, V>> supplier) {
        Objects.requireNonNull(function, "keySelector is null");
        Objects.requireNonNull(function2, "valueSelector is null");
        return (Single<Map<K, V>>) collect(supplier, Functions.toMapKeyValueSelector(function, function2));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Single<List<T>> toSortedList(int capacityHint) {
        return toSortedList(Functions.naturalComparator(), capacityHint);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, R> Flowable<R> zipWith(@NonNull Publisher<? extends U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> zipper, boolean delayError) {
        return zip(this, other, zipper, delayError);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEager(@NonNull Publisher<? extends Publisher<? extends T>> sources, int maxConcurrency, int prefetch) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEagerPublisher(sources, Functions.identity(), maxConcurrency, prefetch, ErrorMode.IMMEDIATE));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concatEagerDelayError(@NonNull Publisher<? extends Publisher<? extends T>> sources, int maxConcurrency, int prefetch) {
        Objects.requireNonNull(sources, "sources is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEagerPublisher(sources, Functions.identity(), maxConcurrency, prefetch, ErrorMode.END));
    }

    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete, int bufferSize) {
        FlowableBlockingSubscribe.subscribe(this, onNext, onError, onComplete, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U extends Collection<? super T>> Flowable<U> buffer(int count, @NonNull Supplier<U> bufferSupplier) {
        return buffer(count, count, bufferSupplier);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> concatWith(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableConcatWithCompletable(this, other));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> mergeWith(@NonNull CompletableSource other) {
        Objects.requireNonNull(other, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableMergeWithCompletable(this, other));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureBuffer(int capacity, boolean delayError, boolean unbounded, @NonNull Action onOverflow) {
        Objects.requireNonNull(onOverflow, "onOverflow is null");
        ObjectHelper.verifyPositive(capacity, "capacity");
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureBuffer(this, capacity, unbounded, delayError, onOverflow));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> replay(@NonNull Function<? super Flowable<T>, ? extends Publisher<R>> selector, int bufferSize, boolean eagerTruncate) {
        Objects.requireNonNull(selector, "selector is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replaySupplier(this, bufferSize, eagerTruncate), selector);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("custom")
    public final Flowable<T> skipLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError) {
        return skipLast(time, unit, scheduler, delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> startWith(@NonNull Publisher<? extends T> other) {
        Objects.requireNonNull(other, "other is null");
        return concatArray(other, this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> takeLast(long count, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError, int bufferSize) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        if (count >= 0) {
            return RxJavaPlugins.onAssembly(new FlowableTakeLastTimed(this, count, time, unit, scheduler, bufferSize, delayError));
        }
        throw new IllegalArgumentException("count >= 0 required but it was " + count);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, @NonNull Publisher<? extends T> fallback) {
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(timeout, unit, fallback, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<Flowable<T>> window(long timespan, long timeskip, @NonNull TimeUnit unit) {
        return window(timespan, timeskip, unit, Schedulers.computation(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, R> Flowable<R> zipWith(@NonNull Publisher<? extends U> other, @NonNull BiFunction<? super T, ? super U, ? extends R> zipper, boolean delayError, int bufferSize) {
        return zip(this, other, zipper, delayError, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2, @NonNull Publisher<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return concatArray(source1, source2, source3);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Single<Boolean> sequenceEqual(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2, int bufferSize) {
        return sequenceEqual(source1, source2, ObjectHelper.equalsPredicate(), bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, R> Flowable<R> zip(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2);
    }

    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final void blockingSubscribe(@NonNull Subscriber<? super T> subscriber) {
        Objects.requireNonNull(subscriber, "subscriber is null");
        FlowableBlockingSubscribe.subscribe(this, subscriber);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<List<T>> buffer(long j2, long j3, @NonNull TimeUnit timeUnit) {
        return (Flowable<List<T>>) buffer(j2, j3, timeUnit, Schedulers.computation(), ArrayListSupplier.asSupplier());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, V> Flowable<V> flatMapIterable(@NonNull Function<? super T, ? extends Iterable<? extends U>> function, @NonNull BiFunction<? super T, ? super U, ? extends V> biFunction, int i2) {
        Objects.requireNonNull(function, "mapper is null");
        Objects.requireNonNull(biFunction, "combiner is null");
        return (Flowable<V>) flatMap(FlowableInternalHelper.flatMapIntoIterable(function), biFunction, false, bufferSize(), i2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> retry(@NonNull Predicate<? super Throwable> predicate) {
        return retry(Long.MAX_VALUE, predicate);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("custom")
    public final Flowable<T> skipLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError, int bufferSize) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableSkipLastTimed(this, time, unit, scheduler, bufferSize << 1, delayError));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
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
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<Flowable<T>> window(long timespan, long timeskip, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return window(timespan, timeskip, unit, scheduler, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2, @NonNull Publisher<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return fromArray(source1, source2, source3).flatMap(Functions.identity(), false, 3);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2, @NonNull Publisher<? extends T> source3) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        return fromArray(source1, source2, source3).flatMap(Functions.identity(), true, 3);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<List<T>> buffer(long j2, long j3, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return (Flowable<List<T>>) buffer(j2, j3, timeUnit, scheduler, ArrayListSupplier.asSupplier());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final <R> Flowable<R> concatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapScheduler(this, mapper, prefetch, ErrorMode.IMMEDIATE, scheduler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final <R> Flowable<R> concatMapDelayError(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, boolean tillTheEnd, int prefetch, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapScheduler(this, mapper, prefetch, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, scheduler));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, V> Flowable<T> delay(@NonNull Publisher<U> subscriptionIndicator, @NonNull Function<? super T, ? extends Publisher<V>> itemDelayIndicator) {
        return delaySubscription(subscriptionIndicator).delay(itemDelayIndicator);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final <K, V> Flowable<GroupedFlowable<K, V>> groupBy(@NonNull Function<? super T, ? extends K> keySelector, @NonNull Function<? super T, ? extends V> valueSelector, boolean delayError, int bufferSize, @NonNull Function<? super Consumer<Object>, ? extends Map<K, Object>> evictingMapFactory) {
        Objects.requireNonNull(keySelector, "keySelector is null");
        Objects.requireNonNull(valueSelector, "valueSelector is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(evictingMapFactory, "evictingMapFactory is null");
        return RxJavaPlugins.onAssembly(new FlowableGroupBy(this, keySelector, valueSelector, bufferSize, delayError, evictingMapFactory));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <U> Flowable<T> sample(@NonNull Publisher<U> sampler) {
        Objects.requireNonNull(sampler, "sampler is null");
        return RxJavaPlugins.onAssembly(new FlowableSamplePublisher(this, sampler, false));
    }

    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final Disposable subscribe(@NonNull Consumer<? super T> onNext, @NonNull Consumer<? super Throwable> onError, @NonNull Action onComplete, @NonNull DisposableContainer container) {
        Objects.requireNonNull(onNext, "onNext is null");
        Objects.requireNonNull(onError, "onError is null");
        Objects.requireNonNull(onComplete, "onComplete is null");
        Objects.requireNonNull(container, "container is null");
        DisposableAutoReleaseSubscriber disposableAutoReleaseSubscriber = new DisposableAutoReleaseSubscriber(container, onNext, onError, onComplete);
        container.add(disposableAutoReleaseSubscriber);
        subscribe((FlowableSubscriber) disposableAutoReleaseSubscriber);
        return disposableAutoReleaseSubscriber;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("custom")
    public final Flowable<T> timeout(long timeout, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return timeout0(timeout, unit, null, scheduler);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<Flowable<T>> window(long timespan, long timeskip, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, int bufferSize) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        ObjectHelper.verifyPositive(timespan, "timespan");
        ObjectHelper.verifyPositive(timeskip, "timeskip");
        Objects.requireNonNull(scheduler, "scheduler is null");
        Objects.requireNonNull(unit, "unit is null");
        return RxJavaPlugins.onAssembly(new FlowableWindowTimed(this, timespan, timeskip, unit, scheduler, Long.MAX_VALUE, bufferSize, false));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <T1, T2, T3, R> Flowable<R> withLatestFrom(@NonNull Publisher<T1> source1, @NonNull Publisher<T2> source2, @NonNull Publisher<T3> source3, @NonNull Function4<? super T, ? super T1, ? super T2, ? super T3, R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return withLatestFrom((Publisher<?>[]) new Publisher[]{source1, source2, source3}, Functions.toFunction(combiner));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, R> Flowable<R> combineLatest(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Function3<? super T1, ? super T2, ? super T3, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new Publisher[]{source1, source2, source3}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, S> Flowable<T> generate(@NonNull Supplier<S> initialState, @NonNull BiConsumer<S, Emitter<T>> generator, @NonNull Consumer<? super S> disposeState) {
        Objects.requireNonNull(generator, "generator is null");
        return generate(initialState, FlowableInternalHelper.simpleBiGenerator(generator), disposeState);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> just(T item1, T item2, T item3, T item4) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        Objects.requireNonNull(item3, "item3 is null");
        Objects.requireNonNull(item4, "item4 is null");
        return fromArray(item1, item2, item3, item4);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final <U extends Collection<? super T>> Flowable<U> buffer(long timespan, long timeskip, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, @NonNull Supplier<U> bufferSupplier) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        Objects.requireNonNull(bufferSupplier, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableBufferTimed(this, timespan, timeskip, unit, scheduler, bufferSupplier, Integer.MAX_VALUE, false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureBuffer(int capacity, @NonNull Action onOverflow) {
        return onBackpressureBuffer(capacity, false, false, onOverflow);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("io.reactivex:computation")
    public final <R> Flowable<R> replay(@NonNull Function<? super Flowable<T>, ? extends Publisher<R>> selector, int bufferSize, long time, @NonNull TimeUnit unit) {
        return replay(selector, bufferSize, time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <U, V> Flowable<T> timeout(@NonNull Publisher<U> firstTimeoutIndicator, @NonNull Function<? super T, ? extends Publisher<V>> itemTimeoutIndicator) {
        Objects.requireNonNull(firstTimeoutIndicator, "firstTimeoutIndicator is null");
        return timeout0(firstTimeoutIndicator, itemTimeoutIndicator, null);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> doOnEach(@NonNull Subscriber<? super T> subscriber) {
        Objects.requireNonNull(subscriber, "subscriber is null");
        return doOnEach(FlowableInternalHelper.subscriberOnNext(subscriber), FlowableInternalHelper.subscriberOnError(subscriber), FlowableInternalHelper.subscriberOnComplete(subscriber), Functions.EMPTY_ACTION);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final Flowable<T> onBackpressureBuffer(long capacity, @Nullable Action onOverflow, @NonNull BackpressureOverflowStrategy overflowStrategy) {
        Objects.requireNonNull(overflowStrategy, "overflowStrategy is null");
        ObjectHelper.verifyPositive(capacity, "capacity");
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureBufferStrategy(this, capacity, onOverflow, overflowStrategy));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final <R> Flowable<R> replay(@NonNull Function<? super Flowable<T>, ? extends Publisher<R>> selector, int bufferSize, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(selector, "selector is null");
        Objects.requireNonNull(unit, "unit is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replaySupplier(this, bufferSize, time, unit, scheduler, false), selector);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <U> Flowable<T> sample(@NonNull Publisher<U> sampler, boolean emitLast) {
        Objects.requireNonNull(sampler, "sampler is null");
        return RxJavaPlugins.onAssembly(new FlowableSamplePublisher(this, sampler, emitLast));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> concat(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2, @NonNull Publisher<? extends T> source3, @NonNull Publisher<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return concatArray(source1, source2, source3, source4);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, S> Flowable<T> generate(@NonNull Supplier<S> initialState, @NonNull BiFunction<S, Emitter<T>, S> generator) {
        return generate(initialState, generator, Functions.emptyConsumer());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, R> Flowable<R> zip(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> zipper, boolean delayError) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), delayError, bufferSize(), source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> takeLast(long time, @NonNull TimeUnit unit) {
        return takeLast(time, unit, Schedulers.computation(), false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, V> Flowable<T> timeout(@NonNull Publisher<U> firstTimeoutIndicator, @NonNull Function<? super T, ? extends Publisher<V>> itemTimeoutIndicator, @NonNull Publisher<? extends T> fallback) {
        Objects.requireNonNull(firstTimeoutIndicator, "firstTimeoutIndicator is null");
        Objects.requireNonNull(fallback, "fallback is null");
        return timeout0(firstTimeoutIndicator, itemTimeoutIndicator, fallback);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T, S> Flowable<T> generate(@NonNull Supplier<S> initialState, @NonNull BiFunction<S, Emitter<T>, S> generator, @NonNull Consumer<? super S> disposeState) {
        Objects.requireNonNull(initialState, "initialState is null");
        Objects.requireNonNull(generator, "generator is null");
        Objects.requireNonNull(disposeState, "disposeState is null");
        return RxJavaPlugins.onAssembly(new FlowableGenerate(initialState, generator, disposeState));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> merge(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2, @NonNull Publisher<? extends T> source3, @NonNull Publisher<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return fromArray(source1, source2, source3, source4).flatMap(Functions.identity(), false, 4);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> mergeDelayError(@NonNull Publisher<? extends T> source1, @NonNull Publisher<? extends T> source2, @NonNull Publisher<? extends T> source3, @NonNull Publisher<? extends T> source4) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        return fromArray(source1, source2, source3, source4).flatMap(Functions.identity(), true, 4);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> onNextMapper, @NonNull Function<? super Throwable, ? extends Publisher<? extends R>> onErrorMapper, @NonNull Supplier<? extends Publisher<? extends R>> onCompleteSupplier) {
        Objects.requireNonNull(onNextMapper, "onNextMapper is null");
        Objects.requireNonNull(onErrorMapper, "onErrorMapper is null");
        Objects.requireNonNull(onCompleteSupplier, "onCompleteSupplier is null");
        return merge(new FlowableMapNotification(this, onNextMapper, onErrorMapper, onCompleteSupplier));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> takeLast(long time, @NonNull TimeUnit unit, boolean delayError) {
        return takeLast(time, unit, Schedulers.computation(), delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <K, V> Single<Map<K, Collection<V>>> toMultimap(@NonNull Function<? super T, ? extends K> keySelector, @NonNull Function<? super T, ? extends V> valueSelector, @NonNull Supplier<Map<K, Collection<V>>> mapSupplier) {
        return toMultimap(keySelector, valueSelector, mapSupplier, ArrayListSupplier.asFunction());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<List<T>> buffer(long timespan, @NonNull TimeUnit unit) {
        return buffer(timespan, unit, Schedulers.computation(), Integer.MAX_VALUE);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> takeLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return takeLast(time, unit, scheduler, false, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, R> Flowable<R> combineLatest(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new Publisher[]{source1, source2, source3, source4}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> just(T item1, T item2, T item3, T item4, T item5) {
        Objects.requireNonNull(item1, "item1 is null");
        Objects.requireNonNull(item2, "item2 is null");
        Objects.requireNonNull(item3, "item3 is null");
        Objects.requireNonNull(item4, "item4 is null");
        Objects.requireNonNull(item5, "item5 is null");
        return fromArray(item1, item2, item3, item4, item5);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<List<T>> buffer(long timespan, @NonNull TimeUnit unit, int count) {
        return buffer(timespan, unit, Schedulers.computation(), count);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> takeLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError) {
        return takeLast(time, unit, scheduler, delayError, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<Flowable<T>> window(long timespan, @NonNull TimeUnit unit) {
        return window(timespan, unit, Schedulers.computation(), Long.MAX_VALUE, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <T1, T2, T3, T4, R> Flowable<R> withLatestFrom(@NonNull Publisher<T1> source1, @NonNull Publisher<T2> source2, @NonNull Publisher<T3> source3, @NonNull Publisher<T4> source4, @NonNull Function5<? super T, ? super T1, ? super T2, ? super T3, ? super T4, R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return withLatestFrom((Publisher<?>[]) new Publisher[]{source1, source2, source3, source4}, Functions.toFunction(combiner));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, R> Flowable<R> zip(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull BiFunction<? super T1, ? super T2, ? extends R> zipper, boolean delayError, int bufferSize) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), delayError, bufferSize, source1, source2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<List<T>> buffer(long j2, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler, int i2) {
        return (Flowable<List<T>>) buffer(j2, timeUnit, scheduler, i2, ArrayListSupplier.asSupplier(), false);
    }

    @Override // org.reactivestreams.Publisher
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final void subscribe(@NonNull Subscriber<? super T> subscriber) {
        if (subscriber instanceof FlowableSubscriber) {
            subscribe((FlowableSubscriber) subscriber);
        } else {
            Objects.requireNonNull(subscriber, "subscriber is null");
            subscribe((FlowableSubscriber) new StrictSubscriber(subscriber));
        }
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final Flowable<T> takeLast(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean delayError, int bufferSize) {
        return takeLast(Long.MAX_VALUE, time, unit, scheduler, delayError, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<Flowable<T>> window(long timespan, @NonNull TimeUnit unit, long count) {
        return window(timespan, unit, Schedulers.computation(), count, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final <U extends Collection<? super T>> Flowable<U> buffer(long timespan, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, int count, @NonNull Supplier<U> bufferSupplier, boolean restartTimerOnMaxSize) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        Objects.requireNonNull(bufferSupplier, "bufferSupplier is null");
        ObjectHelper.verifyPositive(count, "count");
        return RxJavaPlugins.onAssembly(new FlowableBufferTimed(this, timespan, timespan, unit, scheduler, bufferSupplier, count, restartTimerOnMaxSize));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> onNextMapper, @NonNull Function<Throwable, ? extends Publisher<? extends R>> onErrorMapper, @NonNull Supplier<? extends Publisher<? extends R>> onCompleteSupplier, int maxConcurrency) {
        Objects.requireNonNull(onNextMapper, "onNextMapper is null");
        Objects.requireNonNull(onErrorMapper, "onErrorMapper is null");
        Objects.requireNonNull(onCompleteSupplier, "onCompleteSupplier is null");
        return merge(new FlowableMapNotification(this, onNextMapper, onErrorMapper, onCompleteSupplier), maxConcurrency);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final <R> Flowable<R> replay(@NonNull Function<? super Flowable<T>, ? extends Publisher<R>> selector, int bufferSize, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean eagerTruncate) {
        Objects.requireNonNull(selector, "selector is null");
        Objects.requireNonNull(unit, "unit is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replaySupplier(this, bufferSize, time, unit, scheduler, eagerTruncate), selector);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<Flowable<T>> window(long timespan, @NonNull TimeUnit unit, long count, boolean restart) {
        return window(timespan, unit, Schedulers.computation(), count, restart);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<Flowable<T>> window(long timespan, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        return window(timespan, unit, scheduler, Long.MAX_VALUE, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<Flowable<T>> window(long timespan, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, long count) {
        return window(timespan, unit, scheduler, count, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, R> Flowable<R> zip(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3);
    }

    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public final void subscribe(@NonNull FlowableSubscriber<? super T> subscriber) {
        Objects.requireNonNull(subscriber, "subscriber is null");
        try {
            Subscriber<? super T> subscriberOnSubscribe = RxJavaPlugins.onSubscribe(this, subscriber);
            Objects.requireNonNull(subscriberOnSubscribe, "The RxJavaPlugins.onSubscribe hook returned a null FlowableSubscriber. Please check the handler provided to RxJavaPlugins.setOnFlowableSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
            subscribeActual(subscriberOnSubscribe);
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
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<Flowable<T>> window(long timespan, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, long count, boolean restart) {
        return window(timespan, unit, scheduler, count, restart, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, R> Flowable<R> combineLatest(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Publisher<? extends T5> source5, @NonNull Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new Publisher[]{source1, source2, source3, source4, source5}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> just(T item1, T item2, T item3, T item4, T item5, T item6) {
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
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner) {
        return flatMap(mapper, combiner, false, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<Flowable<T>> window(long timespan, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, long count, boolean restart, int bufferSize) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        Objects.requireNonNull(scheduler, "scheduler is null");
        Objects.requireNonNull(unit, "unit is null");
        ObjectHelper.verifyPositive(count, "count");
        return RxJavaPlugins.onAssembly(new FlowableWindowTimed(this, timespan, timespan, unit, scheduler, count, bufferSize, restart));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("custom")
    public final Flowable<List<T>> buffer(long j2, @NonNull TimeUnit timeUnit, @NonNull Scheduler scheduler) {
        return (Flowable<List<T>>) buffer(j2, timeUnit, scheduler, Integer.MAX_VALUE, ArrayListSupplier.asSupplier(), false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors) {
        return flatMap(mapper, combiner, delayErrors, bufferSize(), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> Flowable<R> withLatestFrom(@NonNull Publisher<?>[] others, @NonNull Function<? super Object[], R> combiner) {
        Objects.requireNonNull(others, "others is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return RxJavaPlugins.onAssembly(new FlowableWithLatestFromMany(this, others, combiner));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <TOpening, TClosing> Flowable<List<T>> buffer(@NonNull Publisher<? extends TOpening> publisher, @NonNull Function<? super TOpening, ? extends Publisher<? extends TClosing>> function) {
        return (Flowable<List<T>>) buffer(publisher, function, ArrayListSupplier.asSupplier());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors, int maxConcurrency) {
        return flatMap(mapper, combiner, delayErrors, maxConcurrency, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("io.reactivex:computation")
    public final <R> Flowable<R> replay(@NonNull Function<? super Flowable<T>, ? extends Publisher<R>> selector, long time, @NonNull TimeUnit unit) {
        return replay(selector, time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <TOpening, TClosing, U extends Collection<? super T>> Flowable<U> buffer(@NonNull Publisher<? extends TOpening> openingIndicator, @NonNull Function<? super TOpening, ? extends Publisher<? extends TClosing>> closingIndicator, @NonNull Supplier<U> bufferSupplier) {
        Objects.requireNonNull(openingIndicator, "openingIndicator is null");
        Objects.requireNonNull(closingIndicator, "closingIndicator is null");
        Objects.requireNonNull(bufferSupplier, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableBufferBoundary(this, openingIndicator, closingIndicator, bufferSupplier));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors, int maxConcurrency, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        Objects.requireNonNull(combiner, "combiner is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return flatMap(FlowableInternalHelper.flatMapWithCombiner(mapper, combiner), delayErrors, maxConcurrency, bufferSize);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final <R> Flowable<R> replay(@NonNull Function<? super Flowable<T>, ? extends Publisher<R>> selector, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(selector, "selector is null");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replaySupplier(this, time, unit, scheduler, false), selector);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, R> Flowable<R> zip(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(zipper, "zipper is null");
        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> Flowable<R> withLatestFrom(@NonNull Iterable<? extends Publisher<?>> others, @NonNull Function<? super Object[], R> combiner) {
        Objects.requireNonNull(others, "others is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return RxJavaPlugins.onAssembly(new FlowableWithLatestFromMany(this, others, combiner));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <B> Flowable<Flowable<T>> window(@NonNull Publisher<B> boundaryIndicator) {
        return window(boundaryIndicator, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <B> Flowable<Flowable<T>> window(@NonNull Publisher<B> boundaryIndicator, int bufferSize) {
        Objects.requireNonNull(boundaryIndicator, "boundaryIndicator is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableWindowBoundary(this, boundaryIndicator, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, R> Flowable<R> combineLatest(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Publisher<? extends T5> source5, @NonNull Publisher<? extends T6> source6, @NonNull Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new Publisher[]{source1, source2, source3, source4, source5, source6}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7) {
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
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <B> Flowable<List<T>> buffer(@NonNull Publisher<B> publisher) {
        return (Flowable<List<T>>) buffer(publisher, ArrayListSupplier.asSupplier());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final <R> Flowable<R> replay(@NonNull Function<? super Flowable<T>, ? extends Publisher<R>> selector, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean eagerTruncate) {
        Objects.requireNonNull(selector, "selector is null");
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replaySupplier(this, time, unit, scheduler, eagerTruncate), selector);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <B> Flowable<List<T>> buffer(@NonNull Publisher<B> publisher, int i2) {
        ObjectHelper.verifyPositive(i2, "initialCapacity");
        return (Flowable<List<T>>) buffer(publisher, Functions.createArrayList(i2));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U, R> Flowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends U>> mapper, @NonNull BiFunction<? super T, ? super U, ? extends R> combiner, int maxConcurrency) {
        return flatMap(mapper, combiner, false, maxConcurrency, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <U, V> Flowable<Flowable<T>> window(@NonNull Publisher<U> openingIndicator, @NonNull Function<? super U, ? extends Publisher<V>> closingIndicator) {
        return window(openingIndicator, closingIndicator, bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, R> Flowable<R> zip(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Publisher<? extends T5> source5, @NonNull Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper) {
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
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <B, U extends Collection<? super T>> Flowable<U> buffer(@NonNull Publisher<B> boundaryIndicator, @NonNull Supplier<U> bufferSupplier) {
        Objects.requireNonNull(boundaryIndicator, "boundaryIndicator is null");
        Objects.requireNonNull(bufferSupplier, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableBufferExactBoundary(this, boundaryIndicator, bufferSupplier));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.ERROR)
    @SchedulerSupport("none")
    public final <U, V> Flowable<Flowable<T>> window(@NonNull Publisher<U> openingIndicator, @NonNull Function<? super U, ? extends Publisher<V>> closingIndicator, int bufferSize) {
        Objects.requireNonNull(openingIndicator, "openingIndicator is null");
        Objects.requireNonNull(closingIndicator, "closingIndicator is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableWindowBoundarySelector(this, openingIndicator, closingIndicator, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final ConnectableFlowable<T> replay(int bufferSize) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return FlowableReplay.create(this, bufferSize, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final ConnectableFlowable<T> replay(int bufferSize, boolean eagerTruncate) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return FlowableReplay.create(this, bufferSize, eagerTruncate);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, R> Flowable<R> combineLatest(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Publisher<? extends T5> source5, @NonNull Publisher<? extends T6> source6, @NonNull Publisher<? extends T7> source7, @NonNull Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(source7, "source7 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new Publisher[]{source1, source2, source3, source4, source5, source6, source7}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8) {
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
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("io.reactivex:computation")
    public final ConnectableFlowable<T> replay(int bufferSize, long time, @NonNull TimeUnit unit) {
        return replay(bufferSize, time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final ConnectableFlowable<T> replay(int bufferSize, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return FlowableReplay.create(this, time, unit, scheduler, bufferSize, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, R> Flowable<R> zip(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Publisher<? extends T5> source5, @NonNull Publisher<? extends T6> source6, @NonNull Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper) {
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
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final ConnectableFlowable<T> replay(int bufferSize, long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean eagerTruncate) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return FlowableReplay.create(this, time, unit, scheduler, bufferSize, eagerTruncate);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Flowable<R> combineLatest(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Publisher<? extends T5> source5, @NonNull Publisher<? extends T6> source6, @NonNull Publisher<? extends T7> source7, @NonNull Publisher<? extends T8> source8, @NonNull Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> combiner) {
        Objects.requireNonNull(source1, "source1 is null");
        Objects.requireNonNull(source2, "source2 is null");
        Objects.requireNonNull(source3, "source3 is null");
        Objects.requireNonNull(source4, "source4 is null");
        Objects.requireNonNull(source5, "source5 is null");
        Objects.requireNonNull(source6, "source6 is null");
        Objects.requireNonNull(source7, "source7 is null");
        Objects.requireNonNull(source8, "source8 is null");
        Objects.requireNonNull(combiner, "combiner is null");
        return combineLatestArray(new Publisher[]{source1, source2, source3, source4, source5, source6, source7, source8}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8, T item9) {
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
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("io.reactivex:computation")
    public final ConnectableFlowable<T> replay(long time, @NonNull TimeUnit unit) {
        return replay(time, unit, Schedulers.computation());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, R> Flowable<R> zip(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Publisher<? extends T5> source5, @NonNull Publisher<? extends T6> source6, @NonNull Publisher<? extends T7> source7, @NonNull Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper) {
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
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final ConnectableFlowable<T> replay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.create(this, time, unit, scheduler, false);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final ConnectableFlowable<T> replay(long time, @NonNull TimeUnit unit, @NonNull Scheduler scheduler, boolean eagerTruncate) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.create(this, time, unit, scheduler, eagerTruncate);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Flowable<R> combineLatest(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Publisher<? extends T5> source5, @NonNull Publisher<? extends T6> source6, @NonNull Publisher<? extends T7> source7, @NonNull Publisher<? extends T8> source8, @NonNull Publisher<? extends T9> source9, @NonNull Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> combiner) {
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
        return combineLatestArray(new Publisher[]{source1, source2, source3, source4, source5, source6, source7, source8, source9}, Functions.toFunction(combiner), bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> Flowable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8, T item9, T item10) {
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
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Flowable<R> zip(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Publisher<? extends T5> source5, @NonNull Publisher<? extends T6> source6, @NonNull Publisher<? extends T7> source7, @NonNull Publisher<? extends T8> source8, @NonNull Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper) {
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
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Flowable<R> zip(@NonNull Publisher<? extends T1> source1, @NonNull Publisher<? extends T2> source2, @NonNull Publisher<? extends T3> source3, @NonNull Publisher<? extends T4> source4, @NonNull Publisher<? extends T5> source5, @NonNull Publisher<? extends T6> source6, @NonNull Publisher<? extends T7> source7, @NonNull Publisher<? extends T8> source8, @NonNull Publisher<? extends T9> source9, @NonNull Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper) {
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
