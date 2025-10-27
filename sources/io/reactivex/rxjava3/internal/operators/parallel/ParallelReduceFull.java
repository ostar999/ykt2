package io.reactivex.rxjava3.internal.operators.parallel;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ParallelReduceFull<T> extends Flowable<T> {
    final BiFunction<T, T, T> reducer;
    final ParallelFlowable<? extends T> source;

    public static final class ParallelReduceFullInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -7954444275102466525L;
        boolean done;
        final ParallelReduceFullMainSubscriber<T> parent;
        final BiFunction<T, T, T> reducer;
        T value;

        public ParallelReduceFullInnerSubscriber(ParallelReduceFullMainSubscriber<T> parent, BiFunction<T, T, T> reducer) {
            this.parent = parent;
            this.reducer = reducer;
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.innerComplete(this.value);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
            } else {
                this.done = true;
                this.parent.innerError(t2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            T t3 = this.value;
            if (t3 == null) {
                this.value = t2;
                return;
            }
            try {
                T tApply = this.reducer.apply(t3, t2);
                Objects.requireNonNull(tApply, "The reducer returned a null value");
                this.value = tApply;
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

    public static final class ParallelReduceFullMainSubscriber<T> extends DeferredScalarSubscription<T> {
        private static final long serialVersionUID = -5370107872170712765L;
        final AtomicReference<SlotPair<T>> current;
        final AtomicThrowable error;
        final BiFunction<T, T, T> reducer;
        final AtomicInteger remaining;
        final ParallelReduceFullInnerSubscriber<T>[] subscribers;

        public ParallelReduceFullMainSubscriber(Subscriber<? super T> subscriber, int n2, BiFunction<T, T, T> reducer) {
            super(subscriber);
            this.current = new AtomicReference<>();
            this.remaining = new AtomicInteger();
            this.error = new AtomicThrowable();
            ParallelReduceFullInnerSubscriber<T>[] parallelReduceFullInnerSubscriberArr = new ParallelReduceFullInnerSubscriber[n2];
            for (int i2 = 0; i2 < n2; i2++) {
                parallelReduceFullInnerSubscriberArr[i2] = new ParallelReduceFullInnerSubscriber<>(this, reducer);
            }
            this.subscribers = parallelReduceFullInnerSubscriberArr;
            this.reducer = reducer;
            this.remaining.lazySet(n2);
        }

        public SlotPair<T> addValue(T value) {
            SlotPair<T> slotPair;
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
            for (ParallelReduceFullInnerSubscriber<T> parallelReduceFullInnerSubscriber : this.subscribers) {
                parallelReduceFullInnerSubscriber.cancel();
            }
        }

        public void innerComplete(T value) {
            if (value != null) {
                while (true) {
                    SlotPair<T> slotPairAddValue = addValue(value);
                    if (slotPairAddValue == null) {
                        break;
                    }
                    try {
                        value = this.reducer.apply(slotPairAddValue.first, slotPairAddValue.second);
                        Objects.requireNonNull(value, "The reducer returned a null value");
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        innerError(th);
                        return;
                    }
                }
            }
            if (this.remaining.decrementAndGet() == 0) {
                SlotPair<T> slotPair = this.current.get();
                this.current.lazySet(null);
                if (slotPair != null) {
                    complete(slotPair.first);
                } else {
                    this.downstream.onComplete();
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

    public ParallelReduceFull(ParallelFlowable<? extends T> source, BiFunction<T, T, T> reducer) {
        this.source = source;
        this.reducer = reducer;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        ParallelReduceFullMainSubscriber parallelReduceFullMainSubscriber = new ParallelReduceFullMainSubscriber(s2, this.source.parallelism(), this.reducer);
        s2.onSubscribe(parallelReduceFullMainSubscriber);
        this.source.subscribe(parallelReduceFullMainSubscriber.subscribers);
    }
}
