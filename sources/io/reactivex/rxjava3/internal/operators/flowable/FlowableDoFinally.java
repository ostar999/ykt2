package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableDoFinally<T> extends AbstractFlowableWithUpstream<T, T> {
    final Action onFinally;

    public static final class DoFinallyConditionalSubscriber<T> extends BasicIntQueueSubscription<T> implements ConditionalSubscriber<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final ConditionalSubscriber<? super T> downstream;
        final Action onFinally;
        QueueSubscription<T> qs;
        boolean syncFused;
        Subscription upstream;

        public DoFinallyConditionalSubscriber(ConditionalSubscriber<? super T> actual, Action onFinally) {
            this.downstream = actual;
            this.onFinally = onFinally;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            runFinally();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            this.qs.clear();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return this.qs.isEmpty();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
            runFinally();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.downstream.onError(t2);
            runFinally();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                if (s2 instanceof QueueSubscription) {
                    this.qs = (QueueSubscription) s2;
                }
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T tPoll = this.qs.poll();
            if (tPoll == null && this.syncFused) {
                runFinally();
            }
            return tPoll;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            this.upstream.request(n2);
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            QueueSubscription<T> queueSubscription = this.qs;
            if (queueSubscription == null || (mode & 4) != 0) {
                return 0;
            }
            int iRequestFusion = queueSubscription.requestFusion(mode);
            if (iRequestFusion != 0) {
                this.syncFused = iRequestFusion == 1;
            }
            return iRequestFusion;
        }

        public void runFinally() {
            if (compareAndSet(0, 1)) {
                try {
                    this.onFinally.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(T t2) {
            return this.downstream.tryOnNext(t2);
        }
    }

    public static final class DoFinallySubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final Subscriber<? super T> downstream;
        final Action onFinally;
        QueueSubscription<T> qs;
        boolean syncFused;
        Subscription upstream;

        public DoFinallySubscriber(Subscriber<? super T> actual, Action onFinally) {
            this.downstream = actual;
            this.onFinally = onFinally;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            runFinally();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            this.qs.clear();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return this.qs.isEmpty();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
            runFinally();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.downstream.onError(t2);
            runFinally();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                if (s2 instanceof QueueSubscription) {
                    this.qs = (QueueSubscription) s2;
                }
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T tPoll = this.qs.poll();
            if (tPoll == null && this.syncFused) {
                runFinally();
            }
            return tPoll;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            this.upstream.request(n2);
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            QueueSubscription<T> queueSubscription = this.qs;
            if (queueSubscription == null || (mode & 4) != 0) {
                return 0;
            }
            int iRequestFusion = queueSubscription.requestFusion(mode);
            if (iRequestFusion != 0) {
                this.syncFused = iRequestFusion == 1;
            }
            return iRequestFusion;
        }

        public void runFinally() {
            if (compareAndSet(0, 1)) {
                try {
                    this.onFinally.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    public FlowableDoFinally(Flowable<T> source, Action onFinally) {
        super(source);
        this.onFinally = onFinally;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        if (s2 instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new DoFinallyConditionalSubscriber((ConditionalSubscriber) s2, this.onFinally));
        } else {
            this.source.subscribe((FlowableSubscriber) new DoFinallySubscriber(s2, this.onFinally));
        }
    }
}
