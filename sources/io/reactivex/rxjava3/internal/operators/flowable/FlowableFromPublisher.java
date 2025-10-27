package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableFromPublisher<T> extends Flowable<T> {
    final Publisher<? extends T> publisher;

    public FlowableFromPublisher(Publisher<? extends T> publisher) {
        this.publisher = publisher;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.publisher.subscribe(s2);
    }
}
