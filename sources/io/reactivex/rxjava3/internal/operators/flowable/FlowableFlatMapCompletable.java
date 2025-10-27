package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableFlatMapCompletable<T> extends AbstractFlowableWithUpstream<T, T> {
    final boolean delayErrors;
    final Function<? super T, ? extends CompletableSource> mapper;
    final int maxConcurrency;

    public static final class FlatMapCompletableMainSubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 8443155186132538303L;
        volatile boolean cancelled;
        final boolean delayErrors;
        final Subscriber<? super T> downstream;
        final Function<? super T, ? extends CompletableSource> mapper;
        final int maxConcurrency;
        Subscription upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final CompositeDisposable set = new CompositeDisposable();

        public final class InnerConsumer extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
            private static final long serialVersionUID = 8606673141535671828L;

            public InnerConsumer() {
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                FlatMapCompletableMainSubscriber.this.innerComplete(this);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                FlatMapCompletableMainSubscriber.this.innerError(this, e2);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.setOnce(this, d3);
            }
        }

        public FlatMapCompletableMainSubscriber(Subscriber<? super T> subscriber, Function<? super T, ? extends CompletableSource> mapper, boolean delayErrors, int maxConcurrency) {
            this.downstream = subscriber;
            this.mapper = mapper;
            this.delayErrors = delayErrors;
            this.maxConcurrency = maxConcurrency;
            lazySet(1);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            this.set.dispose();
            this.errors.tryTerminateAndReport();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
        }

        public void innerComplete(FlatMapCompletableMainSubscriber<T>.InnerConsumer inner) {
            this.set.delete(inner);
            onComplete();
        }

        public void innerError(FlatMapCompletableMainSubscriber<T>.InnerConsumer inner, Throwable e2) {
            this.set.delete(inner);
            onError(e2);
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return true;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (decrementAndGet() == 0) {
                this.errors.tryTerminateConsumer(this.downstream);
            } else if (this.maxConcurrency != Integer.MAX_VALUE) {
                this.upstream.request(1L);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable e2) {
            if (this.errors.tryAddThrowableOrReport(e2)) {
                if (!this.delayErrors) {
                    this.cancelled = true;
                    this.upstream.cancel();
                    this.set.dispose();
                    this.errors.tryTerminateConsumer(this.downstream);
                    return;
                }
                if (decrementAndGet() == 0) {
                    this.errors.tryTerminateConsumer(this.downstream);
                } else if (this.maxConcurrency != Integer.MAX_VALUE) {
                    this.upstream.request(1L);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T value) {
            try {
                CompletableSource completableSourceApply = this.mapper.apply(value);
                Objects.requireNonNull(completableSourceApply, "The mapper returned a null CompletableSource");
                CompletableSource completableSource = completableSourceApply;
                getAndIncrement();
                InnerConsumer innerConsumer = new InnerConsumer();
                if (this.cancelled || !this.set.add(innerConsumer)) {
                    return;
                }
                completableSource.subscribe(innerConsumer);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                int i2 = this.maxConcurrency;
                if (i2 == Integer.MAX_VALUE) {
                    s2.request(Long.MAX_VALUE);
                } else {
                    s2.request(i2);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            return null;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return mode & 2;
        }
    }

    public FlowableFlatMapCompletable(Flowable<T> source, Function<? super T, ? extends CompletableSource> mapper, boolean delayErrors, int maxConcurrency) {
        super(source);
        this.mapper = mapper;
        this.delayErrors = delayErrors;
        this.maxConcurrency = maxConcurrency;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new FlatMapCompletableMainSubscriber(subscriber, this.mapper, this.delayErrors, this.maxConcurrency));
    }
}
