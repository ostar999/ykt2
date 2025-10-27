package io.reactivex.rxjava3.internal.subscribers;

import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public abstract class BasicFuseableConditionalSubscriber<T, R> implements ConditionalSubscriber<T>, QueueSubscription<R> {
    protected boolean done;
    protected final ConditionalSubscriber<? super R> downstream;
    protected QueueSubscription<T> qs;
    protected int sourceMode;
    protected Subscription upstream;

    public BasicFuseableConditionalSubscriber(ConditionalSubscriber<? super R> downstream) {
        this.downstream = downstream;
    }

    public void afterDownstream() {
    }

    public boolean beforeDownstream() {
        return true;
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        this.upstream.cancel();
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public void clear() {
        this.qs.clear();
    }

    public final void fail(Throwable t2) {
        Exceptions.throwIfFatal(t2);
        this.upstream.cancel();
        onError(t2);
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean isEmpty() {
        return this.qs.isEmpty();
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean offer(R e2) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.done) {
            return;
        }
        this.done = true;
        this.downstream.onComplete();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable t2) {
        if (this.done) {
            RxJavaPlugins.onError(t2);
        } else {
            this.done = true;
            this.downstream.onError(t2);
        }
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription s2) {
        if (SubscriptionHelper.validate(this.upstream, s2)) {
            this.upstream = s2;
            if (s2 instanceof QueueSubscription) {
                this.qs = (QueueSubscription) s2;
            }
            if (beforeDownstream()) {
                this.downstream.onSubscribe(this);
                afterDownstream();
            }
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long n2) {
        this.upstream.request(n2);
    }

    public final int transitiveBoundaryFusion(int mode) {
        QueueSubscription<T> queueSubscription = this.qs;
        if (queueSubscription == null || (mode & 4) != 0) {
            return 0;
        }
        int iRequestFusion = queueSubscription.requestFusion(mode);
        if (iRequestFusion != 0) {
            this.sourceMode = iRequestFusion;
        }
        return iRequestFusion;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean offer(R v12, R v2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
