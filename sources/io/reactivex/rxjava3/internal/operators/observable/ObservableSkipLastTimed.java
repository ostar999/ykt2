package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
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

        public SkipLastTimedObserver(Observer<? super T> actual, long time, TimeUnit unit, Scheduler scheduler, int bufferSize, boolean delayError) {
            this.downstream = actual;
            this.time = time;
            this.unit = unit;
            this.scheduler = scheduler;
            this.queue = new SpscLinkedArrayQueue<>(bufferSize);
            this.delayError = delayError;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
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

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            this.error = t2;
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            this.queue.offer(Long.valueOf(this.scheduler.now(this.unit)), t2);
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableSkipLastTimed(ObservableSource<T> source, long time, TimeUnit unit, Scheduler scheduler, int bufferSize, boolean delayError) {
        super(source);
        this.time = time;
        this.unit = unit;
        this.scheduler = scheduler;
        this.bufferSize = bufferSize;
        this.delayError = delayError;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> t2) {
        this.source.subscribe(new SkipLastTimedObserver(t2, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError));
    }
}
