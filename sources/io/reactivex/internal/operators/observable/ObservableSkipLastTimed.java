package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public final class ObservableSkipLastTimed<T> extends AbstractObservableWithUpstream<T, T> {
    final int bufferSize;
    final boolean delayError;
    final Scheduler scheduler;
    final long time;
    final TimeUnit unit;

    public static final class SkipLastTimedObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = -5677354903406201275L;
        volatile boolean cancelled;
        final boolean delayError;
        volatile boolean done;
        final Observer<? super T> downstream;
        Throwable error;
        final SpscLinkedArrayQueue<Object> queue;
        final Scheduler scheduler;
        final long time;
        final TimeUnit unit;
        Disposable upstream;

        public SkipLastTimedObserver(Observer<? super T> observer, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2, boolean z2) {
            this.downstream = observer;
            this.time = j2;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.queue = new SpscLinkedArrayQueue<>(i2);
            this.delayError = z2;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.dispose();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Observer<? super T> observer = this.downstream;
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
            boolean z2 = this.delayError;
            TimeUnit timeUnit = this.unit;
            Scheduler scheduler = this.scheduler;
            long j2 = this.time;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                boolean z3 = this.done;
                Long l2 = (Long) spscLinkedArrayQueue.peek();
                boolean z4 = l2 == null;
                long jNow = scheduler.now(timeUnit);
                if (!z4 && l2.longValue() > jNow - j2) {
                    z4 = true;
                }
                if (z3) {
                    if (!z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            this.queue.clear();
                            observer.onError(th);
                            return;
                        } else if (z4) {
                            observer.onComplete();
                            return;
                        }
                    } else if (z4) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            observer.onError(th2);
                            return;
                        } else {
                            observer.onComplete();
                            return;
                        }
                    }
                }
                if (z4) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    spscLinkedArrayQueue.poll();
                    observer.onNext(spscLinkedArrayQueue.poll());
                }
            }
            this.queue.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            this.queue.offer(Long.valueOf(this.scheduler.now(this.unit)), t2);
            drain();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableSkipLastTimed(ObservableSource<T> observableSource, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2, boolean z2) {
        super(observableSource);
        this.time = j2;
        this.unit = timeUnit;
        this.scheduler = scheduler;
        this.bufferSize = i2;
        this.delayError = z2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new SkipLastTimedObserver(observer, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError));
    }
}
