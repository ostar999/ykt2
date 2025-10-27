package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.b;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.util.BlockingHelper;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes8.dex */
public final class BlockingDisposableMultiObserver<T> extends CountDownLatch implements MaybeObserver<T>, SingleObserver<T>, CompletableObserver, Disposable {
    Throwable error;
    final SequentialDisposable upstream;
    T value;

    public BlockingDisposableMultiObserver() {
        super(1);
        this.upstream = new SequentialDisposable();
    }

    public void blockingConsume(CompletableObserver observer) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e2) {
                dispose();
                observer.onError(e2);
                return;
            }
        }
        if (isDisposed()) {
            return;
        }
        Throwable th = this.error;
        if (th != null) {
            observer.onError(th);
        } else {
            observer.onComplete();
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        this.upstream.dispose();
        countDown();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.upstream.isDisposed();
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onComplete() {
        this.upstream.lazySet(b.a());
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onError(@NonNull Throwable e2) {
        this.error = e2;
        this.upstream.lazySet(b.a());
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onSubscribe(@NonNull Disposable d3) {
        DisposableHelper.setOnce(this.upstream, d3);
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
    public void onSuccess(@NonNull T t2) {
        this.value = t2;
        this.upstream.lazySet(b.a());
        countDown();
    }

    public void blockingConsume(SingleObserver<? super T> singleObserver) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e2) {
                dispose();
                singleObserver.onError(e2);
                return;
            }
        }
        if (isDisposed()) {
            return;
        }
        Throwable th = this.error;
        if (th != null) {
            singleObserver.onError(th);
        } else {
            singleObserver.onSuccess(this.value);
        }
    }

    public void blockingConsume(MaybeObserver<? super T> maybeObserver) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e2) {
                dispose();
                maybeObserver.onError(e2);
                return;
            }
        }
        if (isDisposed()) {
            return;
        }
        Throwable th = this.error;
        if (th != null) {
            maybeObserver.onError(th);
            return;
        }
        T t2 = this.value;
        if (t2 == null) {
            maybeObserver.onComplete();
        } else {
            maybeObserver.onSuccess(t2);
        }
    }
}
