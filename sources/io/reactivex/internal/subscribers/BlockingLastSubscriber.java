package io.reactivex.internal.subscribers;

/* loaded from: classes8.dex */
public final class BlockingLastSubscriber<T> extends BlockingBaseSubscriber<T> {
    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        this.value = null;
        this.error = th;
        countDown();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        this.value = t2;
    }
}
