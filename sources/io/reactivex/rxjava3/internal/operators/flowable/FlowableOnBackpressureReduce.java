package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableOnBackpressureReduce<T> extends AbstractFlowableWithUpstream<T, T> {
    final BiFunction<T, T, T> reducer;

    public static final class BackpressureReduceSubscriber<T> extends AbstractBackpressureThrottlingSubscriber<T, T> {
        private static final long serialVersionUID = 821363947659780367L;
        final BiFunction<T, T, T> reducer;

        public BackpressureReduceSubscriber(@NonNull Subscriber<? super T> downstream, @NonNull BiFunction<T, T, T> reducer) {
            super(downstream);
            this.reducer = reducer;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.AbstractBackpressureThrottlingSubscriber, org.reactivestreams.Subscriber
        public void onNext(T t2) {
            Object andSet = this.current.get();
            if (andSet != null) {
                andSet = this.current.getAndSet(null);
            }
            if (andSet == null) {
                this.current.lazySet(t2);
            } else {
                try {
                    AtomicReference<R> atomicReference = this.current;
                    Object objApply = this.reducer.apply(andSet, t2);
                    Objects.requireNonNull(objApply, "The reducer returned a null value");
                    atomicReference.lazySet(objApply);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.cancel();
                    onError(th);
                    return;
                }
            }
            drain();
        }
    }

    public FlowableOnBackpressureReduce(@NonNull Flowable<T> source, @NonNull BiFunction<T, T, T> reducer) {
        super(source);
        this.reducer = reducer;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(@NonNull Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new BackpressureReduceSubscriber(s2, this.reducer));
    }
}
