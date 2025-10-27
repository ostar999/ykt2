package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableOnBackpressureBufferStrategy<T> extends AbstractFlowableWithUpstream<T, T> {
    final long bufferSize;
    final Action onOverflow;
    final BackpressureOverflowStrategy strategy;

    /* renamed from: io.reactivex.rxjava3.internal.operators.flowable.FlowableOnBackpressureBufferStrategy$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$reactivex$rxjava3$core$BackpressureOverflowStrategy;

        static {
            int[] iArr = new int[BackpressureOverflowStrategy.values().length];
            $SwitchMap$io$reactivex$rxjava3$core$BackpressureOverflowStrategy = iArr;
            try {
                iArr[BackpressureOverflowStrategy.DROP_LATEST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$reactivex$rxjava3$core$BackpressureOverflowStrategy[BackpressureOverflowStrategy.DROP_OLDEST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static final class OnBackpressureBufferStrategySubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 3240706908776709697L;
        final long bufferSize;
        volatile boolean cancelled;
        volatile boolean done;
        final Subscriber<? super T> downstream;
        Throwable error;
        final Action onOverflow;
        final BackpressureOverflowStrategy strategy;
        Subscription upstream;
        final AtomicLong requested = new AtomicLong();
        final Deque<T> deque = new ArrayDeque();

        public OnBackpressureBufferStrategySubscriber(Subscriber<? super T> actual, Action onOverflow, BackpressureOverflowStrategy strategy, long bufferSize) {
            this.downstream = actual;
            this.onOverflow = onOverflow;
            this.strategy = strategy;
            this.bufferSize = bufferSize;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            if (getAndIncrement() == 0) {
                clear(this.deque);
            }
        }

        public void clear(Deque<T> dq) {
            synchronized (dq) {
                dq.clear();
            }
        }

        public void drain() {
            boolean zIsEmpty;
            T tPoll;
            if (getAndIncrement() != 0) {
                return;
            }
            Deque<T> deque = this.deque;
            Subscriber<? super T> subscriber = this.downstream;
            int iAddAndGet = 1;
            do {
                long j2 = this.requested.get();
                long j3 = 0;
                while (j3 != j2) {
                    if (this.cancelled) {
                        clear(deque);
                        return;
                    }
                    boolean z2 = this.done;
                    synchronized (deque) {
                        tPoll = deque.poll();
                    }
                    boolean z3 = tPoll == null;
                    if (z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            clear(deque);
                            subscriber.onError(th);
                            return;
                        } else if (z3) {
                            subscriber.onComplete();
                            return;
                        }
                    }
                    if (z3) {
                        break;
                    }
                    subscriber.onNext(tPoll);
                    j3++;
                }
                if (j3 == j2) {
                    if (this.cancelled) {
                        clear(deque);
                        return;
                    }
                    boolean z4 = this.done;
                    synchronized (deque) {
                        zIsEmpty = deque.isEmpty();
                    }
                    if (z4) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            clear(deque);
                            subscriber.onError(th2);
                            return;
                        } else if (zIsEmpty) {
                            subscriber.onComplete();
                            return;
                        }
                    }
                }
                if (j3 != 0) {
                    BackpressureHelper.produced(this.requested, j3);
                }
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.error = t2;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            boolean z2;
            boolean z3;
            if (this.done) {
                return;
            }
            Deque<T> deque = this.deque;
            synchronized (deque) {
                z2 = false;
                if (deque.size() == this.bufferSize) {
                    int i2 = AnonymousClass1.$SwitchMap$io$reactivex$rxjava3$core$BackpressureOverflowStrategy[this.strategy.ordinal()];
                    z3 = true;
                    if (i2 == 1) {
                        deque.pollLast();
                        deque.offer(t2);
                    } else if (i2 == 2) {
                        deque.poll();
                        deque.offer(t2);
                    }
                    z3 = false;
                    z2 = true;
                } else {
                    deque.offer(t2);
                    z3 = false;
                }
            }
            if (!z2) {
                if (!z3) {
                    drain();
                    return;
                } else {
                    this.upstream.cancel();
                    onError(new MissingBackpressureException());
                    return;
                }
            }
            Action action = this.onOverflow;
            if (action != null) {
                try {
                    action.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.cancel();
                    onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                drain();
            }
        }
    }

    public FlowableOnBackpressureBufferStrategy(Flowable<T> source, long bufferSize, Action onOverflow, BackpressureOverflowStrategy strategy) {
        super(source);
        this.bufferSize = bufferSize;
        this.onOverflow = onOverflow;
        this.strategy = strategy;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new OnBackpressureBufferStrategySubscriber(s2, this.onOverflow, this.strategy, this.bufferSize));
    }
}
