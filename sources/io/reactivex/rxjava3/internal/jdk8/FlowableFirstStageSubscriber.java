package io.reactivex.rxjava3.internal.jdk8;

import java.util.NoSuchElementException;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableFirstStageSubscriber<T> extends FlowableStageSubscriber<T> {
    final T defaultItem;
    final boolean hasDefault;

    public FlowableFirstStageSubscriber(boolean hasDefault, T defaultItem) {
        this.hasDefault = hasDefault;
        this.defaultItem = defaultItem;
    }

    @Override // io.reactivex.rxjava3.internal.jdk8.FlowableStageSubscriber
    public void afterSubscribe(Subscription s2) {
        s2.request(1L);
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (isDone()) {
            return;
        }
        clear();
        if (this.hasDefault) {
            complete(this.defaultItem);
        } else {
            completeExceptionally(new NoSuchElementException());
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        complete(t2);
    }
}
