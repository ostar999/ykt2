package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public abstract class ConcatMapXMainSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T> {
    private static final long serialVersionUID = -3214213361171757852L;
    volatile boolean cancelled;
    volatile boolean done;
    final ErrorMode errorMode;
    final AtomicThrowable errors = new AtomicThrowable();
    final int prefetch;
    SimpleQueue<T> queue;
    boolean syncFused;
    Subscription upstream;

    public ConcatMapXMainSubscriber(int prefetch, ErrorMode errorMode) {
        this.errorMode = errorMode;
        this.prefetch = prefetch;
    }

    public void clearValue() {
    }

    abstract void disposeInner();

    abstract void drain();

    @Override // org.reactivestreams.Subscriber
    public final void onComplete() {
        this.done = true;
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public final void onError(Throwable t2) {
        if (this.errors.tryAddThrowableOrReport(t2)) {
            if (this.errorMode == ErrorMode.IMMEDIATE) {
                disposeInner();
            }
            this.done = true;
            drain();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public final void onNext(T t2) {
        if (t2 == null || this.queue.offer(t2)) {
            drain();
        } else {
            this.upstream.cancel();
            onError(new MissingBackpressureException("queue full?!"));
        }
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription s2) {
        if (SubscriptionHelper.validate(this.upstream, s2)) {
            this.upstream = s2;
            if (s2 instanceof QueueSubscription) {
                QueueSubscription queueSubscription = (QueueSubscription) s2;
                int iRequestFusion = queueSubscription.requestFusion(7);
                if (iRequestFusion == 1) {
                    this.queue = queueSubscription;
                    this.syncFused = true;
                    this.done = true;
                    onSubscribeDownstream();
                    drain();
                    return;
                }
                if (iRequestFusion == 2) {
                    this.queue = queueSubscription;
                    onSubscribeDownstream();
                    this.upstream.request(this.prefetch);
                    return;
                }
            }
            this.queue = new SpscArrayQueue(this.prefetch);
            onSubscribeDownstream();
            this.upstream.request(this.prefetch);
        }
    }

    public abstract void onSubscribeDownstream();

    final void stop() {
        this.cancelled = true;
        this.upstream.cancel();
        disposeInner();
        this.errors.tryTerminateAndReport();
        if (getAndIncrement() == 0) {
            this.queue.clear();
            clearValue();
        }
    }
}
