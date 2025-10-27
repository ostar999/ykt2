package io.reactivex.rxjava3.processors;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
final class SerializedProcessor<T> extends FlowableProcessor<T> {
    final FlowableProcessor<T> actual;
    volatile boolean done;
    boolean emitting;
    AppendOnlyLinkedArrayList<Object> queue;

    public SerializedProcessor(final FlowableProcessor<T> actual) {
        this.actual = actual;
    }

    public void emitLoop() {
        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
        while (true) {
            synchronized (this) {
                appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    this.emitting = false;
                    return;
                }
                this.queue = null;
            }
            appendOnlyLinkedArrayList.accept(this.actual);
        }
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @Nullable
    public Throwable getThrowable() {
        return this.actual.getThrowable();
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    public boolean hasComplete() {
        return this.actual.hasComplete();
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    public boolean hasSubscribers() {
        return this.actual.hasSubscribers();
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    public boolean hasThrowable() {
        return this.actual.hasThrowable();
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
            this.done = true;
            if (!this.emitting) {
                this.emitting = true;
                this.actual.onComplete();
                return;
            }
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
            if (appendOnlyLinkedArrayList == null) {
                appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                this.queue = appendOnlyLinkedArrayList;
            }
            appendOnlyLinkedArrayList.add(NotificationLite.complete());
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
                this.done = true;
                if (this.emitting) {
                    AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                    if (appendOnlyLinkedArrayList == null) {
                        appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                        this.queue = appendOnlyLinkedArrayList;
                    }
                    appendOnlyLinkedArrayList.setFirst(NotificationLite.error(t2));
                    return;
                }
                this.emitting = true;
                z2 = false;
            }
            if (z2) {
                RxJavaPlugins.onError(t2);
            } else {
                this.actual.onError(t2);
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        if (this.done) {
            return;
        }
        synchronized (this) {
            if (this.done) {
                return;
            }
            if (!this.emitting) {
                this.emitting = true;
                this.actual.onNext(t2);
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

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription s2) {
        boolean z2 = true;
        if (!this.done) {
            synchronized (this) {
                if (!this.done) {
                    if (this.emitting) {
                        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                        if (appendOnlyLinkedArrayList == null) {
                            appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                            this.queue = appendOnlyLinkedArrayList;
                        }
                        appendOnlyLinkedArrayList.add(NotificationLite.subscription(s2));
                        return;
                    }
                    this.emitting = true;
                    z2 = false;
                }
            }
        }
        if (z2) {
            s2.cancel();
        } else {
            this.actual.onSubscribe(s2);
            emitLoop();
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.actual.subscribe(s2);
    }
}
