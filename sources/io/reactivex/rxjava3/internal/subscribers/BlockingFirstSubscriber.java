package io.reactivex.rxjava3.internal.subscribers;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class BlockingFirstSubscriber<T> extends BlockingBaseSubscriber<T> {
    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable t2) {
        if (this.value == null) {
            this.error = t2;
        } else {
            RxJavaPlugins.onError(t2);
        }
        countDown();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        if (this.value == null) {
            this.value = t2;
            this.upstream.cancel();
            countDown();
        }
    }
}
