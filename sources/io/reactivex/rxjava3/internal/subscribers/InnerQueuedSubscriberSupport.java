package io.reactivex.rxjava3.internal.subscribers;

/* loaded from: classes8.dex */
public interface InnerQueuedSubscriberSupport<T> {
    void drain();

    void innerComplete(InnerQueuedSubscriber<T> inner);

    void innerError(InnerQueuedSubscriber<T> inner, Throwable e2);

    void innerNext(InnerQueuedSubscriber<T> inner, T value);
}
