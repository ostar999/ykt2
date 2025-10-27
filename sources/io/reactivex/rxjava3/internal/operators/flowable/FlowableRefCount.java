package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableRefCount<T> extends Flowable<T> {
    RefConnection connection;

    /* renamed from: n, reason: collision with root package name */
    final int f27345n;
    final Scheduler scheduler;
    final ConnectableFlowable<T> source;
    final long timeout;
    final TimeUnit unit;

    public static final class RefConnection extends AtomicReference<Disposable> implements Runnable, Consumer<Disposable> {
        private static final long serialVersionUID = -4552101107598366241L;
        boolean connected;
        boolean disconnectedEarly;
        final FlowableRefCount<?> parent;
        long subscriberCount;
        Disposable timer;

        public RefConnection(FlowableRefCount<?> parent) {
            this.parent = parent;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.parent.timeout(this);
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Disposable t2) {
            DisposableHelper.replace(this, t2);
            synchronized (this.parent) {
                if (this.disconnectedEarly) {
                    this.parent.source.reset();
                }
            }
        }
    }

    public static final class RefCountSubscriber<T> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -7419642935409022375L;
        final RefConnection connection;
        final Subscriber<? super T> downstream;
        final FlowableRefCount<T> parent;
        Subscription upstream;

        public RefCountSubscriber(Subscriber<? super T> actual, FlowableRefCount<T> parent, RefConnection connection) {
            this.downstream = actual;
            this.parent = parent;
            this.connection = connection;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            if (compareAndSet(false, true)) {
                this.parent.cancel(this.connection);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (compareAndSet(false, true)) {
                this.parent.terminated(this.connection);
                this.downstream.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (!compareAndSet(false, true)) {
                RxJavaPlugins.onError(t2);
            } else {
                this.parent.terminated(this.connection);
                this.downstream.onError(t2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            this.upstream.request(n2);
        }
    }

    public FlowableRefCount(ConnectableFlowable<T> source) {
        this(source, 1, 0L, TimeUnit.NANOSECONDS, null);
    }

    public void cancel(RefConnection rc) {
        synchronized (this) {
            RefConnection refConnection = this.connection;
            if (refConnection != null && refConnection == rc) {
                long j2 = rc.subscriberCount - 1;
                rc.subscriberCount = j2;
                if (j2 == 0 && rc.connected) {
                    if (this.timeout == 0) {
                        timeout(rc);
                        return;
                    }
                    SequentialDisposable sequentialDisposable = new SequentialDisposable();
                    rc.timer = sequentialDisposable;
                    sequentialDisposable.replace(this.scheduler.scheduleDirect(rc, this.timeout, this.unit));
                }
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
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
            if (refConnection.connected || j3 != this.f27345n) {
                z2 = false;
            } else {
                z2 = true;
                refConnection.connected = true;
            }
        }
        this.source.subscribe((FlowableSubscriber) new RefCountSubscriber(s2, this, refConnection));
        if (z2) {
            this.source.connect(refConnection);
        }
    }

    public void terminated(RefConnection rc) {
        synchronized (this) {
            if (this.connection == rc) {
                Disposable disposable = rc.timer;
                if (disposable != null) {
                    disposable.dispose();
                    rc.timer = null;
                }
                long j2 = rc.subscriberCount - 1;
                rc.subscriberCount = j2;
                if (j2 == 0) {
                    this.connection = null;
                    this.source.reset();
                }
            }
        }
    }

    public void timeout(RefConnection rc) {
        synchronized (this) {
            if (rc.subscriberCount == 0 && rc == this.connection) {
                this.connection = null;
                Disposable disposable = rc.get();
                DisposableHelper.dispose(rc);
                if (disposable == null) {
                    rc.disconnectedEarly = true;
                } else {
                    this.source.reset();
                }
            }
        }
    }

    public FlowableRefCount(ConnectableFlowable<T> source, int n2, long timeout, TimeUnit unit, Scheduler scheduler) {
        this.source = source;
        this.f27345n = n2;
        this.timeout = timeout;
        this.unit = unit;
        this.scheduler = scheduler;
    }
}
