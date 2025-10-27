package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes8.dex */
public final class ObservableBuffer<T, U extends Collection<? super T>> extends AbstractObservableWithUpstream<T, U> {
    final Callable<U> bufferSupplier;
    final int count;
    final int skip;

    public static final class BufferExactObserver<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
        U buffer;
        final Callable<U> bufferSupplier;
        final int count;
        final Observer<? super U> downstream;
        int size;
        Disposable upstream;

        public BufferExactObserver(Observer<? super U> observer, int i2, Callable<U> callable) {
            this.downstream = observer;
            this.count = i2;
            this.bufferSupplier = callable;
        }

        public boolean createBuffer() {
            try {
                this.buffer = (U) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "Empty buffer supplied");
                return true;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.buffer = null;
                Disposable disposable = this.upstream;
                if (disposable == null) {
                    EmptyDisposable.error(th, this.downstream);
                    return false;
                }
                disposable.dispose();
                this.downstream.onError(th);
                return false;
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            U u2 = this.buffer;
            if (u2 != null) {
                this.buffer = null;
                if (!u2.isEmpty()) {
                    this.downstream.onNext(u2);
                }
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.buffer = null;
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            U u2 = this.buffer;
            if (u2 != null) {
                u2.add(t2);
                int i2 = this.size + 1;
                this.size = i2;
                if (i2 >= this.count) {
                    this.downstream.onNext(u2);
                    this.size = 0;
                    createBuffer();
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public static final class BufferSkipObserver<T, U extends Collection<? super T>> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = -8223395059921494546L;
        final Callable<U> bufferSupplier;
        final ArrayDeque<U> buffers = new ArrayDeque<>();
        final int count;
        final Observer<? super U> downstream;
        long index;
        final int skip;
        Disposable upstream;

        public BufferSkipObserver(Observer<? super U> observer, int i2, int i3, Callable<U> callable) {
            this.downstream = observer;
            this.count = i2;
            this.skip = i3;
            this.bufferSupplier = callable;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            while (!this.buffers.isEmpty()) {
                this.downstream.onNext(this.buffers.poll());
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.buffers.clear();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            long j2 = this.index;
            this.index = 1 + j2;
            if (j2 % this.skip == 0) {
                try {
                    this.buffers.offer((Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources."));
                } catch (Throwable th) {
                    this.buffers.clear();
                    this.upstream.dispose();
                    this.downstream.onError(th);
                    return;
                }
            }
            Iterator<U> it = this.buffers.iterator();
            while (it.hasNext()) {
                U next = it.next();
                next.add(t2);
                if (this.count <= next.size()) {
                    it.remove();
                    this.downstream.onNext(next);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableBuffer(ObservableSource<T> observableSource, int i2, int i3, Callable<U> callable) {
        super(observableSource);
        this.count = i2;
        this.skip = i3;
        this.bufferSupplier = callable;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super U> observer) {
        int i2 = this.skip;
        int i3 = this.count;
        if (i2 != i3) {
            this.source.subscribe(new BufferSkipObserver(observer, this.count, this.skip, this.bufferSupplier));
            return;
        }
        BufferExactObserver bufferExactObserver = new BufferExactObserver(observer, i3, this.bufferSupplier);
        if (bufferExactObserver.createBuffer()) {
            this.source.subscribe(bufferExactObserver);
        }
    }
}
