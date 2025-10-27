package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableObserveOn<T> extends AbstractFlowableWithUpstream<T, T> {
    final boolean delayError;
    final int prefetch;
    final Scheduler scheduler;

    public static abstract class BaseObserveOnSubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T>, Runnable {
        private static final long serialVersionUID = -8241002408341274697L;
        volatile boolean cancelled;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final int limit;
        boolean outputFused;
        final int prefetch;
        long produced;
        SimpleQueue<T> queue;
        final AtomicLong requested = new AtomicLong();
        int sourceMode;
        Subscription upstream;
        final Scheduler.Worker worker;

        public BaseObserveOnSubscriber(Scheduler.Worker worker, boolean delayError, int prefetch) {
            this.worker = worker;
            this.delayError = delayError;
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            this.worker.dispose();
            if (this.outputFused || getAndIncrement() != 0) {
                return;
            }
            this.queue.clear();
        }

        final boolean checkTerminated(boolean d3, boolean empty, Subscriber<?> a3) {
            if (this.cancelled) {
                clear();
                return true;
            }
            if (!d3) {
                return false;
            }
            if (this.delayError) {
                if (!empty) {
                    return false;
                }
                this.cancelled = true;
                Throwable th = this.error;
                if (th != null) {
                    a3.onError(th);
                } else {
                    a3.onComplete();
                }
                this.worker.dispose();
                return true;
            }
            Throwable th2 = this.error;
            if (th2 != null) {
                this.cancelled = true;
                clear();
                a3.onError(th2);
                this.worker.dispose();
                return true;
            }
            if (!empty) {
                return false;
            }
            this.cancelled = true;
            a3.onComplete();
            this.worker.dispose();
            return true;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public final void clear() {
            this.queue.clear();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public final boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            trySchedule();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.error = t2;
            this.done = true;
            trySchedule();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t2) {
            if (this.done) {
                return;
            }
            if (this.sourceMode == 2) {
                trySchedule();
                return;
            }
            if (!this.queue.offer(t2)) {
                this.upstream.cancel();
                this.error = new MissingBackpressureException("Queue is full?!");
                this.done = true;
            }
            trySchedule();
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                trySchedule();
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public final int requestFusion(int requestedMode) {
            if ((requestedMode & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.outputFused) {
                runBackfused();
            } else if (this.sourceMode == 1) {
                runSync();
            } else {
                runAsync();
            }
        }

        abstract void runAsync();

        abstract void runBackfused();

        abstract void runSync();

        final void trySchedule() {
            if (getAndIncrement() != 0) {
                return;
            }
            this.worker.schedule(this);
        }
    }

    public static final class ObserveOnConditionalSubscriber<T> extends BaseObserveOnSubscriber<T> {
        private static final long serialVersionUID = 644624475404284533L;
        long consumed;
        final ConditionalSubscriber<? super T> downstream;

        public ObserveOnConditionalSubscriber(ConditionalSubscriber<? super T> actual, Scheduler.Worker worker, boolean delayError, int prefetch) {
            super(worker, delayError, prefetch);
            this.downstream = actual;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                if (s2 instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s2;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = 1;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = 2;
                        this.queue = queueSubscription;
                        this.downstream.onSubscribe(this);
                        s2.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                this.downstream.onSubscribe(this);
                s2.request(this.prefetch);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T tPoll = this.queue.poll();
            if (tPoll != null && this.sourceMode != 1) {
                long j2 = this.consumed + 1;
                if (j2 == this.limit) {
                    this.consumed = 0L;
                    this.upstream.request(j2);
                } else {
                    this.consumed = j2;
                }
            }
            return tPoll;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.BaseObserveOnSubscriber
        public void runAsync() {
            ConditionalSubscriber<? super T> conditionalSubscriber = this.downstream;
            SimpleQueue<T> simpleQueue = this.queue;
            long j2 = this.produced;
            long j3 = this.consumed;
            int iAddAndGet = 1;
            do {
                long j4 = this.requested.get();
                while (j2 != j4) {
                    boolean z2 = this.done;
                    try {
                        T tPoll = simpleQueue.poll();
                        boolean z3 = tPoll == null;
                        if (checkTerminated(z2, z3, conditionalSubscriber)) {
                            return;
                        }
                        if (z3) {
                            break;
                        }
                        if (conditionalSubscriber.tryOnNext(tPoll)) {
                            j2++;
                        }
                        j3++;
                        if (j3 == this.limit) {
                            this.upstream.request(j3);
                            j3 = 0;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.cancelled = true;
                        this.upstream.cancel();
                        simpleQueue.clear();
                        conditionalSubscriber.onError(th);
                        this.worker.dispose();
                        return;
                    }
                }
                if (j2 == j4 && checkTerminated(this.done, simpleQueue.isEmpty(), conditionalSubscriber)) {
                    return;
                }
                this.produced = j2;
                this.consumed = j3;
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.BaseObserveOnSubscriber
        public void runBackfused() {
            int iAddAndGet = 1;
            while (!this.cancelled) {
                boolean z2 = this.done;
                this.downstream.onNext(null);
                if (z2) {
                    this.cancelled = true;
                    Throwable th = this.error;
                    if (th != null) {
                        this.downstream.onError(th);
                    } else {
                        this.downstream.onComplete();
                    }
                    this.worker.dispose();
                    return;
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.BaseObserveOnSubscriber
        public void runSync() {
            ConditionalSubscriber<? super T> conditionalSubscriber = this.downstream;
            SimpleQueue<T> simpleQueue = this.queue;
            long j2 = this.produced;
            int iAddAndGet = 1;
            do {
                long j3 = this.requested.get();
                while (j2 != j3) {
                    try {
                        T tPoll = simpleQueue.poll();
                        if (this.cancelled) {
                            return;
                        }
                        if (tPoll == null) {
                            this.cancelled = true;
                            conditionalSubscriber.onComplete();
                            this.worker.dispose();
                            return;
                        } else if (conditionalSubscriber.tryOnNext(tPoll)) {
                            j2++;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.cancelled = true;
                        this.upstream.cancel();
                        conditionalSubscriber.onError(th);
                        this.worker.dispose();
                        return;
                    }
                }
                if (this.cancelled) {
                    return;
                }
                if (simpleQueue.isEmpty()) {
                    this.cancelled = true;
                    conditionalSubscriber.onComplete();
                    this.worker.dispose();
                    return;
                }
                this.produced = j2;
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }
    }

    public static final class ObserveOnSubscriber<T> extends BaseObserveOnSubscriber<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -4547113800637756442L;
        final Subscriber<? super T> downstream;

        public ObserveOnSubscriber(Subscriber<? super T> actual, Scheduler.Worker worker, boolean delayError, int prefetch) {
            super(worker, delayError, prefetch);
            this.downstream = actual;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                if (s2 instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s2;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = 1;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = 2;
                        this.queue = queueSubscription;
                        this.downstream.onSubscribe(this);
                        s2.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                this.downstream.onSubscribe(this);
                s2.request(this.prefetch);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T tPoll = this.queue.poll();
            if (tPoll != null && this.sourceMode != 1) {
                long j2 = this.produced + 1;
                if (j2 == this.limit) {
                    this.produced = 0L;
                    this.upstream.request(j2);
                } else {
                    this.produced = j2;
                }
            }
            return tPoll;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.BaseObserveOnSubscriber
        public void runAsync() {
            Subscriber<? super T> subscriber = this.downstream;
            SimpleQueue<T> simpleQueue = this.queue;
            long j2 = this.produced;
            int iAddAndGet = 1;
            while (true) {
                long jAddAndGet = this.requested.get();
                while (j2 != jAddAndGet) {
                    boolean z2 = this.done;
                    try {
                        T tPoll = simpleQueue.poll();
                        boolean z3 = tPoll == null;
                        if (checkTerminated(z2, z3, subscriber)) {
                            return;
                        }
                        if (z3) {
                            break;
                        }
                        subscriber.onNext(tPoll);
                        j2++;
                        if (j2 == this.limit) {
                            if (jAddAndGet != Long.MAX_VALUE) {
                                jAddAndGet = this.requested.addAndGet(-j2);
                            }
                            this.upstream.request(j2);
                            j2 = 0;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.cancelled = true;
                        this.upstream.cancel();
                        simpleQueue.clear();
                        subscriber.onError(th);
                        this.worker.dispose();
                        return;
                    }
                }
                if (j2 == jAddAndGet && checkTerminated(this.done, simpleQueue.isEmpty(), subscriber)) {
                    return;
                }
                int i2 = get();
                if (iAddAndGet == i2) {
                    this.produced = j2;
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i2;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.BaseObserveOnSubscriber
        public void runBackfused() {
            int iAddAndGet = 1;
            while (!this.cancelled) {
                boolean z2 = this.done;
                this.downstream.onNext(null);
                if (z2) {
                    this.cancelled = true;
                    Throwable th = this.error;
                    if (th != null) {
                        this.downstream.onError(th);
                    } else {
                        this.downstream.onComplete();
                    }
                    this.worker.dispose();
                    return;
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.BaseObserveOnSubscriber
        public void runSync() {
            Subscriber<? super T> subscriber = this.downstream;
            SimpleQueue<T> simpleQueue = this.queue;
            long j2 = this.produced;
            int iAddAndGet = 1;
            do {
                long j3 = this.requested.get();
                while (j2 != j3) {
                    try {
                        T tPoll = simpleQueue.poll();
                        if (this.cancelled) {
                            return;
                        }
                        if (tPoll == null) {
                            this.cancelled = true;
                            subscriber.onComplete();
                            this.worker.dispose();
                            return;
                        }
                        subscriber.onNext(tPoll);
                        j2++;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.cancelled = true;
                        this.upstream.cancel();
                        subscriber.onError(th);
                        this.worker.dispose();
                        return;
                    }
                }
                if (this.cancelled) {
                    return;
                }
                if (simpleQueue.isEmpty()) {
                    this.cancelled = true;
                    subscriber.onComplete();
                    this.worker.dispose();
                    return;
                }
                this.produced = j2;
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }
    }

    public FlowableObserveOn(Flowable<T> source, Scheduler scheduler, boolean delayError, int prefetch) {
        super(source);
        this.scheduler = scheduler;
        this.delayError = delayError;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        Scheduler.Worker workerCreateWorker = this.scheduler.createWorker();
        if (s2 instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new ObserveOnConditionalSubscriber((ConditionalSubscriber) s2, workerCreateWorker, this.delayError, this.prefetch));
        } else {
            this.source.subscribe((FlowableSubscriber) new ObserveOnSubscriber(s2, workerCreateWorker, this.delayError, this.prefetch));
        }
    }
}
