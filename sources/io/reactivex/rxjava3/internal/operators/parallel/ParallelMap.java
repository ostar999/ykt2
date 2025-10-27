package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ParallelMap<T, R> extends ParallelFlowable<R> {
    final Function<? super T, ? extends R> mapper;
    final ParallelFlowable<T> source;

    public static final class ParallelMapConditionalSubscriber<T, R> implements ConditionalSubscriber<T>, Subscription {
        boolean done;
        final ConditionalSubscriber<? super R> downstream;
        final Function<? super T, ? extends R> mapper;
        Subscription upstream;

        public ParallelMapConditionalSubscriber(ConditionalSubscriber<? super R> actual, Function<? super T, ? extends R> mapper) {
            this.downstream = actual;
            this.mapper = mapper;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
            } else {
                this.done = true;
                this.downstream.onError(t2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            try {
                R rApply = this.mapper.apply(t2);
                Objects.requireNonNull(rApply, "The mapper returned a null value");
                this.downstream.onNext(rApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                onError(th);
            }
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

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(T t2) {
            if (this.done) {
                return false;
            }
            try {
                R rApply = this.mapper.apply(t2);
                Objects.requireNonNull(rApply, "The mapper returned a null value");
                return this.downstream.tryOnNext(rApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                onError(th);
                return false;
            }
        }
    }

    public static final class ParallelMapSubscriber<T, R> implements FlowableSubscriber<T>, Subscription {
        boolean done;
        final Subscriber<? super R> downstream;
        final Function<? super T, ? extends R> mapper;
        Subscription upstream;

        public ParallelMapSubscriber(Subscriber<? super R> actual, Function<? super T, ? extends R> mapper) {
            this.downstream = actual;
            this.mapper = mapper;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
            } else {
                this.done = true;
                this.downstream.onError(t2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            try {
                R rApply = this.mapper.apply(t2);
                Objects.requireNonNull(rApply, "The mapper returned a null value");
                this.downstream.onNext(rApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                onError(th);
            }
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

    public ParallelMap(ParallelFlowable<T> source, Function<? super T, ? extends R> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super R>[] subscribers) {
        Subscriber<?>[] subscriberArrOnSubscribe = RxJavaPlugins.onSubscribe(this, subscribers);
        if (validate(subscriberArrOnSubscribe)) {
            int length = subscriberArrOnSubscribe.length;
            Subscriber<? super T>[] subscriberArr = new Subscriber[length];
            for (int i2 = 0; i2 < length; i2++) {
                Subscriber<?> subscriber = subscriberArrOnSubscribe[i2];
                if (subscriber instanceof ConditionalSubscriber) {
                    subscriberArr[i2] = new ParallelMapConditionalSubscriber((ConditionalSubscriber) subscriber, this.mapper);
                } else {
                    subscriberArr[i2] = new ParallelMapSubscriber(subscriber, this.mapper);
                }
            }
            this.source.subscribe(subscriberArr);
        }
    }
}
