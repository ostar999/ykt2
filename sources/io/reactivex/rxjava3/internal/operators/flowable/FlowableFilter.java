package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import io.reactivex.rxjava3.operators.QueueSubscription;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableFilter<T> extends AbstractFlowableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    public static final class FilterConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
        final Predicate<? super T> filter;

        public FilterConditionalSubscriber(ConditionalSubscriber<? super T> actual, Predicate<? super T> filter) {
            super(actual);
            this.filter = filter;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (tryOnNext(t2)) {
                return;
            }
            this.upstream.request(1L);
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            QueueSubscription<T> queueSubscription = this.qs;
            Predicate<? super T> predicate = this.filter;
            while (true) {
                T tPoll = queueSubscription.poll();
                if (tPoll == null) {
                    return null;
                }
                if (predicate.test(tPoll)) {
                    return tPoll;
                }
                if (this.sourceMode == 2) {
                    queueSubscription.request(1L);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(T t2) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                return this.downstream.tryOnNext(null);
            }
            try {
                return this.filter.test(t2) && this.downstream.tryOnNext(t2);
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }
    }

    public static final class FilterSubscriber<T> extends BasicFuseableSubscriber<T, T> implements ConditionalSubscriber<T> {
        final Predicate<? super T> filter;

        public FilterSubscriber(Subscriber<? super T> actual, Predicate<? super T> filter) {
            super(actual);
            this.filter = filter;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (tryOnNext(t2)) {
                return;
            }
            this.upstream.request(1L);
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            QueueSubscription<T> queueSubscription = this.qs;
            Predicate<? super T> predicate = this.filter;
            while (true) {
                T tPoll = queueSubscription.poll();
                if (tPoll == null) {
                    return null;
                }
                if (predicate.test(tPoll)) {
                    return tPoll;
                }
                if (this.sourceMode == 2) {
                    queueSubscription.request(1L);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(T t2) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return true;
            }
            try {
                boolean zTest = this.filter.test(t2);
                if (zTest) {
                    this.downstream.onNext(t2);
                }
                return zTest;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }
    }

    public FlowableFilter(Flowable<T> source, Predicate<? super T> predicate) {
        super(source);
        this.predicate = predicate;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        if (s2 instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new FilterConditionalSubscriber((ConditionalSubscriber) s2, this.predicate));
        } else {
            this.source.subscribe((FlowableSubscriber) new FilterSubscriber(s2, this.predicate));
        }
    }
}
