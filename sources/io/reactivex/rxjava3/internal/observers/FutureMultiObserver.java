package io.reactivex.rxjava3.internal.observers;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.BlockingHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class FutureMultiObserver<T> extends CountDownLatch implements MaybeObserver<T>, SingleObserver<T>, CompletableObserver, Future<T>, Disposable {
    Throwable error;
    final AtomicReference<Disposable> upstream;
    T value;

    public FutureMultiObserver() {
        super(1);
        this.upstream = new AtomicReference<>();
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean mayInterruptIfRunning) {
        Disposable disposable;
        DisposableHelper disposableHelper;
        do {
            disposable = this.upstream.get();
            if (disposable == this || disposable == (disposableHelper = DisposableHelper.DISPOSED)) {
                return false;
            }
        } while (!j.a(this.upstream, disposable, disposableHelper));
        if (disposable != null) {
            disposable.dispose();
        }
        countDown();
        return true;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
    }

    @Override // java.util.concurrent.Future
    public T get() throws ExecutionException, InterruptedException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            await();
        }
        if (isCancelled()) {
            throw new CancellationException();
        }
        Throwable th = this.error;
        if (th == null) {
            return this.value;
        }
        throw new ExecutionException(th);
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return DisposableHelper.isDisposed(this.upstream.get());
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return isDone();
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return getCount() == 0;
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onComplete() {
        Disposable disposable = this.upstream.get();
        if (disposable == DisposableHelper.DISPOSED) {
            return;
        }
        j.a(this.upstream, disposable, this);
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onError(Throwable t2) {
        Disposable disposable;
        do {
            disposable = this.upstream.get();
            if (disposable == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.error = t2;
        } while (!j.a(this.upstream, disposable, this));
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onSubscribe(Disposable d3) {
        DisposableHelper.setOnce(this.upstream, d3);
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
    public void onSuccess(T t2) {
        Disposable disposable = this.upstream.get();
        if (disposable == DisposableHelper.DISPOSED) {
            return;
        }
        this.value = t2;
        j.a(this.upstream, disposable, this);
        countDown();
    }

    @Override // java.util.concurrent.Future
    public T get(long timeout, @NonNull TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            if (!await(timeout, unit)) {
                throw new TimeoutException(ExceptionHelper.timeoutMessage(timeout, unit));
            }
        }
        if (!isCancelled()) {
            Throwable th = this.error;
            if (th == null) {
                return this.value;
            }
            throw new ExecutionException(th);
        }
        throw new CancellationException();
    }
}
