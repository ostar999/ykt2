package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.Objects;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableDistinct<T, K> extends AbstractFlowableWithUpstream<T, T> {
    final Supplier<? extends Collection<? super K>> collectionSupplier;
    final Function<? super T, K> keySelector;

    public static final class DistinctSubscriber<T, K> extends BasicFuseableSubscriber<T, T> {
        final Collection<? super K> collection;
        final Function<? super T, K> keySelector;

        public DistinctSubscriber(Subscriber<? super T> actual, Function<? super T, K> keySelector, Collection<? super K> collection) {
            super(actual);
            this.keySelector = keySelector;
            this.collection = collection;
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber, io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            this.collection.clear();
            super.clear();
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.collection.clear();
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable e2) {
            if (this.done) {
                RxJavaPlugins.onError(e2);
                return;
            }
            this.done = true;
            this.collection.clear();
            this.downstream.onError(e2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T value) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return;
            }
            try {
                K kApply = this.keySelector.apply(value);
                Objects.requireNonNull(kApply, "The keySelector returned a null key");
                if (this.collection.add(kApply)) {
                    this.downstream.onNext(value);
                } else {
                    this.upstream.request(1L);
                }
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T tPoll;
            while (true) {
                tPoll = this.qs.poll();
                if (tPoll == null) {
                    break;
                }
                Collection<? super K> collection = this.collection;
                K kApply = this.keySelector.apply(tPoll);
                Objects.requireNonNull(kApply, "The keySelector returned a null key");
                if (collection.add(kApply)) {
                    break;
                }
                if (this.sourceMode == 2) {
                    this.upstream.request(1L);
                }
            }
            return tPoll;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }
    }

    public FlowableDistinct(Flowable<T> source, Function<? super T, K> keySelector, Supplier<? extends Collection<? super K>> collectionSupplier) {
        super(source);
        this.keySelector = keySelector;
        this.collectionSupplier = collectionSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        try {
            this.source.subscribe((FlowableSubscriber) new DistinctSubscriber(subscriber, this.keySelector, (Collection) ExceptionHelper.nullCheck(this.collectionSupplier.get(), "The collectionSupplier returned a null Collection.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
