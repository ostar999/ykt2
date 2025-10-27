package io.reactivex.processors;

import io.reactivex.Flowable;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class UnicastProcessor<T> extends FlowableProcessor<T> {
    volatile boolean cancelled;
    final boolean delayError;
    volatile boolean done;
    final AtomicReference<Subscriber<? super T>> downstream;
    boolean enableOperatorFusion;
    Throwable error;
    final AtomicReference<Runnable> onTerminate;
    final AtomicBoolean once;
    final SpscLinkedArrayQueue<T> queue;
    final AtomicLong requested;
    final BasicIntQueueSubscription<T> wip;

    public final class UnicastQueueSubscription extends BasicIntQueueSubscription<T> {
        private static final long serialVersionUID = -4896760517184205454L;

        public UnicastQueueSubscription() {
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (UnicastProcessor.this.cancelled) {
                return;
            }
            UnicastProcessor.this.cancelled = true;
            UnicastProcessor.this.doTerminate();
            UnicastProcessor unicastProcessor = UnicastProcessor.this;
            if (unicastProcessor.enableOperatorFusion || unicastProcessor.wip.getAndIncrement() != 0) {
                return;
            }
            UnicastProcessor.this.queue.clear();
            UnicastProcessor.this.downstream.lazySet(null);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            UnicastProcessor.this.queue.clear();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return UnicastProcessor.this.queue.isEmpty();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            return UnicastProcessor.this.queue.poll();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(UnicastProcessor.this.requested, j2);
                UnicastProcessor.this.drain();
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i2) {
            if ((i2 & 2) == 0) {
                return 0;
            }
            UnicastProcessor.this.enableOperatorFusion = true;
            return 2;
        }
    }

    public UnicastProcessor(int i2) {
        this(i2, null, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create() {
        return new UnicastProcessor<>(Flowable.bufferSize());
    }

    public boolean checkTerminated(boolean z2, boolean z3, boolean z4, Subscriber<? super T> subscriber, SpscLinkedArrayQueue<T> spscLinkedArrayQueue) {
        if (this.cancelled) {
            spscLinkedArrayQueue.clear();
            this.downstream.lazySet(null);
            return true;
        }
        if (!z3) {
            return false;
        }
        if (z2 && this.error != null) {
            spscLinkedArrayQueue.clear();
            this.downstream.lazySet(null);
            subscriber.onError(this.error);
            return true;
        }
        if (!z4) {
            return false;
        }
        Throwable th = this.error;
        this.downstream.lazySet(null);
        if (th != null) {
            subscriber.onError(th);
        } else {
            subscriber.onComplete();
        }
        return true;
    }

    public void doTerminate() {
        Runnable andSet = this.onTerminate.getAndSet(null);
        if (andSet != null) {
            andSet.run();
        }
    }

    public void drain() {
        if (this.wip.getAndIncrement() != 0) {
            return;
        }
        Subscriber<? super T> subscriber = this.downstream.get();
        int iAddAndGet = 1;
        while (subscriber == null) {
            iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            } else {
                subscriber = this.downstream.get();
            }
        }
        if (this.enableOperatorFusion) {
            drainFused(subscriber);
        } else {
            drainRegular(subscriber);
        }
    }

    public void drainFused(Subscriber<? super T> subscriber) {
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
        int iAddAndGet = 1;
        boolean z2 = !this.delayError;
        while (!this.cancelled) {
            boolean z3 = this.done;
            if (z2 && z3 && this.error != null) {
                spscLinkedArrayQueue.clear();
                this.downstream.lazySet(null);
                subscriber.onError(this.error);
                return;
            }
            subscriber.onNext(null);
            if (z3) {
                this.downstream.lazySet(null);
                Throwable th = this.error;
                if (th != null) {
                    subscriber.onError(th);
                    return;
                } else {
                    subscriber.onComplete();
                    return;
                }
            }
            iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            }
        }
        spscLinkedArrayQueue.clear();
        this.downstream.lazySet(null);
    }

    public void drainRegular(Subscriber<? super T> subscriber) {
        long j2;
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
        boolean z2 = true;
        boolean z3 = !this.delayError;
        int iAddAndGet = 1;
        while (true) {
            long j3 = this.requested.get();
            long j4 = 0;
            while (true) {
                if (j3 == j4) {
                    j2 = j4;
                    break;
                }
                boolean z4 = this.done;
                T tPoll = spscLinkedArrayQueue.poll();
                boolean z5 = tPoll == null ? z2 : false;
                j2 = j4;
                if (checkTerminated(z3, z4, z5, subscriber, spscLinkedArrayQueue)) {
                    return;
                }
                if (z5) {
                    break;
                }
                subscriber.onNext(tPoll);
                j4 = 1 + j2;
                z2 = true;
            }
            if (j3 == j4 && checkTerminated(z3, this.done, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                return;
            }
            if (j2 != 0 && j3 != Long.MAX_VALUE) {
                this.requested.addAndGet(-j2);
            }
            iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            } else {
                z2 = true;
            }
        }
    }

    @Override // io.reactivex.processors.FlowableProcessor
    @Nullable
    public Throwable getThrowable() {
        if (this.done) {
            return this.error;
        }
        return null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasComplete() {
        return this.done && this.error == null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasSubscribers() {
        return this.downstream.get() != null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasThrowable() {
        return this.done && this.error != null;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.done || this.cancelled) {
            return;
        }
        this.done = true;
        doTerminate();
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.done || this.cancelled) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.error = th;
        this.done = true;
        doTerminate();
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        ObjectHelper.requireNonNull(t2, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.done || this.cancelled) {
            return;
        }
        this.queue.offer(t2);
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (this.done || this.cancelled) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (this.once.get() || !this.once.compareAndSet(false, true)) {
            EmptySubscription.error(new IllegalStateException("This processor allows only a single Subscriber"), subscriber);
            return;
        }
        subscriber.onSubscribe(this.wip);
        this.downstream.set(subscriber);
        if (this.cancelled) {
            this.downstream.lazySet(null);
        } else {
            drain();
        }
    }

    public UnicastProcessor(int i2, Runnable runnable) {
        this(i2, runnable, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int i2) {
        return new UnicastProcessor<>(i2);
    }

    public UnicastProcessor(int i2, Runnable runnable, boolean z2) {
        this.queue = new SpscLinkedArrayQueue<>(ObjectHelper.verifyPositive(i2, "capacityHint"));
        this.onTerminate = new AtomicReference<>(runnable);
        this.delayError = z2;
        this.downstream = new AtomicReference<>();
        this.once = new AtomicBoolean();
        this.wip = new UnicastQueueSubscription();
        this.requested = new AtomicLong();
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(boolean z2) {
        return new UnicastProcessor<>(Flowable.bufferSize(), null, z2);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int i2, Runnable runnable) {
        ObjectHelper.requireNonNull(runnable, "onTerminate");
        return new UnicastProcessor<>(i2, runnable);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int i2, Runnable runnable, boolean z2) {
        ObjectHelper.requireNonNull(runnable, "onTerminate");
        return new UnicastProcessor<>(i2, runnable, z2);
    }
}
