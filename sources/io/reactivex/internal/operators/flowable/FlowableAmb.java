package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableAmb<T> extends Flowable<T> {
    final Publisher<? extends T>[] sources;
    final Iterable<? extends Publisher<? extends T>> sourcesIterable;

    public static final class AmbCoordinator<T> implements Subscription {
        final Subscriber<? super T> downstream;
        final AmbInnerSubscriber<T>[] subscribers;
        final AtomicInteger winner = new AtomicInteger();

        public AmbCoordinator(Subscriber<? super T> subscriber, int i2) {
            this.downstream = subscriber;
            this.subscribers = new AmbInnerSubscriber[i2];
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.winner.get() != -1) {
                this.winner.lazySet(-1);
                for (AmbInnerSubscriber<T> ambInnerSubscriber : this.subscribers) {
                    ambInnerSubscriber.cancel();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                int i2 = this.winner.get();
                if (i2 > 0) {
                    this.subscribers[i2 - 1].request(j2);
                    return;
                }
                if (i2 == 0) {
                    for (AmbInnerSubscriber<T> ambInnerSubscriber : this.subscribers) {
                        ambInnerSubscriber.request(j2);
                    }
                }
            }
        }

        public void subscribe(Publisher<? extends T>[] publisherArr) {
            AmbInnerSubscriber<T>[] ambInnerSubscriberArr = this.subscribers;
            int length = ambInnerSubscriberArr.length;
            int i2 = 0;
            while (i2 < length) {
                int i3 = i2 + 1;
                ambInnerSubscriberArr[i2] = new AmbInnerSubscriber<>(this, i3, this.downstream);
                i2 = i3;
            }
            this.winner.lazySet(0);
            this.downstream.onSubscribe(this);
            for (int i4 = 0; i4 < length && this.winner.get() == 0; i4++) {
                publisherArr[i4].subscribe(ambInnerSubscriberArr[i4]);
            }
        }

        public boolean win(int i2) {
            int i3 = 0;
            if (this.winner.get() != 0 || !this.winner.compareAndSet(0, i2)) {
                return false;
            }
            AmbInnerSubscriber<T>[] ambInnerSubscriberArr = this.subscribers;
            int length = ambInnerSubscriberArr.length;
            while (i3 < length) {
                int i4 = i3 + 1;
                if (i4 != i2) {
                    ambInnerSubscriberArr[i3].cancel();
                }
                i3 = i4;
            }
            return true;
        }
    }

    public static final class AmbInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -1185974347409665484L;
        final Subscriber<? super T> downstream;
        final int index;
        final AtomicLong missedRequested = new AtomicLong();
        final AmbCoordinator<T> parent;
        boolean won;

        public AmbInnerSubscriber(AmbCoordinator<T> ambCoordinator, int i2, Subscriber<? super T> subscriber) {
            this.parent = ambCoordinator;
            this.index = i2;
            this.downstream = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.won) {
                this.downstream.onComplete();
            } else if (!this.parent.win(this.index)) {
                get().cancel();
            } else {
                this.won = true;
                this.downstream.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.won) {
                this.downstream.onError(th);
            } else if (this.parent.win(this.index)) {
                this.won = true;
                this.downstream.onError(th);
            } else {
                get().cancel();
                RxJavaPlugins.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.won) {
                this.downstream.onNext(t2);
            } else if (!this.parent.win(this.index)) {
                get().cancel();
            } else {
                this.won = true;
                this.downstream.onNext(t2);
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this, this.missedRequested, subscription);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            SubscriptionHelper.deferredRequest(this, this.missedRequested, j2);
        }
    }

    public FlowableAmb(Publisher<? extends T>[] publisherArr, Iterable<? extends Publisher<? extends T>> iterable) {
        this.sources = publisherArr;
        this.sourcesIterable = iterable;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        int length;
        Publisher<? extends T>[] publisherArr = this.sources;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                length = 0;
                for (Publisher<? extends T> publisher : this.sourcesIterable) {
                    if (publisher == null) {
                        EmptySubscription.error(new NullPointerException("One of the sources is null"), subscriber);
                        return;
                    }
                    if (length == publisherArr.length) {
                        Publisher<? extends T>[] publisherArr2 = new Publisher[(length >> 2) + length];
                        System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
                        publisherArr = publisherArr2;
                    }
                    int i2 = length + 1;
                    publisherArr[length] = publisher;
                    length = i2;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptySubscription.error(th, subscriber);
                return;
            }
        } else {
            length = publisherArr.length;
        }
        if (length == 0) {
            EmptySubscription.complete(subscriber);
        } else if (length == 1) {
            publisherArr[0].subscribe(subscriber);
        } else {
            new AmbCoordinator(subscriber, length).subscribe(publisherArr);
        }
    }
}
