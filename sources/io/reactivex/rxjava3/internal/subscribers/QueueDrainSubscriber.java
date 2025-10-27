package io.reactivex.rxjava3.internal.subscribers;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.QueueDrain;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public abstract class QueueDrainSubscriber<T, U, V> extends QueueDrainSubscriberPad4 implements FlowableSubscriber<T>, QueueDrain<U, V> {
    protected volatile boolean cancelled;
    protected volatile boolean done;
    protected final Subscriber<? super V> downstream;
    protected Throwable error;
    protected final SimplePlainQueue<U> queue;

    public QueueDrainSubscriber(Subscriber<? super V> actual, SimplePlainQueue<U> queue) {
        this.downstream = actual;
        this.queue = queue;
    }

    public boolean accept(Subscriber<? super V> a3, U v2) {
        return false;
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final boolean cancelled() {
        return this.cancelled;
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final boolean done() {
        return this.done;
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final boolean enter() {
        return this.wip.getAndIncrement() == 0;
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final Throwable error() {
        return this.error;
    }

    public final boolean fastEnter() {
        return this.wip.get() == 0 && this.wip.compareAndSet(0, 1);
    }

    public final void fastPathEmitMax(U value, boolean delayError, Disposable dispose) {
        Subscriber<? super V> subscriber = this.downstream;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (fastEnter()) {
            long j2 = this.requested.get();
            if (j2 == 0) {
                dispose.dispose();
                subscriber.onError(new MissingBackpressureException("Could not emit buffer due to lack of requests"));
                return;
            } else {
                if (accept(subscriber, value) && j2 != Long.MAX_VALUE) {
                    produced(1L);
                }
                if (leave(-1) == 0) {
                    return;
                }
            }
        } else {
            simplePlainQueue.offer(value);
            if (!enter()) {
                return;
            }
        }
        QueueDrainHelper.drainMaxLoop(simplePlainQueue, subscriber, delayError, dispose, this);
    }

    public final void fastPathOrderedEmitMax(U value, boolean delayError, Disposable dispose) {
        Subscriber<? super V> subscriber = this.downstream;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (fastEnter()) {
            long j2 = this.requested.get();
            if (j2 == 0) {
                this.cancelled = true;
                dispose.dispose();
                subscriber.onError(new MissingBackpressureException("Could not emit buffer due to lack of requests"));
                return;
            } else if (simplePlainQueue.isEmpty()) {
                if (accept(subscriber, value) && j2 != Long.MAX_VALUE) {
                    produced(1L);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                simplePlainQueue.offer(value);
            }
        } else {
            simplePlainQueue.offer(value);
            if (!enter()) {
                return;
            }
        }
        QueueDrainHelper.drainMaxLoop(simplePlainQueue, subscriber, delayError, dispose, this);
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final int leave(int m2) {
        return this.wip.addAndGet(m2);
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final long produced(long n2) {
        return this.requested.addAndGet(-n2);
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final long requested() {
        return this.requested.get();
    }

    public final void requested(long n2) {
        if (SubscriptionHelper.validate(n2)) {
            BackpressureHelper.add(this.requested, n2);
        }
    }
}
