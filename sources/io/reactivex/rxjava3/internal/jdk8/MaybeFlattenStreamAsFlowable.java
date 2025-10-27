package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class MaybeFlattenStreamAsFlowable<T, R> extends Flowable<R> {
    final Function<? super T, ? extends Stream<? extends R>> mapper;
    final Maybe<T> source;

    public static final class FlattenStreamMultiObserver<T, R> extends BasicIntQueueSubscription<R> implements MaybeObserver<T>, SingleObserver<T> {
        private static final long serialVersionUID = 7363336003027148283L;
        volatile boolean cancelled;
        AutoCloseable close;
        final Subscriber<? super R> downstream;
        long emitted;
        volatile Iterator<? extends R> iterator;
        final Function<? super T, ? extends Stream<? extends R>> mapper;
        boolean once;
        boolean outputFused;
        final AtomicLong requested = new AtomicLong();
        Disposable upstream;

        public FlattenStreamMultiObserver(Subscriber<? super R> downstream, Function<? super T, ? extends Stream<? extends R>> mapper) {
            this.downstream = downstream;
            this.mapper = mapper;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.dispose();
            if (this.outputFused) {
                return;
            }
            drain();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            this.iterator = null;
            AutoCloseable autoCloseable = this.close;
            this.close = null;
            close(autoCloseable);
        }

        public void close(AutoCloseable c3) {
            if (c3 != null) {
                try {
                    c3.close();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super R> subscriber = this.downstream;
            long j2 = this.emitted;
            long j3 = this.requested.get();
            Iterator<? extends R> it = this.iterator;
            int iAddAndGet = 1;
            while (true) {
                if (this.cancelled) {
                    clear();
                } else if (this.outputFused) {
                    if (it != null) {
                        subscriber.onNext(null);
                        subscriber.onComplete();
                    }
                } else if (it != null && j2 != j3) {
                    try {
                        R next = it.next();
                        if (!this.cancelled) {
                            subscriber.onNext(next);
                            j2++;
                            if (!this.cancelled) {
                                try {
                                    boolean zHasNext = it.hasNext();
                                    if (!this.cancelled && !zHasNext) {
                                        subscriber.onComplete();
                                        this.cancelled = true;
                                    }
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    subscriber.onError(th);
                                    this.cancelled = true;
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        subscriber.onError(th2);
                        this.cancelled = true;
                    }
                }
                this.emitted = j2;
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
                j3 = this.requested.get();
                if (it == null) {
                    it = this.iterator;
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            Iterator<? extends R> it = this.iterator;
            if (it == null) {
                return true;
            }
            if (!this.once || it.hasNext()) {
                return false;
            }
            clear();
            return true;
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(@NonNull Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(@NonNull Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(@NonNull T t2) {
            try {
                Stream<? extends R> streamApply = this.mapper.apply(t2);
                Objects.requireNonNull(streamApply, "The mapper returned a null Stream");
                Stream<? extends R> stream = streamApply;
                Iterator<? extends R> it = stream.iterator();
                if (!it.hasNext()) {
                    this.downstream.onComplete();
                    close(stream);
                } else {
                    this.iterator = it;
                    this.close = stream;
                    drain();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public R poll() throws Throwable {
            Iterator<? extends R> it = this.iterator;
            if (it == null) {
                return null;
            }
            if (!this.once) {
                this.once = true;
            } else if (!it.hasNext()) {
                clear();
                return null;
            }
            return it.next();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                drain();
            }
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

    public MaybeFlattenStreamAsFlowable(Maybe<T> source, Function<? super T, ? extends Stream<? extends R>> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(@NonNull Subscriber<? super R> s2) {
        this.source.subscribe(new FlattenStreamMultiObserver(s2, this.mapper));
    }
}
