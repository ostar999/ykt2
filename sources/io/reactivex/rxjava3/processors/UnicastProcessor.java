package io.reactivex.rxjava3.processors;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
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
    boolean enableOperatorFusion;
    Throwable error;
    final AtomicReference<Runnable> onTerminate;
    final SpscLinkedArrayQueue<T> queue;
    final AtomicReference<Subscriber<? super T>> downstream = new AtomicReference<>();
    final AtomicBoolean once = new AtomicBoolean();
    final BasicIntQueueSubscription<T> wip = new UnicastQueueSubscription();
    final AtomicLong requested = new AtomicLong();

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
            UnicastProcessor.this.downstream.lazySet(null);
            if (UnicastProcessor.this.wip.getAndIncrement() == 0) {
                UnicastProcessor.this.downstream.lazySet(null);
                UnicastProcessor unicastProcessor = UnicastProcessor.this;
                if (unicastProcessor.enableOperatorFusion) {
                    return;
                }
                unicastProcessor.queue.clear();
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            UnicastProcessor.this.queue.clear();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return UnicastProcessor.this.queue.isEmpty();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            return UnicastProcessor.this.queue.poll();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(UnicastProcessor.this.requested, n2);
                UnicastProcessor.this.drain();
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 2) == 0) {
                return 0;
            }
            UnicastProcessor.this.enableOperatorFusion = true;
            return 2;
        }
    }

    public UnicastProcessor(int capacityHint, Runnable onTerminate, boolean delayError) {
        this.queue = new SpscLinkedArrayQueue<>(capacityHint);
        this.onTerminate = new AtomicReference<>(onTerminate);
        this.delayError = delayError;
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create() {
        return new UnicastProcessor<>(Flowable.bufferSize(), null, true);
    }

    public boolean checkTerminated(boolean failFast, boolean d3, boolean empty, Subscriber<? super T> a3, SpscLinkedArrayQueue<T> q2) {
        if (this.cancelled) {
            q2.clear();
            this.downstream.lazySet(null);
            return true;
        }
        if (!d3) {
            return false;
        }
        if (failFast && this.error != null) {
            q2.clear();
            this.downstream.lazySet(null);
            a3.onError(this.error);
            return true;
        }
        if (!empty) {
            return false;
        }
        Throwable th = this.error;
        this.downstream.lazySet(null);
        if (th != null) {
            a3.onError(th);
        } else {
            a3.onComplete();
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

    public void drainFused(Subscriber<? super T> a3) {
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
        int iAddAndGet = 1;
        boolean z2 = !this.delayError;
        while (!this.cancelled) {
            boolean z3 = this.done;
            if (z2 && z3 && this.error != null) {
                spscLinkedArrayQueue.clear();
                this.downstream.lazySet(null);
                a3.onError(this.error);
                return;
            }
            a3.onNext(null);
            if (z3) {
                this.downstream.lazySet(null);
                Throwable th = this.error;
                if (th != null) {
                    a3.onError(th);
                    return;
                } else {
                    a3.onComplete();
                    return;
                }
            }
            iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            }
        }
        this.downstream.lazySet(null);
    }

    public void drainRegular(Subscriber<? super T> a3) {
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
                if (checkTerminated(z3, z4, z5, a3, spscLinkedArrayQueue)) {
                    return;
                }
                if (z5) {
                    break;
                }
                a3.onNext(tPoll);
                j4 = 1 + j2;
                z2 = true;
            }
            if (j3 == j4 && checkTerminated(z3, this.done, spscLinkedArrayQueue.isEmpty(), a3, spscLinkedArrayQueue)) {
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

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    @Nullable
    public Throwable getThrowable() {
        if (this.done) {
            return this.error;
        }
        return null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasComplete() {
        return this.done && this.error == null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasSubscribers() {
        return this.downstream.get() != null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
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
    public void onError(Throwable t2) {
        ExceptionHelper.nullCheck(t2, "onError called with a null Throwable.");
        if (this.done || this.cancelled) {
            RxJavaPlugins.onError(t2);
            return;
        }
        this.error = t2;
        this.done = true;
        doTerminate();
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        ExceptionHelper.nullCheck(t2, "onNext called with a null value.");
        if (this.done || this.cancelled) {
            return;
        }
        this.queue.offer(t2);
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription s2) {
        if (this.done || this.cancelled) {
            s2.cancel();
        } else {
            s2.request(Long.MAX_VALUE);
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        if (this.once.get() || !this.once.compareAndSet(false, true)) {
            EmptySubscription.error(new IllegalStateException("This processor allows only a single Subscriber"), s2);
            return;
        }
        s2.onSubscribe(this.wip);
        this.downstream.set(s2);
        if (this.cancelled) {
            this.downstream.lazySet(null);
        } else {
            drain();
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int capacityHint) {
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return new UnicastProcessor<>(capacityHint, null, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(boolean delayError) {
        return new UnicastProcessor<>(Flowable.bufferSize(), null, delayError);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int capacityHint, @NonNull Runnable onTerminate) {
        return create(capacityHint, onTerminate, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastProcessor<T> create(int capacityHint, @NonNull Runnable onTerminate, boolean delayError) {
        Objects.requireNonNull(onTerminate, "onTerminate");
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return new UnicastProcessor<>(capacityHint, onTerminate, delayError);
    }
}
