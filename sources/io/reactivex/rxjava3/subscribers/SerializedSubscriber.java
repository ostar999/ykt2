package io.reactivex.rxjava3.subscribers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
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

    public SerializedSubscriber(Subscriber<? super T> downstream) {
        this(downstream, false);
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
    public void onError(Throwable t2) {
        if (this.done) {
            RxJavaPlugins.onError(t2);
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
                    Object objError = NotificationLite.error(t2);
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
                RxJavaPlugins.onError(t2);
            } else {
                this.downstream.onError(t2);
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(@NonNull T t2) {
        if (this.done) {
            return;
        }
        if (t2 == null) {
            this.upstream.cancel();
            onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
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

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(@NonNull Subscription s2) {
        if (SubscriptionHelper.validate(this.upstream, s2)) {
            this.upstream = s2;
            this.downstream.onSubscribe(this);
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long n2) {
        this.upstream.request(n2);
    }

    public SerializedSubscriber(@NonNull Subscriber<? super T> actual, boolean delayError) {
        this.downstream = actual;
        this.delayError = delayError;
    }
}
