package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class SingleDematerialize<T, R> extends Maybe<R> {
    final Function<? super T, Notification<R>> selector;
    final Single<T> source;

    public static final class DematerializeObserver<T, R> implements SingleObserver<T>, Disposable {
        final MaybeObserver<? super R> downstream;
        final Function<? super T, Notification<R>> selector;
        Disposable upstream;

        public DematerializeObserver(MaybeObserver<? super R> downstream, Function<? super T, Notification<R>> selector) {
            this.downstream = downstream;
            this.selector = selector;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t2) {
            try {
                Notification<R> notificationApply = this.selector.apply(t2);
                Objects.requireNonNull(notificationApply, "The selector returned a null Notification");
                Notification<R> notification = notificationApply;
                if (notification.isOnNext()) {
                    this.downstream.onSuccess(notification.getValue());
                } else if (notification.isOnComplete()) {
                    this.downstream.onComplete();
                } else {
                    this.downstream.onError(notification.getError());
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }
    }

    public SingleDematerialize(Single<T> source, Function<? super T, Notification<R>> selector) {
        this.source = source;
        this.selector = selector;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super R> observer) {
        this.source.subscribe(new DematerializeObserver(observer, this.selector));
    }
}
