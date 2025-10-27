package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import java.util.Objects;
import java.util.Optional;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableMapOptional<T, R> extends Flowable<R> {
    final Function<? super T, Optional<? extends R>> mapper;
    final Flowable<T> source;

    public static final class MapOptionalConditionalSubscriber<T, R> extends BasicFuseableConditionalSubscriber<T, R> {
        final Function<? super T, Optional<? extends R>> mapper;

        public MapOptionalConditionalSubscriber(ConditionalSubscriber<? super R> downstream, Function<? super T, Optional<? extends R>> mapper) {
            super(downstream);
            this.mapper = mapper;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (tryOnNext(t2)) {
                return;
            }
            this.upstream.request(1L);
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public R poll() throws Throwable {
            while (true) {
                T tPoll = this.qs.poll();
                if (tPoll == null) {
                    return null;
                }
                Optional<? extends R> optionalApply = this.mapper.apply(tPoll);
                Objects.requireNonNull(optionalApply, "The mapper returned a null Optional");
                Optional<? extends R> optional = optionalApply;
                if (optional.isPresent()) {
                    return (R) optional.get();
                }
                if (this.sourceMode == 2) {
                    this.qs.request(1L);
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
                return true;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return true;
            }
            try {
                Optional<? extends R> optionalApply = this.mapper.apply(t2);
                Objects.requireNonNull(optionalApply, "The mapper returned a null Optional");
                Optional<? extends R> optional = optionalApply;
                if (optional.isPresent()) {
                    return this.downstream.tryOnNext((Object) optional.get());
                }
                return false;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }
    }

    public static final class MapOptionalSubscriber<T, R> extends BasicFuseableSubscriber<T, R> implements ConditionalSubscriber<T> {
        final Function<? super T, Optional<? extends R>> mapper;

        public MapOptionalSubscriber(Subscriber<? super R> downstream, Function<? super T, Optional<? extends R>> mapper) {
            super(downstream);
            this.mapper = mapper;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (tryOnNext(t2)) {
                return;
            }
            this.upstream.request(1L);
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public R poll() throws Throwable {
            while (true) {
                T tPoll = this.qs.poll();
                if (tPoll == null) {
                    return null;
                }
                Optional<? extends R> optionalApply = this.mapper.apply(tPoll);
                Objects.requireNonNull(optionalApply, "The mapper returned a null Optional");
                Optional<? extends R> optional = optionalApply;
                if (optional.isPresent()) {
                    return (R) optional.get();
                }
                if (this.sourceMode == 2) {
                    this.qs.request(1L);
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
                return true;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return true;
            }
            try {
                Optional<? extends R> optionalApply = this.mapper.apply(t2);
                Objects.requireNonNull(optionalApply, "The mapper returned a null Optional");
                Optional<? extends R> optional = optionalApply;
                if (!optional.isPresent()) {
                    return false;
                }
                this.downstream.onNext((Object) optional.get());
                return true;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }
    }

    public FlowableMapOptional(Flowable<T> source, Function<? super T, Optional<? extends R>> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        if (s2 instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new MapOptionalConditionalSubscriber((ConditionalSubscriber) s2, this.mapper));
        } else {
            this.source.subscribe((FlowableSubscriber) new MapOptionalSubscriber(s2, this.mapper));
        }
    }
}
