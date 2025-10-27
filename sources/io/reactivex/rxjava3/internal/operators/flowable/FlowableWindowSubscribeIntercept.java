package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
final class FlowableWindowSubscribeIntercept<T> extends Flowable<T> {
    final AtomicBoolean once = new AtomicBoolean();
    final FlowableProcessor<T> window;

    public FlowableWindowSubscribeIntercept(FlowableProcessor<T> source) {
        this.window = source;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.window.subscribe(s2);
        this.once.set(true);
    }

    public boolean tryAbandon() {
        return !this.once.get() && this.once.compareAndSet(false, true);
    }
}
