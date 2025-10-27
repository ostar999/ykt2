package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class CompletableConcat extends Completable {
    final int prefetch;
    final Publisher<? extends CompletableSource> sources;

    public static final class CompletableConcatSubscriber extends AtomicInteger implements FlowableSubscriber<CompletableSource>, Disposable {
        private static final long serialVersionUID = 9032184911934499404L;
        volatile boolean active;
        int consumed;
        volatile boolean done;
        final CompletableObserver downstream;
        final int limit;
        final int prefetch;
        SimpleQueue<CompletableSource> queue;
        int sourceFused;
        Subscription upstream;
        final ConcatInnerObserver inner = new ConcatInnerObserver(this);
        final AtomicBoolean once = new AtomicBoolean();

        public static final class ConcatInnerObserver extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = -5454794857847146511L;
            final CompletableConcatSubscriber parent;

            public ConcatInnerObserver(CompletableConcatSubscriber parent) {
                this.parent = parent;
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.innerComplete();
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                this.parent.innerError(e2);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.replace(this, d3);
            }
        }

        public CompletableConcatSubscriber(CompletableObserver actual, int prefetch) {
            this.downstream = actual;
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.cancel();
            DisposableHelper.dispose(this.inner);
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            while (!isDisposed()) {
                if (!this.active) {
                    boolean z2 = this.done;
                    try {
                        CompletableSource completableSourcePoll = this.queue.poll();
                        boolean z3 = completableSourcePoll == null;
                        if (z2 && z3) {
                            this.downstream.onComplete();
                            return;
                        } else if (!z3) {
                            this.active = true;
                            completableSourcePoll.subscribe(this.inner);
                            request();
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        innerError(th);
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
        }

        public void innerComplete() {
            this.active = false;
            drain();
        }

        public void innerError(Throwable e2) {
            if (!this.once.compareAndSet(false, true)) {
                RxJavaPlugins.onError(e2);
            } else {
                this.upstream.cancel();
                this.downstream.onError(e2);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.inner.get());
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (!this.once.compareAndSet(false, true)) {
                RxJavaPlugins.onError(t2);
            } else {
                DisposableHelper.dispose(this.inner);
                this.downstream.onError(t2);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                int i2 = this.prefetch;
                long j2 = i2 == Integer.MAX_VALUE ? Long.MAX_VALUE : i2;
                if (s2 instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s2;
                    int iRequestFusion = queueSubscription.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.sourceFused = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceFused = iRequestFusion;
                        this.queue = queueSubscription;
                        this.downstream.onSubscribe(this);
                        s2.request(j2);
                        return;
                    }
                }
                if (this.prefetch == Integer.MAX_VALUE) {
                    this.queue = new SpscLinkedArrayQueue(Flowable.bufferSize());
                } else {
                    this.queue = new SpscArrayQueue(this.prefetch);
                }
                this.downstream.onSubscribe(this);
                s2.request(j2);
            }
        }

        public void request() {
            if (this.sourceFused != 1) {
                int i2 = this.consumed + 1;
                if (i2 != this.limit) {
                    this.consumed = i2;
                } else {
                    this.consumed = 0;
                    this.upstream.request(i2);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(CompletableSource t2) {
            if (this.sourceFused != 0 || this.queue.offer(t2)) {
                drain();
            } else {
                onError(new MissingBackpressureException());
            }
        }
    }

    public CompletableConcat(Publisher<? extends CompletableSource> sources, int prefetch) {
        this.sources = sources;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver observer) {
        this.sources.subscribe(new CompletableConcatSubscriber(observer, this.prefetch));
    }
}
