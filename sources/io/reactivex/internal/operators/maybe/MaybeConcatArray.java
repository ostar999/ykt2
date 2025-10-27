package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class MaybeConcatArray<T> extends Flowable<T> {
    final MaybeSource<? extends T>[] sources;

    public static final class ConcatMaybeObserver<T> extends AtomicInteger implements MaybeObserver<T>, Subscription {
        private static final long serialVersionUID = 3520831347801429610L;
        final Subscriber<? super T> downstream;
        int index;
        long produced;
        final MaybeSource<? extends T>[] sources;
        final AtomicLong requested = new AtomicLong();
        final SequentialDisposable disposables = new SequentialDisposable();
        final AtomicReference<Object> current = new AtomicReference<>(NotificationLite.COMPLETE);

        public ConcatMaybeObserver(Subscriber<? super T> subscriber, MaybeSource<? extends T>[] maybeSourceArr) {
            this.downstream = subscriber;
            this.sources = maybeSourceArr;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.disposables.dispose();
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            AtomicReference<Object> atomicReference = this.current;
            Subscriber<? super T> subscriber = this.downstream;
            SequentialDisposable sequentialDisposable = this.disposables;
            while (!sequentialDisposable.isDisposed()) {
                Object obj = atomicReference.get();
                if (obj != null) {
                    boolean z2 = true;
                    if (obj != NotificationLite.COMPLETE) {
                        long j2 = this.produced;
                        if (j2 != this.requested.get()) {
                            this.produced = j2 + 1;
                            atomicReference.lazySet(null);
                            subscriber.onNext(obj);
                        } else {
                            z2 = false;
                        }
                    } else {
                        atomicReference.lazySet(null);
                    }
                    if (z2 && !sequentialDisposable.isDisposed()) {
                        int i2 = this.index;
                        MaybeSource<? extends T>[] maybeSourceArr = this.sources;
                        if (i2 == maybeSourceArr.length) {
                            subscriber.onComplete();
                            return;
                        } else {
                            this.index = i2 + 1;
                            maybeSourceArr[i2].subscribe(this);
                        }
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            atomicReference.lazySet(null);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.current.lazySet(NotificationLite.COMPLETE);
            drain();
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            this.disposables.replace(disposable);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t2) {
            this.current.lazySet(t2);
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.requested, j2);
                drain();
            }
        }
    }

    public MaybeConcatArray(MaybeSource<? extends T>[] maybeSourceArr) {
        this.sources = maybeSourceArr;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        ConcatMaybeObserver concatMaybeObserver = new ConcatMaybeObserver(subscriber, this.sources);
        subscriber.onSubscribe(concatMaybeObserver);
        concatMaybeObserver.drain();
    }
}
