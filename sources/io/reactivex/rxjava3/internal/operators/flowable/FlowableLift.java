package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableOperator;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableLift<R, T> extends AbstractFlowableWithUpstream<T, R> {
    final FlowableOperator<? extends R, ? super T> operator;

    public FlowableLift(Flowable<T> source, FlowableOperator<? extends R, ? super T> operator) {
        super(source);
        this.operator = operator;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        try {
            Subscriber<? super Object> subscriberApply = this.operator.apply(s2);
            if (subscriberApply != null) {
                this.source.subscribe(subscriberApply);
                return;
            }
            throw new NullPointerException("Operator " + this.operator + " returned a null Subscriber");
        } catch (NullPointerException e2) {
            throw e2;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
            NullPointerException nullPointerException = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            nullPointerException.initCause(th);
            throw nullPointerException;
        }
    }
}
