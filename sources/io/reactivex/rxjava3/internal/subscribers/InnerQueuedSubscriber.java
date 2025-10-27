package io.reactivex.rxjava3.internal.subscribers;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class InnerQueuedSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
    private static final long serialVersionUID = 22876611072430776L;
    volatile boolean done;
    int fusionMode;
    final int limit;
    final InnerQueuedSubscriberSupport<T> parent;
    final int prefetch;
    long produced;
    volatile SimpleQueue<T> queue;

    public InnerQueuedSubscriber(InnerQueuedSubscriberSupport<T> parent, int prefetch) {
        this.parent = parent;
        this.prefetch = prefetch;
        this.limit = prefetch - (prefetch >> 2);
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        SubscriptionHelper.cancel(this);
    }

    public boolean isDone() {
        return this.done;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        this.parent.innerComplete(this);
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable t2) {
        this.parent.innerError(this, t2);
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        if (this.fusionMode == 0) {
            this.parent.innerNext(this, t2);
        } else {
            this.parent.drain();
        }
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription s2) {
        if (SubscriptionHelper.setOnce(this, s2)) {
            if (s2 instanceof QueueSubscription) {
                QueueSubscription queueSubscription = (QueueSubscription) s2;
                int iRequestFusion = queueSubscription.requestFusion(3);
                if (iRequestFusion == 1) {
                    this.fusionMode = iRequestFusion;
                    this.queue = queueSubscription;
                    this.done = true;
                    this.parent.innerComplete(this);
                    return;
                }
                if (iRequestFusion == 2) {
                    this.fusionMode = iRequestFusion;
                    this.queue = queueSubscription;
                    QueueDrainHelper.request(s2, this.prefetch);
                    return;
                }
            }
            this.queue = QueueDrainHelper.createQueue(this.prefetch);
            QueueDrainHelper.request(s2, this.prefetch);
        }
    }

    public SimpleQueue<T> queue() {
        return this.queue;
    }

    @Override // org.reactivestreams.Subscription
    public void request(long n2) {
        if (this.fusionMode != 1) {
            long j2 = this.produced + n2;
            if (j2 < this.limit) {
                this.produced = j2;
            } else {
                this.produced = 0L;
                get().request(j2);
            }
        }
    }

    public void setDone() {
        this.done = true;
    }
}
