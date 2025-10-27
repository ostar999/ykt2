package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableRepeatWhen;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import io.reactivex.rxjava3.subscribers.SerializedSubscriber;
import java.util.Objects;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableRetryWhen<T> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler;

    public static final class RetryWhenSubscriber<T> extends FlowableRepeatWhen.WhenSourceSubscriber<T, Throwable> {
        private static final long serialVersionUID = -2680129890138081029L;

        public RetryWhenSubscriber(Subscriber<? super T> actual, FlowableProcessor<Throwable> processor, Subscription receiver) {
            super(actual, processor, receiver);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.receiver.cancel();
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            again(t2);
        }
    }

    public FlowableRetryWhen(Flowable<T> source, Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler) {
        super(source);
        this.handler = handler;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(s2);
        FlowableProcessor<T> serialized = UnicastProcessor.create(8).toSerialized();
        try {
            Publisher<?> publisherApply = this.handler.apply(serialized);
            Objects.requireNonNull(publisherApply, "handler returned a null Publisher");
            Publisher<?> publisher = publisherApply;
            FlowableRepeatWhen.WhenReceiver whenReceiver = new FlowableRepeatWhen.WhenReceiver(this.source);
            RetryWhenSubscriber retryWhenSubscriber = new RetryWhenSubscriber(serializedSubscriber, serialized, whenReceiver);
            whenReceiver.subscriber = retryWhenSubscriber;
            s2.onSubscribe(retryWhenSubscriber);
            publisher.subscribe(whenReceiver);
            whenReceiver.onNext(0);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, s2);
        }
    }
}
