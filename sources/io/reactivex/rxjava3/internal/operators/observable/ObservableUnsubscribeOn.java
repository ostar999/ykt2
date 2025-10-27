package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes8.dex */
public final class ObservableUnsubscribeOn<T> extends AbstractObservableWithUpstream<T, T> {
    final Scheduler scheduler;

    public static final class UnsubscribeObserver<T> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = 1015244841293359600L;
        final Observer<? super T> downstream;
        final Scheduler scheduler;
        Disposable upstream;

        public final class DisposeTask implements Runnable {
            public DisposeTask() {
            }

            @Override // java.lang.Runnable
            public void run() {
                UnsubscribeObserver.this.upstream.dispose();
            }
        }

        public UnsubscribeObserver(Observer<? super T> actual, Scheduler scheduler) {
            this.downstream = actual;
            this.scheduler = scheduler;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (compareAndSet(false, true)) {
                this.scheduler.scheduleDirect(new DisposeTask());
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (get()) {
                return;
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            if (get()) {
                RxJavaPlugins.onError(t2);
            } else {
                this.downstream.onError(t2);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            if (get()) {
                return;
            }
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableUnsubscribeOn(ObservableSource<T> source, Scheduler scheduler) {
        super(source);
        this.scheduler = scheduler;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> t2) {
        this.source.subscribe(new UnsubscribeObserver(t2, this.scheduler));
    }
}
