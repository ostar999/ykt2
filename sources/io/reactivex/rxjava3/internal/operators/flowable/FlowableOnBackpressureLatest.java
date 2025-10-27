package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableOnBackpressureLatest<T> extends AbstractFlowableWithUpstream<T, T> {

    public static final class BackpressureLatestSubscriber<T> extends AbstractBackpressureThrottlingSubscriber<T, T> {
        private static final long serialVersionUID = 163080509307634843L;

        public BackpressureLatestSubscriber(Subscriber<? super T> downstream) {
            super(downstream);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.AbstractBackpressureThrottlingSubscriber, org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.current.lazySet(t2);
            drain();
        }
    }

    public FlowableOnBackpressureLatest(Flowable<T> source) {
        super(source);
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new BackpressureLatestSubscriber(s2));
    }
}
