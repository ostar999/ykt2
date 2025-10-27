package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ParallelJoin<T> extends Flowable<T> {
    final boolean delayErrors;
    final int prefetch;
    final ParallelFlowable<? extends T> source;

    public static final class JoinInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 8410034718427740355L;
        final int limit;
        final JoinSubscriptionBase<T> parent;
        final int prefetch;
        long produced;
        volatile SimplePlainQueue<T> queue;

        public JoinInnerSubscriber(JoinSubscriptionBase<T> joinSubscriptionBase, int i2) {
            this.parent = joinSubscriptionBase;
            this.prefetch = i2;
            this.limit = i2 - (i2 >> 2);
        }

        public boolean cancel() {
            return SubscriptionHelper.cancel(this);
        }

        public SimplePlainQueue<T> getQueue() {
            SimplePlainQueue<T> simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                return simplePlainQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.prefetch);
            this.queue = spscArrayQueue;
            return spscArrayQueue;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.parent.onNext(this, t2);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, this.prefetch);
        }

        public void request(long j2) {
            long j3 = this.produced + j2;
            if (j3 < this.limit) {
                this.produced = j3;
            } else {
                this.produced = 0L;
                get().request(j3);
            }
        }

        public void requestOne() {
            long j2 = this.produced + 1;
            if (j2 != this.limit) {
                this.produced = j2;
            } else {
                this.produced = 0L;
                get().request(j2);
            }
        }
    }

    public static final class JoinSubscription<T> extends JoinSubscriptionBase<T> {
        private static final long serialVersionUID = 6312374661811000451L;

        public JoinSubscription(Subscriber<? super T> subscriber, int i2, int i3) {
            super(subscriber, i2, i3);
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x005d, code lost:
        
            if (r12 == false) goto L85;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x005f, code lost:
        
            if (r15 == false) goto L86;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0061, code lost:
        
            r3.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0064, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0065, code lost:
        
            if (r15 == false) goto L87;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drainLoop() {
            /*
                Method dump skipped, instructions count: 208
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscription.drainLoop():void");
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onComplete() {
            this.done.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onError(Throwable th) {
            if (this.errors.compareAndSet(null, th)) {
                cancelAll();
                drain();
            } else if (th != this.errors.get()) {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onNext(JoinInnerSubscriber<T> joinInnerSubscriber, T t2) {
            if (get() == 0 && compareAndSet(0, 1)) {
                if (this.requested.get() != 0) {
                    this.downstream.onNext(t2);
                    if (this.requested.get() != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    joinInnerSubscriber.request(1L);
                } else if (!joinInnerSubscriber.getQueue().offer(t2)) {
                    cancelAll();
                    MissingBackpressureException missingBackpressureException = new MissingBackpressureException("Queue full?!");
                    if (this.errors.compareAndSet(null, missingBackpressureException)) {
                        this.downstream.onError(missingBackpressureException);
                        return;
                    } else {
                        RxJavaPlugins.onError(missingBackpressureException);
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!joinInnerSubscriber.getQueue().offer(t2)) {
                cancelAll();
                onError(new MissingBackpressureException("Queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }
    }

    public static abstract class JoinSubscriptionBase<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 3100232009247827843L;
        volatile boolean cancelled;
        final Subscriber<? super T> downstream;
        final JoinInnerSubscriber<T>[] subscribers;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();
        final AtomicInteger done = new AtomicInteger();

        public JoinSubscriptionBase(Subscriber<? super T> subscriber, int i2, int i3) {
            this.downstream = subscriber;
            JoinInnerSubscriber<T>[] joinInnerSubscriberArr = new JoinInnerSubscriber[i2];
            for (int i4 = 0; i4 < i2; i4++) {
                joinInnerSubscriberArr[i4] = new JoinInnerSubscriber<>(this, i3);
            }
            this.subscribers = joinInnerSubscriberArr;
            this.done.lazySet(i2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelAll();
            if (getAndIncrement() == 0) {
                cleanup();
            }
        }

        public void cancelAll() {
            for (JoinInnerSubscriber<T> joinInnerSubscriber : this.subscribers) {
                joinInnerSubscriber.cancel();
            }
        }

        public void cleanup() {
            for (JoinInnerSubscriber<T> joinInnerSubscriber : this.subscribers) {
                joinInnerSubscriber.queue = null;
            }
        }

        public abstract void drain();

        public abstract void onComplete();

        public abstract void onError(Throwable th);

        public abstract void onNext(JoinInnerSubscriber<T> joinInnerSubscriber, T t2);

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.requested, j2);
                drain();
            }
        }
    }

    public static final class JoinSubscriptionDelayError<T> extends JoinSubscriptionBase<T> {
        private static final long serialVersionUID = -5737965195918321883L;

        public JoinSubscriptionDelayError(Subscriber<? super T> subscriber, int i2, int i3) {
            super(subscriber, i2, i3);
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x004b, code lost:
        
            if (r12 == false) goto L80;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x004d, code lost:
        
            if (r15 == false) goto L81;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0057, code lost:
        
            if (r18.errors.get() == null) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0059, code lost:
        
            r3.onError(r18.errors.terminate());
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0063, code lost:
        
            r3.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0067, code lost:
        
            if (r15 == false) goto L82;
         */
        /* JADX WARN: Code restructure failed: missing block: B:91:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drainLoop() {
            /*
                Method dump skipped, instructions count: 213
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscriptionDelayError.drainLoop():void");
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onComplete() {
            this.done.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onError(Throwable th) {
            this.errors.addThrowable(th);
            this.done.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onNext(JoinInnerSubscriber<T> joinInnerSubscriber, T t2) {
            if (get() == 0 && compareAndSet(0, 1)) {
                if (this.requested.get() != 0) {
                    this.downstream.onNext(t2);
                    if (this.requested.get() != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    joinInnerSubscriber.request(1L);
                } else if (!joinInnerSubscriber.getQueue().offer(t2)) {
                    joinInnerSubscriber.cancel();
                    this.errors.addThrowable(new MissingBackpressureException("Queue full?!"));
                    this.done.decrementAndGet();
                    drainLoop();
                    return;
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                if (!joinInnerSubscriber.getQueue().offer(t2) && joinInnerSubscriber.cancel()) {
                    this.errors.addThrowable(new MissingBackpressureException("Queue full?!"));
                    this.done.decrementAndGet();
                }
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }
    }

    public ParallelJoin(ParallelFlowable<? extends T> parallelFlowable, int i2, boolean z2) {
        this.source = parallelFlowable;
        this.prefetch = i2;
        this.delayErrors = z2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        JoinSubscriptionBase joinSubscriptionDelayError = this.delayErrors ? new JoinSubscriptionDelayError(subscriber, this.source.parallelism(), this.prefetch) : new JoinSubscription(subscriber, this.source.parallelism(), this.prefetch);
        subscriber.onSubscribe(joinSubscriptionDelayError);
        this.source.subscribe(joinSubscriptionDelayError.subscribers);
    }
}
