package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.LongConsumer;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ParallelPeek<T> extends ParallelFlowable<T> {
    final Consumer<? super T> onAfterNext;
    final Action onAfterTerminated;
    final Action onCancel;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;
    final LongConsumer onRequest;
    final Consumer<? super Subscription> onSubscribe;
    final ParallelFlowable<T> source;

    public static final class ParallelPeekSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        boolean done;
        final Subscriber<? super T> downstream;
        final ParallelPeek<T> parent;
        Subscription upstream;

        public ParallelPeekSubscriber(Subscriber<? super T> actual, ParallelPeek<T> parent) {
            this.downstream = actual;
            this.parent = parent;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            try {
                this.parent.onCancel.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            try {
                this.parent.onComplete.run();
                this.downstream.onComplete();
                try {
                    this.parent.onAfterTerminated.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(th2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.done = true;
            try {
                this.parent.onError.accept(t2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                t2 = new CompositeException(t2, th);
            }
            this.downstream.onError(t2);
            try {
                this.parent.onAfterTerminated.run();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                RxJavaPlugins.onError(th2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            try {
                this.parent.onNext.accept(t2);
                this.downstream.onNext(t2);
                try {
                    this.parent.onAfterNext.accept(t2);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                onError(th2);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                try {
                    this.parent.onSubscribe.accept(s2);
                    this.downstream.onSubscribe(this);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    s2.cancel();
                    this.downstream.onSubscribe(EmptySubscription.INSTANCE);
                    onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            try {
                this.parent.onRequest.accept(n2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.upstream.request(n2);
        }
    }

    public ParallelPeek(ParallelFlowable<T> source, Consumer<? super T> onNext, Consumer<? super T> onAfterNext, Consumer<? super Throwable> onError, Action onComplete, Action onAfterTerminated, Consumer<? super Subscription> onSubscribe, LongConsumer onRequest, Action onCancel) {
        this.source = source;
        Objects.requireNonNull(onNext, "onNext is null");
        this.onNext = onNext;
        Objects.requireNonNull(onAfterNext, "onAfterNext is null");
        this.onAfterNext = onAfterNext;
        Objects.requireNonNull(onError, "onError is null");
        this.onError = onError;
        Objects.requireNonNull(onComplete, "onComplete is null");
        this.onComplete = onComplete;
        Objects.requireNonNull(onAfterTerminated, "onAfterTerminated is null");
        this.onAfterTerminated = onAfterTerminated;
        Objects.requireNonNull(onSubscribe, "onSubscribe is null");
        this.onSubscribe = onSubscribe;
        Objects.requireNonNull(onRequest, "onRequest is null");
        this.onRequest = onRequest;
        Objects.requireNonNull(onCancel, "onCancel is null");
        this.onCancel = onCancel;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super T>[] subscribers) {
        Subscriber<?>[] subscriberArrOnSubscribe = RxJavaPlugins.onSubscribe(this, subscribers);
        if (validate(subscriberArrOnSubscribe)) {
            int length = subscriberArrOnSubscribe.length;
            Subscriber<? super T>[] subscriberArr = new Subscriber[length];
            for (int i2 = 0; i2 < length; i2++) {
                subscriberArr[i2] = new ParallelPeekSubscriber(subscriberArrOnSubscribe[i2], this);
            }
            this.source.subscribe(subscriberArr);
        }
    }
}
