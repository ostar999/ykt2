package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import io.reactivex.rxjava3.parallel.ParallelFailureHandling;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.Optional;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ParallelMapTryOptional<T, R> extends ParallelFlowable<R> {
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
    final Function<? super T, Optional<? extends R>> mapper;
    final ParallelFlowable<T> source;

    /* renamed from: io.reactivex.rxjava3.internal.jdk8.ParallelMapTryOptional$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$reactivex$rxjava3$parallel$ParallelFailureHandling;

        static {
            int[] iArr = new int[ParallelFailureHandling.values().length];
            $SwitchMap$io$reactivex$rxjava3$parallel$ParallelFailureHandling = iArr;
            try {
                iArr[ParallelFailureHandling.RETRY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$reactivex$rxjava3$parallel$ParallelFailureHandling[ParallelFailureHandling.SKIP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$reactivex$rxjava3$parallel$ParallelFailureHandling[ParallelFailureHandling.STOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static final class ParallelMapTryConditionalSubscriber<T, R> implements ConditionalSubscriber<T>, Subscription {
        boolean done;
        final ConditionalSubscriber<? super R> downstream;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
        final Function<? super T, Optional<? extends R>> mapper;
        Subscription upstream;

        public ParallelMapTryConditionalSubscriber(ConditionalSubscriber<? super R> actual, Function<? super T, Optional<? extends R>> mapper, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler) {
            this.downstream = actual;
            this.mapper = mapper;
            this.errorHandler = errorHandler;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
            } else {
                this.done = true;
                this.downstream.onError(t2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (tryOnNext(t2) || this.done) {
                return;
            }
            this.upstream.request(1L);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            this.upstream.request(n2);
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(T t2) {
            int i2;
            if (this.done) {
                return false;
            }
            long j2 = 0;
            do {
                try {
                    Optional<? extends R> optionalApply = this.mapper.apply(t2);
                    Objects.requireNonNull(optionalApply, "The mapper returned a null Optional");
                    Optional<? extends R> optional = optionalApply;
                    return optional.isPresent() && this.downstream.tryOnNext((Object) optional.get());
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    try {
                        j2++;
                        ParallelFailureHandling parallelFailureHandlingApply = this.errorHandler.apply(Long.valueOf(j2), th);
                        Objects.requireNonNull(parallelFailureHandlingApply, "The errorHandler returned a null ParallelFailureHandling");
                        i2 = AnonymousClass1.$SwitchMap$io$reactivex$rxjava3$parallel$ParallelFailureHandling[parallelFailureHandlingApply.ordinal()];
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        cancel();
                        onError(new CompositeException(th, th2));
                        return false;
                    }
                }
            } while (i2 == 1);
            if (i2 != 2) {
                if (i2 != 3) {
                    cancel();
                    onError(th);
                    return false;
                }
                cancel();
                onComplete();
            }
            return false;
        }
    }

    public static final class ParallelMapTrySubscriber<T, R> implements ConditionalSubscriber<T>, Subscription {
        boolean done;
        final Subscriber<? super R> downstream;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
        final Function<? super T, Optional<? extends R>> mapper;
        Subscription upstream;

        public ParallelMapTrySubscriber(Subscriber<? super R> actual, Function<? super T, Optional<? extends R>> mapper, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler) {
            this.downstream = actual;
            this.mapper = mapper;
            this.errorHandler = errorHandler;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
            } else {
                this.done = true;
                this.downstream.onError(t2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (tryOnNext(t2) || this.done) {
                return;
            }
            this.upstream.request(1L);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            this.upstream.request(n2);
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(T t2) {
            int i2;
            if (this.done) {
                return false;
            }
            long j2 = 0;
            do {
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
                    Exceptions.throwIfFatal(th);
                    try {
                        j2++;
                        ParallelFailureHandling parallelFailureHandlingApply = this.errorHandler.apply(Long.valueOf(j2), th);
                        Objects.requireNonNull(parallelFailureHandlingApply, "The errorHandler returned a null ParallelFailureHandling");
                        i2 = AnonymousClass1.$SwitchMap$io$reactivex$rxjava3$parallel$ParallelFailureHandling[parallelFailureHandlingApply.ordinal()];
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        cancel();
                        onError(new CompositeException(th, th2));
                        return false;
                    }
                }
            } while (i2 == 1);
            if (i2 != 2) {
                if (i2 != 3) {
                    cancel();
                    onError(th);
                    return false;
                }
                cancel();
                onComplete();
            }
            return false;
        }
    }

    public ParallelMapTryOptional(ParallelFlowable<T> source, Function<? super T, Optional<? extends R>> mapper, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler) {
        this.source = source;
        this.mapper = mapper;
        this.errorHandler = errorHandler;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super R>[] subscribers) {
        if (validate(subscribers)) {
            int length = subscribers.length;
            Subscriber<? super T>[] subscriberArr = new Subscriber[length];
            for (int i2 = 0; i2 < length; i2++) {
                Subscriber<? super R> subscriber = subscribers[i2];
                if (subscriber instanceof ConditionalSubscriber) {
                    subscriberArr[i2] = new ParallelMapTryConditionalSubscriber((ConditionalSubscriber) subscriber, this.mapper, this.errorHandler);
                } else {
                    subscriberArr[i2] = new ParallelMapTrySubscriber(subscriber, this.mapper, this.errorHandler);
                }
            }
            this.source.subscribe(subscriberArr);
        }
    }
}
