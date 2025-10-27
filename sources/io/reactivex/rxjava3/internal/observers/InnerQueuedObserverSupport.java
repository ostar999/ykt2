package io.reactivex.rxjava3.internal.observers;

/* loaded from: classes8.dex */
public interface InnerQueuedObserverSupport<T> {
    void drain();

    void innerComplete(InnerQueuedObserver<T> inner);

    void innerError(InnerQueuedObserver<T> inner, Throwable e2);

    void innerNext(InnerQueuedObserver<T> inner, T value);
}
