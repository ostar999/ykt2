package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.observers.QueueDrainObserver;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.observers.SerializedObserver;
import java.util.Collection;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class ObservableBufferExactBoundary<T, U extends Collection<? super T>, B> extends AbstractObservableWithUpstream<T, U> {
    final ObservableSource<B> boundary;
    final Supplier<U> bufferSupplier;

    public static final class BufferBoundaryObserver<T, U extends Collection<? super T>, B> extends DisposableObserver<B> {
        final BufferExactBoundaryObserver<T, U, B> parent;

        public BufferBoundaryObserver(BufferExactBoundaryObserver<T, U, B> parent) {
            this.parent = parent;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.parent.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            this.parent.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(B t2) {
            this.parent.next();
        }
    }

    public static final class BufferExactBoundaryObserver<T, U extends Collection<? super T>, B> extends QueueDrainObserver<T, U, U> implements Observer<T>, Disposable {
        final ObservableSource<B> boundary;
        U buffer;
        final Supplier<U> bufferSupplier;
        Disposable other;
        Disposable upstream;

        public BufferExactBoundaryObserver(Observer<? super U> actual, Supplier<U> bufferSupplier, ObservableSource<B> boundary) {
            super(actual, new MpscLinkedQueue());
            this.bufferSupplier = bufferSupplier;
            this.boundary = boundary;
        }

        @Override // io.reactivex.rxjava3.internal.observers.QueueDrainObserver, io.reactivex.rxjava3.internal.util.ObservableQueueDrain
        public /* bridge */ /* synthetic */ void accept(Observer a3, Object v2) {
            accept((Observer<? super Observer>) a3, (Observer) v2);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.other.dispose();
            this.upstream.dispose();
            if (enter()) {
                this.queue.clear();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        public void next() {
            try {
                U u2 = this.bufferSupplier.get();
                Objects.requireNonNull(u2, "The buffer supplied is null");
                U u3 = u2;
                synchronized (this) {
                    U u4 = this.buffer;
                    if (u4 == null) {
                        return;
                    }
                    this.buffer = u3;
                    fastPathEmit(u4, false, this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            synchronized (this) {
                U u2 = this.buffer;
                if (u2 == null) {
                    return;
                }
                this.buffer = null;
                this.queue.offer(u2);
                this.done = true;
                if (enter()) {
                    QueueDrainHelper.drainLoop(this.queue, this.downstream, false, this, this);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            dispose();
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            synchronized (this) {
                U u2 = this.buffer;
                if (u2 == null) {
                    return;
                }
                u2.add(t2);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                try {
                    U u2 = this.bufferSupplier.get();
                    Objects.requireNonNull(u2, "The buffer supplied is null");
                    this.buffer = u2;
                    BufferBoundaryObserver bufferBoundaryObserver = new BufferBoundaryObserver(this);
                    this.other = bufferBoundaryObserver;
                    this.downstream.onSubscribe(this);
                    if (this.cancelled) {
                        return;
                    }
                    this.boundary.subscribe(bufferBoundaryObserver);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.cancelled = true;
                    d3.dispose();
                    EmptyDisposable.error(th, this.downstream);
                }
            }
        }

        public void accept(Observer<? super U> a3, U v2) {
            this.downstream.onNext(v2);
        }
    }

    public ObservableBufferExactBoundary(ObservableSource<T> source, ObservableSource<B> boundary, Supplier<U> bufferSupplier) {
        super(source);
        this.boundary = boundary;
        this.bufferSupplier = bufferSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super U> t2) {
        this.source.subscribe(new BufferExactBoundaryObserver(new SerializedObserver(t2), this.bufferSupplier, this.boundary));
    }
}
