package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.functions.Functions;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableInternalHelper {

    public static final class BufferedReplaySupplier<T> implements Supplier<ConnectableFlowable<T>> {
        final int bufferSize;
        final boolean eagerTruncate;
        final Flowable<T> parent;

        public BufferedReplaySupplier(Flowable<T> parent, int bufferSize, boolean eagerTruncate) {
            this.parent = parent;
            this.bufferSize = bufferSize;
            this.eagerTruncate = eagerTruncate;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableFlowable<T> get() {
            return this.parent.replay(this.bufferSize, this.eagerTruncate);
        }
    }

    public static final class BufferedTimedReplay<T> implements Supplier<ConnectableFlowable<T>> {
        final int bufferSize;
        final boolean eagerTruncate;
        final Flowable<T> parent;
        final Scheduler scheduler;
        final long time;
        final TimeUnit unit;

        public BufferedTimedReplay(Flowable<T> parent, int bufferSize, long time, TimeUnit unit, Scheduler scheduler, boolean eagerTruncate) {
            this.parent = parent;
            this.bufferSize = bufferSize;
            this.time = time;
            this.unit = unit;
            this.scheduler = scheduler;
            this.eagerTruncate = eagerTruncate;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableFlowable<T> get() {
            return this.parent.replay(this.bufferSize, this.time, this.unit, this.scheduler, this.eagerTruncate);
        }
    }

    public static final class FlatMapIntoIterable<T, U> implements Function<T, Publisher<U>> {
        private final Function<? super T, ? extends Iterable<? extends U>> mapper;

        public FlatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> mapper) {
            this.mapper = mapper;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.Function
        public /* bridge */ /* synthetic */ Object apply(Object t2) throws Throwable {
            return apply((FlatMapIntoIterable<T, U>) t2);
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public Publisher<U> apply(T t2) throws Throwable {
            Iterable<? extends U> iterableApply = this.mapper.apply(t2);
            Objects.requireNonNull(iterableApply, "The mapper returned a null Iterable");
            return new FlowableFromIterable(iterableApply);
        }
    }

    public static final class FlatMapWithCombinerInner<U, R, T> implements Function<U, R> {
        private final BiFunction<? super T, ? super U, ? extends R> combiner;

        /* renamed from: t, reason: collision with root package name */
        private final T f27344t;

        public FlatMapWithCombinerInner(BiFunction<? super T, ? super U, ? extends R> combiner, T t2) {
            this.combiner = combiner;
            this.f27344t = t2;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(U u2) throws Throwable {
            return this.combiner.apply(this.f27344t, u2);
        }
    }

    public static final class FlatMapWithCombinerOuter<T, R, U> implements Function<T, Publisher<R>> {
        private final BiFunction<? super T, ? super U, ? extends R> combiner;
        private final Function<? super T, ? extends Publisher<? extends U>> mapper;

        public FlatMapWithCombinerOuter(BiFunction<? super T, ? super U, ? extends R> combiner, Function<? super T, ? extends Publisher<? extends U>> mapper) {
            this.combiner = combiner;
            this.mapper = mapper;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.Function
        public /* bridge */ /* synthetic */ Object apply(final Object t2) throws Throwable {
            return apply((FlatMapWithCombinerOuter<T, R, U>) t2);
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public Publisher<R> apply(final T t2) throws Throwable {
            Publisher<? extends U> publisherApply = this.mapper.apply(t2);
            Objects.requireNonNull(publisherApply, "The mapper returned a null Publisher");
            return new FlowableMapPublisher(publisherApply, new FlatMapWithCombinerInner(this.combiner, t2));
        }
    }

    public static final class ItemDelayFunction<T, U> implements Function<T, Publisher<T>> {
        final Function<? super T, ? extends Publisher<U>> itemDelay;

        public ItemDelayFunction(Function<? super T, ? extends Publisher<U>> itemDelay) {
            this.itemDelay = itemDelay;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.Function
        public /* bridge */ /* synthetic */ Object apply(final Object v2) throws Throwable {
            return apply((ItemDelayFunction<T, U>) v2);
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public Publisher<T> apply(final T v2) throws Throwable {
            Publisher<U> publisherApply = this.itemDelay.apply(v2);
            Objects.requireNonNull(publisherApply, "The itemDelay returned a null Publisher");
            return new FlowableTakePublisher(publisherApply, 1L).map(Functions.justFunction(v2)).defaultIfEmpty(v2);
        }
    }

    public static final class ReplaySupplier<T> implements Supplier<ConnectableFlowable<T>> {
        final Flowable<T> parent;

        public ReplaySupplier(Flowable<T> parent) {
            this.parent = parent;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableFlowable<T> get() {
            return this.parent.replay();
        }
    }

    public enum RequestMax implements Consumer<Subscription> {
        INSTANCE;

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Subscription t2) {
            t2.request(Long.MAX_VALUE);
        }
    }

    public static final class SimpleBiGenerator<T, S> implements BiFunction<S, Emitter<T>, S> {
        final BiConsumer<S, Emitter<T>> consumer;

        public SimpleBiGenerator(BiConsumer<S, Emitter<T>> consumer) {
            this.consumer = consumer;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.BiFunction
        public /* bridge */ /* synthetic */ Object apply(Object t12, Object t2) throws Throwable {
            return apply((SimpleBiGenerator<T, S>) t12, (Emitter) t2);
        }

        public S apply(S t12, Emitter<T> t2) throws Throwable {
            this.consumer.accept(t12, t2);
            return t12;
        }
    }

    public static final class SimpleGenerator<T, S> implements BiFunction<S, Emitter<T>, S> {
        final Consumer<Emitter<T>> consumer;

        public SimpleGenerator(Consumer<Emitter<T>> consumer) {
            this.consumer = consumer;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.BiFunction
        public /* bridge */ /* synthetic */ Object apply(Object t12, Object t2) throws Throwable {
            return apply((SimpleGenerator<T, S>) t12, (Emitter) t2);
        }

        public S apply(S t12, Emitter<T> t2) throws Throwable {
            this.consumer.accept(t2);
            return t12;
        }
    }

    public static final class SubscriberOnComplete<T> implements Action {
        final Subscriber<T> subscriber;

        public SubscriberOnComplete(Subscriber<T> subscriber) {
            this.subscriber = subscriber;
        }

        @Override // io.reactivex.rxjava3.functions.Action
        public void run() {
            this.subscriber.onComplete();
        }
    }

    public static final class SubscriberOnError<T> implements Consumer<Throwable> {
        final Subscriber<T> subscriber;

        public SubscriberOnError(Subscriber<T> subscriber) {
            this.subscriber = subscriber;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Throwable v2) {
            this.subscriber.onError(v2);
        }
    }

    public static final class SubscriberOnNext<T> implements Consumer<T> {
        final Subscriber<T> subscriber;

        public SubscriberOnNext(Subscriber<T> subscriber) {
            this.subscriber = subscriber;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(T v2) {
            this.subscriber.onNext(v2);
        }
    }

    public static final class TimedReplay<T> implements Supplier<ConnectableFlowable<T>> {
        final boolean eagerTruncate;
        private final Flowable<T> parent;
        private final Scheduler scheduler;
        private final long time;
        private final TimeUnit unit;

        public TimedReplay(Flowable<T> parent, long time, TimeUnit unit, Scheduler scheduler, boolean eagerTruncate) {
            this.parent = parent;
            this.time = time;
            this.unit = unit;
            this.scheduler = scheduler;
            this.eagerTruncate = eagerTruncate;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableFlowable<T> get() {
            return this.parent.replay(this.time, this.unit, this.scheduler, this.eagerTruncate);
        }
    }

    private FlowableInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, U> Function<T, Publisher<U>> flatMapIntoIterable(final Function<? super T, ? extends Iterable<? extends U>> mapper) {
        return new FlatMapIntoIterable(mapper);
    }

    public static <T, U, R> Function<T, Publisher<R>> flatMapWithCombiner(final Function<? super T, ? extends Publisher<? extends U>> mapper, final BiFunction<? super T, ? super U, ? extends R> combiner) {
        return new FlatMapWithCombinerOuter(combiner, mapper);
    }

    public static <T, U> Function<T, Publisher<T>> itemDelay(final Function<? super T, ? extends Publisher<U>> itemDelay) {
        return new ItemDelayFunction(itemDelay);
    }

    public static <T> Supplier<ConnectableFlowable<T>> replaySupplier(final Flowable<T> parent) {
        return new ReplaySupplier(parent);
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> consumer) {
        return new SimpleBiGenerator(consumer);
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> consumer) {
        return new SimpleGenerator(consumer);
    }

    public static <T> Action subscriberOnComplete(Subscriber<T> subscriber) {
        return new SubscriberOnComplete(subscriber);
    }

    public static <T> Consumer<Throwable> subscriberOnError(Subscriber<T> subscriber) {
        return new SubscriberOnError(subscriber);
    }

    public static <T> Consumer<T> subscriberOnNext(Subscriber<T> subscriber) {
        return new SubscriberOnNext(subscriber);
    }

    public static <T> Supplier<ConnectableFlowable<T>> replaySupplier(final Flowable<T> parent, final int bufferSize, boolean eagerTruncate) {
        return new BufferedReplaySupplier(parent, bufferSize, eagerTruncate);
    }

    public static <T> Supplier<ConnectableFlowable<T>> replaySupplier(final Flowable<T> parent, final int bufferSize, final long time, final TimeUnit unit, final Scheduler scheduler, boolean eagerTruncate) {
        return new BufferedTimedReplay(parent, bufferSize, time, unit, scheduler, eagerTruncate);
    }

    public static <T> Supplier<ConnectableFlowable<T>> replaySupplier(final Flowable<T> parent, final long time, final TimeUnit unit, final Scheduler scheduler, boolean eagerTruncate) {
        return new TimedReplay(parent, time, unit, scheduler, eagerTruncate);
    }
}
