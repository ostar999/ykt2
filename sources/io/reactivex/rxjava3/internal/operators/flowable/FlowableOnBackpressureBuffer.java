package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableOnBackpressureBuffer<T> extends AbstractFlowableWithUpstream<T, T> {
    final int bufferSize;
    final boolean delayError;
    final Action onOverflow;
    final boolean unbounded;

    public static final class BackpressureBufferSubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -2514538129242366402L;
        volatile boolean cancelled;
        final boolean delayError;
        volatile boolean done;
        final Subscriber<? super T> downstream;
        Throwable error;
        final Action onOverflow;
        boolean outputFused;
        final SimplePlainQueue<T> queue;
        final AtomicLong requested = new AtomicLong();
        Subscription upstream;

        public BackpressureBufferSubscriber(Subscriber<? super T> actual, int bufferSize, boolean unbounded, boolean delayError, Action onOverflow) {
            this.downstream = actual;
            this.onOverflow = onOverflow;
            this.delayError = delayError;
            this.queue = unbounded ? new SpscLinkedArrayQueue<>(bufferSize) : new SpscArrayQueue<>(bufferSize);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            if (this.outputFused || getAndIncrement() != 0) {
                return;
            }
            this.queue.clear();
        }

        public boolean checkTerminated(boolean d3, boolean empty, Subscriber<? super T> a3) {
            if (this.cancelled) {
                this.queue.clear();
                return true;
            }
            if (!d3) {
                return false;
            }
            if (this.delayError) {
                if (!empty) {
                    return false;
                }
                Throwable th = this.error;
                if (th != null) {
                    a3.onError(th);
                } else {
                    a3.onComplete();
                }
                return true;
            }
            Throwable th2 = this.error;
            if (th2 != null) {
                this.queue.clear();
                a3.onError(th2);
                return true;
            }
            if (!empty) {
                return false;
            }
            a3.onComplete();
            return true;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            this.queue.clear();
        }

        public void drain() {
            if (getAndIncrement() == 0) {
                SimplePlainQueue<T> simplePlainQueue = this.queue;
                Subscriber<? super T> subscriber = this.downstream;
                int iAddAndGet = 1;
                while (!checkTerminated(this.done, simplePlainQueue.isEmpty(), subscriber)) {
                    long j2 = this.requested.get();
                    long j3 = 0;
                    while (j3 != j2) {
                        boolean z2 = this.done;
                        T tPoll = simplePlainQueue.poll();
                        boolean z3 = tPoll == null;
                        if (checkTerminated(z2, z3, subscriber)) {
                            return;
                        }
                        if (z3) {
                            break;
                        }
                        subscriber.onNext(tPoll);
                        j3++;
                    }
                    if (j3 == j2 && checkTerminated(this.done, simplePlainQueue.isEmpty(), subscriber)) {
                        return;
                    }
                    if (j3 != 0 && j2 != Long.MAX_VALUE) {
                        this.requested.addAndGet(-j3);
                    }
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            if (this.outputFused) {
                this.downstream.onComplete();
            } else {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.error = t2;
            this.done = true;
            if (this.outputFused) {
                this.downstream.onError(t2);
            } else {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.queue.offer(t2)) {
                if (this.outputFused) {
                    this.downstream.onNext(null);
                    return;
                } else {
                    drain();
                    return;
                }
            }
            this.upstream.cancel();
            MissingBackpressureException missingBackpressureException = new MissingBackpressureException("Buffer is full");
            try {
                this.onOverflow.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                missingBackpressureException.initCause(th);
            }
            onError(missingBackpressureException);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            return this.queue.poll();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (this.outputFused || !SubscriptionHelper.validate(n2)) {
                return;
            }
            BackpressureHelper.add(this.requested, n2);
            drain();
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            if ((mode & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }
    }

    public FlowableOnBackpressureBuffer(Flowable<T> source, int bufferSize, boolean unbounded, boolean delayError, Action onOverflow) {
        super(source);
        this.bufferSize = bufferSize;
        this.unbounded = unbounded;
        this.delayError = delayError;
        this.onOverflow = onOverflow;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new BackpressureBufferSubscriber(s2, this.bufferSize, this.unbounded, this.delayError, this.onOverflow));
    }
}
