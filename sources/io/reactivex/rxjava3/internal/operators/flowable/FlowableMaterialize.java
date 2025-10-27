package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.internal.subscribers.SinglePostCompleteSubscriber;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableMaterialize<T> extends AbstractFlowableWithUpstream<T, Notification<T>> {

    public static final class MaterializeSubscriber<T> extends SinglePostCompleteSubscriber<T, Notification<T>> {
        private static final long serialVersionUID = -3740826063558713822L;

        public MaterializeSubscriber(Subscriber<? super Notification<T>> downstream) {
            super(downstream);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            complete(Notification.createOnComplete());
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            complete(Notification.createOnError(t2));
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.produced++;
            this.downstream.onNext(Notification.createOnNext(t2));
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.SinglePostCompleteSubscriber
        public void onDrop(Notification<T> n2) {
            if (n2.isOnError()) {
                RxJavaPlugins.onError(n2.getError());
            }
        }
    }

    public FlowableMaterialize(Flowable<T> source) {
        super(source);
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super Notification<T>> s2) {
        this.source.subscribe((FlowableSubscriber) new MaterializeSubscriber(s2));
    }
}
