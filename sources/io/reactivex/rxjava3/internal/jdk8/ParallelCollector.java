package io.reactivex.rxjava3.internal.jdk8;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ParallelCollector<T, A, R> extends Flowable<R> {
    final Collector<T, A, R> collector;
    final ParallelFlowable<? extends T> source;

    public static final class ParallelCollectorInnerSubscriber<T, A, R> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -7954444275102466525L;
        final BiConsumer<A, T> accumulator;
        final BinaryOperator<A> combiner;
        A container;
        boolean done;
        final ParallelCollectorSubscriber<T, A, R> parent;

        public ParallelCollectorInnerSubscriber(ParallelCollectorSubscriber<T, A, R> parent, A container, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner) {
            this.parent = parent;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.container = container;
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            A a3 = this.container;
            this.container = null;
            this.done = true;
            this.parent.innerComplete(a3, this.combiner);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.container = null;
            this.done = true;
            this.parent.innerError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            try {
                this.accumulator.accept(this.container, t2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                get().cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.setOnce(this, s2, Long.MAX_VALUE);
        }
    }

    public static final class ParallelCollectorSubscriber<T, A, R> extends DeferredScalarSubscription<R> {
        private static final long serialVersionUID = -5370107872170712765L;
        final AtomicReference<SlotPair<A>> current;
        final AtomicThrowable error;
        final Function<A, R> finisher;
        final AtomicInteger remaining;
        final ParallelCollectorInnerSubscriber<T, A, R>[] subscribers;

        public ParallelCollectorSubscriber(Subscriber<? super R> subscriber, int n2, Collector<T, A, R> collector) {
            super(subscriber);
            this.current = new AtomicReference<>();
            this.remaining = new AtomicInteger();
            this.error = new AtomicThrowable();
            this.finisher = collector.finisher();
            ParallelCollectorInnerSubscriber<T, A, R>[] parallelCollectorInnerSubscriberArr = new ParallelCollectorInnerSubscriber[n2];
            for (int i2 = 0; i2 < n2; i2++) {
                parallelCollectorInnerSubscriberArr[i2] = new ParallelCollectorInnerSubscriber<>(this, collector.supplier().get(), collector.accumulator(), collector.combiner());
            }
            this.subscribers = parallelCollectorInnerSubscriberArr;
            this.remaining.lazySet(n2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public SlotPair<A> addValue(A value) {
            SlotPair<A> slotPair;
            int iTryAcquireSlot;
            while (true) {
                slotPair = this.current.get();
                if (slotPair == null) {
                    slotPair = new SlotPair<>();
                    if (!j.a(this.current, null, slotPair)) {
                        continue;
                    }
                }
                iTryAcquireSlot = slotPair.tryAcquireSlot();
                if (iTryAcquireSlot >= 0) {
                    break;
                }
                j.a(this.current, slotPair, null);
            }
            if (iTryAcquireSlot == 0) {
                slotPair.first = value;
            } else {
                slotPair.second = value;
            }
            if (!slotPair.releaseSlot()) {
                return null;
            }
            j.a(this.current, slotPair, null);
            return slotPair;
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            for (ParallelCollectorInnerSubscriber<T, A, R> parallelCollectorInnerSubscriber : this.subscribers) {
                parallelCollectorInnerSubscriber.cancel();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void innerComplete(A a3, BinaryOperator<A> binaryOperator) {
            while (true) {
                SlotPair slotPairAddValue = addValue(a3);
                if (slotPairAddValue == null) {
                    break;
                }
                try {
                    a3 = (A) binaryOperator.apply(slotPairAddValue.first, slotPairAddValue.second);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    innerError(th);
                    return;
                }
            }
            if (this.remaining.decrementAndGet() == 0) {
                SlotPair<A> slotPair = this.current.get();
                this.current.lazySet(null);
                try {
                    Object objApply = this.finisher.apply(slotPair.first);
                    Objects.requireNonNull(objApply, "The finisher returned a null value");
                    complete(objApply);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    innerError(th2);
                }
            }
        }

        public void innerError(Throwable ex) {
            if (this.error.compareAndSet(null, ex)) {
                cancel();
                this.downstream.onError(ex);
            } else if (ex != this.error.get()) {
                RxJavaPlugins.onError(ex);
            }
        }
    }

    public static final class SlotPair<T> extends AtomicInteger {
        private static final long serialVersionUID = 473971317683868662L;
        T first;
        final AtomicInteger releaseIndex = new AtomicInteger();
        T second;

        public boolean releaseSlot() {
            return this.releaseIndex.incrementAndGet() == 2;
        }

        public int tryAcquireSlot() {
            int i2;
            do {
                i2 = get();
                if (i2 >= 2) {
                    return -1;
                }
            } while (!compareAndSet(i2, i2 + 1));
            return i2;
        }
    }

    public ParallelCollector(ParallelFlowable<? extends T> source, Collector<T, A, R> collector) {
        this.source = source;
        this.collector = collector;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        try {
            ParallelCollectorSubscriber parallelCollectorSubscriber = new ParallelCollectorSubscriber(s2, this.source.parallelism(), this.collector);
            s2.onSubscribe(parallelCollectorSubscriber);
            this.source.subscribe(parallelCollectorSubscriber.subscribers);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, s2);
        }
    }
}
