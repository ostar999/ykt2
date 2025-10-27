package io.reactivex.rxjava3.internal.observers;

/* loaded from: classes8.dex */
public final class BlockingLastObserver<T> extends BlockingBaseObserver<T> {
    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable t2) {
        this.value = null;
        this.error = t2;
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t2) {
        this.value = t2;
    }
}
