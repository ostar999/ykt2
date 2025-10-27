package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.BlockingHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes8.dex */
public abstract class BlockingBaseObserver<T> extends CountDownLatch implements Observer<T>, Disposable {
    volatile boolean cancelled;
    Throwable error;
    Disposable upstream;
    T value;

    public BlockingBaseObserver() {
        super(1);
    }

    public final T blockingGet() throws InterruptedException {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e2) {
                dispose();
                throw ExceptionHelper.wrapOrThrow(e2);
            }
        }
        Throwable th = this.error;
        if (th == null) {
            return this.value;
        }
        throw ExceptionHelper.wrapOrThrow(th);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        this.cancelled = true;
        Disposable disposable = this.upstream;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return this.cancelled;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onComplete() {
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onSubscribe(Disposable d3) {
        this.upstream = d3;
        if (this.cancelled) {
            d3.dispose();
        }
    }
}
