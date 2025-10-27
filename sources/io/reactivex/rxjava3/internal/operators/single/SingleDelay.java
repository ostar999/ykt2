package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public final class SingleDelay<T> extends Single<T> {
    final boolean delayError;
    final Scheduler scheduler;
    final SingleSource<? extends T> source;
    final long time;
    final TimeUnit unit;

    public final class Delay implements SingleObserver<T> {
        final SingleObserver<? super T> downstream;
        private final SequentialDisposable sd;

        public final class OnError implements Runnable {

            /* renamed from: e, reason: collision with root package name */
            private final Throwable f27365e;

            public OnError(Throwable e2) {
                this.f27365e = e2;
            }

            @Override // java.lang.Runnable
            public void run() {
                Delay.this.downstream.onError(this.f27365e);
            }
        }

        public final class OnSuccess implements Runnable {
            private final T value;

            public OnSuccess(T value) {
                this.value = value;
            }

            @Override // java.lang.Runnable
            public void run() {
                Delay.this.downstream.onSuccess(this.value);
            }
        }

        public Delay(SequentialDisposable sd, SingleObserver<? super T> observer) {
            this.sd = sd;
            this.downstream = observer;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(final Throwable e2) {
            SequentialDisposable sequentialDisposable = this.sd;
            Scheduler scheduler = SingleDelay.this.scheduler;
            OnError onError = new OnError(e2);
            SingleDelay singleDelay = SingleDelay.this;
            sequentialDisposable.replace(scheduler.scheduleDirect(onError, singleDelay.delayError ? singleDelay.time : 0L, singleDelay.unit));
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.sd.replace(d3);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(final T value) {
            SequentialDisposable sequentialDisposable = this.sd;
            Scheduler scheduler = SingleDelay.this.scheduler;
            OnSuccess onSuccess = new OnSuccess(value);
            SingleDelay singleDelay = SingleDelay.this;
            sequentialDisposable.replace(scheduler.scheduleDirect(onSuccess, singleDelay.time, singleDelay.unit));
        }
    }

    public SingleDelay(SingleSource<? extends T> source, long time, TimeUnit unit, Scheduler scheduler, boolean delayError) {
        this.source = source;
        this.time = time;
        this.unit = unit;
        this.scheduler = scheduler;
        this.delayError = delayError;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(final SingleObserver<? super T> observer) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        observer.onSubscribe(sequentialDisposable);
        this.source.subscribe(new Delay(sequentialDisposable, observer));
    }
}
