package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class ObservableDematerialize<T, R> extends AbstractObservableWithUpstream<T, R> {
    final Function<? super T, ? extends Notification<R>> selector;

    public static final class DematerializeObserver<T, R> implements Observer<T>, Disposable {
        boolean done;
        final Observer<? super R> downstream;
        final Function<? super T, ? extends Notification<R>> selector;
        Disposable upstream;

        public DematerializeObserver(Observer<? super R> downstream, Function<? super T, ? extends Notification<R>> selector) {
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

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
            } else {
                this.done = true;
                this.downstream.onError(t2);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T item) {
            if (this.done) {
                if (item instanceof Notification) {
                    Notification notification = (Notification) item;
                    if (notification.isOnError()) {
                        RxJavaPlugins.onError(notification.getError());
                        return;
                    }
                    return;
                }
                return;
            }
            try {
                Notification<R> notificationApply = this.selector.apply(item);
                Objects.requireNonNull(notificationApply, "The selector returned a null Notification");
                Notification<R> notification2 = notificationApply;
                if (notification2.isOnError()) {
                    this.upstream.dispose();
                    onError(notification2.getError());
                } else if (!notification2.isOnComplete()) {
                    this.downstream.onNext(notification2.getValue());
                } else {
                    this.upstream.dispose();
                    onComplete();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableDematerialize(ObservableSource<T> source, Function<? super T, ? extends Notification<R>> selector) {
        super(source);
        this.selector = selector;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new DematerializeObserver(observer, this.selector));
    }
}
