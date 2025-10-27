package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.observers.DefaultObserver;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes8.dex */
public final class BlockingObservableMostRecent<T> implements Iterable<T> {
    final T initialValue;
    final ObservableSource<T> source;

    public static final class MostRecentObserver<T> extends DefaultObserver<T> {
        volatile Object value;

        public final class MostRecentIterator implements Iterator<T> {
            private Object buf;

            public MostRecentIterator() {
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                this.buf = MostRecentObserver.this.value;
                return !NotificationLite.isComplete(r0);
            }

            @Override // java.util.Iterator
            public T next() {
                try {
                    if (this.buf == null) {
                        this.buf = MostRecentObserver.this.value;
                    }
                    if (NotificationLite.isComplete(this.buf)) {
                        throw new NoSuchElementException();
                    }
                    if (NotificationLite.isError(this.buf)) {
                        throw ExceptionHelper.wrapOrThrow(NotificationLite.getError(this.buf));
                    }
                    return (T) NotificationLite.getValue(this.buf);
                } finally {
                    this.buf = null;
                }
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Read only iterator");
            }
        }

        public MostRecentObserver(T value) {
            this.value = NotificationLite.next(value);
        }

        public MostRecentObserver<T>.MostRecentIterator getIterable() {
            return new MostRecentIterator();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.value = NotificationLite.complete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            this.value = NotificationLite.error(e2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T args) {
            this.value = NotificationLite.next(args);
        }
    }

    public BlockingObservableMostRecent(ObservableSource<T> source, T initialValue) {
        this.source = source;
        this.initialValue = initialValue;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        MostRecentObserver mostRecentObserver = new MostRecentObserver(this.initialValue);
        this.source.subscribe(mostRecentObserver);
        return mostRecentObserver.getIterable();
    }
}
