package io.reactivex.rxjava3.internal.subscribers;

/* loaded from: classes8.dex */
public final class BlockingLastSubscriber<T> extends BlockingBaseSubscriber<T> {
    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable t2) {
        this.value = null;
        this.error = t2;
        countDown();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        this.value = t2;
    }
}
