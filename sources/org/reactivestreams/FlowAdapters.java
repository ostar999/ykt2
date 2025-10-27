package org.reactivestreams;

import java.util.Objects;
import java.util.concurrent.Flow;

/* loaded from: classes9.dex */
public final class FlowAdapters {

    public static final class FlowPublisherFromReactive<T> implements Flow.Publisher<T> {
        final Publisher<? extends T> reactiveStreams;

        public FlowPublisherFromReactive(Publisher<? extends T> publisher) {
            this.reactiveStreams = publisher;
        }

        @Override // java.util.concurrent.Flow.Publisher
        public void subscribe(Flow.Subscriber<? super T> subscriber) {
            this.reactiveStreams.subscribe(subscriber == null ? null : new ReactiveToFlowSubscriber(subscriber));
        }
    }

    public static final class FlowToReactiveProcessor<T, U> implements Flow.Processor<T, U> {
        final Processor<? super T, ? extends U> reactiveStreams;

        public FlowToReactiveProcessor(Processor<? super T, ? extends U> processor) {
            this.reactiveStreams = processor;
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onComplete() {
            this.reactiveStreams.onComplete();
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onError(Throwable th) {
            this.reactiveStreams.onError(th);
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onNext(T t2) {
            this.reactiveStreams.onNext(t2);
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onSubscribe(Flow.Subscription subscription) {
            this.reactiveStreams.onSubscribe(subscription == null ? null : new ReactiveToFlowSubscription(subscription));
        }

        @Override // java.util.concurrent.Flow.Publisher
        public void subscribe(Flow.Subscriber<? super U> subscriber) {
            this.reactiveStreams.subscribe(subscriber == null ? null : new ReactiveToFlowSubscriber(subscriber));
        }
    }

    public static final class FlowToReactiveSubscriber<T> implements Flow.Subscriber<T> {
        final Subscriber<? super T> reactiveStreams;

        public FlowToReactiveSubscriber(Subscriber<? super T> subscriber) {
            this.reactiveStreams = subscriber;
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onComplete() {
            this.reactiveStreams.onComplete();
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onError(Throwable th) {
            this.reactiveStreams.onError(th);
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onNext(T t2) {
            this.reactiveStreams.onNext(t2);
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onSubscribe(Flow.Subscription subscription) {
            this.reactiveStreams.onSubscribe(subscription == null ? null : new ReactiveToFlowSubscription(subscription));
        }
    }

    public static final class FlowToReactiveSubscription implements Flow.Subscription {
        final Subscription reactiveStreams;

        public FlowToReactiveSubscription(Subscription subscription) {
            this.reactiveStreams = subscription;
        }

        @Override // java.util.concurrent.Flow.Subscription
        public void cancel() {
            this.reactiveStreams.cancel();
        }

        @Override // java.util.concurrent.Flow.Subscription
        public void request(long j2) {
            this.reactiveStreams.request(j2);
        }
    }

    public static final class ReactivePublisherFromFlow<T> implements Publisher<T> {
        final Flow.Publisher<? extends T> flow;

        public ReactivePublisherFromFlow(Flow.Publisher<? extends T> publisher) {
            this.flow = publisher;
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            this.flow.subscribe(subscriber == null ? null : new FlowToReactiveSubscriber(subscriber));
        }
    }

    public static final class ReactiveToFlowProcessor<T, U> implements Processor<T, U> {
        final Flow.Processor<? super T, ? extends U> flow;

        public ReactiveToFlowProcessor(Flow.Processor<? super T, ? extends U> processor) {
            this.flow = processor;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.flow.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.flow.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.flow.onNext(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.flow.onSubscribe(subscription == null ? null : new FlowToReactiveSubscription(subscription));
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super U> subscriber) {
            this.flow.subscribe(subscriber == null ? null : new FlowToReactiveSubscriber(subscriber));
        }
    }

    public static final class ReactiveToFlowSubscriber<T> implements Subscriber<T> {
        final Flow.Subscriber<? super T> flow;

        public ReactiveToFlowSubscriber(Flow.Subscriber<? super T> subscriber) {
            this.flow = subscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.flow.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.flow.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.flow.onNext(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.flow.onSubscribe(subscription == null ? null : new FlowToReactiveSubscription(subscription));
        }
    }

    public static final class ReactiveToFlowSubscription implements Subscription {
        final Flow.Subscription flow;

        public ReactiveToFlowSubscription(Flow.Subscription subscription) {
            this.flow = subscription;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.flow.cancel();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            this.flow.request(j2);
        }
    }

    private FlowAdapters() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, U> Flow.Processor<T, U> toFlowProcessor(Processor<? super T, ? extends U> processor) {
        Objects.requireNonNull(processor, "reactiveStreamsProcessor");
        return processor instanceof ReactiveToFlowProcessor ? ((ReactiveToFlowProcessor) processor).flow : processor instanceof Flow.Processor ? (Flow.Processor) processor : new FlowToReactiveProcessor(processor);
    }

    public static <T> Flow.Publisher<T> toFlowPublisher(Publisher<? extends T> publisher) {
        Objects.requireNonNull(publisher, "reactiveStreamsPublisher");
        return publisher instanceof ReactivePublisherFromFlow ? ((ReactivePublisherFromFlow) publisher).flow : publisher instanceof Flow.Publisher ? (Flow.Publisher) publisher : new FlowPublisherFromReactive(publisher);
    }

    public static <T> Flow.Subscriber<T> toFlowSubscriber(Subscriber<T> subscriber) {
        Objects.requireNonNull(subscriber, "reactiveStreamsSubscriber");
        return subscriber instanceof ReactiveToFlowSubscriber ? ((ReactiveToFlowSubscriber) subscriber).flow : subscriber instanceof Flow.Subscriber ? (Flow.Subscriber) subscriber : new FlowToReactiveSubscriber(subscriber);
    }

    public static <T, U> Processor<T, U> toProcessor(Flow.Processor<? super T, ? extends U> processor) {
        Objects.requireNonNull(processor, "flowProcessor");
        return processor instanceof FlowToReactiveProcessor ? ((FlowToReactiveProcessor) processor).reactiveStreams : processor instanceof Processor ? (Processor) processor : new ReactiveToFlowProcessor(processor);
    }

    public static <T> Publisher<T> toPublisher(Flow.Publisher<? extends T> publisher) {
        Objects.requireNonNull(publisher, "flowPublisher");
        return publisher instanceof FlowPublisherFromReactive ? ((FlowPublisherFromReactive) publisher).reactiveStreams : publisher instanceof Publisher ? (Publisher) publisher : new ReactivePublisherFromFlow(publisher);
    }

    public static <T> Subscriber<T> toSubscriber(Flow.Subscriber<T> subscriber) {
        Objects.requireNonNull(subscriber, "flowSubscriber");
        return subscriber instanceof FlowToReactiveSubscriber ? ((FlowToReactiveSubscriber) subscriber).reactiveStreams : subscriber instanceof Subscriber ? (Subscriber) subscriber : new ReactiveToFlowSubscriber(subscriber);
    }
}
