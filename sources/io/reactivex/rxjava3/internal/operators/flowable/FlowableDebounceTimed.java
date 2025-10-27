package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableDebounceTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final Scheduler scheduler;
    final long timeout;
    final TimeUnit unit;

    public static final class DebounceEmitter<T> extends AtomicReference<Disposable> implements Runnable, Disposable {
        private static final long serialVersionUID = 6812032969491025141L;
        final long idx;
        final AtomicBoolean once = new AtomicBoolean();
        final DebounceTimedSubscriber<T> parent;
        final T value;

        public DebounceEmitter(T value, long idx, DebounceTimedSubscriber<T> parent) {
            this.value = value;
            this.idx = idx;
            this.parent = parent;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public void emit() {
            if (this.once.compareAndSet(false, true)) {
                this.parent.emit(this.idx, this.value, this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        @Override // java.lang.Runnable
        public void run() {
            emit();
        }

        public void setResource(Disposable d3) {
            DisposableHelper.replace(this, d3);
        }
    }

    public static final class DebounceTimedSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -9102637559663639004L;
        boolean done;
        final Subscriber<? super T> downstream;
        volatile long index;
        final long timeout;
        Disposable timer;
        final TimeUnit unit;
        Subscription upstream;
        final Scheduler.Worker worker;

        public DebounceTimedSubscriber(Subscriber<? super T> actual, long timeout, TimeUnit unit, Scheduler.Worker worker) {
            this.downstream = actual;
            this.timeout = timeout;
            this.unit = unit;
            this.worker = worker;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            this.worker.dispose();
        }

        public void emit(long idx, T t2, DebounceEmitter<T> emitter) {
            if (idx == this.index) {
                if (get() == 0) {
                    cancel();
                    this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
                } else {
                    this.downstream.onNext(t2);
                    BackpressureHelper.produced(this, 1L);
                    emitter.dispose();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            Disposable disposable = this.timer;
            if (disposable != null) {
                disposable.dispose();
            }
            DebounceEmitter debounceEmitter = (DebounceEmitter) disposable;
            if (debounceEmitter != null) {
                debounceEmitter.emit();
            }
            this.downstream.onComplete();
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.done = true;
            Disposable disposable = this.timer;
            if (disposable != null) {
                disposable.dispose();
            }
            this.downstream.onError(t2);
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            long j2 = this.index + 1;
            this.index = j2;
            Disposable disposable = this.timer;
            if (disposable != null) {
                disposable.dispose();
            }
            DebounceEmitter debounceEmitter = new DebounceEmitter(t2, j2, this);
            this.timer = debounceEmitter;
            debounceEmitter.setResource(this.worker.schedule(debounceEmitter, this.timeout, this.unit));
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this, n2);
            }
        }
    }

    public FlowableDebounceTimed(Flowable<T> source, long timeout, TimeUnit unit, Scheduler scheduler) {
        super(source);
        this.timeout = timeout;
        this.unit = unit;
        this.scheduler = scheduler;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new DebounceTimedSubscriber(new SerializedSubscriber(s2), this.timeout, this.unit, this.scheduler.createWorker()));
    }
}
