package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
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

        public JoinInnerSubscriber(JoinSubscriptionBase<T> parent, int prefetch) {
            this.parent = parent;
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
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
        public void onError(Throwable t2) {
            this.parent.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.parent.onNext(this, t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.setOnce(this, s2, this.prefetch);
        }

        public void request(long n2) {
            long j2 = this.produced + n2;
            if (j2 < this.limit) {
                this.produced = j2;
            } else {
                this.produced = 0L;
                get().request(j2);
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

        public JoinSubscription(Subscriber<? super T> actual, int n2, int prefetch) {
            super(actual, n2, prefetch);
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x005d, code lost:
        
            if (r12 == false) goto L79;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x005f, code lost:
        
            if (r15 == false) goto L80;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0061, code lost:
        
            r3.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0064, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0065, code lost:
        
            if (r15 == false) goto L81;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drainLoop() {
            /*
                r18 = this;
                r0 = r18
                io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin$JoinInnerSubscriber<T>[] r1 = r0.subscribers
                int r2 = r1.length
                org.reactivestreams.Subscriber<? super T> r3 = r0.downstream
                r5 = 1
            L8:
                java.util.concurrent.atomic.AtomicLong r6 = r0.requested
                long r6 = r6.get()
                r8 = 0
                r10 = r8
            L11:
                int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r12 == 0) goto L67
                boolean r12 = r0.cancelled
                if (r12 == 0) goto L1d
                r18.cleanup()
                return
            L1d:
                io.reactivex.rxjava3.internal.util.AtomicThrowable r12 = r0.errors
                java.lang.Object r12 = r12.get()
                java.lang.Throwable r12 = (java.lang.Throwable) r12
                if (r12 == 0) goto L2e
                r18.cleanup()
                r3.onError(r12)
                return
            L2e:
                java.util.concurrent.atomic.AtomicInteger r12 = r0.done
                int r12 = r12.get()
                if (r12 != 0) goto L38
                r12 = 1
                goto L39
            L38:
                r12 = 0
            L39:
                r14 = 0
                r15 = 1
            L3b:
                int r4 = r1.length
                if (r14 >= r4) goto L5d
                r4 = r1[r14]
                io.reactivex.rxjava3.operators.SimplePlainQueue<T> r13 = r4.queue
                if (r13 == 0) goto L5a
                java.lang.Object r13 = r13.poll()
                if (r13 == 0) goto L5a
                r3.onNext(r13)
                r4.requestOne()
                r16 = 1
                long r10 = r10 + r16
                int r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r4 != 0) goto L59
                goto L67
            L59:
                r15 = 0
            L5a:
                int r14 = r14 + 1
                goto L3b
            L5d:
                if (r12 == 0) goto L65
                if (r15 == 0) goto L65
                r3.onComplete()
                return
            L65:
                if (r15 == 0) goto L11
            L67:
                int r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r4 != 0) goto Lac
                boolean r4 = r0.cancelled
                if (r4 == 0) goto L73
                r18.cleanup()
                return
            L73:
                io.reactivex.rxjava3.internal.util.AtomicThrowable r4 = r0.errors
                java.lang.Object r4 = r4.get()
                java.lang.Throwable r4 = (java.lang.Throwable) r4
                if (r4 == 0) goto L84
                r18.cleanup()
                r3.onError(r4)
                return
            L84:
                java.util.concurrent.atomic.AtomicInteger r4 = r0.done
                int r4 = r4.get()
                if (r4 != 0) goto L8e
                r4 = 1
                goto L8f
            L8e:
                r4 = 0
            L8f:
                r6 = 0
            L90:
                if (r6 >= r2) goto La3
                r7 = r1[r6]
                io.reactivex.rxjava3.operators.SimplePlainQueue<T> r7 = r7.queue
                if (r7 == 0) goto La0
                boolean r7 = r7.isEmpty()
                if (r7 != 0) goto La0
                r13 = 0
                goto La4
            La0:
                int r6 = r6 + 1
                goto L90
            La3:
                r13 = 1
            La4:
                if (r4 == 0) goto Lac
                if (r13 == 0) goto Lac
                r3.onComplete()
                return
            Lac:
                int r4 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
                if (r4 == 0) goto Lb5
                java.util.concurrent.atomic.AtomicLong r4 = r0.requested
                io.reactivex.rxjava3.internal.util.BackpressureHelper.produced(r4, r10)
            Lb5:
                int r4 = -r5
                int r5 = r0.addAndGet(r4)
                if (r5 != 0) goto L8
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscription.drainLoop():void");
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onComplete() {
            this.done.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onError(Throwable e2) {
            if (this.errors.compareAndSet(null, e2)) {
                cancelAll();
                drain();
            } else if (e2 != this.errors.get()) {
                RxJavaPlugins.onError(e2);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onNext(JoinInnerSubscriber<T> inner, T value) {
            if (get() == 0 && compareAndSet(0, 1)) {
                if (this.requested.get() != 0) {
                    this.downstream.onNext(value);
                    if (this.requested.get() != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    inner.request(1L);
                } else if (!inner.getQueue().offer(value)) {
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
            } else if (!inner.getQueue().offer(value)) {
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

        public JoinSubscriptionBase(Subscriber<? super T> actual, int n2, int prefetch) {
            this.downstream = actual;
            JoinInnerSubscriber<T>[] joinInnerSubscriberArr = new JoinInnerSubscriber[n2];
            for (int i2 = 0; i2 < n2; i2++) {
                joinInnerSubscriberArr[i2] = new JoinInnerSubscriber<>(this, prefetch);
            }
            this.subscribers = joinInnerSubscriberArr;
            this.done.lazySet(n2);
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

        public abstract void onError(Throwable e2);

        public abstract void onNext(JoinInnerSubscriber<T> inner, T value);

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                drain();
            }
        }
    }

    public static final class JoinSubscriptionDelayError<T> extends JoinSubscriptionBase<T> {
        private static final long serialVersionUID = -5737965195918321883L;

        public JoinSubscriptionDelayError(Subscriber<? super T> actual, int n2, int prefetch) {
            super(actual, n2, prefetch);
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x004b, code lost:
        
            if (r12 == false) goto L68;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x004d, code lost:
        
            if (r15 == false) goto L69;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x004f, code lost:
        
            r18.errors.tryTerminateConsumer(r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0054, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0055, code lost:
        
            if (r15 == false) goto L70;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drainLoop() {
            /*
                r18 = this;
                r0 = r18
                io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin$JoinInnerSubscriber<T>[] r1 = r0.subscribers
                int r2 = r1.length
                org.reactivestreams.Subscriber<? super T> r3 = r0.downstream
                r5 = 1
            L8:
                java.util.concurrent.atomic.AtomicLong r6 = r0.requested
                long r6 = r6.get()
                r8 = 0
                r10 = r8
            L11:
                int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r12 == 0) goto L57
                boolean r12 = r0.cancelled
                if (r12 == 0) goto L1d
                r18.cleanup()
                return
            L1d:
                java.util.concurrent.atomic.AtomicInteger r12 = r0.done
                int r12 = r12.get()
                if (r12 != 0) goto L27
                r12 = 1
                goto L28
            L27:
                r12 = 0
            L28:
                r14 = 0
                r15 = 1
            L2a:
                if (r14 >= r2) goto L4b
                r4 = r1[r14]
                io.reactivex.rxjava3.operators.SimplePlainQueue<T> r13 = r4.queue
                if (r13 == 0) goto L48
                java.lang.Object r13 = r13.poll()
                if (r13 == 0) goto L48
                r3.onNext(r13)
                r4.requestOne()
                r16 = 1
                long r10 = r10 + r16
                int r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r4 != 0) goto L47
                goto L57
            L47:
                r15 = 0
            L48:
                int r14 = r14 + 1
                goto L2a
            L4b:
                if (r12 == 0) goto L55
                if (r15 == 0) goto L55
                io.reactivex.rxjava3.internal.util.AtomicThrowable r1 = r0.errors
                r1.tryTerminateConsumer(r3)
                return
            L55:
                if (r15 == 0) goto L11
            L57:
                int r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r4 != 0) goto L8d
                boolean r4 = r0.cancelled
                if (r4 == 0) goto L63
                r18.cleanup()
                return
            L63:
                java.util.concurrent.atomic.AtomicInteger r4 = r0.done
                int r4 = r4.get()
                if (r4 != 0) goto L6d
                r4 = 1
                goto L6e
            L6d:
                r4 = 0
            L6e:
                r6 = 0
            L6f:
                if (r6 >= r2) goto L82
                r7 = r1[r6]
                io.reactivex.rxjava3.operators.SimplePlainQueue<T> r7 = r7.queue
                if (r7 == 0) goto L7f
                boolean r7 = r7.isEmpty()
                if (r7 != 0) goto L7f
                r13 = 0
                goto L83
            L7f:
                int r6 = r6 + 1
                goto L6f
            L82:
                r13 = 1
            L83:
                if (r4 == 0) goto L8d
                if (r13 == 0) goto L8d
                io.reactivex.rxjava3.internal.util.AtomicThrowable r1 = r0.errors
                r1.tryTerminateConsumer(r3)
                return
            L8d:
                int r4 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
                if (r4 == 0) goto L96
                java.util.concurrent.atomic.AtomicLong r4 = r0.requested
                io.reactivex.rxjava3.internal.util.BackpressureHelper.produced(r4, r10)
            L96:
                int r4 = -r5
                int r5 = r0.addAndGet(r4)
                if (r5 != 0) goto L8
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionDelayError.drainLoop():void");
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onComplete() {
            this.done.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onError(Throwable e2) {
            if (this.errors.tryAddThrowableOrReport(e2)) {
                this.done.decrementAndGet();
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onNext(JoinInnerSubscriber<T> inner, T value) {
            if (get() == 0 && compareAndSet(0, 1)) {
                if (this.requested.get() != 0) {
                    this.downstream.onNext(value);
                    if (this.requested.get() != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    inner.request(1L);
                } else if (!inner.getQueue().offer(value)) {
                    inner.cancel();
                    this.errors.tryAddThrowableOrReport(new MissingBackpressureException("Queue full?!"));
                    this.done.decrementAndGet();
                    drainLoop();
                    return;
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                if (!inner.getQueue().offer(value)) {
                    inner.cancel();
                    this.errors.tryAddThrowableOrReport(new MissingBackpressureException("Queue full?!"));
                    this.done.decrementAndGet();
                }
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }
    }

    public ParallelJoin(ParallelFlowable<? extends T> source, int prefetch, boolean delayErrors) {
        this.source = source;
        this.prefetch = prefetch;
        this.delayErrors = delayErrors;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        JoinSubscriptionBase joinSubscriptionDelayError = this.delayErrors ? new JoinSubscriptionDelayError(s2, this.source.parallelism(), this.prefetch) : new JoinSubscription(s2, this.source.parallelism(), this.prefetch);
        s2.onSubscribe(joinSubscriptionDelayError);
        this.source.subscribe(joinSubscriptionDelayError.subscribers);
    }
}
