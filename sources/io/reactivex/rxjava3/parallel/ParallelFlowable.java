package io.reactivex.rxjava3.parallel;

import io.reactivex.rxjava3.annotations.BackpressureKind;
import io.reactivex.rxjava3.annotations.BackpressureSupport;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.SchedulerSupport;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.LongConsumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.jdk8.ParallelCollector;
import io.reactivex.rxjava3.internal.jdk8.ParallelFlatMapStream;
import io.reactivex.rxjava3.internal.jdk8.ParallelMapOptional;
import io.reactivex.rxjava3.internal.jdk8.ParallelMapTryOptional;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelCollect;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelConcatMap;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelDoOnNextTry;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelFilter;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelFilterTry;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelFlatMap;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelFlatMapIterable;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelFromArray;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelFromPublisher;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelMap;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelMapTry;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelPeek;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelReduce;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelReduceFull;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelRunOn;
import io.reactivex.rxjava3.internal.operators.parallel.ParallelSortedJoin;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.internal.util.ListAddBiConsumer;
import io.reactivex.rxjava3.internal.util.MergerBiFunction;
import io.reactivex.rxjava3.internal.util.SorterFunction;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public abstract class ParallelFlowable<T> {
    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> ParallelFlowable<T> from(@NonNull Publisher<? extends T> source) {
        return from(source, Runtime.getRuntime().availableProcessors(), Flowable.bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SafeVarargs
    @SchedulerSupport("none")
    public static <T> ParallelFlowable<T> fromArray(@NonNull Publisher<T>... publishers) {
        Objects.requireNonNull(publishers, "publishers is null");
        if (publishers.length != 0) {
            return RxJavaPlugins.onAssembly(new ParallelFromArray(publishers));
        }
        throw new IllegalArgumentException("Zero publishers not supported");
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <C> ParallelFlowable<C> collect(@NonNull Supplier<? extends C> collectionSupplier, @NonNull BiConsumer<? super C, ? super T> collector) {
        Objects.requireNonNull(collectionSupplier, "collectionSupplier is null");
        Objects.requireNonNull(collector, "collector is null");
        return RxJavaPlugins.onAssembly(new ParallelCollect(this, collectionSupplier, collector));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <U> ParallelFlowable<U> compose(@NonNull ParallelTransformer<T, U> composer) {
        Objects.requireNonNull(composer, "composer is null");
        return RxJavaPlugins.onAssembly(composer.apply(this));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> concatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return concatMap(mapper, 2);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> concatMapDelayError(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, boolean tillTheEnd) {
        return concatMapDelayError(mapper, 2, tillTheEnd);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> doAfterNext(@NonNull Consumer<? super T> onAfterNext) {
        Objects.requireNonNull(onAfterNext, "onAfterNext is null");
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, consumerEmptyConsumer, onAfterNext, consumerEmptyConsumer2, action, action, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, action));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> doAfterTerminated(@NonNull Action onAfterTerminate) {
        Objects.requireNonNull(onAfterTerminate, "onAfterTerminate is null");
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer3 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, consumerEmptyConsumer, consumerEmptyConsumer2, consumerEmptyConsumer3, action, onAfterTerminate, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, action));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> doOnCancel(@NonNull Action onCancel) {
        Objects.requireNonNull(onCancel, "onCancel is null");
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer3 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, consumerEmptyConsumer, consumerEmptyConsumer2, consumerEmptyConsumer3, action, action, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, onCancel));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> doOnComplete(@NonNull Action onComplete) {
        Objects.requireNonNull(onComplete, "onComplete is null");
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer3 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, consumerEmptyConsumer, consumerEmptyConsumer2, consumerEmptyConsumer3, onComplete, action, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, action));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> doOnError(@NonNull Consumer<? super Throwable> onError) {
        Objects.requireNonNull(onError, "onError is null");
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, consumerEmptyConsumer, consumerEmptyConsumer2, onError, action, action, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, action));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> doOnNext(@NonNull Consumer<? super T> onNext) {
        Objects.requireNonNull(onNext, "onNext is null");
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, onNext, consumerEmptyConsumer, consumerEmptyConsumer2, action, action, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, action));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> doOnRequest(@NonNull LongConsumer onRequest) {
        Objects.requireNonNull(onRequest, "onRequest is null");
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer3 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, consumerEmptyConsumer, consumerEmptyConsumer2, consumerEmptyConsumer3, action, action, Functions.emptyConsumer(), onRequest, action));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> doOnSubscribe(@NonNull Consumer<? super Subscription> onSubscribe) {
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        Consumer consumerEmptyConsumer = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer2 = Functions.emptyConsumer();
        Consumer consumerEmptyConsumer3 = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, consumerEmptyConsumer, consumerEmptyConsumer2, consumerEmptyConsumer3, action, action, onSubscribe, Functions.EMPTY_LONG_CONSUMER, action));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> filter(@NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new ParallelFilter(this, predicate));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return flatMap(mapper, false, Flowable.bufferSize(), Flowable.bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> ParallelFlowable<U> flatMapIterable(@NonNull Function<? super T, ? extends Iterable<? extends U>> mapper) {
        return flatMapIterable(mapper, Flowable.bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> flatMapStream(@NonNull Function<? super T, ? extends Stream<? extends R>> mapper) {
        return flatMapStream(mapper, Flowable.bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> map(@NonNull Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ParallelMap(this, mapper));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> mapOptional(@NonNull Function<? super T, Optional<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return RxJavaPlugins.onAssembly(new ParallelMapOptional(this, mapper));
    }

    @CheckReturnValue
    public abstract int parallelism();

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<T> reduce(@NonNull BiFunction<T, T, T> reducer) {
        Objects.requireNonNull(reducer, "reducer is null");
        return RxJavaPlugins.onAssembly(new ParallelReduceFull(this, reducer));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final ParallelFlowable<T> runOn(@NonNull Scheduler scheduler) {
        return runOn(scheduler, Flowable.bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> sequential() {
        return sequential(Flowable.bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> sequentialDelayError() {
        return sequentialDelayError(Flowable.bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<T> sorted(@NonNull Comparator<? super T> comparator) {
        return sorted(comparator, 16);
    }

    @BackpressureSupport(BackpressureKind.SPECIAL)
    @SchedulerSupport("none")
    public abstract void subscribe(@NonNull Subscriber<? super T>[] subscribers);

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> R to(@NonNull ParallelFlowableConverter<T, R> converter) {
        Objects.requireNonNull(converter, "converter is null");
        return converter.apply(this);
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<List<T>> toSortedList(@NonNull Comparator<? super T> comparator) {
        return toSortedList(comparator, 16);
    }

    public final boolean validate(@NonNull Subscriber<?>[] subscribers) {
        Objects.requireNonNull(subscribers, "subscribers is null");
        int iParallelism = parallelism();
        if (subscribers.length == iParallelism) {
            return true;
        }
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("parallelism = " + iParallelism + ", subscribers = " + subscribers.length);
        int length = subscribers.length;
        for (int i2 = 0; i2 < length; i2++) {
            EmptySubscription.error(illegalArgumentException, subscribers[i2]);
        }
        return false;
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> ParallelFlowable<T> from(@NonNull Publisher<? extends T> source, int parallelism) {
        return from(source, parallelism, Flowable.bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> concatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelConcatMap(this, mapper, prefetch, ErrorMode.IMMEDIATE));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> concatMapDelayError(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch, boolean tillTheEnd) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelConcatMap(this, mapper, prefetch, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError) {
        return flatMap(mapper, delayError, Flowable.bufferSize(), Flowable.bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <U> ParallelFlowable<U> flatMapIterable(@NonNull Function<? super T, ? extends Iterable<? extends U>> mapper, int bufferSize) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ParallelFlatMapIterable(this, mapper, bufferSize));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> flatMapStream(@NonNull Function<? super T, ? extends Stream<? extends R>> mapper, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelFlatMapStream(this, mapper, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("custom")
    public final ParallelFlowable<T> runOn(@NonNull Scheduler scheduler, int prefetch) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelRunOn(this, scheduler, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> sequential(int prefetch) {
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelJoin(this, prefetch, false));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> sequentialDelayError(int prefetch) {
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelJoin(this, prefetch, true));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<T> sorted(@NonNull Comparator<? super T> comparator, int capacityHint) {
        Objects.requireNonNull(comparator, "comparator is null");
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return RxJavaPlugins.onAssembly(new ParallelSortedJoin(reduce(Functions.createArrayList((capacityHint / parallelism()) + 1), ListAddBiConsumer.instance()).map(new SorterFunction(comparator)), comparator));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final Flowable<List<T>> toSortedList(@NonNull Comparator<? super T> comparator, int capacityHint) {
        Objects.requireNonNull(comparator, "comparator is null");
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return RxJavaPlugins.onAssembly(reduce(Functions.createArrayList((capacityHint / parallelism()) + 1), ListAddBiConsumer.instance()).map(new SorterFunction(comparator)).reduce(new MergerBiFunction(comparator)));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public static <T> ParallelFlowable<T> from(@NonNull Publisher<? extends T> source, int parallelism, int prefetch) {
        Objects.requireNonNull(source, "source is null");
        ObjectHelper.verifyPositive(parallelism, "parallelism");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelFromPublisher(source, parallelism, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> filter(@NonNull Predicate<? super T> predicate, @NonNull ParallelFailureHandling errorHandler) {
        Objects.requireNonNull(predicate, "predicate is null");
        Objects.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelFilterTry(this, predicate, errorHandler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError, int maxConcurrency) {
        return flatMap(mapper, delayError, maxConcurrency, Flowable.bufferSize());
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> map(@NonNull Function<? super T, ? extends R> mapper, @NonNull ParallelFailureHandling errorHandler) {
        Objects.requireNonNull(mapper, "mapper is null");
        Objects.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelMapTry(this, mapper, errorHandler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> mapOptional(@NonNull Function<? super T, Optional<? extends R>> mapper, @NonNull ParallelFailureHandling errorHandler) {
        Objects.requireNonNull(mapper, "mapper is null");
        Objects.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelMapTryOptional(this, mapper, errorHandler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> reduce(@NonNull Supplier<R> initialSupplier, @NonNull BiFunction<R, ? super T, R> reducer) {
        Objects.requireNonNull(initialSupplier, "initialSupplier is null");
        Objects.requireNonNull(reducer, "reducer is null");
        return RxJavaPlugins.onAssembly(new ParallelReduce(this, initialSupplier, reducer));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @SchedulerSupport("none")
    public final <A, R> Flowable<R> collect(@NonNull Collector<T, A, R> collector) {
        Objects.requireNonNull(collector, "collector is null");
        return RxJavaPlugins.onAssembly(new ParallelCollector(this, collector));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError, int maxConcurrency, int prefetch) {
        Objects.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelFlatMap(this, mapper, delayError, maxConcurrency, prefetch));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> filter(@NonNull Predicate<? super T> predicate, @NonNull BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler) {
        Objects.requireNonNull(predicate, "predicate is null");
        Objects.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelFilterTry(this, predicate, errorHandler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> map(@NonNull Function<? super T, ? extends R> mapper, @NonNull BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler) {
        Objects.requireNonNull(mapper, "mapper is null");
        Objects.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelMapTry(this, mapper, errorHandler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final <R> ParallelFlowable<R> mapOptional(@NonNull Function<? super T, Optional<? extends R>> mapper, @NonNull BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler) {
        Objects.requireNonNull(mapper, "mapper is null");
        Objects.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelMapTryOptional(this, mapper, errorHandler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> doOnNext(@NonNull Consumer<? super T> onNext, @NonNull ParallelFailureHandling errorHandler) {
        Objects.requireNonNull(onNext, "onNext is null");
        Objects.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelDoOnNextTry(this, onNext, errorHandler));
    }

    @CheckReturnValue
    @NonNull
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final ParallelFlowable<T> doOnNext(@NonNull Consumer<? super T> onNext, @NonNull BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler) {
        Objects.requireNonNull(onNext, "onNext is null");
        Objects.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelDoOnNextTry(this, onNext, errorHandler));
    }
}
