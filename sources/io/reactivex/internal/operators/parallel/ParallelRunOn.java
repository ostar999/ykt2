package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ParallelRunOn<T> extends ParallelFlowable<T> {
    final int prefetch;
    final Scheduler scheduler;
    final ParallelFlowable<? extends T> source;

    public static abstract class BaseRunOnSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        private static final long serialVersionUID = 9222303586456402150L;
        volatile boolean cancelled;
        int consumed;
        volatile boolean done;
        Throwable error;
        final int limit;
        final int prefetch;
        final SpscArrayQueue<T> queue;
        final AtomicLong requested = new AtomicLong();
        Subscription upstream;
        final Scheduler.Worker worker;

        public BaseRunOnSubscriber(int i2, SpscArrayQueue<T> spscArrayQueue, Scheduler.Worker worker) {
            this.prefetch = i2;
            this.queue = spscArrayQueue;
            this.limit = i2 - (i2 >> 2);
            this.worker = worker;
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            this.worker.dispose();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            schedule();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            schedule();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t2) {
            if (this.done) {
                return;
            }
            if (this.queue.offer(t2)) {
                schedule();
            } else {
                this.upstream.cancel();
                onError(new MissingBackpressureException("Queue is full?!"));
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.requested, j2);
                schedule();
            }
        }

        public final void schedule() {
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }
    }

    public final class MultiWorkerCallback implements SchedulerMultiWorkerSupport.WorkerCallback {
        final Subscriber<T>[] parents;
        final Subscriber<? super T>[] subscribers;

        public MultiWorkerCallback(Subscriber<? super T>[] subscriberArr, Subscriber<T>[] subscriberArr2) {
            this.subscribers = subscriberArr;
            this.parents = subscriberArr2;
        }

        @Override // io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport.WorkerCallback
        public void onWorker(int i2, Scheduler.Worker worker) {
            ParallelRunOn.this.createSubscriber(i2, this.subscribers, this.parents, worker);
        }
    }

    public static final class RunOnConditionalSubscriber<T> extends BaseRunOnSubscriber<T> {
        private static final long serialVersionUID = 1075119423897941642L;
        final ConditionalSubscriber<? super T> downstream;

        public RunOnConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, int i2, SpscArrayQueue<T> spscArrayQueue, Scheduler.Worker worker) {
            super(i2, spscArrayQueue, worker);
            this.downstream = conditionalSubscriber;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(this.prefetch);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Throwable th;
            int i2 = this.consumed;
            SpscArrayQueue<T> spscArrayQueue = this.queue;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.downstream;
            int i3 = this.limit;
            int iAddAndGet = 1;
            while (true) {
                long j2 = this.requested.get();
                long j3 = 0;
                while (j3 != j2) {
                    if (this.cancelled) {
                        spscArrayQueue.clear();
                        return;
                    }
                    boolean z2 = this.done;
                    if (z2 && (th = this.error) != null) {
                        spscArrayQueue.clear();
                        conditionalSubscriber.onError(th);
                        this.worker.dispose();
                        return;
                    }
                    T tPoll = spscArrayQueue.poll();
                    boolean z3 = tPoll == null;
                    if (z2 && z3) {
                        conditionalSubscriber.onComplete();
                        this.worker.dispose();
                        return;
                    } else {
                        if (z3) {
                            break;
                        }
                        if (conditionalSubscriber.tryOnNext(tPoll)) {
                            j3++;
                        }
                        i2++;
                        if (i2 == i3) {
                            this.upstream.request(i2);
                            i2 = 0;
                        }
                    }
                }
                if (j3 == j2) {
                    if (this.cancelled) {
                        spscArrayQueue.clear();
                        return;
                    }
                    if (this.done) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            spscArrayQueue.clear();
                            conditionalSubscriber.onError(th2);
                            this.worker.dispose();
                            return;
                        } else if (spscArrayQueue.isEmpty()) {
                            conditionalSubscriber.onComplete();
                            this.worker.dispose();
                            return;
                        }
                    }
                }
                if (j3 != 0 && j2 != Long.MAX_VALUE) {
                    this.requested.addAndGet(-j3);
                }
                int i4 = get();
                if (i4 == iAddAndGet) {
                    this.consumed = i2;
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i4;
                }
            }
        }
    }

    public static final class RunOnSubscriber<T> extends BaseRunOnSubscriber<T> {
        private static final long serialVersionUID = 1075119423897941642L;
        final Subscriber<? super T> downstream;

        public RunOnSubscriber(Subscriber<? super T> subscriber, int i2, SpscArrayQueue<T> spscArrayQueue, Scheduler.Worker worker) {
            super(i2, spscArrayQueue, worker);
            this.downstream = subscriber;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(this.prefetch);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Throwable th;
            int i2 = this.consumed;
            SpscArrayQueue<T> spscArrayQueue = this.queue;
            Subscriber<? super T> subscriber = this.downstream;
            int i3 = this.limit;
            int iAddAndGet = 1;
            while (true) {
                long j2 = this.requested.get();
                long j3 = 0;
                while (j3 != j2) {
                    if (this.cancelled) {
                        spscArrayQueue.clear();
                        return;
                    }
                    boolean z2 = this.done;
                    if (z2 && (th = this.error) != null) {
                        spscArrayQueue.clear();
                        subscriber.onError(th);
                        this.worker.dispose();
                        return;
                    }
                    T tPoll = spscArrayQueue.poll();
                    boolean z3 = tPoll == null;
                    if (z2 && z3) {
                        subscriber.onComplete();
                        this.worker.dispose();
                        return;
                    } else {
                        if (z3) {
                            break;
                        }
                        subscriber.onNext(tPoll);
                        j3++;
                        i2++;
                        if (i2 == i3) {
                            this.upstream.request(i2);
                            i2 = 0;
                        }
                    }
                }
                if (j3 == j2) {
                    if (this.cancelled) {
                        spscArrayQueue.clear();
                        return;
                    }
                    if (this.done) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            spscArrayQueue.clear();
                            subscriber.onError(th2);
                            this.worker.dispose();
                            return;
                        } else if (spscArrayQueue.isEmpty()) {
                            subscriber.onComplete();
                            this.worker.dispose();
                            return;
                        }
                    }
                }
                if (j3 != 0 && j2 != Long.MAX_VALUE) {
                    this.requested.addAndGet(-j3);
                }
                int i4 = get();
                if (i4 == iAddAndGet) {
                    this.consumed = i2;
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i4;
                }
            }
        }
    }

    public ParallelRunOn(ParallelFlowable<? extends T> parallelFlowable, Scheduler scheduler, int i2) {
        this.source = parallelFlowable;
        this.scheduler = scheduler;
        this.prefetch = i2;
    }

    public void createSubscriber(int i2, Subscriber<? super T>[] subscriberArr, Subscriber<T>[] subscriberArr2, Scheduler.Worker worker) {
        Subscriber<? super T> subscriber = subscriberArr[i2];
        SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.prefetch);
        if (subscriber instanceof ConditionalSubscriber) {
            subscriberArr2[i2] = new RunOnConditionalSubscriber((ConditionalSubscriber) subscriber, this.prefetch, spscArrayQueue, worker);
        } else {
            subscriberArr2[i2] = new RunOnSubscriber(subscriber, this.prefetch, spscArrayQueue, worker);
        }
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<T>[] subscriberArr2 = new Subscriber[length];
            Object obj = this.scheduler;
            if (obj instanceof SchedulerMultiWorkerSupport) {
                ((SchedulerMultiWorkerSupport) obj).createWorkers(length, new MultiWorkerCallback(subscriberArr, subscriberArr2));
            } else {
                for (int i2 = 0; i2 < length; i2++) {
                    createSubscriber(i2, subscriberArr, subscriberArr2, this.scheduler.createWorker());
                }
            }
            this.source.subscribe(subscriberArr2);
        }
    }
}
