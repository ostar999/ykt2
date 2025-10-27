package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableInterval extends Observable<Long> {
    final long initialDelay;
    final long period;
    final Scheduler scheduler;
    final TimeUnit unit;

    public static final class IntervalObserver extends AtomicReference<Disposable> implements Disposable, Runnable {
        private static final long serialVersionUID = 346773832286157679L;
        long count;
        final Observer<? super Long> downstream;

        public IntervalObserver(Observer<? super Long> observer) {
            this.downstream = observer;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (get() != DisposableHelper.DISPOSED) {
                Observer<? super Long> observer = this.downstream;
                long j2 = this.count;
                this.count = 1 + j2;
                observer.onNext(Long.valueOf(j2));
            }
        }

        public void setResource(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    public ObservableInterval(long j2, long j3, TimeUnit timeUnit, Scheduler scheduler) {
        this.initialDelay = j2;
        this.period = j3;
        this.unit = timeUnit;
        this.scheduler = scheduler;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Long> observer) {
        IntervalObserver intervalObserver = new IntervalObserver(observer);
        observer.onSubscribe(intervalObserver);
        Scheduler scheduler = this.scheduler;
        if (!(scheduler instanceof TrampolineScheduler)) {
            intervalObserver.setResource(scheduler.schedulePeriodicallyDirect(intervalObserver, this.initialDelay, this.period, this.unit));
            return;
        }
        Scheduler.Worker workerCreateWorker = scheduler.createWorker();
        intervalObserver.setResource(workerCreateWorker);
        workerCreateWorker.schedulePeriodically(intervalObserver, this.initialDelay, this.period, this.unit);
    }
}
