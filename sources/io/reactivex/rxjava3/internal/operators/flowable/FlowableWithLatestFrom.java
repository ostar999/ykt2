package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import io.reactivex.rxjava3.subscribers.SerializedSubscriber;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableWithLatestFrom<T, U, R> extends AbstractFlowableWithUpstream<T, R> {
    final BiFunction<? super T, ? super U, ? extends R> combiner;
    final Publisher<? extends U> other;

    public final class FlowableWithLatestSubscriber implements FlowableSubscriber<U> {
        private final WithLatestFromSubscriber<T, U, R> wlf;

        public FlowableWithLatestSubscriber(WithLatestFromSubscriber<T, U, R> wlf) {
            this.wlf = wlf;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.wlf.otherError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U t2) {
            this.wlf.lazySet(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (this.wlf.setOther(s2)) {
                s2.request(Long.MAX_VALUE);
            }
        }
    }

    public static final class WithLatestFromSubscriber<T, U, R> extends AtomicReference<U> implements ConditionalSubscriber<T>, Subscription {
        private static final long serialVersionUID = -312246233408980075L;
        final BiFunction<? super T, ? super U, ? extends R> combiner;
        final Subscriber<? super R> downstream;
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();
        final AtomicReference<Subscription> other = new AtomicReference<>();

        public WithLatestFromSubscriber(Subscriber<? super R> actual, BiFunction<? super T, ? super U, ? extends R> combiner) {
            this.downstream = actual;
            this.combiner = combiner;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.upstream);
            SubscriptionHelper.cancel(this.other);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            SubscriptionHelper.cancel(this.other);
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            SubscriptionHelper.cancel(this.other);
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (tryOnNext(t2)) {
                return;
            }
            this.upstream.get().request(1L);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, s2);
        }

        public void otherError(Throwable e2) {
            SubscriptionHelper.cancel(this.upstream);
            this.downstream.onError(e2);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            SubscriptionHelper.deferredRequest(this.upstream, this.requested, n2);
        }

        public boolean setOther(Subscription o2) {
            return SubscriptionHelper.setOnce(this.other, o2);
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(T t2) {
            U u2 = get();
            if (u2 != null) {
                try {
                    R rApply = this.combiner.apply(t2, u2);
                    Objects.requireNonNull(rApply, "The combiner returned a null value");
                    this.downstream.onNext(rApply);
                    return true;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    this.downstream.onError(th);
                }
            }
            return false;
        }
    }

    public FlowableWithLatestFrom(Flowable<T> source, BiFunction<? super T, ? super U, ? extends R> combiner, Publisher<? extends U> other) {
        super(source);
        this.combiner = combiner;
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(s2);
        WithLatestFromSubscriber withLatestFromSubscriber = new WithLatestFromSubscriber(serializedSubscriber, this.combiner);
        serializedSubscriber.onSubscribe(withLatestFromSubscriber);
        this.other.subscribe(new FlowableWithLatestSubscriber(withLatestFromSubscriber));
        this.source.subscribe((FlowableSubscriber) withLatestFromSubscriber);
    }
}
