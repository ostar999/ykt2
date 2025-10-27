package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableRefCount<T> extends Observable<T> {
    RefConnection connection;

    /* renamed from: n, reason: collision with root package name */
    final int f27309n;
    final Scheduler scheduler;
    final ConnectableObservable<T> source;
    final long timeout;
    final TimeUnit unit;

    public static final class RefConnection extends AtomicReference<Disposable> implements Runnable, Consumer<Disposable> {
        private static final long serialVersionUID = -4552101107598366241L;
        boolean connected;
        boolean disconnectedEarly;
        final ObservableRefCount<?> parent;
        long subscriberCount;
        Disposable timer;

        public RefConnection(ObservableRefCount<?> observableRefCount) {
            this.parent = observableRefCount;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.parent.timeout(this);
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Disposable disposable) throws Exception {
            DisposableHelper.replace(this, disposable);
            synchronized (this.parent) {
                if (this.disconnectedEarly) {
                    ((ResettableConnectable) this.parent.source).resetIf(disposable);
                }
            }
        }
    }

    public static final class RefCountObserver<T> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = -7419642935409022375L;
        final RefConnection connection;
        final Observer<? super T> downstream;
        final ObservableRefCount<T> parent;
        Disposable upstream;

        public RefCountObserver(Observer<? super T> observer, ObservableRefCount<T> observableRefCount, RefConnection refConnection) {
            this.downstream = observer;
            this.parent = observableRefCount;
            this.connection = refConnection;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            if (compareAndSet(false, true)) {
                this.parent.cancel(this.connection);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (compareAndSet(false, true)) {
                this.parent.terminated(this.connection);
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!compareAndSet(false, true)) {
                RxJavaPlugins.onError(th);
            } else {
                this.parent.terminated(this.connection);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableRefCount(ConnectableObservable<T> connectableObservable) {
        this(connectableObservable, 1, 0L, TimeUnit.NANOSECONDS, Schedulers.trampoline());
    }

    public void cancel(RefConnection refConnection) {
        synchronized (this) {
            RefConnection refConnection2 = this.connection;
            if (refConnection2 != null && refConnection2 == refConnection) {
                long j2 = refConnection.subscriberCount - 1;
                refConnection.subscriberCount = j2;
                if (j2 == 0 && refConnection.connected) {
                    if (this.timeout == 0) {
                        timeout(refConnection);
                        return;
                    }
                    SequentialDisposable sequentialDisposable = new SequentialDisposable();
                    refConnection.timer = sequentialDisposable;
                    sequentialDisposable.replace(this.scheduler.scheduleDirect(refConnection, this.timeout, this.unit));
                }
            }
        }
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        RefConnection refConnection;
        boolean z2;
        Disposable disposable;
        synchronized (this) {
            refConnection = this.connection;
            if (refConnection == null) {
                refConnection = new RefConnection(this);
                this.connection = refConnection;
            }
            long j2 = refConnection.subscriberCount;
            if (j2 == 0 && (disposable = refConnection.timer) != null) {
                disposable.dispose();
            }
            long j3 = j2 + 1;
            refConnection.subscriberCount = j3;
            if (refConnection.connected || j3 != this.f27309n) {
                z2 = false;
            } else {
                z2 = true;
                refConnection.connected = true;
            }
        }
        this.source.subscribe(new RefCountObserver(observer, this, refConnection));
        if (z2) {
            this.source.connect(refConnection);
        }
    }

    public void terminated(RefConnection refConnection) {
        synchronized (this) {
            RefConnection refConnection2 = this.connection;
            if (refConnection2 != null && refConnection2 == refConnection) {
                this.connection = null;
                Disposable disposable = refConnection.timer;
                if (disposable != null) {
                    disposable.dispose();
                }
            }
            long j2 = refConnection.subscriberCount - 1;
            refConnection.subscriberCount = j2;
            if (j2 == 0) {
                ConnectableObservable<T> connectableObservable = this.source;
                if (connectableObservable instanceof Disposable) {
                    ((Disposable) connectableObservable).dispose();
                } else if (connectableObservable instanceof ResettableConnectable) {
                    ((ResettableConnectable) connectableObservable).resetIf(refConnection.get());
                }
            }
        }
    }

    public void timeout(RefConnection refConnection) {
        synchronized (this) {
            if (refConnection.subscriberCount == 0 && refConnection == this.connection) {
                this.connection = null;
                Disposable disposable = refConnection.get();
                DisposableHelper.dispose(refConnection);
                ConnectableObservable<T> connectableObservable = this.source;
                if (connectableObservable instanceof Disposable) {
                    ((Disposable) connectableObservable).dispose();
                } else if (connectableObservable instanceof ResettableConnectable) {
                    if (disposable == null) {
                        refConnection.disconnectedEarly = true;
                    } else {
                        ((ResettableConnectable) connectableObservable).resetIf(disposable);
                    }
                }
            }
        }
    }

    public ObservableRefCount(ConnectableObservable<T> connectableObservable, int i2, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        this.source = connectableObservable;
        this.f27309n = i2;
        this.timeout = j2;
        this.unit = timeUnit;
        this.scheduler = scheduler;
    }
}
