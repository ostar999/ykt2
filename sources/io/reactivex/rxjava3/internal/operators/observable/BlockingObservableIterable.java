package io.reactivex.rxjava3.internal.operators.observable;

import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.BlockingHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes8.dex */
public final class BlockingObservableIterable<T> implements Iterable<T> {
    final int bufferSize;
    final ObservableSource<? extends T> source;

    public static final class BlockingObservableIterator<T> extends AtomicReference<Disposable> implements Observer<T>, Iterator<T>, Disposable {
        private static final long serialVersionUID = 6695226475494099826L;
        final Condition condition;
        volatile boolean done;
        volatile Throwable error;
        final Lock lock;
        final SpscLinkedArrayQueue<T> queue;

        public BlockingObservableIterator(int batchSize) {
            this.queue = new SpscLinkedArrayQueue<>(batchSize);
            ReentrantLock reentrantLock = new ReentrantLock();
            this.lock = reentrantLock;
            this.condition = reentrantLock.newCondition();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            signalConsumer();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            while (!isDisposed()) {
                boolean z2 = this.done;
                boolean zIsEmpty = this.queue.isEmpty();
                if (z2) {
                    Throwable th = this.error;
                    if (th != null) {
                        throw ExceptionHelper.wrapOrThrow(th);
                    }
                    if (zIsEmpty) {
                        return false;
                    }
                }
                if (!zIsEmpty) {
                    return true;
                }
                try {
                    BlockingHelper.verifyNonBlocking();
                    this.lock.lock();
                    while (!this.done && this.queue.isEmpty() && !isDisposed()) {
                        try {
                            this.condition.await();
                        } finally {
                        }
                    }
                    this.lock.unlock();
                } catch (InterruptedException e2) {
                    DisposableHelper.dispose(this);
                    signalConsumer();
                    throw ExceptionHelper.wrapOrThrow(e2);
                }
            }
            Throwable th2 = this.error;
            if (th2 == null) {
                return false;
            }
            throw ExceptionHelper.wrapOrThrow(th2);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // java.util.Iterator
        public T next() {
            if (hasNext()) {
                return this.queue.poll();
            }
            throw new NoSuchElementException();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            signalConsumer();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            this.error = t2;
            this.done = true;
            signalConsumer();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            this.queue.offer(t2);
            signalConsumer();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            DisposableHelper.setOnce(this, d3);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException(PLVRemoveMicSiteEvent.EVENT_NAME);
        }

        public void signalConsumer() {
            this.lock.lock();
            try {
                this.condition.signalAll();
            } finally {
                this.lock.unlock();
            }
        }
    }

    public BlockingObservableIterable(ObservableSource<? extends T> source, int bufferSize) {
        this.source = source;
        this.bufferSize = bufferSize;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        BlockingObservableIterator blockingObservableIterator = new BlockingObservableIterator(this.bufferSize);
        this.source.subscribe(blockingObservableIterator);
        return blockingObservableIterator;
    }
}
