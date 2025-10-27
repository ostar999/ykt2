package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class CompletableMerge extends Completable {
    final boolean delayErrors;
    final int maxConcurrency;
    final Publisher<? extends CompletableSource> source;

    public static final class CompletableMergeSubscriber extends AtomicInteger implements FlowableSubscriber<CompletableSource>, Disposable {
        private static final long serialVersionUID = -2108443387387077490L;
        final boolean delayErrors;
        final CompletableObserver downstream;
        final int maxConcurrency;
        Subscription upstream;
        final CompositeDisposable set = new CompositeDisposable();
        final AtomicThrowable error = new AtomicThrowable();

        public final class MergeInnerObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
            private static final long serialVersionUID = 251330541679988317L;

            public MergeInnerObserver() {
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                CompletableMergeSubscriber.this.innerComplete(this);
            }

            @Override // io.reactivex.CompletableObserver
            public void onError(Throwable th) {
                CompletableMergeSubscriber.this.innerError(this, th);
            }

            @Override // io.reactivex.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }
        }

        public CompletableMergeSubscriber(CompletableObserver completableObserver, int i2, boolean z2) {
            this.downstream = completableObserver;
            this.maxConcurrency = i2;
            this.delayErrors = z2;
            lazySet(1);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.upstream.cancel();
            this.set.dispose();
        }

        public void innerComplete(MergeInnerObserver mergeInnerObserver) {
            this.set.delete(mergeInnerObserver);
            if (decrementAndGet() != 0) {
                if (this.maxConcurrency != Integer.MAX_VALUE) {
                    this.upstream.request(1L);
                }
            } else {
                Throwable th = this.error.get();
                if (th != null) {
                    this.downstream.onError(th);
                } else {
                    this.downstream.onComplete();
                }
            }
        }

        public void innerError(MergeInnerObserver mergeInnerObserver, Throwable th) {
            this.set.delete(mergeInnerObserver);
            if (!this.delayErrors) {
                this.upstream.cancel();
                this.set.dispose();
                if (!this.error.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                } else {
                    if (getAndSet(0) > 0) {
                        this.downstream.onError(this.error.terminate());
                        return;
                    }
                    return;
                }
            }
            if (!this.error.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (decrementAndGet() == 0) {
                this.downstream.onError(this.error.terminate());
            } else if (this.maxConcurrency != Integer.MAX_VALUE) {
                this.upstream.request(1L);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.set.isDisposed();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (decrementAndGet() == 0) {
                if (this.error.get() != null) {
                    this.downstream.onError(this.error.terminate());
                } else {
                    this.downstream.onComplete();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.delayErrors) {
                if (!this.error.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                } else {
                    if (decrementAndGet() == 0) {
                        this.downstream.onError(this.error.terminate());
                        return;
                    }
                    return;
                }
            }
            this.set.dispose();
            if (!this.error.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (getAndSet(0) > 0) {
                this.downstream.onError(this.error.terminate());
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                int i2 = this.maxConcurrency;
                if (i2 == Integer.MAX_VALUE) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.request(i2);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(CompletableSource completableSource) {
            getAndIncrement();
            MergeInnerObserver mergeInnerObserver = new MergeInnerObserver();
            this.set.add(mergeInnerObserver);
            completableSource.subscribe(mergeInnerObserver);
        }
    }

    public CompletableMerge(Publisher<? extends CompletableSource> publisher, int i2, boolean z2) {
        this.source = publisher;
        this.maxConcurrency = i2;
        this.delayErrors = z2;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new CompletableMergeSubscriber(completableObserver, this.maxConcurrency, this.delayErrors));
    }
}
