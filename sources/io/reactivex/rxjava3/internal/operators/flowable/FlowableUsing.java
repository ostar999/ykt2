package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableUsing<T, D> extends Flowable<T> {
    final Consumer<? super D> disposer;
    final boolean eager;
    final Supplier<? extends D> resourceSupplier;
    final Function<? super D, ? extends Publisher<? extends T>> sourceSupplier;

    public static final class UsingSubscriber<T, D> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 5904473792286235046L;
        final Consumer<? super D> disposer;
        final Subscriber<? super T> downstream;
        final boolean eager;
        final D resource;
        Subscription upstream;

        public UsingSubscriber(Subscriber<? super T> actual, D resource, Consumer<? super D> disposer, boolean eager) {
            this.downstream = actual;
            this.resource = resource;
            this.disposer = disposer;
            this.eager = eager;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.eager) {
                disposeResource();
                this.upstream.cancel();
                this.upstream = SubscriptionHelper.CANCELLED;
            } else {
                this.upstream.cancel();
                this.upstream = SubscriptionHelper.CANCELLED;
                disposeResource();
            }
        }

        public void disposeResource() {
            if (compareAndSet(false, true)) {
                try {
                    this.disposer.accept(this.resource);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.eager) {
                this.downstream.onComplete();
                disposeResource();
                return;
            }
            if (compareAndSet(false, true)) {
                try {
                    this.disposer.accept(this.resource);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                    return;
                }
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.eager) {
                this.downstream.onError(th);
                disposeResource();
                return;
            }
            if (compareAndSet(false, true)) {
                try {
                    this.disposer.accept(this.resource);
                    th = null;
                } catch (Throwable th2) {
                    th = th2;
                    Exceptions.throwIfFatal(th);
                }
            } else {
                th = null;
            }
            if (th != null) {
                this.downstream.onError(new CompositeException(th, th));
            } else {
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.downstream.onNext(t2);
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

    public FlowableUsing(Supplier<? extends D> resourceSupplier, Function<? super D, ? extends Publisher<? extends T>> sourceSupplier, Consumer<? super D> disposer, boolean eager) {
        this.resourceSupplier = resourceSupplier;
        this.sourceSupplier = sourceSupplier;
        this.disposer = disposer;
        this.eager = eager;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        try {
            D d3 = this.resourceSupplier.get();
            try {
                Publisher<? extends T> publisherApply = this.sourceSupplier.apply(d3);
                Objects.requireNonNull(publisherApply, "The sourceSupplier returned a null Publisher");
                publisherApply.subscribe(new UsingSubscriber(s2, d3, this.disposer, this.eager));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                try {
                    this.disposer.accept(d3);
                    EmptySubscription.error(th, s2);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    EmptySubscription.error(new CompositeException(th, th2), s2);
                }
            }
        } catch (Throwable th3) {
            Exceptions.throwIfFatal(th3);
            EmptySubscription.error(th3, s2);
        }
    }
}
