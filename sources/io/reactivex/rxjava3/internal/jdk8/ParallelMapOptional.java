package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.Optional;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ParallelMapOptional<T, R> extends ParallelFlowable<R> {
    final Function<? super T, Optional<? extends R>> mapper;
    final ParallelFlowable<T> source;

    public static final class ParallelMapConditionalSubscriber<T, R> implements ConditionalSubscriber<T>, Subscription {
        boolean done;
        final ConditionalSubscriber<? super R> downstream;
        final Function<? super T, Optional<? extends R>> mapper;
        Subscription upstream;

        public ParallelMapConditionalSubscriber(ConditionalSubscriber<? super R> actual, Function<? super T, Optional<? extends R>> mapper) {
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
            if (tryOnNext(t2)) {
                return;
            }
            this.upstream.request(1L);
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
                Optional<? extends R> optionalApply = this.mapper.apply(t2);
                Objects.requireNonNull(optionalApply, "The mapper returned a null value");
                Optional<? extends R> optional = optionalApply;
                return optional.isPresent() && this.downstream.tryOnNext((Object) optional.get());
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                onError(th);
                return false;
            }
        }
    }

    public static final class ParallelMapSubscriber<T, R> implements ConditionalSubscriber<T>, Subscription {
        boolean done;
        final Subscriber<? super R> downstream;
        final Function<? super T, Optional<? extends R>> mapper;
        Subscription upstream;

        public ParallelMapSubscriber(Subscriber<? super R> actual, Function<? super T, Optional<? extends R>> mapper) {
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
            if (tryOnNext(t2)) {
                return;
            }
            this.upstream.request(1L);
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
                return true;
            }
            try {
                Optional<? extends R> optionalApply = this.mapper.apply(t2);
                Objects.requireNonNull(optionalApply, "The mapper returned a null Optional");
                Optional<? extends R> optional = optionalApply;
                if (!optional.isPresent()) {
                    return false;
                }
                this.downstream.onNext((Object) optional.get());
                return true;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                onError(th);
                return true;
            }
        }
    }

    public ParallelMapOptional(ParallelFlowable<T> source, Function<? super T, Optional<? extends R>> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super R>[] subscribers) {
        if (validate(subscribers)) {
            int length = subscribers.length;
            Subscriber<? super T>[] subscriberArr = new Subscriber[length];
            for (int i2 = 0; i2 < length; i2++) {
                Subscriber<? super R> subscriber = subscribers[i2];
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
