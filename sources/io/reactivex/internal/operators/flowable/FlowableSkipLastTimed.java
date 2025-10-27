package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableSkipLastTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final int bufferSize;
    final boolean delayError;
    final Scheduler scheduler;
    final long time;
    final TimeUnit unit;

    public static final class SkipLastTimedSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5677354903406201275L;
        volatile boolean cancelled;
        final boolean delayError;
        volatile boolean done;
        final Subscriber<? super T> downstream;
        Throwable error;
        final SpscLinkedArrayQueue<Object> queue;
        final AtomicLong requested = new AtomicLong();
        final Scheduler scheduler;
        final long time;
        final TimeUnit unit;
        Subscription upstream;

        public SkipLastTimedSubscriber(Subscriber<? super T> subscriber, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2, boolean z2) {
            this.downstream = subscriber;
            this.time = j2;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.queue = new SpscLinkedArrayQueue<>(i2);
            this.delayError = z2;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        public boolean checkTerminated(boolean z2, boolean z3, Subscriber<? super T> subscriber, boolean z4) {
            if (this.cancelled) {
                this.queue.clear();
                return true;
            }
            if (!z2) {
                return false;
            }
            if (z4) {
                if (!z3) {
                    return false;
                }
                Throwable th = this.error;
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
            Throwable th2 = this.error;
            if (th2 != null) {
                this.queue.clear();
                subscriber.onError(th2);
                return true;
            }
            if (!z3) {
                return false;
            }
            subscriber.onComplete();
            return true;
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = this.downstream;
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
            boolean z2 = this.delayError;
            TimeUnit timeUnit = this.unit;
            Scheduler scheduler = this.scheduler;
            long j2 = this.time;
            int iAddAndGet = 1;
            do {
                long j3 = this.requested.get();
                long j4 = 0;
                while (j4 != j3) {
                    boolean z3 = this.done;
                    Long l2 = (Long) spscLinkedArrayQueue.peek();
                    boolean z4 = l2 == null;
                    boolean z5 = (z4 || l2.longValue() <= scheduler.now(timeUnit) - j2) ? z4 : true;
                    if (checkTerminated(z3, z5, subscriber, z2)) {
                        return;
                    }
                    if (z5) {
                        break;
                    }
                    spscLinkedArrayQueue.poll();
                    subscriber.onNext(spscLinkedArrayQueue.poll());
                    j4++;
                }
                if (j4 != 0) {
                    BackpressureHelper.produced(this.requested, j4);
                }
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.queue.offer(Long.valueOf(this.scheduler.now(this.unit)), t2);
            drain();
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.requested, j2);
                drain();
            }
        }
    }

    public FlowableSkipLastTimed(Flowable<T> flowable, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2, boolean z2) {
        super(flowable);
        this.time = j2;
        this.unit = timeUnit;
        this.scheduler = scheduler;
        this.bufferSize = i2;
        this.delayError = z2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new SkipLastTimedSubscriber(subscriber, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError));
    }
}
