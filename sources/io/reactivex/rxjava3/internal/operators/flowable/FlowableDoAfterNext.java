package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableDoAfterNext<T> extends AbstractFlowableWithUpstream<T, T> {
    final Consumer<? super T> onAfterNext;

    public static final class DoAfterConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
        final Consumer<? super T> onAfterNext;

        public DoAfterConditionalSubscriber(ConditionalSubscriber<? super T> actual, Consumer<? super T> onAfterNext) {
            super(actual);
            this.onAfterNext = onAfterNext;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.downstream.onNext(t2);
            if (this.sourceMode == 0) {
                try {
                    this.onAfterNext.accept(t2);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T tPoll = this.qs.poll();
            if (tPoll != null) {
                this.onAfterNext.accept(tPoll);
            }
            return tPoll;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(T t2) {
            boolean zTryOnNext = this.downstream.tryOnNext(t2);
            try {
                this.onAfterNext.accept(t2);
            } catch (Throwable th) {
                fail(th);
            }
            return zTryOnNext;
        }
    }

    public static final class DoAfterSubscriber<T> extends BasicFuseableSubscriber<T, T> {
        final Consumer<? super T> onAfterNext;

        public DoAfterSubscriber(Subscriber<? super T> actual, Consumer<? super T> onAfterNext) {
            super(actual);
            this.onAfterNext = onAfterNext;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            this.downstream.onNext(t2);
            if (this.sourceMode == 0) {
                try {
                    this.onAfterNext.accept(t2);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T tPoll = this.qs.poll();
            if (tPoll != null) {
                this.onAfterNext.accept(tPoll);
            }
            return tPoll;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }
    }

    public FlowableDoAfterNext(Flowable<T> source, Consumer<? super T> onAfterNext) {
        super(source);
        this.onAfterNext = onAfterNext;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        if (s2 instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new DoAfterConditionalSubscriber((ConditionalSubscriber) s2, this.onAfterNext));
        } else {
            this.source.subscribe((FlowableSubscriber) new DoAfterSubscriber(s2, this.onAfterNext));
        }
    }
}
