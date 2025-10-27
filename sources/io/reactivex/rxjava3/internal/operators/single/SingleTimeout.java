package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class SingleTimeout<T> extends Single<T> {
    final SingleSource<? extends T> other;
    final Scheduler scheduler;
    final SingleSource<T> source;
    final long timeout;
    final TimeUnit unit;

    public static final class TimeoutMainObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Runnable, Disposable {
        private static final long serialVersionUID = 37497744973048446L;
        final SingleObserver<? super T> downstream;
        final TimeoutFallbackObserver<T> fallback;
        SingleSource<? extends T> other;
        final AtomicReference<Disposable> task = new AtomicReference<>();
        final long timeout;
        final TimeUnit unit;

        public static final class TimeoutFallbackObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
            private static final long serialVersionUID = 2071387740092105509L;
            final SingleObserver<? super T> downstream;

            public TimeoutFallbackObserver(SingleObserver<? super T> downstream) {
                this.downstream = downstream;
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                this.downstream.onError(e2);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.setOnce(this, d3);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(T t2) {
                this.downstream.onSuccess(t2);
            }
        }

        public TimeoutMainObserver(SingleObserver<? super T> actual, SingleSource<? extends T> other, long timeout, TimeUnit unit) {
            this.downstream = actual;
            this.other = other;
            this.timeout = timeout;
            this.unit = unit;
            if (other != null) {
                this.fallback = new TimeoutFallbackObserver<>(actual);
            } else {
                this.fallback = null;
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            DisposableHelper.dispose(this.task);
            TimeoutFallbackObserver<T> timeoutFallbackObserver = this.fallback;
            if (timeoutFallbackObserver != null) {
                DisposableHelper.dispose(timeoutFallbackObserver);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper || !compareAndSet(disposable, disposableHelper)) {
                RxJavaPlugins.onError(e2);
            } else {
                DisposableHelper.dispose(this.task);
                this.downstream.onError(e2);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            DisposableHelper.setOnce(this, d3);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t2) {
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper || !compareAndSet(disposable, disposableHelper)) {
                return;
            }
            DisposableHelper.dispose(this.task);
            this.downstream.onSuccess(t2);
        }

        @Override // java.lang.Runnable
        public void run() {
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper || !compareAndSet(disposable, disposableHelper)) {
                return;
            }
            if (disposable != null) {
                disposable.dispose();
            }
            SingleSource<? extends T> singleSource = this.other;
            if (singleSource == null) {
                this.downstream.onError(new TimeoutException(ExceptionHelper.timeoutMessage(this.timeout, this.unit)));
            } else {
                this.other = null;
                singleSource.subscribe(this.fallback);
            }
        }
    }

    public SingleTimeout(SingleSource<T> source, long timeout, TimeUnit unit, Scheduler scheduler, SingleSource<? extends T> other) {
        this.source = source;
        this.timeout = timeout;
        this.unit = unit;
        this.scheduler = scheduler;
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(final SingleObserver<? super T> observer) {
        TimeoutMainObserver timeoutMainObserver = new TimeoutMainObserver(observer, this.other, this.timeout, this.unit);
        observer.onSubscribe(timeoutMainObserver);
        DisposableHelper.replace(timeoutMainObserver.task, this.scheduler.scheduleDirect(timeoutMainObserver, this.timeout, this.unit));
        this.source.subscribe(timeoutMainObserver);
    }
}
