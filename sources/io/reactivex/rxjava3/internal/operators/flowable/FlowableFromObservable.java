package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableFromObservable<T> extends Flowable<T> {
    private final ObservableSource<T> upstream;

    public static final class SubscriberObserver<T> implements Observer<T>, Subscription {
        final Subscriber<? super T> downstream;
        Disposable upstream;

        public SubscriberObserver(Subscriber<? super T> s2) {
            this.downstream = s2;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T value) {
            this.downstream.onNext(value);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            this.upstream = d3;
            this.downstream.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
        }
    }

    public FlowableFromObservable(ObservableSource<T> upstream) {
        this.upstream = upstream;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.upstream.subscribe(new SubscriberObserver(s2));
    }
}
