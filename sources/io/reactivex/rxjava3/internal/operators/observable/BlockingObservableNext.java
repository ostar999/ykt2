package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.internal.util.BlockingHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public final class BlockingObservableNext<T> implements Iterable<T> {
    final ObservableSource<T> source;

    public static final class NextIterator<T> implements Iterator<T> {
        private Throwable error;
        private boolean hasNext = true;
        private boolean isNextConsumed = true;
        private final ObservableSource<T> items;
        private T next;
        private final NextObserver<T> observer;
        private boolean started;

        public NextIterator(ObservableSource<T> items, NextObserver<T> observer) {
            this.items = items;
            this.observer = observer;
        }

        private boolean moveToNext() {
            if (!this.started) {
                this.started = true;
                this.observer.setWaiting();
                new ObservableMaterialize(this.items).subscribe(this.observer);
            }
            try {
                Notification<T> notificationTakeNext = this.observer.takeNext();
                if (notificationTakeNext.isOnNext()) {
                    this.isNextConsumed = false;
                    this.next = notificationTakeNext.getValue();
                    return true;
                }
                this.hasNext = false;
                if (notificationTakeNext.isOnComplete()) {
                    return false;
                }
                Throwable error = notificationTakeNext.getError();
                this.error = error;
                throw ExceptionHelper.wrapOrThrow(error);
            } catch (InterruptedException e2) {
                this.observer.dispose();
                this.error = e2;
                throw ExceptionHelper.wrapOrThrow(e2);
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            Throwable th = this.error;
            if (th != null) {
                throw ExceptionHelper.wrapOrThrow(th);
            }
            if (this.hasNext) {
                return !this.isNextConsumed || moveToNext();
            }
            return false;
        }

        @Override // java.util.Iterator
        public T next() {
            Throwable th = this.error;
            if (th != null) {
                throw ExceptionHelper.wrapOrThrow(th);
            }
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            this.isNextConsumed = true;
            return this.next;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Read only iterator");
        }
    }

    public static final class NextObserver<T> extends DisposableObserver<Notification<T>> {
        private final BlockingQueue<Notification<T>> buf = new ArrayBlockingQueue(1);
        final AtomicInteger waiting = new AtomicInteger();

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            RxJavaPlugins.onError(e2);
        }

        public void setWaiting() {
            this.waiting.set(1);
        }

        public Notification<T> takeNext() throws InterruptedException {
            setWaiting();
            BlockingHelper.verifyNonBlocking();
            return this.buf.take();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Notification<T> args) {
            if (this.waiting.getAndSet(0) == 1 || !args.isOnNext()) {
                while (!this.buf.offer(args)) {
                    Notification<T> notificationPoll = this.buf.poll();
                    if (notificationPoll != null && !notificationPoll.isOnNext()) {
                        args = notificationPoll;
                    }
                }
            }
        }
    }

    public BlockingObservableNext(ObservableSource<T> source) {
        this.source = source;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return new NextIterator(this.source, new NextObserver());
    }
}
