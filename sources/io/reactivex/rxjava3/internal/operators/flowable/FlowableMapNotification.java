package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscribers.SinglePostCompleteSubscriber;
import java.util.Objects;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableMapNotification<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final Supplier<? extends R> onCompleteSupplier;
    final Function<? super Throwable, ? extends R> onErrorMapper;
    final Function<? super T, ? extends R> onNextMapper;

    public static final class MapNotificationSubscriber<T, R> extends SinglePostCompleteSubscriber<T, R> {
        private static final long serialVersionUID = 2757120512858778108L;
        final Supplier<? extends R> onCompleteSupplier;
        final Function<? super Throwable, ? extends R> onErrorMapper;
        final Function<? super T, ? extends R> onNextMapper;

        public MapNotificationSubscriber(Subscriber<? super R> actual, Function<? super T, ? extends R> onNextMapper, Function<? super Throwable, ? extends R> onErrorMapper, Supplier<? extends R> onCompleteSupplier) {
            super(actual);
            this.onNextMapper = onNextMapper;
            this.onErrorMapper = onErrorMapper;
            this.onCompleteSupplier = onCompleteSupplier;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            try {
                R r2 = this.onCompleteSupplier.get();
                Objects.requireNonNull(r2, "The onComplete publisher returned is null");
                complete(r2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            try {
                R rApply = this.onErrorMapper.apply(t2);
                Objects.requireNonNull(rApply, "The onError publisher returned is null");
                complete(rApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(new CompositeException(t2, th));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            try {
                R rApply = this.onNextMapper.apply(t2);
                Objects.requireNonNull(rApply, "The onNext publisher returned is null");
                this.produced++;
                this.downstream.onNext(rApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }
    }

    public FlowableMapNotification(Flowable<T> source, Function<? super T, ? extends R> onNextMapper, Function<? super Throwable, ? extends R> onErrorMapper, Supplier<? extends R> onCompleteSupplier) {
        super(source);
        this.onNextMapper = onNextMapper;
        this.onErrorMapper = onErrorMapper;
        this.onCompleteSupplier = onCompleteSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        this.source.subscribe((FlowableSubscriber) new MapNotificationSubscriber(s2, this.onNextMapper, this.onErrorMapper, this.onCompleteSupplier));
    }
}
