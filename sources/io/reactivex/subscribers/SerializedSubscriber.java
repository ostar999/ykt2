package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class SerializedSubscriber<T> implements FlowableSubscriber<T>, Subscription {
    static final int QUEUE_LINK_SIZE = 4;
    final boolean delayError;
    volatile boolean done;
    final Subscriber<? super T> downstream;
    boolean emitting;
    AppendOnlyLinkedArrayList<Object> queue;
    Subscription upstream;

    public SerializedSubscriber(Subscriber<? super T> subscriber) {
        this(subscriber, false);
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        this.upstream.cancel();
    }

    public void emitLoop() {
        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
        do {
            synchronized (this) {
                appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    this.emitting = false;
                    return;
                }
                this.queue = null;
            }
        } while (!appendOnlyLinkedArrayList.accept(this.downstream));
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.done) {
            return;
        }
        synchronized (this) {
            if (this.done) {
                return;
            }
            if (!this.emitting) {
                this.done = true;
                this.emitting = true;
                this.downstream.onComplete();
            } else {
                AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                    this.queue = appendOnlyLinkedArrayList;
                }
                appendOnlyLinkedArrayList.add(NotificationLite.complete());
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (this.done) {
            RxJavaPlugins.onError(th);
            return;
        }
        synchronized (this) {
            boolean z2 = true;
            if (!this.done) {
                if (this.emitting) {
                    this.done = true;
                    AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                    if (appendOnlyLinkedArrayList == null) {
                        appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                        this.queue = appendOnlyLinkedArrayList;
                    }
                    Object objError = NotificationLite.error(th);
                    if (this.delayError) {
                        appendOnlyLinkedArrayList.add(objError);
                    } else {
                        appendOnlyLinkedArrayList.setFirst(objError);
                    }
                    return;
                }
                this.done = true;
                this.emitting = true;
                z2 = false;
            }
            if (z2) {
                RxJavaPlugins.onError(th);
            } else {
                this.downstream.onError(th);
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        if (this.done) {
            return;
        }
        if (t2 == null) {
            this.upstream.cancel();
            onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            return;
        }
        synchronized (this) {
            if (this.done) {
                return;
            }
            if (!this.emitting) {
                this.emitting = true;
                this.downstream.onNext(t2);
                emitLoop();
            } else {
                AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                    this.queue = appendOnlyLinkedArrayList;
                }
                appendOnlyLinkedArrayList.add(NotificationLite.next(t2));
            }
        }
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.upstream, subscription)) {
            this.upstream = subscription;
            this.downstream.onSubscribe(this);
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j2) {
        this.upstream.request(j2);
    }

    public SerializedSubscriber(Subscriber<? super T> subscriber, boolean z2) {
        this.downstream = subscriber;
        this.delayError = z2;
    }
}
