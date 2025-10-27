package io.reactivex.rxjava3.internal.operators.flowable;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import io.reactivex.rxjava3.subscribers.SerializedSubscriber;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableDebounce<T, U> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super T, ? extends Publisher<U>> debounceSelector;

    public static final class DebounceSubscriber<T, U> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 6725975399620862591L;
        final Function<? super T, ? extends Publisher<U>> debounceSelector;
        final AtomicReference<Disposable> debouncer = new AtomicReference<>();
        boolean done;
        final Subscriber<? super T> downstream;
        volatile long index;
        Subscription upstream;

        public static final class DebounceInnerSubscriber<T, U> extends DisposableSubscriber<U> {
            boolean done;
            final long index;
            final AtomicBoolean once = new AtomicBoolean();
            final DebounceSubscriber<T, U> parent;
            final T value;

            public DebounceInnerSubscriber(DebounceSubscriber<T, U> parent, long index, T value) {
                this.parent = parent;
                this.index = index;
                this.value = value;
            }

            public void emit() {
                if (this.once.compareAndSet(false, true)) {
                    this.parent.emit(this.index, this.value);
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                if (this.done) {
                    return;
                }
                this.done = true;
                emit();
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable t2) {
                if (this.done) {
                    RxJavaPlugins.onError(t2);
                } else {
                    this.done = true;
                    this.parent.onError(t2);
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(U t2) {
                if (this.done) {
                    return;
                }
                this.done = true;
                cancel();
                emit();
            }
        }

        public DebounceSubscriber(Subscriber<? super T> actual, Function<? super T, ? extends Publisher<U>> debounceSelector) {
            this.downstream = actual;
            this.debounceSelector = debounceSelector;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            DisposableHelper.dispose(this.debouncer);
        }

        public void emit(long idx, T value) {
            if (idx == this.index) {
                if (get() != 0) {
                    this.downstream.onNext(value);
                    BackpressureHelper.produced(this, 1L);
                } else {
                    cancel();
                    this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            Disposable disposable = this.debouncer.get();
            if (DisposableHelper.isDisposed(disposable)) {
                return;
            }
            DebounceInnerSubscriber debounceInnerSubscriber = (DebounceInnerSubscriber) disposable;
            if (debounceInnerSubscriber != null) {
                debounceInnerSubscriber.emit();
            }
            DisposableHelper.dispose(this.debouncer);
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            DisposableHelper.dispose(this.debouncer);
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            long j2 = this.index + 1;
            this.index = j2;
            Disposable disposable = this.debouncer.get();
            if (disposable != null) {
                disposable.dispose();
            }
            try {
                Publisher<U> publisherApply = this.debounceSelector.apply(t2);
                Objects.requireNonNull(publisherApply, "The publisher supplied is null");
                Publisher<U> publisher = publisherApply;
                DebounceInnerSubscriber debounceInnerSubscriber = new DebounceInnerSubscriber(this, j2, t2);
                if (j.a(this.debouncer, disposable, debounceInnerSubscriber)) {
                    publisher.subscribe(debounceInnerSubscriber);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.downstream.onError(th);
            }
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

    public FlowableDebounce(Flowable<T> source, Function<? super T, ? extends Publisher<U>> debounceSelector) {
        super(source);
        this.debounceSelector = debounceSelector;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new DebounceSubscriber(new SerializedSubscriber(s2), this.debounceSelector));
    }
}
