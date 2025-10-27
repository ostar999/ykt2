package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import java.util.Objects;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
    final Function<? super T, ? extends U> mapper;

    public static final class MapConditionalSubscriber<T, U> extends BasicFuseableConditionalSubscriber<T, U> {
        final Function<? super T, ? extends U> mapper;

        public MapConditionalSubscriber(ConditionalSubscriber<? super U> actual, Function<? super T, ? extends U> function) {
            super(actual);
            this.mapper = function;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return;
            }
            try {
                U uApply = this.mapper.apply(t2);
                Objects.requireNonNull(uApply, "The mapper function returned a null value.");
                this.downstream.onNext(uApply);
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public U poll() throws Throwable {
            T tPoll = this.qs.poll();
            if (tPoll == null) {
                return null;
            }
            U uApply = this.mapper.apply(tPoll);
            Objects.requireNonNull(uApply, "The mapper function returned a null value.");
            return uApply;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(T t2) {
            if (this.done) {
                return true;
            }
            if (this.sourceMode != 0) {
                this.downstream.tryOnNext(null);
                return true;
            }
            try {
                U uApply = this.mapper.apply(t2);
                Objects.requireNonNull(uApply, "The mapper function returned a null value.");
                return this.downstream.tryOnNext(uApply);
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }
    }

    public static final class MapSubscriber<T, U> extends BasicFuseableSubscriber<T, U> {
        final Function<? super T, ? extends U> mapper;

        public MapSubscriber(Subscriber<? super U> actual, Function<? super T, ? extends U> mapper) {
            super(actual);
            this.mapper = mapper;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return;
            }
            try {
                U uApply = this.mapper.apply(t2);
                Objects.requireNonNull(uApply, "The mapper function returned a null value.");
                this.downstream.onNext(uApply);
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public U poll() throws Throwable {
            T tPoll = this.qs.poll();
            if (tPoll == null) {
                return null;
            }
            U uApply = this.mapper.apply(tPoll);
            Objects.requireNonNull(uApply, "The mapper function returned a null value.");
            return uApply;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }
    }

    public FlowableMap(Flowable<T> source, Function<? super T, ? extends U> mapper) {
        super(source);
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super U> s2) {
        if (s2 instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new MapConditionalSubscriber((ConditionalSubscriber) s2, this.mapper));
        } else {
            this.source.subscribe((FlowableSubscriber) new MapSubscriber(s2, this.mapper));
        }
    }
}
