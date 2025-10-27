package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableFromStream<T> extends Flowable<T> {
    final Stream<T> stream;

    public static abstract class AbstractStreamSubscription<T> extends AtomicLong implements QueueSubscription<T> {
        private static final long serialVersionUID = -9082954702547571853L;
        volatile boolean cancelled;
        AutoCloseable closeable;
        Iterator<T> iterator;
        boolean once;

        public AbstractStreamSubscription(Iterator<T> iterator, AutoCloseable closeable) {
            this.iterator = iterator;
            this.closeable = closeable;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            request(1L);
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            this.iterator = null;
            AutoCloseable autoCloseable = this.closeable;
            this.closeable = null;
            if (autoCloseable != null) {
                FlowableFromStream.closeSafely(autoCloseable);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            Iterator<T> it = this.iterator;
            if (it == null) {
                return true;
            }
            if (!this.once || it.hasNext()) {
                return false;
            }
            clear();
            return true;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(@NonNull T value) {
            throw new UnsupportedOperationException();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            Iterator<T> it = this.iterator;
            if (it == null) {
                return null;
            }
            if (!this.once) {
                this.once = true;
            } else if (!it.hasNext()) {
                clear();
                return null;
            }
            T next = this.iterator.next();
            Objects.requireNonNull(next, "The Stream's Iterator.next() returned a null value");
            return next;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2) && BackpressureHelper.add(this, n2) == 0) {
                run(n2);
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            if ((mode & 1) == 0) {
                return 0;
            }
            lazySet(Long.MAX_VALUE);
            return 1;
        }

        public abstract void run(long n2);

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(@NonNull T v12, @NonNull T v2) {
            throw new UnsupportedOperationException();
        }
    }

    public static final class StreamConditionalSubscription<T> extends AbstractStreamSubscription<T> {
        private static final long serialVersionUID = -9082954702547571853L;
        final ConditionalSubscriber<? super T> downstream;

        public StreamConditionalSubscription(ConditionalSubscriber<? super T> downstream, Iterator<T> iterator, AutoCloseable closeable) {
            super(iterator, closeable);
            this.downstream = downstream;
        }

        @Override // io.reactivex.rxjava3.internal.jdk8.FlowableFromStream.AbstractStreamSubscription
        public void run(long n2) {
            Iterator<T> it = this.iterator;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.downstream;
            long j2 = 0;
            while (!this.cancelled) {
                try {
                    T next = it.next();
                    Objects.requireNonNull(next, "The Stream's Iterator returned a null value");
                    if (conditionalSubscriber.tryOnNext(next)) {
                        j2++;
                    }
                    if (this.cancelled) {
                        continue;
                    } else {
                        try {
                            if (!it.hasNext()) {
                                conditionalSubscriber.onComplete();
                                this.cancelled = true;
                            } else if (j2 != n2) {
                                continue;
                            } else {
                                n2 = get();
                                if (j2 != n2) {
                                    continue;
                                } else if (compareAndSet(n2, 0L)) {
                                    return;
                                } else {
                                    n2 = get();
                                }
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            conditionalSubscriber.onError(th);
                            this.cancelled = true;
                        }
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    conditionalSubscriber.onError(th2);
                    this.cancelled = true;
                }
            }
            clear();
        }
    }

    public static final class StreamSubscription<T> extends AbstractStreamSubscription<T> {
        private static final long serialVersionUID = -9082954702547571853L;
        final Subscriber<? super T> downstream;

        public StreamSubscription(Subscriber<? super T> downstream, Iterator<T> iterator, AutoCloseable closeable) {
            super(iterator, closeable);
            this.downstream = downstream;
        }

        @Override // io.reactivex.rxjava3.internal.jdk8.FlowableFromStream.AbstractStreamSubscription
        public void run(long n2) {
            Iterator<T> it = this.iterator;
            Subscriber<? super T> subscriber = this.downstream;
            long j2 = 0;
            while (!this.cancelled) {
                try {
                    T next = it.next();
                    Objects.requireNonNull(next, "The Stream's Iterator returned a null value");
                    subscriber.onNext(next);
                    if (this.cancelled) {
                        continue;
                    } else {
                        try {
                            if (it.hasNext()) {
                                j2++;
                                if (j2 != n2) {
                                    continue;
                                } else {
                                    n2 = get();
                                    if (j2 != n2) {
                                        continue;
                                    } else if (compareAndSet(n2, 0L)) {
                                        return;
                                    } else {
                                        n2 = get();
                                    }
                                }
                            } else {
                                subscriber.onComplete();
                                this.cancelled = true;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            subscriber.onError(th);
                            this.cancelled = true;
                        }
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    subscriber.onError(th2);
                    this.cancelled = true;
                }
            }
            clear();
        }
    }

    public FlowableFromStream(Stream<T> stream) {
        this.stream = stream;
    }

    public static void closeSafely(AutoCloseable c3) {
        try {
            c3.close();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }

    public static <T> void subscribeStream(Subscriber<? super T> s2, Stream<T> stream) {
        try {
            Iterator it = stream.iterator();
            if (!it.hasNext()) {
                EmptySubscription.complete(s2);
                closeSafely(stream);
            } else if (s2 instanceof ConditionalSubscriber) {
                s2.onSubscribe(new StreamConditionalSubscription((ConditionalSubscriber) s2, it, stream));
            } else {
                s2.onSubscribe(new StreamSubscription(s2, it, stream));
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, s2);
            closeSafely(stream);
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        subscribeStream(s2, this.stream);
    }
}
