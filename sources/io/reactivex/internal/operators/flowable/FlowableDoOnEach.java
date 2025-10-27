package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableDoOnEach<T> extends AbstractFlowableWithUpstream<T, T> {
    final Action onAfterTerminate;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;

    public static final class DoOnEachConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
        final Action onAfterTerminate;
        final Action onComplete;
        final Consumer<? super Throwable> onError;
        final Consumer<? super T> onNext;

        public DoOnEachConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
            super(conditionalSubscriber);
            this.onNext = consumer;
            this.onError = consumer2;
            this.onComplete = action;
            this.onAfterTerminate = action2;
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            try {
                this.onComplete.run();
                this.done = true;
                this.downstream.onComplete();
                try {
                    this.onAfterTerminate.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            } catch (Throwable th2) {
                fail(th2);
            }
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            boolean z2 = true;
            this.done = true;
            try {
                this.onError.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(new CompositeException(th, th2));
                z2 = false;
            }
            if (z2) {
                this.downstream.onError(th);
            }
            try {
                this.onAfterTerminate.run();
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(th3);
            }
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
                this.onNext.accept(t2);
                this.downstream.onNext(t2);
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            try {
                T tPoll = this.qs.poll();
                if (tPoll != null) {
                    try {
                        this.onNext.accept(tPoll);
                    } catch (Throwable th) {
                        try {
                            Exceptions.throwIfFatal(th);
                            try {
                                this.onError.accept(th);
                                throw ExceptionHelper.throwIfThrowable(th);
                            } catch (Throwable th2) {
                                throw new CompositeException(th, th2);
                            }
                        } finally {
                            this.onAfterTerminate.run();
                        }
                    }
                } else if (this.sourceMode == 1) {
                    this.onComplete.run();
                }
                return tPoll;
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                try {
                    this.onError.accept(th3);
                    throw ExceptionHelper.throwIfThrowable(th3);
                } catch (Throwable th4) {
                    throw new CompositeException(th3, th4);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i2) {
            return transitiveBoundaryFusion(i2);
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t2) {
            if (this.done) {
                return false;
            }
            try {
                this.onNext.accept(t2);
                return this.downstream.tryOnNext(t2);
            } catch (Throwable th) {
                fail(th);
                return false;
            }
        }
    }

    public static final class DoOnEachSubscriber<T> extends BasicFuseableSubscriber<T, T> {
        final Action onAfterTerminate;
        final Action onComplete;
        final Consumer<? super Throwable> onError;
        final Consumer<? super T> onNext;

        public DoOnEachSubscriber(Subscriber<? super T> subscriber, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
            super(subscriber);
            this.onNext = consumer;
            this.onError = consumer2;
            this.onComplete = action;
            this.onAfterTerminate = action2;
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            try {
                this.onComplete.run();
                this.done = true;
                this.downstream.onComplete();
                try {
                    this.onAfterTerminate.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            } catch (Throwable th2) {
                fail(th2);
            }
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            boolean z2 = true;
            this.done = true;
            try {
                this.onError.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(new CompositeException(th, th2));
                z2 = false;
            }
            if (z2) {
                this.downstream.onError(th);
            }
            try {
                this.onAfterTerminate.run();
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(th3);
            }
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
                this.onNext.accept(t2);
                this.downstream.onNext(t2);
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            try {
                T tPoll = this.qs.poll();
                if (tPoll != null) {
                    try {
                        this.onNext.accept(tPoll);
                    } catch (Throwable th) {
                        try {
                            Exceptions.throwIfFatal(th);
                            try {
                                this.onError.accept(th);
                                throw ExceptionHelper.throwIfThrowable(th);
                            } catch (Throwable th2) {
                                throw new CompositeException(th, th2);
                            }
                        } finally {
                            this.onAfterTerminate.run();
                        }
                    }
                } else if (this.sourceMode == 1) {
                    this.onComplete.run();
                }
                return tPoll;
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                try {
                    this.onError.accept(th3);
                    throw ExceptionHelper.throwIfThrowable(th3);
                } catch (Throwable th4) {
                    throw new CompositeException(th3, th4);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i2) {
            return transitiveBoundaryFusion(i2);
        }
    }

    public FlowableDoOnEach(Flowable<T> flowable, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
        super(flowable);
        this.onNext = consumer;
        this.onError = consumer2;
        this.onComplete = action;
        this.onAfterTerminate = action2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new DoOnEachConditionalSubscriber((ConditionalSubscriber) subscriber, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
        } else {
            this.source.subscribe((FlowableSubscriber) new DoOnEachSubscriber(subscriber, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
        }
    }
}
