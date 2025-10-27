package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.subjects.UnicastSubject;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public final class ObservableWindow<T> extends AbstractObservableWithUpstream<T, Observable<T>> {
    final int capacityHint;
    final long count;
    final long skip;

    public static final class WindowExactObserver<T> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        private static final long serialVersionUID = -7481782523886138128L;
        final AtomicBoolean cancelled = new AtomicBoolean();
        final int capacityHint;
        final long count;
        final Observer<? super Observable<T>> downstream;
        long size;
        Disposable upstream;
        UnicastSubject<T> window;

        public WindowExactObserver(Observer<? super Observable<T>> actual, long count, int capacityHint) {
            this.downstream = actual;
            this.count = count;
            this.capacityHint = capacityHint;
            lazySet(1);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled.get();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            UnicastSubject<T> unicastSubject = this.window;
            if (unicastSubject != null) {
                this.window = null;
                unicastSubject.onComplete();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            UnicastSubject<T> unicastSubject = this.window;
            if (unicastSubject != null) {
                this.window = null;
                unicastSubject.onError(t2);
            }
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            ObservableWindowSubscribeIntercept observableWindowSubscribeIntercept;
            UnicastSubject<T> unicastSubjectCreate = this.window;
            if (unicastSubjectCreate != null || this.cancelled.get()) {
                observableWindowSubscribeIntercept = null;
            } else {
                getAndIncrement();
                unicastSubjectCreate = UnicastSubject.create(this.capacityHint, this);
                this.window = unicastSubjectCreate;
                observableWindowSubscribeIntercept = new ObservableWindowSubscribeIntercept(unicastSubjectCreate);
                this.downstream.onNext(observableWindowSubscribeIntercept);
            }
            if (unicastSubjectCreate != null) {
                unicastSubjectCreate.onNext(t2);
                long j2 = this.size + 1;
                this.size = j2;
                if (j2 >= this.count) {
                    this.size = 0L;
                    this.window = null;
                    unicastSubjectCreate.onComplete();
                }
                if (observableWindowSubscribeIntercept == null || !observableWindowSubscribeIntercept.tryAbandon()) {
                    return;
                }
                this.window = null;
                unicastSubjectCreate.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.upstream.dispose();
            }
        }
    }

    public static final class WindowSkipObserver<T> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        private static final long serialVersionUID = 3366976432059579510L;
        final int capacityHint;
        final long count;
        final Observer<? super Observable<T>> downstream;
        long firstEmission;
        long index;
        final long skip;
        Disposable upstream;
        final ArrayDeque<UnicastSubject<T>> windows = new ArrayDeque<>();
        final AtomicBoolean cancelled = new AtomicBoolean();

        public WindowSkipObserver(Observer<? super Observable<T>> actual, long count, long skip, int capacityHint) {
            this.downstream = actual;
            this.count = count;
            this.skip = skip;
            this.capacityHint = capacityHint;
            lazySet(1);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled.get();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            ArrayDeque<UnicastSubject<T>> arrayDeque = this.windows;
            while (!arrayDeque.isEmpty()) {
                arrayDeque.poll().onComplete();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            ArrayDeque<UnicastSubject<T>> arrayDeque = this.windows;
            while (!arrayDeque.isEmpty()) {
                arrayDeque.poll().onError(t2);
            }
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            ObservableWindowSubscribeIntercept observableWindowSubscribeIntercept;
            ArrayDeque<UnicastSubject<T>> arrayDeque = this.windows;
            long j2 = this.index;
            long j3 = this.skip;
            if (j2 % j3 != 0 || this.cancelled.get()) {
                observableWindowSubscribeIntercept = null;
            } else {
                getAndIncrement();
                UnicastSubject<T> unicastSubjectCreate = UnicastSubject.create(this.capacityHint, this);
                observableWindowSubscribeIntercept = new ObservableWindowSubscribeIntercept(unicastSubjectCreate);
                arrayDeque.offer(unicastSubjectCreate);
                this.downstream.onNext(observableWindowSubscribeIntercept);
            }
            long j4 = this.firstEmission + 1;
            Iterator<UnicastSubject<T>> it = arrayDeque.iterator();
            while (it.hasNext()) {
                it.next().onNext(t2);
            }
            if (j4 >= this.count) {
                arrayDeque.poll().onComplete();
                if (arrayDeque.isEmpty() && this.cancelled.get()) {
                    return;
                } else {
                    this.firstEmission = j4 - j3;
                }
            } else {
                this.firstEmission = j4;
            }
            this.index = j2 + 1;
            if (observableWindowSubscribeIntercept == null || !observableWindowSubscribeIntercept.tryAbandon()) {
                return;
            }
            observableWindowSubscribeIntercept.window.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.upstream.dispose();
            }
        }
    }

    public ObservableWindow(ObservableSource<T> source, long count, long skip, int capacityHint) {
        super(source);
        this.count = count;
        this.skip = skip;
        this.capacityHint = capacityHint;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super Observable<T>> t2) {
        if (this.count == this.skip) {
            this.source.subscribe(new WindowExactObserver(t2, this.count, this.capacityHint));
        } else {
            this.source.subscribe(new WindowSkipObserver(t2, this.count, this.skip, this.capacityHint));
        }
    }
}
