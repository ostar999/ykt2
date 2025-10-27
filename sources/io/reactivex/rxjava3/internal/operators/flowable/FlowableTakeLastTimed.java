package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableTakeLastTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final int bufferSize;
    final long count;
    final boolean delayError;
    final Scheduler scheduler;
    final long time;
    final TimeUnit unit;

    public static final class TakeLastTimedSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5677354903406201275L;
        volatile boolean cancelled;
        final long count;
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

        public TakeLastTimedSubscriber(Subscriber<? super T> actual, long count, long time, TimeUnit unit, Scheduler scheduler, int bufferSize, boolean delayError) {
            this.downstream = actual;
            this.count = count;
            this.time = time;
            this.unit = unit;
            this.scheduler = scheduler;
            this.queue = new SpscLinkedArrayQueue<>(bufferSize);
            this.delayError = delayError;
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

        public boolean checkTerminated(boolean empty, Subscriber<? super T> a3, boolean delayError) {
            if (this.cancelled) {
                this.queue.clear();
                return true;
            }
            if (delayError) {
                if (!empty) {
                    return false;
                }
                Throwable th = this.error;
                if (th != null) {
                    a3.onError(th);
                } else {
                    a3.onComplete();
                }
                return true;
            }
            Throwable th2 = this.error;
            if (th2 != null) {
                this.queue.clear();
                a3.onError(th2);
                return true;
            }
            if (!empty) {
                return false;
            }
            a3.onComplete();
            return true;
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = this.downstream;
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
            boolean z2 = this.delayError;
            int iAddAndGet = 1;
            do {
                if (this.done) {
                    if (checkTerminated(spscLinkedArrayQueue.isEmpty(), subscriber, z2)) {
                        return;
                    }
                    long j2 = this.requested.get();
                    long j3 = 0;
                    while (true) {
                        if (checkTerminated(spscLinkedArrayQueue.peek() == null, subscriber, z2)) {
                            return;
                        }
                        if (j2 != j3) {
                            spscLinkedArrayQueue.poll();
                            subscriber.onNext(spscLinkedArrayQueue.poll());
                            j3++;
                        } else if (j3 != 0) {
                            BackpressureHelper.produced(this.requested, j3);
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            trim(this.scheduler.now(this.unit), this.queue);
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.delayError) {
                trim(this.scheduler.now(this.unit), this.queue);
            }
            this.error = t2;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
            long jNow = this.scheduler.now(this.unit);
            spscLinkedArrayQueue.offer(Long.valueOf(jNow), t2);
            trim(jNow, spscLinkedArrayQueue);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                drain();
            }
        }

        public void trim(long now, SpscLinkedArrayQueue<Object> q2) {
            long j2 = this.time;
            long j3 = this.count;
            boolean z2 = j3 == Long.MAX_VALUE;
            while (!q2.isEmpty()) {
                if (((Long) q2.peek()).longValue() >= now - j2 && (z2 || (q2.size() >> 1) <= j3)) {
                    return;
                }
                q2.poll();
                q2.poll();
            }
        }
    }

    public FlowableTakeLastTimed(Flowable<T> source, long count, long time, TimeUnit unit, Scheduler scheduler, int bufferSize, boolean delayError) {
        super(source);
        this.count = count;
        this.time = time;
        this.unit = unit;
        this.scheduler = scheduler;
        this.bufferSize = bufferSize;
        this.delayError = delayError;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new TakeLastTimedSubscriber(s2, this.count, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError));
    }
}
