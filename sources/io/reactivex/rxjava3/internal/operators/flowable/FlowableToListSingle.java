package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.fuseable.FuseToFlowable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.ArrayListSupplier;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Collection;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableToListSingle<T, U extends Collection<? super T>> extends Single<U> implements FuseToFlowable<U> {
    final Supplier<U> collectionSupplier;
    final Flowable<T> source;

    public static final class ToListSubscriber<T, U extends Collection<? super T>> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super U> downstream;
        Subscription upstream;
        U value;

        public ToListSubscriber(SingleObserver<? super U> actual, U collection) {
            this.downstream = actual;
            this.value = collection;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.cancel();
            this.upstream = SubscriptionHelper.CANCELLED;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onSuccess(this.value);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.value = null;
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.value.add(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }
    }

    public FlowableToListSingle(Flowable<T> source) {
        this(source, ArrayListSupplier.asSupplier());
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.FuseToFlowable
    public Flowable<U> fuseToFlowable() {
        return RxJavaPlugins.onAssembly(new FlowableToList(this.source, this.collectionSupplier));
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super U> observer) {
        try {
            this.source.subscribe((FlowableSubscriber) new ToListSubscriber(observer, (Collection) ExceptionHelper.nullCheck(this.collectionSupplier.get(), "The collectionSupplier returned a null Collection.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }

    public FlowableToListSingle(Flowable<T> source, Supplier<U> collectionSupplier) {
        this.source = source;
        this.collectionSupplier = collectionSupplier;
    }
}
