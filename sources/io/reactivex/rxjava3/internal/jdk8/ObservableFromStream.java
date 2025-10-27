package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.operators.QueueDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/* loaded from: classes8.dex */
public final class ObservableFromStream<T> extends Observable<T> {
    final Stream<T> stream;

    public static final class StreamDisposable<T> implements QueueDisposable<T> {
        AutoCloseable closeable;
        volatile boolean disposed;
        final Observer<? super T> downstream;
        Iterator<T> iterator;
        boolean once;
        boolean outputFused;

        public StreamDisposable(Observer<? super T> downstream, Iterator<T> iterator, AutoCloseable closeable) {
            this.downstream = downstream;
            this.iterator = iterator;
            this.closeable = closeable;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            this.iterator = null;
            AutoCloseable autoCloseable = this.closeable;
            this.closeable = null;
            if (autoCloseable != null) {
                ObservableFromStream.closeSafely(autoCloseable);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            run();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            Iterator<T> it = this.iterator;
            if (it == null) {
                return true;
            }
            if (!this.once || it.hasNext()) {
                return false;
            }
            clear();
            return true;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(@NonNull T value) {
            throw new UnsupportedOperationException();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            Iterator<T> it = this.iterator;
            if (it == null) {
                return null;
            }
            if (!this.once) {
                this.once = true;
            } else if (!it.hasNext()) {
                clear();
                return null;
            }
            T next = this.iterator.next();
            Objects.requireNonNull(next, "The Stream's Iterator.next() returned a null value");
            return next;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            if ((mode & 1) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 1;
        }

        public void run() {
            if (this.outputFused) {
                return;
            }
            Iterator<T> it = this.iterator;
            Observer<? super T> observer = this.downstream;
            while (!this.disposed) {
                try {
                    T next = it.next();
                    Objects.requireNonNull(next, "The Stream's Iterator.next returned a null value");
                    if (!this.disposed) {
                        observer.onNext(next);
                        if (!this.disposed) {
                            try {
                                if (!it.hasNext()) {
                                    observer.onComplete();
                                    this.disposed = true;
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                observer.onError(th);
                                this.disposed = true;
                            }
                        }
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    observer.onError(th2);
                    this.disposed = true;
                }
            }
            clear();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(@NonNull T v12, @NonNull T v2) {
            throw new UnsupportedOperationException();
        }
    }

    public ObservableFromStream(Stream<T> stream) {
        this.stream = stream;
    }

    public static void closeSafely(AutoCloseable c3) {
        try {
            c3.close();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }

    public static <T> void subscribeStream(Observer<? super T> observer, Stream<T> stream) {
        try {
            Iterator it = stream.iterator();
            if (!it.hasNext()) {
                EmptyDisposable.complete(observer);
                closeSafely(stream);
            } else {
                StreamDisposable streamDisposable = new StreamDisposable(observer, it, stream);
                observer.onSubscribe(streamDisposable);
                streamDisposable.run();
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
            closeSafely(stream);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        subscribeStream(observer, this.stream);
    }
}
